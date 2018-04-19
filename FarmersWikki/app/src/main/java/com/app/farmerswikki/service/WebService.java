package com.app.farmerswikki.service;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.farmerswikki.util.data.UtilityOfActivity;

import org.json.JSONObject;

/**
 * Created by ORBITWS19 on 24-May-2017.
 */

public class WebService {

    int MY_SOCKET_TIMEOUT_MS=120000;
    public  void GetRequestThroughVolley(final Fragment fragment, JSONObject jsonObject, String url, int requestCode, final String calledBy){


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getRequestCode(requestCode), url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                IwebService iwebService= (IwebService) fragment;
                iwebService.Result(response.toString(),calledBy);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.toString());
                Toast.makeText(fragment.getActivity(),error.getMessage().toLowerCase().toString(),Toast.LENGTH_LONG).show();
                fragment.getActivity().onBackPressed();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance().addToRequestQueue(jsonObjectRequest, "get data");

    }



    public int getRequestCode(int requestCode){

        if(requestCode==Request.Method.GET){
            return Request.Method.GET;
        }
        else
        {
            return Request.Method.POST;
        }

    }

}
