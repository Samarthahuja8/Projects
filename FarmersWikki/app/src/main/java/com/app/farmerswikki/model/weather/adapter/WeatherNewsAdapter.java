package com.app.farmerswikki.model.weather.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.weather.pojo.WeatherDetails;
import com.app.farmerswikki.util.custom.CustomDialogTwoButtons;
import com.app.farmerswikki.util.custom.GenrateClassObject;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ORBITWS19 on 03-Aug-2017.
 */

public class WeatherNewsAdapter  extends RecyclerView.Adapter<WeatherNewsAdapter.MyViewHolder> {

    private List<WeatherDetails> weatherDetailsList;
    Context context;
    String currentDate,nextDate;
    SimpleDateFormat sdf,sdf1;
    Calendar c;

    public WeatherNewsAdapter(Context context,List<WeatherDetails> weatherDetailsList){
        this.context=context;
        String val=new Gson().toJson(weatherDetailsList);

        this.weatherDetailsList=weatherDetailsList;
        //setDateTime();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_weather_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.minTemp.setText(String.valueOf(position+1));


            findNextDate(position);
            holder.weekDays.setText(nextDate);
            holder.maxTemp.setText(UtilityOfActivity.formatTemperature(weatherDetailsList.get(position).getMaxTemp()));
            holder.minTemp.setText(UtilityOfActivity.formatTemperature(weatherDetailsList.get(position).getMinTemp()));
            holder.aboutWeather.setText(weatherDetailsList.get(position).getDescription());
            String iconDes=weatherDetailsList.get(position).getIcon();
            setIconToImageView(holder,iconDes);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView maxTemp,weekDays,minTemp,aboutWeather;
        public ImageView weatherImage;

        public MyViewHolder(View view) {
            super(view);
            maxTemp=(TextView) view.findViewById(R.id.maxTemp);
            minTemp=(TextView) view.findViewById(R.id.minTemp);
            weekDays=(TextView) view.findViewById(R.id.weekDays);
            aboutWeather=(TextView) view.findViewById(R.id.aboutWeather);
            weatherImage=(ImageView) view.findViewById(R.id.weatherImage);
        }
    }

    @Override
    public int getItemCount() {
        return weatherDetailsList.size();
    }


    public void findNextDate(int position){
        currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        sdf = new SimpleDateFormat("dd MMM yyyy");
        c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, position); // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        sdf1 = new SimpleDateFormat("dd MMM yyyy");
        nextDate = sdf1.format(c.getTime());

    }


    public void setIconToImageView(MyViewHolder myViewHolder,String iconDes){

        switch (iconDes){

            case "01d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_clear));
                break;
            }


            case "02d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_light_clouds));
                break;
            }



            case "03d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_scattered));
               // myViewHolder.weatherImage.setColorFilter(R.color.grey,android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            }

            case "04d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_clouds));
                break;
            }





            case "09d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_light_rain));
                break;
            }


            case "10d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_rain));
                break;
            }


            case "11d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_storm));
                break;
            }

            case "13d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_snow));
                break;
            }

            case "50d":{
                myViewHolder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.art_fog));
                break;
            }


        }


    }

}