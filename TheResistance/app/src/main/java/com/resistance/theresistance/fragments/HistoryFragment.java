package com.resistance.theresistance.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.Round;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class  HistoryFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, null);

        // Create UI components here.

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addRow();
    }

    private void addRow() {
        /* Find Tablelayout defined in main.xml */
        TableLayout tl;
        tl = (TableLayout) getView().findViewById(R.id.Table);
        /* Create a new row to be added. */
        TableRow round = new TableRow(this.getActivity());
        round.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        ImageView view = new ImageView(round.getContext());
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        view.setImageResource(R.drawable.player_icon_indigo);
        round.addView(view);

        ImageView view2 = new ImageView(round.getContext());
        view2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        view2.setImageResource(R.drawable.player_icon_gray);
        round.addView(view2);


        tl.addView(round, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void addMission() {

    }

    public void addRound(Round r) {
        Round round = r;
        ArrayList<String> missionaries = (ArrayList<String>) round.getMissionaries();


        //add a new row
        addRow();
        /* Find Tablelayout defined in main.xml */
        TableLayout tl;
        tl = (TableLayout) getView().findViewById(R.id.Table);

        /* Extract data from round */
    }
}
