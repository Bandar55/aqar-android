package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sign_Up {
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

    public static class DataEntity {
        @SerializedName("__v")
        private int V;
        @SerializedName("socialDocuments")
        private List<String> socialdocuments;
        @SerializedName("videosFile")
        private List<String> videosfile;
        @SerializedName("imagesFile")
        private List<String> imagesfile;
        @SerializedName("profileId")
        private String profileid;
        @SerializedName("businessId")
        private String businessid;
        @SerializedName("professionalId")
        private String professionalid;
        @SerializedName("speakLanguage")
        private String speaklanguage;
        @SerializedName("appLanguage")
        private String applanguage;
        @SerializedName("measurement")
        private String measurement;
        @SerializedName("currency")
        private String currency;
        @SerializedName("country")
        private String country;
        @SerializedName("birthYear")
        private String birthyear;
        @SerializedName("gender")
        private String gender;
        @SerializedName("subCategory")
        private String subcategory;
        @SerializedName("mobileNumber")
        private String mobilenumber;
        @SerializedName("countryCode")
        private String countrycode;
        @SerializedName("deviceToken")
        private String devicetoken;
        @SerializedName("deviceType")
        private String devicetype;
        @SerializedName("jwtToken")
        private String jwttoken;
        @SerializedName("_id")
        private String Id;
        @SerializedName("createdAt1")
        private String createdat1;
        @SerializedName("created")
        private String created;
        @SerializedName("termsCondition")
        private boolean termscondition;
        @SerializedName("email")
        private String email;
        @SerializedName("fullName")
        private String fullname;
        @SerializedName("status")
        private String status;

        public int getV() {
            return V;
        }

        public List<String> getSocialdocuments() {
            return socialdocuments;
        }

        public List<String> getVideosfile() {
            return videosfile;
        }

        public List<String> getImagesfile() {
            return imagesfile;
        }

        public String getProfileid() {
            return profileid;
        }

        public String getBusinessid() {
            return businessid;
        }

        public String getProfessionalid() {
            return professionalid;
        }

        public String getSpeaklanguage() {
            return speaklanguage;
        }

        public String getApplanguage() {
            return applanguage;
        }

        public String getMeasurement() {
            return measurement;
        }

        public String getCurrency() {
            return currency;
        }

        public String getCountry() {
            return country;
        }

        public String getBirthyear() {
            return birthyear;
        }

        public String getGender() {
            return gender;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public String getDevicetoken() {
            return devicetoken;
        }

        public String getDevicetype() {
            return devicetype;
        }

        public String getJwttoken() {
            return jwttoken;
        }

        public String getId() {
            return Id;
        }

        public String getCreatedat1() {
            return createdat1;
        }

        public String getCreated() {
            return created;
        }

        public boolean getTermscondition() {
            return termscondition;
        }

        public String getEmail() {
            return email;
        }

        public String getFullname() {
            return fullname;
        }

        public String getStatus() {
            return status;
        }
    }

    public Sign_Up(DataEntity data) {
        this.data = data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }


}
