package com.app.farmerswikki.util.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

/**
 * Created by web-shuttle-2 on 13-May-16.
 */
public class AutoCompleteText extends AutoCompleteTextView {
    private static final String TAG = "TextViewMedium";

    public AutoCompleteText(Context context) {
        super(context);
        init(null);
    }
    public AutoCompleteText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public AutoCompleteText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * To set the custom attribute to the View
     * @param context
     */
    private void init(Context context) {
        try {
            if (context!=null) {
                Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "alice_reg.ttf");;
                setTypeface(myTypeface);
            }
        }
        catch (Exception e) {

            Log.e(TAG, e.getMessage(), e);
        }
    }

}
