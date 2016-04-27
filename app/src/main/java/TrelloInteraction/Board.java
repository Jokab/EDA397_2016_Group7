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
import java.util.Map;

public class Board implements Parcelable{

    private String id;
    private String name;
    public ArrayList<Card> cards = new ArrayList<Card>();
    //private Map<String, Card> cards = new HashMap<>();
    private String logTag = "Board LOG";


    public Board(String id) {
        this.id = id;
    }

    public Board(Parcel in) {
        id = in.readString();
        name = in.readString();
        in.readTypedList(cards, Card.CREATOR);
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

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
                Log.i(logTag, "Board Error: Could not update cards for board " + name + ", error cause:" + error.getCause().toString());
            }

        });
        VolleyManager.getInstance(null).addToRequestQueue(cardsRequest);

    }
    public void addCard(Card card) {
        cards.add(card);
        //cards.put(card.getName(), card);
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

    public Card getCard(String cardName){
        try{
            return(cards.get(0));
            //return(cards.get(cardName));
        }catch (Exception e) {
            Log.i(logTag, "Card array empty");
        }
        return null;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeList(cards);
    }
}
