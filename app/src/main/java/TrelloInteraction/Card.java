package TrelloInteraction;

/**
 * Created by lundenant on 2016-04-19.
 */
public class Card {

    private String id;
    private String name;
    private String desc;
    private int rating;

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

    public String getName() {
        return name;
    }
}
