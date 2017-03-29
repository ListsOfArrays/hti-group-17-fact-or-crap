package ucf.cap4104.group17.factorcrap;


/**
 * Created by Jacob on 3/23/2017.
 */

public class NetworkConnectionStub {

    public void sendGuess(boolean guessedTrue, NetworkCallback networkCallback, int turnNum) {
        RoundManager.INSTANCE.makeGuess(guessedTrue, networkCallback, turnNum);
    }

    public interface NetworkCallback {
        void onNetworkCallback(int points);
    }
}
