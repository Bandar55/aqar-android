package com.aqar55.fragments;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.activitys.MainActivity;
import com.aqar55.adapters.product_detail_slider_adapter;
import com.aqar55.databinding.LayoutPropertyDetailBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyDetail;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertyDetailSaleFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private static final String TAG = "Property_Detail_Fragmen";
    Date currentTime;
    private GoogleMap mMap;
    private String propid;
    private String type;

    private String userId="";
    private LayoutPropertyDetailBinding layoutPropertyDetailBindingl;
    private Response<GetPropertyDetail> responseData;
    private boolean isLiked;
    private boolean isLikeUser;
    private SupportMapFragment suppMapFragment;
    private List<String> elephantListview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutPropertyDetailBindingl = DataBindingUtil.inflate(inflater, R.layout.layout_property_detail, container, false);
        initMap();
        Bundle bundle = getArguments();
        propid = bundle.getString("propid");
        type = bundle.getString("type");

        userId = bundle.getString("user_id");

        if(type.equalsIgnoreCase("sale")){
            layoutPropertyDetailBindingl.layoutTime.setVisibility(View.GONE);
        }else {
            layoutPropertyDetailBindingl.layoutTime.setVisibility(View.VISIBLE);

        }
        getDataFromApi();

        currentTime = Calendar.getInstance().getTime();
        Log.d("date_CURRENT", currentTime + "");

        layoutPropertyDetailBindingl.indicator.setupWithViewPager(layoutPropertyDetailBindingl.viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PropertyDetailSaleFragment.SliderTimer(), 2000, 3000);
        layoutPropertyDetailBindingl.reportImageviewPropertyDetail.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivBack.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivLikeProperty.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivLikeUser.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivCalling.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivShare.setOnClickListener(this);
        layoutPropertyDetailBindingl.ivPropChating.setOnClickListener(this);


        return layoutPropertyDetailBindingl.getRoot();


    }

    private void playVideo(List<GetPropertyDetail.Videosfile> videosfile) {

        if (videosfile.size() > 0 && videosfile != null) {
            Uri uri = Uri.parse(videosfile.get(0).getVideo()); //Declare your url here.

            MediaController mediacontroller = new MediaController(
                    getContext());
            // Get the URL from String VideoURL
            layoutPropertyDetailBindingl.videoview.setMediaController(mediacontroller);
            layoutPropertyDetailBindingl.videoview.setVideoURI(uri);
            layoutPropertyDetailBindingl.scrollBar.scrollTo(0, 0);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM;
            mediacontroller.setLayoutParams(lp);
            ((ViewGroup) mediacontroller.getParent()).removeView(mediacontroller);
            (layoutPropertyDetailBindingl.imageVideoView).addView(mediacontroller);

        }
    }

    private void getDataFromApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Log.d(TAG, "getDataFromApi: " + propid);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyDetail> getPropertyDetailCall = api.getPropertyDetail(propid,type,ModelManager.modelManager().getCurrentUser().getId());
        getPropertyDetailCall.enqueue(new Callback<GetPropertyDetail>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyDetail> call, @NonNull Response<GetPropertyDetail> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getData() != null) {
                            responseData = response;
                            if (responseData.body().getData().getType().equalsIgnoreCase("sale") || responseData.body().getData().getType().equalsIgnoreCase("rent")) {
                                layoutPropertyDetailBindingl.layout13ProductDetail.setVisibility(View.GONE);
                            } else {
                                layoutPropertyDetailBindingl.layout13ProductDetail.setVisibility(View.VISIBLE);
                            }
                            playVideo(response.body().getData().getVideosfile());
                            initMap();
                            layoutPropertyDetailBindingl.viewPager.setAdapter(new product_detail_slider_adapter(getContext(), response.body().getData().getImagesfile()));
                            setDataToViews(response);
                        }

                }
            }

            @Override
            public void onFailure(Call<GetPropertyDetail> call, Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
                Log.d(TAG, "onFailure: " + t.getMessage());
                layoutPropertyDetailBindingl.scrollBar.scrollTo(0, 0);
            }
        });
    }

    private void setDataToViews(Response<GetPropertyDetail> response) {

        if (response.body().getData() != null) {
            layoutPropertyDetailBindingl.propertytitle.setText(response.body().getData().getTitle());
            layoutPropertyDetailBindingl.propertyfor.setText("Property For " + response.body().getData().getType());
            layoutPropertyDetailBindingl.prpid.setText(response.body().getData().getId());
            layoutPropertyDetailBindingl.purpose.setText("Property Purpose : " + response.body().getData().getPurpose());
            layoutPropertyDetailBindingl.abailable.setText("Available For : " + response.body().getData().getAvailable());
            if (response.body().getData().getImagesfile().size() > 0) {
                layoutPropertyDetailBindingl.text.setText("1 of " + response.body().getData().getImagesfile().size());
            } else {
                layoutPropertyDetailBindingl.text.setText("");
            }
            layoutPropertyDetailBindingl.category.setText("Category : " + response.body().getData().getCategory());
            layoutPropertyDetailBindingl.price.setText("Price " +response.body().getData().getUserid().getCurrency()+ response.body().getData().getTotalPrice());
            layoutPropertyDetailBindingl.priperme.setText("Price per Meter : "+response.body().getData().getUserid().getCurrency() + response.body().getData().getPricepermeter());
            layoutPropertyDetailBindingl.priceplotsize.setText("Price X Plot size : "+response.body().getData().getUserid().getCurrency() + response.body().getData().getTotalPriceSale() + " X " + response.body().getData().getPlotsize()+" "+response.body().getData().getPlotsizeunit());
            layoutPropertyDetailBindingl.plotsize.setText(response.body().getData().getPlotsize() + " " + response.body().getData().getPlotsizeunit());
            layoutPropertyDetailBindingl.plotsize.setText(response.body().getData().getPlotsize() + " " + response.body().getData().getPlotsizeunit());
            layoutPropertyDetailBindingl.measurement.setText(response.body().getData().getLength()+response.body().getData().getLengthUnit()+"X"+response.body().getData().getWidth()+response.body().getData().getWidthUnit());
            layoutPropertyDetailBindingl.builtinsize.setText(response.body().getData().getBuiltsize() + " " + response.body().getData().getBuiltsizeunit());
            layoutPropertyDetailBindingl.builtyear.setText(response.body().getData().getYearbuilt());
            layoutPropertyDetailBindingl.streetview.setText(response.body().getData().getStreetview());
            layoutPropertyDetailBindingl.streetwidth.setText(response.body().getData().getStreetwidth() + " " + response.body().getData().getStreetwidthunit());
            layoutPropertyDetailBindingl.bedroom.setText(response.body().getData().getBedrooms());
            layoutPropertyDetailBindingl.bathrooms.setText(response.body().getData().getBathrooms());
            layoutPropertyDetailBindingl.kitchin.setText(response.body().getData().getKitchens());
            layoutPropertyDetailBindingl.livingroom.setText("null");
            layoutPropertyDetailBindingl.extrabuilding.setText("Building : Apartment " + response.body().getData().getExtrabuildingno());
            layoutPropertyDetailBindingl.extrashowrooms.setText("Building : Room " + response.body().getData().getExtrashowroomno());
            layoutPropertyDetailBindingl.revenue.setText("Revenue : " + response.body().getData().getRevenue());
            layoutPropertyDetailBindingl.description.setText(response.body().getData().getDescription());

            setSpecificData(response);
            layoutPropertyDetailBindingl.address.setText(response.body().getData().getAddress());
            layoutPropertyDetailBindingl.postdate.setText("Posted On "+response.body().getData().getCreated());
            layoutPropertyDetailBindingl.updatedtime.setText("Updated "+response.body().getData().getModified());
            if ((response.body().getData().getUserid() != null)) {

                if (response.body().getData().getUserid().getImagesfile().size() > 0) {
                    Glide.with(((getContext()))).load(response.body().getData().getUserid().getImagesfile())
                            .placeholder(new ColorDrawable(Color.GRAY)).into(layoutPropertyDetailBindingl.userpic);
                } else {
                    layoutPropertyDetailBindingl.userpic.setImageResource(R.color.placeholder_gray);
                }
                layoutPropertyDetailBindingl.username.setText(response.body().getData().getUserid().getFullname());
                layoutPropertyDetailBindingl.categoryP.setText(response.body().getData().getUserid().getCategory());
                layoutPropertyDetailBindingl.subcategoryP.setText(response.body().getData().getUserid().getSubcategory());
                layoutPropertyDetailBindingl.pId.setText(response.body().getData().getUserid().getProfessionalid());
                // layoutPropertyDetailBindingl.pdetail.setText(response.body().getData().getUserid().);
                layoutPropertyDetailBindingl.mobile.setText(response.body().getData().getUserid().getMobilenumber());

                if (response.body().getData().getLikedStatus().equalsIgnoreCase("yes")) {
                    layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                    isLiked = false;
                } else {
                    layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                    isLiked = true;

                }

                if (response.body().getData().getLikedStatus().equalsIgnoreCase("yes")) {
                    layoutPropertyDetailBindingl.ivLikeUser.setImageResource(R.drawable.like_icon_new);
                    isLikeUser = false;
                } else {
                    layoutPropertyDetailBindingl.ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                    isLikeUser = true;
                    if (response.body().getData().getLiked() != null) {
                        if (response.body().getData().getLiked().equalsIgnoreCase("yes")) {
                            layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                        } else {
                            layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                        }
                    }


                    if (response.body().getData().getUserid().getLikedStatus() != null) {
                        if (response.body().getData().getUserid().getLikedStatus().equalsIgnoreCase("yes")) {
                            layoutPropertyDetailBindingl.ivLikeUser.setImageResource(R.drawable.like_icon_new);
                        } else {
                            layoutPropertyDetailBindingl.ivLikeUser.setImageResource(R.drawable.unselcted_heart);

                        }
                    }


                    if (response.body().getData().getUserid().getType().equalsIgnoreCase("normal")) {
                        layoutPropertyDetailBindingl.ivLikeUser.setVisibility(View.GONE);
                        layoutPropertyDetailBindingl.layout13ProductDetail.setVisibility(View.GONE);
                    } else {
                        layoutPropertyDetailBindingl.ivLikeUser.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.layout13ProductDetail.setVisibility(View.VISIBLE);

                    }
                    // layoutPropertyDetailBindingl.mobile2.setText(response.body().getData().getUserid().getMobilenumber());
                }
                getDateAndTime();
            }
        }
    }
    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map_rent_property_detail);
