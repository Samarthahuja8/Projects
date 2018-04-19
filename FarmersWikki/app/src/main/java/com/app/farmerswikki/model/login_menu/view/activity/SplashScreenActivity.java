package com.app.farmerswikki.model.login_menu.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.app.farmerswikki.R;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ORBITWS19 on 29-Jul-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 201;
    private static final int REQUEST_INTERNET = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkMultiplePermission();

    }




    public boolean checkMultiplePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> listPermissionsNeeded = new ArrayList<>();

            int permissionFineLocation = ContextCompat.checkSelfPermission(SplashScreenActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionCorseLocation = ContextCompat.checkSelfPermission(SplashScreenActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
           /* int permissioncamera = ContextCompat.checkSelfPermission(SplashScreenActivity.this,
                    Manifest.permission.CAMERA);
            int permissioncall = ContextCompat.checkSelfPermission(SplashScreenActivity.this,
                   Manifest.permission.CALL_PHONE);
            int permissionSDCARD = ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionSDCARD2 = ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int readSms = ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_SMS);
           if (permissioncall != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            }*/

            if (permissionCorseLocation != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
       /*     if (permissioncamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (permissionSDCARD != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissionSDCARD2 != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_SMS);
            }*/

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(SplashScreenActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            else {
                moveToLoginPage();

            }

            return true;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissions.length == 0){
            return;
        }
        boolean allPermissionsGranted = true;
        if(grantResults.length>0){
            for(int grantResult: grantResults){
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted = false;
                    break;
                }
            }
        }

        if(!allPermissionsGranted){
            boolean somePermissionsForeverDenied = false;
            for(String permission: permissions){
               /* if (ContextCompat.checkSelfPermission(SplashScreenActivity.this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{ Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
                }*/

                if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                    ActivityCompat.requestPermissions(SplashScreenActivity.this, permissions, REQUEST_INTERNET);
                    //denied

                    Log.e("denied", permission);
                }else{
                    if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){




                        //allowed
                        Log.e("allowed", permission);
                    } else {



                        somePermissionsForeverDenied = true;
                    }
                }
            }

            if(somePermissionsForeverDenied){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })


                        .show();
            }
        } else {
            switch (requestCode) {
                case 201:{

                    moveToLoginPage();

                }
            }
        }
    }

    public void moveToLoginPage(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,LoginRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }

}


