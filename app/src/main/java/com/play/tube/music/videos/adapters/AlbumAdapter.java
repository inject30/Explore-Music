package com.play.tube.music.videos.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.activities.AlbumActivity;
import com.play.tube.music.videos.api.Album;
import com.play.tube.music.videos.listeners.ListInteractionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> mDataSet;
    private ListInteractionsListener listener;

    public AlbumAdapter(ListInteractionsListener listener, List<Album> dataSet) {
        this.listener = listener;
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        Picasso.with(holder.albumImage.getContext())
                .load(url)
                .placeholder(R.drawable.no_cover)
                .error(R.drawable.no_cover)
                .into(holder.albumImage);

        holder.albumName.setText(mDataSet.get(position).getName());

        if (listener != null && position > mDataSet.size() - 10)
            listener.onListEndReached();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView albumImage;
        private TextView albumName;

        ViewHolder(View itemView) {
            super(itemView);

            albumImage = itemView.findViewById(R.id.album_image);
            albumName = itemView.findViewById(R.id.album_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AlbumActivity.class);
            intent.putExtra("album", mDataSet.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
