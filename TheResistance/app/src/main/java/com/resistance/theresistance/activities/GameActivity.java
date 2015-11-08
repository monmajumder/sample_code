package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

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
        getSupportActionBar().hide();


        setContentView(R.layout.activity_game);
        // Get the message from the intent
        Intent intent = getIntent();
        String gameName = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        String gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.game_room_name)).setText(gameRoomStr);

        ImageView player1 = new ImageView(this);
        player1.setVisibility(View.VISIBLE);
    }
}
