package com.aqar55.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.aqar55.activitys.ActivityViewProfessionalBusinessProfile;
import com.aqar55.activitys.ActivityViewProfileThrowProperty;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.activitys.ProfessionalPropertyListingActivity;
import com.aqar55.adapters.product_detail_slider_adapter;
import com.aqar55.databinding.LayoutRentPropertyDetailBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyDetail;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import com.longtailvideo.jwplayer.events.BufferChangeEvent;
import com.longtailvideo.jwplayer.events.CompleteEvent;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.LevelsChangedEvent;
import com.longtailvideo.jwplayer.events.SeekedEvent;
import com.longtailvideo.jwplayer.events.TimeEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Property_Detail_Fragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, VideoPlayerEvents.OnFullscreenListener, VideoPlayerEvents.OnCompleteListener,VideoPlayerEvents.OnSeekedListener,VideoPlayerEvents.OnBufferChangeListener ,VideoPlayerEvents.OnLevelsChangedListener ,VideoPlayerEvents.OnTimeListener {
    private static final String TAG = "Property_Detail_Fragmen";
    private Response<GetPropertyDetail> responseData;
    private View view;
    private LayoutRentPropertyDetailBinding layoutPropertyDetailBinding;
    private GoogleMap mMap;
    private int position;
    private String propid;
    private String type;
    private boolean isLikedProfNorm;

    private String userId;

    private boolean isLiked;
    SupportMapFragment suppMapFragment;
    private Context context;

//    ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutPropertyDetailBinding = DataBindingUtil.inflate(inflater, R.layout.layout_rent_property_detail, container, false);
        view = layoutPropertyDetailBinding.getRoot();
        context = getContext();

        layoutPropertyDetailBinding.indicator.setupWithViewPager(layoutPropertyDetailBinding.viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Property_Detail_Fragment.SliderTimer(), 2000, 3000);

        layoutPropertyDetailBinding.reportImageviewPropertyDetail.setOnClickListener(this);
        layoutPropertyDetailBinding.ivBack.setOnClickListener(this);
        layoutPropertyDetailBinding.layout13ProductDetail.setOnClickListener(this);
        layoutPropertyDetailBinding.tvViewProfile.setOnClickListener(this);
        layoutPropertyDetailBinding.tvPropertyListed.setOnClickListener(this);
        layoutPropertyDetailBinding.ivLikeUser.setOnClickListener(this);


        Bundle bundle = getArguments();
        assert bundle != null;
        propid = bundle.getString("propid");
        type = bundle.getString("type");

        userId = bundle.getString("user_id");
        Log.d(TAG, "onCreateView: " + propid);
        layoutPropertyDetailBinding.ivLikeProperty.setOnClickListener(this);
        layoutPropertyDetailBinding.ivLikeUser.setOnClickListener(this);
        layoutPropertyDetailBinding.ivChatUsr.setOnClickListener(this);
        layoutPropertyDetailBinding.ivCallingUrs.setOnClickListener(this);
        layoutPropertyDetailBinding.ivShareUrs.setOnClickListener(this);
        layoutPropertyDetailBinding.llReqMoreInfo.setOnClickListener(this);

        layoutPropertyDetailBinding.ivShare.setOnClickListener(this);
        layoutPropertyDetailBinding.ivCalling.setOnClickListener(this);
        layoutPropertyDetailBinding.ivChatting.setOnClickListener(this);


        if (type.equalsIgnoreCase("sale")) {
            layoutPropertyDetailBinding.layoutSalePrice.setVisibility(View.GONE);
        } else {
            layoutPropertyDetailBinding.layoutSalePrice.setVisibility(View.VISIBLE);

        }
        getDataFromApi();




        return view;
    }

    private void playVideo(List<GetPropertyDetail.Videosfile> videosfile) {

        if (videosfile.size() > 0 && videosfile != null) {
            Uri uri = Uri.parse(videosfile.get(0).getVideo()); //Declare your url here.

            PlaylistItem video = new PlaylistItem(videosfile.get(0).getVideo());
           /* layoutPropertyDetailBinding.videoview.addOnFullscreenListener(this);
            layoutPropertyDetailBinding.videoview.addOnCompleteListener(this);
            layoutPropertyDetailBinding.videoview.addOnBufferChangeListener(this);
            layoutPropertyDetailBinding.videoview.addOnLevelsChangedListener(this);
            layoutPropertyDetailBinding.videoview.addOnTimeListener(this);
            layoutPropertyDetailBinding.videoview.load(video);*/
          layoutPropertyDetailBinding.videoview.setMediaController(new MediaController(getActivity()));
            layoutPropertyDetailBinding.videoview.setVideoURI(uri);
             layoutPropertyDetailBinding.videoview.start();
            MediaController mediacontroller = new MediaController(context);
            // Get the URL from String VideoURL
            layoutPropertyDetailBinding.videoview.setMediaController(mediacontroller);
            layoutPropertyDetailBinding.videoview.setVideoURI(uri);
            layoutPropertyDetailBinding.scrollview.scrollTo(0, 0);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM;
            mediacontroller.setLayoutParams(lp);
            ((ViewGroup) mediacontroller.getParent()).removeView(mediacontroller);
            (layoutPropertyDetailBinding.imageVideoView).addView(mediacontroller);

        }
    }




    private void getDataFromApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Log.d(TAG, "getDataFromApi: " + propid);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyDetail> getPropertyDetailCall;
        if (ModelManager.modelManager().getCurrentUser() != null)
            getPropertyDetailCall = api.getPropertyDetail(propid, type, ModelManager.modelManager().getCurrentUser().getId());
        else
            getPropertyDetailCall = api.getPropertyDetail(propid, type, "");

        getPropertyDetailCall.enqueue(new Callback<GetPropertyDetail>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyDetail> call, @NonNull Response<GetPropertyDetail> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                layoutPropertyDetailBinding.scrollview.scrollTo(0, 0);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        responseData = response;
                        playVideo(response.body().getData().getVideosfile());
                        setDataToViews(response);
                        initMap();
                        layoutPropertyDetailBinding.viewPager.setAdapter(new product_detail_slider_adapter(getContext(), response.body().getData().getImagesfile()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyDetail> call, @NonNull Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
                Log.d(TAG, "onFailure: " + t.getMessage());
                layoutPropertyDetailBinding.scrollview.scrollTo(0, 0);


            }
        });
    }

    private void setDataToViews(Response<GetPropertyDetail> response) {

        if (response.body() != null && response.body().getData() != null) {

            if (ModelManager.modelManager().getCurrentUser()!=null){
                if(ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(response.body().getData().getUserId())){
                    layoutPropertyDetailBinding.llReqMoreInfo.setVisibility(View.GONE);
                    layoutPropertyDetailBinding.reportImageviewPropertyDetail.setVisibility(View.GONE);
                }else {
                    layoutPropertyDetailBinding.llReqMoreInfo.setVisibility(View.VISIBLE);
                    layoutPropertyDetailBinding.reportImageviewPropertyDetail.setVisibility(View.VISIBLE);


                }
            }



            layoutPropertyDetailBinding.tvViewTimeDetails.setText("Viewed " + response.body().getData().getCounter() + " times");
            layoutPropertyDetailBinding.tvPropertyListed.setText(response.body().getData().getTotalPropertyCreated() + " " + context.getString(R.string.properties_listed));

            setExtraData(response.body().getData());

            if (response.body().getData().getType().equalsIgnoreCase("rent")) {
                layoutPropertyDetailBinding.layoutSalePrice.setVisibility(View.VISIBLE);

            } else {
                layoutPropertyDetailBinding.layoutSalePrice.setVisibility(View.GONE);

            }
            layoutPropertyDetailBinding.propertyfor.setText("Property For " + response.body().getData().getType());
            layoutPropertyDetailBinding.propid.setText(response.body().getData().getId());

            if (response.body().getData().getPurpose().equalsIgnoreCase(getString(R.string.both)))
                layoutPropertyDetailBinding.propversion.setText("Property Purpose : " + getString(R.string.commercial) + " and " + getString(R.string.residential));
            else
                layoutPropertyDetailBinding.propversion.setText("Property Purpose : " + response.body().getData().getPurpose());

            if (response.body().getData().getAvailable().equalsIgnoreCase(getString(R.string.both)))
                layoutPropertyDetailBinding.availablefor.setText("Available For : " + getString(R.string.single) + " and " + getString(R.string.family));
            else
                layoutPropertyDetailBinding.availablefor.setText("Available For : " + response.body().getData().getAvailable());


            layoutPropertyDetailBinding.measurment.setText(response.body().getData().getLength() + "X" + response.body().getData().getWidth());
            if (response.body().getData().getImagesfile().size() > 0) {
                layoutPropertyDetailBinding.text.setText("1 of " + response.body().getData().getImagesfile().size());
            } else {
                layoutPropertyDetailBinding.text.setText("");
            }
            layoutPropertyDetailBinding.propertytitle.setText(response.body().getData().getTitle());
            layoutPropertyDetailBinding.propcategory.setText("Category : " + response.body().getData().getCategory());
            layoutPropertyDetailBinding.dailayprice.setText("Price : " + response.body().getData().getUserid().getCurrency() + " " + response.body().getData().getTotalPrice() + " " + response.body().getData().getRentTime());

            if (response.body().getData().getUserid().getCurrency() != null && response.body().getData().getPricepermeter() != null)
                layoutPropertyDetailBinding.pricepermeter.setText("Price per Meter : " + response.body().getData().getUserid().getCurrency() + " " + response.body().getData().getPricepermeter());
            else
                layoutPropertyDetailBinding.pricepermeter.setText("Price per Meter : Not Available for Rent");

            if (response.body().getData().getType().equalsIgnoreCase("sale"))
                layoutPropertyDetailBinding.priceplotsize.setText("Price per Meter X Size M2 : " + response.body().getData().getUserid().getCurrency() + " " + response.body().getData().getPricepermeter() + " X " + response.body().getData().getSizem2() + " " + "M2");
            else
                layoutPropertyDetailBinding.priceplotsize.setVisibility(View.GONE);
            layoutPropertyDetailBinding.plotsize.setText(response.body().getData().getPlotsize() + " " + response.body().getData().getPlotsizeunit());

            if (response.body().getData().getRentTime() != null) {

                layoutPropertyDetailBinding.dailyprice.setText(response.body().getData().getDefaultDailyPrice());
                layoutPropertyDetailBinding.weeklyprice.setText(response.body().getData().getDefaultWeeklyPrice());

                layoutPropertyDetailBinding.monthlyprice.setText(response.body().getData().getDefaultMonthlyPrice());

                layoutPropertyDetailBinding.yearlyprice.setText(response.body().getData().getDefaultyearlyPrice());

            }


            layoutPropertyDetailBinding.plotsize2.setText(response.body().getData().getPlotsize() + " " + response.body().getData().getPlotsizeunit());
            layoutPropertyDetailBinding.measurment.setText(response.body().getData().getLength() + response.body().getData().getLengthUnit() + "X" + response.body().getData().getWidth() + response.body().getData().getWidthUnit());
            layoutPropertyDetailBinding.builtinsize.setText(response.body().getData().getBuiltsize() + " " + response.body().getData().getBuiltsizeunit());
            layoutPropertyDetailBinding.yearbuilt.setText(response.body().getData().getYearbuilt());
            layoutPropertyDetailBinding.streetview.setText(response.body().getData().getStreetview());
            layoutPropertyDetailBinding.width.setText(response.body().getData().getStreetwidth() + " " + response.body().getData().getStreetwidthunit());
            layoutPropertyDetailBinding.bedrooms.setText(response.body().getData().getBedrooms());
            layoutPropertyDetailBinding.bathrooms.setText(response.body().getData().getBathrooms());
            layoutPropertyDetailBinding.kitchens.setText(response.body().getData().getKitchens());
            layoutPropertyDetailBinding.livingrooms.setText(response.body().getData().getFloor());
            layoutPropertyDetailBinding.tvNoOfBuilding.setText("Apartment : " + response.body().getData().getExtrabuildingno());
            layoutPropertyDetailBinding.tvShowroom.setText("Showroom : " + response.body().getData().getExtrashowroomno());
            layoutPropertyDetailBinding.revenue.setText("Revenue : " + response.body().getData().getRevenue());
            layoutPropertyDetailBinding.description.setText(response.body().getData().getDescription());

            setSpecificData(response);
            layoutPropertyDetailBinding.address.setText(response.body().getData().getAddress());
            layoutPropertyDetailBinding.postedon.setText(response.body().getData().getCreated());
            layoutPropertyDetailBinding.lastupdate.setText(response.body().getData().getModified());
            if ((response.body().getData().getUserid() != null)) {

                if (response.body().getData().getUserid().getProfileImage() != null && !response.body().getData().getUserid().getProfileImage().isEmpty()) {
                    Glide.with(((getContext()))).load(response.body().getData().getUserid().getProfileImage())
                            .placeholder(new ColorDrawable(Color.GRAY)).into(layoutPropertyDetailBinding.userpic);
                }

                layoutPropertyDetailBinding.username.setText(response.body().getData().getUserid().getFullname());
                layoutPropertyDetailBinding.categoryP.setText("Cat.-" + response.body().getData().getUserid().getCategory());
                layoutPropertyDetailBinding.subcategoryP.setText("Sub Cat.-" + response.body().getData().getUserid().getSubcategory());
                layoutPropertyDetailBinding.pId.setText("Id-" + response.body().getData().getUserid().getProfessionalid());
                if (response.body().getData().getUserid().getDescription() != null && !response.body().getData().getUserid().getDescription().isEmpty())
                    layoutPropertyDetailBinding.pdetail.setText(response.body().getData().getUserid().getDescription());
                else
                    layoutPropertyDetailBinding.pdetail.setText(getString(R.string.details_not_found));

                layoutPropertyDetailBinding.mobile.setText(response.body().getData().getUserid().getCountrycode() + " " + response.body().getData().getUserid().getMobilenumber());
                //layoutPropertyDetailBinding.mobile2.setText(response.body().getData().getUserid().getMobilenumber());

                if (response.body().getData().getLikedStatus() != null) {
                    if (response.body().getData().getLikedStatus().equalsIgnoreCase("yes")) {
                        layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                        isLiked = false;
                    } else {
                        layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                        isLiked = true;
                    }
//                    if (response.body().getData().getLiked() != null) {
//                        if (response.body().getData().getLiked().equalsIgnoreCase("yes")) {
//                            layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
//                            isLiked = false;
//                        } else {
//                            layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
//                            isLiked = true;
//
//                        }
//                    }
//
                }
                if (response.body().getData().getUserid().getLikedStatus() != null) {
                    if (response.body().getData().getUserid().getLikedStatus().equalsIgnoreCase("yes")) {
                        layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.like_icon_new);
                        isLikedProfNorm = false;
                    } else {
                        layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                        isLikedProfNorm = true;
                    }
                }
                getDateAndTime();
            }

        }
    }

    private void setExtraData(GetPropertyDetail.Data data) {
        if (data.getBalcony())
            layoutPropertyDetailBinding.tvBalcony.setText("Balcony : Installed");
        else
            layoutPropertyDetailBinding.tvBalcony.setText("Balcony : Uninstalled");

        if (data.getParking())
            layoutPropertyDetailBinding.tvParking.setText("Parking : Installed");
        else
            layoutPropertyDetailBinding.tvParking.setText("Parking : Uninstalled");

        if (data.getGarden())
            layoutPropertyDetailBinding.tvGarden.setText("Garden : Installed");
        else
            layoutPropertyDetailBinding.tvGarden.setText("Garden : Uninstalled");

        if (data.getModularkitchen())
            layoutPropertyDetailBinding.tvModulerkitchen.setText("Modular Kitchen : Installed");
        else
            layoutPropertyDetailBinding.tvModulerkitchen.setText("Modular Kitchen : Uninstalled");

        if (data.isStore())
            layoutPropertyDetailBinding.tvStore.setText("Store : Installed");
        else
            layoutPropertyDetailBinding.tvStore.setText("Store : Uninstalled");

        if (data.isLift())
            layoutPropertyDetailBinding.tvLift.setText("Lift : Installed");
        else
            layoutPropertyDetailBinding.tvLift.setText("Lift : Uninstalled");

        if (data.isDuplex())
            layoutPropertyDetailBinding.tvDuplex.setText("Duplex : Installed");
        else
            layoutPropertyDetailBinding.tvDuplex.setText("Duplex : Uninstalled");

        if (data.isAirConditioning())
            layoutPropertyDetailBinding.tvAirConditioning.setText("Air-Conditioning : Installed");
        else
            layoutPropertyDetailBinding.tvAirConditioning.setText("Air-Conditioning : Uninstalled");

        if (data.isFurnished())
            layoutPropertyDetailBinding.tvFurnished.setText("Furnished : Installed");
        else
            layoutPropertyDetailBinding.tvFurnished.setText("Furnished : Uninstalled");


    }

    private void setSpecificData(Response<GetPropertyDetail> response) {
        assert response.body() != null;
        List<String> elephantListindoor = Arrays.asList(response.body().getData().getIndoor().split(","));
        List<String> elephantListoutdoor = Arrays.asList(response.body().getData().getOutdoor().split(","));
        List<String> elephantListparking = Arrays.asList(response.body().getData().getParkingOption().split(","));
        List<String> elephantListview = null;
        if (response.body().getData().getViews() != null) {
            elephantListview = Arrays.asList(response.body().getData().getViews().split(","));
        }
        List<String> elephantListFurnishing = Arrays.asList(response.body().getData().getFurnish().split(","));
        layoutPropertyDetailBinding.specification.setVisibility(View.VISIBLE);

        if (elephantListindoor != null) {
            for (int i = 0; i < elephantListindoor.size(); i++) {
                layoutPropertyDetailBinding.indoortext.setVisibility(View.VISIBLE);
                if (i == 0) {
                    if (!elephantListindoor.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.indooroption1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.indooroption1.setText(elephantListindoor.get(0));
                    }
                }
                if (i == 1) {
                    if (!elephantListindoor.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.indooroption2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.indooroption2.setText(elephantListindoor.get(1));
                    }
                }
                if (i == 2) {
                    if (!elephantListindoor.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.indooroption3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.indooroption3.setText(elephantListindoor.get(2));
                    }
                }
                if (i == 3) {
                    if (!elephantListindoor.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.indooroption4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.indooroption4.setText(elephantListindoor.get(3));
                    }
                }
            }
            if (elephantListoutdoor != null) {
                layoutPropertyDetailBinding.outdoortext.setVisibility(View.VISIBLE);
                for (int i = 0; i < elephantListoutdoor.size(); i++) {
                    if (i == 0) {
                        if (!elephantListoutdoor.get(0).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.outdooroption1.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.outdooroption1.setText(elephantListoutdoor.get(0));
                        }
                    }
                    if (i == 1) {
                        if (!elephantListoutdoor.get(1).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.outdooroption2.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.outdooroption2.setText(elephantListoutdoor.get(1));
                        }

                    }
                    if (i == 2) {
                        if (!elephantListoutdoor.get(2).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.outdooroption3.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.outdooroption3.setText(elephantListoutdoor.get(2));
                        }
                    }
                    if (i == 3) {
                        if (!elephantListoutdoor.get(3).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.outdooroption4.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.outdooroption4.setText(elephantListoutdoor.get(3));
                        }
                    }
                }
            }
            if (elephantListparking != null) {

                layoutPropertyDetailBinding.parkingtext.setVisibility(View.VISIBLE);
                for (int i = 0; i < elephantListparking.size(); i++) {
                    if (i == 0) {
                        if (!elephantListparking.get(0).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.parking1.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.parking1.setText(elephantListparking.get(0));
                        }
                    }
                    if (i == 1) {
                        if (!elephantListparking.get(1).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.parking2.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.parking2.setText(elephantListparking.get(1));
                        }
                    }
                    if (i == 2) {
                        if (!elephantListparking.get(2).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.parking3.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.parking3.setText(elephantListparking.get(2));
                        }
                    }
                    if (i == 3) {
                        if (!elephantListparking.get(3).equalsIgnoreCase("null")) {
                            layoutPropertyDetailBinding.parking4.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBinding.parking4.setText(elephantListparking.get(3));
                        }
                    }
                }
            }
        }
        if (elephantListview != null) {
            for (int i = 0; i < elephantListview.size(); i++) {
                layoutPropertyDetailBinding.viewtext.setVisibility(View.VISIBLE);

                if (i == 0) {
                    if (!elephantListview.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.viewoption1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.viewoption1.setText(elephantListview.get(0));
                    }
                }
                if (i == 1) {
                    if (!elephantListview.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.viewoption2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.viewoption2.setText(elephantListview.get(1));
                    }
                }
                if (i == 2) {
                    if (!elephantListview.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.viewoption3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.viewoption3.setText(elephantListview.get(2));
                    }
                }
                if (i == 3) {
                    if (!elephantListview.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.viewoption4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.viewoption4.setText(elephantListview.get(3));
                    }
                }
            }

        }
        if (elephantListFurnishing != null) {
            layoutPropertyDetailBinding.furnishingtext.setVisibility(View.VISIBLE);


            for (int i = 0; i < elephantListFurnishing.size(); i++) {


                if (i == 0) {
                    if (!elephantListFurnishing.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.furnishing1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.furnishing1.setText(elephantListFurnishing.get(0));
                    }
                }
                if (i == 1) {

                    if (!elephantListFurnishing.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.furnishing2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.furnishing2.setText(elephantListFurnishing.get(1));
                    }
                }
                if (i == 2) {

                    if (!elephantListFurnishing.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.furnishing3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.furnishing3.setText(elephantListFurnishing.get(2));
                    }
                }
                if (i == 3) {

                    if (!elephantListFurnishing.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBinding.furnishing4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBinding.furnishing4.setText(elephantListFurnishing.get(3));
                    }
                }
            }
        }


    }

    public void getDateAndTime() {
        assert responseData.body() != null;
        if (responseData.body().getData().getCreated() != null) {
            String server_format = responseData.body().getData().getCreated();    //server comes format ?
            String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
            String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            try {
                Date date = sdf.parse(server_format);
                System.out.println(date);
                String dateText = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
                String your_format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
                System.out.println(your_format);
                String[] splitted = your_format.split(" ");
                // Now you can set the TextView here
                layoutPropertyDetailBinding.postedon.setText(String.valueOf("Posted on " + dateText));
                layoutPropertyDetailBinding.lastupdate.setText(String.valueOf(splitted[1]));
            } catch (Exception e) {
                System.out.println(e.toString()); //date format error
            }
        }
    }

    private void initMap() {
        suppMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_rent_property_detail, suppMapFragment).commit();
        suppMapFragment.getMapAsync(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvViewProfile:
                dispatchProfProfile();
                break;

            case R.id.tvPropertyListed:
                dispatchNormOrProfListing();
                break;

            case R.id.report_imageview_property_detail:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    Contact_Admin_Fragment report_fragment = new Contact_Admin_Fragment();
                    getFragmentManager().beginTransaction().replace(R.id.propertysalecontainer,
                            report_fragment).addToBackStack("Property_Detail_Fragment").commit();
                }
                break;
            case R.id.ivBack:
                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("professional", "all");
                getActivity().startActivity(intent);*/
                getActivity().finish();
                break;

            case R.id.iv_like_property:

                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                } else {

                    if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                        Toaster.toast(getActivity().getString(R.string.you_not_like_property));
                    } else {
                        getLikeApiCalling(responseData);

                    }
                }
                break;

            case R.id.iv_like_user:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {

                    if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                        Toaster.toast(getActivity().getString(R.string.you_not_like_your_profile));
                    } else {
                        getLikeApiCallingUser(responseData.body().getData().getUserid().getUserId());

                    }
                }
                break;

            case R.id.iv_chat_usr:
                if (ModelManager.modelManager().getCurrentUser() != null)

                    if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                        Toaster.toast(getActivity().getString(R.string.you_not_chat));
                    } else {
                        onChatting();
                    }

                else
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                break;
            case R.id.iv_calling_urs:

                if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                    Toaster.toast(getActivity().getString(R.string.you_not_call));
                } else {
                    onCalling();
                }
                break;

            case R.id.iv_share_urs:
                onShare();
                break;

            case R.id.iv_calling:
                if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                    Toaster.toast(getActivity().getString(R.string.you_not_call));
                } else {

                    onCalling();
                }

                break;
            case R.id.iv_share:
                onShare();
                break;
            case R.id.iv_chatting:
                if (ModelManager.modelManager().getCurrentUser() != null)

                    if (ModelManager.modelManager().getCurrentUser().getId().equalsIgnoreCase(responseData.body().getData().getUserId())) {
                        Toaster.toast(getActivity().getString(R.string.you_not_chat));

                    } else {
                        onChatting();
                    }

                else
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                break;


            case R.id.llReqMoreInfo:
                if (ModelManager.modelManager().getCurrentUser() != null) {
                    startActivity(ChatActivity.getIntent(getContext(), responseData.body().getData().getUserId(), responseData.body().getData().getId(), responseData.body().getData().getTitle(), responseData.body().getData().getDescription()));

                } else {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                }

                break;

        }
    }

    private void dispatchProfProfile() {
        if (responseData.body() != null) {
            if (responseData.body().getData().getUserid() != null && responseData.body().getData().getUserid().isProfessionalProfile() == true) {
                Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
                intent.putExtra("_userId", responseData.body().getData().getProfessionalUserId());
                intent.putExtra("businessUserId",responseData.body().getData().getUserid().getUserId());
                intent.putExtra("info_window_type", "professional");
                startActivity(intent);

               // startActivity(ActivityViewProfileThrowProperty.getIntent(getActivity(), responseData.body().getData()));

            } else {
                Toast.makeText(getContext(), responseData.body().getData().getUserid().getFullname() + " didn't publish the professional profile", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dispatchNormOrProfListing() {
        if (responseData.body() != null) {
            if (responseData.body().getData().getUserid().getType().equalsIgnoreCase("professional")) {
                //BaseManager.saveDataIntoPreferences(responseData.body().getData().getUserid().getId(),"professionalId");
                Intent intent = new Intent(getContext(), ProfessionalPropertyListingActivity.class);
                intent.putExtra("type", responseData.body().getData().getUserid().getType());
                intent.putExtra("propertyType", responseData.body().getData().getType());

                if(responseData.body().getData().getType().equalsIgnoreCase("rent")){
                    intent.putExtra("_id", responseData.body().getData().getUserid().getId());

                }else {
                    intent.putExtra("_id", responseData.body().getData().getProfessionalUserId());

                }
                startActivity(intent);


            } else {
                //BaseManager.saveDataIntoPreferences(responseData.body().getData().getUserid().getId(),"professionalId");
                Intent intent = new Intent(getContext(), ProfessionalPropertyListingActivity.class);
                intent.putExtra("type", responseData.body().getData().getUserid().getType());
                intent.putExtra("propertyType", responseData.body().getData().getType());

                if(responseData.body().getData().getType().equalsIgnoreCase("sale")){
                    intent.putExtra("_id", responseData.body().getData().getProfessionalUserId());

                }else {
                    intent.putExtra("_id", responseData.body().getData().getProfessionalUserId());

                }
                startActivity(intent);
            }

        }
    }

    private void onChatting() {
        if (ModelManager.modelManager().getCurrentUser() != null) {
            startActivity(ChatActivity.getIntent(getActivity(), responseData.body().getData().getUserId(), responseData.body().getData().getId(), responseData.body().getData().getTitle(), responseData.body().getData().getDescription()));

        } else {
            startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

        }


    }

    private void onCalling() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", responseData.body().getData().getMobileNumber(), null));
        startActivity(intent);
    }

    private void onShare() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getString(R.string.body_foodie);
        String shareSub = getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        if (responseData.body().getData().getLat() != null && responseData.body().getData().getLongg() != null) {
            LatLng latLng = new LatLng(Double.parseDouble(responseData.body().getData().getLat()), Double.parseDouble(responseData.body().getData().getLongg()));
            markerOptions.position(latLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.blue_map_icon)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        }
    }

    @Override
    public void onBufferChange(BufferChangeEvent bufferChangeEvent) {

    }

    @Override
    public void onComplete(CompleteEvent completeEvent) {

    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {

    }

    @Override
    public void onLevelsChanged(LevelsChangedEvent levelsChangedEvent) {

    }

    @Override
    public void onSeeked(SeekedEvent seekedEvent) {

    }

    @Override
    public void onTime(TimeEvent timeEvent) {

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null)
                return;
            (getActivity()).runOnUiThread(() -> {
                if (layoutPropertyDetailBinding.viewPager.getCurrentItem() < 7) {
                    layoutPropertyDetailBinding.viewPager.setCurrentItem(layoutPropertyDetailBinding.viewPager.getCurrentItem() + 1);
                } else {
                    layoutPropertyDetailBinding.viewPager.setCurrentItem(0);
                }
            });
        }
    }

    private void getLikeApiCalling(Response<GetPropertyDetail> data) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if (data.body() != null) {
            getUserDetailsCall = api.getLikePostPorBusiness(data.body().getData().getType(),
                    data.body().getData().getId(), ModelManager.modelManager().getCurrentUser().getId(), isLiked);

            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 200) {
                                if (response.body().getLiked() == true) {
                                    layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                    //layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.like_icon_new);
                                    isLiked = false;
                                } else if (response.body().getLiked() == false) {
                                    isLiked = true;
                                    layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                    //layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                                }
                                Toaster.toast(response.body().getResponseMessage());

                            } else {
                                Toaster.toast(response.body().getResponseMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                    Toaster.toast(t.getMessage());

                }
            });

        }

    }


    private void getLikeApiCallingUser(String userId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if (responseData.body() != null) {
            getUserDetailsCall = api.getLikePost(responseData.body().getData().getUserid().getType(), ModelManager.modelManager().getCurrentUser().getId(),
                    responseData.body().getData().getUserid().getId(), isLikedProfNorm);

            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 200) {
                                Toaster.toast(response.body().getResponseMessage());
                                if (response.body().getLiked() == true) {
                                    //layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                    layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.like_icon_new);
                                    isLikedProfNorm = false;
                                } else if (response.body().getLiked() == false) {
                                    isLikedProfNorm = true;
                                    //layoutPropertyDetailBinding.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                    layoutPropertyDetailBinding.ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                                }

                            } else {
                                Toaster.toast(response.body().getResponseMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                    Toaster.toast(t.getMessage());
                }
            });
        }




    }

}
