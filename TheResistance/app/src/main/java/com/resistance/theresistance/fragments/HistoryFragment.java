package com.resistance.theresistance.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.logic.Round;
import com.resistance.theresistance.views.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class  HistoryFragment extends android.support.v4.app.Fragment {

    private String gameName;
    private int currentMissionNumber;
    ArrayList<String> players; //real list of players
    //ArrayList<String> players = new ArrayList<>(); //DELETE - ONLY FOR TESTING PURPOSES
    //private Round test; //DELETE - ONLY FOR TESTING PURPOSES

    public HistoryFragment() {
        currentMissionNumber = 0;
    }

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

        players = GameController.updatePlayers(gameName);
        //dummyPlayers(); // testing purposes
        addPlayerImages();
        addNames();

        /**
        Round dummy = dummyRound(); // testing
        addRoundInNewMission(dummy);
        addRound(dummy); //testing
        addRound(dummy);
        addRoundInNewMission(dummy);
        addRound(dummy);
        addRound(dummy);
        addRoundInNewMission(dummy);
        addRoundInNewMission(dummy);
        addRoundInNewMission(dummy);
        addRound(dummy);
        addRound(dummy); **/

    }

    /**
     * Adds Player names
     */
    private void addNames() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<MyTextView> textViews = new ArrayList<MyTextView>();
        MyTextView v;
        for (int i = 0; i < players.size(); i++) {
            v = new MyTextView(this.getContext());
            /*extract substring*/
            String subname = (players.get(i) + "  ").substring(0,3);
            /*add spaces to substring in case name < 3 characters*/
            names.add(i, "    " + subname + "   ");
            /* set the textview text*/
            v.setText(names.get(i));
            /* add to the textviews array*/
            textViews.add(v);
        }

        TableLayout tl;
        tl = (TableLayout) getView().findViewById(R.id.Table);
        /* Create a new row to be added. */
        TableRow round = new TableRow(this.getActivity());
//        round.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        round = addObject(round, R.drawable.blank_transparency);
        for (int i = 0; i < textViews.size(); i++) {
            round = addText(round, textViews.get(i));
        }
        tl.addView(round, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    /**
     * Adds Images for each Player.
     */
    private void addPlayerImages() {
        ArrayList<Integer> row1 = new ArrayList<Integer>();
        row1.add(R.drawable.blank_transparency);
        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        int numPlayers = players.size();
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
     * Add the player names under the player image
     * @param names of player names
     */
    private void addNameUnderImage(ArrayList<String> names) {
        TableLayout tl;
        tl = (TableLayout) getView().findViewById(R.id.Table);
        /* Create a new row to be added. */
        TableRow round = new TableRow(this.getActivity());
        round.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (String name : names) {
            round = addObject(round, 1);
        }

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

    private TableRow addText(TableRow round, TextView view)
    {
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        round.addView(view);
        return round;
    }
    /**
     * Adds a round in a new mission
     * @param r the round to be added
     */
    public void addRoundInNewMission(Round r) {
        addRoundBlocks(r, true);
        currentMissionNumber++;
    }

    /**
     * Adds a round to the history
     * @param r the round to be added
     */
    public void addRound(Round r) {
        addRoundBlocks(r,false);
    }
    /**
     * Adds a round's outcomes to the history
     * @param r the round to be added
     * @param isNewMission whether a new mission is being added
     */
    private void addRoundBlocks(Round r,boolean isNewMission) {
        Round round = r;

        List<String> missionaries = round.getMissionaries();

        ArrayList<String> yesVotes = (round.getAssentors() != null) ? ((ArrayList<String>) round.getAssentors()) : new ArrayList<String>();
        ArrayList<Integer> resourceIDs = new ArrayList<>();
        String leader = round.getLeader();
        boolean isMissionary, votedYes, isLeader;
        isLeader = false;
        isMissionary = false;
        votedYes = false;

        if (isNewMission) {
            TypedArray historyMissionNumberIcons = getResources().obtainTypedArray(R.array.history_mission_imgs);
            resourceIDs.add(historyMissionNumberIcons.getResourceId(currentMissionNumber, -1));
            //resourceIDs.add(R.drawable.history_mission_icon_1);
        }
        else {
            resourceIDs.add(R.drawable.blank_transparency);
        }

        //Go through the players array to check for which box each one needs
        for (int i= 0; i < players.size(); i++) {


            //check to see if the player is the mission leader
            if (players.get(i).equals(leader)) {
                isLeader = true;
            }

            //check to see if the player is a missionary
            for (int x = 0; x < missionaries.size(); x++) {
                if (missionaries.get(x).equals(players.get(i))) {
                    isMissionary = true;
                }
            }

            //check to see if the player voted yes
            for (int y = 0; y < yesVotes.size(); y++) {
                if (yesVotes.get(y).equals(players.get(i))) {
                    votedYes = true;
                }
            }

            //Check for the different combinations of resource ids
            if (isLeader && isMissionary && votedYes) {
                resourceIDs.add(R.drawable.green_box_star_m);
            }
            else if (isLeader && isMissionary) {
                resourceIDs.add(R.drawable.red_box_star_m);
            }
            else if (isLeader && votedYes) {
                resourceIDs.add(R.drawable.green_box_star);
            }
            else if (isLeader) {
                resourceIDs.add(R.drawable.red_box_star);
            }
            else if(votedYes && isMissionary) {
                resourceIDs.add(R.drawable.green_box_m);
            }
            else if(isMissionary) {
                resourceIDs.add(R.drawable.red_box_m);
            }
            else if (votedYes) {
                resourceIDs.add(R.drawable.green_box_empty);
            }
            else {
                resourceIDs.add(R.drawable.red_box_empty);
            }

            //set all the variables to false again for the next player
            isLeader = false;
            isMissionary = false;
            votedYes = false;
        }

        //add a new row
        addRow(resourceIDs);

        /* Extract data from round */
    }


    /**
     * Fills in a dummy arraylist of players
     * DELETE - FOR TESTING PURPOSES ONLY
     */
    private void dummyPlayers() {
        //Add players
        players.add("Monica");
        players.add("Andrew");
        players.add("Jenny");
        players.add("Mindy");
        players.add("Candace");
        players.add("6");
        players.add("7");
    }

/*    *//**
     * Create a dummy Round
     * Delete - Testing Purposes only
     * @return Round
     *//*
    private Round dummyRound() {

        //missionaries arraylist
        ArrayList<String> tempMiss = new ArrayList<>();
        tempMiss.add("Monica");
        tempMiss.add("Andrew");
        tempMiss.add("Jenny");

        //assentors arraylist
        ArrayList<String> tempAss = new ArrayList<>();
        tempAss.add("Monica");
        tempAss.add("Andrew");

        //Create the round - set leader, missionaries, assentors
        test = new Round();
        test.setLeader("Monica");
        test.setMissionaries(tempMiss);
        test.setAssentors(tempAss);

        return test;
    }*/

}
