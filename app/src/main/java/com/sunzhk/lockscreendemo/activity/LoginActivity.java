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

public class LoginActivity extends Activity {
	
	private EditText etPassword;
	private Button btConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etPassword = (EditText) findViewById(R.id.activity_login_et_password);
		btConfirm = (Button) findViewById(R.id.activity_login_bt_confirm);
		
		btConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = etPassword.getText().toString();
				if(!TextUtils.isEmpty(password) && password.equals(SettingData.getInstance().getParentModePassword())){
					startActivity(new Intent(LoginActivity.this, SettingActivity.class));
					finish();
				}
			}
		});
		
	}

}
