package Game;

import android.os.Parcelable;
import android.os.Parcel;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
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

/**
 * A game session.
 */
public class GameSession {

    protected Board gameBoard;
    protected String currentCardId;
    protected int memberId;
    protected RequestQueue queue = VolleyManager.getInstance(null).getRequestQueue();
    protected String logTag = "Game Session Log";
    public GameSession(String boardId) {
        gameBoard = new Board(boardId);
    }
    public GameSession() { }

    public ArrayList<Board> board = new ArrayList<Board>();
    /*

    public GameSession(Parcel in){
        in.readTypedList(board, Board.CREATOR);
        memberId = in.readInt();
        currentCardId = in.readString();
     }

     */
    /**
     * Retrieves the current card id from the game server
     */
    public void getCurrentCard() {
        Map<String, String> jsonParams = new HashMap<String, String>();
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.GET,
                createURL(GET_CURRENT_CARD).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentCardId = response.getString("cardId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(logTag,"Successfully retrieved current card: " + currentCardId);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to retrieve current card");
            }

        });
        queue.add(startRequest);
    }

    /**
     *
      * @return id of current card.
     */

    public String getCurrentCardId() {
        return currentCardId;
    }

    /**
     *
     * @return the gameboard
     */
    public Board getGameBoard() {
        return gameBoard;
    }

    /*
    public int describeContents(){
        return 0;
    }

    public static final Creator<GameSession> CREATOR = new Creator<GameSession>(){
        @Override
        public GameSession createFromParcel(Parcel in){
            return new GameSession(in);
        }

        @Override
        public GameSession[] newArray(int size){
            return new GameSession[size];
        }
    };

    public void writeToParcel(Parcel parcel){
        parcel.writeList(board);
        parcel.writeInt(memberId);
        parcel.writeString(currentCardId);
    }
    */
}