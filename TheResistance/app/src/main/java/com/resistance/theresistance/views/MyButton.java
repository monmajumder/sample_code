package com.resistance.theresistance.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by jwagner on 10/23/15.
 */
public class MyButton extends Button {

    //This comment is here to fix issue.
    public MyButton(Context context) {
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface font=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        this.setTypeface(font);
    }
    @Override
    public void setTypeface(Typeface tf, int style) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueDeskInterface.ttf");
        super.setTypeface(tf);
    }

}
