package Game;


import TrelloInteraction.Argument;
import TrelloInteraction.TrelloUrl;

public class ServerURL {

    public static final String API_URL = "http://agile.project.domein.co.at";

    public static final String START_SESSION = "/startNewSession"; // amountOfMembers, trelloBoardId
    public static final String REGISTER_MEMBER = "/registerMember";
    public static final String SET_CURRENT_CARD = "/selectNewCard"; // cardId, :memberId
    public static final String GET_CURRENT_CARD = "/getCurrentCard";
    public static final String RATE_CURRENT_CARD = "/RateCurrentCard"; //:memberId, Rating
    public static final String CLOSE_CURRENT_CARD = "//closeCard"; //:memberId
    public static final String RESET_GAME = "/resetGame";
    public static final String RESET_CARD = "/resetCard";// memberId
    public static final String GET_CURRENT_CARD_RATINGS = "/getAllRatingsForCurrentCard"; //:memberId
    public static final String GET_ALL_RATINGS = "/getAllRatings"; // :memberid




    private String baseURL;
    private Argument[] args = {};

    private ServerURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public ServerURL params(Argument... args) {
        this.args = args;

        return this;
    }

    public static ServerURL createURL(String baseURL) {
        return new ServerURL(baseURL);
    }

    public String asString() {
        StringBuilder builder = new StringBuilder(API_URL);
        builder.append(baseURL);
        for (Argument arg : args) {
            builder.append("&");
            builder.append(arg.getArgName());
            builder.append("=");
            builder.append(arg.getArgValue());
        }
        return builder.toString();
    }

}
