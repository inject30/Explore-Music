package com.play.tube.music.videos.fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.ArtistAdapter;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.LastFM;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopArtistsFragment extends BaseFragment<Artist> {

    private String mCountry;

    public static TopArtistsFragment newInstance(String country) {
        TopArtistsFragment fragment = new TopArtistsFragment();
        Bundle args = new Bundle();
        args.putString("country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            if (getArguments().getString("country") != null)
                mCountry = getArguments().getString("country");
        }

        super.onCreate(savedInstanceState);

    }

    @Override
    public String getTitle() {
        if (mCountry != null)
            return getString(R.string.local_top_artists);
        else
            return getString(R.string.top_artists);
    }

    @Override
    public String getSearchErrorMessage() {
        return getString(R.string.artists_not_found);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new ArtistAdapter(this, mDataSet);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        int spanCount;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            spanCount = getResources().getInteger(R.integer.horizontal_columns_count);
        else
            spanCount = getResources().getInteger(R.integer.vertical_columns_count);
        return new GridLayoutManager(getContext(), spanCount);
    }

    @Override
    public void getData(int page) {
        updating = true;
        if (mCountry != null) {
            LastFM.getLocalTopArtists(mCountry, page, this);
        } else {
            LastFM.getTopArtists(page, this);
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
