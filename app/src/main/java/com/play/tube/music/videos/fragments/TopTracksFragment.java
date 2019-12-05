package com.play.tube.music.videos.fragments;

import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.TrackAdapter;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Track;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopTracksFragment extends BaseFragment<Track> {

    private String mCountry;

    public static TopTracksFragment newInstance(String country) {
        TopTracksFragment fragment = new TopTracksFragment();
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
            return getString(R.string.local_top_tracks);
        else
            return getString(R.string.top_tracks);
    }

    @Override
    public String getSearchErrorMessage() {
        return getString(R.string.tracks_not_found);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new TrackAdapter(getActivity(), this, mDataSet);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void getData(int page) {
        updating = true;
        if (mCountry != null) {
            LastFM.getLocalTopTracks(mCountry, page, this);
        } else {
            LastFM.getTopTracks(page, this);
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
