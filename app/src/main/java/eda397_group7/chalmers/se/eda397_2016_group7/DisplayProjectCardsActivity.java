package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Game.GameSessionHolder;
import eda397_group7.chalmers.se.eda397_2016_group7.R;

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really exit?")
                .setMessage("Are you sure you want to exit the session?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DisplayProjectCardsActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create().show();
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            try {
                cards = GameSessionHolder.getInstance().getSession().getGameBoard().getCardNames();

                myAdapter.clear();
                myAdapter.addAll(cards);
                if(!cards.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ProgressBar) findViewById(R.id.projectcards_progressbar)).setVisibility(ProgressBar.INVISIBLE);
                        }
                    });
                }
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
            startActivity(new Intent(DisplayProjectCardsActivity.this, DisplayResultsActivity.class));
        }
    }
}

