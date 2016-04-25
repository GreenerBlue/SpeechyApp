package com.gears.yashodhan.speechy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class musicPlayer extends AppCompatActivity {


    static private final String TAG = "musicPlayerActivity";
    private List<MusicFileData> paths = new ArrayList<>();
    private ArrayAdapter<MusicFileData> adapter;
    private MediaPlayer mediaPlayer;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_music_player);

        ListView lv = (ListView) findViewById(R.id.listView);
        adapter = new MusicAdapter(this, paths);
        assert lv != null;
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MusicFileData musicFileData = (MusicFileData)adapterView.getItemAtPosition(i);
                Toast.makeText(mContext,"Playing " + musicFileData.title,Toast.LENGTH_SHORT).show();

                if(mediaPlayer !=null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(musicFileData.filePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //TODO: add a notification panel to play and pause music

    public void searchMusic(View v){

        ContentResolver cr = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count;

        if(paths.size()==0) {

            assert cur != null;
            count = cur.getCount();

            if (count > 0) {
                while (cur.moveToNext()) {

                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    //String albumArt = cur.getString(cur.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART));
                    String albumArt = "hello";
                    // Add code to get more column here
                    adapter.add(new MusicFileData(title, albumArt, data));
                    //adapter.notifyDataSetChanged();
                    Log.d(TAG, data);
                }
            }
            cur.close();
        }
        else {
            Toast.makeText(mContext,"No New Music found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
            mediaPlayer.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
