package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class AdminDetailsModel {


    @Expose
    @SerializedName("Data")
    private List<Data> data;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("response_code")
    private int responseCode;

    public List<Data> getData() {
        return data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public static class Data {
        @Expose
        @SerializedName("__v")
        private int V;
        @Expose
        @SerializedName("created")
        private String created;
        @Expose
        @SerializedName("modified")
        private String modified;
        @Expose
        @SerializedName("details")
        private String details;
        @Expose
        @SerializedName("reason")
        private String reason;
        @Expose
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("Type")
        private String type;

        public int getV() {
            return V;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public String getDetails() {
            return details;
        }

        public String getReason() {
            return reason;
        }

        public String getUserid() {
            return userid;
        }

        public String getId() {
            return Id;
        }

        public String getType() {
            return type;
        }
    }
}
