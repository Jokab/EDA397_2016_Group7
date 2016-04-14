package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import game.GameSessionHolder;
import game.HostSession;
import game.PlayerSession;
import trelloInteraction.Board;

public class ChooseRoleActivity extends AppCompatActivity {

    private Board primaryBoard;
    private String logTag = "ChooseRoleActivity LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button devButton = (Button) findViewById(R.id.DeveloperButton);

        devButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameSessionHolder.getInstance().setSession(new PlayerSession());
                ((PlayerSession) GameSessionHolder.getInstance().getSession()).registerToSession();
                // TODO: DON'T SWITCH TO DisplaycardActivity unless actually registered
                startActivity(new Intent(ChooseRoleActivity.this, DisplaycardActivity.class));
            }
        });

        Button modButton = (Button) findViewById(R.id.ModeratorButton);
        modButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ChooseRoleActivity.this, SelectProjectActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            if(GameSessionHolder.getInstance().getSession().getClass() == HostSession.class) {
                ((HostSession) GameSessionHolder.getInstance().getSession()).resetGame();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
