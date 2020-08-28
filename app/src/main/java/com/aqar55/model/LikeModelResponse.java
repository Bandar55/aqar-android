package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class LikeModelResponse {


    @Expose
    @SerializedName("liked")
    private boolean liked;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("response_code")
    private int responseCode;

    public boolean getLiked() {
        return liked;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }
}