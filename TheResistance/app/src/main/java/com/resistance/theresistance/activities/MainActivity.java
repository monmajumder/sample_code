package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.R;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static String playerName;
    private static View alreadyExistsView;

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //This comment is here to fix issue.
        setContentView(R.layout.activity_main);
    }

    /**
     * Inflate the menu
     * @param menu the menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles action bar item.
     * @param item item that is called
     * @return if no inspection, true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when user clicks the Send button after typing name
     * @param view view that is called
     */
    public void enterName(View view) {
        alreadyExistsView= (View) findViewById(R.id.sorry_use_another_name);
        EditText editText = (EditText) findViewById(R.id.enter_name);
        playerName = editText.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("NameObject");
        query.whereEqualTo("Name", playerName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("name", "The retrieval succeeded");
                    if (count <= 0) {
                        createNewParseNameObject(playerName);
                        createIntent(playerName);
                    } else {
                        alreadyExistsView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("name", "The retrieval failed");
                }
            }
        });
    }

    /**
     * Creates a new Parse object for player name.
     * @param playerName name of the player
     */
    public void createNewParseNameObject(String playerName) {
        ParseObject nameObject = new ParseObject("NameObject");
        nameObject.put("Name", playerName);
        nameObject.saveInBackground();
    }

    /**
     * Create an intent
     * @param playerName name of the user
     */
    public void createIntent(String playerName) {
        Intent intent = new Intent(this, GameNameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, playerName);
        startActivity(intent);
    }
}
