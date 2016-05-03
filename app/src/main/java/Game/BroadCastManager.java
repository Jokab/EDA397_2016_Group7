package Game;

import android.app.Application;
import android.content.Intent;

public class BroadCastManager extends Application {

        private static BroadCastManager instance;
        public static BroadCastManager get() { return instance; }

        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
        }

    public void broadCast(String msg) {
        sendBroadcast(new Intent(msg));
    }
}
