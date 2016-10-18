package com.sunzhk.lockscreendemo;

import com.sunzhk.lockscreendemo.common.SettingData;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sunzhk on 2016/3/8.
 */
public class ScreenListenerService extends Service {
	
	public static final int START_NORMAL = 0;
	public static final int START_RESTART = -1;
	public static final int START_RESET_ALARM = 1;
	public static final int START_CONTROL_MEDIA = 2;
	public static final int START_STOP = 4;
	
	public static final int MEDIA_CONTROL_STOP = 0;
	public static final int MEDIA_CONTROL_START = 1;
	public static final int MEDIA_CONTROL_PAUSE = 2;
	public static final int MEDIA_CONTROL_START_OR_PAUSE = 3;
	
	private MediaPlayer mediaPlayer;
	
//    private LockScreenListener lockScreenListener;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		initPermission();
//		startAlarm();
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(intent != null){
        	
        	switch (intent.getIntExtra("StartMode", START_NORMAL)) {
			case START_RESTART:
				
				break;
			case START_NORMAL:
//				restartAlarm();
				break;
			case START_RESET_ALARM:
        		restartAlarm();
        		Toast.makeText(this, "修改设置成功", Toast.LENGTH_LONG).show();
				break;
			case START_STOP:
				stopAlarm();
				break;
			case START_CONTROL_MEDIA:
				switch (intent.getIntExtra("MediaControl", MEDIA_CONTROL_STOP)) {
				case MEDIA_CONTROL_STOP:
					if(mediaPlayer != null){
						try {
							mediaPlayer.stop();
							mediaPlayer = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case MEDIA_CONTROL_START:
					if(mediaPlayer == null){
						mediaPlayer = MediaPlayer.create(this, R.raw.eye_exercises);
					}else if(!mediaPlayer.isPlaying()){
						try {
							mediaPlayer.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case MEDIA_CONTROL_PAUSE:
					if(mediaPlayer != null){
						try {
							mediaPlayer.pause();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case MEDIA_CONTROL_START_OR_PAUSE:

					if(mediaPlayer == null){
						mediaPlayer = MediaPlayer.create(this, R.raw.eye_exercises);
					}
					if(mediaPlayer.isPlaying()){
						try {
							mediaPlayer.pause();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						try {
							mediaPlayer.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
        }
        return START_STICKY;
    }
    
    public void stopAlarm(){
    	Intent sendIntent = new Intent(RelaxBroadcastReceiver.ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
    	SystemAlarmUtils.cancelAlarm(this, pendingIntent);
    }

    public void restartAlarm(){
    	Intent sendIntent = new Intent(RelaxBroadcastReceiver.ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
		SystemAlarmUtils.cancelAlarm(this, pendingIntent);
		SystemAlarmUtils.setAlarm(this,pendingIntent, 
									System.currentTimeMillis() + SettingData.getInstance().getTimeInterval() * 1000, 
									SettingData.getInstance().getTimeInterval() * 1000);
    }
    
	public void startAlarm(){
		Intent sendIntent = new Intent(RelaxBroadcastReceiver.ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
		SystemAlarmUtils.setAlarm(this,pendingIntent, 
									System.currentTimeMillis() + SettingData.getInstance().getTimeInterval() * 1000, 
									SettingData.getInstance().getTimeInterval() * 1000);
//		Log.e("ScreenListenerService", "alarm is start by :"+SettingData.getInstance().getTimeInterval()+", "+SettingData.getInstance().getRelaxTime());
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ScreenListenerService", "service is stop,but i will back!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
