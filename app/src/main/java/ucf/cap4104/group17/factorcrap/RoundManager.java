package ucf.cap4104.group17.factorcrap;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jacob on 3/23/2017.
 * Could use a LOT of abstraction.
 */

public enum RoundManager {
    INSTANCE();

    private FullCard currentFullCard;
    private boolean firstCorrect;
    private FullCard roundFullCards[];
    private int roundNum;
    private int tokensLeft;
    private int currentPlayersCount;
    private int allPlayersCount;
    private Player[] playerList;
    private Random rng;
    private final int RUSH_HOUR_TIME = 60_000; // 60 seconds
    private int turnNum = 0;
    private ArrayList<Player> suddenDeathPlayers;
    int rushHourRounds;
    private ArrayList<Player> rushHourGoodPlayers;
    private Player rushHourEvilSide;
    private boolean endedGame;

    public void startGame(RealPlayer realPlayer) {
        ArrayList<FullCard> list = FullCard.getCards();
        Collections.shuffle(list);
        roundFullCards = list.toArray(new FullCard[list.size()]);

        endedGame = false;
        roundNum = 0;
        this.tokensLeft = list.size();

//        rng = new Random();
//        long seed = rng.nextLong();
//        Log.d("seed", "Seed value: " + seed);

        // this is a good seed. Got rush hour, 7 person game, rush hour wait turn, and delivered rush hour.
//        long seed = -3061875589648820969L;
        long seed = 0; // 0: got rush hour, rush hour delivered, paused turn 7 person game. lol, first try!
        rng = new Random(seed);
        String[] nameList = new String[]{"Robert", "Wendy (AI)", "Dave", "Mary", "John", "Valerie (AI)", "armsDealer"};
        playerList = new Player[2 + rng.nextInt(7)];
        for (int i = 1; i < playerList.length; i++) {
            playerList[i] = new AI(nameList[i - 1], rng.nextLong());
        }
        playerList[0] = realPlayer;
        suddenDeathPlayers = null;
        rushHourGoodPlayers = null;
        allPlayersCount = playerList.length;
        nextRound();
    }

    private void endRushHour() {
        rushHourEvilSide = null;
        rushHourGoodPlayers = null;
        rushHourRounds = 0;
    }

    private void nextRound() {
        turnNum += 1;
        firstCorrect = false;
        if (roundNum < roundFullCards.length && tokensLeft > 0) {
            // during rush hour give new cards only to those subscribed
            if (rushHourGoodPlayers != null) {
                firstCorrect = false;
                if (rushHourRounds > 0) {
                    currentPlayersCount = rushHourGoodPlayers.size();
                    currentFullCard = roundFullCards[roundNum++];
                    rushHourRounds -= 1;

                    ArrayList<Player> notPlaying = new ArrayList<>(playerList.length);
                    Collections.addAll(notPlaying, playerList);
                    for (Player player : rushHourGoodPlayers) {
                        player.rushHourTurn(turnNum, 5 - rushHourRounds, currentFullCard, tokensLeft);
                        // O(n^2)
                        notPlaying.remove(player);
                    }
                    for (Player player : notPlaying) {
                        player.waitTurn(currentFullCard, tokensLeft);
                    }
                }
                // we ran out of rush hour cards.
                else {
                    endRushHour();
                    // during sudden death, check to see if someone has won yet.
                    if (suddenDeathPlayers != null) {
                        tryToEndGame();
                    } else {
                        nextRound();
                    }
                }
            }
            // during sudden death, always use rush hour
            else if (suddenDeathPlayers != null) {
                startRushHour(suddenDeathPlayers);
            }
            // not rush hour or sudden death: and normal card round
            else if (rng.nextInt(10) > 2) {
                currentFullCard = roundFullCards[roundNum++];
                firstCorrect = true;
                currentPlayersCount = allPlayersCount;
                for (Player player : playerList) {
                    player.normalTurn(turnNum, currentFullCard, tokensLeft);
                }
            }
            // randomly deal a rush hour card
            else {
                Player player = playerList[rng.nextInt(playerList.length)];
                Player[] notSelfList = new Player[playerList.length - 1];
                int i = 0;
                for (Player notSelf : playerList) {
                    notSelf.rushHourAlert();
                    if (player.equals(notSelf)) continue;
                    notSelfList[i++] = notSelf;
                }
                rushHourEvilSide = player;
                rushHourRounds = 5;
                player.dealtRushHourCard(notSelfList, 0);
            }
        }
        // no tokens left
        else {
            tryToEndGame();
        }
    }

