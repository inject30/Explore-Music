package com.play.tube.music.videos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.play.tube.music.videos.api.Track;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;

public class YoutubeSearch extends AsyncTask<Void, Void, Void> {

    private static final String WEB_KEY = "AIzaSyABDEn41hmkb7yCGvLiqLflI0KC8UpdIF0";
    private static final String YOUTUBE_API_KEY = "AIzaSyA_1cudDIS81RK2t80fGTsdDpP4DkcNAc8";

    private Activity mActivity;
    private Track mTrack;

    private List<SearchResult> searchResultList;
    private MaterialDialog progressDialog;

    public YoutubeSearch(Activity activity, Track track) {
        mActivity = activity;
        mTrack = track;

        progressDialog = new MaterialDialog.Builder(mActivity)
                .theme(Theme.DARK)
                .title(R.string.search_video_dialog_title)
                .content(R.string.search_video_dialog_content)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            YouTube.Search.List search = Application.getYouTube().search().list("id,snippet");
            search.setKey(WEB_KEY);
            search.setQ(mTrack.getArtist() + " " + mTrack.getTitle());
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(1L);

            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (searchResultList != null && !searchResultList.isEmpty()) {
            if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(mActivity).equals(YouTubeInitializationResult.SUCCESS)){
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        mActivity,
                        YOUTUBE_API_KEY,
                        searchResultList.get(0).getId().getVideoId(),
                        0,
                        true,
                        true);
                mActivity.startActivity(intent);
            } else {
                new MaterialDialog.Builder(mActivity)
                        .title(R.string.youtube_error_dialog_title)
                        .content(R.string.youtube_error_dialog_content)
                        .positiveText(R.string.install)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.getView().getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.youtube")));
                            }
                        })
                        .show();
            }

        } else {
            Toast.makeText(mActivity, R.string.video_not_found, Toast.LENGTH_LONG).show();
        }
    }

}
