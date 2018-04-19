package com.app.farmerswikki.model.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ORBITWS19 on 02-Aug-2017.
 */

public class WeatherDetails {

    public String id;
    public String mornTemp;
    public String dayTemp;
    public String evenTemp;
    public String nightTemp;
    public String minTemp;
    public String maxTemp;
    public String main;
    public String description;
    public String windSpeed;
    public String humidity;
    public String icon;
    public String refershTime;
    public String getRefershTime() {
        return refershTime;
    }

    public void setRefershTime(String refershTime) {
        this.refershTime = refershTime;
    }



    protected WeatherDetails(Parcel in) {
        id = in.readString();
        mornTemp = in.readString();
        dayTemp = in.readString();
        evenTemp = in.readString();
        nightTemp = in.readString();
        minTemp = in.readString();
        maxTemp = in.readString();
        main = in.readString();
        description = in.readString();
        windSpeed = in.readString();
        humidity = in.readString();
    }

    public WeatherDetails(){

    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMornTemp() {
        return mornTemp;
    }

    public void setMornTemp(String mornTemp) {
        this.mornTemp = mornTemp;
    }

    public String getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp) {
        this.dayTemp = dayTemp;
    }

    public String getEvenTemp() {
        return evenTemp;
    }

    public void setEvenTemp(String evenTemp) {
        this.evenTemp = evenTemp;
    }

    public String getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(String nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

}
