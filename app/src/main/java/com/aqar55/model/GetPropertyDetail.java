package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetPropertyDetail implements Serializable {
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

    public static class Data implements Serializable{



        @Expose
        @SerializedName("userId")
        private String userId;

        @Expose
        @SerializedName("totalPropertyCreated")
        private int totalPropertyCreated;

        public int getTotalPropertyCreated() {
            return totalPropertyCreated;
        }

        @Expose
        @SerializedName("counter")
        private int counter;

        public int getCounter() {
            return counter;
        }

        public String getUserId() {
            return userId;
        }

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
        private List<Videosfile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<Imagesfile> imagesfile;
        @Expose
        @SerializedName("professionalId")
        private String professionalid;
        @Expose
        @SerializedName("businessId")
        private String businessid;

        @Expose
        @SerializedName("professionalUserId")
        private String professionalUserId;




        @Expose
        @SerializedName("parkingOption")
        private String parkingOption;
        @Expose
        @SerializedName("long")
        private String longg;
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
        @SerializedName("rentTime")
        private String rentTime;
        @Expose
        @SerializedName("totalPriceRent")
        private String totalPriceRent;
        @Expose
        @SerializedName("totalPriceSale")
        private String totalPriceSale;
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
        @SerializedName("store")
        private boolean store;
        @Expose
        @SerializedName("lift")
        private boolean lift;
        @Expose
        @SerializedName("duplex")
        private boolean duplex;
        @Expose
        @SerializedName("aircondition")
        private boolean aircondition;
        @Expose
        @SerializedName("furnished")
        private boolean furnished;
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
        @SerializedName("userData")
        private Userid userData;

        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("liked")
        private String liked;

        @Expose
        @SerializedName("likedStatus")
        private String likedStatus;

        @Expose
        @SerializedName("likeUserId")
        private List<LikedUser> likeUserId;
        @Expose
        @SerializedName("length")
        private String length;


        @Expose
        @SerializedName("defaultDailyPrice")
        private String defaultDailyPrice;

        @Expose
        @SerializedName("defaultWeeklyPrice")
        private String defaultWeeklyPrice;

        @Expose
        @SerializedName("defaultMonthlyPrice")
        private String defaultMonthlyPrice;


        @Expose
        @SerializedName("defaultyearlyPrice")
        private String defaultyearlyPrice;



        @Expose
        @SerializedName("mobileNumber")
        private String mobileNumber;
        @Expose
        @SerializedName("width")
        private String width;

        @Expose
        @SerializedName("widthUnit")
        private String widthUnit;

        @Expose
        @SerializedName("lengthUnit")
        private String lengthUnit;


        @Expose
        @SerializedName("totalPrice")
        private String totalPrice;


        public String getProfessionalUserId() {
            return professionalUserId;
        }

        public boolean isModularkitchen() {
            return modularkitchen;
        }

        public boolean isStore() {
            return store;
        }

        public boolean isLift() {
            return lift;
        }

        public boolean isDuplex() {
            return duplex;
        }

        public boolean isAirConditioning() {
            return aircondition;
        }

        public boolean isFurnished() {
            return furnished;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public String getDefaultDailyPrice() {
            return defaultDailyPrice;
        }

        public String getDefaultWeeklyPrice() {
            return defaultWeeklyPrice;
        }

        public String getDefaultMonthlyPrice() {
            return defaultMonthlyPrice;
        }

        public String getDefaultyearlyPrice() {
            return defaultyearlyPrice;
        }

        public String getLength() {
            return length;
        }

        public String getWidth() {
            return width;
        }

        public String getWidthUnit() {
            return widthUnit;
        }

        public String getLengthUnit() {
            return lengthUnit;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public List<LikedUser> getLikeUserId() {
            return likeUserId;
        }


        public String getLikedStatus() {
            return likedStatus;
        }

        public String getRentTime() {
            return rentTime;
        }

        public String getTotalPriceRent() {
            return totalPriceRent;
        }

        public String getTotalPriceSale() {
            return totalPriceSale;
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

        public List<Videosfile> getVideosfile() {
            return videosfile;
        }

        public List<Imagesfile> getImagesfile() {
            return imagesfile;
        }

        public String getProfessionalid() {
            return professionalid;
        }

        public String getBusinessid() {
            return businessid;
        }

        public String getLongg() {
            return longg;
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

        public Userid getUserid() {
            return userData;
        }

        public String getType() {
            return type;
        }

        public String getId() {
            return Id;
        }
        public String getParkingOption() {
            return parkingOption;
        }

        public String getLiked() {
            return liked;
        }

    }

    public static class LikedUser implements Serializable {
        @Expose
        @SerializedName("userId")
        private String userId;
        @Expose
        @SerializedName("_id")
        private String Id;

        public String getUserId() {
            return userId;
        }

        public String getId() {
            return Id;
        }
    }

    public static class Videosfile implements Serializable{
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

    public static class Imagesfile implements Serializable {
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

    public static class Userid implements Serializable{


        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("userId")
        private String userId;

        public String getUserId() {
            return userId;
        }

        @Expose
        @SerializedName("profileImage")
        private String profileImage;

        public String getDescription() {
            return description;
        }

        public String getProfileImage() {
            return profileImage;
        }

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
        private List<Videosfile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<Imagesfile> imagesfile;
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
        @SerializedName("professionalProfile")

        private boolean professionalProfile;

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

        public boolean isProfessionalProfile() {
            return professionalProfile;
        }

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

        public List<Videosfile> getVideosfile() {
            return videosfile;
        }

        public List<Imagesfile> getImagesfile() {
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
