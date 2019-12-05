package com.play.tube.music.videos.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Album implements Serializable, Model {

    private String name;
    private String mbid;
    private String spotifyId;
    private Artist artist;
    private Map<String, String> images = new HashMap<>();

    private Album() {}

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public Artist getArtist() {
        return artist;
    }

    public Map<String, String> getImages() {
        return images;
    }

    static Album parse(JSONObject jsonObject) {
        Album album = new Album();
        try {
            album.name = jsonObject.getString("name");
            album.mbid = jsonObject.getString("mbid");

            try {
                JSONObject jsonArtist = jsonObject.getJSONObject("artist");
                album.artist = Artist.parse(jsonArtist);
            } catch (JSONException albumParseException) {
                album.artist = new Artist(jsonObject.getString("artist"));
            }

            JSONArray images = jsonObject.getJSONArray("image");
            for (int i = 0; i < images.length(); i++) {
                JSONObject image = images.getJSONObject(i);
                String size = image.getString("size");
                String url = image.getString("#text");
                album.images.put(size, url);
            }
            return album;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Album parseSpotify(JSONObject jsonObject) {
        Album album = new Album();
        try {
            album.spotifyId = jsonObject.getString("id");
            album.name = jsonObject.getString("name");

            JSONArray images = jsonObject.getJSONArray("images");
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
                album.images.put(size, url);
            }
            return album;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
