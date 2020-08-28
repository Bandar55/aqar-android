package com.aqar55.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SortData {

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
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("viewsOption4")
        private boolean viewsoption4;
        @Expose
        @SerializedName("viewsOption3")
        private boolean viewsoption3;
        @Expose
        @SerializedName("viewsOption2")
        private boolean viewsoption2;
        @Expose
        @SerializedName("viewsOption1")
        private boolean viewsoption1;
        @Expose
        @SerializedName("parkingOption4")
        private boolean parkingoption4;
        @Expose
        @SerializedName("parkingOption3")
        private boolean parkingoption3;
        @Expose
        @SerializedName("parkingOption2")
        private boolean parkingoption2;
        @Expose
        @SerializedName("parkingOption1")
        private boolean parkingoption1;
        @Expose
        @SerializedName("outdoorOption4")
        private boolean outdooroption4;
        @Expose
        @SerializedName("outdoorOption3")
        private boolean outdooroption3;
        @Expose
        @SerializedName("outdoorOption2")
        private boolean outdooroption2;
        @Expose
        @SerializedName("outdoorOption1")
        private boolean outdooroption1;
        @Expose
        @SerializedName("indoorOption4")
        private boolean indooroption4;
        @Expose
        @SerializedName("indoorOption3")
        private boolean indooroption3;
        @Expose
        @SerializedName("indoorOption2")
        private boolean indooroption2;
        @Expose
        @SerializedName("indoorOption1")
        private boolean indooroption1;
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
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("sizem2")
        private String sizem2;
        @Expose
        @SerializedName("rentTimeYearly")
        private boolean renttimeyearly;
        @Expose
        @SerializedName("rentTimeMonthly")
        private boolean renttimemonthly;
        @Expose
        @SerializedName("rentTimeWeekly")
        private boolean renttimeweekly;
        @Expose
        @SerializedName("rentTimeDaily")
        private boolean renttimedaily;
        @Expose
        @SerializedName("floor")
        private String floor;
        @Expose
        @SerializedName("bathrooms")
        private String bathrooms;
        @Expose
        @SerializedName("bedrooms")
        private String bedrooms;
        @Expose
        @SerializedName("availableBoth")
        private boolean availableboth;
        @Expose
        @SerializedName("availableSingle")
        private boolean availablesingle;
        @Expose
        @SerializedName("availableFamily")
        private boolean availablefamily;
        @Expose
        @SerializedName("purposeBoth")
        private boolean purposeboth;
        @Expose
        @SerializedName("purposeResidential")
        private boolean purposeresidential;
        @Expose
        @SerializedName("purposeCommercial")
        private boolean purposecommercial;
        @Expose
        @SerializedName("purposeNon")
        private String purposenon;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
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

        public String getCountry() {
            return country;
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

        public String getType() {
            return type;
        }

        public String getId() {
            return Id;
        }

        public boolean getLiked() {
            return liked;
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
}
