package TrelloInteraction;

public class TrelloAuthenticationConstants {
    public final static String appKey = "ff0bd4ea77916c91b322590c767a684d";
    public final static String appName = "PlanningPokerWithTrello";
    public final static String expiration = "never";
    public final static String callbackMethod = "fragment";
    public final static String returnUrl = "ase://oauthresponse";
    public final static String trelloAuthorizeUrl = "https://trello.com/1/authorize?expiration="+expiration+"&name="+appName+"&key="+appKey+"&callback_method="+callbackMethod+"&return_url="+returnUrl;
}
