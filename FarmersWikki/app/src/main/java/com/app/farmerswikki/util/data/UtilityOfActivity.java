package com.app.farmerswikki.util.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.farmerswikki.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * Created by ORBITWS19 on 20-May-2017.
 */

public class UtilityOfActivity {

    AppCompatActivity appCompatActivity;
    ProgressDialog progressDialog;


    public UtilityOfActivity(AppCompatActivity appCompatActivity){
        this.appCompatActivity=appCompatActivity;
    }

    public static String loadJSONFromAsset(String jsonName, Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public  Bitmap getIconInBitmap(int drawable) {
        int height = appCompatActivity.getResources().getInteger(R.integer.marker_height_70);
        int width = appCompatActivity.getResources().getInteger(R.integer.marker_width_60);
        BitmapDrawable bitmapdraw = (BitmapDrawable) appCompatActivity.getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    public static void moveFragment(Fragment fragment, String backStack, int containerId,AppCompatActivity appCompatActivity){
        FragmentManager fragmentManager = ((AppCompatActivity) appCompatActivity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        if (backStack != null) {
            fragmentTransaction.addToBackStack(backStack);
        }
        fragmentTransaction.commit();
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void moveFragmentFromChild(Fragment parentfragment, Fragment childfragment, String backStack, int containerId) {

        if (parentfragment != null && childfragment != null) {

            FragmentManager fragmentManager = parentfragment.getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, childfragment);
            if (backStack != null) {
                fragmentTransaction.addToBackStack(backStack);
            }
            fragmentTransaction.commit();
        }
    }

    public void progressDialogShow(String title) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(appCompatActivity);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);


        }
        progressDialog.setMessage(title);
        progressDialog.show();

    }

    public void progresDissmiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    public static Double getDeviceHeightAndWidthInInch(AppCompatActivity appCompatActivity){
        DisplayMetrics dm = new DisplayMetrics();
        appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return Math.sqrt(x+y);
    }

    public static void manageHandler(final AppCompatActivity appCompatActivity,final int TIME_IN_SECS){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                appCompatActivity.finish();
            }
        }, TIME_IN_SECS);
    }


    public static long getTimeDifferenceInMinutes(long previousTimeInMintutes){
        Calendar cal = Calendar.getInstance();
        long currentTime=cal.getTimeInMillis();
        long currentTimeInMinutes = (currentTime / 1000)  / 60;
      //  System.out.println(minutes);
        long diffInMinutes=currentTimeInMinutes-previousTimeInMintutes;
        if(diffInMinutes>20){
                PrefrenceFile.getInstance().setLong("PreviousTimeInMinutes", currentTimeInMinutes);
        }

        return diffInMinutes;

      //  System.out.println(diff);
    }


    public static String formatTemperature(String tempKelvin) {

        double d=Double.parseDouble(tempKelvin);
        double val= d-273.15;
        return String.format( "%.0f", val) ;
    }

}
