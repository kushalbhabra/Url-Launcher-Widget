package com.oracle.bi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

public class LaunchURLWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for(int i=0;i<appWidgetIds.length;i++){
			int appWidgetId = appWidgetIds[i];
			
			SharedPreferences prefs = context.getSharedPreferences("OraclePrefs", MainActivity.MODE_PRIVATE);
			String url = prefs.getString("url", "http://www.google.com"); // IF url is null , then go to google.com 
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			
			PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
			
			RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetlayout);
			views.setOnClickPendingIntent(R.id.imgBtnLaunchUrl, pending);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
			
		}
	}

}
