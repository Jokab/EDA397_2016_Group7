package trelloInteraction;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.BroadCastManager;
import game.BroadCastTypes;

public class Board {

    private final String id;
    private String name;
    private final Map<String, Card> cards = new HashMap<>();
    private final String logTag = "Board LOG";


    public Board(String id) {
        this.id = id;
    }

    public void updateCards() {
        JsonArrayRequest cardsRequest = new JsonArrayRequest(
                TrelloManagerS.INSTANCE.getBoardCards(id, Argument.arg("fields", "name,desc")),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JArrayToCards(response);
                        BroadCastManager.get().broadCast(BroadCastTypes.CURRENT_BOARD_UPDATED);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(logTag, "Board Error: Could not update cards for board " + name );
            }

        });
        VolleyManager.getInstance(null).addToRequestQueue(cardsRequest);

    }
    public void addCard(Card card) {
        cards.put(card.getId(), card);
    }

    public void JArrayToCards(JSONArray array) {
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject ob = (JSONObject) array.get(i);
                this.addCard(new Card(ob.getString("id"), ob.getString("name"), ob.getString("desc")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public List<String> getCardId() {
        Set<String> id = cards.keySet();
        return(new ArrayList<>(id));
    }

    public List<String> getCardNames() {
        ArrayList<String> cardNames = new ArrayList<>();
        for(Card card : cards.values()){
            cardNames.add(card.getName());
        }
        return cardNames;
    }

    public Card getCard(String id){
        try{
            return(cards.get(id));
        }catch (Exception e) {
            Log.i(logTag, "Card array empty");
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
