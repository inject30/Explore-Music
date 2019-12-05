package com.play.tube.music.videos.fragments.tabs;

import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.TrackAdapter;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Tag;
import com.play.tube.music.videos.api.Track;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabTracksFragment extends TabBaseFragment<Track> {

    public static TabTracksFragment newInstance(Artist artist) {
        TabTracksFragment fragment = new TabTracksFragment();
        Bundle args = new Bundle();
        args.putSerializable("artist", artist);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabTracksFragment newInstance(Tag tag) {
        TabTracksFragment fragment = new TabTracksFragment();
        Bundle args = new Bundle();
        args.putSerializable("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabTracksFragment newInstance(String query) {
        TabTracksFragment fragment = new TabTracksFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }


    public TabTracksFragment() {}

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
        if (mArtist != null) {
            LastFM.getTopTracks(mArtist, page, this);
        } else if (mTag != null)
            LastFM.getTopTracks(mTag, page, this);
        else if (mQuery != null) {
            LastFM.searchTracks(mQuery, page, this);
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
