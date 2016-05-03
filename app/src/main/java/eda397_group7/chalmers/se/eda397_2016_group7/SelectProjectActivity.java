package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import game.GameSessionHolder;
import game.HostSession;

public class SelectProjectActivity extends AppCompatActivity {

    private static final String DEBUG_PROJECT_ID = "l56NCOG9";
    private static final String[] TEST_PROJECT_LIST_DATA = new String[]{"Aasdasdasd asdas asdsa", "B", "C"};
    private final String[] selectedProject = {""};
    private final int[] players = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                TEST_PROJECT_LIST_DATA);
        final ListView myList =
                (ListView) findViewById(R.id.listViewOfProjects);

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
                selectedProject[0] = (String) myList.getItemAtPosition(position);
            }
        });
    }

    private void createNumberPlayersListener(final int[] players, final TextView numberOfPlayersField) {
        numberOfPlayersField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                players[0] = Integer.parseInt(numberOfPlayersField.getText().toString());
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
}
