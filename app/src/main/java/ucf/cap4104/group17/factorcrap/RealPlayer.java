package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/29/2017.
 */

public class RealPlayer extends Player {

    public interface Listener {
        void normalTurn(int turnNum);
        void rushHourTurn(int turnNum, int rushHourCardNum);
        void endedGame();
        void waitTurn();
    }

    private final Listener turnListener;

    public RealPlayer(String name, Listener listener) {
        super(name);
        turnListener = listener;
    }

    @Override
    public void normalTurn(int turnNum) {
        turnListener.normalTurn(turnNum);
    }

    @Override
    public void rushHourTurn(int turnNum, int rushHourCardNum) {
        turnListener.rushHourTurn(turnNum, rushHourCardNum);
    }

    @Override
    public void endedGame() {
        turnListener.endedGame();
    }

    @Override
    public void waitTurn() {
        turnListener.waitTurn();
    }
}
