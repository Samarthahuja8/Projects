package com.app.farmerswikki.model.nav_drawer.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.app.farmerswikki.model.nav_drawer.pojo.DataSheetMenuOptionResponse;
import com.app.farmerswikki.model.map.view.fragment.FragmentMap;
import com.app.farmerswikki.R;
import com.app.farmerswikki.model.nav_drawer.adapter.RecyclerViewDataSheetOptionsAdapter;
import com.app.farmerswikki.service.IwebService;
import com.app.farmerswikki.service.WebService;
import com.app.farmerswikki.util.data.Constant;
import com.google.gson.GsonBuilder;

/**
 * Created by orbit on 03-Nov-2016.
 */

public class NavDrawerFragmentDataSheetOptions extends Fragment implements IwebService {

    private View view;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppCompatActivity activity;
    Animation zoomIn,zoomOut;

    String url;
    DataSheetMenuOptionResponse dataSheetMenuOptionResponse;

    static int i=0;
    int a[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.nav_drawer_layout, container, false);
        a=new int[]{R.drawable.farmer1,R.drawable.farmer3,R.drawable.farmer4,R.drawable.farmer2,};
        activity=(AppCompatActivity)getActivity();
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //toolbar.setTitle("Mr. Ashish Raj");

        url= Constant.dataSheetMenuUrl;
        toolbarTextAppearence();
        getDatasheetMenu();


        zoomIn = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);


        collapsingToolbarLayout.setAnimation(zoomIn);

        zoomIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                collapsingToolbarLayout.startAnimation(zoomOut);

            }
        });


        zoomOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                collapsingToolbarLayout.startAnimation(zoomIn);
                if(i<a.length){
                    Drawable drawable=ContextCompat.getDrawable(getActivity(),a[i]);
                    collapsingToolbarLayout.setBackground(drawable);
                    i++;
                }
                else {
                    i=0;
                }
            }
        });

        return view;
    }





    private void toolbarTextAppearence() {


        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getActivity(),R.color.transparent));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getActivity(),R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getActivity(),R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    public void getDatasheetMenu(){


        try {


            WebService webService=new WebService();
            FragmentMap myFragment = (FragmentMap)getFragmentManager().findFragmentByTag("FRAGMENT");
            webService.GetRequestThroughVolley(NavDrawerFragmentDataSheetOptions.this,null,url, Request.Method.GET,"GetDataSheetMenu");
        }

        catch (Exception e) {
            Log.d("", "");
        }



    }


    @Override
    public void Result(String response,String calledBy) {

        dataSheetMenuOptionResponse=new GsonBuilder().create().fromJson(response,DataSheetMenuOptionResponse.class);

        setRecyclerView();


    }


    public void setRecyclerView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerViewDataSheetOptionsAdapter recylerViewAdapeter =
                new RecyclerViewDataSheetOptionsAdapter(dataSheetMenuOptionResponse,activity);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recylerViewAdapeter);
    }

}
