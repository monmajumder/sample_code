package com.resistance.theresistance.logic;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Starts the application.
 * Created by andrewshiau on 10/26/15.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Player.class);
        ParseObject.registerSubclass(Game.class);
        Parse.initialize(this, "C5sIm3CQ2cGgW7CbD7XTDb9Ji0uiw6ouYuXGoWBL", "lBdhDvJITQQqgXBrimKvMy36at4o3aa5hW75SYev");
    }
}
