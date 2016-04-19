package TrelloInteraction;


import android.util.Log;

import java.util.regex.Matcher;

public class TrelloUrl {

    public static final String API_URL = "https://api.trello.com/1";
    public static final String API_KEY_TOKEN_PARAM = "key=[applicationKey]&token=[userToken]";

    // Boards Base URLs
    public static final String GET_BOARD = "/boards/{boardId}?";
    public static final String GET_BOARD_CARDS = "/boards/{boardId}/cards?";

    //Actions Base URLs
    public static final String GET_ACTION = "/actions/{actionId}?";

    //Cards Base URLs
    public static final String GET_CARD = "/cards/{cardId}?";


    private String baseURL;
    private Argument[] args = {};

    private TrelloUrl (String baseURL) {
        this.baseURL = baseURL;
    }

    public TrelloUrl params(Argument... args) {
        this.args = args;

        return this;
    }

    public static TrelloUrl createURL(String baseURL) {
        return new TrelloUrl(baseURL);
    }

    public String asString(String realID) {
        StringBuilder builder = new StringBuilder(API_URL);
        //builder.append(baseURL.replaceFirst("(.*?(\\{).*?(id)(\\}))",realID));
        builder.append(baseURL.replace("{boardId}", realID));
        builder.append(API_KEY_TOKEN_PARAM);
        for(Argument arg : args){
            builder.append("&");
            builder.append(arg.getArgName());
            builder.append("=");
            builder.append(arg.getArgValue());
        }
        Log.println(Log.INFO, "Trello request", builder.toString());
        return builder.toString();
    }



}
