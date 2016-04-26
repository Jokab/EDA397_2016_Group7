package eda397_group7.chalmers.se.eda397_2016_group7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Random;

public class DisplayResultsActivity extends AppCompatActivity {

    public static String[] TEST_RESULTS_LIST_DATA = new String[]{"5", "3", "7", "1", "3", "6", "5"};
    private ArrayAdapter<String> resultsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultsListAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                TEST_RESULTS_LIST_DATA);
        GridView gridview = (GridView) findViewById(R.id.ratingListGrid);
        gridview.setAdapter(resultsListAdapter);

        Button nextCard = (Button) findViewById(R.id.moveNextCard);
        nextCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Finish destroys this activity and returns to the previous one
                finish();
            }
        });
        Button rateAgain = (Button) findViewById(R.id.rateCardAgain);
        rateAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TEST_RESULTS_LIST_DATA = assignRandomDataToList();
                resultsListAdapter.notifyDataSetChanged();
            }
        });
    }

    private String[] assignRandomDataToList() {
        Random random = new Random();
        String[] result = new String[TEST_RESULTS_LIST_DATA.length];
        for(int i = 0; i < TEST_RESULTS_LIST_DATA.length; ++i) {
            int randomInt = random.nextInt((20 - 1) + 1) + 1;
            result[i] = String.valueOf(randomInt);
        }

        return result;
    }

}
