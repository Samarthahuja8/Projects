package com.app.farmerswikki.util.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

/**
 * Created by ORBITWS19 on 31-May-2017.
 */

public class TextViewLatoThin extends JustifiedTextView {

    private static final String TAG = "TextViewRegular";

    public TextViewLatoThin(Context context) {
        super(context);
        init(null);
    }
    public TextViewLatoThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public TextViewLatoThin(Context context, AttributeSet attrs, int defStyle) {
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
                Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "lato_thin.ttf");;
                setTypeface(myTypeface);
            }
        }
        catch (Exception e) {

            Log.e(TAG, e.getMessage(), e);
        }
    }

   /* @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // setTextIsSelectable doesn't work unless the text view is attached to the window
        // because it uses the window layout params to check if it can display the handles.
        if (Build.VERSION.SDK_INT > 10) {
            setTextIsSelectable(true);
        }
    }

    @Override
    public boolean onTouchEvent(final @NotNull MotionEvent event) {
        final Spannable text = (Spannable)getText();
        if (text != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                final Layout layout = getLayout();
                if (layout != null) {
                    // final int pos = getOffsetForPosition(event.getX(), event.getY()); // API >= 14 only
                    final int line = getLineAtCoordinate(layout, event.getY());
                    final int pos = getOffsetAtCoordinate(layout, line, event.getX());
                    final ClickableSpan[] links = text.getSpans(pos, pos, ClickableSpan.class);
                    if (links != null && links.length > 0) {
                        links[0].onClick(this);
                        return true;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private int getLineAtCoordinate(final @NotNull Layout layout, final float y) {
        final int max = getHeight() - getTotalPaddingBottom() - 1;
        final int v = Math.min(max, Math.max(0, (int)y - getTotalPaddingTop())) + getScrollY();
        return layout.getLineForVertical(v);
    }

    private int getOffsetAtCoordinate(final @NotNull Layout layout, final int line, final float x) {
        final int  max = getWidth() - getTotalPaddingRight() - 1;
        final int v = Math.min(max, Math.max(0, (int)x - getTotalPaddingLeft())) + getScrollX();
        return layout.getOffsetForHorizontal(line, v);
    }
*/
}

