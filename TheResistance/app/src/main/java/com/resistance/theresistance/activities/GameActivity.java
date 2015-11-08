package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.resistance.theresistance.R;

/**
 * Game Activity
 */
public class GameActivity extends AppCompatActivity {

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Get the message from the intent
        Intent intent = getIntent();

        String gameRoomStr = this.getResources().getString(R.string.game_room_name);
        String gameName = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.game_room_name)).setText(gameRoomStr);

        // Create the text view
//        com.resistance.theresistance.views.MyTextView gameNameView = new com.resistance.theresistance.views.MyTextView(this);
//        gameNameView.setTextSize(40);
//        gameNameView.setText(gameName);
        //gameNameView.setLayoutParams();

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.content_game);

    }
}
