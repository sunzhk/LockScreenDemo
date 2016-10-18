package com.sunzhk.lockscreendemo.PermissionUtils;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    /**
     * 获取设备存储的数值
     *
     * @param context
     * @return
     */
    public static SharedPreferences getDevicePreference(Context context) {
        return context.getSharedPreferences(DeviceAdminReceiver.class.getName(), 0);
    }

    // 密码的特点
    public static String PREF_PASSWORD_QUALITY = "password_quality";
    // 密码的长度
    public static String PREF_PASSWORD_LENGTH = "password_length";

    public static String PREF_MAX_FAILED_PW = "max_failed_pw";

    void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("DeviceAdminReceiver", "设备管理：可用");
        showToast(context, "设备管理：可用");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("DeviceAdminReceiver", "设备管理：不可用");
        showToast(context, "设备管理：不可用");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // TODO Auto-generated method stub
        return "从设备管理器中取消权限时提示的文本";
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("DeviceAdminReceiver", "设备管理：密码己经改变");
        showToast(context, "设备管理：密码己经改变");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("DeviceAdminReceiver", "设备管理：改变密码失败");
        showToast(context, "设备管理：改变密码失败");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("DeviceAdminReceiver", "设备管理：改变密码成功");
        showToast(context, "设备管理：改变密码成功");
    }

}
