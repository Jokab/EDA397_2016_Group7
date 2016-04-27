package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import TrelloInteraction.Board;

public class ChooseRoleActivity extends AppCompatActivity {

    private Board primaryBoard;
    private String logTag = "ChooseRoleActivity LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button devButton = (Button) findViewById(R.id.ModeratorButton);

        //Attempt to get objects from intent
        Intent i = getIntent();
        primaryBoard = i.getParcelableExtra("board");
        Log.i(logTag, primaryBoard.getCard("cardName").getName());

        devButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ChooseRoleActivity.this, SelectProjectActivity.class));
            }
        });

        Button modButton = (Button) findViewById(R.id.DeveloperButton);
        modButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ChooseRoleActivity.this, DisplaycardActivity.class));
            }
        });
    }

}
