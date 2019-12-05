package com.play.tube.music.videos.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.adapters.AlbumTracksAdapter;
import com.play.tube.music.videos.api.Album;
import com.play.tube.music.videos.api.Attributes;
import com.play.tube.music.videos.api.LastFM;
import com.play.tube.music.videos.api.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlbumActivity extends AppCompatActivity implements LastFM.Listener<Track> {

    private Album mAlbum;
    private List<Track> mDataSet = new ArrayList<>();

    private ImageView mHeaderImage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView.Adapter mAdapter;

    public View mErrorContainer;
    public TextView mErrorMessage;

    private boolean updating = false;

    public void getData() {
        updating = true;
        LastFM.getAlbumTracks(mAlbum, this);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mAlbum = (Album) intent.getSerializableExtra("album");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mAlbum.getName());
        TextView subtitle = findViewById(R.id.subtitle);
        subtitle.setText(mAlbum.getArtist().getName());

        mHeaderImage = findViewById(R.id.header_image);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHeaderImage.getLayoutParams();
            params.height = displaymetrics.widthPixels;
            mHeaderImage.setLayoutParams(params);
        }

        String url = null;
        Map<String, String> images = mAlbum.getImages();
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

        mErrorContainer = findViewById(R.id.error_container);
        mErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorContainer.setVisibility(View.GONE);
                getData();
            }
        });
        mErrorMessage = findViewById(R.id.error_message);

        getData();

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AlbumTracksAdapter(this, mDataSet);
        recyclerView.setAdapter(mAdapter);
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHeaderImage.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = getResources().getDimensionPixelSize(R.dimen.header_height_landscape);
        } else {
            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            params.height = displaymetrics.widthPixels;
        }

        mHeaderImage.setLayoutParams(params);
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
            mErrorMessage.setText(R.string.network_error);
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(List<Track> data, Attributes attributes) {
        mSwipeRefreshLayout.setRefreshing(false);
        updating = false;
        mDataSet.addAll(data);
        mAdapter.notifyDataSetChanged();

        if (mDataSet.isEmpty()) {
            mErrorMessage.setText(R.string.tracks_not_found);
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }
}
