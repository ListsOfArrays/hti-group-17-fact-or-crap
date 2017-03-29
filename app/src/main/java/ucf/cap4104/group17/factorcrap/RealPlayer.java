package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/29/2017.
 */

public class RealPlayer extends Player {

    public interface Listener {
        void normalTurn(int turnNum, CardDescription currentCard);
        void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard);
        void endedGame();
        void someoneGotRushHour();
        void waitTurn(CardDescription currentCard);
        void dealtRushHourCard(Player[] chooseFrom, int authCode);
        void runOnUiThread(Runnable action);
    }

    private final Listener turnListener;

    public RealPlayer(String name, Listener listener) {
        super(name);
        turnListener = listener;
    }

    @Override
    public void rushHourAlert() {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.someoneGotRushHour();
            }
        });
    }

    @Override
    public void normalTurn(final int turnNum, final CardDescription currentCard) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.normalTurn(turnNum, currentCard);
            }
        });
    }

    @Override
    public void dealtRushHourCard(final Player[] chooseFrom, final int authCode) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.dealtRushHourCard(chooseFrom, authCode);
            }
        });
    }

    @Override
    public void rushHourTurn(final int turnNum, final int rushHourCardNum, final CardDescription currentCard) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.rushHourTurn(turnNum, rushHourCardNum, currentCard);
            }
        });
    }

    @Override
    public void endedGame() {
        turnListener.endedGame();
    }

    @Override
    public void waitTurn(CardDescription currentCard) {
        turnListener.waitTurn(currentCard);
    }
}
