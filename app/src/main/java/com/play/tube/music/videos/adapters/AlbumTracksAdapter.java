package com.play.tube.music.videos.adapters;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.YoutubeSearch;
import com.play.tube.music.videos.api.Track;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Track> mDataSet;

    public AlbumTracksAdapter(Activity activity, List<Track> dataSet) {
        mActivity = activity;
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_track_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.trackNumber.setText(String.valueOf(position + 1));
        holder.trackTitle.setText(mDataSet.get(position).getTitle());

        int duration = mDataSet.get(position).getDuration();
        int min = duration/60;
        int sec = duration%60;

        holder.trackDuration.setText((min>10?min:"0"+min) +":"+ (sec>10?sec:"0"+sec));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView trackNumber;
        private TextView trackTitle;
        private TextView trackDuration;

        ViewHolder(View itemView) {
            super(itemView);

            trackNumber = itemView.findViewById(R.id.track_number);
            trackTitle = itemView.findViewById(R.id.track_title);
            trackDuration = itemView.findViewById(R.id.track_duration);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Track track = mDataSet.get(getAdapterPosition());
            new YoutubeSearch(mActivity, track).execute();
        }
    }


}
