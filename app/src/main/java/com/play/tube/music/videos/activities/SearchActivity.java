package com.play.tube.music.videos.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.play.tube.music.videos.R;
import com.play.tube.music.videos.fragments.tabs.TabAlbumsFragment;
import com.play.tube.music.videos.fragments.tabs.TabArtistsFragment;
import com.play.tube.music.videos.fragments.tabs.TabTracksFragment;

public class SearchActivity extends AppCompatActivity {

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(mQuery);
        }

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class TabsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabs = {
                getString(R.string.tab_albums),
                getString(R.string.tab_artists),
                getString(R.string.tab_tracks)
        };

        TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TabAlbumsFragment.newInstance(mQuery);
                case 1:
                    return TabArtistsFragment.newInstance(mQuery);
                case 2:
                    return TabTracksFragment.newInstance(mQuery);
                default:
                    return null;
            }
        }
    }
}