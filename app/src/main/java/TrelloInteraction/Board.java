package TrelloInteraction;

import android.os.Parcel;
import android.os.Parcelable;
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

public class Board {

    private String id;
    private String name;
    private Map<String, Card> cards = new HashMap<>();
    private String logTag = "Board LOG";


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
