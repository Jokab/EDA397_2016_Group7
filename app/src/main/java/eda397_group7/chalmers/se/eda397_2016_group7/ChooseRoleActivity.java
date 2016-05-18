package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button devButton = (Button) findViewById(R.id.DeveloperButton);

        registerProgress = new ProgressDialog(this);
        registerProgress.setTitle("Registering");
        registerProgress.setMessage("Please wait while trying to register...");
        myHandler = new Handler();
        Intent i = getIntent();

        devButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameSessionHolder.getInstance().setSession(new PlayerSession());
                registerProgress.show();
                myHandler.postDelayed(registerProgressRunnable, 3000);
                ((PlayerSession) GameSessionHolder.getInstance().getSession()).registerToSession();
            }
        });
        registerProgressRunnable = new Runnable() {
            @Override
            public void run() {
                registerProgress.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Could not register", Toast.LENGTH_SHORT).show();

            }
        };

        Button modButton = (Button) findViewById(R.id.ModeratorButton);
        modButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ChooseRoleActivity.this, SelectProjectActivity.class));
            }
        });


        receiver = new MyBroadcastReceiver();
        IntentFilter f1 = new IntentFilter(BroadCastTypes.REGISTER_SUCCESSFUL);
        registerReceiver(receiver, f1);

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
        if (item.getItemId()== R.id.action_settings){
            startActivity(new Intent(ChooseRoleActivity.this, tutorial.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        super.onBackPressed();
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
        }
    }
}
