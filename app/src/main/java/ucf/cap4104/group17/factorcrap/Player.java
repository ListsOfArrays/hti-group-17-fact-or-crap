package ucf.cap4104.group17.factorcrap;

/**
 * Created by Jacob on 3/23/2017.
 */

public class Player implements NetworkConnectionStub.NetworkCallback {
    private final String name;
    private final NetworkConnectionStub connection;
    private boolean won;
    private int points;

    public Player(String name) {
        this.name = name;
        this.connection = new NetworkConnectionStub();
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

    public boolean won() {
        return won;
    }

    public void guess(boolean guessedTrue, int turnNum) {
        connection.sendGuess(guessedTrue, this, turnNum);
    }

    @Override
    public void onNetworkCallback(int points) {
        incPoints(points);
    }
}
