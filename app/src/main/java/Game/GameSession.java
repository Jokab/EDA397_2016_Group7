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

/*
PHP API

http://agile.project.domein.co.at/

Method used for starting a session
**startNewSession

Method for registering a user in an ongoing session.
**registerMember

Method for selecting a new card. Old card will be deleted if one exists.
**selectNewCard

Method for closing a card so that no more votings for it are accepted.
**closeCard

Method for resetting a card so that the old votings of a card are deleted in case a card is iterated multiple times.
**resetCard

Method for receiving the id of the currently selected card.
**getCurrentCard

Method for receiving votings for the current card given that every member has voted.
**getVotingsForCurrentCard

Method for receiving votings for the current card given that every member has voted.
**getAllVotings

Method for resetting the whole game.
**resetGame

 */