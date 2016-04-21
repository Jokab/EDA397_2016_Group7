package Game;

import java.util.List;

import TrelloInteraction.Board;
import TrelloInteraction.Card;

public class GameSession {

    private Board gameBoard;
    private int nrOfPlayers;
    private String currentCardID;

    public GameSession(String id, int nrOfPlayers) {
        gameBoard = new Board(id);
        this.nrOfPlayers = nrOfPlayers;
        //Start_Session
    }

    public void setCurrentCard(Card card) {
        // DOM REQ
        // Delete current card from Board
    }

    public void getCurrentCard() {
        //Dom req
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void getCurrentRatings() {
        //Dom req
    }

    public void getAllCardRatings() {
        //Dom req
    }

    public void setRating() {
        //Dom Req
    }
}
