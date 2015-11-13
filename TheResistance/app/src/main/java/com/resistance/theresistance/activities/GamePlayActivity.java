package com.resistance.theresistance.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.resistance.theresistance.R;
import com.resistance.theresistance.views.GamePlayFragment;

public class GamePlayActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return GamePlayFragment.newInstance("FirstFragment, Instance 1");
                //case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
                default: return GamePlayFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}

