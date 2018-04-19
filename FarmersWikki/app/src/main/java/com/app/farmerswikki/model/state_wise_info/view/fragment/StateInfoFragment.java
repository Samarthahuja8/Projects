package com.app.farmerswikki.model.state_wise_info.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.farmerswikki.R;
import com.app.farmerswikki.model.state_wise_info.pojo.StateWiseDataResponse;
import com.app.farmerswikki.model.weather.view.fragment.WeatherNewsFragment;
import com.app.farmerswikki.service.IwebService;
import com.app.farmerswikki.service.WebService;
import com.app.farmerswikki.util.custom.TextViewBold;
import com.app.farmerswikki.util.custom.TextViewRegular;
import com.app.farmerswikki.util.data.Constant;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.google.gson.GsonBuilder;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.util.List;

/**
 * Created by ORBITWS19 on 31-May-2017.
 */

public class StateInfoFragment extends Fragment implements IwebService {

    View view;
    String stateName;
    String url;
    StateWiseDataResponse stateWiseDataResponse;
    TextView aboutState,stateNameTv;
    NestedScrollView nestedScrollViewLayout;
    LinearLayout krishiVigyanKendraLinearLayout,childLinearLayoutScrollView,childLinearLayoutHorizontalScrollView;
    boolean flag=true;
    String response;
    GoogleProgressBar circularProgressBarStateInfoFrag;
    LayoutInflater inflater ;
    FloatingActionButton weatherPredictButton;
    String cityId=null;
    AppBarLayout appBarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().findViewById(R.id.parent_container).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.app_main_background));
        view=inflater.inflate(R.layout.state_info_layout,container,false);
        Bundle bundle=getArguments();
        if(bundle.getString("stateName")!=null){
            stateName=bundle.getString("stateName");
        }
        url= Constant.stateInfoUrl;

        setView();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getStateInfoResponse();
        weatherPredictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new WeatherNewsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("CityId",cityId);
                Toast.makeText(getActivity(),cityId,Toast.LENGTH_LONG).show();
                fragment.setArguments(bundle);
                //getActivity().findViewById(R.id.parent_container).setForeground(ContextCompat.getDrawable(getActivity(),R.drawable.gradient_grey_and_white));
                UtilityOfActivity.moveFragment(fragment,"WeatherNewsFragment",R.id.container,(AppCompatActivity)getActivity());
            }
        });

    }

    public void setView(){
        appBarLayout=(AppBarLayout) view.findViewById(R.id.appBarLayout);
        nestedScrollViewLayout=(NestedScrollView) view.findViewById(R.id.nestedScrollViewLayout);
        aboutState=(TextViewRegular) view.findViewById(R.id.aboutState);
        stateNameTv=(TextViewBold) view.findViewById(R.id.stateNameTv);
        circularProgressBarStateInfoFrag=(GoogleProgressBar) view.findViewById(R.id.circularProgressBarStateInfoFrag);
        try{
            circularProgressBarStateInfoFrag.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
            //circularProgressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            Log.d("circularProgressBar", "onCreate() returned: " + e );
        }
        childLinearLayoutScrollView=(LinearLayout) view.findViewById(R.id.childLinearLayoutScrollView);
        childLinearLayoutHorizontalScrollView=(LinearLayout) view.findViewById(R.id.childLinearLayoutHorizontalScrollView);
        weatherPredictButton=(FloatingActionButton) view.findViewById(R.id.weatherPredictButton);
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void getStateInfoResponse(){
        try {
            WebService webService=new WebService();
            webService.GetRequestThroughVolley(StateInfoFragment.this,null,url, Request.Method.GET,"GetStateInfoResponse");
        }

        catch (Exception e) {
            Log.d("", "");
        }
    }

    @Override
    public void Result(String response,String calledBy) {
        this.response=response;
        switch (calledBy){
            case "GetStateInfoResponse":{
                new AsyncCaller().execute();
                break;
            }


        }

    }


     public void setData(StateWiseDataResponse stateWiseDataResponse){
         //nestedScrollViewLayout.setVisibility(View.VISIBLE);

         for(StateWiseDataResponse.StateInfoList  stateInfoList:stateWiseDataResponse.stateInfoList){
             if(stateName.equalsIgnoreCase(stateInfoList.stateName)){
                 cityId=stateInfoList.id;
                 aboutState.setText(stateInfoList.about.replaceAll("\\s+", " "));//removes the extra spaces from the string
                 stateNameTv.setText(stateInfoList.stateName.toUpperCase());//removes the extra spaces from the string
                 setKrishiVigyanKendras(stateInfoList.vigyanKendra);
                 setCropsDetails(stateInfoList.crops);
                 setViewVisibilityVisible();
             }
         }
     }

     public void setKrishiVigyanKendras(List<StateWiseDataResponse.StateInfoList.VigyanKendra> vigyanKendrasList){
         for(StateWiseDataResponse.StateInfoList.VigyanKendra vigyanKendra:vigyanKendrasList){
             View view = inflater.inflate(R.layout.layout_kirshi_vigyan_kendra, null);
             TextView krishiVigyanKendraValue=(TextView) view.findViewById(R.id.krishiVigyanKendraValue);
             String stringAfterRemovingExtraSpaces=vigyanKendra.location.replaceAll("\\s+", " ");
             String locationAfterCommaSplitted="";
             String arrLocation[]=stringAfterRemovingExtraSpaces.split(",");
             for(String locationName:arrLocation){
                 locationAfterCommaSplitted=locationAfterCommaSplitted+locationName+"\n";
             }
             krishiVigyanKendraValue.setText(locationAfterCommaSplitted);
             childLinearLayoutScrollView.addView(view);
         }
     }


    public void setCropsDetails(List<StateWiseDataResponse.StateInfoList.Crops>  cropsList){
        int pos=1;
        for(StateWiseDataResponse.StateInfoList.Crops cropDetails:cropsList){
            View view = inflater.inflate(R.layout.layout_crops_details, null);
            TextView cropsNameTv=(TextView) view.findViewById(R.id.cropsNameTv);
            TextView cultivationSeasonTv=(TextView) view.findViewById(R.id.cultivationSeasonTv);

            cropsNameTv.setText(String.valueOf(pos++)+". "+cropDetails.name);
            cultivationSeasonTv.setText(cropDetails.cultivationSeason);
            childLinearLayoutHorizontalScrollView.addView(view);
        }
    }




    private class AsyncCaller extends AsyncTask<Void, Void, StateWiseDataResponse>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected StateWiseDataResponse doInBackground(Void... params) {

            stateWiseDataResponse=new GsonBuilder().create().fromJson(response,StateWiseDataResponse.class);

            return stateWiseDataResponse;
        }

        @Override
        protected void onPostExecute(StateWiseDataResponse result) {
            super.onPostExecute(result);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setData(stateWiseDataResponse);
                    circularProgressBarStateInfoFrag.setVisibility(View.GONE);


                }
            }, 1000);
            //this method will be running on UI thread

        }

    }



    public void setViewVisibilityVisible(){
        appBarLayout.setVisibility(View.VISIBLE);
        weatherPredictButton.setVisibility(View.VISIBLE);
        nestedScrollViewLayout.setVisibility(View.VISIBLE);
    }









}
