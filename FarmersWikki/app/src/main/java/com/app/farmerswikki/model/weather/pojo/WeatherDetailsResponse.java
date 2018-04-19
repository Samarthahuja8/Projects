package com.app.farmerswikki.model.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ORBITWS19 on 02-Aug-2017.
 */

public class WeatherDetailsResponse implements Parcelable{

    public City city;
    public List<WeatherList> list;

    protected WeatherDetailsResponse(Parcel in) {
        city = in.readParcelable(City.class.getClassLoader());
        list = in.createTypedArrayList(WeatherList.CREATOR);
    }

    public static final Creator<WeatherDetailsResponse> CREATOR = new Creator<WeatherDetailsResponse>() {
        @Override
        public WeatherDetailsResponse createFromParcel(Parcel in) {
            return new WeatherDetailsResponse(in);
        }

        @Override
        public WeatherDetailsResponse[] newArray(int size) {
            return new WeatherDetailsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(city, i);
        parcel.writeTypedList(list);
    }

    public static class City implements Parcelable{

        public String id,name;


        protected City(Parcel in) {
            id = in.readString();
            name = in.readString();
        }

        public static final Creator<City> CREATOR = new Creator<City>() {
            @Override
            public City createFromParcel(Parcel in) {
                return new City(in);
            }

            @Override
            public City[] newArray(int size) {
                return new City[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(name);
        }
    }

    public static class WeatherList implements Parcelable{

        public Temp temp;
        public List<Weather> weather;
        public String speed,humidity;

        protected WeatherList(Parcel in) {
            temp = in.readParcelable(Temp.class.getClassLoader());
            weather = in.createTypedArrayList(Weather.CREATOR);
            speed = in.readString();
            humidity = in.readString();
        }

        public static final Creator<WeatherList> CREATOR = new Creator<WeatherList>() {
            @Override
            public WeatherList createFromParcel(Parcel in) {
                return new WeatherList(in);
            }

            @Override
            public WeatherList[] newArray(int size) {
                return new WeatherList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(temp, i);
            parcel.writeTypedList(weather);
            parcel.writeString(speed);
            parcel.writeString(humidity);
        }

        public static class Temp implements Parcelable{
            public String day,min,max,night,eve,morn;

            protected Temp(Parcel in) {
                day = in.readString();
                min = in.readString();
                max = in.readString();
                night = in.readString();
                eve = in.readString();
                morn = in.readString();
            }

            public static final Creator<Temp> CREATOR = new Creator<Temp>() {
                @Override
                public Temp createFromParcel(Parcel in) {
                    return new Temp(in);
                }

                @Override
                public Temp[] newArray(int size) {
                    return new Temp[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(day);
                parcel.writeString(min);
                parcel.writeString(max);
                parcel.writeString(night);
                parcel.writeString(eve);
                parcel.writeString(morn);
            }
        }
        public static class Weather implements Parcelable{
            public String main,description,icon;

            protected Weather(Parcel in) {
                main = in.readString();
                description = in.readString();
                icon = in.readString();
            }

            public static final Creator<Weather> CREATOR = new Creator<Weather>() {
                @Override
                public Weather createFromParcel(Parcel in) {
                    return new Weather(in);
                }

                @Override
                public Weather[] newArray(int size) {
                    return new Weather[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(main);
                parcel.writeString(description);
                parcel.writeString(icon);
            }
        }


    }
}
