package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.resistance.theresistance.R;
import com.resistance.theresistance.views.GamePlayActivity;

/**
 * Game Activity
 */
public class GameWaitingActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    public final static String ANOTHER_EXTRA_MESSAGE = "";
    private static Intent intent;
    Button startButton;
    String gameName;
    String gameRoomStr;
    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        // Get the message from the intent
        intent = getIntent();
        gameName = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.game_room_name)).setText(gameRoomStr);

        ImageView player1 = new ImageView(this);
        player1.setVisibility(View.VISIBLE);

        //Start GameActivity on start button click
        startButton = (Button) findViewById(R.id.button);
        // Capture button clicks
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {

                startGame();
            }
        });
    }


    public void startGame() {
        intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(ANOTHER_EXTRA_MESSAGE, gameName);
        startActivity(intent);
    }
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
//        query.whereEqualTo("Name", gameName);
//        query.countInBackground(new CountCallback() {
//            @Override
//            public void done(int count, ParseException e) {
//                if (e == null) {
//                    Log.d("gameName", "The retrieval succeeded");
//                    if (count <= 0) {
//                        createNewParseGameObject(gameName);
//                        createIntent(gameName);
//                    } else {
//                        secondView.setVisibility(View.INVISIBLE);
//                        existsView.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    Log.d("gameName", "The retrieval failed");
//                }
//            }
//        });

}
