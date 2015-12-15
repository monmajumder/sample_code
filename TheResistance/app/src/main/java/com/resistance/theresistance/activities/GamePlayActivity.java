package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

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

    /**
     * List of fragments.
     */
    private List<android.support.v4.app.Fragment> _fragments = new ArrayList<android.support.v4.app.Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Create fragments.
        _fragments.add(FRAGMENT_ONE, new PlayFragment());
        _fragments.add(FRAGMENT_TWO, new HistoryFragment());

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

    public void leaderChoosingMissionaries(View v) {
        PlayFragment fragment = (PlayFragment) this.getSupportFragmentManager().findFragmentByTag("play_fragment");
        fragment.leaderChoosingMissionaries(v);
    }

    public void acceptMissionaryTeam(View v) {
        PlayFragment fragment = (PlayFragment) this.getSupportFragmentManager().findFragmentByTag("play_fragment");
        fragment.acceptMissionaryTeam(v);
    }

    public void rejectMissionaryTeam(View v) {
        PlayFragment fragment = (PlayFragment) this.getSupportFragmentManager().findFragmentByTag("play_fragment");
        fragment.rejectMissionaryTeam(v);
    }

    public void passMission(View v) {
        PlayFragment fragment = (PlayFragment) this.getSupportFragmentManager().findFragmentByTag("play_fragment");
        fragment.passMission(v);
    }

    public void failMission(View v) {
        PlayFragment fragment = (PlayFragment) this.getSupportFragmentManager().findFragmentByTag("play_fragment");
        fragment.failMission(v);
    }
}