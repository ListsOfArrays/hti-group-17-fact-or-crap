package ucf.cap4104.group17.factorcrap;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Random;

public class TotalCount extends AppCompatActivity implements RealPlayer.Listener {
    private static String PLAYER = "PLAYER";
    private RealPlayer player;
    private int turnNum;
    private int prevTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalcount);
        final ProgressBar waiting = (ProgressBar) findViewById(R.id.progressBar);

        if (savedInstanceState != null) {
            Gson gson = new Gson();
            player = gson.fromJson(savedInstanceState.getString(PLAYER), RealPlayer.class);
        }
        // first time
        else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = preferences.getString("NAME", "Player");
            player = new RealPlayer(name, this);
            waiting.setVisibility(View.INVISIBLE);
            TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
            rushHourText.setVisibility(View.INVISIBLE);
            prevTokens = 9999999;
            RoundManager.INSTANCE.startGame(player);
        }

        final Button fact = (Button) findViewById(R.id.button);
        final Button crap = (Button) findViewById(R.id.button2);
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWaiting();
                player.guess(true, turnNum);
            }
        });
        crap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWaiting();
                player.guess(false, turnNum);
            }
        });
    }

    private void startWaiting() {
        final Button fact = (Button) findViewById(R.id.button);
        final Button crap = (Button) findViewById(R.id.button2);
        final ProgressBar waiting = (ProgressBar) findViewById(R.id.progressBar);

        waiting.setVisibility(View.VISIBLE);
        fact.setEnabled(false);
        crap.setEnabled(false);
    }

    private void startWaiting(CardDescription currentCard) {
        final TextView cardContents = (TextView) findViewById(R.id.theCardContents);
        cardContents.setText(currentCard.getDescription());
        String points = "Token Count: " + player.getPoints();
        final TextView pointsText = (TextView) findViewById(R.id.pointsText);
        pointsText.setText(points);
        startWaiting();
    }

    private void stopWaiting() {
        final Button fact = (Button) findViewById(R.id.button);
        final Button crap = (Button) findViewById(R.id.button2);
        final ProgressBar waiting = (ProgressBar) findViewById(R.id.progressBar);

        waiting.setVisibility(View.INVISIBLE);
        fact.setEnabled(true);
        crap.setEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        outState.putString(PLAYER, gson.toJson(player));
    }

    private void newTurn(int turnNum, CardDescription cardDescription, int tokensLeft) {
        this.turnNum = turnNum;
        final TextView cardContents = (TextView) findViewById(R.id.theCardContents);
        cardContents.setText(cardDescription.getDescription());
        String points = "Token Count: " + player.getPoints();
        final TextView pointsText = (TextView) findViewById(R.id.pointsText);
        pointsText.setText(points);
        setTokensImage(tokensLeft);
        stopWaiting();
    }

    private void setTokensImage(int tokensLeft) {
        ImageView view = (ImageView) findViewById(R.id.tokenPile);
        if (prevTokens == 1 && tokensLeft == 0) {
            view.setImageResource(R.drawable.ic_0_token);
        } else if (prevTokens > 1 && tokensLeft == 1) {
            view.setImageResource(R.drawable.ic_1_token);
        } else if (prevTokens >= 50 && tokensLeft < 50) {
            view.setImageResource(R.drawable.ic_2_token);
        } else if (prevTokens >= 100 && tokensLeft < 100) {
            view.setImageResource(R.drawable.ic_3_token);
        } else if (prevTokens >= 150 && tokensLeft < 150) {
            view.setImageResource(R.drawable.ic_4_token);
        } else if (prevTokens >= 200 && tokensLeft < 200) {
            view.setImageResource(R.drawable.ic_5_token);
        } else if (prevTokens >= 250 && tokensLeft < 250) {
            view.setImageResource(R.drawable.ic_6_token);
        }

        prevTokens = tokensLeft;
    }

    private void giveNotification(String message) {
        Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_rush_hour)
                .setContentTitle("Fact or Crap")
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{10, 700})
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification);
    }

    @Override
    public void someoneGotRushHour() {
        giveNotification("A Rush Hour card has been dealt!");
    }

    @Override
    public void waitTurn(CardDescription currentCard, int tokensLeft) {
        TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
        rushHourText.setVisibility(View.GONE);
        setTokensImage(tokensLeft);
        startWaiting(currentCard);
    }

    @Override
    public void dealtRushHourCard(Player[] chooseFrom, int authCode) {
        RushHourSelectFragment.showRushHourSelectFragement(getSupportFragmentManager(), chooseFrom, authCode);
    }

    /**
     * Shows selection for rush hour who to send the card to
     */
    public static class RushHourSelectFragment extends DialogFragment {
        private static String PLAYER_NAMES = "PLAYER_NAMES";
        private static String AUTH_CODE = "AUTH_CODE";

        public static void showRushHourSelectFragement(FragmentManager fragmentManager, Player[] toChooseFrom, int authCode) {
            RushHourSelectFragment dialog = new RushHourSelectFragment();
            Bundle args = new Bundle();

            String[] names = new String[toChooseFrom.length];
            for (int i = 0; i < toChooseFrom.length; i++) {
                names[i] = toChooseFrom[i].getName();
            }

            args.putStringArray(PLAYER_NAMES, names);
            args.putInt(AUTH_CODE, authCode);
            dialog.setArguments(args);
            dialog.show(fragmentManager, "RushHourSelectFragment");
        }

        private String[] playerNames;
        private int authCode;
        private boolean dismissIntentional;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            playerNames = getArguments().getStringArray(PLAYER_NAMES);
            authCode = getArguments().getInt(AUTH_CODE);
            dismissIntentional = false;

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Pick a player to send a Rush Hour card to.")
                    .setItems(playerNames, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            RoundManager.INSTANCE.sendRushHourCardTo(playerNames[which], authCode);
                            dismissIntentional = true;
                            dismiss();
                        }
                    });
            return builder.create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if (!dismissIntentional) {
                Random rng = new Random();
                RoundManager.INSTANCE.sendRushHourCardTo(playerNames[rng.nextInt(playerNames.length)], authCode);
            }
            super.onDismiss(dialog);
        }
    }

    @Override
    public void normalTurn(int turnNum, CardDescription currentCard, int tokensLeft) {
        TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
        rushHourText.setVisibility(View.GONE);
        newTurn(turnNum, currentCard, tokensLeft);
    }

    @Override
    public void rushHourTurn(int turnNum, int rushHourCardNum, CardDescription currentCard, int tokensLeft) {
        TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
        String rushHour = "Rush Hour: Card " + rushHourCardNum + " / 5";
        rushHourText.setText(rushHour);
        rushHourText.setVisibility(View.VISIBLE);
        newTurn(turnNum, currentCard, tokensLeft);
    }

    @Override
    public void endedGame() {
        startActivity(new Intent(this, Endgame.class));
        finish();
    }
}