//        mapFragment.getMapAsync(this);

        suppMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_11_product_detail, suppMapFragment).commit();
        suppMapFragment.getMapAsync(this);
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
                layoutPropertyDetailBindingl.postdate.setText(String.valueOf("Posted on " + dateText));
                layoutPropertyDetailBindingl.updatedtime.setText(String.valueOf(splitted[1]));

            } catch (Exception e) {
                System.out.println(e.toString()); //date format error
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.report_imageview_property_detail:
                Report_Fragment report_fragment = new Report_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.propertysalecontainer,
                        report_fragment).addToBackStack("Property_Detail_Fragment").commit();
                break;
            case R.id.ivBack:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("professional", "all");
                getActivity().startActivity(intent);
                getActivity().finish();
                break;

            case R.id.iv_like_property:
                if(ModelManager.modelManager().getCurrentUser()==null){
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                }else {
                    getLikeApiCalling(responseData);
                }
                break;
            case R.id.iv_like_user:
                if(ModelManager.modelManager().getCurrentUser()==null){
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                }else {
                    getLikeApiCallingUser(responseData.body().getData().getUserid().getId());
                }
                break;


            case R.id.iv_user_chating:
                if(ModelManager.modelManager().getCurrentUser()==null){
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                }else {
                    chatingActivityCalling(propid);
                }
                break;
            case R.id.iv_prop_chating:
                if(ModelManager.modelManager().getCurrentUser()==null){
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                }else {
                    chatingActivityCalling(responseData.body().getData().getUserId());
                }
                break;

            case R.id.iv_calling:
                 intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", responseData.body().getData().getMobileNumber(), null));
                startActivity(intent);

                break;

            case R.id.iv_share:

                Intent intenti = new Intent();
                intenti.setAction(Intent.ACTION_SEND);
                intenti.putExtra(Intent.EXTRA_TEXT,"Aqar 55");
                intenti.setType("text/plain");
                startActivity(intenti);

                break;
        }
    }

    private void chatingActivityCalling(String propid) {

        if(ModelManager.modelManager().getCurrentUser()!=null){
            startActivity(ChatActivity.getIntent(getActivity(),propid,responseData.body().getData().getId(),responseData.body().getData().getTitle(),responseData.body().getData().getDescription()));
            getActivity().finish();
        }else {
            startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        if(responseData!=null){
            assert responseData.body() != null;
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

    }

    private void setSpecificData(Response<GetPropertyDetail> response) {
        assert response.body() != null;
        List<String> elephantListindoor = Arrays.asList(response.body().getData().getIndoor().split(","));
        List<String> elephantListoutdoor = Arrays.asList(response.body().getData().getOutdoor().split(","));
        List<String> elephantListparking = Arrays.asList(response.body().getData().getParkingOption().split(","));
        if(response.body().getData().getViews()!=null){
            elephantListview = Arrays.asList(response.body().getData().getViews().split(","));
        }
        List<String> elephantListFurnishing = Arrays.asList(response.body().getData().getFurnish().split(","));
        layoutPropertyDetailBindingl.specification.setVisibility(View.VISIBLE);

        if (elephantListindoor != null) {
            for (int i = 0; i < elephantListindoor.size(); i++) {
                layoutPropertyDetailBindingl.indoortext.setVisibility(View.VISIBLE);
                if (i == 0) {
                    if (!elephantListindoor.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.indooroption1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.indooroption1.setText(elephantListindoor.get(0));
                    }
                }
                if (i == 1) {
                    if (!elephantListindoor.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.indooroption2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.indooroption2.setText(elephantListindoor.get(1));
                    }
                }
                if (i == 2) {
                    if (!elephantListindoor.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.indooroption3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.indooroption3.setText(elephantListindoor.get(2));
                    }
                }

                if (i == 3) {
                    if (!elephantListindoor.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.indooroption4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.indooroption4.setText(elephantListindoor.get(3));
                    }
                }
            }
        }
        if (elephantListoutdoor != null) {
            layoutPropertyDetailBindingl.outdoortext.setVisibility(View.VISIBLE);

            for (int i = 0; i < elephantListoutdoor.size(); i++) {
                if (i == 0) {
                    if (!elephantListoutdoor.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.outdooroption1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.outdooroption1.setText(elephantListoutdoor.get(0));
                    }
                }
                if (i == 1) {
                    if (!elephantListoutdoor.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.outdooroption2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.outdooroption2.setText(elephantListoutdoor.get(1));
                    }
                }
                if (i == 2) {
                    if (!elephantListoutdoor.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.outdooroption3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.outdooroption3.setText(elephantListoutdoor.get(2));
                    }
                }
                if (i == 3) {
                    if (!elephantListoutdoor.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.outdooroption4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.outdooroption4.setText(elephantListoutdoor.get(3));
                    }
                }
            }
        }
        if (elephantListparking != null) {
            layoutPropertyDetailBindingl.parkingtext.setVisibility(View.VISIBLE);

            for (int i = 0; i < elephantListparking.size(); i++) {
                if (i == 0) {

                    if (!elephantListparking.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.parking1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.parking1.setText(elephantListparking.get(0));
                    }
                }

                if (i == 1) {
                    if (elephantListparking.get(1) != null) {
                        if (!elephantListparking.get(1).equalsIgnoreCase("null") && elephantListparking.get(1).isEmpty()) {
                            layoutPropertyDetailBindingl.parking2.setVisibility(View.VISIBLE);
                            layoutPropertyDetailBindingl.parking2.setText(elephantListparking.get(1));
                        }
                    }
                }
                if (i == 2) {
                    if (!elephantListparking.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.parking3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.parking3.setText(elephantListparking.get(2));
                    }
                }
                if (i == 3) {
                    if (!elephantListparking.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.parking4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.parking4.setText(elephantListparking.get(3));
                    }
                }
            }
        }
        if (elephantListview != null) {
            layoutPropertyDetailBindingl.viewtext.setVisibility(View.VISIBLE);


            for (int i = 0; i < elephantListview.size(); i++) {
                if (i == 0) {

                    if (!elephantListview.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.viewoption1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.viewoption1.setText(elephantListview.get(0));
                    }
                }
                if (i == 1) {

                    if (!elephantListview.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.viewoption2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.viewoption2.setText(elephantListview.get(1));
                    }
                }
                if (i == 2) {

                    if (!elephantListview.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.viewoption3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.viewoption3.setText(elephantListview.get(2));
                    }
                }
                if (i == 3) {

                    if (!elephantListview.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.viewoption4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.viewoption4.setText(elephantListview.get(3));
                    }
                }
            }
        }
        if (elephantListFurnishing != null) {
            layoutPropertyDetailBindingl.furnishingtext.setVisibility(View.VISIBLE);

            for (int i = 0; i < elephantListFurnishing.size(); i++) {
                if (i == 0) {

                    if (!elephantListFurnishing.get(0).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.furnishing1.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.furnishing1.setText(elephantListFurnishing.get(0));
                    }
                }
                if (i == 1) {
                    if (!elephantListFurnishing.get(1).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.furnishing2.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.furnishing2.setText(elephantListFurnishing.get(1));
                    }
                }
                if (i == 2) {
                    if (!elephantListFurnishing.get(2).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.furnishing3.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.furnishing3.setText(elephantListFurnishing.get(2));
                    }
                }
                if (i == 3) {
                    if (!elephantListFurnishing.get(3).equalsIgnoreCase("null")) {
                        layoutPropertyDetailBindingl.furnishing4.setVisibility(View.VISIBLE);
                        layoutPropertyDetailBindingl.furnishing4.setText(elephantListFurnishing.get(3));
                    }
                }
            }
        }

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null)
                return;
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (layoutPropertyDetailBindingl.viewPager.getCurrentItem() < 7) {
                        layoutPropertyDetailBindingl.viewPager.setCurrentItem(layoutPropertyDetailBindingl.viewPager.getCurrentItem() + 1);
                    } else {
                        layoutPropertyDetailBindingl.viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }



    private void getLikeApiCalling(Response<GetPropertyDetail> userId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;

        getUserDetailsCall = api.getLikePostPorBusiness("sale",userId.body().getData().getId(),ModelManager.modelManager().getCurrentUser().getId(),isLiked);

        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        if(response.body().getResponseCode()==200)
                        {
                            if(response.body().getLiked()==true){
                                layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                isLiked=false;

                            }else if(response.body().getLiked()==false){
                                layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                isLiked=true;

                            }
                            Toaster.toast(response.body().getResponseMessage());

                        }else {
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




    private void getLikeApiCallingUser(String userId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall = api.getLikePost(responseData.body().getData().getUserid().getType(),ModelManager.modelManager().getCurrentUser().getId(),userId,isLikeUser);
        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        if(response.body().getResponseCode()==200)
                        {
                            Toaster.toast(response.body().getResponseMessage());
                            if(response.body().getLiked()==true){
                                layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                isLikeUser=false;

                            }else {
                                layoutPropertyDetailBindingl.ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                isLikeUser=true;

                            }

                        }else {
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
