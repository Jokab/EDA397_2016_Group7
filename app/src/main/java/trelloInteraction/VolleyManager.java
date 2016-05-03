package TrelloInteraction;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton providing a volley requestqueue for activities.
 */
public class VolleyManager {

    private static VolleyManager volleyInstance;
    private RequestQueue requestQueue;
    private static Context volleyContext;

    private VolleyManager(Context context) {
        volleyContext = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized VolleyManager getInstance(Context context) {
        if (volleyInstance == null) {
            volleyInstance = new VolleyManager(context);
        }
        return volleyInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(volleyContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
