package ucf.cap4104.group17.factorcrap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

public class Choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button longGame = (Button) findViewById(R.id.button5);
        Button shortGame = (Button) findViewById(R.id.button6);

        longGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TotalCount.class));
                finish();
            }
        });
        shortGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TotalCount.class));
                finish();
            }
        });

        TextView session = (TextView) findViewById(R.id.textView);
        SecureRandom random = new SecureRandom();
        byte[] arr = new byte[10];
        random.nextBytes(arr);
        String randomStr = Base64.encodeToString(arr, Base64.URL_SAFE);
        randomStr = "Session ID: " + randomStr.replaceAll("=", "");

        session.setText(randomStr);
    }

}
