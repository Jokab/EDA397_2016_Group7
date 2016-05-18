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

import game.GameSessionHolder;
import game.HostSession;
import trelloInteraction.Board;
import trelloInteraction.TrelloAuthenticationConstants;
import trelloInteraction.TrelloManagerS;
import trelloInteraction.VolleyManager;

public class MainActivity extends AppCompatActivity {

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

        Button trelloLoginButton = (Button) findViewById(R.id.trello_login);
        sharedPreferences = this.getSharedPreferences(
                "authorizeprefs", Context.MODE_PRIVATE);
        String authtoken = sharedPreferences.getString("authtoken", "empty");
        if (!authtoken.equals("empty")) {
            Intent intent = new Intent(MainActivity.this, ChooseRoleActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        //Need to set this up ONCE.
        RequestQueue queue = VolleyManager.getInstance(this.getApplicationContext()).getRequestQueue();
        TrelloManagerS.INSTANCE.init(TrelloAuthenticationConstants.APP_KEY, authtoken);
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
        String code;

        if (uri != null && uri.toString().startsWith("ase://oauthresponse")) {
            String[] uriParts = uri.toString().split("#token=");
            if (uriParts.length > 0) {
                sharedPreferences = this.getSharedPreferences(
                        "authorizeprefs", Context.MODE_PRIVATE);
                code = uriParts[1];
                sharedPreferences.edit().putString("authtoken", code).apply();
                returnFromAuth = true;
                TrelloManagerS.INSTANCE.setAuthToken(code);
            }
            Intent i = new Intent(MainActivity.this, Tutorial.class);
            startActivity(i);
        }
        return returnFromAuth;
    }
}
