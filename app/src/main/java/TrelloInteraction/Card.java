package trelloInteraction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Trello card
 */
public class Card {

    private final String id;
    private final String name;
    private final String desc;
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
