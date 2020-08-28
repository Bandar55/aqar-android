package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionalBusinessModelResponse {


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
        @SerializedName("userId")
        private String userId;

        public String getUserData() {
            return userId;
        }
        @Expose
        @SerializedName("remainingDays")
        private String remainingdays;
        @Expose
        @SerializedName("govtIdImage2")
        private String govtidimage2;
        @Expose
        @SerializedName("govtIdImage1")
        private String govtidimage1;
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
        private List<VideosFile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<ImagesFile> imagesfile;
        @Expose
        @SerializedName("govtIdNumber2")
        private String govtidnumber2;
        @Expose
        @SerializedName("govtIdType2")
        private String govtidtype2;
        @Expose
        @SerializedName("govtIdNumber1")
        private String govtidnumber1;
        @Expose
        @SerializedName("govtIdType1")
        private String govtidtype1;
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
        @SerializedName("website")
        private String website;
        @Expose
        @SerializedName("mobileNumber")
        private String mobilenumber;
        @Expose
        @SerializedName("long")
        private String lon;
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
        @SerializedName("categoryId")
        private String categoryid;
        @Expose
        @SerializedName("birthDate")
        private String birthdate;

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
        @SerializedName("business_id")
        private String business_id;


        @SerializedName("businessId")
        private String businessId;

        @Expose
        @SerializedName("professional_id")
        private String professional_id;


        @SerializedName("professionalId")
        private String professionalId;


        public String getBusinessId() {
            return businessId;
        }

        public String getProfessionalId() {
            return professionalId;
        }

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
        @SerializedName("liked")
        private boolean liked;
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
        @SerializedName("likedStatus")
        private String likedStatus;

        public String getLikedStatus() {
            return likedStatus;
        }

        public String getRemainingdays() {
            return remainingdays;
        }

        public String getGovtidimage2() {
            return govtidimage2;
        }

        public String getGovtidimage1() {
            return govtidimage1;
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

        public List<VideosFile> getVideosfile() {
            return videosfile;
        }

        public List<ImagesFile> getImagesfile() {
            return imagesfile;
        }


        public String getGovtidnumber2() {
            return govtidnumber2;
        }

        public String getGovtidtype2() {
            return govtidtype2;
        }

        public String getGovtidnumber1() {
            return govtidnumber1;
        }

        public String getGovtidtype1() {
            return govtidtype1;
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

        public String getWebsite() {
            return website;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public String getLon() {
            return lon;
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

        public String getCategoryid() {
            return categoryid;
        }

        public String getBirthdate() {
            return birthdate;
        }

    public String getUserid() {
         return userId;
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

        public String getBusiness_Id() {
            return business_id;
        }

        public String getProfessional_Id() {
            return professional_id;
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

        public boolean getLiked() {
            return liked;
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
    }

    public static class VideosFile {
        @Expose
        @SerializedName("video")
        private String video;
        @Expose
        @SerializedName("_id")
        private String Id;

        public String getVideo() {
            return video;
        }

        public String getId() {
            return Id;
        }
    }

    public static class ImagesFile {
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("_id")
        private String Id;

        public String getImage() {
            return image;
        }

        public String getId() {
            return Id;
        }
    }

    public static class Userid {
        @Expose
        @SerializedName("modified")
        private String modified;
        @Expose
        @SerializedName("__v")
        private int V;
        @Expose
        @SerializedName("socialDocuments")
        private List<String> socialdocuments;
        @Expose
        @SerializedName("videosFile")
        private List<GetPropertyDetail.Videosfile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<GetPropertyDetail.Imagesfile> imagesfile;
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
        @SerializedName("birthYear")
        private String birthyear;
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
        @SerializedName("createdAt1")
        private String createdat1;
        @Expose
        @SerializedName("createdAt")
        private String createdat;
        @Expose
        @SerializedName("_id")
        private String Id;
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
        @SerializedName("likedStatus")
        private String likedStatus;


        public String getLikedStatus() {
            return likedStatus;
        }

        public String getModified() {
            return modified;
        }

        public int getV() {
            return V;
        }

        public List<String> getSocialdocuments() {
            return socialdocuments;
        }

        public List<GetPropertyDetail.Videosfile> getVideosfile() {
            return videosfile;
        }

        public List<GetPropertyDetail.Imagesfile> getImagesfile() {
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

        public String getCreatedat1() {
            return createdat1;
        }

        public String getCreatedat() {
            return createdat;
        }

        public String getId() {
            return Id;
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

        public boolean isTermscondition() {
            return termscondition;
        }

    }
}

