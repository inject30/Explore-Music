package com.play.tube.music.videos.provider;

import android.content.ContentUris;
import android.net.Uri;

import com.play.tube.music.videos.api.Track;

public class Song {

    private long id;
    private int albumId;
    private int artistId;
    private String artist;
    private String title;
    private String album;
    private String data;
    private String albumArt;
    private int duration;
    private String durationString;
    private long dateAdded;

    public Song(int id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId){
        this.albumId = albumId;
        Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
        this.albumArt = albumArtUri.toString();
    }

    public String getAlbum(){
        return album;
    }

    public void setAlbum(String album){
        this.album = album;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId){
        this.artistId = artistId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getAlbumArt(){
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getDuration(){
        return duration;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDuration(int duration){
        this.duration = duration;
        int sec = duration/1000;
        this.durationString = ""+(sec/60)+":"+(sec%60<10?"0":"")+(sec%60);
    }

    public String getDurationString(){
        return durationString;
    }

    public int hashCode() {
        return artist.toLowerCase().hashCode() + title.toLowerCase().hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof Track) {
            Track track = (Track) object;
            return this.artist.equalsIgnoreCase(track.getArtist()) && this.title.equalsIgnoreCase(track.getTitle());
        } else if (object instanceof Song) {
            Song song = (Song) object;
            return this.artist.equalsIgnoreCase(song.artist) && this.title.equalsIgnoreCase(song.title);
        } else {
            return false;
        }
    }
}
