package com.sunzhk.lockscreendemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sunzhk.lockscreendemo.R;
import com.sunzhk.lockscreendemo.common.SettingData;

public class SetParentModePasswordActivity extends Activity {

	private EditText etParentModePassword;
	private Button btConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_parentmode_password);
		etParentModePassword = (EditText) findViewById(R.id.activity_set_parentmode_password_et_password);
		btConfirm = (Button) findViewById(R.id.activity_set_parentmode_password_bt_confirm);
		
		etParentModePassword.setText(SettingData.getInstance().getParentModePassword());
		
		btConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = etParentModePassword.getText().toString();
				if(!TextUtils.isEmpty(password)){
					SettingData.getInstance().setParentModePassword(password);
					startActivity(new Intent(SetParentModePasswordActivity.this, FinalActivity.class));
					finish();
				}
			}
		});
	}

}
