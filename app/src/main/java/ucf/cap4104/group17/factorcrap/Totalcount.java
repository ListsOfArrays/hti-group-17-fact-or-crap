package ucf.cap4104.group17.factorcrap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

public class Totalcount extends AppCompatActivity implements RoundManager.TurnListener {
    private static String PLAYER = "PLAYER";
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalcount);
        final ProgressBar waiting = (ProgressBar) findViewById(R.id.progressBar);

        if (savedInstanceState != null) {
            Gson gson = new Gson();
            player = gson.fromJson(savedInstanceState.getString(PLAYER), Player.class);
        }
        // first time
        else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = preferences.getString("NAME", "Player");
            player = new Player(name);
            waiting.setVisibility(View.INVISIBLE);
            TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
            rushHourText.setVisibility(View.INVISIBLE);
            RoundManager.INSTANCE.startGame(this, player);
        }

        final Button fact = (Button) findViewById(R.id.button);
        final Button crap = (Button) findViewById(R.id.button2);
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWaiting();
                player.guess(true);
            }
        });
        crap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWaiting();
                player.guess(false);
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

    @Override
    public void newTurn(boolean isRushHour, int cardNum) {
        final TextView cardContents = (TextView) findViewById(R.id.theCardContents);
        cardContents.setText(RoundManager.INSTANCE.getCurrentCard().getDescription());
        String points = "Token Count: " + player.getPoints();

        final TextView pointsText = (TextView) findViewById(R.id.pointsText);
        pointsText.setText(points);

        stopWaiting();

        TextView rushHourText = (TextView) findViewById(R.id.rushHourTextView);
        if (isRushHour) {
            String rushHour = "Rush Hour: Card " + cardNum + " / 5";
            rushHourText.setText(rushHour);
            rushHourText.setVisibility(View.VISIBLE);
        } else {
            rushHourText.setVisibility(View.GONE);
        }
    }

    @Override
    public void waitTurn() {
        startWaiting();
    }

    @Override
    public void endedGame() {
        startActivity(new Intent(this, Endgame.class));
        finish();
    }
}
