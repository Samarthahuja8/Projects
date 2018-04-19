package com.app.farmerswikki.service;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.app.farmerswikki.R;

import java.net.HttpURLConnection;

/**
 * Created by ORBITWS19 on 19-Jul-2017.
 */

public class ErrorResponse {

    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     *
     * @param error
     */

    public String onErrorResponse(VolleyError error, AppCompatActivity appCompatActivity) {


        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
              appCompatActivity.getResources().getString(R.string.bad_url_request);
        }


        return "";

    }

}
