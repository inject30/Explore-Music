package com.play.tube.music.videos.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class Tag implements Model, Serializable {

    private String name;
    private String url;
    private int reach;
    private int taggings;

    private Tag() {}

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getReach() {
        return reach;
    }

    public int getTaggings() {
        return taggings;
    }

    @Nullable
    static Tag parse(JSONObject jsonObject) {
        Tag tag = new Tag();
        try {
            tag.name = jsonObject.getString("name");
            tag.url = jsonObject.getString("url");
            tag.reach = jsonObject.getInt("reach");
            tag.taggings = jsonObject.getInt("taggings");
            return tag;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
