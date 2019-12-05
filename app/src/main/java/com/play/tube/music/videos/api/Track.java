package com.play.tube.music.videos.api;

import com.play.tube.music.videos.provider.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Track implements Comparable<Track>, Model {

    private String title;
    private String artist;
    private double match;
    private int duration;
    private Map<String, String> images = new HashMap<>();

    public void setMatch(double match) {
        this.match = match;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public double getMatch() {
        return match;
    }

    public int getDuration() {
        return duration;
    }

    public Map<String, String> getImages() {
        return images;
    }

    static Track parseLastFM(JSONObject jsonObject){
        Track track = new Track();
        try {
            track.title = jsonObject.getString("name");

            if (jsonObject.has("match") && !jsonObject.isNull("match"))
                track.match = jsonObject.getDouble("match");

            if (jsonObject.has("duration") && !jsonObject.isNull("duration"))
                track.duration = jsonObject.getInt("duration");

            try {
                JSONObject jsonArtist = jsonObject.getJSONObject("artist");
                track.artist = jsonArtist.getString("name");
            } catch (JSONException artistParseException) {
                track.artist = jsonObject.getString("artist");
            }

            if (jsonObject.has("image")) {
                JSONArray images = jsonObject.getJSONArray("image");
                for (int i = 0; i < images.length(); i++) {
                    JSONObject image = images.getJSONObject(i);
                    String size = image.getString("size");
                    String url = image.getString("#text");
                    track.images.put(size, url);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            track = null;
        } finally {
            return track;
        }
    }

    public static Track parseSpotify(JSONObject jsonObject) {
        Track track = new Track();
        try {
            track.title = jsonObject.getString("name");
            track.duration = (int)(jsonObject.getLong("duration_ms")/1000);

            JSONObject jsonAlbum = jsonObject.getJSONObject("album");
            JSONArray images = jsonAlbum.getJSONArray("images");
            for (int i = 0; i < images.length(); i++) {
                String size;
                switch (i) {
                    case 0: size = "small"; break;
                    case 1: size = "medium"; break;
                    case 2: size = "large"; break;
                    case 3: size = "extralarge"; break;
                    case 4: size = "mega"; break;
                    default: size = ""; break;
                }
                String url = images.getJSONObject(images.length() - 1 - i).getString("url");
                track.images.put(size, url);
            }

            JSONArray jsonArtists = jsonObject.getJSONArray("artists");
            track.artist = jsonArtists.getJSONObject(0).getString("name");
            return track;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(Track track) {
        return ((Double) track.match).compareTo(this.match);
    }

    public int hashCode() {
        return artist.toLowerCase().hashCode() + title.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Song) {
            Song song = (Song) object;
            return this.artist.equalsIgnoreCase(song.getArtist()) && this.title.equalsIgnoreCase(song.getTitle());
        } else if (object instanceof Track) {
            Track track = (Track) object;
            return this.artist.equalsIgnoreCase(track.getArtist().toLowerCase()) && this.title.equalsIgnoreCase(track.getTitle().toLowerCase());
        } else {
            return false;
        }
    }
}
