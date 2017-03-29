package ucf.cap4104.group17.factorcrap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class Endgame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);
        LinearLayout list = (LinearLayout) findViewById(R.id.linearEndgameLayout);
        list.removeAllViewsInLayout();

        Player[] players = RoundManager.INSTANCE.getResults();
        for (Player player : players) {
            TextView newTextView = new TextView(this);

            String nameString = player.getName() + ": " + player.getPoints() + " Tokens";
            newTextView.setText(nameString);
            if (player.won()) {
                newTextView.setTextSize(COMPLEX_UNIT_DIP, 35);
            } else {
                newTextView.setTextSize(COMPLEX_UNIT_DIP, 17);
            }

            list.addView(newTextView);
        }
    }
}
