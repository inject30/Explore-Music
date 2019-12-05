package com.play.tube.music.videos.fragments.tabs;

import android.content.res.Configuration;
import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.ArtistAdapter;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Tag;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabArtistsFragment extends TabBaseFragment<Artist> {

    public static TabArtistsFragment newInstance(Tag tag) {
        TabArtistsFragment fragment = new TabArtistsFragment();
        Bundle args = new Bundle();
        args.putSerializable("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabArtistsFragment newInstance(String query) {
        TabArtistsFragment fragment = new TabArtistsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
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
        if (mTag != null)
            LastFM.getTopArtists(mTag, page, this);
        else if (mQuery != null)
            LastFM.searchArtists(mQuery, page, this);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
