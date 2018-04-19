package com.app.farmerswikki.model.weather.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ORBITWS19 on 21-Aug-2017.
 */

public class WeatherDetailsResponse1 implements Parcelable {
    
    public String latitude , longitudetimezone , offset ;
    public  CurrentForecast currently;
    public  HourleyForecast hourly;
    public  DailyForecast daily;
    public  Flags flags;

    protected WeatherDetailsResponse1(Parcel in) {
        latitude = in.readString();
        longitudetimezone = in.readString();
        offset = in.readString();
        currently = in.readParcelable(CurrentForecast.class.getClassLoader());
        hourly = in.readParcelable(HourleyForecast.class.getClassLoader());
        daily = in.readParcelable(DailyForecast.class.getClassLoader());
        flags = in.readParcelable(Flags.class.getClassLoader());
    }

    public static final Creator<WeatherDetailsResponse1> CREATOR = new Creator<WeatherDetailsResponse1>() {
        @Override
        public WeatherDetailsResponse1 createFromParcel(Parcel in) {
            return new WeatherDetailsResponse1(in);
        }

        @Override
        public WeatherDetailsResponse1[] newArray(int size) {
            return new WeatherDetailsResponse1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latitude);
        parcel.writeString(longitudetimezone);
        parcel.writeString(offset);
        parcel.writeParcelable(currently, i);
        parcel.writeParcelable(hourly, i);
        parcel.writeParcelable(daily, i);
        parcel.writeParcelable(flags, i);
    }

    public static class CurrentForecast implements Parcelable {
        public String  time ,summary , icon , precipIntensity , precipProbability , temperature , apparentTemperature , dewPoint ,
                humidity , windSpeed , windGust , windBearing , cloudCover , pressure , ozone , uvIndex ;

        protected CurrentForecast(Parcel in) {
            time = in.readString();
            summary = in.readString();
            icon = in.readString();
            precipIntensity = in.readString();
            precipProbability = in.readString();
            temperature = in.readString();
            apparentTemperature = in.readString();
            dewPoint = in.readString();
            humidity = in.readString();
            windSpeed = in.readString();
            windGust = in.readString();
            windBearing = in.readString();
            cloudCover = in.readString();
            pressure = in.readString();
            ozone = in.readString();
            uvIndex = in.readString();
        }

