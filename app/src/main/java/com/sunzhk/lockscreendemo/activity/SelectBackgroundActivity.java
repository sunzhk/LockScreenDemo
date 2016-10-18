package com.sunzhk.lockscreendemo.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sunzhk.lockscreendemo.R;
import com.sunzhk.lockscreendemo.common.SettingData;

public class SelectBackgroundActivity extends Activity {
	
	private EditText etImage;
    private Button btSelectImage;
	private Button btConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_background);
		etImage = (EditText) findViewById(R.id.activity_select_background_et_path);
		btSelectImage = (Button) findViewById(R.id.activity_select_background_bt_select);
		btConfirm = (Button) findViewById(R.id.activity_select_background_bt_confirm);
		
		etImage.setText(SettingData.getInstance().getBackgroundImagePath());
		
		btSelectImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openAlbum();
			}
		});
		
		btConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String path = etImage.getText().toString();
				if(!TextUtils.isEmpty(path)){
					SettingData.getInstance().setBackgroundImagePath(path);
					Intent intent = new Intent(SelectBackgroundActivity.this, ChooseActivity.class);
					intent.putExtra("ChooseMode", ChooseActivity.CHOOSE_MODE_PARENTS);
					startActivity(intent);
					finish();
				}
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 10086 && resultCode == RESULT_OK && data != null){
			
			Uri uri = data.getData();
			
			final String scheme = uri.getScheme();
		    String result = null;
		    if (scheme == null )
		    	result = uri.getPath();
		    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
		    	result = uri.getPath();
		    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
		        Cursor cursor = getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
		        if ( null != cursor ) {
		            if ( cursor.moveToFirst() ) {
		                int index = cursor.getColumnIndex( ImageColumns.DATA );
		                if ( index > -1 ) {
		                	result = cursor.getString( index );
		                }
		            }
		            cursor.close();
		        }
		    }
            etImage.setText(result);
		}
	}
	
	private void openAlbum(){
		
		Intent intent=new Intent();  
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data  
        intent.setType("image/*");//从所有图片中进行选择  
        startActivityForResult(intent, 10086);
	}

}
