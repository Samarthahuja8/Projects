package com.app.farmerswikki.model.map.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ORBITWS19 on 20-May-2017.
 */

public class LatLngModel implements Parcelable{

    public List<StateLatLngList>stateLatLngList;

    protected LatLngModel(Parcel in) {
        stateLatLngList = in.createTypedArrayList(StateLatLngList.CREATOR);
    }

    public static final Creator<LatLngModel> CREATOR = new Creator<LatLngModel>() {
        @Override
        public LatLngModel createFromParcel(Parcel in) {
            return new LatLngModel(in);
        }

        @Override
        public LatLngModel[] newArray(int size) {
            return new LatLngModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(stateLatLngList);
    }

    public static class StateLatLngList implements Parcelable{
        public String name,Latitude,Longitude,capital,id;

        protected StateLatLngList(Parcel in) {
            name = in.readString();
            Latitude = in.readString();
            Longitude = in.readString();
            capital = in.readString();
            id = in.readString();


        }

        public static final Creator<StateLatLngList> CREATOR = new Creator<StateLatLngList>() {
            @Override
            public StateLatLngList createFromParcel(Parcel in) {
                return new StateLatLngList(in);
            }

            @Override
            public StateLatLngList[] newArray(int size) {
                return new StateLatLngList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(Latitude);
            parcel.writeString(Longitude);
            parcel.writeString(capital);
            parcel.writeString(id);

        }
    }

}