        public static final Creator<CurrentForecast> CREATOR = new Creator<CurrentForecast>() {
            @Override
            public CurrentForecast createFromParcel(Parcel in) {
                return new CurrentForecast(in);
            }

            @Override
            public CurrentForecast[] newArray(int size) {
                return new CurrentForecast[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(time);
            parcel.writeString(summary);
            parcel.writeString(icon);
            parcel.writeString(precipIntensity);
            parcel.writeString(precipProbability);
            parcel.writeString(temperature);
            parcel.writeString(apparentTemperature);
            parcel.writeString(dewPoint);
            parcel.writeString(humidity);
            parcel.writeString(windSpeed);
            parcel.writeString(windGust);
            parcel.writeString(windBearing);
            parcel.writeString(cloudCover);
            parcel.writeString(pressure);
            parcel.writeString(ozone);
            parcel.writeString(uvIndex);
        }
    }
    
    public static class HourleyForecast implements Parcelable {
         public String summary ,icon;
         public List<Data> data;

        protected HourleyForecast(Parcel in) {
            summary = in.readString();
            icon = in.readString();
            data = in.createTypedArrayList(Data.CREATOR);
        }

        public static final Creator<HourleyForecast> CREATOR = new Creator<HourleyForecast>() {
            @Override
            public HourleyForecast createFromParcel(Parcel in) {
                return new HourleyForecast(in);
            }

            @Override
            public HourleyForecast[] newArray(int size) {
                return new HourleyForecast[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(summary);
            parcel.writeString(icon);
            parcel.writeTypedList(data);
        }

        public static class Data implements Parcelable {
             public String time , summary , icon, precipIntensity , precipProbability , temperature , apparentTemperature , dewPoint , humidity ,
                     windSpeed , windGust , windBearing , cloudCover , pressure , ozone , uvIndex ;

            protected Data(Parcel in) {
                time = in.readString();
                summary = in.readString();
                icon = in.readString();
                precipIntensity = in.readString();
                precipProbability = in.readString();
                temperature = in.readString();
                apparentTemperature = in.readString();
                dewPoint = in.readString();
                humidity = in.readString();
                windSpeed = in.readString();
                windGust = in.readString();
                windBearing = in.readString();
                cloudCover = in.readString();
                pressure = in.readString();
                ozone = in.readString();
                uvIndex = in.readString();
            }

            public static final Creator<Data> CREATOR = new Creator<Data>() {
                @Override
                public Data createFromParcel(Parcel in) {
                    return new Data(in);
                }

                @Override
                public Data[] newArray(int size) {
                    return new Data[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(time);
                parcel.writeString(summary);
                parcel.writeString(icon);
                parcel.writeString(precipIntensity);
                parcel.writeString(precipProbability);
                parcel.writeString(temperature);
                parcel.writeString(apparentTemperature);
                parcel.writeString(dewPoint);
                parcel.writeString(humidity);
                parcel.writeString(windSpeed);
                parcel.writeString(windGust);
                parcel.writeString(windBearing);
                parcel.writeString(cloudCover);
                parcel.writeString(pressure);
                parcel.writeString(ozone);
                parcel.writeString(uvIndex);
            }
        }
    }



    public static class DailyForecast implements Parcelable {
        public String summary ,icon;
        public List<Data> data;

        protected DailyForecast(Parcel in) {
            summary = in.readString();
            icon = in.readString();
            data = in.createTypedArrayList(Data.CREATOR);
        }

        public static final Creator<DailyForecast> CREATOR = new Creator<DailyForecast>() {
            @Override
            public DailyForecast createFromParcel(Parcel in) {
                return new DailyForecast(in);
            }

            @Override
            public DailyForecast[] newArray(int size) {
                return new DailyForecast[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(summary);
            parcel.writeString(icon);
            parcel.writeTypedList(data);
        }

        public static class Data implements Parcelable {
            public String  time , summary , icon , sunriseTime , sunsetTime , moonPhase , precipIntensity , precipIntensityMax , precipIntensityMaxTime , precipProbability ,
                    precipType , temperatureMin , temperatureMinTime , temperatureMax , temperatureMaxTime , apparentTemperatureMin , apparentTemperatureMinTime ,
                    apparentTemperatureMax , apparentTemperatureMaxTime , dewPoint , humidity , windSpeed , windGust , windGustTime , windBearing , cloudCover , pressure , ozone ,
                    uvIndex , uvIndexTime ;

            protected Data(Parcel in) {
                time = in.readString();
                summary = in.readString();
                icon = in.readString();
                sunriseTime = in.readString();
                sunsetTime = in.readString();
                moonPhase = in.readString();
                precipIntensity = in.readString();
                precipIntensityMax = in.readString();
                precipIntensityMaxTime = in.readString();
                precipProbability = in.readString();
                precipType = in.readString();
                temperatureMin = in.readString();
                temperatureMinTime = in.readString();
                temperatureMax = in.readString();
                temperatureMaxTime = in.readString();
                apparentTemperatureMin = in.readString();
                apparentTemperatureMinTime = in.readString();
                apparentTemperatureMax = in.readString();
                apparentTemperatureMaxTime = in.readString();
                dewPoint = in.readString();
                humidity = in.readString();
                windSpeed = in.readString();
                windGust = in.readString();
                windGustTime = in.readString();
                windBearing = in.readString();
                cloudCover = in.readString();
                pressure = in.readString();
                ozone = in.readString();
                uvIndex = in.readString();
                uvIndexTime = in.readString();
            }

            public static final Creator<Data> CREATOR = new Creator<Data>() {
                @Override
                public Data createFromParcel(Parcel in) {
                    return new Data(in);
                }

                @Override
                public Data[] newArray(int size) {
                    return new Data[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(time);
                parcel.writeString(summary);
                parcel.writeString(icon);
                parcel.writeString(sunriseTime);
                parcel.writeString(sunsetTime);
                parcel.writeString(moonPhase);
                parcel.writeString(precipIntensity);
                parcel.writeString(precipIntensityMax);
                parcel.writeString(precipIntensityMaxTime);
                parcel.writeString(precipProbability);
                parcel.writeString(precipType);
                parcel.writeString(temperatureMin);
                parcel.writeString(temperatureMinTime);
                parcel.writeString(temperatureMax);
                parcel.writeString(temperatureMaxTime);
                parcel.writeString(apparentTemperatureMin);
                parcel.writeString(apparentTemperatureMinTime);
                parcel.writeString(apparentTemperatureMax);
                parcel.writeString(apparentTemperatureMaxTime);
                parcel.writeString(dewPoint);
                parcel.writeString(humidity);
                parcel.writeString(windSpeed);
                parcel.writeString(windGust);
                parcel.writeString(windGustTime);
                parcel.writeString(windBearing);
                parcel.writeString(cloudCover);
                parcel.writeString(pressure);
                parcel.writeString(ozone);
                parcel.writeString(uvIndex);
                parcel.writeString(uvIndexTime);
            }
        }
    }

    public static class Flags implements Parcelable {

        public List<Sources>sources;
        public String units;

        protected Flags(Parcel in) {
            units = in.readString();
        }

        public static final Creator<Flags> CREATOR = new Creator<Flags>() {
            @Override
            public Flags createFromParcel(Parcel in) {
                return new Flags(in);
            }

            @Override
            public Flags[] newArray(int size) {
                return new Flags[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(units);
        }

        public static class Sources {

        }


    }

}





