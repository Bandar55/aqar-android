package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class TotalPropertyOfUserModel implements Serializable {
    @Expose
    @SerializedName("property")
    private int property;
    @Expose
    @SerializedName("days")
    private Days days;
    @Expose
    @SerializedName("user")
    private User user;
    @Expose
    @SerializedName("status")
    private String status;

    public int getProperty() {
        return property;
    }

    public Days getDays() {
        return days;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public static class Days implements Serializable {
        @Expose
        @SerializedName("businessRemainingDays")
        private int businessremainingdays;
        @Expose
        @SerializedName("profRemainingDays")
        private int profremainingdays;

        public int getBusinessremainingdays() {
            return businessremainingdays;
        }

        public int getProfremainingdays() {
            return profremainingdays;
        }
    }

    public static class User implements Serializable{
        @Expose
        @SerializedName("remainingDays")
        private String remainingdays;
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
        @SerializedName("videosFile")
        private List<String> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<String> imagesfile;
        @Expose
        @SerializedName("profileId")
        private String profileid;
        @Expose
        @SerializedName("businessId")
        private String businessid;
        @Expose
        @SerializedName("professionalId")
        private String professionalid;
        @Expose
        @SerializedName("speakLanguage")
        private String speaklanguage;
        @Expose
        @SerializedName("appLanguage")
        private String applanguage;
        @Expose
        @SerializedName("measurement")
        private String measurement;
        @Expose
        @SerializedName("currency")
        private String currency;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("gender")
        private String gender;
        @Expose
        @SerializedName("subCategory")
        private String subcategory;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("mobileNumber")
        private String mobilenumber;
        @Expose
        @SerializedName("countryCode")
        private String countrycode;
        @Expose
        @SerializedName("deviceToken")
        private String devicetoken;
        @Expose
        @SerializedName("deviceType")
        private String devicetype;
        @Expose
        @SerializedName("jwtToken")
        private String jwttoken;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("totalDays")
        private String totaldays;
        @Expose
        @SerializedName("profileImage")
        private String profileimage;
        @Expose
        @SerializedName("businessProfile")
        private boolean businessprofile;
        @Expose
        @SerializedName("professionalProfile")
        private boolean professionalprofile;
        @Expose
        @SerializedName("termsCondition")
        private boolean termscondition;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("fullName")
        private String fullname;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("status")
        private String status;

        @Expose
        @SerializedName("professional_id")
        private String professional_id;
        @Expose
        @SerializedName("business_id")
        private String business_id;




        @Expose
        @SerializedName("facebookUrl")
        private String facebookUrl;


        @Expose
        @SerializedName("googleplusUrl")
        private String googleplusUrl;


        @Expose
        @SerializedName("twitterUrl")
        private String twitterUrl;


        @Expose
        @SerializedName("snapchatUrl")
        private String snapchatUrl;


        @Expose
        @SerializedName("linkedinUrl")
        private String linkedinUrl;


        public String getFacebookUrl() {
            return facebookUrl;
        }

        public String getGoogleplusUrl() {
            return googleplusUrl;
        }

        public String getTwitterUrl() {
            return twitterUrl;
        }

        public String getSnapchatUrl() {
            return snapchatUrl;
        }

        public String getLinkedinUrl() {
            return linkedinUrl;
        }

        public String getRemainingdays() {
            return remainingdays;
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

        public String getGender() {
            return gender;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public String getCategory() {
            return category;
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

        public String getTotaldays() {
            return totaldays;
        }

        public String getProfileimage() {
            return profileimage;
        }

        public boolean getBusinessprofile() {
            return businessprofile;
        }

        public boolean getProfessionalprofile() {
            return professionalprofile;
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

        public String getType() {
            return type;
        }
        public String getStatus() {
            return status;
        }


        public void setRemainingdays(String remainingdays) {
            this.remainingdays = remainingdays;
        }

        public String getProfessional_id() {
            return professional_id;
        }

        public void setProfessional_id(String professional_id) {
            this.professional_id = professional_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }
    }
}
