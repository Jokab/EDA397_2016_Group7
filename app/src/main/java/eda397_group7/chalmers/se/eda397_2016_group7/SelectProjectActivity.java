package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import game.GameSessionHolder;
import game.HostSession;
import trelloInteraction.TrelloManagerS;
import trelloInteraction.VolleyManager;

public class SelectProjectActivity extends AppCompatActivity {

    private static final String DEBUG_PROJECT_ID = "l56NCOG9";
    private static final ArrayList<String> boardIds = new ArrayList<String>();
    private final String[] selectedProject = {""};
    private final int[] players = {0};
    private SharedPreferences sharedPreferences;
    private String logTag = "selectprojects";
    protected final RequestQueue queue = VolleyManager.getInstance(null).getRequestQueue();
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = this.getSharedPreferences(
                "authorizeprefs", Context.MODE_PRIVATE);
        String authtoken = sharedPreferences.getString("authtoken", "empty");
        if (authtoken.equals("empty")) {
            Toast.makeText(this, "Can not pull boards. Please log in.", Toast.LENGTH_LONG).show();
        } else {
            getBoards();
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                new String[]{});
        myList = (ListView) findViewById(R.id.listViewOfProjects);

        createSelectedProjectListener(selectedProject, myList);
        myList.setAdapter(myAdapter);


        final TextView numberOfPlayersField =
                (TextView) findViewById(R.id.NumberOfPlayers);
        createNumberPlayersListener(players, numberOfPlayersField);

        Button devButton = (Button) findViewById(R.id.startGameButton);
        createStartButtonListener(selectedProject, players, devButton);

        Button debugButton = (Button) findViewById(R.id.select_project_debugbutton);
        createDebugButtonListener(selectedProject, debugButton);
    }

    private void createSelectedProjectListener(final String[] selectedProject, final ListView myList) {
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProject[0] = boardIds.get(position);
            }
        });
    }

    private void createNumberPlayersListener(final int[] players, final TextView numberOfPlayersField) {
        numberOfPlayersField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String playersText = numberOfPlayersField.getText().toString();
                if(!playersText.isEmpty()) {
                    players[0] = Integer.parseInt(numberOfPlayersField.getText().toString());
                }
            }
        });
    }

    private void createStartButtonListener(final String[] s, final int[] player, Button devButton) {
        devButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s[0].isEmpty() || player[0] == 0) {
                    Toast.makeText(getApplicationContext(), "Select project and number of players", Toast.LENGTH_SHORT).show();
                } else {
                    GameSessionHolder.getInstance().setSession(new HostSession(selectedProject[0], players[0]));
                    GameSessionHolder.getInstance().getSession().getGameBoard().updateCards();
                    startActivity(new Intent(SelectProjectActivity.this, DisplayProjectCardsActivity.class));
                }
            }
        });
    }

    private void createDebugButtonListener(final String[] selectedProject, Button debugButton) {
        debugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProject[0] = DEBUG_PROJECT_ID;
            }
        });
    }

    public void getBoards() {
        JsonArrayRequest startRequest = new JsonArrayRequest(
                TrelloManagerS.INSTANCE.getBoardsForCurrentUser(),
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                String[] boardNames = new String[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++){
                    try {
                        JSONObject ob = (JSONObject) jsonArray.get(i);
                        boardNames[i] = ob.getString("name");
                        boardIds.add(ob.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                        SelectProjectActivity.this,
                        android.R.layout.simple_list_item_activated_1,
                        boardNames);
                myList.setAdapter(myAdapter);
                Log.i(logTag, "Successfully retrieved boards for current user ");
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag, "Failed to retrieve boards for current user");
            }

        });

        queue.add(startRequest);
    }
}
