package eda397_group7.chalmers.se.eda397_2016_group7;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import game.BroadCastTypes;
import game.GameSession;
import game.GameSessionHolder;
import game.HostSession;
import trelloInteraction.Board;
import trelloInteraction.Card;
import trelloInteraction.TrelloManagerS;

public class DisplayProjectCardsActivity extends AppCompatActivity {

    private List<String> cards = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private ArrayAdapter<String> myAdapter;
    private int i = 0;
    final String[] selectedCard = {""};
    private BroadcastReceiver receiver;
    private List<Integer> selectedCardPositions = new ArrayList<>();
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

        receiver = new MyBroadcastReceiver();
        IntentFilter f1 = new IntentFilter(BroadCastTypes.CURRENT_BOARD_UPDATED);
        registerReceiver(receiver, f1);

        Button endGameButton = (Button) findViewById(R.id.end_game_button);
        assert endGameButton != null;
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSession session = GameSessionHolder.getInstance().getSession();
                Board gameBoard = session.getGameBoard();
                List<String> cardIds = gameBoard.getCardId();
                RequestQueue queue = Volley.newRequestQueue(DisplayProjectCardsActivity.this);
                for (final String cardId : cardIds) {
                    final Card card = gameBoard.getCard(cardId);
                    if (card != null) {
                        int cardRating = card.getRating();
                        if (cardRating != 0) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("name", "("+card.getRating()+") "+card.getName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest updateCard = new JsonObjectRequest(
                                    Request.Method.PUT,
                                    TrelloManagerS.INSTANCE.updateCard(cardId),
                                    jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.i("Update Success", "Successfully updated Card: "+cardId);
                                        }
                                    }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("Update Error", "Failed to update Card: "+cardId);
                                        Log.i("Update Error", error.getMessage());
                                    }
                            }
                            );
                            queue.add(updateCard);
                        }
                    }
                }
                queue.start();
                (GameSessionHolder.getInstance().getSession()).resetGame();
                Toast.makeText(DisplayProjectCardsActivity.this, "Your trello board has been updated. Thank you for playing", Toast.LENGTH_LONG).show();
                finish();
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
            selectedCard[0] = (String) cards.get(position);
            ((HostSession) GameSessionHolder.getInstance().getSession()).setCurrentCard(selectedCard[0]);
            startActivity(new Intent(DisplayProjectCardsActivity.this, DisplayResultsActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameSession session = GameSessionHolder.getInstance().getSession();
        for (String cardId : cards) {
            Card card = session.getGameBoard().getCard(cardId);
            if (card.getRating() != 0) {
                TextView item = (TextView) myList.getChildAt(cards.indexOf(cardId));
                if (!item.getText().toString().contains("[" + card.getRating() + "]")) {
                    item.setText("[" + card.getRating() + "] " + item.getText());
                }
                item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BroadCastTypes.CURRENT_BOARD_UPDATED)) {
                ((ProgressBar) findViewById(R.id.projectcards_progressbar)).setVisibility(ProgressBar.INVISIBLE);
                GameSession session = GameSessionHolder.getInstance().getSession();
                cards = session.getGameBoard().getCardId();
                names = session.getGameBoard().getCardNames();
                myAdapter.clear();
                myAdapter.addAll(names);

                for (String cardId : cards) {
                    Card card = session.getGameBoard().getCard(cardId);
                    if (card.getRating() != 0) {
                        TextView item = (TextView) myList.getChildAt(cards.indexOf(cardId));
                        item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }
        }
    }
}

