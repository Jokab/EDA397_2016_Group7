package trelloInteraction;

import static trelloInteraction.TrelloUrl.GET_BOARD_CARDS;
import static trelloInteraction.TrelloUrl.GET_BOARDS;
import static trelloInteraction.TrelloUrl.createURL;

/**
 * Construct Trello requests.
 */
public enum TrelloManagerS {
    INSTANCE;
    private String appKey; // Application key used for any Trello API requests
    private String authToken;


    public void init(String appKey, String authToken) {
        this.appKey = appKey;
        this.authToken= authToken;
    }

    public void setAuthToken(String authToken){
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
        if(authToken.equals("empty")){
            URL = URL.replace("&token=[userToken]", "");
        } else {
            URL = URL.replace("[userToken]", authToken);
        }
        return URL;
    }

    /**
     * Returns a URL for requesting all cards belonging to boardID, with optional args
     * @param args
     * @return Request URL
     */
    public String getBoardsForCurrentUser(Argument ... args) {
        return(enhanceURL(createURL(GET_BOARDS).asString()));
    }
}
