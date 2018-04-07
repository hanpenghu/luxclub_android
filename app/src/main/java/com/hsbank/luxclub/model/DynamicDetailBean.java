package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/20
 * Description: 动态详情页面
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class DynamicDetailBean {

    /**
     * {
     * dynamicId:{
     * description:"动态ID",
     * type:"long",
     * optional:"FALSE"       },
     * title:{
     * description:"标题",
     * type:"string",
     * optional:"FALSE"       },
     * imageUlr:{
     * description:"主图URL",
     * type:"string",
     * optional:"FALSE"       },
     * iconUrl:{
     * description:"图标url",
     * type:"string",
     * optional:"FALSE"       },
     * label:{
     * description:"标签(四字标签1,四字标签2,四字标签3)",
     * type:"string",
     * optional:"FALSE"       },
     * createDate:{
     * description:"发布时间",
     * type:"string",
     * optional:"FALSE"       },
     * content:{
     * description:"内容(HTML)",
     * type:"string",
     * optional:"FALSE"     }
     * }
     */

    private int dynamicId;
    private String title;
    private String imageUlr;
    private String iconUrl;
    private String label;
    private String createDate;
    private String content;

    public int getDynamicId() {
        return dynamicId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUlr() {
        return imageUlr;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getLabel() {
        return label;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getContent() {
        return content;
    }
}
