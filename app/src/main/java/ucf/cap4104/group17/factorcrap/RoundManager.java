package ucf.cap4104.group17.factorcrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jacob on 3/23/2017.
 */

public enum RoundManager {
    INSTANCE();

    private Card currentCard;
    private boolean firstCorrect;
    private Card roundCards[];
    private int roundNum;
    private int tokensLeft;
    private int currentPlayersCount;
    private int allPlayersCount;
    private int numPlayerPlayed;
    private Player realPlayer;
    private AI fakePlayerList[];
    private Random rng;
    private TurnListener listener;

    private final int RUSH_HOUR_TIME = 60_000; // 60 seconds
    private int turnNum = 0;

    public void startGame(TurnListener turnListener, Player realPlayer) {
        ArrayList<Card> list = Card.getCards();
        Collections.shuffle(list);
        roundCards = list.toArray(new Card[list.size()]);
        listener = turnListener;
        this.realPlayer = realPlayer;

        roundNum = 0;
        this.tokensLeft = list.size();

        rng = new Random();
        String[] nameList = new String[] {"Robert", "Wendy (AI)", "Dave", "Mary", "John", "Valerie (AI)", "armsDealer"};
        fakePlayerList = new AI[1 + rng.nextInt(7)];
        for (int i = 0; i < fakePlayerList.length; i++) {
            fakePlayerList[i] = new AI(new Player(nameList[i]));
        }

        allPlayersCount = fakePlayerList.length + 1;

        fakePlayerList[0].getPlayer().setWon(true);
        fakePlayerList[0].getPlayer().onNetworkCallback(20);
        someoneWon();
    }

    private void nextRound() {
        turnNum += 1;
        numPlayerPlayed = 0;
        firstCorrect = false;
        if (roundNum < roundCards.length && tokensLeft > 0) {
            if (rng.nextInt(10) != 0) {
                currentCard = roundCards[roundNum++];
                firstCorrect = true;
                currentPlayersCount = allPlayersCount;
                for (AI ai : fakePlayerList) {
                    ai.newTurn(turnNum);
                }
                listener.newTurn(turnNum, false, 0);
            }
            // randomly deal a rush hour card
            else {
                currentPlayersCount = 1;
                if (rng.nextBoolean()) {
                    rushHour(realPlayer);
                    listener.newTurn(turnNum, true, 5);
                } else {
                    rushHour(fakePlayerList[rng.nextInt(fakePlayerList.length)]);
                    listener.waitTurn();
                }
            }
        } else {
            tryToEndGame();
        }
    }

    private Thread timeThread;

    private void rushHour(Player realPlayer) {
        turnNum += 1;
        final int endRound = turnNum + 5;
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(RUSH_HOUR_TIME);
                } catch (InterruptedException e) {
                } finally {
                    timeThread = null;
                    turnNum = endRound;
                    nextRound();
                }
            }
        });
        timeThread.start();
    }

    private void rushHour(AI fakePlayer) {
        turnNum += 1;
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(RUSH_HOUR_TIME);
                } catch (InterruptedException e) {
                } finally {
                    timeThread = null;
                    nextRound();
                }
            }
        });
        timeThread.start();
    }

    private void tryToEndGame() {
        turnNum += 1;
        roundNum = 0;
        int mostPoints = realPlayer.getPoints();
        ArrayList<AI> bestAI = new ArrayList<>();

        for (AI ai : fakePlayerList) {
            Player fake = ai.getPlayer();
            if (fake.getPoints() == mostPoints) {
                bestAI.add(ai);
            } else if (fake.getPoints() > mostPoints) {
                bestAI.clear();
                mostPoints = fake.getPoints();
                bestAI.add(ai);
            }
        }

        if (mostPoints == realPlayer.getPoints() && bestAI.size() == 0) {
            realPlayer.setWon(true);
            someoneWon();
        }
        // tie, sudden death!
        else if (mostPoints == realPlayer.getPoints() && bestAI.size() > 0) {
            suddenDeath(realPlayer, bestAI);
        }
        // one AI won.
        else if (bestAI.size() == 1) {
            bestAI.get(0).getPlayer().setWon(true);
            someoneWon();
        }
        // multiple AI tied, sudden death!
        else {
            suddenDeath(null, bestAI);
        }
    }

    private void suddenDeath(Player realPlayer, ArrayList<AI> bestAI) {

    }

    private void someoneWon() {
        listener.endedGame();
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    private final Object threadLock = new Object();
    public void makeGuess(boolean guessedTrue, NetworkConnectionStub.NetworkCallback networkCallback, int turnNum) {
        synchronized (threadLock) {
            if (turnNum == this.turnNum) {
                if (guessedTrue == currentCard.getTruthValue()) {
                    // first & correct!
                    if (firstCorrect && tokensLeft > 1) {
                        tokensLeft -= 2;
                        networkCallback.onNetworkCallback(2);
                    // either not first or not enough tokens to satisfy, but still correct
                    } else if (tokensLeft > 0) {
                        tokensLeft -= 1;
                        networkCallback.onNetworkCallback(1);
                    // still correct, not enough tokens
                    } else {
                        networkCallback.onNetworkCallback(0);
                    }
                    firstCorrect = false;
                // not correct
                } else {
                    networkCallback.onNetworkCallback(0);
                }
            // not even the same turn
            } else {
                networkCallback.onNetworkCallback(0);
            }

            // game over!
            if (tokensLeft == 0) {
                firstCorrect = false;
                tryToEndGame();
            }
        }
    }

    public interface TurnListener {
        void newTurn(int turnNum, boolean isRushHour, int cardNum);
        void endedGame();
        void waitTurn();
    }

    public Player[] getResults() {
        Player[] list = new Player[fakePlayerList.length + 1];
        for(int i = 0; i < fakePlayerList.length; i++) {
            list[i] = fakePlayerList[i].getPlayer();
        }
        list[fakePlayerList.length] = realPlayer;
        return list;
    }
}
