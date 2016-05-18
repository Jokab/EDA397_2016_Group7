package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import game.BroadCastTypes;
import game.GameSessionHolder;
import game.PlayerSession;

public class ChooseRoleActivity extends AppCompatActivity {

    private String logTag = "ChooseRoleActivity LOG";
    private ProgressDialog registerProgress;
    private Runnable registerProgressRunnable;
    private Handler myHandler;
    private BroadcastReceiver receiver;

    private AlertDialog.Builder alertBuilder;
    private DialogInterface.OnClickListener dialogClickListener;

    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myHandler = new Handler();

        Button joinSession = (Button) findViewById(R.id.JoinSessionButton);
        joinSession.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int memberId = GameSessionHolder.getInstance().getSession().getMemberId();
                if (memberId != 0) {
                    startActivity(new Intent(ChooseRoleActivity.this, DisplaycardActivity.class));
                } else {
                    GameSessionHolder.getInstance().setSession(new PlayerSession());
                    registerProgress.show();
                    myHandler.postDelayed(registerProgressRunnable, 3000);
                    ((PlayerSession) GameSessionHolder.getInstance().getSession()).registerToSession();
                }
            }
        });

        Button newSession = (Button) findViewById(R.id.NewSessionButton);
        newSession.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameSessionHolder.getInstance().getSession().checkForSession();
            }
        });

        registerProgress = new ProgressDialog(this);
        registerProgress.setTitle("Registering");
        registerProgress.setMessage("Please wait while trying to register...");
        registerProgressRunnable = new Runnable() {
            @Override
            public void run() {
                registerProgress.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Could not register", Toast.LENGTH_SHORT).show();
            }
        };

        receiver = new MyBroadcastReceiver();
        IntentFilter f1 = new IntentFilter(BroadCastTypes.REGISTER_SUCCESSFUL);
        registerReceiver(receiver, f1);
        IntentFilter f2 = new IntentFilter(BroadCastTypes.NO_SESSION);
        registerReceiver(receiver, f2);
        IntentFilter f3 = new IntentFilter(BroadCastTypes.ONGOING_SESSION);
        registerReceiver(receiver, f3);

        dialogClickListener = new DialogOnClickListener();
        alertBuilder = new AlertDialog.Builder(this);

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(
                        "authorizeprefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("authtoken", "empty").apply();
                Intent i = new Intent(ChooseRoleActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            (GameSessionHolder.getInstance().getSession()).resetGame();
        }
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(ChooseRoleActivity.this, Tutorial.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private class DialogOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    (GameSessionHolder.getInstance().getSession()).resetGame();
                    startActivity(new Intent(ChooseRoleActivity.this, SelectProjectActivity.class));
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadCastTypes.REGISTER_SUCCESSFUL)) {
                registerProgress.dismiss();
                myHandler.removeCallbacks(registerProgressRunnable);
                if (action.equals(BroadCastTypes.REGISTER_SUCCESSFUL)) {
                    startActivity(new Intent(ChooseRoleActivity.this, DisplaycardActivity.class));
                }
            }

            if (action.equals((BroadCastTypes.NO_SESSION)) || action.equals((BroadCastTypes.ONGOING_SESSION ))) {
                alertBuilder.setMessage("Do you want to create a new session?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }
    }
}
