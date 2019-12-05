package com.play.tube.music.videos.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.YoutubeSearch;
import com.play.tube.music.videos.api.Track;
import com.play.tube.music.videos.listeners.ListInteractionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Track> mDataSet;
    private ListInteractionsListener listener;

    public TrackAdapter(Activity activity, List<Track> dataSet) {
        mActivity = activity;
        mDataSet = dataSet;
    }

    public TrackAdapter(Activity activity, ListInteractionsListener listener, List<Track> dataSet) {
        mActivity = activity;
        this.listener = listener;
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mDataSet.get(position);

        String url = null;
        Map<String, String> images = mDataSet.get(position).getImages();
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

        Picasso.with(holder.trackImage.getContext())
                .load(url)
                .placeholder(R.drawable.no_cover)
                .error(R.drawable.no_cover)
                .into(holder.trackImage);

        holder.trackTitle.setText(track.getTitle());
        holder.trackArtist.setText(track.getArtist());

        if (listener != null && position > mDataSet.size() - 10)
            listener.onListEndReached();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView trackImage;
        private TextView trackTitle;
        private TextView trackArtist;

        ViewHolder(View itemView) {
            super(itemView);

            trackImage = itemView.findViewById(R.id.track_image);
            trackTitle = itemView.findViewById(R.id.track_title);
            trackArtist = itemView.findViewById(R.id.track_artist);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new YoutubeSearch(mActivity, mDataSet.get(getAdapterPosition())).execute();
        }
    }
}
