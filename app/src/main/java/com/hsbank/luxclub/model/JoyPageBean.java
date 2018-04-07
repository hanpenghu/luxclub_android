package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/23
 * Description: 首页轮播列表条目
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class JoyPageBean {

    /**
     * title : 游艇俱乐部1
     * siteType : 5
     * introduce : 御浪飞行1
     * imageUrl1 : http://120.76.241.15:7007/userfiles/19/images/luxclub/carousel/2017/02/520x630%E6%B8%B8%E8%89%87.png
     * imageUrl2 : http://120.76.241.15:7007/userfiles/19/images/luxclub/carousel/2017/02/520x806%E6%B8%B8%E8%89%87(1).png
     * imageUrl3 : http://120.76.241.15:7007/userfiles/19/images/luxclub/carousel/2017/02/520x806%E6%B8%B8%E8%89%87(1).png
     * imageUrl4 : http://120.76.241.15:7007/userfiles/19/images/luxclub/carousel/2017/02/708x1142%E6%B8%B8%E8%89%87(1).png
     */

    private String title;
    private String siteType; // int
    private String introduce;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;

    public JoyPageBean() {}

    public JoyPageBean(String title, String imageUrl3) {
        this.title = title;
        this.imageUrl3 = imageUrl3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSiteType() {
        return Integer.parseInt(siteType);
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }
}
