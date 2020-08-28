package com.aqar55.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class PictureVideoModel implements Serializable {
    @SerializedName(value = "image",alternate = {"video"})
    private String event_image_video_url;
    @SerializedName("id")
    private String event_image_video_id;

    private File imageVidFile;
    private Uri imageVidUri ;

    public Uri getImageVidUri() {
        return imageVidUri;
    }

    public void setImageVidUri(Uri imageVidUri) {
        this.imageVidUri = imageVidUri;
    }

    public File getImageVidFile() {
        return imageVidFile;
    }

    public void setImageVidFile(File imageVidFile) {
        this.imageVidFile = imageVidFile;
    }

    public String getEvent_image_video_url() {
        return event_image_video_url;
    }

    public void setEvent_image_video_url(String event_image_video_url) {
        this.event_image_video_url = event_image_video_url;
    }

    public String getEvent_image_video_id() {
        return event_image_video_id;
    }

    public void setEvent_image_video_id(String event_image_video_id) {
        this.event_image_video_id = event_image_video_id;
    }


}
