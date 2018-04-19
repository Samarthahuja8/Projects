package com.app.farmerswikki.util.database_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.model.weather.pojo.CityDataResponse;
import com.app.farmerswikki.model.weather.pojo.WeatherDetails;
import com.app.farmerswikki.model.weather.pojo.WeatherDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FarmersWikkiDatabase";

    // Contacts table name
    private static final String TABLE_NOTIFICATIONS = "NOTIFICATIONS";
    private static final String TABLE_CITY_DETAILS = "CITY_DETAILS";
    private static final String TABLE_WEATHER_DETAILS= "WEATHER_DETAILS";

    /*Notification table columns name*/
    private static final String NOTIFICATION_ID = "notificationId";
    private static final String IMAGE_NOTIFICATION_URL = "imageNotificationUrl";
    private static final String NOTIFICATION_HEADING = "notificationHeading";
    private static final String NOTIFICATION_BODY = "notificationBody";
    /**********************************/
    /******************/


    /*Weather Prediction table name*/
    private static final String CITY_ID = "cityid";
    private static final String ID = "id";
    private static final String MORN_TEMP = "morntemp";
    private static final String DAY_TEMP = "daytemp";
    private static final String EVEN_TEMP = "eventemp";
    private static final String NIGHT_TEMP= "nighttemp";
    private static final String MIN_TEMP= "mintemp";
    private static final String MAX_TEMP= "maxtemp";
    private static final String WIND_SPEED= "windspeed";
    private static final String WEATHER_HUMIDITY= "weatherhumidity";
    private static final String WEATHER_MAIN= "weathermain";
    private static final String WEATHER_DESCRIPTION= "weatherdescription";
    private static final String WEATHER_ICON= "weathericon";
    private static final String REFRESH_TIME= "refreshtime";
    /*****************************/

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + NOTIFICATION_ID + " INTEGER PRIMARY KEY," + IMAGE_NOTIFICATION_URL + " TEXT," + NOTIFICATION_HEADING + " TEXT, "
                + NOTIFICATION_BODY + " TEXT" + ")";

        
