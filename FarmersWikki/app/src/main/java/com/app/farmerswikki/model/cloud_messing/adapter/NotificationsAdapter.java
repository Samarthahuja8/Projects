package com.app.farmerswikki.model.cloud_messing.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.util.custom.CustomDialogTwoButtons;
import com.app.farmerswikki.util.custom.GenrateClassObject;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ORBITWS19 on 21-Jul-2017.
 */

public class NotificationsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements
        OnClickNotificationAdapter{

    Context context;
    DatabaseHandler db;
    NotificationResponse notificationResponse;
    int size;
    List<NotificationResponse> notificationResponseList;
    int position;
    ViewPagerNotificationHandler viewPagerNotificationHandler;
    RecyclerView.ViewHolder holder;
    UtilityOfActivity utilityOfActivity;


    boolean flag=false;

    public NotificationsAdapter(Context context,ViewPagerNotificationHandler viewPagerNotificationHandler, List<NotificationResponse> notificationResponseList,int size) {
        this.context = context;
        db=new DatabaseHandler(context);
        this.notificationResponseList=notificationResponseList;
        this.size=size;
        this.viewPagerNotificationHandler=viewPagerNotificationHandler;
        this.utilityOfActivity=new UtilityOfActivity((AppCompatActivity)context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        if(!flag) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification, parent, false);

            return new MyViewHolder(itemView);
        }
        if(flag){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_no_notifications, parent, false);

            return new MyViewHolderNoNotificatons(itemView);
        }


        throw new RuntimeException(context.getResources().getString(R.string.api_custom_error_msg));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if(holder instanceof MyViewHolder) {

            this.position = position;
            this.notificationResponse = notificationResponseList.get(position);
            holder.setIsRecyclable(false);
            Picasso.with(context)
                    .load(this.notificationResponse.imageNotificationUrl)
                    .fit()
                    .into(((MyViewHolder)holder).collapsingToolbarImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            ((MyViewHolder)holder).progressbar.setVisibility(View.GONE);
                            ((MyViewHolder)holder).floatingActionButton.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            // TODO Auto-generated method stub
                            ((MyViewHolder)holder).progressbar.setVisibility(View.GONE);
                            ((MyViewHolder)holder).floatingActionButton.setVisibility(View.VISIBLE);
                        }
                    });


            ((MyViewHolder)holder).notificationHeadingTv.setText(this.notificationResponse.notificationHeading);
            ((MyViewHolder)holder).notificationBodyTv.setText(this.notificationResponse.notificationBody);

            ((MyViewHolder)holder).floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickNotificationAdapter onClickNotificationAdapter = NotificationsAdapter.this;
                    onClickNotificationAdapter.onClickToNotificationsItem(((MyViewHolder)holder), position);
                }
            });


        }


    }


    public NotificationResponse getNotificationResponse(int position){
        return notificationResponseList.get(position);
    }


    private boolean isNotificationLiesAt(int position) {
        return size == position;
    }

    @Override
    public int getItemCount() {
        if(size!=0) {
            flag=false;
            return size;
        }
        else {
            flag=true;
            return 1;
        }
    }

    @Override
    public void onClickToNotificationsItem(MyViewHolder myViewHolder, int position) {
        GenrateClassObject genrateClassObject=new GenrateClassObject(NotificationsAdapter.this);
        //int a=myViewHolder.getLayoutPosition();
        CustomDialogTwoButtons.showDialog(context,viewPagerNotificationHandler,"Delete This News !","Want to Delete this News ?","Leave It!",
                "Delete",getNotificationResponse(position),position,
                genrateClassObject);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView collapsingToolbarImageView;
        TextView notificationHeadingTv, notificationBodyTv;
        ProgressBar progressbar;
        CoordinatorLayout coordinateLayout;
        FloatingActionButton floatingActionButton;

        public MyViewHolder(final View view) {
            super(view);
            collapsingToolbarImageView = (ImageView) view.findViewById(R.id.collapsingToolbarImageView);
            notificationHeadingTv = (TextView) view.findViewById(R.id.notificationHeadingTv);
            notificationBodyTv = (TextView) view.findViewById(R.id.notificationBodyTv);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            coordinateLayout = (CoordinatorLayout) view.findViewById(R.id.coordinateLayout);
            floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


        }
    }


    public class MyViewHolderNoNotificatons extends RecyclerView.ViewHolder {

        public MyViewHolderNoNotificatons(final View view){
            super(view);
        }
    }


}
 interface OnClickNotificationAdapter{
    public void onClickToNotificationsItem(NotificationsAdapter.MyViewHolder myViewHolder, int position);
}



