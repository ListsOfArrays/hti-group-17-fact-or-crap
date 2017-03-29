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
    private Player[] playerList;
    private Random rng;
    private final int RUSH_HOUR_TIME = 60_000; // 60 seconds
    private int turnNum = 0;

    public void startGame(RealPlayer realPlayer) {
        ArrayList<Card> list = Card.getCards();
        Collections.shuffle(list);
        roundCards = list.toArray(new Card[list.size()]);

        roundNum = 0;
        this.tokensLeft = list.size();

        rng = new Random();
        String[] nameList = new String[] {"Robert", "Wendy (AI)", "Dave", "Mary", "John", "Valerie (AI)", "armsDealer"};
        playerList = new Player[2 + rng.nextInt(7)];
        for (int i = 1; i < playerList.length; i++) {
            playerList[i] = new AI(nameList[i - 1]);
        }
        playerList[0] = realPlayer;
        suddenDeathPlayers = null;
        allPlayersCount = playerList.length;
        nextRound();
    }

    private void nextRound() {
        turnNum += 1;
        firstCorrect = false;
        if (roundNum < roundCards.length && tokensLeft > 0) {
            // during sudden death, always use rush hour
            if (suddenDeathPlayers != null) {
                startRushHour(suddenDeathPlayers);
            } else if (rng.nextInt(10) != 0) {
                currentCard = roundCards[roundNum++];
                firstCorrect = true;
                currentPlayersCount = allPlayersCount;
            }
            // randomly deal a rush hour card
            else {
                currentPlayersCount = 1;
                ArrayList<Player> rushHourList = new ArrayList<>(1);
                if (rng.nextBoolean()) {
                    rushHourList.add(playerList[0]);
                } else {
                    rushHourList.add(playerList[rng.nextInt(playerList.length - 1) + 1]);
                }
                startRushHour(rushHourList);
            }
        } else {
            tryToEndGame();
        }
    }

    private ArrayList<Player> suddenDeathPlayers;
    private ArrayList<Player> rushHourPlayers;

    private void startRushHour(final ArrayList<Player> players) {
        final int endRound = turnNum + 5;
        rushHourPlayers = players;

        // start timer
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(RUSH_HOUR_TIME);
                } catch (InterruptedException e) {
                } finally {
                    if (turnNum < endRound) {
                        turnNum = endRound;
                        rushHourPlayers = null;
                        nextRound();
                    }
                }
            }
        }).start();

        rushHourRound();
    }

    private void rushHourRound() {
        turnNum += 1;
        if (roundNum < roundCards.length && tokensLeft > 0) {

        }
    }

    private void tryToEndGame() {
        turnNum += 1;
        int mostPoints = playerList[0].getPoints();

        // note: we don't add the first player until the for loop below
        ArrayList<Player> bestPlayers = new ArrayList<>();
        Player[] currentPlayers;
        if (suddenDeathPlayers != null) {
            currentPlayers = suddenDeathPlayers.toArray(new Player[suddenDeathPlayers.size()]);
        } else {
            currentPlayers = playerList;
        }

        for (Player player : currentPlayers) {
            // tie
            if (player.getPoints() == mostPoints) {
                bestPlayers.add(player);
            // new winner
            } else if (player.getPoints() > mostPoints) {
                bestPlayers.clear();
                mostPoints = player.getPoints();
                bestPlayers.add(player);
            }
            // loser!
        }

        // only one player won!
        if (bestPlayers.size() == 1) {
            bestPlayers.get(0).setWon(true);
            someoneWon();
        }
        // tie, sudden death!
        else if (bestPlayers.size() > 1) {
            ArrayList<Card> list = Card.getCards();
            Collections.shuffle(list);
            roundCards = list.toArray(new Card[list.size()]);
            roundNum = 0;
            this.tokensLeft = list.size();
            activateSuddenDeath(bestPlayers);
        }
        // the universe has ended!
        else if (bestPlayers.size() == 0) {
            throw new AssertionError("This should not have been 0, ever!");
        }
    }

    private void activateSuddenDeath(ArrayList<Player> bestPlayers) {
        suddenDeathPlayers = bestPlayers;
        nextRound();
    }

    private void someoneWon() {
        for (Player player : playerList)
            player.endedGame();
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    private final Object threadLock = new Object();
    public void makeGuess(boolean guessedTrue, NetworkConnectionStub.NetworkCallback networkCallback, int turnNum) {
        synchronized (threadLock) {
            currentPlayersCount -= 1;
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
            } else if (currentPlayersCount == 0) {
                firstCorrect = false;
                nextRound();
            }
        }
    }

    public Player[] getResults() {
        return playerList;
    }
}
