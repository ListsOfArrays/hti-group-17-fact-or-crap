package ucf.cap4104.group17.factorcrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jacob on 3/23/2017.
 */

public class RoundManager {
    private Object lock;

    private Card currentCard;
    private boolean first;
    private final Card roundCards[];
    private int roundNum;
    private int tokensLeft;
    private int numPlayerPlayed;
    private final Player realPlayer;
    private AI fakePlayerList[];
    private Random rng;

    private static final int RUSH_HOUR_TIME = 60_000; // 60 seconds

    public RoundManager(int tokensLeft, Player realPlayer) {
        this.realPlayer = realPlayer;

        lock = new Object();

        ArrayList<Card> list = Card.getCards();
        Collections.shuffle(list);
        roundCards = list.toArray(new Card[list.size()]);

        roundNum = 0;
        this.tokensLeft = tokensLeft;

        rng = new Random();
        fakePlayerList = new AI[1 + rng.nextInt(8)];

        nextRound();
    }

    private void nextRound() {
        if (roundNum < roundCards.length && tokensLeft > 0) {
            if (rng.nextInt(10) != 0) {
                currentCard = roundCards[roundNum++];
                first = true;
                numPlayerPlayed = 0;
                for (AI ai : fakePlayerList) {
                    ai.newTurn();
                }
            }
            // randomly deal a rush hour card
            else {
                if (rng.nextBoolean()) {
                    rushHour(realPlayer);
                } else {
                    rushHour(fakePlayerList[rng.nextInt(fakePlayerList.length)]);
                }
            }
        } else {
            endGame();
        }
    }

    private Thread timeThread;

    private void rushHour(Player realPlayer) {
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

    private void rushHour(AI fakePlayer) {
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

    private void endGame() {
        int mostPoints = realPlayer.getPoints();
        ArrayList<AI> bestAI = new ArrayList<>();

        for (AI ai : fakePlayerList) {
            Player fake = ai.onEnd();
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
            bestAI.get(0).onEnd().setWon(true);
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

    }


    public void makeGuess(boolean guessedTrue, NetworkConnectionStub.NetworkCallback networkCallback) {
        synchronized (lock) {
            if (guessedTrue == currentCard.getTruthValue()) {

            }
        }
    }
}
