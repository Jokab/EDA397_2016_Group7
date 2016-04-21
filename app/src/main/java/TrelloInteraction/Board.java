package TrelloInteraction;

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


    public Board(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

}
