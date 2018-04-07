package com.hsbank.luxclub.util.constants;

import java.util.Map;

public class GlobalData {

    private static GlobalData singleInstance = null;
    private GlobalData() {}

    private int currentSelectItem = 0;
    /**用于图片地址暂存*/
    public String avatar = "";

    // 管家号码
    private String managerMobile = "";

    public Map<String, Object> getMyData() {
        return MyData;
    }

    public void setMyData(Map<String, Object> myData) {
        MyData = myData;
    }

    /**头像Url*/
    private Map<String, Object> MyData;

    /**
     * 经理端首页的当前模块索引
     */
    private int managerCurrentIndex;

    public int getCurrentSelectItem() {
        return currentSelectItem;
    }

    public void setCurrentSelectItem(int currentSelectItem) {
        this.currentSelectItem = currentSelectItem;
    }

    public static GlobalData getInstance() {
        if (singleInstance == null) {
            singleInstance = new GlobalData();
        }
        return singleInstance;
    }

    public int getManagerCurrentIndex() {
        return managerCurrentIndex;
    }

    public void setManagerCurrentIndex(int managerCurrentIndex) {
        this.managerCurrentIndex = managerCurrentIndex;
    }
}