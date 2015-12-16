package com.resistance.theresistance.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Extends EditTextView.
 */
public class MyEditTextView extends EditText {

    /**
     * Initialize edit text view with context
     * @param context context
     */
    public MyEditTextView(Context context) {
        super(context);
        init();

    }

    /**
     * Initialize edit text view with context, attrs
     * @param context context
     * @param attrs attrs
     */
    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialize edit text view with context, attrs, defStyle
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public MyEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initialize font.
     */
    private void init() {
        Typeface font=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        this.setTypeface(font);
    }

    /**
     * Set typeface and style with Helvetica Neue
     * @param tf typeface
     * @param style style (font)
     */
    @Override
    public void setTypeface(Typeface tf, int style) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        super.setTypeface(tf, style);
    }

    /**
     * Set typeface Helvetica Neue.
     * @param tf typeface
     */
    @Override
    public void setTypeface(Typeface tf) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        super.setTypeface(tf);
    }
}
