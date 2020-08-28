package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionalListDataResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("termsCondition")
    @Expose
    private Boolean termsCondition;
    @SerializedName("professionalProfile")
    @Expose
    private Boolean professionalProfile;
    @SerializedName("businessProfile")
    @Expose
    private Boolean businessProfile;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("memberSince")
    @Expose
    private String memberSince;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("professionalId")
    @Expose
    private String professionalId;
    @SerializedName("businessId")
    @Expose
    private String businessId;
    @SerializedName("imagesFile")
    @Expose
    private List<Object> imagesFile = null;
    @SerializedName("videosFile")
    @Expose
    private List<Object> videosFile = null;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @Expose
    @SerializedName("currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Boolean getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(Boolean termsCondition) {
        this.termsCondition = termsCondition;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
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

    public List<Object> getImagesFile() {
        return imagesFile;
    }

    public void setImagesFile(List<Object> imagesFile) {
        this.imagesFile = imagesFile;
    }

    public List<Object> getVideosFile() {
        return videosFile;
    }

    public void setVideosFile(List<Object> videosFile) {
        this.videosFile = videosFile;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
