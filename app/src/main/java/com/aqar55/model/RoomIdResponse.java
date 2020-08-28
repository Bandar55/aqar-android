package com.aqar55.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomIdResponse {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("response_code")
    private int responseCode;

    public Data getData() {
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
        @SerializedName("blockroom_id")
        private String blockroomId;
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
        @SerializedName("receiver_id")
        private String receiverId;
        @Expose
        @SerializedName("sender_id")
        private String senderId;
        @Expose
        @SerializedName("room_id")
        private String roomId;
        @Expose
        @SerializedName("_id")
        private String Id;

        public String getBlockroomId() {
            return blockroomId;
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

        public String getReceiverId() {
            return receiverId;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getId() {
            return Id;
        }
    }
}

