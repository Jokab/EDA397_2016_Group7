package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TrelloInteraction.Argument;
import TrelloInteraction.Board;
import TrelloInteraction.TrelloManagerS;
import TrelloInteraction.VolleyManager;

public class MainActivity extends AppCompatActivity {

    private final static String appKey = "ff0bd4ea77916c91b322590c767a684d";
    private final static String appName = "PlanningPokerWithTrello";
    private final static String expiration = "never";
    private final static String callbackMethod = "fragment";
    private final static String returnUrl = "ase://oauthresponse";
    private final static String trelloAuthorizeUrl = "https://trello.com/1/authorize?expiration="+expiration+"&name="+appName+"&key="+appKey+"&callback_method="+callbackMethod+"&return_url="+returnUrl;
    private RequestQueue queue;

    private JSONObject requestObj;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button trelloLoginButton = (Button) findViewById(R.id.trello_login);
        queue = VolleyManager.getInstance(this.getApplicationContext()).getRequestQueue();
        if (trelloLoginButton != null) {
            trelloLoginButton.setOnClickListener(   new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trelloAuthorizeUrl)));
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                        Log.e("Dashboard no auth", "Cannot initiate communication to get the request token\nException: " + ex.getClass().getName() + "\nMessage: " + ex.getMessage());
                    }
                }
            });
        }
        boolean justAuthenticated = checkOAuthReturn(getIntent());

        TrelloManagerS.INSTANCE.init(appKey, "babblish");

        final JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                TrelloManagerS.INSTANCE.getBoard("l56NCOG9", Argument.arg("fields", "name,desc,idList")),
                new Response.Listener<JSONArray>() {

            @Override

            public void onResponse(JSONArray response) {
                TextView t = (TextView) findViewById(R.id.trello_text);
                t.setText("Response => " + response.toString());
                Log.i("Real Response", response.toString());

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                TextView t = (TextView) findViewById(R.id.trello_text);
                Log.i("Error Response", error.getCause().toString());
                t.setText("request dun goofed");
            }

        });


        Button trelloTestButton = (Button) findViewById(R.id.trello_api);
        if (trelloTestButton != null) {
            trelloTestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        queue.add(jsObjRequest);
                    } catch (Exception ex) {
                    }
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
                code = uriParts[1];
                returnFromAuth = true;
            }
            Toast.makeText(this, code, Toast.LENGTH_LONG).show();
        }
        return returnFromAuth;
    }
}
