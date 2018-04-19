package com.app.farmerswikki.model.state_wise_info.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ORBITWS19 on 31-May-2017.
 */

public class StateInfoResponse implements Parcelable{


    public List<StateInfoList> stateInfoList;

    protected StateInfoResponse(Parcel in) {
        stateInfoList = in.createTypedArrayList(StateInfoList.CREATOR);
    }

    public static final Creator<StateInfoResponse> CREATOR = new Creator<StateInfoResponse>() {
        @Override
        public StateInfoResponse createFromParcel(Parcel in) {
            return new StateInfoResponse(in);
        }

        @Override
        public StateInfoResponse[] newArray(int size) {
            return new StateInfoResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(stateInfoList);
    }

    public static class StateInfoList implements Parcelable{
       public String stateName,aboutStateCultivation;


       protected StateInfoList(Parcel in) {
           stateName = in.readString();
           aboutStateCultivation = in.readString();
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
       public void writeToParcel(Parcel parcel, int i) {
           parcel.writeString(stateName);
           parcel.writeString(aboutStateCultivation);
       }
   }

}
