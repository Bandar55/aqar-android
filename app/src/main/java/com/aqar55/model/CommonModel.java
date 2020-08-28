package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonModel {

    @SerializedName("response_code")
    private int response_code;

    @SerializedName("response_message")
    private String response_message;

    @SerializedName("status")
    private String status;

    @SerializedName("Data")
    private ArrayList<CommonResponse> Data;

    public String getStatus() {
        return status;
    }
    public ArrayList<CommonResponse> getData() {
        return Data;
    }

    public void setData(ArrayList<CommonResponse> data) {
        Data = data;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }
}
