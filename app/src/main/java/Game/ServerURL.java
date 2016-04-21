package Game;


import TrelloInteraction.Argument;
import TrelloInteraction.TrelloUrl;

public class ServerURL {

    public static final String API_URL = "https://api.trello.com/1";
    public static final String API_KEY_TOKEN_PARAM = "key=[applicationKey]&token=[userToken]";

    public static final String Start_Session = "/boards/{boardId}?";

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
        builder.append(baseURL.replaceFirst("((\\{).*?(Id)(\\}))",realID));
      //  builder.append(API_KEY_TOKEN_PARAM);
        for(Argument arg : args){
            builder.append("&");
            builder.append(arg.getArgName());
            builder.append("=");
            builder.append(arg.getArgValue());
        }
        return builder.toString();
    }


}
