package game;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import trelloInteraction.Board;

import static game.ServerURL.ARG_MEMBER_ID;
import static game.ServerURL.ARG_RATING_ID;
import static game.ServerURL.RATE_CURRENT_CARD;
import static game.ServerURL.REGISTER_MEMBER;
import static game.ServerURL.createURL;


/**
 * A player specific game session
 */
public class PlayerSession extends GameSession {

    public PlayerSession() {
    }

    /**
     * Sends a request to game server to rate the current card
     * @param rating
     */
    public void setRating(int rating) {
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put(ARG_MEMBER_ID,""+memberId);
        jsonParams.put(ARG_RATING_ID, ""+rating);
        Log.i(logTag, (new JSONObject((jsonParams)).toString() ));
        CustomJsonObjRequest rateCardRequest = new CustomJsonObjRequest(Request.Method.POST,
                createURL(RATE_CURRENT_CARD).asString(), jsonParams ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Log.i(logTag, "Rated card");
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to rate card");
                BroadCastManager.get().broadCast(BroadCastTypes.COULD_NOT_RATE);
            }
        });
        queue.add(rateCardRequest);
    }

    /**
     * Sends a request to game server to registers at the current session
     */
    public void registerToSession() {
        CustomJsonObjRequest registerRequest = new CustomJsonObjRequest(Request.Method.POST,
                createURL(REGISTER_MEMBER).asString(), null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            memberId = response.getInt(ServerURL.ARG_MEMBER_ID);
                            BroadCastManager.get().broadCast(BroadCastTypes.REGISTER_SUCCESSFUL);
                            setGameBoard(response.getString(ServerURL.ARG_BOARD_ID));
                            gameBoard.updateCards();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(logTag, "Registered to session");
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"could not register to session");
            }
        });
        queue.add(registerRequest);
    }

    public void setGameBoard(String boardId) {
        gameBoard = new Board(boardId);
    }
}
