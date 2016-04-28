package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import Game.GameSession;
import Game.GameSessionHolder;
import Game.HostSession;
import TrelloInteraction.Board;
import TrelloInteraction.Card;
import TrelloInteraction.TrelloAuthenticationConstants;
import TrelloInteraction.TrelloManagerS;
import TrelloInteraction.VolleyManager;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private HostSession host;
    private SharedPreferences sharedPreferences;

    private JSONObject requestObj;
    final String boardID = "l56NCOG9";
    private Board primaryBoard = new Board(boardID);
    private String logTag = "MainActivity LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button LogButton = (Button) findViewById(R.id.LoginButton);
        LogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChooseRoleActivity.class);

                //add testcard to board
               // primaryBoard.addCard(new Card("cardId", "cardName", "cardDesc"));
                //get card
                //Card cardz = primaryBoard.getCard("cardName");
                //Log.i(logTag, cardz.getName());

               // i.putExtra("board", primaryBoard);

                startActivity(i);
            }
        });


        Button trelloLoginButton = (Button) findViewById(R.id.trello_login);
        sharedPreferences = this.getSharedPreferences(
                "authorizeprefs", Context.MODE_PRIVATE);
        String authtoken = sharedPreferences.getString("authtoken", "empty");
        if (!authtoken.equals("empty")) {
            Toast.makeText(this, "already authorized", Toast.LENGTH_LONG).show();
        }

        //Need to set this up ONCE.
        queue = VolleyManager.getInstance(this.getApplicationContext()).getRequestQueue();
        TrelloManagerS.INSTANCE.init(TrelloAuthenticationConstants.appKey, "babblish");
        GameSessionHolder.getInstance().setSession(new HostSession() {
        });

        if (trelloLoginButton != null) {
            trelloLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(TrelloAuthenticationConstants.trelloAuthorizeUrl));
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                        Log.e("Dashboard no auth", "Cannot initiate communication to get the request token\nException: " + ex.getClass().getName() + "\nMessage: " + ex.getMessage());
                    }
                }
            });
        }
        boolean justAuthenticated = checkOAuthReturn(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        checkOAuthReturn(intent);
    }

    private boolean checkOAuthReturn(Intent intent) {
        boolean returnFromAuth = false;
        Uri uri = intent.getData();
        String code = "";

        if (uri != null && uri.toString().startsWith("ase://oauthresponse")) {
            String[] uriParts = uri.toString().split("#token=");
            if (uriParts.length > 0) {
                sharedPreferences = this.getSharedPreferences(
                        "authorizeprefs", Context.MODE_PRIVATE);
                code = uriParts[1];
                sharedPreferences.edit().putString("authtoken", code).apply();
                returnFromAuth = true;
            }
            Toast.makeText(this, "Login successful.", Toast.LENGTH_LONG).show();
        }
        return returnFromAuth;
    }
}
