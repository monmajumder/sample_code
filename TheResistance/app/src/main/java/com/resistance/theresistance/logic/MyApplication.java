package com.resistance.theresistance.logic;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Starts the application.
 * Created by andrewshiau on 10/26/15.
 */

public class MyApplication extends Application {

    public GameController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //Register Parse objects
        ParseObject.registerSubclass(Player.class);
        ParseObject.registerSubclass(Game.class);
        ParseObject.registerSubclass(Round.class);
        ParseObject.registerSubclass(Mission.class);

        //Initialize Parse
        Parse.initialize(this, "C5sIm3CQ2cGgW7CbD7XTDb9Ji0uiw6ouYuXGoWBL", "lBdhDvJITQQqgXBrimKvMy36at4o3aa5hW75SYev");

        //Create GameController object
        this.controller = new GameController();
    }
}
