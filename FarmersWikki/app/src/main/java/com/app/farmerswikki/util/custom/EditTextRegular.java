package com.app.farmerswikki.util.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;


public class EditTextRegular extends EditText {
    private static final String TAG = "CustomeditText";

    public EditTextRegular(Context context) {
        super(context);
        init(context);
    }
    public EditTextRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public EditTextRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * To set the custom attribute to the View
     * @param
     */
    private void init(Context mContext) {
        try {
            if (mContext!=null) {

                    Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "alice_reg.ttf");
                    setTypeface(myTypeface);


            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

}
