package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetUserDetails implements Serializable{
    @SerializedName("Data")
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
    public static class DataEntity implements Serializable {
        @SerializedName("socialDocuments")
        private List<String> socialdocuments;
        @SerializedName("lat")
        private String lat;
        @SerializedName("long")
        private String lng;
        @SerializedName("location")
        private String location;
        @SerializedName("profileImage")
        private String profileimage;
        @SerializedName("__v")
        private int V;
        @SerializedName("videosFile")
        private List<VideosFileEntity> videosfile;
        @SerializedName("imagesFile")
        private List<ImagesFileEntity> imagesfile;
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
        @SerializedName("category")
        private String category;
        @SerializedName("mobileNumber")
        private String mobilenumber;
        @SerializedName("countryCode")
        private String countrycode;
        @SerializedName("memberSince")
        private String membersince;
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
        private String createdat;

        @SerializedName("modified")
        private String modified;

        @SerializedName("termsCondition")
        private boolean termscondition;
        @SerializedName("email")
        private String email;
        @SerializedName("fullName")
        private String fullname;
        @SerializedName("status")
        private String status;

        @SerializedName("state")
        private String state;
        @SerializedName("zipcode")
        private String zipcode;

        @SerializedName("city")
        private String city;
        @SerializedName("area")
        private String area;
        @SerializedName("website")
        private String website;
        @SerializedName("govtIdImage1")
        private String govtIdImage1;
        @SerializedName("govtIdImage2")
        private String govtIdImage2;


        @SerializedName("description")
        private String description;
        @SerializedName("projectAchieved")
        private String projectAchieved;

        @SerializedName("govtIdNumber1")
        private String govtIdNumber1;
        @SerializedName("govtIdNumber2")
        private String govtIdNumber2;

        @SerializedName("facebookUrl")
        private String facebookUrl;

        @SerializedName("googleplusUrl")
        private String googleplusUrl;


        @SerializedName("twitterUrl")
        private String twitterUrl;

        @SerializedName("snapchatUrl")
        private String snapchatUrl;

        @SerializedName("linkedinUrl")
        private String linkedinUrl;


        @SerializedName("specialities")
        private String specialities;

        @SerializedName("areaCovered")
        private String areaCovered;


        @SerializedName("govtIdType2")
        private String govtIdType2;
        @SerializedName("govtIdType1")
        private String govtIdType1;

        @SerializedName("totalDays")
        private  String totalDays;
        @SerializedName("remainingDays")
        private String remainingDays;

        @SerializedName("professionalProfile")
        private Boolean professionalProfile;

        @SerializedName("businessProfile")
        private Boolean businessProfile;



        @SerializedName("birthDate")
        private String birthDate;

        @SerializedName("Type")
        private String Type;

        @SerializedName("liked")
        private Boolean liked;


        @SerializedName("notification")
        private boolean notification;

        @SerializedName("userId")
        private String userId;

        public String getUserId() {
            return userId;
        }

        public boolean isNotification() {
            return notification;
        }

        @SerializedName("likedStatus")
        private String likedStatus;

        @SerializedName("counter")
        private int count;

        @SerializedName("totalPropertyCreated")
        private String totalPropertyCreated;


        @SerializedName("commanUserProfessionalId")
        private String commanUserProfessionalId;

        @SerializedName("commanUserBusinessId")
        private String commanUserBusinessId;

        @SerializedName("businessProfileExists")
        private boolean businessProfileExists;

        @SerializedName("professionalProfileExists")
        private boolean professionalProfileExists;

        public String getCommanUserProfessionalId() {
            return commanUserProfessionalId;
        }

        public String getCommanUserBusinessId() {
            return commanUserBusinessId;
        }

        public boolean isBusinessProfileExists() {
            return businessProfileExists;
        }

        public boolean isProfessionalProfileExists() {
            return professionalProfileExists;
        }

        public String getModified() {
            return modified;
        }

        public String getTotalPropertyCreated() {
            return totalPropertyCreated;
        }

        public int getCount() {
            return count;
        }

        public Boolean getLiked() {
            return liked;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLikedStatus() {
            return likedStatus;
        }

        public void setLikedStatus(String likedStatus) {
            this.likedStatus = likedStatus;
        }

        public List<String> getSocialdocuments() {
            return socialdocuments;
        }

        public String getLat() {
            return lat;
        }

        public String getLocation() {
            return location;
        }

        public String getProfileimage() {
            return profileimage;
        }

        public int getV() {
            return V;
        }

        public List<VideosFileEntity> getVideosfile() {
            return videosfile;
        }

        public List<ImagesFileEntity> getImagesfile() {
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

        public String getMembersince() {
            return membersince;
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

        public String getCreatedat() {
            return createdat;
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

        public void setSocialdocuments(List<String> socialdocuments) {
            this.socialdocuments = socialdocuments;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }


        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getGovtIdImage1() {
            return govtIdImage1;
        }

        public void setGovtIdImage1(String govtIdImage1) {
            this.govtIdImage1 = govtIdImage1;
        }

        public String getGovtIdImage2() {
            return govtIdImage2;
        }

        public void setGovtIdImage2(String govtIdImage2) {
            this.govtIdImage2 = govtIdImage2;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getGovtIdNumber1() {
            return govtIdNumber1;
        }

        public void setGovtIdNumber1(String govtIdNumber1) {
            this.govtIdNumber1 = govtIdNumber1;
        }

        public String getGovtIdNumber2() {
            return govtIdNumber2;
        }

        public void setGovtIdNumber2(String govtIdNumber2) {
            this.govtIdNumber2 = govtIdNumber2;
        }


        public void setProfileimage(String profileimage) {
            this.profileimage = profileimage;
        }

        public String getFacebookUrl() {
            return facebookUrl;
        }

        public void setFacebookUrl(String facebookUrl) {
            this.facebookUrl = facebookUrl;
        }

        public String getGoogleplusUrl() {
            return googleplusUrl;
        }

        public void setGoogleplusUrl(String googleplusUrl) {
            this.googleplusUrl = googleplusUrl;
        }

        public String getTwitterUrl() {
            return twitterUrl;
        }

        public void setTwitterUrl(String twitterUrl) {
            this.twitterUrl = twitterUrl;
        }

        public String getSnapchatUrl() {
            return snapchatUrl;
        }

        public void setSnapchatUrl(String snapchatUrl) {
            this.snapchatUrl = snapchatUrl;
        }

        public String getLinkedinUrl() {
            return linkedinUrl;
        }

        public void setLinkedinUrl(String linkedinUrl) {
            this.linkedinUrl = linkedinUrl;
        }

        public void setV(int v) {
            V = v;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProjectAchieved() {
            return projectAchieved;
        }

        public void setProjectAchieved(String projectAchieved) {
            this.projectAchieved = projectAchieved;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getAreaCovered() {
            return areaCovered;
        }

        public void setAreaCovered(String areaCovered) {
            this.areaCovered = areaCovered;
        }


        public void setVideosfile(List<VideosFileEntity> videosfile) {
            this.videosfile = videosfile;
        }

        public String getGovtIdType2() {
            return govtIdType2;
        }

        public void setGovtIdType2(String govtIdType2) {
            this.govtIdType2 = govtIdType2;
        }

        public String getGovtIdType1() {
            return govtIdType1;
        }

        public void setGovtIdType1(String govtIdType1) {
            this.govtIdType1 = govtIdType1;
        }

        public void setImagesfile(List<ImagesFileEntity> imagesfile) {
            this.imagesfile = imagesfile;
        }

        public String getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(String totalDays) {
            this.totalDays = totalDays;
        }

        public String getRemainingDays() {
            return remainingDays;
        }

        public void setRemainingDays(String remainingDays) {
            this.remainingDays = remainingDays;
        }

        public void setProfileid(String profileid) {
            this.profileid = profileid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public Boolean getProfessionalProfile() {
            return professionalProfile;
        }

        public void setProfessionalProfile(Boolean professionalProfile) {
            this.professionalProfile = professionalProfile;
        }

        public Boolean getBusinessProfile() {
            return businessProfile;
        }

        public void setBusinessProfile(Boolean businessProfile) {
            this.businessProfile = businessProfile;
        }

        public void setProfessionalid(String professionalid) {
            this.professionalid = professionalid;
        }


        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }


        public void setSpeaklanguage(String speaklanguage) {
            this.speaklanguage = speaklanguage;
        }

        public void setApplanguage(String applanguage) {
            this.applanguage = applanguage;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setBirthyear(String birthyear) {
            this.birthyear = birthyear;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setMobilenumber(String mobilenumber) {
            this.mobilenumber = mobilenumber;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }

        public void setMembersince(String membersince) {
            this.membersince = membersince;
        }

        public void setDevicetoken(String devicetoken) {
            this.devicetoken = devicetoken;
        }

        public void setDevicetype(String devicetype) {
            this.devicetype = devicetype;
        }

        public void setId(String id) {
            Id = id;
        }

        public boolean isTermscondition() {
            return termscondition;
        }

        public void setTermscondition(boolean termscondition) {
            this.termscondition = termscondition;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }


        public String getType() {
            return Type;
        }


    }

    public static class VideosFileEntity implements Serializable {
        @SerializedName("video")
        private String video;
        @SerializedName("_id")
        private String Id;

        public String getVideo() {
            return video;
        }

        public String getId() {
            return Id;
        }
    }

    public static class ImagesFileEntity implements  Serializable  {
        @SerializedName("image")
        private String image;
        @SerializedName("_id")
        private String Id;

        public String getImage() {
            return image;
        }

        public String getId() {
            return Id;
        }
    }




}
