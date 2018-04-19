package com.app.farmerswikki.model.weather.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.android.volley.Request;
import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.adapter.NotificationsAdapter;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.state_wise_info.view.fragment.StateInfoFragment;
import com.app.farmerswikki.model.weather.adapter.WeatherNewsAdapter;
import com.app.farmerswikki.model.weather.pojo.WeatherDetails;
import com.app.farmerswikki.model.weather.pojo.WeatherDetailsResponse;
import com.app.farmerswikki.service.IwebService;
import com.app.farmerswikki.service.WebService;
import com.app.farmerswikki.util.custom.TextViewLatoThin;
import com.app.farmerswikki.util.data.Constant;
import com.app.farmerswikki.util.data.PrefrenceFile;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.google.gson.GsonBuilder;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ORBITWS19 on 03-Aug-2017.
 */

public class WeatherNewsFragment extends Fragment implements IwebService {

    View view;
    WeatherDetailsResponse weatherDetailsResponse;
    String response, weatherApiUrl;
    RecyclerView weatherNewsRecyclerView;
    GoogleProgressBar circularProgressBarWeatherNewsFrag;
    int CityId;
    DatabaseHandler databaseHandler;
    List<WeatherDetails> weatherDetailsList;
    boolean flag = false;
    int size = 0;
    TextViewLatoThin textViewCurrentTemp, textViewMaxTemp, textViewMinTemp, textViewFeelsLike, textViewDesc, textViewLastUpdated;
    ImageView imageViewDesc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.parent_container).setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.weather_app_background));
        view = inflater.inflate(R.layout.weather_details_layout, container, false);
        setView();
        weatherDetailsList = new ArrayList<>();
        try {
            circularProgressBarWeatherNewsFrag.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
            //circularProgressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.d("circularProgressBar", "onCreate() returned: " + e);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        this.CityId = Integer.parseInt(bundle.getString("CityId"));
        this.weatherApiUrl = Constant.weatherApiUrl + CityId + Constant.AppIdOpenWeather + "&cnt=16";
        this.databaseHandler = new DatabaseHandler(getActivity());
        this.size = databaseHandler.getWeatherDetailsList(CityId).size();
        getWeatherDataResponse();
    }


    public void getWeatherDataResponse() {

        long previousTimeInMinutes = PrefrenceFile.getInstance().getLong("PreviousTimeInMinutes");
        long diffInMinutes = UtilityOfActivity.getTimeDifferenceInMinutes(previousTimeInMinutes);

        if (size == 0 || previousTimeInMinutes == 0L || diffInMinutes > 20) {
            if (diffInMinutes > 20 && previousTimeInMinutes != 0L & size != 0) {
                databaseHandler.deleteWeatherData(this.CityId);
            }
            try {
                Log.d("RequestApi", "TRUE");
                WebService webService = new WebService();
                webService.GetRequestThroughVolley(WeatherNewsFragment.this, null, this.weatherApiUrl, Request.Method.GET, "WeatherDataResponse");

            } catch (Exception e) {
                Log.d("", "");
            }
        } else {
            Log.d("RequestApi", "FALSE");
            //setRecyclerView(this.databaseHandler.getWeatherDetailsList(this.CityId));
            setCurrentTemp(this.databaseHandler.getWeatherDetailsList(this.CityId));
            circularProgressBarWeatherNewsFrag.setVisibility(View.GONE);
        }

    }


    @Override
    public void Result(String response, String calledBy) {

        this.response = response;
        new AsyncWeatherCaller().execute();

    }


    private class AsyncWeatherCaller extends AsyncTask<Void, Void, List<WeatherDetails>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<WeatherDetails> doInBackground(Void... params) {

            weatherDetailsResponse = new GsonBuilder().create().fromJson(response, WeatherDetailsResponse.class);
            for (WeatherDetailsResponse.WeatherList weatherList : weatherDetailsResponse.list) {
                WeatherDetails weatherDetails = new WeatherDetails();
                //  String id,mornTemp,dayTemp,evenTemp,nightTemp,minTemp,maxTemp,main,description,windSpeed,humidity;
                weatherDetails.setId(weatherDetailsResponse.city.id.toUpperCase());
                weatherDetails.setMornTemp(weatherList.temp.morn);
                weatherDetails.setDayTemp(weatherList.temp.day);
                weatherDetails.setEvenTemp(weatherList.temp.eve);
                weatherDetails.setNightTemp(weatherList.temp.night);
                weatherDetails.setMinTemp(weatherList.temp.min);
                weatherDetails.setMaxTemp(weatherList.temp.max);
                weatherDetails.setMain(weatherList.weather.get(0).main.toUpperCase());
                weatherDetails.setDescription(weatherList.weather.get(0).description.toUpperCase());
                weatherDetails.setIcon(weatherList.weather.get(0).icon);
                weatherDetails.setWindSpeed(weatherList.speed);
                weatherDetails.setHumidity(weatherList.humidity);
                weatherDetails.setRefershTime(getUpdatedRefreshTime());
                databaseHandler.addWeatherDetails(weatherDetails);
                weatherDetailsList.add(weatherDetails);
            }
            return weatherDetailsList;
        }

        @Override
        protected void onPostExecute(List<WeatherDetails> weatherDetailsList) {
            super.onPostExecute(weatherDetailsList);


            //setRecyclerView(weatherDetailsList);
            setCurrentTemp(weatherDetailsList);
            circularProgressBarWeatherNewsFrag.setVisibility(View.GONE);
            //this method will be running on UI thread
        }

    }

    public void setRecyclerView(List<WeatherDetails> weatherDetailsList) {
        //weatherNewsRecyclerView=(RecyclerView) view.findViewById(R.id.weatherNewsRecyclerView);
        //WeatherNewsAdapter weatherNewsAdapter = new WeatherNewsAdapter(getActivity(),weatherDetailsList);
        // RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //weatherNewsRecyclerView.setLayoutManager(mLayoutManager);
        // weatherNewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /*weatherNewsRecyclerView.setItemViewCacheSize(20);
        weatherNewsRecyclerView.setDrawingCacheEnabled(true);
        weatherNewsRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        weatherNewsAdapter.setHasStableIds(true);*/
        // weatherNewsRecyclerView.setAdapter(weatherNewsAdapter);
    }


    public String getUpdatedRefreshTime() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("E dd/MM '  ' hh:mm:ss a");
        return ft.format(dNow).toUpperCase();
    }

    public void setCurrentTemp(List<WeatherDetails> weatherDetailsList) {

        textViewCurrentTemp.setText(UtilityOfActivity.formatTemperature(weatherDetailsList.get(0).getMaxTemp()));
        textViewMaxTemp.setText(UtilityOfActivity.formatTemperature(weatherDetailsList.get(0).getMaxTemp()));
        textViewMinTemp.setText(UtilityOfActivity.formatTemperature(weatherDetailsList.get(0).getMinTemp()));
        textViewDesc.setText(weatherDetailsList.get(0).getDescription());
        setIconToImageView(weatherDetailsList.get(0).getIcon());
        textViewLastUpdated.setText(weatherDetailsList.get(0).getRefershTime());
    }


    public void setView() {
        circularProgressBarWeatherNewsFrag = (GoogleProgressBar) view.findViewById(R.id.circularProgressBarWeatherNewsFrag);
        textViewCurrentTemp = (TextViewLatoThin) view.findViewById(R.id.textViewCurrentTemp);
        textViewMaxTemp = (TextViewLatoThin) view.findViewById(R.id.textViewMaxTemp);
        textViewMinTemp = (TextViewLatoThin) view.findViewById(R.id.textViewMinTemp);
        textViewDesc = (TextViewLatoThin) view.findViewById(R.id.textViewDesc);
        textViewLastUpdated = (TextViewLatoThin) view.findViewById(R.id.textViewLastUpdated);
        imageViewDesc = (ImageView) view.findViewById(R.id.imageViewDesc);

    }


    public void setIconToImageView(String iconDes) {

        switch (iconDes) {

            case "01d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_clear));
                break;
            }


            case "02d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_light_clouds));
                break;
            }


            case "03d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_scattered));
                // imageViewDesc.setColorFilter(R.color.grey,android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            }

            case "04d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_clouds));
                break;
            }


            case "09d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_light_rain));
                break;
            }


            case "10d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_rain));
                break;
            }


            case "11d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_storm));
                break;
            }

            case "13d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_snow));
                break;
            }

            case "50d": {
                imageViewDesc.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.art_fog));
                break;
            }


        }


    }
}
