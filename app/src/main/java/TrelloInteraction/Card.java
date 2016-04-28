package TrelloInteraction;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * A Trello card
 */
public class Card {

    private String id;
    private String name;
    private String desc;
    private int rating;
    private String logTag = "Card LOG";

    public Card (String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDesc() { return desc; }

    public String getName() {
        return name;
    }

    public String getId() { return id; }
}
