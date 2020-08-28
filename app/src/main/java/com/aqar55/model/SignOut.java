package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

public class SignOut {

    @SerializedName("response_message")
    private String responseMessage;
    @SerializedName("response_code")
    private int responseCode;

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
