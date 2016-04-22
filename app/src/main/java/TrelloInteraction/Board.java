package TrelloInteraction;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eda397_group7.chalmers.se.eda397_2016_group7.R;

/**
 * Created by lundenant on 2016-04-19.
 */
public class Board implements Parcelable{

    private String id;
    private String name;
    private List<Card> cards = new ArrayList<>();

    public Board(String id) {
        this.id = id;
    }

    public Board(Parcel in) {
        id = in.readString();
        name = in.readString();
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
                TrelloManagerS.INSTANCE.getBoard(id, Argument.arg("fields", "name,desc")),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JArrayToCards(response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i("Board Error: ", "Could not update cards for board " + name + ", error cause:" + error.getCause().toString());
            }

        });
        VolleyManager.getInstance(null).addToRequestQueue(cardsRequest);

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
            cardName = cards.get(0).getName();
        }catch (Exception e) {
            Log.i("Card array empty", null);
        }
        return cardName;
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
