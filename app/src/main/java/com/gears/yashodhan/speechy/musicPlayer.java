package com.gears.yashodhan.speechy;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class musicPlayer extends AppCompatActivity {


    static private final String TAG = "musicPlayerActivity";
    private List<String> paths = new ArrayList<String>();
    private ListView lv;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, paths);
        lv.setAdapter(adapter);

    }

    public void searchMusic(View v){
        //MediaPlayer mp = new MediaPlayer();
        ContentResolver cr = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(paths.size()!=0)
          switch (v.getId()){
              case R.id.searchMusicBtn:

                  break;

          }
        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    // Add code to get more column here
                    //paths.add(data);
                    adapter.add(data);
                    // Save to your list here
                    Log.d(TAG,data);
                }
            }
        }

        cur.close();



        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
    }
}
