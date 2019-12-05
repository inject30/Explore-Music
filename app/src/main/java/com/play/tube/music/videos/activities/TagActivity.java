package com.play.tube.music.videos.activities;

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
import com.play.tube.music.videos.api.Tag;
import com.play.tube.music.videos.fragments.tabs.TabAlbumsFragment;
import com.play.tube.music.videos.fragments.tabs.TabArtistsFragment;
import com.play.tube.music.videos.fragments.tabs.TabTracksFragment;

public class TagActivity extends AppCompatActivity {

    private Tag mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mTag = (Tag) intent.getSerializableExtra("tag");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mTag.getName());

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
                    return TabAlbumsFragment.newInstance(mTag);
                case 1:
                    return TabArtistsFragment.newInstance(mTag);
                case 2:
                    return TabTracksFragment.newInstance(mTag);
                default:
                    return null;
            }
        }
    }
}
