package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.GameSession;
import game.GameSessionHolder;
import game.HostSession;
import trelloInteraction.Card;

import game.BroadCastTypes;

public class DisplayResultsActivity extends AppCompatActivity {

    public static final List<String> resultListData = new ArrayList<>(Arrays.asList(new String[]{"5", "3", "7", "1", "3", "6", "5", "5", "5","5","5","5","5","5","5","5","5"}));
    private ArrayAdapter<String> resultsListAdapter;
    private int nrOfPlayers;
    private EditText cardRating;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results_moderator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nrOfPlayers = ((HostSession) GameSessionHolder.getInstance().getSession()).getNrOfPlayers();
        cardRating = (EditText) findViewById(R.id.cardRating);

        resultListData.clear();
        for (int i = 0; i < nrOfPlayers; i++) {
            resultListData.add("0");
        }

        resultsListAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                resultListData);
        ListView resultsList = (ListView) findViewById(R.id.rating_listview);
        resultsListAdapter.setNotifyOnChange(true);
        resultsList.setAdapter(resultsListAdapter);

        Button nextCard = (Button) findViewById(R.id.submit_rating);
        nextCard.setOnClickListener(new NextCardListener());
        Button rateAgain = (Button) findViewById(R.id.rateCardAgain);
        rateAgain.setOnClickListener(new RateAgainListener());
        Button updateRatings = (Button) findViewById(R.id.results_update_ratings_button);
        updateRatings.setOnClickListener(new UpdateRatingsButton());

        updateStatistics();

        receiver = new Receiver();
        IntentFilter filter = new IntentFilter(BroadCastTypes.CARD_RATINGS_RECEIVED);
        registerReceiver(receiver, filter);
        IntentFilter filter2 = new IntentFilter(BroadCastTypes.CARD_RATINGS_NOT_RECEIVED);
        registerReceiver(receiver, filter2);
    }

    private void updateStatistics() {
        int[] resultsAsInt = new int[resultListData.size()];
        for(int i = 0; i < resultsAsInt.length; ++i) {
            resultsAsInt[i] = Integer.parseInt(resultListData.get(i));
        }

        TextView avgValText = (TextView) findViewById(R.id.results_avg_value);
        double avg = computeAverage(resultsAsInt);
        avgValText.setText(formatStatistic(avg));

        TextView stdValText = (TextView) findViewById(R.id.results_std_value);
        double std = computeStd(resultsAsInt, avg);
        stdValText.setText(formatStatistic(std));

        TextView medianValText = (TextView) findViewById(R.id.results_median_value);
        double median = computeMedian(resultsAsInt);
        medianValText.setText(formatStatistic(median));

        TextView modeValText = (TextView) findViewById(R.id.results_mode_value);
        double mode = computerMode(resultsAsInt);
        modeValText.setText(formatStatistic(mode));
    }

    private double computeAverage(int[] numbers) {
        int sum = 0;
        for (int aResultsAsInt : numbers) {
            sum += aResultsAsInt;
        }
        return (double) (sum / numbers.length);
    }

    private String formatStatistic(double std) {
        DecimalFormat statisticsNumberFormat = new DecimalFormat("#.00");
        return String.valueOf(statisticsNumberFormat.format(std));
    }

    private double computeMedian(int[] numbers) {
        // Shamelessly stolen from
        // http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
        Arrays.sort(numbers);
        double median;
        if (numbers.length % 2 == 0)
            median = ((double)numbers[numbers.length/2] + (double)numbers[numbers.length/2 - 1])/2;
        else
            median = (double) numbers[numbers.length/2];
        return median;
    }

    private double computeStd(int[] numbers, double average) {
        double stdSum = 0;
        for (int number : numbers) {
            stdSum += Math.pow((number - average), 2);
        }
        return Math.sqrt((1/(double)numbers.length) * stdSum);
    }

    private double computerMode(int[] numbers) {
        int maxValue = 0;
        int maxCount = 0;

        for (int i = 0; i < numbers.length; ++i) {
            int count = 0;
            for (int j = 0; j < numbers.length; ++j) {
                if (numbers[j] == numbers[i]) ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = numbers[i];
            }
        }

        return maxValue;
    }

    private class RateAgainListener implements View.OnClickListener {
        public void onClick(View v) {
            ((HostSession) GameSessionHolder.getInstance().getSession()).resetCurrentCard();
            resultsListAdapter.clear();
            for (int i = 0; i < nrOfPlayers; i++) {
                resultsListAdapter.add("0");
            }
            resetStatistics();
        }
    }

    private void resetStatistics() {
        TextView avgValText = (TextView) findViewById(R.id.results_avg_value);
        avgValText.setText("0.00");

        TextView stdValText = (TextView) findViewById(R.id.results_std_value);
        stdValText.setText("0.00");

        TextView medianValText = (TextView) findViewById(R.id.results_median_value);
        medianValText.setText("0.00");

        TextView modalValText = (TextView) findViewById(R.id.results_mode_value);
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

    private class NextCardListener implements View.OnClickListener {
        public void onClick(View v) {
            GameSession session = GameSessionHolder.getInstance().getSession();
            Card currentCard = session.getGameBoard().getCard(session.getCurrentCardId());
            if (!cardRating.getText().toString().equals("") && !cardRating.getText().toString().equals("0")) {
                int ratingForCard = Integer.parseInt(cardRating.getText().toString());
                currentCard.setRating(ratingForCard);
                finish();
            } else {
                Toast.makeText(DisplayResultsActivity.this, "Can not submit an empty rating", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateRatingsButton implements View.OnClickListener {
        public void onClick(View v) {
            ((HostSession) GameSessionHolder.getInstance().getSession()).getCurrentRatings();
        }
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(BroadCastTypes.CARD_RATINGS_RECEIVED)) {
                Log.i(DisplayResultsActivity.class.getSimpleName(), "Received ratings");
                List<String> currentRatings = ((HostSession) GameSessionHolder.getInstance().getSession()).getCurrentRatingsList();
                if(!currentRatings.isEmpty()) {
                    resultsListAdapter.clear();
                    resultsListAdapter.addAll(currentRatings);
                    updateStatistics();
                }
            }

            if(action.equals(BroadCastTypes.CARD_RATINGS_NOT_RECEIVED)) {
                Log.i(DisplayResultsActivity.class.getSimpleName(), "Could not retrieve ratings");
                Toast.makeText(DisplayResultsActivity.this, "Not every member has voted yet", Toast.LENGTH_LONG).show();
            }
        }
    }
}
