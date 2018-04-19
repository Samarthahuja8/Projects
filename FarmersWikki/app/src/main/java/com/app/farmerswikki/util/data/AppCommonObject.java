package com.app.farmerswikki.util.data;

/**
 * Created by ORBITWS19 on 03-Jun-2017.
 */

public class AppCommonObject  {
    private static AppCommonObject appCommonObject = new AppCommonObject();
    public String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public static AppCommonObject getInstance(){
        if(appCommonObject==null){
            return appCommonObject;

        }else {
            return appCommonObject;
        }
    }



}
