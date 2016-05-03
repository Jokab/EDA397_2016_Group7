package game;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import static game.ServerURL.*;

/**
 * A host specific game session
 */
public class HostSession extends GameSession {

    private int nrOfPlayers;

    public HostSession() { }

    public HostSession(String boardId, int nrOfPlayers) {
        super(boardId);
        this.nrOfPlayers = nrOfPlayers;
        startSession(nrOfPlayers);
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    /**
     * Starts a session on the game server
     * @param nrOfPlayers
     */
    public void startSession(int nrOfPlayers) {
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put(ARG_NR_OF_PLAYERS,""+nrOfPlayers);
        jsonParams.put(ARG_BOARD_ID, gameBoard.getId());

        Log.i(logTag, (new JSONObject((jsonParams)).toString()));
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.POST,
                createURL(START_SESSION).asString(), jsonParams ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            memberId = response.getInt(ARG_MEMBER_ID);
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

    public void setCurrentCard(final String cardId) {
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put(ARG_CARD_ID, cardId);
        jsonParams.put(ARG_MEMBER_ID, "" + memberId);
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.POST,
                createURL(SET_CURRENT_CARD).asString(), jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(logTag,"Successfully set current card to: " + cardId);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to set current card to " + cardId);
            }

        });
        queue.add(startRequest);
    }

    public void resetCurrentCard() {
        CustomJsonObjRequest startRequest = new CustomJsonObjRequest(Request.Method.GET,
                createURL(RESET_CARD+"/"+this.memberId).asString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(logTag,"Successfully reset ratings for current card: ");
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag,"Failed to reset ratings for current card");
            }

        });
        queue.add(startRequest);
    }

    public void getCurrentRatings() {
        // TODO
    }

    public void getAllCardRatings() {
        //Dom req TODO
    }

}
