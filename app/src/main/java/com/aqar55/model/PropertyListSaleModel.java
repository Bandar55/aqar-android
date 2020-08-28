package com.aqar55.model;

import java.io.Serializable;

public class PropertyListSaleModel implements Serializable{

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



   /* @Expose
    @SerializedName("Data")
    private List<PropertyListSaleModel.Data> data;
    @Expose
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    @SerializedName("response_code")
    private int responseCode;

    public List<PropertyListSaleModel.Data> getData() {
        return data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public static class Data implements Serializable {
        boolean b;

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        @Expose
        @SerializedName("mobileNumber")
        private String mobileNumber;
        @Expose
        @SerializedName("professionalUserId")
        private String professionalUserId;

        public String getProfessionalUserId() {
            return professionalUserId;
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
        private List<PropertyListSaleModel.Videosfile> videosfile;
        @Expose
        @SerializedName("imagesFile")
        private List<PropertyListSaleModel.Imagesfile> imagesfile;
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
        @SerializedName("userId")
        private String userid;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("_id")
        private String Id;
        @Expose
        @SerializedName("liked")
        private boolean liked;
        @Expose
        @SerializedName("subCategory")
        private String subCategory;
        @Expose
        @SerializedName("fullName")
        private String fullName;
        @Expose
        @SerializedName("profileImage")
        private String profileImage;

        @Expose
        @SerializedName("likedStatus")
        private String likedStatus;
        @Expose
        @SerializedName("totalPrice")
        private String totalPrice;

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getLikedStatus() {
            return likedStatus;
        }




        public void setProfessionalUserId(String professionalUserId) {
            this.professionalUserId = professionalUserId;

        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
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

        public List<PropertyListSaleModel.Videosfile> getVideosfile() {
            return videosfile;
        }

        public List<PropertyListSaleModel.Imagesfile> getImagesfile() {
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

        public String getPricepermeter() {
            return pricepermeter;
        }

        public String getSizem2() {
            return sizem2;
        }

        public String getRentTime() {
            return rentTime;
        }

        public String getTotalPriceRent() {
            return totalPriceRent;
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

        public String getUserid() {
            return userid;
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


        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public void setV(int v) {
            V = v;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setVideosfile(List<PropertyListSaleModel.Videosfile> videosfile) {
            this.videosfile = videosfile;
        }

        public void setImagesfile(List<PropertyListSaleModel.Imagesfile> imagesfile) {
            this.imagesfile = imagesfile;
        }

        public void setProfessionalid(String professionalid) {
            this.professionalid = professionalid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public void setLongg(String longg) {
            this.longg = longg;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setBuildingno(String buildingno) {
            this.buildingno = buildingno;
        }

        public void setApartmentno(String apartmentno) {
            this.apartmentno = apartmentno;
        }

        public void setRentTime(String rentTime) {
            this.rentTime = rentTime;
        }

        public void setTotalPriceRent(String totalPriceRent) {
            this.totalPriceRent = totalPriceRent;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public void setParkingoption(String parkingoption) {
            this.parkingoption = parkingoption;
        }

        public void setFurnish(String furnish) {
            this.furnish = furnish;
        }

        public void setOutdoor(String outdoor) {
            this.outdoor = outdoor;
        }

        public void setIndoor(String indoor) {
            this.indoor = indoor;
        }

        public void setModularkitchen(boolean modularkitchen) {
            this.modularkitchen = modularkitchen;
        }

        public void setParking(boolean parking) {
            this.parking = parking;
        }

        public void setGarden(boolean garden) {
            this.garden = garden;
        }

        public void setBalcony(boolean balcony) {
            this.balcony = balcony;
        }

        public void setPricepermeter(String pricepermeter) {
            this.pricepermeter = pricepermeter;
        }

        public void setSizem2(String sizem2) {
            this.sizem2 = sizem2;
        }

        public void setTotalpricesale(String totalpricesale) {
            this.totalpricesale = totalpricesale;
        }

        public void setRevenue(String revenue) {
            this.revenue = revenue;
        }

        public void setExtrashowroomno(String extrashowroomno) {
            this.extrashowroomno = extrashowroomno;
        }

        public void setExtrabuildingno(String extrabuildingno) {
            this.extrabuildingno = extrabuildingno;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setStreetwidthunit(String streetwidthunit) {
            this.streetwidthunit = streetwidthunit;
        }

        public void setStreetwidth(String streetwidth) {
            this.streetwidth = streetwidth;
        }

        public void setStreetview(String streetview) {
            this.streetview = streetview;
        }

        public void setYearbuilt(String yearbuilt) {
            this.yearbuilt = yearbuilt;
        }

        public void setPlotsizeunit(String plotsizeunit) {
            this.plotsizeunit = plotsizeunit;
        }

        public void setPlotsize(String plotsize) {
            this.plotsize = plotsize;
        }

        public void setBuiltsizeunit(String builtsizeunit) {
            this.builtsizeunit = builtsizeunit;
        }

        public void setBuiltsize(String builtsize) {
            this.builtsize = builtsize;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public void setKitchens(String kitchens) {
            this.kitchens = kitchens;
        }

        public void setBathrooms(String bathrooms) {
            this.bathrooms = bathrooms;
        }

        public void setBedrooms(String bedrooms) {
            this.bedrooms = bedrooms;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setId(String id) {
            Id = id;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public void setLikedStatus(String likedStatus) {
            this.likedStatus = likedStatus;
        }
    }

    public static class Videosfile  implements Serializable{
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

    public static class Imagesfile implements  Serializable{
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
*/
}
