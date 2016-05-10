package trelloInteraction;

public class TrelloAuthenticationConstants {
    public final static String APP_KEY = "ff0bd4ea77916c91b322590c767a684d";
    public final static String APP_NAME = "PlanningPokerWithTrello";
    public final static String EXPIRATION = "never";
    public final static String CALLBACK_METHOD = "fragment";
    public final static String SCOPE = "read,write";
    public final static String returnUrl = "ase://oauthresponse";
    public final static String trelloAuthorizeUrl = "https://trello.com/1/authorize?expiration="+ EXPIRATION +"&name="+ APP_NAME +"&key="+ APP_KEY +"&callback_method="+ CALLBACK_METHOD +"&return_url="+returnUrl+"&scope="+SCOPE;
}
