package com.play.tube.music.videos.adapters;

import android.content.Intent;
import com.play.tube.music.videos.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.tube.music.videos.activities.ArtistActivity;
import com.play.tube.music.videos.api.Artist;
import com.play.tube.music.videos.listeners.ListInteractionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private List<Artist> mDataSet;
    private ListInteractionsListener listener;

    public ArtistAdapter(ListInteractionsListener listener, List<Artist> dataSet) {
        this.listener = listener;
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);
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
        Picasso.with(holder.artistImage.getContext())
                .load(url)
                .placeholder(R.drawable.no_cover)
                .error(R.drawable.no_cover)
                .into(holder.artistImage);

        holder.artistName.setText(mDataSet.get(position).getName());

        if (listener != null && position > mDataSet.size() - 10)
            listener.onListEndReached();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView artistImage;
        private TextView artistName;

        ViewHolder(View itemView) {
            super(itemView);

            artistImage = itemView.findViewById(R.id.artist_image);
            artistName = itemView.findViewById(R.id.artist_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(view.getContext(), ArtistActivity.class);
            intent.putExtra("artist", mDataSet.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
