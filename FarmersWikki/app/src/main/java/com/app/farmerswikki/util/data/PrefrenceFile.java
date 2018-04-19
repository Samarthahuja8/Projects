package com.app.farmerswikki.util.data;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.service.AppSingleton;
import com.google.gson.GsonBuilder;


/**
 * Created by Android-1 on 24-11-2015.
 */
public class PrefrenceFile {

    private String language;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor ed;
    private NotificationResponse notificationResponse;

    private static PrefrenceFile mInstance = null;

    private PrefrenceFile(Context context) {
        super();
    }

    public static PrefrenceFile getInstance() {
        Context ctx = AppSingleton.getInstance().getApplicationContext();
        if (mInstance == null) {
            mInstance = new PrefrenceFile(ctx);
        }
        prefs = ctx.getSharedPreferences("MyPrefrence", Context.MODE_PRIVATE);
        ed = prefs.edit();
        return mInstance;
    }

    public void clearData() {
        ed.clear();
        ed.commit();
    }

    public void setBoolean(String key, boolean value) {
        ed.putBoolean(key, value);
        ed.commit();
    }

    public void setInt(String key, int value) {
        ed.putInt(key, value);
        ed.commit();
    }

    public void setString(String key, String value) {
        ed.putString(key, value);
        ed.commit();
    }


    public void setLong(String key, Long value) {
        ed.putLong(key, value);
        ed.commit();
    }

    public void setFloat(String key, float value) {
        ed.putFloat(key, value);
        ed.commit();
    }

    public Long getLong(String key) {
        return prefs.getLong(key,0L);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public String getString(String key) {
        return prefs.getString(key, null);
    }

    public int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    public float getFloat(String key) {
        return prefs.getFloat(key, 0.0f);
    }



    public void deleteRecord(String key) {
        ed.remove(key);
        ed.commit();
    }

    public void setNotificationResponseToPrefFile(NotificationResponse notificationResponse,String notificationId){
        if(Integer.parseInt(notificationId)>5){
            removeAllKeys();
        }
        else {
            this.notificationResponse = notificationResponse;
            addNotificationToPrefFile();
        }
    }

    public NotificationResponse getNotificationResponseFromPrefFile(int notificationId){

         String json=PrefrenceFile.getInstance().getString(String.valueOf(notificationId));
         NotificationResponse notificationResponse=new GsonBuilder().create().fromJson(json,NotificationResponse.class);
         return  notificationResponse;
    }

    public void addNotificationToPrefFile(){
        String json = new GsonBuilder().create().toJson(notificationResponse);
        for(int i=0;i<=5;i++){
            if(PrefrenceFile.getInstance().getString(String.valueOf(i))==null){
                PrefrenceFile.getInstance().setString(String.valueOf(i),json);
                break;
            }
        }
    }

    public void removeAllKeys(){
        for(int i=1;i<=5;i++){
            ed.remove(String.valueOf(i));
            ed.commit();
        }
    }

    public static int countNotifications(){
        int count=0;
        for(int i=0;i<=5;i++){
            if(PrefrenceFile.getInstance().getString(String.valueOf(i))==null){
                 break;
            }
            else {
                count=count+1;
            }
        }
        return count;
    }

}
