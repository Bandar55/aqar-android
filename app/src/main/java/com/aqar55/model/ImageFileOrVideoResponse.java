package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

public class ImageFileOrVideoResponse {

    @SerializedName("_id")
    private String _id;

    @SerializedName("image")
    private String image;

    @SerializedName("video")
    private String video;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
