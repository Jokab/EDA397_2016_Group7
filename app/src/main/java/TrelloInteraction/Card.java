package TrelloInteraction;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by lundenant on 2016-04-19.
 */
public class Card implements Parcelable{

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

    public Card(Parcel in){
        id = in.readString();
        name = in.readString();
        desc = in.readString();
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

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel parcel) {
            return new Card(parcel);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(desc);
    }

}
