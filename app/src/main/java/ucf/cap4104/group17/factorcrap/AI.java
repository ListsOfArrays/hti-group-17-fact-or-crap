package ucf.cap4104.group17.factorcrap;

import java.security.SecureRandom;

/**
 * Created by Jacob on 3/23/2017.
 */

public class AI {
    private final Object lock;
    private final SecureRandom rng;
    private final Player toControl;

    public AI(Player toControl) {
        lock = new Object();
        rng = new SecureRandom();
        this.toControl = toControl;
    }

    public int millisecondsToWait(int mean, int range) {
        double clippedGaussian = rng.nextGaussian() / 2;
        if (clippedGaussian < 0)
            clippedGaussian = -clippedGaussian;
        if (clippedGaussian > 1)
            clippedGaussian = 1;

        final double millisecondsToWait = mean + (range * clippedGaussian);
        return (int) (millisecondsToWait * 1000);
    }

    public void newTurn() {
        final boolean guess = rng.nextBoolean();
        final int millisecondsToWait = millisecondsToWait(10, 7);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millisecondsToWait);
                } catch (InterruptedException e) {
                }
                finally {
                    toControl.guess(guess);
                }
            }
        }).start();
    }

    public void quickGuess() {
        final int millisecondsToWait = millisecondsToWait(7, 3);
        final boolean guess = rng.nextBoolean();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millisecondsToWait);
                } catch (InterruptedException e) {
                }
                finally {
                    toControl.guess(guess);
                }
            }
        }).start();
    }

    public Player onEnd() {
        return toControl;
    }
}
