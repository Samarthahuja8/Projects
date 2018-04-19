package com.app.farmerswikki.util.custom;

/**
 * Created by ORBITWS19 on 24-Jul-2017.
 */

public class GenrateClassObject<T> {
private T object;

public GenrateClassObject(T object){
        this.object=object;
        }

public T getObject(){
        return object;
        }

        }
