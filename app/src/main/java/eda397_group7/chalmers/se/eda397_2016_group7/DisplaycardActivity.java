package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import game.BroadCastTypes;
import game.GameSessionHolder;
import game.PlayerSession;

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

        currentCard = (TextView) findViewById(R.id.currentCard);
        ratingResultView = (TextView) findViewById(R.id.rateResult);
        ratingResultView.addTextChangedListener(new RateListener());

        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new RefreshListener());
        Button submitButton = (Button) findViewById(R.id.submitRateButton);
        submitButton.setOnClickListener(new SubmitListener());

        BroadcastReceiver receiver = new MyBroadcastReceiver();
        IntentFilter f1 = new IntentFilter(BroadCastTypes.CURRENT_CARD_RECEIVED);
        registerReceiver(receiver, f1);


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
                ((PlayerSession)GameSessionHolder.getInstance().getSession()).setRating(rateResult);
                Toast.makeText(getApplicationContext(), "Thanks for the rating", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private class RefreshListener implements View.OnClickListener {
        public void onClick(View v) {
            Log.i("RB", "Refresh button set");
            GameSessionHolder.getInstance().getSession().getCurrentCard();
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("receiver","Filtering :");
            if(action.equals(BroadCastTypes.CURRENT_CARD_RECEIVED)){
                String id = GameSessionHolder.getInstance().getSession().getCurrentCardId();
                String name = GameSessionHolder.getInstance().getSession().getGameBoard().getCard(id).getName();
                currentCard.setText(name);
                Log.i("receiver","received current card:");
            } else if (action.equals(BroadCastTypes.COULD_NOT_RATE)) {
                Toast.makeText(getApplicationContext(), "Could not rate card", Toast.LENGTH_SHORT).show();
            }
        }

    }
}