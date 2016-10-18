package com.sunzhk.lockscreendemo;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by sunzhk on 2016/3/8.
 */
public class MyApplication extends Application {

	private static MyApplication myApplication;

	public static MyApplication getInstance(){
		return myApplication;
	}

	private boolean isInited = false;
	
    @Override
    public void onCreate() {
		myApplication = this;
		super.onCreate();
        Intent intent = new Intent(this, ScreenListenerService.class);
        startService(intent);
        updateUseTimes();
    }
	
	private void updateUseTimes(){
		SharedPreferences usePreferences = getSharedPreferences("UseInfo", MODE_PRIVATE);
		isInited = usePreferences.getBoolean("InitState", false);
	}
	
	public boolean getInitState(){
		return isInited;
	}
	
	public void setInitState(boolean isInited){
		if(this.isInited == isInited){
			return;
		}
		this.isInited = isInited;
		SharedPreferences usePreferences = getSharedPreferences("UseInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = usePreferences.edit();
		editor.putBoolean("InitState", isInited);
		editor.commit();
	}
    
}
