package game;

import android.app.Application;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcel;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import trelloInteraction.Board;
import trelloInteraction.VolleyManager;

import trelloInteraction.Argument;
import trelloInteraction.Board;
import trelloInteraction.Card;
import trelloInteraction.TrelloManagerS;
import trelloInteraction.VolleyManager;

import static game.ServerURL.ARG_CARD_ID;
import static game.ServerURL.ARG_MEMBER_ID;
import static game.ServerURL.GET_CURRENT_CARD;
import static game.ServerURL.RESET_GAME;
import static game.ServerURL.SET_CURRENT_CARD;
import static game.ServerURL.createURL;
import static game.ServerURL.GET_CURRENT_CARD;
import static game.ServerURL.createURL;

/**
 * A game session.
 */
public abstract class GameSession {

    protected Board gameBoard;
    protected String currentCardId;
    protected int memberId;
    protected final RequestQueue queue = VolleyManager.getInstance(null).getRequestQueue();
    protected final String logTag = "Game Session Log";

    public GameSession(String boardId) {
        gameBoard = new Board(boardId);

    }

    public GameSession() {
    }

    public void getCurrentCard() {
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.GET,
                createURL(GET_CURRENT_CARD).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentCardId = response.getString("cardId");
                            BroadCastManager.get().broadCast(BroadCastTypes.CURRENT_CARD_RECEIVED);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(logTag, "Successfully retrieved current card: " + currentCardId);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag, "Failed to retrieve current card");
            }

        });
        queue.add(startRequest);
    }

    public void resetGame() {
        JsonObjectRequest startRequest = new JsonObjectRequest(Request.Method.GET,
                createURL(RESET_GAME).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(logTag, "Successfully reset");
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag, "Failed to reset session");
            }

        });
        queue.add(startRequest);
    }

    /**
     * @return id of current card.
     */

    public String getCurrentCardId() {
        return currentCardId;
    }

    /**
     * @return the gameboard
     */
    public Board getGameBoard() {
        return gameBoard;
    }
}