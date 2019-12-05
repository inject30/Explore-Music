package com.play.tube.music.videos;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.play.tube.music.videos.api.Track;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application {

    private static RequestQueue requestQueue;
    private static Context context;
    private static YouTube youTube;
    private static List<Track> recommendations = new ArrayList<>();

    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        context = this;

        youTube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName(getContext().getString(R.string.app_name)).build();
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static Context getContext() {
        return context;
    }

    public static YouTube getYouTube() {
        return youTube;
    }

    public static List<Track> getRecommendations() {
        return recommendations;
    }
}
