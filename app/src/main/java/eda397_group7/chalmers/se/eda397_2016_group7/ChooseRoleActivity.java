package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import game.BroadCastTypes;
import game.GameSessionHolder;
import game.PlayerSession;
import trelloInteraction.Board;

public class ChooseRoleActivity extends AppCompatActivity {

    private Board primaryBoard;
    private String logTag = "ChooseRoleActivity LOG";
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button devButton = (Button) findViewById(R.id.DeveloperButton);


        Intent i = getIntent();

        devButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameSessionHolder.getInstance().setSession(new PlayerSession());
                ((PlayerSession) GameSessionHolder.getInstance().getSession()).registerToSession();

            }
        });

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
    protected void onStop() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            //    if(GameSessionHolder.getInstance().getSession().getClass() == HostSession.class) {
            (GameSessionHolder.getInstance().getSession()).resetGame();
            //  }
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadCastTypes.REGISTER_SUCCESSFUL)) {
                startActivity(new Intent(ChooseRoleActivity.this, DisplaycardActivity.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        unregisterReceiver(receiver);
        super.onBackPressed();
    }
}
