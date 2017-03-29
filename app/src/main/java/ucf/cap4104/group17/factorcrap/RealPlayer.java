package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/29/2017.
 */

public class RealPlayer extends Player {

    public interface Listener {
        void normalTurn(int turnNum, CardDescription currentCard);
        void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard);
        void endedGame();
        void waitTurn(CardDescription currentCard);
        void dealtRushHourCard(Player[] chooseFrom, int authCode);
    }

    private final Listener turnListener;

    public RealPlayer(String name, Listener listener) {
        super(name);
        turnListener = listener;
    }

    @Override
    public void normalTurn(int turnNum, CardDescription currentCard) {
        turnListener.normalTurn(turnNum, currentCard);
    }

    @Override
    public void dealtRushHourCard(Player[] chooseFrom, int authCode) {
        turnListener.dealtRushHourCard(chooseFrom, authCode);
    }

    @Override
    public void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard) {
        turnListener.rushHourTurn(turnNum, rushHourCardNum, currentCard);
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
