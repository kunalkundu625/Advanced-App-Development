package com.example.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //access of time from device to update on widget screen
        String timeString= DateFormat.getTimeInstance(DateFormat.FULL).format(new Date());
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            Intent i= new Intent(context,MainActivity.class);//to open MainActivity
            PendingIntent pi=PendingIntent.getActivity(context,0,i,0);
            //App widget layouts are based on RemoteViews
            //Creates a RemoteViews object from the layout
            //Updates the views in the layout with data
            //Passes remote view to app widget manager to display
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            //Use setOnClickPendingIntent() to connect PendingIntent to one or more views in app widget
            views.setOnClickPendingIntent(R.id.widgetbutton,pi);
            // Update a text view to display the app widget ID
          views.setTextViewText(R.id.appwidget_text,timeString);//only after 30 min
            //Toast message which dispaly  widget update time: and ID
             Toast.makeText(context,"widget update time:"+timeString+ " Id:"+appWidgetId,Toast.LENGTH_SHORT).show();
             //to display on logcat
            Log.d("widgetupdatevalue1","Widget update time: "+timeString+ " Id:"+appWidgetId);
            //Create an intent with the AppWidgetManager.ACTION_UPDATE action//
            Intent intentUpdate=new Intent(context,NewAppWidget.class);
            //App widget manager sends broadcast intent with ACTION_APPWIDGET_UPDATE
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            //Update the current widget instance only,by creating an array that contains the widget unique ID//
            int[] idArray=new int[]{appWidgetId};
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,idArray);
            //wrap the intent as aPendingIntent.getBroadcast()//
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,appWidgetId,intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);

            //Send the pending intent in response to the user tapping the update value button//
            views.setOnClickPendingIntent(R.id.updateValueBTN,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}