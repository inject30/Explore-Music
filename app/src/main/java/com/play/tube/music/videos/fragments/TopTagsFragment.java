package com.play.tube.music.videos.fragments;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.TagAdapter;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Tag;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopTagsFragment extends BaseFragment<Tag> {

    @Override
    public String getTitle() {
        return getString(R.string.top_tags);
    }

    @Override
    public String getSearchErrorMessage() {
        return getString(R.string.tags_not_found);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new TagAdapter(mDataSet);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void getData(int page) {
        updating = true;
        LastFM.getTopTags(page, this);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
