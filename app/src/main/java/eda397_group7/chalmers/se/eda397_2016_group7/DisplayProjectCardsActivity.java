package eda397_group7.chalmers.se.eda397_2016_group7;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import eda397_group7.chalmers.se.eda397_2016_group7.R;

public class DisplayProjectCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_cards_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] myStringArray = {"Card A", "Card B", "Card C"};
        final String[] selectedCard = {""};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                myStringArray);
        final ListView myList =
                (ListView) findViewById(R.id.listViewOfProjectsCards);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCard[0] = (String) myList.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), (String) myList.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
        myList.setAdapter(myAdapter);
    }

}
