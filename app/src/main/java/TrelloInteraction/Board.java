package TrelloInteraction;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lundenant on 2016-04-19.
 */
public class Board {

    private String id;
    private String name;
    private List<Card> cards = new ArrayList<>();

    public Board(String id) {
        this.id = id;
    }

    public void addCard(Card card) {
        cards.add(card);
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

    public String getCard(){
        String cardName = "";

        try{
            cardName = cards.get(0).name.toString();
        }catch (Exception e) {
            Log.i("Card array empty", null);
        }

        return cardName;
    }
}
