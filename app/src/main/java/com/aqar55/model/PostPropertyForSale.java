package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPropertyForSale {
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

    public static class DataEntity {
        @SerializedName("__v")
        private int V;
        @SerializedName("created")
        private String created;
        @SerializedName("modified")
        private String modified;
        @SerializedName("videosFile")
        private List<String> videosfile;
        @SerializedName("imagesFile")
        private List<String> imagesfile;
        @SerializedName("professionalId")
        private String professionalid;
        @SerializedName("businessId")
        private String businessid;
        @SerializedName("apartmentNo")
        private String apartmentno;
        @SerializedName("address")
        private String address;
        @SerializedName("zipcode")
        private String zipcode;
        @SerializedName("area")
        private String area;
        @SerializedName("city")
        private String city;
        @SerializedName("state")
        private String state;
        @SerializedName("country")
        private String country;
        @SerializedName("long")
        private String longg;
        @SerializedName("lat")
        private String lat;
        @SerializedName("viewsOption4")
        private boolean viewsoption4;
        @SerializedName("viewsOption3")
        private boolean viewsoption3;
        @SerializedName("viewsOption2")
        private boolean viewsoption2;
        @SerializedName("viewsOption1")
        private boolean viewsoption1;
        @SerializedName("parkingOption4")
        private boolean parkingoption4;
        @SerializedName("parkingOption3")
        private boolean parkingoption3;
        @SerializedName("parkingOption2")
        private boolean parkingoption2;
        @SerializedName("parkingOption1")
        private boolean parkingoption1;
        @SerializedName("outdoorOption4")
        private boolean outdooroption4;
        @SerializedName("outdoorOption3")
        private boolean outdooroption3;
        @SerializedName("outdoorOption2")
        private boolean outdooroption2;
        @SerializedName("outdoorOption1")
        private boolean outdooroption1;
        @SerializedName("indoorOption4")
        private boolean indooroption4;
        @SerializedName("indoorOption3")
        private boolean indooroption3;
        @SerializedName("indoorOption2")
        private boolean indooroption2;
        @SerializedName("indoorOption1")
        private boolean indooroption1;
        @SerializedName("parking")
        private boolean parking;
        @SerializedName("garden")
        private boolean garden;
        @SerializedName("balcony")
        private boolean balcony;
        @SerializedName("description")
        private String description;
        @SerializedName("streetWidth")
        private String streetwidth;
        @SerializedName("streetView")
        private String streetview;
        @SerializedName("yearBuilt")
        private String yearbuilt;
        @SerializedName("plotSizeUnit")
        private String plotsizeunit;
        @SerializedName("plotSize")
        private String plotsize;
        @SerializedName("builtSizeUnit")
        private String builtsizeunit;
        @SerializedName("builtSize")
        private String builtsize;
        @SerializedName("totalPrice")
        private String totalprice;
        @SerializedName("pricePerMeter")
        private String pricepermeter;
        @SerializedName("sizem2")
        private String sizem2;
        @SerializedName("rentTimeYearly")
        private boolean renttimeyearly;
        @SerializedName("rentTimeMonthly")
        private boolean renttimemonthly;
        @SerializedName("rentTimeWeekly")
        private boolean renttimeweekly;
        @SerializedName("rentTimeDaily")
        private boolean renttimedaily;
        @SerializedName("floor")
        private String floor;
        @SerializedName("kitchens")
        private String kitchens;
        @SerializedName("bathrooms")
        private String bathrooms;
        @SerializedName("bedrooms")
        private String bedrooms;
        @SerializedName("availableBoth")
        private boolean availableboth;
        @SerializedName("availableSingle")
        private boolean availablesingle;
        @SerializedName("availableFamily")
        private boolean availablefamily;
        @SerializedName("purposeBoth")
        private boolean purposeboth;
        @SerializedName("purposeResidential")
        private boolean purposeresidential;
        @SerializedName("purposeCommercial")
        private boolean purposecommercial;
        @SerializedName("purposeNon")
        private String purposenon;
        @SerializedName("category")
        private String category;
        @SerializedName("title")
        private String title;
        @SerializedName("_id")
        private String Id;
        @SerializedName("liked")
        private boolean liked;

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

        public String getProfessionalid() {
            return professionalid;
        }

        public String getBusinessid() {
            return businessid;
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

        public String getLongg() {
            return longg;
        }

        public String getLat() {
            return lat;
        }

        public boolean getViewsoption4() {
            return viewsoption4;
        }

        public boolean getViewsoption3() {
            return viewsoption3;
        }

        public boolean getViewsoption2() {
            return viewsoption2;
        }

        public boolean getViewsoption1() {
            return viewsoption1;
        }

        public boolean getParkingoption4() {
            return parkingoption4;
        }

        public boolean getParkingoption3() {
            return parkingoption3;
        }

        public boolean getParkingoption2() {
            return parkingoption2;
        }

        public boolean getParkingoption1() {
            return parkingoption1;
        }

        public boolean getOutdooroption4() {
            return outdooroption4;
        }

        public boolean getOutdooroption3() {
            return outdooroption3;
        }

        public boolean getOutdooroption2() {
            return outdooroption2;
        }

        public boolean getOutdooroption1() {
            return outdooroption1;
        }

        public boolean getIndooroption4() {
            return indooroption4;
        }

        public boolean getIndooroption3() {
            return indooroption3;
        }

        public boolean getIndooroption2() {
            return indooroption2;
        }

        public boolean getIndooroption1() {
            return indooroption1;
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

        public String getDescription() {
            return description;
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

        public String getTotalprice() {
            return totalprice;
        }

        public String getPricepermeter() {
            return pricepermeter;
        }

        public String getSizem2() {
            return sizem2;
        }

        public boolean getRenttimeyearly() {
            return renttimeyearly;
        }

        public boolean getRenttimemonthly() {
            return renttimemonthly;
        }

        public boolean getRenttimeweekly() {
            return renttimeweekly;
        }

        public boolean getRenttimedaily() {
            return renttimedaily;
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

        public boolean getAvailableboth() {
            return availableboth;
        }

        public boolean getAvailablesingle() {
            return availablesingle;
        }

        public boolean getAvailablefamily() {
            return availablefamily;
        }

        public boolean getPurposeboth() {
            return purposeboth;
        }

        public boolean getPurposeresidential() {
            return purposeresidential;
        }

        public boolean getPurposecommercial() {
            return purposecommercial;
        }

        public String getPurposenon() {
            return purposenon;
        }

        public String getCategory() {
            return category;
        }

        public String getTitle() {
            return title;
        }

        public String getId() {
            return Id;
        }

        public boolean getLiked() {
            return liked;
        }
    }
}
