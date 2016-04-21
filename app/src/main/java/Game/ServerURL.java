package Game;


import TrelloInteraction.Argument;
import TrelloInteraction.TrelloUrl;

public class ServerURL {

    public static final String API_URL = "http://agile.project.domein.co.at";

    public static final String START_SESSION = "/startNewSession";
    public static final String REGISTER_MEMBER = "/registerMember";
    public static final String SELECT_CARD = "/selectNewCard";

    private String baseURL;
    private Argument[] args = {};

    private ServerURL (String baseURL) {
        this.baseURL = baseURL;
    }

    public ServerURL params(Argument... args) {
        this.args = args;

        return this;
    }

    public static ServerURL createURL(String baseURL) {
        return new ServerURL(baseURL);
    }

    public String asString(String realID) {
        StringBuilder builder = new StringBuilder(API_URL);
        builder.append(baseURL);
        for(Argument arg : args){
            builder.append("&");
            builder.append(arg.getArgName());
            builder.append("=");
            builder.append(arg.getArgValue());
        }
        return builder.toString();
    }


}


/*
PHP API

Method used for starting a session
/startNewSession
* Keys:     amountOfMembers
*           trelloBoardId

{
    "error": false,
    "message": "Session successfully created.",
    "sessionId": "3",
    "memberId": "4"
}

Method for registering a user in an ongoing session.
/registerMember
{
    "error": false,
    "message": "Successfully registered in session.",
    "memberId": "5",
    "trelloBoardId": "AntonsGame"
}

Method for selecting a new card. Old card will be deleted if one exists.
/selectNewCard
* Keys:     cardId
*           memberId (of host..)

Method for closing a card so that no more votings for it are accepted.
/closeCard/:memberId

Method for resetting a card so that the old votings of a card are deleted in case a card is iterated multiple times.
/resetCard/:memberId

Method for receiving the id of the currently selected card.
/getCurrentCard

Method for receiving votings for the current card given that every member has voted.
/getVotingsForCurrentCard/:memberId

Method for receiving votings for the current card given that every member has voted.
/getAllVotings/:memberId

Method for resetting the whole game.
/resetGame
*/
