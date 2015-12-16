package com.resistance.theresistance.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resistance.theresistance.R;

import java.util.ArrayList;

/**
 * Tracks the missions on the PlayFragment.
 */
public class MissionTracker extends RelativeLayout{

    ArrayList<ImageView> missionIconViews = new ArrayList<>();
    ArrayList<Integer> passIconIds = new ArrayList<>();
    ArrayList<Integer> failIconIds = new ArrayList<>();

    /**
     * Inialize text view with context and attributes.
     * @param context Context
     * @param attrs Attributes
     */
    public MissionTracker(Context context, AttributeSet attrs) {
        super(context, attrs);

        setIconIds();

        TypedArray missionIcons = getResources().obtainTypedArray(R.array.mission_imgs);
        TypedArray iconIDs = getResources().obtainTypedArray(R.array.mission_icon_ids);

        LayoutParams imageParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        LayoutParams imageParams2 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        LayoutParams imageParams3 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        LayoutParams imageParams4 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

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

        missionIconViews.add(iv1);
        missionIconViews.add(iv2);
        missionIconViews.add(iv3);
        missionIconViews.add(iv4);
        missionIconViews.add(iv5);

        this.addView(iv1);
        this.addView(iv2);
        this.addView(iv3);
        this.addView(iv4);
        this.addView(iv5);
    }

    /**
     * Changes the icon color on the mission tracker.
     * @param missNum Number of mission to be changed
     * @param pass True if mission passed, false if mission failed
     */
    public void changeIconColor (int missNum, boolean pass) {
        if (pass) {
            missionIconViews.get(missNum-1).setImageResource(passIconIds.get(missNum - 1));
        }
        else {
            //Log.d("something", "mission number is: " + missNum);
            missionIconViews.get(missNum-1).setImageResource(failIconIds.get(missNum-1));
        }
    }

    /**
     * Sets the ids on the icons.
     */
    private void setIconIds () {

        //Set pass icon id arraylist
        passIconIds.add(R.drawable.mission_icon_1_won);
        passIconIds.add(R.drawable.mission_icon_2_won);
        passIconIds.add(R.drawable.mission_icon_3_won);
        passIconIds.add(R.drawable.mission_icon_4_won);
        passIconIds.add(R.drawable.mission_icon_5_won);

        //Set fail icon id arraylist
        failIconIds.add(R.drawable.mission_icon_1_lost);
        failIconIds.add(R.drawable.mission_icon_2_lost);
        failIconIds.add(R.drawable.mission_icon_3_lost);
        failIconIds.add(R.drawable.mission_icon_4_lost);
        failIconIds.add(R.drawable.mission_icon_5_lost);
    }

}
