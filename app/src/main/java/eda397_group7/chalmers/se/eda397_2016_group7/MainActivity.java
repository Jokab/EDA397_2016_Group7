package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Game.HostSession;
import TrelloInteraction.Argument;
import TrelloInteraction.Board;
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
        if(!authtoken.equals("empty")){
            Toast.makeText(this, "already authorized", Toast.LENGTH_LONG).show();
        }

        //Need to set this up ONCE.
        queue = VolleyManager.getInstance(this.getApplicationContext()).getRequestQueue();
        //Need to set this up once, unless
        TrelloManagerS.INSTANCE.init(TrelloAuthenticationConstants.appKey, "babblish");
        if (trelloLoginButton != null) {
            trelloLoginButton.setOnClickListener(   new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TrelloAuthenticationConstants.trelloAuthorizeUrl)));
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                        Log.e("Dashboard no auth", "Cannot initiate communication to get the request token\nException: " + ex.getClass().getName() + "\nMessage: " + ex.getMessage());
                    }
                }
            });
        }
        boolean justAuthenticated = checkOAuthReturn(getIntent());

        Button trelloTestButton = (Button) findViewById(R.id.trello_api);
        if (trelloTestButton != null) {
            trelloTestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        host = new HostSession(primaryBoard.getId(), 1);
                        primaryBoard.updateCards();
                        TextView t = (TextView) findViewById(R.id.trello_text);
                        t.setText(primaryBoard.getCard());
                    } catch (Exception ex) {
                    }
                }
            });
        }

        Button phpButton = (Button) findViewById(R.id.phpButton);
        if (phpButton != null) {
            phpButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent i = new Intent(MainActivity.this, PHPActivity.class);
                    i.putExtra("board", boardID);
                    startActivity(i);

                }
            });
        }

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
            if(uriParts.length > 0){
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
