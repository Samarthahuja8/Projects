package com.app.farmerswikki.util.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by Admin1 on 7/21/2016.
 */
public class ButtonBold extends android.support.v7.widget.AppCompatButton {
    private final static String NAME = "skodaFont";
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);

    public ButtonBold(Context context) {
        super(context);
        init(null);
    }

    public ButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ButtonBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        try {
            Typeface typeface = sTypefaceCache.get(NAME);

            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.getAssets(),
                        "text_bold.ttf");
                sTypefaceCache.put(NAME, typeface);
            }
            setTypeface(typeface);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

