package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/14
 * Description: 场所信息
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class SiteInfoBean {

    /**
     * siteId : 45
     * siteType : 0
     * siteName : 场所名称2
     * siteAddr : 场所地址2
     * siteImageUrl : http://120.76.241.15:7007/userfiles/1/images/luxclub/siteInfo/2016/11/QQ%E6%88%AA%E5%9B%BE20160606232522.png
     * siteTypeName : 1
     */

    private int siteId;
    private int siteType;
    private String siteName;
    private String siteAddr;
    private String siteImageUrl;
    private String siteTypeName;

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getSiteType() {
        return siteType;
    }

    public void setSiteType(int siteType) {
        this.siteType = siteType;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAddr() {
        return siteAddr;
    }

    public void setSiteAddr(String siteAddr) {
        this.siteAddr = siteAddr;
    }

    public String getSiteImageUrl() {
        return siteImageUrl;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }

    public String getSiteTypeName() {
        return siteTypeName;
    }

    public void setSiteTypeName(String siteTypeName) {
        this.siteTypeName = siteTypeName;
    }
}
