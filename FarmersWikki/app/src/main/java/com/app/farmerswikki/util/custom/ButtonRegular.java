package com.app.farmerswikki.util.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Admin1 on 7/21/2016.
 */
public class ButtonRegular extends Button {
    private final static String NAME = "FONTAWESOME";
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);
    public ButtonRegular(Context context) {
        super(context);
    }

    public ButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void init() {

        try{
            Typeface typeface = sTypefaceCache.get(NAME);

            if (typeface == null) {
                typeface = Typeface.createFromAsset(getContext().getAssets(), "alice_reg.ttf");
                sTypefaceCache.put(NAME, typeface);

            }
            setTypeface(typeface);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
