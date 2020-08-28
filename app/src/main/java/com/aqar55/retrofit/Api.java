package com.aqar55.retrofit;

import java.util.List;
import java.util.Map;

import com.aqar55.model.AdminDetailsModel;
import com.aqar55.model.AllDataList;
import com.aqar55.model.AllDataResponse;
import com.aqar55.model.ChatDelateModel;
import com.aqar55.model.CommonModel;
import com.aqar55.model.GetCategoryList;
import com.aqar55.model.GetPropertyDetail;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.LikeListResponse;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.model.MessageChat;
import com.aqar55.model.NotificationModel;
import com.aqar55.model.PostPropertyForSale;
import com.aqar55.model.ProfessionalBusinessModelResponse;
import com.aqar55.model.ProfessionalListResponse;
import com.aqar55.model.ProfessionalResponse;
import com.aqar55.model.PropertyUpdateModel;
import com.aqar55.model.ResponseReport;
import com.aqar55.model.RoomIdResponse;
import com.aqar55.model.SerachSaleOrRent;
import com.aqar55.model.SignOut;
import com.aqar55.model.Sign_Up;
import com.aqar55.model.StataticContent;
import com.aqar55.model.StaticContentByType;
import com.aqar55.model.SubCatResponse;
import com.aqar55.model.TotalPropertyOfUserModel;
import com.aqar55.model.UpdateProfileModel;
import com.aqar55.model.UserBlockModel;
import com.aqar55.model.UserChatListResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api {
    @FormUrlEncoded
    @POST("getStaticContentByType")
    Call<StaticContentByType> getStaticData(@Field("type") String type);




    @GET("getStaticContent")
    Call<StataticContent> getStaticContent();


    @FormUrlEncoded
    @POST("userSignUp")
    Call<Sign_Up> getSignUpData(@Field("deviceType") String device,
                                @Field("deviceToken") String token,
                                @Field("status") String status,
                                @Field("mobileNumber") String number,
                                @Field("countryCode") String countryCode,
                                @Field("fullName") String name,
                                @Field("category") String category,
                                @Field("subCategory") String subCategory,
                                @Field("gender") String gender,
                                @Field("birthDate") String birthyear,
                                @Field("email") String email,
                                @Field("country") String country,
                                @Field("currency") String currency,
                                @Field("measurement") String measurment,
                                @Field("appLanguage") String applanguage,
                                @Field("speakLanguage") String speakLanguage,
                                @Field("termsCondition") boolean termsCondition);

    @FormUrlEncoded
    @POST("userSignin")
    Call<Sign_Up> getLoginData(
            @Field("countryCode") String code,
            @Field("mobileNumber") String mobile,
            @Field("deviceType") String deviceType,
            @Field("deviceToken") String deviceToken
    );

    @FormUrlEncoded
    @POST("userSignout")
    Call<SignOut> getSignoutData(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getuserDetails")
    Call<GetUserDetails> getUserDetails(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("getBusinessOrProfessionalDetails")
    Call<GetUserDetails> getBusinessOrProfessionalDetails(@Field("businessUserId") String userId,
                                                          @Field("profbusinessId") String profbusinessId,
                                                          @Field("type") String type);




    @FormUrlEncoded
    @POST("getProfessionalProfile")
    Call<GetUserDetails> getBusinessOrProfessional(@Field("userId") String userId,
                                                   @Field("Type") String Type);



    @FormUrlEncoded
    @POST("updateParticularProfile")
    Call<UpdateProfileModel> updateBusinessOrProfessional(@Field("profOrBusId") String userId);


    @Multipart
    @POST("postProperty")
    Call<PostPropertyForSale> postDataForSale(
            @PartMap Map<String, RequestBody> partMapData,
            @Part List<MultipartBody.Part> imageFile,
            @Part MultipartBody.Part video);

    @Multipart
    @POST("updateProperty")
    Call<PostPropertyForSale> updateProperty(
            @PartMap Map<String, RequestBody> partMapData,
            @Part List<MultipartBody.Part> imageFile,
            @Part MultipartBody.Part video);


    @FormUrlEncoded
    @POST("updateParticularProperty")
    Call<PropertyUpdateModel> updatePropertyForDays(@Field("propertyId") String propertyId);





    @FormUrlEncoded
    @POST("propertylisting")
    Call<GetPropertyListing> getPropertyList(@Field("type") String type);

    @FormUrlEncoded
    @POST("propertylisting")
    Call<GetPropertyListing> getPropertyListWithUserId(@Field("type") String type,
                                                       @Field("userId") String suserId);

    @FormUrlEncoded
    @POST("propertylisting")
    Call<AllDataResponse> getAllData(@Field("type") String type);

    @FormUrlEncoded
    @POST("getPropertyDetails")
    Call<GetPropertyDetail> getPropertyDetail(@Field("propertyId") String propid,
                                              @Field("type") String type,
                                              @Field("userId") String userId);


    @FormUrlEncoded
    @POST("sortProperty")
    Call<GetPropertyListing> getSortedData(@Field("type") String type,
                                           @Field("userId") String userId,
                                           @Field("lowtohighPrice") boolean lowtohighPrice,
                                           @Field("hightolowPrice") boolean hightolowPrice,
                                           @Field("lowtohighSize") boolean lowtohighSize,
                                           @Field("hightolowSize") boolean hightolowSize);


    @FormUrlEncoded
    @POST("sortTotalPostedProperty")
    Call<GetPropertyListing> sortTotalPostedPropertyProfessional(@Field("type") String type,
                                                                 @Field("professionalUserId") String professionalUserId,
                                                                 @Field("lowtohighPrice") boolean lowtohighPrice,
                                                                 @Field("hightolowPrice") boolean hightolowPrice,
                                                                 @Field("lowtohighSize") boolean lowtohighSize,
                                                                 @Field("hightolowSize") boolean hightolowSize);


    @FormUrlEncoded
    @POST("sortTotalPostedProperty")
    Call<GetPropertyListing> sortTotalPostedPropertynormalUserId(@Field("type") String type,
                                                                 @Field("normalUserId") String normalUserId,
                                                                 @Field("lowtohighPrice") boolean lowtohighPrice,
                                                                 @Field("hightolowPrice") boolean hightolowPrice,
                                                                 @Field("lowtohighSize") boolean lowtohighSize,
                                                                 @Field("hightolowSize") boolean hightolowSize);


    @FormUrlEncoded
    @POST("sortProperty")
    Call<GetPropertyListing> getSortedData(@Field("type") String type,
                                           @Field("userId") String userId,
                                           @Field("sortByName") boolean sortByName,
                                           @Field("sortByCategory") boolean sortByCategory);

    @FormUrlEncoded
    @POST("sortProperty")
    Call<CommonModel> getProfSortedData(@Field("type") String propid,
                                        @Field("sortByName") boolean sortByName,
                                        @Field("sortByCategory") boolean sortByCategory);

    @FormUrlEncoded
    @POST("listDiffProperty")
    Call<ManageActiveProperty> getPostedProperty(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("myPropertyInactiveList")
    Call<ManageActiveProperty> getInactivePropertyList(@Field("userId") String userId);

    @GET("allProperty")
    Call<AllDataList> getAllData();

    @GET("propertyCategoryListing")
    Call<GetCategoryList> getPropertyCategory();

    @FormUrlEncoded
    @POST("propertySearchByKeywords")
    Call<GetPropertyListing> getSearchedProperty(@Field("searchText") String search,
                                                 @Field("userId") String userId);

    @FormUrlEncoded
    @POST("searchByGooglePlaceApi")
    Call<GetPropertyListing> getSearchByGoogle(@Field("type") String type,
                                               @Field("lat") String lat,
                                               @Field("long") String log,
                                               @Field("userId") String userId);


    @FormUrlEncoded
    @POST("searchByGooglePlaceApi")
    Call<GetPropertyListing> getSearchByGoogleWithoutLogin(@Field("type") String type,
                                                           @Field("lat") String lat,
                                                           @Field("long") String log);

    @FormUrlEncoded
    @POST("getpropertyCategory")
    Call<GetPropertyListing> getPropertyOnTheBasisOfCategory(@Field("type") String type,
                                                             @Field("category") String category,
                                                             @Field("userId") String userId);


    @FormUrlEncoded
    @POST("getpropertyCategory")
    Call<GetPropertyListing> getPropertyOnTheBasisOfCategoryWithoutUserId(@Field("type") String type,
                                                             @Field("category") String category);

    @FormUrlEncoded
    @POST("deleteProperty")
    Call<GetPropertyListing> deleteProperty(@Field("propertyId") String id);


    @GET("categoryList")
    Call<ProfessionalResponse> getCategoryList();

    @FormUrlEncoded
    @POST("subcategoryList")
    Call<SubCatResponse> getSubCatList(@Field("categoryId") String categoryId);


    @Multipart
    @POST("createBusinessOrProfessional")
    Call<ProfessionalListResponse> createBusinessOrProfessional(@PartMap Map<String, RequestBody> partMapData,
                                                                @Part List<MultipartBody.Part> image_collection,
                                                                @Part MultipartBody.Part imageProfile,
                                                                @Part MultipartBody.Part video,
                                                                @Part MultipartBody.Part imageIndentityFirst,
                                                                @Part MultipartBody.Part imageIndentitySecound);


    @Multipart
    @POST("updateBusinessOrProfessionalProfile")
    Call<ProfessionalListResponse> updateBusinessOrProfessional(@PartMap Map<String, RequestBody> partMapData,
                                                                @Part List<MultipartBody.Part> image_collection,
                                                                @Part MultipartBody.Part imageProfile,
                                                                @Part MultipartBody.Part video,
                                                                @Part MultipartBody.Part imageIndentityFirst,
                                                                @Part MultipartBody.Part imageIndentitySecound);

    @Multipart
    @POST("updateNormalProfile")
    Call<ProfessionalListResponse> updateProfile(@PartMap Map<String, RequestBody> part,
                                                 @Part MultipartBody.Part profileFile,
                                                 @Part MultipartBody.Part governmentIdFileFirst,
                                                 @Part MultipartBody.Part governmentIdFileSecond);

    @Multipart
    @POST("updateNormalProfile")
    Call<ProfessionalListResponse> updateProfile(@PartMap Map<String, RequestBody> part,
                                                 @Part MultipartBody.Part profileFile,
                                                 @Part MultipartBody.Part governmentIdFileFirst);

    @Multipart
    @POST("updateNormalProfile")
    Call<ProfessionalListResponse> updateProfile(@PartMap Map<String, RequestBody> part,
                                                 @Part MultipartBody.Part profileFile);


    @Multipart
    @POST("updateNormalProfile")
    Call<ProfessionalListResponse> updateProfile(@PartMap Map<String, RequestBody> part);


    @FormUrlEncoded
    @POST("deleteBusinessProfessionalProfile")
    Call<GetUserDetails> deleteProfessionalOrBusinessMap(@Field("userId") String userId,
                                                         @Field("_id") String id);

    @FormUrlEncoded
    @POST("totalPropertyOfUser")
    Call<TotalPropertyOfUserModel> getTotalPropertyOfUser(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("searchBusinessProfessional")
    Call<SerachSaleOrRent> getSearchBusinessProfessional(@Field("type") String type,
                                                         @Field("fullName") String fullName,
                                                         @Field("professionalId") String professionalId,
                                                         @Field("category") String category,
                                                         @Field("subCategory") String subCategory,
                                                         @Field("area") String area,
                                                         @Field("speakLanguage") String speakLanguage);

    @FormUrlEncoded
    @POST("searchBusinessProfessional")
    Call<GetPropertyListing> getSearchBusinessProfessionalList(@Field("type") String type,
                                                                  @Field("fullName") String fullName,
                                                                  @Field("professionalId") String professionalId,
                                                                  @Field("category") String category,
                                                                  @Field("subCategory") String subCategory,
                                                                  @Field("area") String area,
                                                                  @Field("speakLanguage") String speakLanguage);


    @FormUrlEncoded
    @POST("searchSaleOrRent")
    Call<SerachSaleOrRent> getSearchSaleOrRent(@Field("type") String type,
                                               @Field("propertyId") String propertyId,
                                               @Field("category") String category,
                                               @Field("purpose") String purpose,
                                               @Field("available") String available,
                                               @Field("bedrooms") String bedrooms,
                                               @Field("bathrooms") String bathrooms,
                                               @Field("kitchens") String kitchens,
                                               @Field("builtSizeMin") String builtSize,
                                               @Field("builtSizeMax") String builtSizeUnit,
                                               @Field("plotSize") String plotSize,
                                               @Field("plotSizeUnit") String plotSizeUnit,
                                               @Field("totalPriceMin") String totalPriceMin,
                                               @Field("yearBuilt") String yearBuilt,
                                               @Field("balcony") String balcony,
                                               @Field("garden") String garden,
                                               @Field("parking") String parking,
                                               @Field("modularKitchen") String modularKitchen,
                                               @Field("photos") String photos,
                                               @Field("videos") String videos,
                                               @Field("plotSizeMin") String plotSizeMin,
                                               @Field("plotSizeMax") String plotSizeMax,
                                               @Field("rentTime") String rentTime,
                                               @Field("store") String store,
                                               @Field("lift") String lift,
                                               @Field("duplex") String duplex,
                                               @Field("furnished") String furnished,
                                               @Field("aircondition") String aircondition);


    @FormUrlEncoded
    @POST("searchSaleOrRent")
    Call<GetPropertyListing> getSearchAll(@Field("type") String type,
                                          @Field("propertyId") String propertyId,
                                          @Field("category") String category,
                                          @Field("purpose") String purpose,
                                          @Field("available") String available,
                                          @Field("bedrooms") String bedrooms,
                                          @Field("bathrooms") String bathrooms,
                                          @Field("kitchens") String kitchens,
                                          @Field("builtSizeMin") String builtSize,
                                          @Field("builtSizeMax") String builtSizeUnit,
                                          @Field("plotSize") String plotSize,
                                          @Field("plotSizeUnit") String plotSizeUnit,
                                          @Field("totalPriceMin") String totalPriceMin,
                                          @Field("yearBuilt") String yearBuilt,
                                          @Field("balcony") String balcony,
                                          @Field("garden") String garden,
                                          @Field("parking") String parking,
                                          @Field("modularKitchen") String modularKitchen,
                                          @Field("photos") String photos,
                                          @Field("videos") String videos,
                                          @Field("plotSizeMin") String plotSizeMin,
                                          @Field("plotSizeMax") String plotSizeMax,
                                          @Field("rentTime") String rentTime,
                                          @Field("store") String store,
                                          @Field("lift") String lift,
                                          @Field("duplex") String duplex,
                                          @Field("furnished") String furnished,
                                          @Field("aircondition") String aircondition);


    @FormUrlEncoded
    @POST("likedPost")
    Call<LikeModelResponse> getLikePost(@Field("type") String type,
                                        @Field("userId") String userId,
                                        @Field("profbusinessId") String profbusinessId,
                                        @Field("liked") boolean liked);

    @FormUrlEncoded
    @POST("likedPost")
    Call<LikeModelResponse> getLikePostPorBusiness(@Field("type") String type,
                                                   @Field("propertyId") String propertyId,
                                                   @Field("userId") String userId,
                                                   @Field("liked") boolean liked);

    @FormUrlEncoded
    @POST("listLikedPost")
    Call<LikeListResponse> getLikePostPorBusinessList(@Field("type") String type,
                                                      @Field("userId") String userId);

    @FormUrlEncoded
    @POST("listLikedPost")
    Call<ProfessionalBusinessModelResponse> getProfessionalBusinessList(@Field("type") String type,
                                                                        @Field("userId") String userId);

    @FormUrlEncoded
    @POST("recentPost")
    Call<LikeListResponse> getRecentList(@Field("type") String type,
                                         @Field("userId") String userId);

    @FormUrlEncoded
    @POST("recentPost")
    Call<LikeListResponse> getRecentListWithoutId(@Field("type") String type);

    @FormUrlEncoded
    @POST("recentPost")
    Call<ProfessionalBusinessModelResponse> getProBusinessRecentList(@Field("type") String type,
                                                                     @Field("userId") String userId);


    @FormUrlEncoded
    @POST("recentPost")
    Call<ProfessionalBusinessModelResponse> getProBusinessRecentListWithId(@Field("type") String type);


    @FormUrlEncoded
    @POST("chatUserList")
    Call<UserChatListResponse> getChatUserList(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getRoomId")
    Call<RoomIdResponse> getRoomId(@Field("sender_id") String sender_id, @Field("receiver_id") String receiver_id);


    @FormUrlEncoded
    @POST("searchByGooglePlaceApi")
    Call<GetPropertyListing> getSearchByGooglePlace(@Field("lat") String lat,
                                                    @Field("long") String lng,
                                                    @Field("type") String type);

    @FormUrlEncoded
    @POST("professionalPropertyListing")
    Call<GetPropertyListing> getProfessionalPropertyListing(@Field("userId") String userId,
                                                            @Field("professionalId") String professionalId);

    @FormUrlEncoded
    @POST("professionalPropertyListing")
    Call<GetPropertyListing> getNormalListing(@Field("userId") String userId,
                                              @Field("normalId") String normalId);

    @FormUrlEncoded
    @POST("notificationSetting")
    Call<GetUserDetails> getNotificationSetting(@Field("userId") String userId,
                                                @Field("notification") boolean notification);


    @FormUrlEncoded
    @POST("chatDetails")
    Call<MessageChat> chatDetails(@Field("sender_id") String sender_id,
                                  @Field("room_id") String room_id);

    @GET("updatePropertyDays")
    Call<CommonModel> getUpdatePropertyDays();

    @FormUrlEncoded
    @POST("getAdminContactDetails")
    Call<AdminDetailsModel> getAdminDetails(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("contactAdmin")
    Call<ResponseReport> setReport(@Field("userId") String userId,
                                   @Field("reason") String reason,
                                   @Field("details") String details);

    @FormUrlEncoded
    @POST("chatDelete")
    Call<ChatDelateModel> chatDelete(@Field("room_id") String room_id);


    @FormUrlEncoded
    @POST("blockUser")
    Call<UserBlockModel> userBlock(@Field("block_to") String block_to,
                                   @Field("block_from") String block_from);


    @FormUrlEncoded
    @POST("notificationList")
    Call<NotificationModel> getNoticationList(@Field("userId") String room_id);


    @FormUrlEncoded
    @POST("viewedBusinessProfessional")
    Call<NotificationModel> viewedBusinessProfessional(@Field("profbusId") String profbusId);


    @FormUrlEncoded
    @POST("updateSetting")
    Call<GetUserDetails> upDateSettings(@Field("currency") String currency,
                                        @Field("userId") String userId,
                                        @Field("measurement") String measurement,
                                        @Field("appLanguage") String appLanguage,
                                        @Field("speakLanguage") String speakLanguage);

}
