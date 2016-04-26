package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import eda397_group7.chalmers.se.eda397_2016_group7.R;

public class DisplayResultsActivity extends AppCompatActivity {

    public static final String[] TEST_RESULTS_LIST_DATA = new String[]{"5", "3", "7", "1", "3", "6", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                TEST_RESULTS_LIST_DATA);
        GridView gridview = (GridView) findViewById(R.id.ratingListGrid);
        gridview.setAdapter(myAdapter);


        Button nextCard = (Button) findViewById(R.id.moveNextCard);
        nextCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DisplayResultsActivity.this, DisplayProjectCardsActivity.class));
            }
        });
        Button rateAgain = (Button) findViewById(R.id.rateCardAgain);
        rateAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


    }

}
