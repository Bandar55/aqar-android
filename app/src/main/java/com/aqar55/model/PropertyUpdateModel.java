package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class PropertyUpdateModel {


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
        @SerializedName("professionalUserId")
        private String professionaluserid;
        @Expose
        @SerializedName("propertyId")
        private String propertyid;

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
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("status")
        private String status;
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


        public String getProfessionaluserid() {
            return professionaluserid;
        }

        public String getPropertyid() {
            return propertyid;
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

        public String getUserid() {
            return userid;
        }

        public String getStatus() {
            return status;
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


    }





}
