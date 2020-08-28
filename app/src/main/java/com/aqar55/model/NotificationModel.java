package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class NotificationModel {


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
        @SerializedName("message")
        private String message;
        @Expose
        @SerializedName("notificationType")
        private String notificationtype;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("notificationReceiver")
        private String notificationreceiver;
        @Expose
        @SerializedName("notificationSender")
        private String notificationsender;
        @Expose
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("_id")
        private String Id;

        @Expose
        @SerializedName("propOrRoomOrUserId")
        private String propOrRoomOrUserId;
        @Expose
        @SerializedName("propOrUserType")
        private String propOrUserType;

        @Expose
        @SerializedName("propertyTitle")
        private String propertyTitle;


        @Expose
        @SerializedName("description")
        private String description;

        public String getPropertyTitle() {
            return propertyTitle;
        }

        public String getDescription() {
            return description;
        }

        public String getPropOrUserType() {
            return propOrUserType;
        }

        public String getPropOrRoomOrUserId() {
            return propOrRoomOrUserId;
        }

        public int getV() {
            return V;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public String getMessage() {
            return message;
        }

        public String getNotificationtype() {
            return notificationtype;
        }

        public String getTitle() {
            return title;
        }

        public String getNotificationreceiver() {
            return notificationreceiver;
        }

        public String getNotificationsender() {
            return notificationsender;
        }

        public String getUserid() {
            return userid;
        }

        public String getId() {
            return Id;
        }
    }
}
