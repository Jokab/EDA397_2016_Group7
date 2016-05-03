package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import game.BroadCastTypes;
import game.GameSessionHolder;
import game.HostSession;

public class DisplayProjectCardsActivity extends AppCompatActivity {

    public static final String[] TEST_CARDS_LIST_DATA = new String[]{"Card A", "Card B", "Card C"};
    private List<String> cards = new ArrayList<>();
    private ArrayAdapter<String> myAdapter;
    private final Handler myHandler = new Handler();
    private int i=0;
    final String[] selectedCard = {""};
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_cards_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView myList = (ListView) findViewById(R.id.listViewOfProjectsCards);

        myList.setOnItemClickListener(new MyOnItemClickListener(myList));
        myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                cards);
        myAdapter.setNotifyOnChange(true);
        myList.setAdapter(myAdapter);

        receiver = new MyBroadcastReceiver();
        IntentFilter f1 = new IntentFilter(BroadCastTypes.CURRENT_BOARD_UPDATED);
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

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(BroadCastTypes.CURRENT_BOARD_UPDATED)){
                ((ProgressBar) findViewById(R.id.projectcards_progressbar)).setVisibility(ProgressBar.INVISIBLE);
                cards = GameSessionHolder.getInstance().getSession().getGameBoard().getCardId();
                myAdapter.clear();
                myAdapter.addAll(cards);
            }
        }
    }
}

