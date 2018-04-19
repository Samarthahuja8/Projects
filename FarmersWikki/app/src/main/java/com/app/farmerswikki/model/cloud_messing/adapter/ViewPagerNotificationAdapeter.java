package com.app.farmerswikki.model.cloud_messing.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ORBITWS19 on 22-Jul-2017.
 */

public class ViewPagerNotificationAdapeter extends PagerAdapter {

    private Context context;
    List<NotificationResponse> notificationResponseList;
    int size=0;
    LayoutInflater mLayoutInflater;
    ImageView collapsingToolbarImageView;
    TextView notificationHeadingTv, notificationBodyTv;
    ProgressBar progressbar;
    NotificationResponse notificationResponse;
    View view;
    public ViewPagerNotificationAdapeter(Context context) {
        this.context = context;
        DatabaseHandler db=new DatabaseHandler(context);
        this.notificationResponseList=db.getAllContacts();
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.size=db.getAllContacts().size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


             view = mLayoutInflater.inflate(R.layout.fragment_notification1, container, false);

            collapsingToolbarImageView = (ImageView) view.findViewById(R.id.collapsingToolbarImageView);
            notificationHeadingTv = (TextView) view.findViewById(R.id.notificationHeadingTv);
            notificationBodyTv = (TextView) view.findViewById(R.id.notificationBodyTv);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);


        this.notificationResponse=notificationResponseList.get(position);

        Picasso.with(context)
                .load(this.notificationResponse.imageNotificationUrl)
                .fit()
                .into(collapsingToolbarImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub
                        progressbar.setVisibility(View.GONE);
                    }
                });


        notificationHeadingTv.setText(this.notificationResponse.notificationHeading);
        notificationBodyTv.setText(this.notificationResponse.notificationBody);


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return context.getString(customPagerEnum.getTitleResId());
    }*/

}