package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.resistance.theresistance.R;
import com.resistance.theresistance.fragments.HistoryFragment;
import com.resistance.theresistance.fragments.PlayFragment;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends FragmentActivity {

    private static Intent intent;

    /**
     * Identifier for the first fragment.
     */
    public static final int FRAGMENT_ONE = 0;

    /**
     * Identifier for the second fragment.
     */
    public static final int FRAGMENT_TWO = 1;

    /**
     * Number of total fragments.
     */
    public static final int FRAGMENTS = 2;

    /**
     * The adapter definition of the fragments.
     */
    private FragmentPagerAdapter _fragmentPagerAdapter;

    /**
     * The ViewPager that hosts the section contents.
     */
    private ViewPager _viewPager;

    private PlayFragment playFragment;
    private HistoryFragment historyFragment;

    /**
     * List of fragments.
     */
    private List<android.support.v4.app.Fragment> _fragments = new ArrayList<android.support.v4.app.Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        this.playFragment = new PlayFragment();
        this.historyFragment = new HistoryFragment();

        // Create fragments.
        _fragments.add(FRAGMENT_ONE, this.playFragment);
        _fragments.add(FRAGMENT_TWO, this.historyFragment);

        // Setup the fragments, defining the number of fragments, the screens and titles.
        _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return FRAGMENTS;
            }


            @Override
            public Fragment getItem(final int position) {
                return _fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case FRAGMENT_ONE:
                        return "Title One";
                    case FRAGMENT_TWO:
                        return "Title Two";
                    default:
                        return null;
                }
            }
        };
        _viewPager = (ViewPager) findViewById(R.id.pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
    }

    /**
     * Called when a leader clicks "done" when choosing missionaries.
     * @param v Button view
     */
    public void leaderChoosingMissionaries(View v) {
        playFragment.leaderChoosingMissionaries(v);
    }

    /**
     * Called when a player clicks "accept" when choosing missionaries.
     * @param v Button view
     */
    public void acceptMissionaryTeam(View v) {
        playFragment.acceptMissionaryTeam(v);
    }

    /**
     * Called when a player clicks "reject" when choosing missionaries.
     * @param v Button view
     */
    public void rejectMissionaryTeam(View v) {
        playFragment.rejectMissionaryTeam(v);
    }

    /**
     * Called when a missionary clicks "pass" when going on a mission.
     * @param v Button view
     */
    public void passMission(View v) {
        playFragment.passMission(v);
    }

    /**
     * Called when a missionary clicks "fail" when going on a mission.
     * @param v Button view
     */
    public void failMission(View v) {
        playFragment.failMission(v);
    }

    /**
     * Returns the play fragment in an activity.
     * @return play fragment
     */
    public PlayFragment getPlayFragment() {
        return this.playFragment;
    }

    /**
     * Returns the history fragment in an activity.
     * @return history fragment
     */
    public HistoryFragment getHistoryFragment() {
        return this.historyFragment;
    }
}