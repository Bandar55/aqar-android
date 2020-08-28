package com.aqar55.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public  class UserChatListResponse implements Serializable {

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

    public static class Data implements Serializable {


        @Expose
        @SerializedName("modified")
        private String modified;
        @Expose
        @SerializedName("sender_id")
        private String senderId;
        @Expose
        @SerializedName("property_id")
        private String propertyId;
        @Expose
        @SerializedName("_id")
        private String Id;

        @Expose
        @SerializedName("receiver_id")
        private String receiver_id;

        @Expose
        @SerializedName("lastmessage")
        private String lastmessage;

        @Expose
        @SerializedName("fullName")
        private String fullName;

        @Expose
        @SerializedName("profileImage")
        private String profileImage;

        @Expose
        @SerializedName("created")
        private String created;

        @Expose
        @SerializedName("room_id")
        private String roomId;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getCreated() {
            return created;
        }

        public String getLastmessage() {
            return lastmessage;
        }

        public String getFullName() {
            return fullName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getModified() {
            return modified;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public String getId() {
            return Id;
        }
    }
}

