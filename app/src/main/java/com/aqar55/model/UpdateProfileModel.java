package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class UpdateProfileModel {
    @Expose
    @SerializedName("Data")
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
        @SerializedName("remainingDays")
        private String remainingdays;
        @Expose
        @SerializedName("counter")
        private int counter;
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
        @SerializedName("businessId")
        private String businessid;
        @Expose
        @SerializedName("professionalId")
        private String professionalid;
        @Expose
        @SerializedName("mobileNumber")
        private String mobilenumber;
        @Expose
        @SerializedName("countryCode")
        private String countrycode;
        @Expose
        @SerializedName("long")
        private String longg;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("area")
        private String area;
        @Expose
        @SerializedName("zipcode")
        private String zipcode;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("subCategory")
        private String subcategory;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("birthDate")
        private String birthdate;
        @Expose
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("room_id")
        private String roomId;
        @Expose
        @SerializedName("notification")
        private boolean notification;
        @Expose
        @SerializedName("totalDays")
        private String totaldays;
        @Expose
        @SerializedName("areaCovered")
        private String areacovered;
        @Expose
        @SerializedName("specialities")
        private String specialities;
        @Expose
        @SerializedName("projectAchieved")
        private String projectachieved;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("linkedinUrl")
        private String linkedinurl;
        @Expose
        @SerializedName("snapchatUrl")
        private String snapchaturl;
        @Expose
        @SerializedName("twitterUrl")
        private String twitterurl;
        @Expose
        @SerializedName("googleplusUrl")
        private String googleplusurl;
        @Expose
        @SerializedName("facebookUrl")
        private String facebookurl;
        @Expose
        @SerializedName("govtIdImage2")
        private String govtidimage2;
        @Expose
        @SerializedName("govtIdNumber2")
        private String govtidnumber2;
        @Expose
        @SerializedName("govtIdType2")
        private String govtidtype2;
        @Expose
        @SerializedName("govtIdImage1")
        private String govtidimage1;
        @Expose
        @SerializedName("govtIdNumber1")
        private String govtidnumber1;
        @Expose
        @SerializedName("govtIdType1")
        private String govtidtype1;
        @Expose
        @SerializedName("website")
        private String website;
        @Expose
        @SerializedName("profileImage")
        private String profileimage;
        @Expose
        @SerializedName("business_id")
        private String businessId;
        @Expose
        @SerializedName("professional_id")
        private String professionalId;
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
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("fullName")
        private String fullname;
        @Expose
        @SerializedName("memberSince")
        private String membersince;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("locat")
        private Locat locat;

        public String getRemainingdays() {
            return remainingdays;
        }

        public int getCounter() {
            return counter;
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

        public String getBusinessid() {
            return businessid;
        }

        public String getProfessionalid() {
            return professionalid;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public String getLongg() {
            return longg;
        }

        public String getLat() {
            return lat;
        }

        public String getArea() {
            return area;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public String getCategory() {
            return category;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public String getUserid() {
            return userid;
        }

        public String getId() {
            return Id;
        }

        public String getRoomId() {
            return roomId;
        }

        public boolean getNotification() {
            return notification;
        }

        public String getTotaldays() {
            return totaldays;
        }

        public String getAreacovered() {
            return areacovered;
        }

        public String getSpecialities() {
            return specialities;
        }

        public String getProjectachieved() {
            return projectachieved;
        }

        public String getDescription() {
            return description;
        }

        public String getLinkedinurl() {
            return linkedinurl;
        }

        public String getSnapchaturl() {
            return snapchaturl;
        }

        public String getTwitterurl() {
            return twitterurl;
        }

        public String getGoogleplusurl() {
            return googleplusurl;
        }

        public String getFacebookurl() {
            return facebookurl;
        }

        public String getGovtidimage2() {
            return govtidimage2;
        }

        public String getGovtidnumber2() {
            return govtidnumber2;
        }

        public String getGovtidtype2() {
            return govtidtype2;
        }

        public String getGovtidimage1() {
            return govtidimage1;
        }

        public String getGovtidnumber1() {
            return govtidnumber1;
        }

        public String getGovtidtype1() {
            return govtidtype1;
        }

        public String getWebsite() {
            return website;
        }

        public String getProfileimage() {
            return profileimage;
        }

        public String getBusinessId() {
            return businessId;
        }

        public String getProfessionalId() {
            return professionalId;
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

        public String getEmail() {
            return email;
        }

        public String getFullname() {
            return fullname;
        }

        public String getMembersince() {
            return membersince;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public Locat getLocat() {
            return locat;
        }
    }

    public static class Likeuserid {
        @Expose
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("_id")
        private String Id;

        public String getUserid() {
            return userid;
        }

        public String getId() {
            return Id;
        }
    }

    public static class Locat {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("coordinates")
        private List<Double> coordinates;

        public String getType() {
            return type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }
    }
}
