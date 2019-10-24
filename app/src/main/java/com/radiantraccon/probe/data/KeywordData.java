package com.radiantraccon.probe.data;

public class KeywordData {
    private int imageId;
    private String keyword;
    private String address;
    private String desc;

    public KeywordData(int imageId , String keyword, String address, String desc) {
        this.imageId = imageId;
        this.keyword= keyword;
        this.address = address;
        this.desc = desc;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return desc;
    }
    public void setDescription(String desc) {
        this.desc = desc;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
