package com.radiantraccon.probe.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultData implements Parcelable{
    private String imageUrl;
    private String title;
    private String address;
    private String desc;

    public ResultData(String imageUrl, String title, String address, String desc) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.address = address;
        this.desc = desc;
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
    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(desc);
    }

    protected ResultData(Parcel in) {
        imageUrl = in.readString();
        title = in.readString();
        address = in.readString();
        desc = in.readString();
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

}
