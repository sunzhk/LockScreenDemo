package com.sunzhk.lockscreendemo.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.sunzhk.lockscreendemo.MyApplication;

/**
 * Created by sunzhk on 2016/3/9.
 */
public class SettingData {
	
	/**
	 * SharedPreferences
	 */
	private static final String SHARED_PREFERENCES_NAME = "SettingData";
	/**
	 * 时间间隔，秒
	 */
	private int intervalMinute;
	private int intervalSecond;
	/**
	 * 休息时间，秒
	 */
	private int relaxMinute;
	private int relaxSecond;
	/**
	 * 保护图画路径
	 */
	private String backgroundImagePath;
	/**
	 * 是否为家长模式
	 */
	private boolean isParentMode;
	/**
	 * 家长模式密码
	 */
	private String parentModePassword;

	private static SettingData settingData;

	public static SettingData getInstance(){
		if(settingData == null){
			settingData = new SettingData();
		}
		return settingData;
	}

	private SettingData(){
		initData();
	}

	public void saveData(){
		SharedPreferences.Editor editor = MyApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt("intervalMinute", intervalMinute);
		editor.putInt("intervalSecond", intervalSecond);
		editor.putInt("relaxMinute", relaxMinute);
		editor.putInt("relaxSecond", relaxSecond);
		editor.putString("backgroundImagePath", backgroundImagePath);
		editor.putBoolean("isParentMode", isParentMode);
		editor.putString("parentModePassword", parentModePassword);
		editor.commit();
		MyApplication.getInstance().setInitState(true);
	}

	public void update(int intervalMinute, int intervalSecond ,int relaxMinute, int relaxSecond ,String backgroundImagePath ,boolean isParentMode ,String parentModePassword){
		this.intervalMinute = intervalMinute;
		this.intervalSecond = intervalSecond;
		this.relaxMinute = relaxMinute;
		this.relaxSecond = relaxSecond;
		this.backgroundImagePath = backgroundImagePath;
		this.isParentMode = isParentMode;
		this.parentModePassword = parentModePassword;
		saveData();
	}
	
	public boolean compare(int intervalMinute, int intervalSecond ,int relaxMinute, int relaxSecond ,String backgroundImagePath ,boolean isParentMode ,String parentModePassword) {
		// TODO Auto-generated method stub
		return this.intervalMinute == intervalMinute && 
				this.intervalSecond == intervalSecond && 
				this.relaxMinute == relaxMinute && 
				this.relaxSecond == relaxSecond && 
				((this.backgroundImagePath == null && backgroundImagePath == null) ||
					(this.backgroundImagePath != null && backgroundImagePath != null && this.backgroundImagePath.equals(backgroundImagePath))) && 
				this.isParentMode == isParentMode && 
				((this.parentModePassword == null && parentModePassword == null) ||
					(this.parentModePassword != null && parentModePassword != null && this.parentModePassword.equals(parentModePassword)));
	}

	private void initData(){
		SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		intervalMinute = sharedPreferences.getInt("intervalMinute", 3600);
		intervalSecond = sharedPreferences.getInt("intervalSecond", 3600);
		relaxMinute = sharedPreferences.getInt("relaxMinute", 60);
		relaxSecond = sharedPreferences.getInt("relaxSecond", 60);
		backgroundImagePath = sharedPreferences.getString("backgroundImagePath", "");
		isParentMode = sharedPreferences.getBoolean("isParentMode", false);
		parentModePassword = sharedPreferences.getString("parentModePassword", "");
	}

	public long getTimeInterval() {
		return (((long)intervalMinute) * 60L + (long)intervalSecond);
	}

	public long getRelaxTime() {
		return (((long)relaxMinute) * 60L + (long)relaxSecond);
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
		saveData();
	}

	public boolean isParentMode() {
		return isParentMode;
	}

	public void setParentMode(boolean parentMode) {
		isParentMode = parentMode;
		saveData();
	}

	public String getParentModePassword() {
		return parentModePassword;
	}

	public void setParentModePassword(String parentModePassword) {
		this.parentModePassword = parentModePassword;
		saveData();
	}

	public int getIntervalMinute() {
		return intervalMinute;
	}

	public void setIntervalMinute(int intervalMinute) {
		this.intervalMinute = intervalMinute;
	}

	public int getIntervalSecond() {
		return intervalSecond;
	}

	public void setIntervalSecond(int intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

	public int getRelaxMinute() {
		return relaxMinute;
	}

	public void setRelaxMinute(int relaxMinute) {
		this.relaxMinute = relaxMinute;
	}

	public int getRelaxSecond() {
		return relaxSecond;
	}

	public void setRelaxSecond(int relaxSecond) {
		this.relaxSecond = relaxSecond;
	}
}
