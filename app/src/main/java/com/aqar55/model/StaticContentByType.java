package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

public class StaticContentByType {

    @SerializedName("data")
    private DataEntity data;
    @SerializedName("response_message")
    private String responseMessage;
    @SerializedName("response_code")
    private int responseCode;

    public DataEntity getData() {
        return data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public class DataEntity {
        @SerializedName("__v")
        private int V;
        @SerializedName("contentType")
        private String contenttype;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("_id")
        private String Id;
        @SerializedName("createdAt1")
        private String createdat1;
        @SerializedName("createdAt")
        private String createdat;

        public int getV() {
            return V;
        }

        public String getContenttype() {
            return contenttype;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public String getId() {
            return Id;
        }

        public String getCreatedat1() {
            return createdat1;
        }

        public String getCreatedat() {
            return createdat;
        }
    }
}
