package com.play.tube.music.videos.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Artist implements Model, Serializable {

    private String name;
    private String mbid;
    private String id;
    private Map<String, String> images = new HashMap<>();

    private Artist() {}

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getImages() {
        return images;
    }

    static Artist parse(JSONObject jsonObject) {
        Artist artist = new Artist();
        try {
            artist.name = jsonObject.getString("name");
            artist.mbid = jsonObject.getString("mbid");

            if (jsonObject.has("image")) {
                JSONArray images = jsonObject.getJSONArray("image");
                for (int i = 0; i < images.length(); i++) {
                    JSONObject image = images.getJSONObject(i);
                    String size = image.getString("size");
                    String url = image.getString("#text");
                    artist.images.put(size, url);
                }
            }
            return artist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Artist parseSpotify(JSONObject jsonObject) {
        Artist artist = new Artist();
        try {
            artist.name = jsonObject.getString("name");
            artist.id = jsonObject.getString("id");

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
                artist.images.put(size, url);
            }
            return artist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
