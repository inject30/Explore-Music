package com.play.tube.music.videos.fragments.tabs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.api.Attributes;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Model;
import com.play.tube.music.videos.api.Tag;
import com.play.tube.music.videos.fragments.State;
import com.play.tube.music.videos.listeners.ListInteractionsListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class TabBaseFragment<T extends Model> extends Fragment implements LastFM.Listener<T>, ListInteractionsListener {

    Artist mArtist;
    Tag mTag;
    String mQuery;
    public List<T> mDataSet = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private View mErrorContainer;
    private TextView mErrorMessage;

    private Attributes mAttributes;
    public boolean updating = false;

    private State mState = State.UNKNOWN;

    public abstract String getSearchErrorMessage();

    public abstract RecyclerView.Adapter createAdapter();

    public abstract RecyclerView.LayoutManager createLayoutManager();

    public abstract void getData(int page);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().get("artist") != null) {
                mArtist = (Artist) getArguments().get("artist");
            } else if (getArguments().get("tag") != null) {
                mTag = (Tag) getArguments().get("tag");
            } else if (getArguments().get("query") != null)
                mQuery = getArguments().getString("query");
            getData(1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);

        mErrorContainer = view.findViewById(R.id.error_container);
        mErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorContainer.setVisibility(View.GONE);
                getData(1);
            }
        });
        mErrorMessage = view.findViewById(R.id.error_message);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = createLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = createAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            mRecyclerView.setPadding(padding, mRecyclerView.getPaddingTop(), padding, mRecyclerView.getPaddingBottom());
        } else {
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
            mRecyclerView.setPadding(padding, mRecyclerView.getPaddingTop(), padding, mRecyclerView.getPaddingBottom());
        }

        switch (mState) {
            case UNKNOWN:
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
                break;
            case INITIALIZED:
                if (mDataSet.isEmpty()) {
                    mErrorMessage.setText(getSearchErrorMessage());
                    mErrorContainer.setVisibility(View.VISIBLE);
                }
                break;
            case ERROR:
                mErrorMessage.setText(R.string.network_error);
                mErrorContainer.setVisibility(View.VISIBLE);
                break;
        }

        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            mRecyclerView.setPadding(padding, mRecyclerView.getPaddingTop(), padding, mRecyclerView.getPaddingBottom());

            if (mLayoutManager instanceof GridLayoutManager) {
                int spanCount = getResources().getInteger(R.integer.horizontal_columns_count);
                ((GridLayoutManager) mLayoutManager).setSpanCount(spanCount);
            }
        } else {
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
            mRecyclerView.setPadding(padding, mRecyclerView.getPaddingTop(), padding, mRecyclerView.getPaddingBottom());

            if (mLayoutManager instanceof GridLayoutManager) {
                int spanCount = getResources().getInteger(R.integer.vertical_columns_count);
                ((GridLayoutManager) mLayoutManager).setSpanCount(spanCount);
            }
        }
    }

    @Override
    public void onResponse(List<T> result, Attributes attributes) {
        mState = State.INITIALIZED;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        updating = false;
        mAttributes = attributes;
        mDataSet.addAll(result);
        mAdapter.notifyDataSetChanged();

        if (mDataSet.isEmpty()) {
            mErrorMessage.setText(getSearchErrorMessage());
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        updating = false;

        if (mDataSet.isEmpty()) {
            mState = State.ERROR;
            mErrorMessage.setText(R.string.network_error);
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListEndReached() {
        if (!updating && mAttributes != null && mAttributes.getPage() < mAttributes.getTotalPages())
            getData(mAttributes.getPage() + 1);
    }

}
