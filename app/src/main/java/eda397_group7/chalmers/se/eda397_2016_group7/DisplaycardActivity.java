package eda397_group7.chalmers.se.eda397_2016_group7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import Game.GameSessionHolder;
import TrelloInteraction.Card;

public class DisplaycardActivity extends AppCompatActivity {

    private int rateResult = 0;
    private TextView ratingResultView;
    private TextView currentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycard_developers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ratingResultView = (TextView) findViewById(R.id.rateResult);
        ratingResultView.addTextChangedListener(new RateListener());

        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new RefreshListener());
        Button submitButton = (Button) findViewById(R.id.submitRateButton);
        submitButton.setOnClickListener(new SubmitListener());
    }

    private class RateListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            rateResult = Integer.parseInt(ratingResultView.getText().toString());
        }
    }

    private class SubmitListener implements View.OnClickListener {
        public void onClick(View v) {
            if (rateResult <= 0) {
                Toast.makeText(getApplicationContext(), "Please rate the card", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Thanks for the rating", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RefreshListener implements View.OnClickListener {
        public void onClick(View v) {
            Log.i("RB", "Refresh button set");
            try {
                GameSessionHolder.getInstance().getSession().getCurrentCard();
                String id = GameSessionHolder.getInstance().getSession().getCurrentCardId();
                String name = GameSessionHolder.getInstance().getSession().getGameBoard().getCard(id).getName();
                ((TextView) findViewById(R.id.currentCard)).setText(name);
            } catch (NullPointerException e) {
            }
        }
    }
}