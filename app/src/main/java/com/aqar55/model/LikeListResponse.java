package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LikeListResponse implements Serializable {
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

    public static class Data implements Serializable{


        @Expose
        @SerializedName("rentTime")
        private String rentTime;

        public String getRentTime() {
            return rentTime;
        }

        @Expose
        @SerializedName("userId")
        private String userId;

        @Expose
        @SerializedName("likedStatus")
        private String likedStatus;

        public String getLikedStatus() {
            return likedStatus;
        }

        public String getUserData() {
            return userId;
        }

        @Expose
        @SerializedName("professionalUserId")
        private String professionalUserId;

        public String getProfessionalUserId() {
            return professionalUserId;
        }

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
        private List<VideosFile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<ImagesFile> imagesfile;
        @Expose
        @SerializedName("long")
        private String lon;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("buildingNo")
        private String buildingno;
        @Expose
        @SerializedName("apartmentNo")
        private String apartmentno;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("zipcode")
        private String zipcode;
        @Expose
        @SerializedName("area")
        private String area;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("views")
        private String views;
        @Expose
        @SerializedName("parkingOption")
        private String parkingoption;
        @Expose
        @SerializedName("furnish")
        private String furnish;
        @Expose
        @SerializedName("outdoor")
        private String outdoor;
        @Expose
        @SerializedName("indoor")
        private String indoor;


        @Expose
        @SerializedName("modularKitchen")
        private boolean modularkitchen;
        @Expose
        @SerializedName("parking")
        private boolean parking;
        @Expose
        @SerializedName("garden")
        private boolean garden;
        @Expose
        @SerializedName("balcony")
        private boolean balcony;
        @Expose
        @SerializedName("pricePerMeter")
        private String pricepermeter;
        @Expose
        @SerializedName("sizem2")
        private String sizem2;
        @Expose
        @SerializedName("totalPrice")
        private String totalprice;
        @Expose
        @SerializedName("totalPriceSale")
        private String totalpricesale;
        @Expose
        @SerializedName("revenue")
        private String revenue;
        @Expose
        @SerializedName("extrashowroomNo")
        private String extrashowroomno;
        @Expose
        @SerializedName("extrabuildingNo")
        private String extrabuildingno;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("streetWidthUnit")
        private String streetwidthunit;
        @Expose
        @SerializedName("streetWidth")
        private String streetwidth;
        @Expose
        @SerializedName("streetView")
        private String streetview;
        @Expose
        @SerializedName("yearBuilt")
        private String yearbuilt;
        @Expose
        @SerializedName("plotSizeUnit")
        private String plotsizeunit;
        @Expose
        @SerializedName("plotSize")
        private String plotsize;
        @Expose
        @SerializedName("builtSizeUnit")
        private String builtsizeunit;
        @Expose
        @SerializedName("builtSize")
        private String builtsize;
        @Expose
        @SerializedName("floor")
        private String floor;
        @Expose
        @SerializedName("kitchens")
        private String kitchens;
        @Expose
        @SerializedName("bathrooms")
        private String bathrooms;
        @Expose
        @SerializedName("bedrooms")
        private String bedrooms;
        @Expose
        @SerializedName("available")
        private String available;
        @Expose
        @SerializedName("purpose")
        private String purpose;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("title")
        private String title;


        @Expose
        @SerializedName("mobileNumber")
        private String mobileNumber;
//        @Expose
//        @SerializedName("userId")
//        private String userid;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("totalDays")
        private String totaldays;
        @Expose
        @SerializedName("liked")
        private boolean liked;
        @Expose
        @SerializedName("status")
        private String status;


        @Expose
        @SerializedName("currency")
        private String currency;

        public String getCurrency() {
            return currency;
        }

        public String getUserId() {
            return userId;
        }


        public String getMobileNumber() {
            return mobileNumber;
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

        public List<VideosFile> getVideosfile() {
            return videosfile;
        }

        public List<ImagesFile> getImagesfile() {
            return imagesfile;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }

        public String getBuildingno() {
            return buildingno;
        }

        public String getApartmentno() {
            return apartmentno;
        }

        public String getAddress() {
            return address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getArea() {
            return area;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public String getViews() {
            return views;
        }

        public String getParkingoption() {
            return parkingoption;
        }

        public String getFurnish() {
            return furnish;
        }

        public String getOutdoor() {
            return outdoor;
        }

        public String getIndoor() {
            return indoor;
        }

        public boolean getModularkitchen() {
            return modularkitchen;
        }

        public boolean getParking() {
            return parking;
        }

        public boolean getGarden() {
            return garden;
        }

        public boolean getBalcony() {
            return balcony;
        }

        public String getPricepermeter() {
            return pricepermeter;
        }

        public String getSizem2() {
            return sizem2;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public String getTotalpricesale() {
            return totalpricesale;
        }

        public String getRevenue() {
            return revenue;
        }

        public String getExtrashowroomno() {
            return extrashowroomno;
        }

        public String getExtrabuildingno() {
            return extrabuildingno;
        }

        public String getDescription() {
            return description;
        }

        public String getStreetwidthunit() {
            return streetwidthunit;
        }

        public String getStreetwidth() {
            return streetwidth;
        }

        public String getStreetview() {
            return streetview;
        }

        public String getYearbuilt() {
            return yearbuilt;
        }

        public String getPlotsizeunit() {
            return plotsizeunit;
        }

        public String getPlotsize() {
            return plotsize;
        }

        public String getBuiltsizeunit() {
            return builtsizeunit;
        }

        public String getBuiltsize() {
            return builtsize;
        }

        public String getFloor() {
            return floor;
        }

        public String getKitchens() {
            return kitchens;
        }

        public String getBathrooms() {
            return bathrooms;
        }

        public String getBedrooms() {
            return bedrooms;
        }

        public String getAvailable() {
            return available;
        }

        public String getPurpose() {
            return purpose;
        }

        public String getCategory() {
            return category;
        }

        public String getTitle() {
            return title;
        }

//        public String getUserid() {
//            return userid;
//        }

        public String getType() {
            return type;
        }

        public String getId() {
            return Id;
        }

        public String getTotaldays() {
            return totaldays;
        }

        public boolean getLiked() {
            return liked;
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

    /*public static class Userid {
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

    }*/
}


