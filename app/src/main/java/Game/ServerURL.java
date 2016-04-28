package game;


import trelloInteraction.Argument;


/**
 * Helper class to construct requests for the game server
 */
public class ServerURL {

    public static final String API_URL = "http://agile.project.domein.co.at";

    //Base URLs
    public static final String START_SESSION = "/startNewSession"; // amountOfMembers, trelloBoardId
    public static final String REGISTER_MEMBER = "/registerMember";
    public static final String SET_CURRENT_CARD = "/selectNewCard"; // cardId, :memberId
    public static final String GET_CURRENT_CARD = "/getCurrentCard";
    public static final String RATE_CURRENT_CARD = "/rateCurrentCard"; //:memberId, Rating
    public static final String CLOSE_CURRENT_CARD = "//closeCard"; //:memberId
    public static final String RESET_GAME = "/resetGame";
    public static final String RESET_CARD = "/resetCard";// memberId
    public static final String GET_CURRENT_CARD_RATINGS = "/getAllRatingsForCurrentCard"; //:memberId
    public static final String GET_ALL_RATINGS = "/getAllRatings"; // :memberid

    //Parameters
    public static final String ARG_BOARD_ID = "trelloBoardId";
    public static final String ARG_NR_OF_PLAYERS = "amountOfMembers";
    public static final String ARG_MEMBER_ID = "memberId";
    public static final String ARG_CARD_ID = "cardId";
    public static final String ARG_RATING_ID = "rating";

    private final String baseURL;
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
