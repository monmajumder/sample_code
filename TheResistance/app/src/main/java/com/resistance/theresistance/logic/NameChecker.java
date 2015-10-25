package com.resistance.theresistance.logic;

import android.util.Log;

import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

/**
 * Created by Candace on 10/24/2015.
 */
public class NameChecker {

    private static String playerName;
    private static boolean added;

    public static void newName(String name) throws NameExistsException {
        playerName = name;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("NameObject");
        query.whereEqualTo("Name", playerName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("name", "The retrieval succeeded");
                    if (count <= 0) {
                        ParseObject nameObject = new ParseObject("NameObject");
                        nameObject.put("Name", playerName);
                        nameObject.saveInBackground();
                        added = true;
                        Log.d("name","The name was added");
                    } else {
                        Log.d("name","The name already exists");
                        added = false;
                    }
                } else {
                    Log.d("name", "The retrieval failed");
                }
            }
        });
    }

    /**
     * Exception for when a name exists
     */
    public static class NameExistsException extends Exception {
        public NameExistsException(String message) {
            super(message);
        }
    }
}