    int rushHourCardCode = 0;

    public void sendRushHourCardTo(String toSendTo, int authCode) {
        if (authCode == rushHourCardCode) {
            turnNum += 1;
            currentPlayersCount = 1;
            ArrayList<Player> rushHourList = new ArrayList<>(1);
            for (Player player : playerList) {
                if (player.getName().equals(toSendTo)) {
                    rushHourList.add(player);
                    break;
                }
            }
            startRushHour(rushHourList);
        }
    }

    private void startRushHour(final ArrayList<Player> players) {
        final int endRound = turnNum + 5;
        rushHourRounds = 5;
        rushHourGoodPlayers = players;

        // start timer
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(RUSH_HOUR_TIME);
                } catch (InterruptedException ignored) {
                } finally {
                    if (turnNum <= endRound) {
                        turnNum = endRound + 1;
                        rushHourRounds = 0;
                        rushHourGoodPlayers = null;
                        nextRound();
                    }
                }
            }
        }).start();

        nextRound();
    }

    private void tryToEndGame() {
        turnNum += 1;
        int mostPoints = playerList[0].getPoints();

        // note: we don't add the first player until the for loop below
        ArrayList<Player> bestPlayers = new ArrayList<>();
        Player[] currentPlayers;
        if (suddenDeathPlayers != null) {
            currentPlayers = suddenDeathPlayers.toArray(new Player[suddenDeathPlayers.size()]);
        } else {
            currentPlayers = playerList;
        }

        for (Player player : currentPlayers) {
            // tie
            if (player.getPoints() == mostPoints) {
                bestPlayers.add(player);
                // new winner
            } else if (player.getPoints() > mostPoints) {
                bestPlayers.clear();
                mostPoints = player.getPoints();
                bestPlayers.add(player);
            }
            // loser!
        }

        // only one player won!
        if (bestPlayers.size() == 1) {
            bestPlayers.get(0).setWon(true);
            someoneWon();
        }
        // tie, sudden death!
        else if (bestPlayers.size() > 1) {
            ArrayList<FullCard> list = FullCard.getCards();
            Collections.shuffle(list);
            roundFullCards = list.toArray(new FullCard[list.size()]);
            roundNum = 0;
            this.tokensLeft = list.size();
            activateSuddenDeath(bestPlayers);
        }
        // the universe has ended!
        else if (bestPlayers.size() == 0) {
            throw new AssertionError("This should not have been 0, ever!");
        }
    }

    private void activateSuddenDeath(ArrayList<Player> bestPlayers) {
        suddenDeathPlayers = bestPlayers;
        nextRound();
    }

    private void someoneWon() {
        if (!endedGame) {
            // prevents activity from being called again because listener fires mutiple extra times
            endedGame = true;
            for (Player player : playerList)
                player.endedGame();
        }
    }

    private final Object threadLock = new Object();

    public void makeGuess(boolean guessedTrue, NetworkConnectionStub.NetworkCallback networkCallback, int turnNum) {
        synchronized (threadLock) {
            currentPlayersCount -= 1;
            if (turnNum == this.turnNum) {
                if (guessedTrue == currentFullCard.getTruthValue()) {
                    if (rushHourEvilSide != null) {
                        rushHourEvilSide.onNetworkCallback(0);
                    }
                    // first & correct!
                    if (firstCorrect && tokensLeft > 1) {
                        tokensLeft -= 2;
                        networkCallback.onNetworkCallback(2);
                        // either not first or not enough tokens to satisfy, but still correct
                    } else if (tokensLeft > 0) {
                        tokensLeft -= 1;
                        networkCallback.onNetworkCallback(1);
                        // still correct, not enough tokens
                    } else {
                        networkCallback.onNetworkCallback(0);
                    }
                    firstCorrect = false;
                    // not correct
                } else {
                    if (rushHourEvilSide != null) {
                        if (tokensLeft > 0) {
                            rushHourEvilSide.onNetworkCallback(1);
                            tokensLeft -= 1;
                        } else {
                            rushHourEvilSide.onNetworkCallback(0);
                        }
                    }
                    networkCallback.onNetworkCallback(0);
                }
                // not even the same turn
            } else {
                networkCallback.onNetworkCallback(0);
            }

            // game over!
            if (tokensLeft <= 0) {
                firstCorrect = false;
                tryToEndGame();
            } else if (currentPlayersCount == 0) {
                firstCorrect = false;
                nextRound();
            }
        }
    }

    public Player[] getResults() {
        return playerList;
    }
}
