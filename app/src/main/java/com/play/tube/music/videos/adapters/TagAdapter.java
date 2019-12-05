package com.play.tube.music.videos.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.play.tube.music.videos.R;
import com.play.tube.music.videos.activities.TagActivity;
import com.play.tube.music.videos.api.Tag;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<Tag> mDataSet;

    public TagAdapter(List<Tag> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tagName.setText(mDataSet.get(position).getName());
        holder.tagReach.setText(String.valueOf(mDataSet.get(position).getTaggings()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tagName;
        private TextView tagReach;

        ViewHolder(View itemView) {
            super(itemView);

            tagName  = itemView.findViewById(R.id.tag_name);
            tagReach = itemView.findViewById(R.id.tag_reach);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), TagActivity.class);
            intent.putExtra("tag", mDataSet.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
