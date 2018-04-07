package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        2017/9/26
 * Description: 商家所在的城市
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class CityBean {

    private String cityId;
    private String cityName;
    private boolean isSelected = false;

    public CityBean() {
    }

    public CityBean(String cityId, String cityName, boolean isSelected) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.isSelected = isSelected;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
