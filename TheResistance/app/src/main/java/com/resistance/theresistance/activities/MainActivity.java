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
import com.parse.ParseQuery;
import com.resistance.theresistance.R;

import com.parse.Parse;
import com.parse.ParseObject;
import com.resistance.theresistance.logic.GameController;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "come.resistance.theresistance.MESSAGE";
    private static String playerName;
    private static Intent intent;
    private static View alreadyExistsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "C5sIm3CQ2cGgW7CbD7XTDb9Ji0uiw6ouYuXGoWBL", "lBdhDvJITQQqgXBrimKvMy36at4o3aa5hW75SYev");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    /** Called when user clicks the Send button after typing name */
    public void enterName(View view) {
        alreadyExistsView= (View) findViewById(R.id.sorry_use_another_name);
        intent = new Intent(this, GameNameActivity.class);
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
                        ParseObject nameObject = new ParseObject("NameObject");
                        nameObject.put("Name", playerName);
                        nameObject.saveInBackground();
                        intent.putExtra(EXTRA_MESSAGE, playerName);
                        startActivity(intent);
                    } else {
                        intent.putExtra(EXTRA_MESSAGE, "Name is taken.");
                        alreadyExistsView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("name", "The retrieval failed");
                }
            }
        });
    }
}
