package com.app.farmerswikki.service;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

public class AppSingleton extends Application {

    public static final String TAG = AppSingleton.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppSingleton mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        /***************************************/
        /* To handle Global Exceptions in the App*/
        /***************************************/
        // Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(getApplicationContext()));
        /***************************************/
        /* To handle GlobalException in the App*/
        /***************************************/
    }

    public static synchronized AppSingleton getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(JsonRequest<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
