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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import eda397_group7.chalmers.se.eda397_2016_group7.R;

public class DisplaycardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycard_developers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int[] rateResult = {0};
        final TextView ratingResult =
                (TextView) findViewById(R.id.rateResult);
        ratingResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                rateResult[0] = Integer.parseInt(ratingResult.getText().toString());
            }
        });

        Button submitButton = (Button) findViewById(R.id.submitRateButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (rateResult[0] == 0) {
                    Toast.makeText(getApplicationContext(), "Please rate the card", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Thanks for the rating", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
