package com.oracle.bi;

import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity  implements OnClickListener{

	EditText mEditUrl;
	ImageButton mBtnUpdateUrl;
	SharedPreferences mPrefs;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RedirectToOracleSite();
        mBtnUpdateUrl = (ImageButton) findViewById(R.id.btnUpdatePrefs);
        mEditUrl  = (EditText) findViewById(R.id.editTextPrefs);
        SetPreferences();
        
        mBtnUpdateUrl.setOnClickListener(this);
    }

	private void SetPreferences() {
		mPrefs = getSharedPreferences("OraclePrefs", this.MODE_PRIVATE);
    	String url = mPrefs.getString("url", "http://www.google.com");
    	mEditUrl.setText(url);    	
	}

	private void RedirectToOracleSite() {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse("http://www.facebook.com"));
    	startActivity(intent);
    	this.finish();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btnUpdatePrefs:
				UpdatePreferences();
				break;
			default:
				break;
		}
	}

	private void UpdatePreferences() {
		try {
			String url = mEditUrl.getText().toString();
			
			URL testUrl = new URL(url);
			// Only for validation.. Throws exception if URL is not valid
			
			Editor editor = mPrefs.edit();
			editor.clear();
			editor.putString("url", url);
			editor.commit();
			UpdateWidgets();
			Toast.makeText(this, "URL Updated!", 5000).show();
	    } catch (MalformedURLException malformedURLException) {
	        Toast.makeText(this, "Please verify URL!", 5000).show();
	    }
	}

	private void UpdateWidgets() {
		Intent intent = new Intent(this,LaunchURLWidget.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
		// since it seems the onUpdate() is only fired on that:
		int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), LaunchURLWidget.class));
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
		sendBroadcast(intent);
	}
	
}
