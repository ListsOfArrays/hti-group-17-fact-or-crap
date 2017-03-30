package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/29/2017.
 * This class swallows the Runnable syntax, as well as allowing us to use Player almost like a sublcass.
 */

class RealPlayer extends Player {

    interface Listener {
        void normalTurn(int turnNum, CardDescription currentCard, int tokensLeft);
        void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard, int tokensLeft);
        void endedGame();
        void someoneGotRushHour();
        void waitTurn(CardDescription currentCard, int tokensLeft);
        void dealtRushHourCard(Player[] chooseFrom, int authCode);
        void runOnUiThread(Runnable action);
    }

    private final Listener turnListener;

    RealPlayer(String name, Listener listener) {
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
    public void normalTurn(final int turnNum, final CardDescription currentCard, final int tokensLeft) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.normalTurn(turnNum, currentCard, tokensLeft);
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
    public void rushHourTurn(final int turnNum, final int rushHourCardNum, final CardDescription currentCard, final int tokensLeft) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.rushHourTurn(turnNum, rushHourCardNum, currentCard, tokensLeft);
            }
        });
    }

    @Override
    public void endedGame() {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.endedGame();
            }
        });
    }

    @Override
    public void waitTurn(final CardDescription currentCard, final int tokensLeft) {
        turnListener.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                turnListener.waitTurn(currentCard, tokensLeft);
            }
        });
    }
}
