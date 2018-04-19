package com.app.farmerswikki.model.nav_drawer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.login_menu.view.activity.LoginRegistrationActivity;
import com.app.farmerswikki.model.map.view.fragment.FragmentMap;
import com.app.farmerswikki.model.nav_drawer.pojo.DataSheetMenuOptionResponse;
import com.app.farmerswikki.R;
import com.app.farmerswikki.util.data.UtilityOfActivity;

/**
 * Created by orbit on 24-Aug-2016.
 */
public class RecyclerViewDataSheetOptionsAdapter extends RecyclerView.Adapter<RecyclerViewDataSheetOptionsAdapter.MyViewHolder>  {

    Context context;

    Typeface typeface;

    DataSheetMenuOptionResponse dataSheetMenuOptionResponse;

    public RecyclerViewDataSheetOptionsAdapter(DataSheetMenuOptionResponse dataSheetMenuOptionResponse,Context context) {
        this.context = context;
        this.dataSheetMenuOptionResponse = dataSheetMenuOptionResponse;

        typeface=Typeface.createFromAsset(context.getAssets(),"alice_reg.ttf");
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_data_sheet_options, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String desc=dataSheetMenuOptionResponse.dataSheetList.get(position).description;
        String code=dataSheetMenuOptionResponse.dataSheetList.get(position).code;
        holder.item.setText(desc);
        holder.item.setTypeface(typeface);
        if(code.equalsIgnoreCase("LOGOUT")){
           holder.logOut.setVisibility(View.VISIBLE);
        }
        else {
            holder.logOut.setVisibility(View.GONE);
        }
        if(code.equalsIgnoreCase("NEWS")){
            holder.notificationCountTv.setVisibility(View.VISIBLE);
        }
        else {
            holder.notificationCountTv.setVisibility(View.GONE);

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               displayView(position);
            }
        });

    }




    @Override
    public int getItemCount() {
        return dataSheetMenuOptionResponse.dataSheetList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item,notificationCountTv;
        public View nav_drawer_option_separator;
        ImageView logOut;

        public MyViewHolder(final View view) {
            super(view);

            item = (TextView) view.findViewById(R.id.item);
            notificationCountTv = (TextView) view.findViewById(R.id.notificationCountTv);

            logOut=(ImageView)view.findViewById(R.id.logOut);

        }
    }


    public void displayView(int position){

        switch (dataSheetMenuOptionResponse.dataSheetList.get(position).code){
            case "LOCATION": {
                UtilityOfActivity.moveFragment(new FragmentMap(),"fragmentMap", R.id.container,(AppCompatActivity)context);
                OnClickNavItems onClickNavItems= (OnClickNavItems) context;
                onClickNavItems.onNavItemClick(context);
                break;
            }
            case "NEWS": {

                Fragment fragment = new ViewPagerNotificationHandler();
                UtilityOfActivity.moveFragment(fragment, "viewpagernotification", R.id.container,(AppCompatActivity)context);
                OnClickNavItems onClickNavItems= (OnClickNavItems) context;
                onClickNavItems.onNavItemClick(context);
                break;
            }

            case "ENQUIRY": {

                break;
            }case "ABOUT": {

                break;
            }case "CONTACT_US": {

                break;
            }case "LOGOUT": {

                Intent intent=new Intent(context,LoginRegistrationActivity.class);
                intent.putExtra("calledBy","LOGOUT");
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();

                break;
            }

        }

    }


    public interface OnClickNavItems{
        public void onNavItemClick(Context context);
    }

}