/*
        String CREATE_WEATHER_DETAILS="CREATE TABLE " +TABLE_WEATHER_DETAILS +"(" + CITY_ID +"  INTEGER REFERENCES " +TABLE_CITY_DETAILS +" ( "+CITY_ID+" ) "+", "
                +MORN_TEMP+" TEXT," +DAY_TEMP+"  TEXT," +EVEN_TEMP+"  TEXT,"+NIGHT_TEMP+"  TEXT,"+MIN_TEMP+"  TEXT,"+MAX_TEMP+"  TEXT,"+WIND_SPEED+"  TEXT,"
                +WEATHER_HUMIDITY+"  TEXT,"+WEATHER_MAIN+"  TEXT,"+WEATHER_DESCRIPTION+"  TEXT" +")";*/


        String CREATE_WEATHER_DETAILS="CREATE TABLE " +TABLE_WEATHER_DETAILS +"(" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CITY_ID +"  INTEGER, "
                +MORN_TEMP+" TEXT, " +DAY_TEMP+"  TEXT, " +EVEN_TEMP+"  TEXT, "+NIGHT_TEMP+"  TEXT, "+MIN_TEMP+"  TEXT, "+MAX_TEMP+"  TEXT, "+WIND_SPEED+"  TEXT, "
                +WEATHER_HUMIDITY+"  TEXT, "+WEATHER_MAIN+"  TEXT, "+WEATHER_DESCRIPTION+"  TEXT," + WEATHER_ICON +" TEXT, " +REFRESH_TIME+" TEXT " +")";

        /*db.execSQL("CREATE TABLE CITY_DETAILS(cityId BIGINT PRIMARY KEY,cityName TEXT,previousTime BIGINT,currentTime  BIGINT)");

       db.execSQL("CREATE TABLE WEATHER_DETAILS (id  BIGINT REFERENCES CITY_DETAILS (cityId), morntemp  TEXT,daytemp TEXT" +
               ",eventemp TEXT,nighttemp   TEXT,mintemp  TEXT,maxtemp   TEXTwindspeed TEXT," +
               "weatherhumidity TEXT,weathermain  TEXT,weatherdescription TEXT)");*/

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_WEATHER_DETAILS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);

        // Create tables again
        onCreate(db);
    }


    public void addNotification(NotificationResponse notificationResponse) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_ID, Integer.parseInt(notificationResponse.notificationId));
        values.put(IMAGE_NOTIFICATION_URL, notificationResponse.imageNotificationUrl);
        values.put(NOTIFICATION_HEADING, notificationResponse.notificationHeading);
        values.put(NOTIFICATION_BODY, notificationResponse.notificationBody);
        // Inserting Row
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close(); // Closing database connection
    }

    public List<NotificationResponse> getAllContacts() {
        List<NotificationResponse> notificationResponseList = new ArrayList<NotificationResponse>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationResponse notificationResponse = new NotificationResponse();
                notificationResponse.setNotificationId(String.valueOf(cursor.getInt(0)));
                notificationResponse.setImageNotificationUrl(cursor.getString(1));
                notificationResponse.setNotificationHeading(cursor.getString(2));
                notificationResponse.setNotificationBody(cursor.getString(3));

                // Adding contact to list
                notificationResponseList.add(notificationResponse);
            } while (cursor.moveToNext());
        }

        // return contact list
        return notificationResponseList;
    }


    public int getNotificationsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        return count;
    }


    // Deleting single contact
    public void deleteContact(NotificationResponse notificationResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATIONS, NOTIFICATION_ID + " = ?",
                new String[] { notificationResponse.getNotificationId()});

        db.close();
    }





    // Updating single Notification
    public int updateContact(NotificationResponse notificationResponse) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_ID, Integer.parseInt(notificationResponse.getNotificationId()));
        values.put(IMAGE_NOTIFICATION_URL, notificationResponse.imageNotificationUrl);
        values.put(NOTIFICATION_HEADING, notificationResponse.notificationHeading);
        values.put(NOTIFICATION_BODY, notificationResponse.notificationBody);

        // updating row
        return db.update(TABLE_NOTIFICATIONS, values, NOTIFICATION_ID + " = ?",
                new String[] {notificationResponse.getNotificationId()});
    }


    // Getting single Notification
    public NotificationResponse getSingleNotification(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATIONS, new String[] { NOTIFICATION_ID, IMAGE_NOTIFICATION_URL,NOTIFICATION_HEADING
                        , NOTIFICATION_BODY }, NOTIFICATION_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NotificationResponse notificationResponse = new NotificationResponse();
        if(cursor.moveToFirst()) {
            notificationResponse.setNotificationId(String.valueOf(cursor.getInt(0)));
            notificationResponse.setImageNotificationUrl(cursor.getString(1));
            notificationResponse.setNotificationHeading(cursor.getString(2));
            notificationResponse.setNotificationBody(cursor.getString(3));

        }
        else {
            notificationResponse=null;
        }
        // return contact
        return notificationResponse;
    }



    public void deleteWeatherData(int  id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER_DETAILS, CITY_ID + " = ?",
                new String[] { String.valueOf(id)});

        db.close();
    }

    public void addWeatherDetails(WeatherDetails weatherDetails){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_ID,Integer.parseInt(weatherDetails.id));
        values.put(MORN_TEMP,weatherDetails.getMornTemp());
        values.put(DAY_TEMP,weatherDetails.getDayTemp());
        values.put(EVEN_TEMP,weatherDetails.getEvenTemp());
        values.put(NIGHT_TEMP,weatherDetails.getNightTemp());
        values.put(MIN_TEMP,weatherDetails.getMinTemp());
        values.put(MAX_TEMP,weatherDetails.getMaxTemp());
        values.put(WIND_SPEED,weatherDetails.getWindSpeed());
        values.put(WEATHER_HUMIDITY,weatherDetails.getHumidity());
        values.put(WEATHER_MAIN,weatherDetails.getMain());
        values.put(WEATHER_DESCRIPTION,weatherDetails.getDescription());
        values.put(WEATHER_ICON,weatherDetails.getIcon());
        values.put(REFRESH_TIME,weatherDetails.getRefershTime());


        // Inserting Row
        db.insert(TABLE_WEATHER_DETAILS, null, values);
        db.close(); // Closing database connection
    }


    public WeatherDetails getWeatherDetails(String cityId){


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEATHER_DETAILS, new String[] { CITY_ID, MORN_TEMP,DAY_TEMP
                        , EVEN_TEMP,NIGHT_TEMP,MIN_TEMP,MAX_TEMP,WIND_SPEED,WEATHER_HUMIDITY,WEATHER_MAIN,WEATHER_DESCRIPTION,WEATHER_ICON,REFRESH_TIME}, CITY_ID + "=?",
                new String[] { cityId }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WeatherDetails weatherDetails = new WeatherDetails();
        if(cursor.moveToFirst()) {
            weatherDetails.setId(String.valueOf(cursor.getInt(1)));
            weatherDetails.setMornTemp(cursor.getString(2));
            weatherDetails.setDayTemp(cursor.getString(3));
            weatherDetails.setEvenTemp(cursor.getString(4));
            weatherDetails.setNightTemp(cursor.getString(5));
            weatherDetails.setMinTemp(cursor.getString(6));
            weatherDetails.setMaxTemp(cursor.getString(7));
            weatherDetails.setWindSpeed(cursor.getString(8));
            weatherDetails.setHumidity(cursor.getString(9));
            weatherDetails.setMain(cursor.getString(10));
            weatherDetails.setDescription(cursor.getString(11));
            weatherDetails.setIcon(cursor.getString(12));
            weatherDetails.setRefershTime(cursor.getString(13));

        }
        else {
            weatherDetails=null;
        }
        // return contact
        return weatherDetails;





     //   db.rawQuery(MY_QUERY, new String[]{String.valueOf(cityDataResponse.cityId)});

    }



    public List<WeatherDetails> getWeatherDetailsList(int id) {
        List<WeatherDetails> weatherDetailsList = new ArrayList<WeatherDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER_DETAILS +" where " +CITY_ID+" = "+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WeatherDetails weatherDetails = new WeatherDetails();
                weatherDetails.setId(String.valueOf(cursor.getInt(1)));
                weatherDetails.setMornTemp(cursor.getString(2));
                weatherDetails.setDayTemp(cursor.getString(3));
                weatherDetails.setEvenTemp(cursor.getString(4));
                weatherDetails.setNightTemp(cursor.getString(5));
                weatherDetails.setMinTemp(cursor.getString(6));
                weatherDetails.setMaxTemp(cursor.getString(7));
                weatherDetails.setWindSpeed(cursor.getString(8));
                weatherDetails.setHumidity(cursor.getString(9));
                weatherDetails.setMain(cursor.getString(10));
                weatherDetails.setDescription(cursor.getString(11));
                weatherDetails.setIcon(cursor.getString(12));
                weatherDetails.setRefershTime(cursor.getString(13));
                // Adding contact to list
                weatherDetailsList.add(weatherDetails);
            } while (cursor.moveToNext());
        }


        // return contact list
        return weatherDetailsList;
    }



}