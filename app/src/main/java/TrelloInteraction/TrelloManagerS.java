package TrelloInteraction;

import android.content.Context;
import android.util.Pair;

import static TrelloInteraction.TrelloUrl.GET_BOARD;
import static TrelloInteraction.TrelloUrl.GET_BOARD_CARDS;
import static TrelloInteraction.TrelloUrl.createURL;

/**
 * Construct Trello requests.
 */
public enum TrelloManagerS {
    INSTANCE;
    private String appKey; // Application key used for any Trello API requests
    private String authToken; //Authentication token used for any Trello API requests



    public void init(String appKey, String authToken) {
        this.appKey = appKey;
        this.authToken = authToken;
    }

    /**
     * Returns a URL for requesting all cards belonging to boardID, with optional args
     * @param boardID
     * @param args
     * @return Request URL
     */
    public String getBoardCards(String boardID, Argument ... args) {
        return(enhanceURL(createURL(GET_BOARD_CARDS).params(args).asString(boardID)));
    }

    //Temporary hacky method.
    private String enhanceURL (String URL) {
    URL = URL.replace("[applicationKey]", appKey);
    URL = URL.replace("&token=[userToken]", "");
        return URL;
    }
}
