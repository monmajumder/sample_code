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
 * Defines the PlayerView that is the CircleLayout.
 */
public class PlayerView extends CircleLayout {
    String gameName;
    String playerName;
    public ArrayList<String> playerNames;

    /**
     * Constructor for the PlayerView.
     * @param context
     * @param attrs
     */
    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        this.addPlayerIcons();

    }

    /**
     * Adds a player to the PlayerView.
     * @param playerNumber
     */
    private void addPlayer(int playerNumber){

        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        TypedArray playerIds = getResources().obtainTypedArray(R.array.player_ids_ints);
        playerNames = GameController.updatePlayers(gameName);

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this.getContext());
        relativeLayout.setId(playerNumber);

        // Creating a new ImageView
        ImageView iv = new ImageView(this.getContext());
        iv.setImageResource(playerIcons.getResourceId(playerNumber, -1));
        iv.setId(playerNumber+520);


        // Defining the layout parameters of the ImageView
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);


        //Creating a new TextView
        MyTextView tv = new MyTextView(this.getContext());
        tv.setText(playerNames.get(playerNumber));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tv.setId(playerNumber+320);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50,
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

    /**
     * Adds the player icons to the view.
     */
    public void addPlayerIcons() {

        ArrayList<String> playerNames = GameController.updatePlayers(gameName);
        //Log.d("Player names size", String.valueOf(playerNames.size()));

        for (int i = 0; i < playerNames.size(); i++) {
            addPlayer(i);
            //Log.d("Looped", "looped!");
        }
    }
}
