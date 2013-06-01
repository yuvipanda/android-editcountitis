package in.yuvi.editcountitis.widget;

import java.net.HttpURLConnection;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import in.yuvi.editcountitis.R;

public class EditsWidgetUpdaterService extends Service {
    private static final String TAG = "Editcountitis";

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "Called");
        // Create some random data

        String url = "https://en.wikipedia.org/w/api.php?action=query&list=users&format=json&usprop=editcount&ususers=Yuvipanda";
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        ComponentName thisWidget = new ComponentName(getApplicationContext(),EditsWidgetProvider.class);

        RequestQueue queue = Volley.newRequestQueue(this);

        for (final int widgetId : allWidgetIds) {
            // Create some random data
            int number = (new Random().nextInt(100));

            final RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);

            queue.add(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    String count = result.optJSONObject("query").optJSONArray("users").optJSONObject(0).optString("editcount");
                    remoteViews.setTextViewText(R.id.count_alledits, count);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                    Log.d(TAG, "DONE!~");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d(TAG, "AW FUCK");
                }
            }
            ));
        }

        queue.start();
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}