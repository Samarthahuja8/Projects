package com.app.farmerswikki.model.cloud_messing.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ORBITWS19 on 18-Jul-2017.
 */

public class NotificationResponse implements Parcelable {

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getImageNotificationUrl() {
        return imageNotificationUrl;
    }

    public void setImageNotificationUrl(String imageNotificationUrl) {
        this.imageNotificationUrl = imageNotificationUrl;
    }

    public String getNotificationHeading() {
        return notificationHeading;
    }

    public void setNotificationHeading(String notificationHeading) {
        this.notificationHeading = notificationHeading;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String notificationId;
    public String imageNotificationUrl;
    public String notificationHeading;
    public String notificationBody;


    protected NotificationResponse(Parcel in) {

        notificationId = in.readString();
        imageNotificationUrl = in.readString();
        notificationHeading = in.readString();
        notificationBody = in.readString();
    }

    public NotificationResponse(){

    }






    public static final Creator<NotificationResponse> CREATOR = new Creator<NotificationResponse>() {
        @Override
        public NotificationResponse createFromParcel(Parcel in) {
            return new NotificationResponse(in);
        }

        @Override
        public NotificationResponse[] newArray(int size) {
            return new NotificationResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(notificationId);
        parcel.writeString(imageNotificationUrl);
        parcel.writeString(notificationHeading);
        parcel.writeString(notificationBody);
    }
}
