package com.radiantraccon.probe.data;


public class AddressData {
    private int imageId;
    private String title;
    private String address;

    public AddressData(int imageId, String title, String address) {
        this.imageId = imageId;
        this.title = title;
        this.address = address;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


}
