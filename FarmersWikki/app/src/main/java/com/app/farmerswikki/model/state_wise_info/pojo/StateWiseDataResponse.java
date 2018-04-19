package com.app.farmerswikki.model.state_wise_info.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by samarth on 09-07-2017.
 */

public class StateWiseDataResponse implements Parcelable {
    public List<StateInfoList> stateInfoList;

    protected StateWiseDataResponse(Parcel in) {
        stateInfoList = in.createTypedArrayList(StateInfoList.CREATOR);
    }

    public static final Creator<StateWiseDataResponse> CREATOR = new Creator<StateWiseDataResponse>() {
        @Override
        public StateWiseDataResponse createFromParcel(Parcel in) {
            return new StateWiseDataResponse(in);
        }

        @Override
        public StateWiseDataResponse[] newArray(int size) {
            return new StateWiseDataResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(stateInfoList);
    }

    public static class StateInfoList implements Parcelable{

        public String stateName;
        public String about;
        public String id;
        public List<VigyanKendra> vigyanKendra;
        public List<Crops> crops;

         protected StateInfoList(Parcel in) {
             stateName = in.readString();
             about = in.readString();
             id = in.readString();
             vigyanKendra = in.createTypedArrayList(VigyanKendra.CREATOR);
             crops = in.createTypedArrayList(Crops.CREATOR);
         }

         public static final Creator<StateInfoList> CREATOR = new Creator<StateInfoList>() {
             @Override
             public StateInfoList createFromParcel(Parcel in) {
                 return new StateInfoList(in);
             }

             @Override
             public StateInfoList[] newArray(int size) {
                 return new StateInfoList[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel dest, int flags) {
             dest.writeString(stateName);
             dest.writeString(about);
             dest.writeString(id);
             dest.writeTypedList(vigyanKendra);
             dest.writeTypedList(crops);
         }

         public static class VigyanKendra implements Parcelable{
            public String location;

             protected VigyanKendra(Parcel in) {
                 location = in.readString();
             }

             public static final Creator<VigyanKendra> CREATOR = new Creator<VigyanKendra>() {
                 @Override
                 public VigyanKendra createFromParcel(Parcel in) {
                     return new VigyanKendra(in);
                 }

                 @Override
                 public VigyanKendra[] newArray(int size) {
                     return new VigyanKendra[size];
                 }
             };

             @Override
             public int describeContents() {
                 return 0;
             }

             @Override
             public void writeToParcel(Parcel dest, int flags) {
                 dest.writeString(location);
             }
         }

         public static class Crops implements Parcelable{

             public String name;
             public String cultivationSeason;


             protected Crops(Parcel in) {
                 name = in.readString();
                 cultivationSeason = in.readString();
             }

             public static final Creator<Crops> CREATOR = new Creator<Crops>() {
                 @Override
                 public Crops createFromParcel(Parcel in) {
                     return new Crops(in);
                 }

                 @Override
                 public Crops[] newArray(int size) {
                     return new Crops[size];
                 }
             };

             @Override
             public int describeContents() {
                 return 0;
             }

             @Override
             public void writeToParcel(Parcel dest, int flags) {
                 dest.writeString(name);
                 dest.writeString(cultivationSeason);
             }
         }
    }
}
