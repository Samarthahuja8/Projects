package com.app.farmerswikki.model.cloud_messing.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.adapter.NotificationsAdapter;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.util.custom.CustomDialogTwoButtons;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.util.List;


/**
 * Created by ORBITWS19 on 21-Jul-2017.
 */

public class ViewPagerNotificationHandler extends Fragment implements CustomDialogTwoButtons.OnNewsDelete{

    View view;
    RecyclerView view_pager_recyclerview;
    NotificationsAdapter notificationsAdapter;
    DatabaseHandler db;
    public static boolean isNotificationFragmentActive;
    List<NotificationResponse> notificationResponseList;
    int size;
    GoogleProgressBar circularProgressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.parent_container).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.app_main_background));
        view=inflater.inflate(R.layout.view_pager_notifications_handler,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isNotificationFragmentActive=true;
        circularProgressBar=(GoogleProgressBar) view.findViewById(R.id.circularProgressBarNotificationFrag);
        try{
            circularProgressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
            //circularProgressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getActivity()).colors(getResources().getIntArray(R.array.progressLoader)).build());
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            Log.d("circularProgressBar", "onCreate() returned: " + e );
        }
        view_pager_recyclerview=(RecyclerView) view.findViewById(R.id.view_pager_recyclerview);
        setRecyclerView();



    }


    @Override
    public void onNewsDelete(int position) {
       //db=new DatabaseHandler(getActivity());
       // int size=db.getAllContacts().size();
       // notificationsAdapter.notifyItemRemoved(position);
        //notificationsAdapter.notifyItemRangeChanged(position, size);
        //notificationsAdapter.notifyDataSetChanged();
        setRecyclerView();
    }



    public  void setRecyclerView() {
        new AsyncCaller().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        isNotificationFragmentActive=false;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            db=new DatabaseHandler(getActivity());
            notificationResponseList=db.getAllContacts();
            size=db.getAllContacts().size();

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationsAdapter = new NotificationsAdapter(getActivity(),ViewPagerNotificationHandler.this,notificationResponseList,size);
                    RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    view_pager_recyclerview.setLayoutManager(mLayoutManager);
                    view_pager_recyclerview.setItemAnimator(new DefaultItemAnimator());
                    view_pager_recyclerview.setItemViewCacheSize(20);
                    view_pager_recyclerview.setDrawingCacheEnabled(true);
                    view_pager_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    notificationsAdapter.setHasStableIds(true);
                    view_pager_recyclerview.setAdapter(notificationsAdapter);
                    circularProgressBar.setVisibility(View.GONE);

                }
            }, 1000);
        }
    }


}


