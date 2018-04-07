package com.hsbank.luxclub.model;

import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 模特信息分页列表
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ModelItemBean {

    private String modelId;
    private String nickname;
    private String country;
    private String sex;
    private String age;
    private String height;
    private String weight;
    private String measurements;
    private String speciality;
    private String modelHead;
    private String isOrdered;
    private List<String> modelAlbum;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getModelHead() {
        return modelHead;
    }

    public void setModelHead(String modelHead) {
        this.modelHead = modelHead;
    }

    public String getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(String isOrdered) {
        this.isOrdered = isOrdered;
    }

    public List<String> getModelAlbum() {
        return modelAlbum;
    }

    public void setModelAlbum(List<String> modelAlbum) {
        this.modelAlbum = modelAlbum;
    }
}
