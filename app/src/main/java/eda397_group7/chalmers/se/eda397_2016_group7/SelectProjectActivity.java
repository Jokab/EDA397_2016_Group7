package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import eda397_group7.chalmers.se.eda397_2016_group7.R;

public class SelectProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String[] myStringArray = {"Aasdasdasd asdas asdsa", "B", "C"};
        final String[] selectedProject = {""};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                myStringArray);
        final ListView myList =
                (ListView) findViewById(R.id.listViewOfProjects);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProject[0] = (String) myList.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), (String) myList.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
        myList.setAdapter(myAdapter);

        final int[] players = {0};
        final TextView numberOfPlayersField =
                (TextView) findViewById(R.id.NumberOfPlayers);
        numberOfPlayersField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                players[0] = Integer.parseInt(numberOfPlayersField.getText().toString());
            }
        });

        Button DevButton = (Button) findViewById(R.id.startGameButton);
        DevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedProject[0].isEmpty() || players[0] == 0) {
                    Toast.makeText(getApplicationContext(), "Select project and number of players", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(SelectProjectActivity.this, DisplayProjectCardsActivity.class));
                }
            }
        });

    }

}