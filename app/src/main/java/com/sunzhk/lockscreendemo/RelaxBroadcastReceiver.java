package com.sunzhk.lockscreendemo;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sunzhk.lockscreendemo.common.SettingData;

/**
 * Created by sunzhk on 2016/3/10.
 */
public class RelaxBroadcastReceiver extends BroadcastReceiver {

	public static String ACTION = "ACTION_RELAX";

	private static boolean lockFlag = false;
	
	private static View lockView;
	
	private static CountdownThread mTimer;
	
//	private MediaPlayer mp;
	
	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		if(intent.getAction().equals(ACTION)){
			Log.e("RelaxBroadcastReceiver", "get broadcost");
			this.context = context;
			prepareRelax(context);
		}

	}

	private void prepareRelax(Context context){
		Log.e("RelaxBroadcastReceiver", "start relax");
		//获取电源管理器对象
		PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
		//获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"RelaxBroadcastReceiver");
		if(!pm.isScreenOn()){
			//点亮屏幕
			wl.acquire();
		}
		if(lockFlag){
			stopRelax(context);
		}
		startRelax(context);
//
		Log.e("RelaxBroadcastReceiver", "over");
	}
	
	private void startRelax(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		wmParams.flags = 1280;
		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.gravity = Gravity.CENTER;
		windowManager.addView(getRootView(context), wmParams);
		startTimer(context);
		lockFlag = true;
	}
	
	private void stopRelax(Context context){
		controlMidea(ScreenListenerService.MEDIA_CONTROL_STOP);
		stopTimer();
		KeyguardManager km= (KeyguardManager) MyApplication.getInstance().getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		kl.disableKeyguard();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		try {
			windowManager.removeView(lockView);
		} catch (Exception e) {
			// TODO: handle exception
		}
		lockFlag = false;
	}
	
//	private void stopMidea(){
//		if(mp != null){
//			Log.e("RelaxBroadcastReceiver", "try to stop mp!");
//			try {
//				mp.stop();
//				mp.release();
//				mp = null;
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				Log.e("RelaxBroadcastReceiver", e.getMessage());
//			}
//			Log.e("RelaxBroadcastReceiver", "MP is " + mp);
//		}
//	}
	
	private void controlMidea(int controler){
		Intent intent = new Intent(context, ScreenListenerService.class);
		intent.putExtra("StartMode", ScreenListenerService.START_CONTROL_MEDIA);
		intent.putExtra("MediaControl", controler);
		context.startService(intent);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private View getRootView(final Context context){
		if(lockView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			lockView = inflater.inflate(R.layout.lock_screen, null);
			
			lockView.findViewById(R.id.lock_screen_tv_time);
			lockView.findViewById(R.id.lock_screen_bt_exit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(SettingData.getInstance().isParentMode()){
						lockView.findViewById(R.id.lock_screen_ll_main).setVisibility(View.GONE);
						lockView.findViewById(R.id.lock_screen_ll_parent_mode).setVisibility(View.VISIBLE);
						((EditText) lockView.findViewById(R.id.lock_screen_et_parent_mode_password)).setText("");
					}else{
						stopRelax(context);
					}
				}
			});

			lockView.findViewById(R.id.lock_screen_bt_confirm).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String password = ((EditText) lockView.findViewById(R.id.lock_screen_et_parent_mode_password)).getText().toString();
					
					if(!TextUtils.isEmpty(password) && 
						password.equals(SettingData.getInstance().getParentModePassword())){
						stopRelax(context);
					}
					
				}
			});
			
			lockView.findViewById(R.id.lock_screen_bt_eye_exercises).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					controlMidea(ScreenListenerService.MEDIA_CONTROL_START_OR_PAUSE);
//					if(mp == null){
//						mp = MediaPlayer.create(context, R.raw.eye_exercises);
//					}
//					if(!mp.isPlaying()){
//						try {
//							mp.start();
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}else {
//						try {
//							mp.stop();
//							mp.release();
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
				}
			});
			
		}

		lockView.findViewById(R.id.lock_screen_ll_main).setVisibility(View.VISIBLE);
		lockView.findViewById(R.id.lock_screen_ll_parent_mode).setVisibility(View.GONE);
		String path = SettingData.getInstance().getBackgroundImagePath();
		if(!TextUtils.isEmpty(path)){
			lockView.setBackground(new BitmapDrawable(context.getResources(), path));
				
		}
		return lockView;
	}
	
	
	private void startTimer(Context context){
		if(mTimer != null){
			mTimer.cancel(true);
		}
		mTimer = new CountdownThread(context);
		mTimer.execute();
	}
	
	private void stopTimer(){
		if(mTimer != null){
			mTimer.cancel(true);
			mTimer = null;
		}
	}
	
	/**
	 * 倒计时线程
	 * @author sunzhk
	 *
	 */
	class CountdownThread extends AsyncTask<Void, String, Void>{
		
		private Context context;
		
		public CountdownThread(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.e("RelaxBroadcastReceiver", "timer is start");
			long relaxTime = SettingData.getInstance().getRelaxTime();
			int minute = (int) (relaxTime / 60);
			int second = (int) (relaxTime % 60);
			
			long startTime = System.currentTimeMillis();
			long lastTime = startTime;
			long tempTime;
			
			StringBuilder stringBuilder = new StringBuilder();

			showTimer(minute, second, stringBuilder);
			
			while(((tempTime = System.currentTimeMillis()) - startTime) < relaxTime * 1000L){
				if(tempTime - lastTime >= 999L){
					lastTime = tempTime;
					if(second == 0){
						second = 59;
						minute--;
					}else{
						second--;
					}
					showTimer(minute, second, stringBuilder);
					
				}
			}
			stopRelax(context);
			return null;
		}

		private void showTimer(int minute, int second, StringBuilder stringBuilder){
			stringBuilder.append(minute);
			stringBuilder.append("分 ");
			stringBuilder.append(second);
			stringBuilder.append("秒");
			publishProgress(stringBuilder.toString());
			stringBuilder.delete(0, stringBuilder.length());
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			if(lockView == null){
				return;
			}
			((TextView) lockView.findViewById(R.id.lock_screen_tv_time)).setText(values[0]);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			if(mp != null){
//				mp.stop();
//				mp.release();
//				mp = null;
//			}
//			stopRelax(context);
//			stopMidea();
			Log.e("RelaxBroadcastReceiver", "timer is over");
		}
	}
	
}
