package ucf.cap4104.group17.factorcrap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

public class Welcome extends AppCompatActivity {
    /**
     * http://stackoverflow.com/a/4239019
     * Author: Alexandre Jasmin
     * licensed under cc by-sa 3.0 with attribution required
     *
     * @return true, if internet available
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setAccordingToInternet(final boolean hasInternet) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button newSession = (Button) findViewById(R.id.button4);
                Button submitSession = (Button) findViewById(R.id.button3);
                TextView view = (TextView) findViewById(R.id.editText);

                if (hasInternet) {
                    newSession.setText("Start New\nOnline Session");
                    submitSession.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                } else {
                    newSession.setText("Start New AI-Based\nOffline Session");
                    submitSession.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * http://stackoverflow.com/a/27312494
     * Author: Levit
     * licensed under cc by-sa 3.0 with attribution required
     * Checks if the user actually has internet, and not just WiFi
     */
    private void checkIfHasInternet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                    sock.connect(socketAddress, timeoutMs);
                    sock.close();
                    setAccordingToInternet(true);
                } catch (IOException ignored) {
                    setAccordingToInternet(false);
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button submitSession = (Button) findViewById(R.id.button3);
        submitSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameFragment.showDialog("submit", getSupportFragmentManager());
            }
        });

        Button newSession = (Button) findViewById(R.id.button4);
        newSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameFragment.showDialog("new", getSupportFragmentManager());
            }
        });

        TextView view = (TextView) findViewById(R.id.editText);
        // set it to "internet"
        if (isNetworkAvailable()) {
            newSession.setText("Start New\nOnline Session");
            submitSession.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            checkIfHasInternet();
        } else {
            newSession.setText("Start New AI-Based\nOffline Session");
            submitSession.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }

    public static class NameFragment extends DialogFragment {
        private String finalType;

        public static void showDialog(String type, FragmentManager manager) {
            NameFragment dialog = new NameFragment();
            Bundle typeOfDialog = new Bundle();
            typeOfDialog.putString("dialog_type", type);
            dialog.setArguments(typeOfDialog);
            dialog.show(manager, "dialog_fragment");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final LayoutInflater inflater = getActivity().getLayoutInflater();

            String type = "new";
            Bundle args = getArguments();
            if (args != null) {
                type = args.getString("dialog_type", "new");
            } else if (savedInstanceState != null) {
                type = savedInstanceState.getString("dialog_type", "new");
            }

            finalType = type;

            // null because it's a dialog
            final View layout = inflater.inflate(R.layout.dialog_layout, null);

            builder.setView(layout)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                            EditText input = (EditText) layout.findViewById(R.id.editText4);
                            pref.edit().putString("NAME", input.getText().toString()).apply();
                            if (finalType.equals("new")) {
                                getActivity().startActivity(new Intent(getContext(), Choice.class));
                            } else {
                                getActivity().startActivity(new Intent(getContext(), TotalCount.class));
                            }
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
