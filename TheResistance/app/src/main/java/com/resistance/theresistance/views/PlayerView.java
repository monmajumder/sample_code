package com.resistance.theresistance.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;

import java.util.ArrayList;

import ru.biovamp.widget.CircleLayout;

/**
 * Created by jwagner on 12/11/15.
 */
public class PlayerView extends CircleLayout {
    String gameName;
    String playerName;
    public ArrayList<String> playerNames;

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        this.addPlayerIcons();

    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }

    private void addPlayer(int playerNumber){

        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        TypedArray playerIds = getResources().obtainTypedArray(R.array.player_ids_ints);
        playerNames = GameController.updatePlayers(gameName);
        //CircleLayout circleLayout = (CircleLayout) view.findViewById(R.id.circleview2);
//        CircleLayout circleLayout = new CircleLayout(this.getContext());

//        CircleLayout.LayoutParams circleParams = new CircleLayout.LayoutParams(
//                CircleLayout.LayoutParams.WRAP_CONTENT,
//                CircleLayout.LayoutParams.WRAP_CONTENT);

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this.getContext());

        // Creating a new ImageView
        ImageView iv = new ImageView(this.getContext());
        iv.setImageResource(playerIcons.getResourceId(playerNumber, -1));
        iv.setId(playerIds.getResourceId(playerNumber, -1));

        // Defining the layout parameters of the ImageView
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        //Creating a new TextView
        MyTextView tv = new MyTextView(this.getContext());
        tv.setText(playerNames.get(playerNumber));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                44,
                this.getResources().getDisplayMetrics()
        );

        //Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.setMargins(0, px, 0, 0);

        // Setting the parameters on the views
        iv.setLayoutParams(imageParams);
        tv.setLayoutParams(textParams);
       // this.setLayoutParams(circleParams);
        // circleLayout.setLayoutParams(circleParams);

        // Adding the ImageView and TextView to the RelativeLayout as children
        relativeLayout.addView(iv);
        relativeLayout.addView(tv);
        relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        // Setting the RelativeLayout as our content view
        this.addView(relativeLayout);
    }

    public void addPlayerIcons() {

        ArrayList<String> playerNames = GameController.updatePlayers(gameName);
        Log.d("Player names size", String.valueOf(playerNames.size()));

        for (int i = 0; i < playerNames.size(); i++) {
            addPlayer(i);
            Log.d("Looped", "looped!");
        }
    }
}
