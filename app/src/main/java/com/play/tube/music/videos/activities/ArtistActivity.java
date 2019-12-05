package com.play.tube.music.videos.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.play.tube.music.videos.R;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.fragments.tabs.TabAlbumsFragment;
import com.play.tube.music.videos.fragments.tabs.TabSimilarFragment;
import com.play.tube.music.videos.fragments.tabs.TabTracksFragment;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ArtistActivity extends AppCompatActivity {

    private Artist mArtist;
    private ImageView mHeaderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mArtist = (Artist) intent.getSerializableExtra("artist");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mArtist.getName());

        mHeaderImage = findViewById(R.id.header_image);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHeaderImage.getLayoutParams();
            params.height = displaymetrics.widthPixels;
            mHeaderImage.setLayoutParams(params);
        }

        String url = null;
        Map<String, String> images = mArtist.getImages();
        if (images.get("extralarge") != null && !images.get("extralarge").isEmpty())
            url = images.get("extralarge");
        else if (images.get("mega") != null && !images.get("mega").isEmpty())
            url = images.get("mega");
        else if (images.get("large") != null && !images.get("large").isEmpty())
            url = images.get("large");
        else if (images.get("medium") != null && !images.get("medium").isEmpty())
            url = images.get("medium");
        else if (images.get("small") != null && !images.get("small").isEmpty())
            url = images.get("small");
        else if (images.get("") != null && !images.get("").isEmpty())
            url = images.get("");

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.no_cover_big)
                .error(R.drawable.no_cover_big)
                .into(mHeaderImage);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHeaderImage.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            params.height = getResources().getDimensionPixelSize(R.dimen.header_height_landscape);
        } else {
            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            params.height = displaymetrics.widthPixels;
        }

        mHeaderImage.setLayoutParams(params);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private class TabsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabs = {
                getString(R.string.tab_tracks),
                getString(R.string.tab_albums),
                getString(R.string.tab_similar)
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
                    return TabTracksFragment.newInstance(mArtist);
                case 1:
                    return TabAlbumsFragment.newInstance(mArtist);
                case 2:
                    return TabSimilarFragment.newInstance(mArtist);
                default:
                    return null;
            }
        }
    }
}
