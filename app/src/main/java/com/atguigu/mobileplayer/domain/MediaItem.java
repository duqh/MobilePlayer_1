package com.atguigu.mobileplayer.domain;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 作者：杨光福 on 2016/7/18 09:16
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：代表一个视频和音频
 */
public class MediaItem implements Serializable {


    private String name;

    private long duration;

    private long size;

    private String data;

    private String artist;

    private Bitmap thumbnail ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
