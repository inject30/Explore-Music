package com.play.tube.music.videos.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Attributes {

    private int page;
    private int perPage;
    private int totalPages;
    private int total;

    private Attributes() {}

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotal() {
        return total;
    }

    static Attributes parse(JSONObject jsonObject) {
        Attributes attributes = new Attributes();
        try {
            attributes.page = jsonObject.getInt("page");
            attributes.perPage = jsonObject.getInt("perPage");
            attributes.totalPages = jsonObject.getInt("totalPages");
            attributes.total = jsonObject.getInt("total");
            return attributes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static Attributes parseSearch(JSONObject jsonObject) {
        Attributes attributes = new Attributes();
        try {
            int totalResults = jsonObject.getInt("opensearch:totalResults");
            int startIndex = jsonObject.getInt("opensearch:startIndex");
            int itemsPerPage = jsonObject.getInt("opensearch:itemsPerPage");
            attributes.page = 1 + startIndex/itemsPerPage;
            attributes.perPage = itemsPerPage;
            attributes.totalPages = totalResults/itemsPerPage + (totalResults%itemsPerPage>0?1:0);
            attributes.total = totalResults;
            return attributes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Attributes parseSpotify(JSONObject jsonObject) {
        Attributes attributes = new Attributes();
        try {
            int limit = jsonObject.getInt("limit");
            int offset = jsonObject.getInt("offset");
            int total = jsonObject.getInt("total");

            attributes.page = 1 + offset/limit + (offset%limit>0?1:0);
            attributes.perPage = limit;
            attributes.totalPages = total/limit + (total%limit>0?1:0);
            attributes.total = total;
            return attributes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
