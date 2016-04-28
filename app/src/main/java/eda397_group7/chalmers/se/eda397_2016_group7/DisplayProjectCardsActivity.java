package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Game.GameSessionHolder;
import Game.HostSession;

public class DisplayProjectCardsActivity extends AppCompatActivity {

    public static final String[] TEST_CARDS_LIST_DATA = new String[]{"Card A", "Card B", "Card C"};
    private List<String> cards = new ArrayList<>();
    private ArrayAdapter<String> myAdapter;
    private Handler myHandler = new Handler();
    private int i=0;
    final String[] selectedCard = {""};
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_cards_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myList = (ListView) findViewById(R.id.listViewOfProjectsCards);

        myList.setOnItemClickListener(new MyOnItemClickListener(myList));
        myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                cards);
        myAdapter.setNotifyOnChange(true);
        myList.setAdapter(myAdapter);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {UpdateGUI();}
        }, 0, 1000);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            try {
                cards = GameSessionHolder.getInstance().getSession().getGameBoard().getCardId();
                myAdapter.clear();
                myAdapter.addAll(cards);
            } catch(NullPointerException e) {
            }
        }
    };

    private void UpdateGUI() {
        i++;
        myHandler.post(myRunnable);
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        private final ListView myList;

        public MyOnItemClickListener(ListView myList) {
            this.myList = myList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedCard[0] = (String) myList.getItemAtPosition(position);
            ((HostSession) GameSessionHolder.getInstance().getSession()).setCurrentCard(selectedCard[0]);
            startActivity(new Intent(DisplayProjectCardsActivity.this, DisplayResultsActivity.class));
        }
    }
}

