package com.gears.yashodhan.speechy;

/**
 * Created by Yashodhan on 24-Apr-16.
 */
public class MusicFileData {
    public String title;
    public String filePath;
    public String albumArt;
    public MusicFileData(String name, String art,String path){
        title = name;
        filePath = path;
        albumArt = art;
    }
}
