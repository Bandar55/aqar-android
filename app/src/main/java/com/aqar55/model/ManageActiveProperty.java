package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ManageActiveProperty implements Serializable {
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
        @SerializedName("width")
        private String width;

        @Expose
        @SerializedName("defaultDailyPrice")
        private String defaultDailyPrice;

        @Expose
        @SerializedName("defaultMonthlyPrice")
        private String defaultMonthlyPrice;

        @Expose
        @SerializedName("defaultWeeklyPrice")
        private String defaultWeeklyPrice;

        @Expose
        @SerializedName("defaultyearlyPrice")
        private String defaultyearlyPrice;

        public String getDefaultDailyPrice() {
            return defaultDailyPrice;
        }

        public String getDefaultMonthlyPrice() {
            return defaultMonthlyPrice;
        }

        public String getDefaultWeeklyPrice() {
            return defaultWeeklyPrice;
        }

        public String getDefaultyearlyPrice() {
            return defaultyearlyPrice;
        }

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
        @SerializedName("furnished")
        private boolean furnished;

        @Expose
        @SerializedName("aircondition")
        private boolean aircondition;

        public boolean isStore() {
            return store;
        }

        public boolean isLift() {
            return lift;
        }

        public boolean isDuplex() {
            return duplex;
        }

        public boolean isFurnished() {
            return furnished;
        }

        public boolean isAircondition() {
            return aircondition;
        }

        @Expose
        @SerializedName("length")
        private String length;

        public String getWidth() {
            return width;
        }

        public String getLength() {
            return length;
        }

        @Expose
        @SerializedName("widthUnit")
        private String widthUnit;

        @Expose
        @SerializedName("lengthUnit")
        private String lengthUnit;

        public String getWidthUnit() {
            return widthUnit;
        }

        public String getLengthUnit() {
            return lengthUnit;
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
        @SerializedName("long")
        private String longg;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("pricePerMeter")
        private String pricepermeter;
        @Expose
        @SerializedName("sizem2")
        private String sizem2;
        @Expose
        @SerializedName("totalPriceSale")
        private String totalpricesale;
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
        @SerializedName("currency")
        private String currency;

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
        @SerializedName("totalPriceRent")
        private String totalpricerent;
        @Expose
        @SerializedName("rentTime")
        private String renttime;
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
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("timestamp")
        private String timestamp;
        @Expose
        @SerializedName("liked")
        private boolean liked;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("remainingDays")
        private String remainingDays;
        @Expose
        @SerializedName("totalDays")
        private String totalDays;

        @Expose
        @SerializedName("professionalUserId")
        private String professionalUserId;

        @Expose
        @SerializedName("totalPrice")
        private  String totalPrice;


        public String getCurrency() {
            return currency;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public String getProfessionalUserId() {
            return professionalUserId;
        }

        public String getRemainingDays() {
            return remainingDays;
        }

        public String getTotalDays() {
            return totalDays;
        }

        public String getPricepermeter() {
            return pricepermeter;
        }

        public String getSizem2() {
            return sizem2;
        }

        public String getTotalpricesale() {
            return totalpricesale;
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

        public String getTotalpricerent() {
            return totalpricerent;
        }

        public String getRenttime() {
            return renttime;
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

        public String getUserid() {
            return userid;
        }

        public String getType() {
            return type;
        }

        public String getId() {
            return Id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public boolean getLiked() {
            return liked;
        }

        public String getStatus() {
            return status;
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

    public static class Imagesfile implements Serializable{
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
}
