package com.sunzhk.lockscreendemo.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunzhk.lockscreendemo.PermissionUtils.MyDeviceAdminReceiver;
import com.sunzhk.lockscreendemo.R;
import com.sunzhk.lockscreendemo.common.SettingData;

/**
 * Created by sunzhk on 2016/3/8.
 */
public class SettingActivity extends Activity {

    private EditText etMinuteInterval;
    private EditText etSecondInterval;
    private EditText etRelaxMinute;
    private EditText etRelaxSecond;
	private Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

		initPermission();

		etMinuteInterval = (EditText) findViewById(R.id.activity_setting_et_minute_interval);
		etSecondInterval = (EditText) findViewById(R.id.activity_setting_et_second_interval);
		etRelaxMinute = (EditText) findViewById(R.id.activity_setting_et_relax_minute);
		etRelaxSecond = (EditText) findViewById(R.id.activity_setting_et_relax_second);
//		etImage = (EditText) findViewById(R.id.activity_setting_et_image);
//		btSelectImage = (Button) findViewById(R.id.activity_setting_bt_select_image);
//		btParentMode = (Button) findViewById(R.id.activity_setting_bt_parent_mode);
//		etParentModePassword = (EditText) findViewById(R.id.activity_setting_et_parent_mode_password);
		btConfirm = (Button) findViewById(R.id.activity_setting_bt_confirm);
		
		initSetting();

//		btSelectImage.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				openAlbum();
//			}
//		});
		
//		btParentMode.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				btParentMode.setTag(!(Boolean) btParentMode.getTag());
//				btParentMode.setText((Boolean) btParentMode.getTag() ? "家长模式:开" : "家长模式:关");
//			}
//		});
		
		btConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				afterSettingChange();
			}
		});

    }

	private void initSetting(){
		etMinuteInterval.setText(SettingData.getInstance().getIntervalMinute() + "");
		etSecondInterval.setText(SettingData.getInstance().getIntervalSecond() + "");
		etRelaxMinute.setText(SettingData.getInstance().getRelaxMinute() + "");
		etRelaxSecond.setText(SettingData.getInstance().getRelaxSecond() + "");
//		etImage.setText(SettingData.getInstance().getBackgroundImagePath());
//		btParentMode.setTag(SettingData.getInstance().isParentMode());
//		etParentModePassword.setText(SettingData.getInstance().getParentModePassword());
//		btParentMode.setText((Boolean) btParentMode.getTag() ? "家长模式:开" : "家长模式:关");
	}

	private void afterSettingChange(){
		int minuteInterval = 0;
		int secondInterval = 0;
		int relaxMinute = 0;
		int relaxSecond = 0;
		try {
			minuteInterval = Integer.parseInt(etMinuteInterval.getText().toString());
			secondInterval = Integer.parseInt(etSecondInterval.getText().toString());
			relaxMinute = Integer.parseInt(etRelaxMinute.getText().toString());
			relaxSecond = Integer.parseInt(etRelaxSecond.getText().toString());
		}catch (Exception e){
			Toast.makeText(this, "时间设置错误", Toast.LENGTH_LONG).show();
			return;
		}
		
		if((minuteInterval * 60 + secondInterval) <= (relaxMinute * 60 + relaxSecond)){
			Toast.makeText(this, "间隔时间应该比休息时间长吧", Toast.LENGTH_LONG).show();
			return;
		}
		
		SettingData data = SettingData.getInstance();
		data.setIntervalMinute(minuteInterval);
		data.setIntervalSecond(secondInterval);
		data.setRelaxMinute(relaxMinute);
		data.setRelaxSecond(relaxSecond);
		
		startActivity(new Intent(this, ChooseActivity.class));
		finish();
	}
	
//	private void openAlbum(){
//		
//		Intent intent=new Intent();  
//        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data  
//        intent.setType("image/*");//从所有图片中进行选择  
//        startActivityForResult(intent, 10086);
//	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		if(requestCode == 10086 && resultCode == RESULT_OK && data != null){
//			
//			Uri uri = data.getData();
//			
//			final String scheme = uri.getScheme();
//		    String result = null;
//		    if (scheme == null )
//		    	result = uri.getPath();
//		    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
//		    	result = uri.getPath();
//		    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
//		        Cursor cursor = getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
//		        if ( null != cursor ) {
//		            if ( cursor.moveToFirst() ) {
//		                int index = cursor.getColumnIndex( ImageColumns.DATA );
//		                if ( index > -1 ) {
//		                	result = cursor.getString( index );
//		                }
//		            }
//		            cursor.close();
//		        }
//		    }
//            etImage.setText(result);
//		}
//	}
	

	private void initPermission(){
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		ComponentName mDeviceComponentName = new ComponentName(this, MyDeviceAdminReceiver.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "本应用运行所需必备权限");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
}
