package in.yuvi.editcountitis.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.Override;
import java.util.concurrent.CountDownLatch;

import in.yuvi.editcountitis.R;

public class EditsWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "Editcountitis";
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                EditsWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        Log.d(TAG, "Hello?");

        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(), EditsWidgetUpdaterService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        context.startService(intent);
        Log.d(TAG, "Updated, yo!");
    }
}