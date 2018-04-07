package com.hsbank.luxclub.model;

import java.util.ArrayList;

/**
 * Created by chenliuchun on 2017/6/9.
 */

public class AlbumDetailBean {

    private long albumId;
    private String albumTitle;
    private ArrayList<String> imageUrl;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
