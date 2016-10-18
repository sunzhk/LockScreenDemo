package com.sunzhk.lockscreendemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sunzhk.lockscreendemo.R;
import com.sunzhk.lockscreendemo.common.SettingData;

public class ChooseActivity extends Activity implements OnClickListener {
	
	public static final int CHOOSE_MODE_PARENTS = 0;
	public static final int CHOOSE_MODE_BACKGROUND = 1;
	
	private int chooseMode;
	private Button btCancel;
	private Button btConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		chooseMode = getIntent().getIntExtra("ChooseMode", CHOOSE_MODE_BACKGROUND);
		
		TextView tvTitle = (TextView) findViewById(R.id.activity_choose_tv_title);
		switch (chooseMode) {
		case CHOOSE_MODE_PARENTS:
			tvTitle.setText("是否开启家长模式");
			break;
		case CHOOSE_MODE_BACKGROUND:
			tvTitle.setText("是否设置屏保");
			break;
		default:
			break;
		}
		
		btCancel = (Button) findViewById(R.id.activity_choose_bt_cancel);
		btConfirm = (Button) findViewById(R.id.activity_choose_bt_confirm);

		btCancel.setOnClickListener(this);
		btConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (chooseMode) {
		case CHOOSE_MODE_PARENTS:
			if(v.getId() == R.id.activity_choose_bt_cancel){
				SettingData.getInstance().setParentMode(false);
				startActivity(new Intent(this, FinalActivity.class));
			}else{
				SettingData.getInstance().setParentMode(true);
				startActivity(new Intent(this, SetParentModePasswordActivity.class));
			}
			break;
		case CHOOSE_MODE_BACKGROUND:
			if(v.getId() == R.id.activity_choose_bt_cancel){
				Intent intent = new Intent(this, ChooseActivity.class);
				intent.putExtra("ChooseMode", CHOOSE_MODE_PARENTS);
				startActivity(intent);
			}else{
				startActivity(new Intent(this, SelectBackgroundActivity.class));
			}
			break;
		default:
			break;
		}
		finish();
	}
	
}
