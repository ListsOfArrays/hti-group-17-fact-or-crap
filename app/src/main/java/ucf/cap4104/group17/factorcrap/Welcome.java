package ucf.cap4104.group17.factorcrap;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

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
            String type = "new";
            Bundle args = getArguments();
            if (args != null) {
                type = args.getString("dialog_type", "new");
            } else if (savedInstanceState != null) {
                type = savedInstanceState.getString("dialog_type", "new");
            }

            finalType = type;

            builder.setView(R.layout.dialog_layout)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        if (finalType.equals("new")) {
                            getActivity().startActivity(new Intent(getContext(), Choice.class));
                        } else {
                            getActivity().startActivity(new Intent(getContext(), Totalcount.class));
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
