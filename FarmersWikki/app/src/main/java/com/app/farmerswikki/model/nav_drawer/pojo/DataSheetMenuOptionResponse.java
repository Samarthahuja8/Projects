package com.app.farmerswikki.model.nav_drawer.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ORBITWS19 on 30-May-2017.
 */

public class DataSheetMenuOptionResponse implements Parcelable{

    public List<DataSheetList>dataSheetList;

    protected DataSheetMenuOptionResponse(Parcel in) {
        dataSheetList = in.createTypedArrayList(DataSheetList.CREATOR);
    }

    public static final Creator<DataSheetMenuOptionResponse> CREATOR = new Creator<DataSheetMenuOptionResponse>() {
        @Override
        public DataSheetMenuOptionResponse createFromParcel(Parcel in) {
            return new DataSheetMenuOptionResponse(in);
        }

        @Override
        public DataSheetMenuOptionResponse[] newArray(int size) {
            return new DataSheetMenuOptionResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(dataSheetList);
    }

    public static class DataSheetList implements Parcelable {

        public String description,code;


        protected DataSheetList(Parcel in) {
            description = in.readString();
            code = in.readString();
        }

        public static final Creator<DataSheetList> CREATOR = new Creator<DataSheetList>() {
            @Override
            public DataSheetList createFromParcel(Parcel in) {
                return new DataSheetList(in);
            }

            @Override
            public DataSheetList[] newArray(int size) {
                return new DataSheetList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(description);
            parcel.writeString(code);
        }
    }


}
