package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfessionalPropertyListing {

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
        @SerializedName("likedStatus")
        private String likedstatus;
        @Expose
        @SerializedName("counter")
        private int counter;
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
        @SerializedName("likeUserId")
        private List<LikeUserId> likeuserid;
        @Expose
        @SerializedName("videosFile")
        private List<VideosFile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<ImagesFile> imagesfile;
        @Expose
        @SerializedName("long")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("currency")
        private String currency;
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
        @SerializedName("aircondition")
        private boolean aircondition;
        @Expose
        @SerializedName("furnished")
        private boolean furnished;
        @Expose
        @SerializedName("duplex")
        private boolean duplex;
        @Expose
        @SerializedName("lift")
        private boolean lift;
        @Expose
        @SerializedName("store")
        private boolean store;
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
        @SerializedName("defaultyearlyPrice")
        private String defaultyearlyprice;
        @Expose
        @SerializedName("defaultMonthlyPrice")
        private String defaultmonthlyprice;
        @Expose
        @SerializedName("defaultWeeklyPrice")
        private String defaultweeklyprice;
        @Expose
        @SerializedName("defaultDailyPrice")
        private String defaultdailyprice;
        @Expose
        @SerializedName("totalPrice")
        private String totalprice;
        @Expose
        @SerializedName("totalPriceRent")
        private String totalpricerent;
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
        @SerializedName("widthUnit")
        private String widthunit;
        @Expose
        @SerializedName("width")
        private String width;
        @Expose
        @SerializedName("lengthUnit")
        private String lengthunit;
        @Expose
        @SerializedName("length")
        private String length;
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
        @SerializedName("professionalUserId")
        private Professionaluserid professionaluserid;
        @Expose
        @SerializedName("userId")
        private String userid;
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
        @SerializedName("rentTime")
        private String renttime;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("location")
        private Location location;

        public String getLikedstatus() {
            return likedstatus;
        }

        public int getCounter() {
            return counter;
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

        public List<LikeUserId> getLikeuserid() {
            return likeuserid;
        }

        public List<VideosFile> getVideosfile() {
            return videosfile;
        }

        public List<ImagesFile> getImagesfile() {
            return imagesfile;
        }

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }

        public String getCurrency() {
            return currency;
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

        public boolean getAircondition() {
            return aircondition;
        }

        public boolean getFurnished() {
            return furnished;
        }

        public boolean getDuplex() {
            return duplex;
        }

        public boolean getLift() {
            return lift;
        }

        public boolean getStore() {
            return store;
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

        public String getDefaultyearlyprice() {
            return defaultyearlyprice;
        }

        public String getDefaultmonthlyprice() {
            return defaultmonthlyprice;
        }

        public String getDefaultweeklyprice() {
            return defaultweeklyprice;
        }

        public String getDefaultdailyprice() {
            return defaultdailyprice;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public String getTotalpricerent() {
            return totalpricerent;
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

        public String getWidthunit() {
            return widthunit;
        }

        public String getWidth() {
            return width;
        }

        public String getLengthunit() {
            return lengthunit;
        }

        public String getLength() {
            return length;
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

        public Professionaluserid getProfessionaluserid() {
            return professionaluserid;
        }

        public String getUserid() {
            return userid;
        }

        public String getType() {
            return type;
        }

        public String getId() {
            return Id;
        }

        public String getTotaldays() {
            return totaldays;
        }

        public String getRenttime() {
            return renttime;
        }

        public String getStatus() {
            return status;
        }

        public Location getLocation() {
            return location;
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

    public static class Videosfile {
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

    public static class Imagesfile {
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

    public static class Professionaluserid {
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
        @SerializedName("likeUserId")
        private List<LikeUserId> likeuserid;
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
        private String lng;
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

        public List<LikeUserId> getLikeuserid() {
            return likeuserid;
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

        public String getLng() {
            return lng;
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

    public static class Location {
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

    public static class LikeUserId{
        @Expose
        @SerializedName("_id")
        private String _id;
        @Expose
        @SerializedName("userId")
        private String userId;

        public String get_id() {
            return _id;
        }

        public String getUserId() {
            return userId;
        }
    }
    public static class VideosFile{
        @Expose
        @SerializedName("_id")
        private String _id;
        @Expose
        @SerializedName("image")
        private String image;

        public String get_id() {
            return _id;
        }

        public String getImage() {
            return image;
        }
    }

    public static class ImagesFile{
        @Expose
        @SerializedName("_id")
        private String _id;
        @Expose
        @SerializedName("video")
        private String video;

        public String get_id() {
            return _id;
        }

        public String getVideo() {
            return video;
        }
    }
}
