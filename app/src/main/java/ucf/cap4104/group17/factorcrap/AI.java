package ucf.cap4104.group17.factorcrap;

import java.security.SecureRandom;

/**
 * Created by Jacob on 3/23/2017.
 */

public class AI extends Player {
    private final SecureRandom rng;
    private int realTurnNum;

    public AI(String name) {
        super(name);
        rng = new SecureRandom();
    }

    @Override
    public void rushHourAlert() {

    }

    private int secondsToWaitInMilliseconds(int mean, int range) {
        double clippedGaussian = rng.nextGaussian() / 2;
        if (clippedGaussian < 0)
            clippedGaussian = -clippedGaussian;
        if (clippedGaussian > 1)
            clippedGaussian = 1;

        final double millisecondsToWait = mean + (range * clippedGaussian);
        return (int) (millisecondsToWait * 1000);
    }

    private void newTurn(final int turnNum) {
        realTurnNum = turnNum;
        final boolean guess = rng.nextBoolean();
        final int millisecondsToWait = secondsToWaitInMilliseconds(5, 4);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millisecondsToWait);
                } catch (InterruptedException e) {
                }
                finally {
                    if (turnNum == realTurnNum) {
                        guess(guess, turnNum);
                    }
                }
            }
        }).start();
    }

    private void quickGuess(final int turnNum) {
        realTurnNum = turnNum;
        final int millisecondsToWait = secondsToWaitInMilliseconds(3, 2);
        final boolean guess = rng.nextBoolean();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millisecondsToWait);
                } catch (InterruptedException e) {
                }
                finally {
                    if (turnNum == realTurnNum) {
                        guess(guess, turnNum);
                    }
                }
            }
        }).start();
    }

    @Override
    public void normalTurn(int turnNum, CardDescription currentCard) {
        newTurn(turnNum);
    }

    @Override
    public void dealtRushHourCard(Player[] chooseFrom, int authCode) {
        RoundManager.INSTANCE.sendRushHourCardTo(chooseFrom[rng.nextInt(chooseFrom.length)].getName(), authCode);
    }

    @Override
    public void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard) {
        quickGuess(turnNum);
    }

    @Override
    public void endedGame() {
    }

    @Override
    public void waitTurn(CardDescription currentCard) {
    }
}
