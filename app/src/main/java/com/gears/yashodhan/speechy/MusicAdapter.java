package com.gears.yashodhan.speechy;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yashodhan on 24-Apr-16.
 */
public class MusicAdapter extends ArrayAdapter<MusicFileData> {
    public MusicAdapter(Context context,  List<MusicFileData> objects) {
        super(context, R.layout.music_row_layout, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // some init stuff
            convertView = layoutInflater.inflate(R.layout.music_row_layout,parent,false);
            String musicTitle = getItem(position).title;
            TextView textView = (TextView) convertView.findViewById(R.id.musicTitle);
            textView.setText(musicTitle);
//            //TODO: Create or find albumart and load it onto the imageview
////            String artImage = getItem(position).albumArt;
////            ImageView imageView = (ImageView) convertView.findViewById(R.id.albumImage);
////            imageView.setImageURI(Uri.parse(artImage));
        return convertView;
    }
}
