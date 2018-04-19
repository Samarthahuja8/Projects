package com.app.farmerswikki;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ajeet Web-Shuttle on 01-04-2016.
 */
public class MPermission {
    private static final int REQUEST_CODE_INTERNET = 110;
    private static final int PERMISSION_REQUEST_CODE_GPS = 111;
    public static final int PERMISSION_REQUEST_CODE_CONTACT = 112;
    public static final int PERMISSION_REQUEST_CODE_CAMARA = 113;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 201;
    Context context;
    Activity activity;

    public MPermission(Context context) {
        this.context = context;
    }

    public MPermission(Activity activity) {

        this.activity = activity;
    }


    public boolean checkPermissionGenrec(Fragment fragment, final String manifestPermissionRequestState, final int PERMISSION_REQUEST_PHONE_STATE) {

        boolean contactPermission;
        int currentversion = Build.VERSION.SDK_INT;
        if (currentversion >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(context, manifestPermissionRequestState);
            if (result != PackageManager.PERMISSION_GRANTED) {
                if (fragment.shouldShowRequestPermissionRationale(manifestPermissionRequestState)) {
                    fragment.requestPermissions(new String[]{manifestPermissionRequestState}, PERMISSION_REQUEST_PHONE_STATE);
                    {

                    }
                    contactPermission = false;
                } else {
                    fragment.requestPermissions(new String[]{manifestPermissionRequestState}, PERMISSION_REQUEST_PHONE_STATE);
                    contactPermission = false;
                }

            } else {

                contactPermission = true;

            }
        } else {
// below of M version
            contactPermission = true;

        }

        return contactPermission;
    }

    public boolean checkMultiplePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionFineLocation = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionCorseLocation = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            int permissionSDCARD = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionSDCARD2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionCorseLocation != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (permissionSDCARD != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissionSDCARD2 != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;
        } else {
            return true;
        }

    }

    public boolean checkPermissionGenrec(AppCompatActivity activity, final String manifestPermissionRequestState, final int PERMISSION_REQUEST_PHONE_STATE) {
        boolean contactPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(activity, manifestPermissionRequestState);
            if (result != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, manifestPermissionRequestState)) {
                    activity.requestPermissions(new String[]{manifestPermissionRequestState}, PERMISSION_REQUEST_PHONE_STATE);
                    contactPermission = false;
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{manifestPermissionRequestState}, PERMISSION_REQUEST_PHONE_STATE);
                    contactPermission = false;
                }
            } else {
                contactPermission = true;
            }
        } else {
            contactPermission = true;
        }

        return contactPermission;
    }
}

