package com.sunzhk.lockscreendemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.sunzhk.lockscreendemo.ScreenListenerService;
import com.sunzhk.lockscreendemo.common.SettingData;

public class FinalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		TextView tvTextView = new TextView(this);
		tvTextView.setText("设置完成！");
		tvTextView.setTextSize(30);
		setContentView(tvTextView);
		tvTextView.getLayoutParams().width = LayoutParams.MATCH_PARENT;
		tvTextView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
		tvTextView.setGravity(Gravity.CENTER);
		
		SettingData.getInstance().saveData();
        Intent intent = new Intent(this, ScreenListenerService.class);
        intent.putExtra("StartMode", ScreenListenerService.START_RESET_ALARM);
        startService(intent);
	}
	
}
