package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import Game.GameSessionHolder;
import eda397_group7.chalmers.se.eda397_2016_group7.R;

public class DisplayProjectCardsActivity extends AppCompatActivity {

    public static final String[] TEST_CARDS_LIST_DATA = new String[]{"Card A", "Card B", "Card C"};
    private String[] cards = {""};
    private ArrayAdapter<String> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_cards_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String[] selectedCard = {""};

        final ListView myList =
                (ListView) findViewById(R.id.listViewOfProjectsCards);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCard[0] = (String) myList.getItemAtPosition(position);
                startActivity(new Intent(DisplayProjectCardsActivity.this, DisplayResultsActivity.class));
            }
        });
        myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                cards);
        myList.setAdapter(myAdapter);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                //cards = GameSessionHolder.getInstance().getSession().getGameBoard().getCardNames();
                //myAdapter.clear();
                //myAdapter.addAll(cards);
            }
        }, 0, 1000);
    }
}
