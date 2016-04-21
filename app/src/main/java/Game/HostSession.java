package Game;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import TrelloInteraction.Argument;
import TrelloInteraction.Card;
import TrelloInteraction.TrelloManagerS;
import TrelloInteraction.VolleyManager;

import static Game.ServerURL.*;
public class HostSession extends GameSession {


    public HostSession(String boardId, int nrOfPlayers) {
        super(boardId);
        startSession(nrOfPlayers);
    }

    public void startSession(int nrOfPlayers) {
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("amountOfMembers","5");
        jsonParams.put("trelloBoardId", "5");

        Log.i(logTag, (new JSONObject((jsonParams)).toString() ));
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.POST,
                createURL(START_SESSION).asString(), jsonParams ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.getString("memberId");
                            Log.i(logTag, response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to start session");
            }

        });

        queue.add(startRequest);
    }

    public void setCurrentCard(Card card) {
        // D
        // Delete current card from Board
    }

    public void getCurrentRatings() {
        //
    }

    public void getAllCardRatings() {
        //Dom req
    }

    public void resetGame() {
        JsonObjectRequest startRequest = new JsonObjectRequest(Request.Method.POST,
                createURL(RESET_GAME).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to start session");
            }

        });
        queue.add(startRequest);
    }



}
