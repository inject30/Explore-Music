package com.play.tube.music.videos.fragments.tabs;

import android.content.res.Configuration;
import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.ArtistAdapter;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.LastFM;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabSimilarFragment extends TabBaseFragment<Artist> {

    public static TabSimilarFragment newInstance(Artist artist) {
        TabSimilarFragment fragment = new TabSimilarFragment();
        Bundle args = new Bundle();
        args.putSerializable("artist", artist);
        fragment.setArguments(args);
        return fragment;
    }

    public TabSimilarFragment() {}

    @Override
    public String getSearchErrorMessage() {
        return getString(R.string.similar_artists_not_found);
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
        LastFM.getSimilarArtists(mArtist, page, this);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
