package com.gears.yashodhan.speechy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.List;

public class musicPlayer extends AppCompatActivity {


    static private final String TAG = "musicPlayerActivity";
    private List<MusicFileData> paths = new ArrayList<>();
    private ListView lv;
    private ArrayAdapter<MusicFileData> adapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_music_player);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new MusicAdapter(this, paths);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext,"Clicked on " + i,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void searchMusic(View v){
        //MediaPlayer mp = new MediaPlayer();
        ContentResolver cr = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(paths.size()!=0){
            //TODO: add some code prevent the rescanning of the same music files
        }
        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {

                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    //String albumArt = cur.getString(cur.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART));
                    String albumArt = "hello";
                    // Add code to get more column here
                    //paths.add(new MusicFileData(title,albumArt,data));
                    //adapter.notifyDataSetChanged();
                    adapter.add(new MusicFileData(title,albumArt,data));
                    // Save to your list here
                    Log.d(TAG,data);
                }
            }
        }

        cur.close();



        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
    }
}
