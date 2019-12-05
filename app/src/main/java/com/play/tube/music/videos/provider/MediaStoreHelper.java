package com.play.tube.music.videos.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MediaStoreHelper {

    public static List<Song> getTracks(ContentResolver contentResolver) {
        List<Song> songs = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "<> ?";
        String[] selectionArgs = {"0"};
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";
        Cursor cursor = contentResolver.query(uri, null, selection, selectionArgs, sortOrder);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int albumIdColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int artistIdColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
                int titleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int durationColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int dateAddedColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
                do {
                    if (songs.size() > 49)
                        break;

                    Song song = new Song(cursor.getInt(idColumnIndex));
                    song.setAlbum(cursor.getString(albumColumnIndex));
                    song.setAlbumId(cursor.getInt(albumIdColumnIndex));
                    song.setArtist(cursor.getString(artistColumnIndex));
                    song.setArtistId(cursor.getInt(artistIdColumnIndex));
                    song.setData(cursor.getString(dataColumnIndex));
                    song.setDuration(cursor.getInt(durationColumnIndex));
                    song.setTitle(cursor.getString(titleColumnIndex));
                    song.setDateAdded(cursor.getLong(dateAddedColumnIndex));
                    songs.add(song);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return songs;
    }
}
