package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class MessageChat {

    @Expose
    @SerializedName("Data")
    private List<Data> data;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("response_code")
    private int responseCode;
    private String nickname;
    private String message;
    private String sendderId;
    private String creayedTime;
    private String recieverId;


    public MessageChat(List<Data> data, String responseMessage, int responseCode, String nickname, String message, String sendderId, String creayedTime, String recieverId) {
        this.data = data;
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
        this.nickname = nickname;
        this.message = message;
        this.sendderId = sendderId;
        this.creayedTime = creayedTime;
        this.recieverId = recieverId;
    }

    public List<Data> getData() {
        return data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }

    public String getSendderId() {
        return sendderId;
    }

    public String getCreayedTime() {
        return creayedTime;
    }

    public String getRecieverId() {
        return recieverId;
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
        @SerializedName("receiver_id")
        private String receiverId;
        @Expose
        @SerializedName("room_id")
        private String roomId;
        @Expose
        @SerializedName("sender_id")
        private String senderId;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("message")
        private String message;

        @Expose
        @SerializedName("attachment")
        private String attachment;

        @Expose
        @SerializedName("attachment_type")
        private String attachment_type;


        public Data(int v, String created, String modified, String receiverId, String roomId, String senderId, String id, String message,String attachment_type,String attachment) {
            V = v;
            this.created = created;
            this.modified = modified;
            this.receiverId = receiverId;
            this.roomId = roomId;
            this.senderId = senderId;
            Id = id;
            this.message = message;
            this.attachment_type=attachment_type;
            this.attachment=attachment;
        }

        public String getAttachment() {
            return attachment;
        }

        public String getAttachment_type() {
            return attachment_type;
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

        public String getRoomId() {
            return roomId;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getId() {
            return Id;
        }

        public String getMessage() {
            return message;
        }
    }



}
