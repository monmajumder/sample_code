package com.resistance.theresistance.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.views.PlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import ru.biovamp.widget.CircleLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class  HistoryFragment extends android.support.v4.app.Fragment {

    String gameName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        // Create UI components here.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        gameName = preferences.getString("gameName", "none");
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        addPlayerImages();
    }

    /**
     * Adds Images for each Player.
     */
    private void addPlayerImages() {
        ArrayList<Integer> row1 = new ArrayList<Integer>();
        row1.add(R.drawable.blank_transparency);
        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        int numPlayers = GameController.updatePlayers(gameName).size();
        for (int i = 0; i < numPlayers; i++) {
            row1.add(playerIcons.getResourceId(i, -1));
        }
        addRow(row1);
    }

    /**
     * Adds a row with the images given in parameter.
     * Will make a "table" if all images in every row are the same size.
     * @param resourceIds the ids of the images to be added. Use 0 for a blank space.
     */
    private void addRow(ArrayList<Integer> resourceIds) {
        /* Find Tablelayout defined in main.xml */
        TableLayout tl;
        tl = (TableLayout) getView().findViewById(R.id.Table);
        /* Create a new row to be added. */
        TableRow round = new TableRow(this.getActivity());
        round.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (Integer resourceId : resourceIds) {
            round = addObject(round, resourceId);
        }
        tl.addView(round, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    /**
     * Adds an object to a row.
     * @param round The round to be added onto.
     * @param resourceId The id of the resource to be added.
     * @return the update resource.
     */
    private TableRow addObject(TableRow round, Integer resourceId) {
        ImageView view = new ImageView(round.getContext());
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        view.setImageResource(resourceId);
        round.addView(view);
        return round;
    }

    /**
     * Converts dps to pixels.
     * @param dpLength the length in dp.
     * @return the length in pixels.
     */
    private int getPixelLength(int dpLength) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpLength * scale + 0.5f);
    }
}
