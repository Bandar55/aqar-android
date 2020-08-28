package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class SubCatResponse {
    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("response_code")
    private int responseCode;

    public List<Data> getData() {
        return data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public static class Data {
        @Expose
        @SerializedName("__v")
        private int V;
        @Expose
        @SerializedName("categoryId")
        private Categoryid categoryid;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("createdAt1")
        private String createdat1;
        @Expose
        @SerializedName("createdAt")
        private String createdat;
        @Expose
        @SerializedName("_id")
        private String Id;

        public void setV(int v) {
            V = v;
        }

        public void setCategoryid(Categoryid categoryid) {
            this.categoryid = categoryid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCreatedat1(String createdat1) {
            this.createdat1 = createdat1;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }

        public void setId(String id) {
            Id = id;
        }

        public int getV() {
            return V;
        }

        public Categoryid getCategoryid() {
            return categoryid;
        }

        public String getName() {
            return name;
        }

        public String getCreatedat1() {
            return createdat1;
        }

        public String getCreatedat() {
            return createdat;
        }

        public String getId() {
            return Id;
        }
    }

    public static class Categoryid {
        @Expose
        @SerializedName("__v")
        private int V;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("createdAt1")
        private String createdat1;
        @Expose
        @SerializedName("createdAt")
        private String createdat;
        @Expose
        @SerializedName("_id")
        private String Id;

        public int getV() {
            return V;
        }

        public String getName() {
            return name;
        }

        public String getCreatedat1() {
            return createdat1;
        }

        public String getCreatedat() {
            return createdat;
        }

        public String getId() {
            return Id;
        }
    }
}
