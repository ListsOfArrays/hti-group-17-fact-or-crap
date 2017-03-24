package ucf.cap4104.group17.factorcrap;


/**
 * Created by Jacob on 3/23/2017.
 */

public class NetworkConnectionStub {
    private final RoundManager manager;

    public NetworkConnectionStub(RoundManager manager) {
        this.manager = manager;
    }

    public void sendGuess(boolean guessedTrue, NetworkCallback networkCallback) {
        manager.makeGuess(guessedTrue, networkCallback);
    }

    public interface NetworkCallback {
        void onNetworkCallback(int points);
    }
}
