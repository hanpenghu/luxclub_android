package com.hsbank.luxclub.model;

/**
 * Created by chen_liuchun on 2016/3/16.
 */
public class JoyPagerItem {
    private String txtPagerTitle = "";
    private int imageCoverID = 0;
    private int imageLogoID = 0;

    public JoyPagerItem(String txtPagerTitle, int imageCoverID, int imageLogoID) {
        this.txtPagerTitle = txtPagerTitle;
        this.imageCoverID = imageCoverID;
        this.imageLogoID = imageLogoID;
    }

    public String getTxtPagerTitle() {
        return txtPagerTitle;
    }

    public void setTxtPagerTitle(String txtPagerTitle) {
        this.txtPagerTitle = txtPagerTitle;
    }

    public int getImageCoverID() {
        return imageCoverID;
    }

    public void setImageCoverID(int imageCoverID) {
        this.imageCoverID = imageCoverID;
    }

    public int getImageLogoID() {
        return imageLogoID;
    }

    public void setImageLogoID(int imageLogoID) {
        this.imageLogoID = imageLogoID;
    }
}
