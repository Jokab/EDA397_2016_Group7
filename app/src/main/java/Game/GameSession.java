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
import org.json.JSONObject;

import java.util.List;

import TrelloInteraction.Argument;
import TrelloInteraction.Board;
import TrelloInteraction.Card;
import TrelloInteraction.TrelloManagerS;
import TrelloInteraction.VolleyManager;

public class GameSession {

    private Board gameBoard;
    private String currentCardID;
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
        //GET_CURRENT_CARD -> currentCardID
    }

    public Board getGameBoard() {
        return gameBoard;
    }


    public void onResponse(JSONObject response) {

    }
}