package eda397_group7.chalmers.se.eda397_2016_group7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DisplayResultsActivity extends AppCompatActivity {

    public static final List<String> TEST_RESULTS_LIST_DATA = new ArrayList<>(Arrays.asList(new String[]{"5", "3", "7", "1", "3", "6", "5", "5", "5","5","5","5","5","5","5","5","5"}));
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
        ListView resultsList = (ListView) findViewById(R.id.rating_listview);
        resultsListAdapter.setNotifyOnChange(true);
        resultsList.setAdapter(resultsListAdapter);

        Button nextCard = (Button) findViewById(R.id.submit_rating);
        nextCard.setOnClickListener(new NextCardListener());
        Button rateAgain = (Button) findViewById(R.id.rateCardAgain);
        rateAgain.setOnClickListener(new RateAgainListener());

        updateStatistics();
    }

    private void updateStatistics() {
        int[] resultsAsInt = new int[TEST_RESULTS_LIST_DATA.size()];
        for(int i = 0; i < resultsAsInt.length; ++i) {
            resultsAsInt[i] = Integer.parseInt(TEST_RESULTS_LIST_DATA.get(i));
        }

        TextView avgValText = (TextView) findViewById(R.id.results_avg_value);

        double avgVal = computeAverage(resultsAsInt);
        avgValText.setText(formatStatistic(avgVal));

        TextView stdValText = (TextView) findViewById(R.id.results_std_value);
        double stdVal = computeStd(resultsAsInt, avgVal);
        stdValText.setText(formatStatistic(stdVal));

        TextView medianValText = (TextView) findViewById(R.id.results_median_value);
        double median = computeMedian(resultsAsInt);
        medianValText.setText(formatStatistic(median));

        TextView modalValText = (TextView) findViewById(R.id.results_modal_value);
    }

    private double computeAverage(int[] resultsAsInt) {
        int sum = 0;
        for (int aResultsAsInt : resultsAsInt) {
            sum += aResultsAsInt;
        }
        return (double) (sum / resultsAsInt.length);
    }

    private String formatStatistic(double stdVal) {
        DecimalFormat statisticsNumberFormat = new DecimalFormat("#.00");
        return String.valueOf(statisticsNumberFormat.format(stdVal));
    }

    private double computeMedian(int[] resultsAsInt) {
        // Shamelessly stolen from
        // http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
        Arrays.sort(resultsAsInt);
        double median;
        if (resultsAsInt.length % 2 == 0)
            median = ((double)resultsAsInt[resultsAsInt.length/2] + (double)resultsAsInt[resultsAsInt.length/2 - 1])/2;
        else
            median = (double) resultsAsInt[resultsAsInt.length/2];
        return median;
    }

    private double computeStd(int[] resultsAsInt, double avgVal) {
        double stdSum = 0;
        for (int aResultsAsInt : resultsAsInt) {
            stdSum += Math.pow((aResultsAsInt - avgVal), 2);
        }
        return Math.sqrt((1/(double)resultsAsInt.length) * stdSum);
    }

    private void assignRandomDataToList() {
        int resultsListSize = TEST_RESULTS_LIST_DATA.size();
        resultsListAdapter.clear();
        Random random = new Random();

        for(int i = 0; i < resultsListSize; ++i) {
            int randomInt = random.nextInt((20 - 1) + 1) + 1;
            TEST_RESULTS_LIST_DATA.add(String.valueOf(randomInt));
        }
    }

    private class RateAgainListener implements View.OnClickListener {
        public void onClick(View v) {
            //assignRandomDataToList();
            //updateStatistics();


        }
    }

    private class NextCardListener implements View.OnClickListener {
        public void onClick(View v) {
            // Finish destroys this activity and returns to the previous one
            finish();
        }
    }
}
