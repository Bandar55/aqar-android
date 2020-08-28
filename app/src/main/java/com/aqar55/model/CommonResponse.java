package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CommonResponse implements Serializable {

    @SerializedName("location")
    private LocationResponse location;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String longg;

    @SerializedName("status")
    private String status;

    @SerializedName("Type")
    private String Type;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("termsCondition")
    private boolean termsCondition;

    @SerializedName("professionalProfile")
    private boolean professionalProfile;

    @SerializedName("businessProfile")
    private boolean businessProfile;

    @SerializedName("totalDays")
    private String totalDays;

    @SerializedName("_id")
    private String _id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("category")
    private String category;

    @SerializedName("imagesFile")
    private ArrayList<ImageFileOrVideoResponse> imagesFile;

    @SerializedName("videosFile")
    private ArrayList<ImageFileOrVideoResponse> videosFile;

    @SerializedName("subCategory")
    private String subCategory;

    @SerializedName("state")
    private String state;

    @SerializedName("city")
    private String city;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("area")
    private String area;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("website")
    private String website;

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

    @SerializedName("description")
    private String description;

    @SerializedName("projectAchieved")
    private String projectAchieved;

    @SerializedName("specialities")
    private String specialities;

    @SerializedName("areaCovered")
    private String areaCovered;

    @SerializedName("govtIdType1")
    private String govtIdType1;

    @SerializedName("govtIdNumber1")
    private String govtIdNumber1;

    @SerializedName("govtIdType2")
    private String govtIdType2;

    @SerializedName("govtIdNumber2")
    private String govtIdNumber2;

    @SerializedName("professionalId")
    private String professionalId;

    @SerializedName("businessId")
    private String businessId;

    @SerializedName("modified")
    private String modified;

    @SerializedName("created")
    private String created;

    @SerializedName("__v")
    private String __v;

    @SerializedName("govtIdImage2")
    private String govtIdImage2;

    @SerializedName("memberSince")
    private String memberSince;

    @SerializedName("govtIdImage1")
    private String govtIdImage1;

    @SerializedName("remainingDays")
    private String remainingDays;

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getGovtIdType1() {
        return govtIdType1;
    }

    public void setGovtIdType1(String govtIdType1) {
        this.govtIdType1 = govtIdType1;
    }

    public String getGovtIdNumber1() {
        return govtIdNumber1;
    }

    public void setGovtIdNumber1(String govtIdNumber1) {
        this.govtIdNumber1 = govtIdNumber1;
    }

    public String getGovtIdType2() {
        return govtIdType2;
    }

    public void setGovtIdType2(String govtIdType2) {
        this.govtIdType2 = govtIdType2;
    }

    public String getGovtIdNumber2() {
        return govtIdNumber2;
    }

    public void setGovtIdNumber2(String govtIdNumber2) {
        this.govtIdNumber2 = govtIdNumber2;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getGovtIdImage2() {
        return govtIdImage2;
    }

    public void setGovtIdImage2(String govtIdImage2) {
        this.govtIdImage2 = govtIdImage2;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getGovtIdImage1() {
        return govtIdImage1;
    }

    public void setGovtIdImage1(String govtIdImage1) {
        this.govtIdImage1 = govtIdImage1;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(boolean termsCondition) {
        this.termsCondition = termsCondition;
    }

    public boolean isProfessionalProfile() {
        return professionalProfile;
    }

    public void setProfessionalProfile(boolean professionalProfile) {
        this.professionalProfile = professionalProfile;
    }

    public boolean isBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(boolean businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ImageFileOrVideoResponse> getImagesFile() {
        return imagesFile;
    }

    public void setImagesFile(ArrayList<ImageFileOrVideoResponse> imagesFile) {
        this.imagesFile = imagesFile;
    }

    public ArrayList<ImageFileOrVideoResponse> getVideosFile() {
        return videosFile;
    }

    public void setVideosFile(ArrayList<ImageFileOrVideoResponse> videosFile) {
        this.videosFile = videosFile;
    }
}
