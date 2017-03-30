package ucf.cap4104.group17.factorcrap;

import java.security.SecureRandom;

/**
 * Created by Jacob on 3/23/2017.
 * An AI that plays instead of a human. It guesses w/50% chance of right/wrong
 */

class AI extends Player {
    private final SecureRandom rng;
    private int realTurnNum;

    AI(String name) {
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
                } catch (InterruptedException ignored) {
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
        final boolean guess = rng.nextBoolean();
        final int millisecondsToWait = secondsToWaitInMilliseconds(3, 2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millisecondsToWait);
                } catch (InterruptedException ignored) {
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
    public void normalTurn(int turnNum, CardDescription currentCard, int tokensLeft) {
        newTurn(turnNum);
    }

    @Override
    public void dealtRushHourCard(Player[] chooseFrom, int authCode) {
        RoundManager.INSTANCE.sendRushHourCardTo(chooseFrom[rng.nextInt(chooseFrom.length)].getName(), authCode);
    }

    @Override
    public void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard, int tokensLeft) {
        quickGuess(turnNum);
    }

    @Override
    public void endedGame() {
    }

    @Override
    public void waitTurn(CardDescription currentCard, int tokensLeft) {
    }
}
