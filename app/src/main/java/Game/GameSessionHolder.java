package Game;

/**
 * Singleton used to persist GameSessions when switching activities
 * Note: Parcel is probably a more clean solution.
 */
public class GameSessionHolder {
    private GameSession session;
    private static final GameSessionHolder holder = new GameSessionHolder();

    public GameSession getSession() {
        return session;
    }
    public void setSession(GameSession session) {
        this.session = session;
    }
    public static GameSessionHolder getInstance() {
        return holder;
    }
}
