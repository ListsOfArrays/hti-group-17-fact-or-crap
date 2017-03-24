package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/23/2017.
 */

public class Player implements NetworkConnectionStub.NetworkCallback {
    private final String name;
    private final NetworkConnectionStub connection;
    private boolean won;
    private int points;

    public Player(String name, NetworkConnectionStub connection) {
        this.name = name;
        this.connection = connection;
        points = 0;
        won = false;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    private void incPoints(int pointVal) {
        points += pointVal;
    }

    public void setWon(boolean didWin) {
        won = didWin;
    }

    public boolean didWin() {
        return won;
    }

    public void guess(boolean guessedTrue) {
        connection.sendGuess(guessedTrue, this);
    }

    @Override
    public void onNetworkCallback(int points) {
        incPoints(points);
    }
}
