package com.app.farmerswikki.model.map.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.app.farmerswikki.R;
import com.app.farmerswikki.model.state_wise_info.view.fragment.StateInfoFragment;
import com.app.farmerswikki.model.map.pojo.LatLngModel;
import com.app.farmerswikki.service.IwebService;
import com.app.farmerswikki.service.WebService;
import com.app.farmerswikki.util.custom.AutoCompleteAdapter;
import com.app.farmerswikki.util.data.Constant;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;


import java.util.List;

/**
 * Created by ORBITWS19 on 24-May-2017.
 */

public class FragmentMap extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener,
        LocationListener, IwebService, View.OnClickListener, GoogleMap.InfoWindowAdapter {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LatLngModel latLngModel;
    String url;

    View view;
    AutoCompleteTextView serachDealer_auto;
    private ImageView clear_butn;
    RelativeLayout relativeLayout;
    Typeface typeface;
    UtilityOfActivity utilityOfActivity;
    GoogleProgressBar circularProgressBarMap;
    LinearLayout layoutMap,mainLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().findViewById(R.id.parent_container).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.app_main_background));
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        url = Constant.cityDetailedListUrl;
        utilityOfActivity = new UtilityOfActivity((AppCompatActivity) getActivity());
        setView();

        return view;

    }

    public void setView() {
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        serachDealer_auto = (AutoCompleteTextView) view.findViewById(R.id.serachDealer_auto);
        layoutMap = (LinearLayout) view.findViewById(R.id.layoutMap);
        mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);
        clear_butn = (ImageView) view.findViewById(R.id.clear_butn);
        circularProgressBarMap = (GoogleProgressBar) view.findViewById(R.id.circularProgressBarMap);
        try{
            circularProgressBarMap.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
            //circularProgressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            Log.d("circularProgressBar", "onCreate() returned: " + e );
        }
        clear_butn.setOnClickListener(this);
        mapFrag = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "alice_reg.ttf");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getStateList();


    }


    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        utilityOfActivity.progressDialogShow("Please Wait...");
        mGoogleMap = googleMap;
        mGoogleMap.setOnInfoWindowClickListener(this);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                if (latLngModel != null) {
                    for (int i = 0; i < latLngModel.stateLatLngList.size(); i++) {
                        LatLng latLng = new LatLng(Double.parseDouble(latLngModel.stateLatLngList.get(i).Latitude),
                                Double.parseDouble(latLngModel.stateLatLngList.get(i).Longitude));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLngModel.stateLatLngList.get(i).name);
                        markerOptions.icon(BitmapDescriptorFactory.
                                fromBitmap(new UtilityOfActivity((AppCompatActivity) getActivity()).getIconInBitmap(R.drawable.loc_icon)));

                        googleMap.addMarker(markerOptions);
                    }
                }

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
        setAdapterOFFilteration(latLngModel.stateLatLngList);

        googleMap.setInfoWindowAdapter(this);

        utilityOfActivity.progresDissmiss();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");

        /*markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);*/

        layoutMap.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3.5f));
        showSnackBar();

        //move map camera
        // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // getActivity() thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    public float zoomLevel(Float radius) {
        //Math.round(location.distanceTo(latlngToLocation(bounds.northeast)) / 1000) > radius;
        float zoomLevel = 1;
        float res = 0;
        if (radius != null) {
            zoomLevel = (float) (5 - Math.log(radius) / Math.log(2));
            //reduce/increase getActivity() hardcoded value to change zooming level.
        }
        res = zoomLevel - 0.5f;

        return res;
    }


    public void setLatLangList(String stringRequest) {
        latLngModel = new GsonBuilder().create().fromJson(stringRequest, LatLngModel.class);

    }

    public void getStateList() {
        try {

            WebService webService = new WebService();
            webService.GetRequestThroughVolley(FragmentMap.this, null, url, Request.Method.GET,"GetSTATELIST");
        } catch (Exception e) {
            Log.d("", "");
        }
    }


    @Override
    public void Result(String response,String calledBy) {
        setLatLangList(response.toString());
        mapFrag.getMapAsync(this);
        circularProgressBarMap.setVisibility(View.GONE);

    }

    public Fragment getFragmentInstance() {

        FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();
        List<Fragment> list = manager.getFragments();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof FragmentMap) {
                return list.get(i);
            }
        }

        return new FragmentMap();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_butn:
                hideKeyboard();
                serachDealer_auto.setText("");
                break;
        }


    }

    private void setAdapterOFFilteration(List<LatLngModel.StateLatLngList> stateLatLngList) {
        AutoCompleteAdapter adapter = new
                AutoCompleteAdapter(getActivity(), stateLatLngList);
        serachDealer_auto.setThreshold(2);
        serachDealer_auto.setAdapter(adapter);
        setTypeFace(serachDealer_auto);
        serachDealer_auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    clear_butn.setBackgroundResource(R.drawable.search_icon);
                } else {
                    clear_butn.setBackgroundResource(R.drawable.ic_action_clear);
                }
            }
        });


        serachDealer_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    hideKeyboard();
                    LatLngModel.StateLatLngList stateLatLngList =
                            (LatLngModel.StateLatLngList) parent.getItemAtPosition(position);
                    serachDealer_auto.setText(stateLatLngList.name);
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(Double.parseDouble(stateLatLngList.Latitude), Double.parseDouble(stateLatLngList.Longitude)), 10));
                    showSnackBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public void setTypeFace(AutoCompleteTextView view) {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "alice_reg.ttf");
        view.setTypeface(font);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        String stateName = marker.getTitle();
        Bundle bundle = new Bundle();
        bundle.putString("stateName", stateName);
        Fragment fragment = new StateInfoFragment();
        fragment.setArguments(bundle);
        UtilityOfActivity.moveFragment(fragment, "fragmentStateInfo", R.id.container, (AppCompatActivity) getActivity());
    }

    public void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "Click the markers for more informations.", Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bluish_brown));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        textView.setTypeface(typeface);
        snackbar.show();

        snackbar.show();


    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String markerId = null;
        int id = 0;
        try {
            markerId = marker.getId().replace("m", "");
            id = Integer.parseInt(markerId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (id >= latLngModel.stateLatLngList.size()) {
            return null;
        }


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.marker_info_window_adapter, null);
        setMakerMiniDialog(view, marker);
        //setDialogOnAdapter(view, marker);
        return view;
    }


    private void setMakerMiniDialog(View view, final Marker marker) {

        TextView mStateName;
        TextView mStateInfo;


        mStateName = (TextView) view.findViewById(R.id.state_name);
        mStateInfo = (TextView) view.findViewById(R.id.state_info);


        String markerId = null;
        int id = 0;
        try {
            markerId = marker.getId().replace("m", "");
            id = Integer.parseInt(markerId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (id >= latLngModel.stateLatLngList.size()) {
            return;
        }

        mStateName.setText(latLngModel.stateLatLngList.get(id).name);

        StringBuilder stringBuilder = new StringBuilder(latLngModel.stateLatLngList.get(id).capital);
       /* StringBuilder stringBuilder = new StringBuilder(latLngModel.stateLatLngList.get(id).Latitude);
        stringBuilder.append(latLngModel.stateLatLngList.get(id).Longitude);*/
        mStateInfo.setText(stringBuilder);


    }
    public void hideKeyboard(){
        try {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

