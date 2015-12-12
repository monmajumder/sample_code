package com.resistance.theresistance.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resistance.theresistance.R;

/**
 * Created by jwagner on 12/11/15.
 */
public class MissionTracker extends RelativeLayout{
    public MissionTracker(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray missionIcons = getResources().obtainTypedArray(R.array.mission_imgs);
        TypedArray iconIDs = getResources().obtainTypedArray(R.array.mission_icon_ids);

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams imageParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams imageParams3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams imageParams4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        ImageView iv1 = new ImageView(this.getContext());
        iv1.setId(iconIDs.getResourceId(0, -1));
        iv1.setImageResource(R.drawable.mission_icon_1);

        ImageView iv2 = new ImageView(this.getContext());
        iv2.setId(iconIDs.getResourceId(1, -1));
        iv2.setImageResource(R.drawable.mission_icon_2);
        imageParams.addRule(RelativeLayout.RIGHT_OF, iv1.getId());
        iv2.setLayoutParams(imageParams);

        ImageView iv3 = new ImageView(this.getContext());
        iv3.setId(iconIDs.getResourceId(2, -1));
        iv3.setImageResource(R.drawable.mission_icon_3);
        imageParams2.addRule(RelativeLayout.RIGHT_OF, iv2.getId());
        iv3.setLayoutParams(imageParams2);

        ImageView iv4 = new ImageView(this.getContext());
        iv4.setId(iconIDs.getResourceId(3, -1));
        iv4.setImageResource(R.drawable.mission_icon_4);
        imageParams3.addRule(RelativeLayout.RIGHT_OF, iv3.getId());
        iv4.setLayoutParams(imageParams3);

        ImageView iv5 = new ImageView(this.getContext());
        iv5.setId(iconIDs.getResourceId(4, -1));
        iv5.setImageResource(R.drawable.mission_icon_5);
        imageParams4.addRule(RelativeLayout.RIGHT_OF, iv4.getId());
        iv5.setLayoutParams(imageParams4);


        this.addView(iv1);
        this.addView(iv2);
        this.addView(iv3);
        this.addView(iv4);
        this.addView(iv5);
    }

}
