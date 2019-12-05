package com.play.tube.music.videos.fragments.tabs;

import android.content.res.Configuration;
import android.os.Bundle;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.AlbumAdapter;
import com.play.tube.music.videos.api.Album;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Tag;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabAlbumsFragment extends TabBaseFragment<Album> {

    public static TabAlbumsFragment newInstance(Artist artist) {
        TabAlbumsFragment fragment = new TabAlbumsFragment();
        Bundle args = new Bundle();
        args.putSerializable("artist", artist);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabAlbumsFragment newInstance(Tag tag) {
        TabAlbumsFragment fragment = new TabAlbumsFragment();
        Bundle args = new Bundle();
        args.putSerializable("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabAlbumsFragment newInstance(String query) {
        TabAlbumsFragment fragment = new TabAlbumsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    public TabAlbumsFragment() {}

    @Override
    public String getSearchErrorMessage() {
        return getString(R.string.albums_not_found);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new AlbumAdapter(this, mDataSet);
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
        if (mArtist != null) {
            LastFM.getTopAlbums(mArtist, page, this);
        } else if (mTag != null)
            LastFM.getTopAlbums(mTag, page, this);
        else if (mQuery != null)
            LastFM.searchAlbums(mQuery, page, this);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
