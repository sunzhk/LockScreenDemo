package com.sunzhk.lockscreendemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sunzhk.lockscreendemo.MyApplication;
import com.sunzhk.lockscreendemo.R;
import com.sunzhk.lockscreendemo.ScreenListenerService;
import com.sunzhk.lockscreendemo.common.SettingData;

public class LauncherActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ScreenListenerService.class);
        intent.putExtra("StartMode", ScreenListenerService.START_STOP);
        startService(intent);
		setContentView(R.layout.activity_launcher);
		Button btNext = (Button) findViewById(R.id.activity_launcher_bt_next);
		final boolean isInited = MyApplication.getInstance().getInitState();
		if(isInited){
			btNext.setText("开始");
		}else {
			btNext.setText("初始化");
		}
		btNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(SettingData.getInstance().isParentMode()){
					startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
				}else {
					startActivity(new Intent(LauncherActivity.this, SettingActivity.class));
				}
				finish();
			}
		});

		getPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);
//		getPermission(Manifest.permission.)
	}

	public boolean getPermission(String permission) {
		if (Build.VERSION.SDK_INT < 23) {
			return true;
		}
		if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { permission }, 10086);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		if (requestCode == 10086) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

			} else {

			}
		}
	}


}
