package Game;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.json.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TrelloInteraction.Argument;
import TrelloInteraction.Board;
import TrelloInteraction.Card;
import TrelloInteraction.TrelloManagerS;
import TrelloInteraction.VolleyManager;

import static Game.ServerURL.ARG_CARD_ID;
import static Game.ServerURL.ARG_MEMBER_ID;
import static Game.ServerURL.GET_CURRENT_CARD;
import static Game.ServerURL.SET_CURRENT_CARD;
import static Game.ServerURL.createURL;

public class GameSession {

    protected Board gameBoard;
    protected String currentCardID;
    protected int memberId;
    protected RequestQueue queue = VolleyManager.getInstance(null).getRequestQueue();
    protected String logTag = "Game Session Log";

    /*
        Method used for starting a session
        /startNewSession
        * Keys:     amountOfMembers
        *           trelloBoardId
     
        Method for selecting a new card. Old card will be deleted if one exists.
        /selectNewCard
        * Keys:     cardId
        *           memberId (of host..)
     */

    public GameSession(String boardId) {
        gameBoard = new Board(boardId);
    }

    public void getCurrentCard() {
        Map<String, String> jsonParams = new HashMap<String, String>();
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.GET,
                createURL(GET_CURRENT_CARD).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentCardID = response.getString("cardId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(logTag,"Successfully retrieved current card: " + currentCardID);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to retrieve current card");
            }

        });
        queue.add(startRequest);
    }

    public String getCurrentCardID() {
        return currentCardID;
    }

    public Board getGameBoard() {
        return gameBoard;
    }


    public void onResponse(JSONObject response) {

    }
}