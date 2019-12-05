package com.play.tube.music.videos.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.play.tube.music.videos.Application;
import com.play.tube.music.videos.provider.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LastFM {

    private static final String TAG = "LastFM";

    private static final String API_KEY = "b9bd4d9642ff61813909d7b4603d0e7a";
    private static final String API_URL = "http://ws.audioscrobbler.com/2.0/";

    public static void getTopTracks(int page, final Listener<Track> listener) {
        String requestString = "method=chart.getTopTracks&api_key=" + API_KEY + "&limit=100&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Track> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject jsonTracks = response.getJSONObject("tracks");
                    JSONArray tracks = jsonTracks.getJSONArray("track");
                    for (int i = 0; i < tracks.length(); i++) {
                        Track track = Track.parseLastFM(tracks.getJSONObject(i));
                        if (track != null)
                            result.add(track);

                    }
                    JSONObject attr = jsonTracks.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopTags(int page, final Listener<Tag> listener) {
        String requestString = "method=chart.getTopTags&api_key=" + API_KEY + "&limit=50&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Tag> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject jsonTags = response.getJSONObject("tags");
                    JSONArray tags = jsonTags.getJSONArray("tag");
                    for (int i = 0; i < tags.length(); i++) {
                        Tag tag = Tag.parse(tags.getJSONObject(i));
                        if (tag != null)
                            result.add(tag);

                    }
                    JSONObject attr = jsonTags.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopArtists(int page, final Listener<Artist> listener) {
        String requestString = "method=chart.getTopArtists&api_key=" + API_KEY + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Artist> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject jsonTracks = response.getJSONObject("artists");
                    JSONArray artists = jsonTracks.getJSONArray("artist");
                    for (int i = 0; i < artists.length(); i++) {
                        Artist artist = Artist.parse(artists.getJSONObject(i));
                        if (artist != null)
                            result.add(artist);

                    }
                    JSONObject attr = jsonTracks.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }


    public static void getTopTracks(Artist artist, int page, final Listener<Track> listener) {
        String requestString = "method=artist.getTopTracks&api_key=" + API_KEY + "&mbid=" + artist.getMbid() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Track> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject toptracks = response.getJSONObject("toptracks");
                    JSONArray tracks = toptracks.getJSONArray("track");
                    for (int i = 0; i < tracks.length(); i++) {
                        Track track = Track.parseLastFM(tracks.getJSONObject(i));
                        if (track != null)
                            result.add(track);

                    }
                    JSONObject attr = toptracks.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopTracks(Tag tag, int page, final Listener<Track> listener) {
        String requestString = "method=tag.getTopTracks&api_key=" + API_KEY + "&tag=" + tag.getName() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Track> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject jsonTracks = response.getJSONObject("tracks");
                    JSONArray tracks = jsonTracks.getJSONArray("track");
                    for (int i = 0; i < tracks.length(); i++) {
                        Track track = Track.parseLastFM(tracks.getJSONObject(i));
                        if (track != null)
                            result.add(track);

                    }
                    JSONObject attr = jsonTracks.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopAlbums(Artist artist, int page, final Listener<Album> listener) {
        String requestString = "method=artist.getTopAlbums&api_key=" + API_KEY + "&mbid=" + artist.getMbid() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Album> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject topalbums = response.getJSONObject("topalbums");
                    JSONArray albums = topalbums.getJSONArray("album");
                    for (int i = 0; i < albums.length(); i++) {
                        Album album = Album.parse(albums.getJSONObject(i));
                        if (album != null)
                            result.add(album);

                    }
                    JSONObject attr = topalbums.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopAlbums(Tag tag, int page, final Listener<Album> listener) {
        String requestString = "method=tag.getTopAlbums&api_key=" + API_KEY + "&tag=" + tag.getName() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Log.d(TAG, url);

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Album> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject jsonAlbums = response.getJSONObject("albums");
                    JSONArray albums = jsonAlbums.getJSONArray("album");
                    for (int i = 0; i < albums.length(); i++) {
                        Album album = Album.parse(albums.getJSONObject(i));
                        if (album != null)
                            result.add(album);

                    }
                    JSONObject attr = jsonAlbums.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getTopArtists(Tag tag, int page, final Listener<Artist> listener) {
        String requestString = "method=tag.getTopArtists&api_key=" + API_KEY + "&tag=" + tag.getName() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Artist> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject topartists = response.getJSONObject("topartists");
                    JSONArray artists = topartists.getJSONArray("artist");
                    for (int i = 0; i < artists.length(); i++) {
                        Artist artist = Artist.parse(artists.getJSONObject(i));
                        if (artist != null)
                            result.add(artist);

                    }
                    JSONObject attr = topartists.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getSimilarArtists(Artist artist, int page, final Listener<Artist> listener) {
        String requestString = "method=artist.getSimilar&api_key=" + API_KEY + "&mbid=" + artist.getMbid() + "&limit=500&page=" + page + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Artist> result = new ArrayList<>();
                Attributes attributes = null;

                try {
                    JSONObject similarartists = response.getJSONObject("similarartists");
                    JSONArray artists = similarartists.getJSONArray("artist");
                    for (int i = 0; i < artists.length(); i++) {
                        Artist artist = Artist.parse(artists.getJSONObject(i));
                        if (artist != null)
                            result.add(artist);

                    }
                    JSONObject attr = similarartists.getJSONObject("@attr");
                    attributes = Attributes.parse(attr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, attributes);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void getAlbumTracks(Album album, final Listener<Track> listener) {
        String requestString = "method=album.getInfo&api_key=" + API_KEY + "&mbid=" + album.getMbid() + "&format=json";
        String url = API_URL + "?" + requestString;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        int error = response.getInt("error");
                        String message = response.getString("message");
                        Log.e(TAG, "Error - " + error + ": " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        listener.onError();
                    }
                    return;
                }
                List<Track> result = new ArrayList<>();

                try {
                    JSONObject album = response.getJSONObject("album");
                    JSONObject jsonTracks = album.getJSONObject("tracks");
                    JSONArray tracks = jsonTracks.getJSONArray("track");
                    for (int i = 0; i < tracks.length(); i++) {
                        Track track = Track.parseLastFM(tracks.getJSONObject(i));
                        if (track != null)
                            result.add(track);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onResponse(result, null);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
        Application.getRequestQueue().add(request);
    }

    public static void searchAlbums(String query, int page, final Listener<Album> listener) {
        try {
            String requestString = "method=album.search&api_key=" + API_KEY + "&album=" + URLEncoder.encode(query, "UTF-8") + "&format=json&page=" + page;
            String url = API_URL + "?" + requestString;

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        try {
                            int error = response.getInt("error");
                            String message = response.getString("message");
                            Log.e(TAG, "Error - " + error + ": " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            listener.onError();
                        }
                        return;
                    }
                    List<Album> result = new ArrayList<>();
                    Attributes attributes = null;

                    try {
                        JSONObject results = response.getJSONObject("results");
                        JSONObject albummatches = results.getJSONObject("albummatches");
                        JSONArray albums = albummatches.getJSONArray("album");
                        for (int i = 0; i < albums.length(); i++) {
                            Album album = Album.parse(albums.getJSONObject(i));
                            if (album != null)
                                result.add(album);

                        }

                        attributes = Attributes.parseSearch(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(result, attributes);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
            Application.getRequestQueue().add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void searchArtists(String query, int page, final Listener<Artist> listener) {
        try {
            String requestString = "method=artist.search&api_key=" + API_KEY + "&artist=" + URLEncoder.encode(query, "UTF-8") + "&format=json&page=" + page;
            String url = API_URL + "?" + requestString;

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        try {
                            int error = response.getInt("error");
                            String message = response.getString("message");
                            Log.e(TAG, "Error - " + error + ": " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            listener.onError();
                        }
                        return;
                    }
                    List<Artist> result = new ArrayList<>();
                    Attributes attributes = null;

                    try {
                        JSONObject results = response.getJSONObject("results");
                        JSONObject artistmatches = results.getJSONObject("artistmatches");
                        JSONArray artists = artistmatches.getJSONArray("artist");
                        for (int i = 0; i < artists.length(); i++) {
                            Artist artist = Artist.parse(artists.getJSONObject(i));
                            if (artist != null)
                                result.add(artist);

                        }

                        attributes = Attributes.parseSearch(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(result, attributes);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
            Application.getRequestQueue().add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void searchTracks(String query, int page, final Listener<Track> listener) {
        try {
            String requestString = "method=track.search&api_key=" + API_KEY + "&track=" + URLEncoder.encode(query, "UTF-8") + "&format=json&page=" + page;
            String url = API_URL + "?" + requestString;

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        try {
                            int error = response.getInt("error");
                            String message = response.getString("message");
                            Log.e(TAG, "Error - " + error + ": " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            listener.onError();
                        }
                        return;
                    }
                    List<Track> result = new ArrayList<>();
                    Attributes attributes = null;

                    try {
                        JSONObject results = response.getJSONObject("results");
                        JSONObject trackmatches = results.getJSONObject("trackmatches");
                        JSONArray tracks = trackmatches.getJSONArray("track");
                        for (int i = 0; i < tracks.length(); i++) {
                            Track artist = Track.parseLastFM(tracks.getJSONObject(i));
                            if (artist != null)
                                result.add(artist);

                        }

                        attributes = Attributes.parseSearch(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(result, attributes);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
            Application.getRequestQueue().add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getLocalTopArtists(String country, int page, final Listener<Artist> listener) {
        try {
            String requestString = "method=geo.getTopArtists&api_key=" + API_KEY + "&limit=500&page=" + page + "&format=json&country=" + URLEncoder.encode(country, "UTF-8");
            String url = API_URL + "?" + requestString;

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        try {
                            int error = response.getInt("error");
                            String message = response.getString("message");
                            Log.e(TAG, "Error - " + error + ": " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            listener.onError();
                        }
                        return;
                    }
                    List<Artist> result = new ArrayList<>();
                    Attributes attributes = null;

                    try {
                        JSONObject topArtists = response.getJSONObject("topartists");
                        JSONArray artists = topArtists.getJSONArray("artist");
                        for (int i = 0; i < artists.length(); i++) {
                            Artist artist = Artist.parse(artists.getJSONObject(i));
                            if (artist != null)
                                result.add(artist);

                        }
                        JSONObject attr = topArtists.getJSONObject("@attr");
                        attributes = Attributes.parse(attr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(result, attributes);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
            Application.getRequestQueue().add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            listener.onError();
        }
    }

    public static void getLocalTopTracks(String country, int page, final Listener<Track> listener) {
        try {
            String requestString = "method=geo.getTopTracks&api_key=" + API_KEY + "&limit=500&page=" + page + "&format=json&country=" + URLEncoder.encode(country, "UTF-8");
            String url = API_URL + "?" + requestString;

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        try {
                            int error = response.getInt("error");
                            String message = response.getString("message");
                            Log.e(TAG, "Error - " + error + ": " + message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            listener.onError();
                        }
                        return;
                    }
                    List<Track> result = new ArrayList<>();
                    Attributes attributes = null;

                    try {
                        JSONObject jsonTracks = response.getJSONObject("tracks");
                        JSONArray tracks = jsonTracks.getJSONArray("track");
                        for (int i = 0; i < tracks.length(); i++) {
                            Track track = Track.parseLastFM(tracks.getJSONObject(i));
                            if (track != null)
                                result.add(track);

                        }
                        JSONObject attr = jsonTracks.getJSONObject("@attr");
                        attributes = Attributes.parse(attr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(result, attributes);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener);
            Application.getRequestQueue().add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            listener.onError();
        }
    }

    public interface Listener<T extends Model> {
        void onError();

        void onResponse(List<T> data, Attributes attributes);
    }
}
