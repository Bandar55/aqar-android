package com.aqar55.activitys;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aqar55.helper.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aqar55.R;
import com.aqar55.adapters.SlidingImage_Adapter;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aqar55.helper.ApplicationManager.getContext;

public class ProfPropertyNameActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    @BindView(R.id.tvProfName)
    TextView tvProfName;
    @BindView(R.id.tvProfCategory)
    TextView tvProfCategory;
    @BindView(R.id.tvProfSubCategory)
    TextView tvProfSubCategory;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvLicenseNumber)
    TextView tvLicenseNumber;
    @BindView(R.id.tvLicenseNumber2)
    TextView tvLicenseNumber2;
    @BindView(R.id.tvProfId)
    TextView tvProfId;
    @BindView(R.id.tvProjectAcheived)
    TextView tvProjectAcheived;
    @BindView(R.id.ivVideoThumnail)
    VideoView ivVideoThumnail;
    @BindView(R.id.tvOfficeAddress)
    TextView tvOfficeAddress;
    @BindView(R.id.tvOfficeNumber)
    TextView tvOfficeNumber;
    @BindView(R.id.tvOfficeMail)
    TextView tvOfficeMail;
    @BindView(R.id.tvOfficeWebSite)
    TextView tvOfficeWebSite;
    @BindView(R.id.tvDurationTime)
    TextView tvDurationTime;
    @BindView(R.id.tvUpdatedTime)
    TextView tvUpdatedTime;
    @BindView(R.id.tvViewedTime)
    TextView tvViewedTime;
    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.tvProfileCategory)
    TextView tvProfileCategory;
    @BindView(R.id.tvProfileSubCategory)
    TextView tvProfileSubCategory;
    @BindView(R.id.tvProfileID)
    TextView tvProfileID;
    @BindView(R.id.tvProfileDetails)
    TextView tvProfileDetails;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSpecialities1)
    TextView tvSpecialities1;
    @BindView(R.id.tvIDName)
    TextView tvIDName;
    @BindView(R.id.pbUserDetails)
    ProgressBar pbUserDetails;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.tvSpecialities)
    TextView tvSpecialities;
    @BindView(R.id.tvSpecialities2)
    TextView tvSpecialities2;
    @BindView(R.id.tvAreaCovered)
    TextView tvAreaCovered;
    @BindView(R.id.tvAreaCovered2)
    TextView tvAreaCovered2;
    @BindView(R.id.tvAreaCovered3)
    TextView tvAreaCovered3;
    @BindView(R.id.tvSpecialities3)
    TextView tvSpecialities3;
    @BindView(R.id.ivPlayVideo)
    ImageView ivPlayVideo;
    @BindView(R.id.iv_like_property)
    ImageView ivLikeProperty;
    @BindView(R.id.iv_like_user)
    ImageView ivLikeUser;
    @BindView(R.id.tvProfileCategory5)
    TextView tvPhoneNumber;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.tvPropertyListed)
    TextView tvViewProfile;
    @BindView(R.id.tvPropertyListed2)
    TextView tvPropertyListed;
    SupportMapFragment suppMapFragment;
    private ArrayList<String> ImagesArray;
    private String userId = "";
    private String type = "";
    private GoogleMap mMap;
    private String videoUrl = "";
    private MediaController mediaController;
    private boolean isLike;
    private GetUserDetails.DataEntity dataEntity;
    private String businessUserId;
    private boolean isBusinessProfessional;
    private String commanId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_property_name);
        ButterKnife.bind(this);
        ImagesArray = new ArrayList<>();
        initMap();
        initMediaController();
        getIntentData();
        getUserDetails();

    }

    private void initMediaController() {
        mediaController = new MediaController(this);
        mediaController.setAnchorView(ivVideoThumnail);
        ivVideoThumnail.setMediaController(mediaController);
    }

    @OnClick({R.id.ivBack, R.id.ivPlayVideo, R.id.iv_like_property, R.id.iv_like_user,
            R.id.report_imageview_property_detail, R.id.tvReqMoreInfo, R.id.iv_calling_user, R.id.iv_share_user,
            R.id.iv_calling_urs, R.id.iv_share_urs, R.id.iv_chatting_user, R.id.iv_chat_usr, R.id.tvPropertyListed, R.id.tvPropertyListed2, R.id.ivFB,
            R.id.ivGoogle, R.id.ivTwitter, R.id.ivSnapChat, R.id.ivLinkedIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivPlayVideo:


               if(videoUrl.isEmpty())

               {
                   Toaster.toast("Video url not found");

               }else {
                   ivPlayVideo.setVisibility(View.GONE);

                   String status = videoUrl.substring(videoUrl.lastIndexOf(".") + 1, videoUrl.length());

                   if(status.equalsIgnoreCase("mov")){
                       String[] videoUr= videoUrl.split(".mov");
                       ivVideoThumnail.setVideoPath(videoUr[0]+".mp4");
                       ivVideoThumnail.start();
                   }else {
                       ivVideoThumnail.setVideoPath(videoUrl);
                       ivVideoThumnail.start();
                   }
               }

               break;
            case R.id.iv_like_property:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getApplicationContext()));
                } else {
                    getLikeApiCallingUser(userId);
                }

                break;

            case R.id.iv_like_user:

                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getApplicationContext()));
                } else {
                    getLikeApiCallingPro(dataEntity.getId());
                }

                break;

            case R.id.report_imageview_property_detail:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                } else {
                    Intent intent = new Intent(this, Contact_Admin_Activity.class);
                    startActivity(intent);
                }
                break;

            case R.id.tvReqMoreInfo:
                if (ModelManager.modelManager().getCurrentUser() != null) {
                    startActivity(ChatActivity.getIntent(this, dataEntity.getUserId(), dataEntity.getId(), dataEntity.getFullname(),dataEntity.getDescription()));
                } else {
                    startActivity(Login_Signup_Button_Activity.getIntent(this));

                }


                break;

            case R.id.iv_calling_user:
                calling();
                break;
            case R.id.iv_share_user:
                share();

                break;
            case R.id.iv_calling_urs:
                calling();
                break;
            case R.id.iv_share_urs:
                share();
                break;

            case R.id.iv_chatting_user:
                if (ModelManager.modelManager().getCurrentUser() != null)
                    chattingUser();
                else
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                break;

            case R.id.iv_chat_usr:
                if (ModelManager.modelManager().getCurrentUser() != null)
                    chattingUser();
                else
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                break;

            case R.id.tvPropertyListed:

                if(dataEntity.getType().equalsIgnoreCase("professional")){
                    if (dataEntity.isBusinessProfileExists() == true) {
                        startActivity(ActivityViewProfessionalBusinessProfile.getIntent(this, dataEntity));
                    } else{
                        Toast.makeText(getContext(), dataEntity.getFullname()+" didn't publish the business profile", Toast.LENGTH_LONG).show();

                    }

                }else {
                    if (dataEntity.isProfessionalProfileExists() == true) {
                        startActivity(ActivityViewProfessionalBusinessProfile.getIntent(this, dataEntity));
                    } else {
                        Toast.makeText(getContext(), dataEntity.getFullname()+" didn't publish the professional profile", Toast.LENGTH_LONG).show();

                    }
                }

                break;

            case R.id.tvPropertyListed2:

                if (dataEntity.getTotalPropertyCreated() == null||dataEntity.getTotalPropertyCreated().isEmpty()) {
                    Toaster.toast("No property listed");
                    return;
                }

                if (dataEntity != null) {
                    if (dataEntity.getType().equalsIgnoreCase("professional")) {

                        //BaseManager.saveDataIntoPreferences(responseData.body().getData().getUserid().getId(),"professionalId");
                        Intent intent = new Intent(getContext(), ProfessionalPropertyListingActivity.class);
                        intent.putExtra("type", dataEntity.getType());
                        intent.putExtra("propertyType", dataEntity.getType());
                        intent.putExtra("_id", dataEntity.getId());
                        commanId=dataEntity.getId();
                        startActivity(intent);
                    } else if (dataEntity.getType().equalsIgnoreCase("business")) {
                        //BaseManager.saveDataIntoPreferences(responseData.body().getData().getUserid().getId(),"professionalId");
                        Intent intent = new Intent(getContext(), ProfessionalPropertyListingActivity.class);
                        intent.putExtra("type", dataEntity.getType());
                        intent.putExtra("propertyType", dataEntity.getType());
                        intent.putExtra("_id", dataEntity.getCommanUserProfessionalId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), ProfessionalPropertyListingActivity.class);
                        intent.putExtra("type", dataEntity.getType());
                        intent.putExtra("propertyType", dataEntity.getType());
                        intent.putExtra("_id", commanId);
                        startActivity(intent);
                    }

                }
                break;


            case R.id.ivFB:

                if (dataEntity.getFacebookUrl() != null && dataEntity.getFacebookUrl().isEmpty())
                    Toaster.toast(dataEntity.getFullname() + " " + "not added facebook account");
                if (Utils.isValidURL(dataEntity.getFacebookUrl())) {
                    openBrowser(dataEntity.getFacebookUrl());

                }
                break;


            case R.id.ivTwitter:

                if (dataEntity.getTwitterUrl() != null && dataEntity.getTwitterUrl().isEmpty())
                    Toaster.toast(dataEntity.getFullname() + " " + "not added twitter account");
                if (Utils.isValidURL(dataEntity.getTwitterUrl())) {


                    openBrowser(dataEntity.getTwitterUrl());

                }
                break;


            case R.id.ivGoogle:

                if (dataEntity.getGoogleplusUrl() != null && dataEntity.getGoogleplusUrl().isEmpty())
                    Toaster.toast(dataEntity.getFullname() + " " + "not added youtube account");
                if (Utils.isValidURL(dataEntity.getGoogleplusUrl())) {


                    openBrowser(dataEntity.getGoogleplusUrl());

                }
                break;


            case R.id.ivSnapChat:

                if (dataEntity.getSnapchatUrl() != null && dataEntity.getSnapchatUrl().isEmpty())
                    Toaster.toast(dataEntity.getFullname() + " " + "not added snapchat account");
                if (Utils.isValidURL(dataEntity.getSnapchatUrl())) {
                    openBrowser(dataEntity.getSnapchatUrl());

                }
                break;

            case R.id.ivLinkedIn:

                if (dataEntity.getLinkedinUrl() != null && dataEntity.getLinkedinUrl().isEmpty())
                    Toaster.toast(dataEntity.getFullname() + " " + "not added linkedin account");
                if (Utils.isValidURL(dataEntity.getLinkedinUrl())) {


                    openBrowser(dataEntity.getLinkedinUrl());

                }
                break;

        }
    }

    private void openBrowser(String facebookUrl) {


        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(browserIntent);
    }

    private void chattingUser() {
        if (ModelManager.modelManager().getCurrentUser() != null) {
            startActivity(ChatActivity.getIntent(this, dataEntity.getUserId(), dataEntity.getId(), dataEntity.getFullname(),dataEntity.getDescription()));

        } else {
            startActivity(Login_Signup_Button_Activity.getIntent(this));

        }

    }


    public void payVideo(String url) {




            String[] urlFinal=url.split(".mov");
            String urlViseo=urlFinal[0]+".mp4";


        Uri webpage = Uri.parse(urlViseo);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getString(R.string.body_foodie);
        String shareSub = getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void calling() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataEntity.getMobilenumber(), null));
        startActivity(intent);
    }

    private void initMap() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragProName);
//        mapFragment.getMapAsync(this);
        suppMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapFragProName, suppMapFragment).commit();
        suppMapFragment.getMapAsync(this);
    }

    private void getIntentData() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                userId = bundle.getString("_userId");
                type = bundle.getString("info_window_type");
                businessUserId = bundle.getString("businessUserId");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    // hit getUserDetails api
    private void getUserDetails() {
        if (type.equalsIgnoreCase("professional")) {
            MyDialog.getInstance(this).showDialog(this);
            Api api = ApiClientConnection.getInstance().createApiInterface();
            Call<GetUserDetails> getUserDetailsCall;
            if (ModelManager.modelManager().getCurrentUser() != null)
                getUserDetailsCall = api.getBusinessOrProfessionalDetails(businessUserId, userId, type);
            else
                getUserDetailsCall = api.getBusinessOrProfessionalDetails("", userId, type);
            getUserDetailsCall.enqueue(new Callback<GetUserDetails>() {
                @Override
                public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {
                    MyDialog.getInstance(ProfPropertyNameActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 200) {
                            dataEntity = response.body().getData();
                            setProfessionalData(dataEntity);
                            isBusinessProfessional = true;
                            List<GetUserDetails.ImagesFileEntity> imagesFileEntityList = dataEntity.getImagesfile();
                            init(imagesFileEntityList);
                        } else {
                            Toast.makeText(ProfPropertyNameActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetUserDetails> call, Throwable t) {
                    MyDialog.getInstance(ProfPropertyNameActivity.this).hideDialog();
                }
            });
        } else {
            Api api = ApiClientConnection.getInstance().createApiInterface();
            Call<GetUserDetails> getUserDetailsCall;
            if (ModelManager.modelManager().getCurrentUser() != null)
                getUserDetailsCall = api.getBusinessOrProfessionalDetails(businessUserId, userId, type);
            else
                getUserDetailsCall = api.getBusinessOrProfessionalDetails(businessUserId, userId, type);

            getUserDetailsCall.enqueue(new Callback<GetUserDetails>() {
                @Override
                public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 200) {
                            dataEntity = response.body().getData();

                            List<GetUserDetails.ImagesFileEntity> imagesFileEntityList = dataEntity.getImagesfile();
                            init(imagesFileEntityList);
                            setBusinessData(dataEntity);
                            isBusinessProfessional = false;
                        } else {
                            Toast.makeText(ProfPropertyNameActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetUserDetails> call, Throwable t) {

                }
            });
        }


    }

    private void setProfessionalData(GetUserDetails.DataEntity dataEntity) {
        commanId=dataEntity.getId();

        if (dataEntity.getType().equalsIgnoreCase("business")) {
            tvViewProfile.setText("View Professional Profile");
            tvIDName.setText("Business ID");
            tvProfileID.setText("ID : " + dataEntity.getBusinessid());
            tvProfileDetails.setText("Business Details");
        } else {
            tvViewProfile.setText("View Business Profile");
            tvIDName.setText("Professional ID");
            tvProfileID.setText("ID : " + dataEntity.getProfessionalid());
            tvProfileDetails.setText("Professional Details");

        }
        if (dataEntity.getTotalPropertyCreated() == null) {
            tvPropertyListed.setText("0 " + " Properties Listed");

        } else {
            tvPropertyListed.setText(dataEntity.getTotalPropertyCreated() + " Properties Listed");

        }

        tvTitle.setText(dataEntity.getFullname());
        tvProfName.setText(dataEntity.getFullname());
        tvProfCategory.setText(dataEntity.getCategory());
        tvProfSubCategory.setText(dataEntity.getSubcategory());
        tvProfId.setText(dataEntity.getProfessionalid());
        tvProfileName.setText(dataEntity.getFullname());
        tvProfileCategory.setText(dataEntity.getCategory());
        tvProfileSubCategory.setText(dataEntity.getSubcategory());

        tvOfficeMail.setText(dataEntity.getEmail());
        tvOfficeAddress.setText("");
        tvOfficeNumber.setText(dataEntity.getCountrycode() + " " + dataEntity.getMobilenumber());
        tvOfficeWebSite.setText(dataEntity.getWebsite());
        tvDescription.setText(dataEntity.getDescription());
        tvProjectAcheived.setText(dataEntity.getProjectAchieved());
        tvPhoneNumber.setText(dataEntity.getCountrycode() + " " + dataEntity.getMobilenumber());
        tvLicenseNumber.setText("License No. : " + "0000000000000");
        tvLicenseNumber2.setText("License No. : " + "0000000000000");
        tvDurationTime.setText("Member since " + dataEntity.getMembersince());
        getDateAndTime(dataEntity.getCreatedat());
        tvViewedTime.setText("Viewed " + dataEntity.getCount() + " times");
        tvSpecialities1.setText(dataEntity.getSpecialities());
        tvSpecialities.setText("");
        tvSpecialities2.setText("");
        tvSpecialities3.setText("");
        tvAreaCovered.setText(dataEntity.getAreaCovered());
        tvAreaCovered2.setText("");
        tvAreaCovered3.setText("");
        if (dataEntity.getLikedStatus() != null) {
            if (dataEntity.getLikedStatus().equalsIgnoreCase("yes")) {
                ivLikeUser.setImageResource(R.drawable.like_icon_new);
                ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                isLike = false;
            } else {
                ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                isLike = true;

            }
        }


        tvOfficeAddress.setText(dataEntity.getArea() + " " + dataEntity.getCity() + " " + dataEntity.getCountry() + " " + dataEntity.getZipcode());
        List<GetUserDetails.VideosFileEntity> videosFileEntityList = dataEntity.getVideosfile();
        if (videosFileEntityList.size() > 0)
            if (videosFileEntityList.get(0) != null && videosFileEntityList != null && !videosFileEntityList.isEmpty() && videosFileEntityList.size() > 0 && videosFileEntityList.get(0).getVideo() != null)
                videoUrl = videosFileEntityList.get(0).getVideo();
        setProfilePic(dataEntity.getProfileimage());
        if (dataEntity.getLat() != null && !dataEntity.getLat().isEmpty()) {
            double lat = Double.parseDouble(dataEntity.getLat());
            double lng = Double.parseDouble(dataEntity.getLng());
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8.0f));
        }
    }

    private void setBusinessData(GetUserDetails.DataEntity dataEntity) {
        if (dataEntity.getTotalPropertyCreated() == null) {
            tvPropertyListed.setText("0 " + " Properties Listed");

        } else {
            tvPropertyListed.setText(dataEntity.getTotalPropertyCreated() + " Properties Listed");

        }
        if (dataEntity.getType().equalsIgnoreCase("business")) {
            tvViewProfile.setText("View Professional Profile");
            tvIDName.setText("Business ID");
            tvProfileID.setText("ID : " + dataEntity.getBusinessid());
            tvProfileDetails.setText("Business Details");
        } else {
            tvViewProfile.setText("View Business Profile");
            tvIDName.setText("Professional ID");
            tvProfileID.setText("ID : " + dataEntity.getProfessionalid());
            tvProfileDetails.setText("Professional Details");
        }

        if (dataEntity.getTotalPropertyCreated() == null) {
            tvPropertyListed.setText("0" + " " + " Properties Listed");

        } else {
            tvPropertyListed.setText(dataEntity.getTotalPropertyCreated() + " " + " Properties Listed ");

        }
        tvTitle.setText(dataEntity.getFullname());
        tvProfName.setText(dataEntity.getFullname());
        tvProfCategory.setText(dataEntity.getCategory());
        tvProfSubCategory.setText(dataEntity.getSubcategory());
        tvProfId.setText(dataEntity.getBusinessid());
        tvProfileName.setText(dataEntity.getFullname());
        tvProfileCategory.setText(dataEntity.getCategory());
        tvProfileSubCategory.setText(dataEntity.getSubcategory());

        tvOfficeMail.setText(dataEntity.getEmail());
        tvOfficeAddress.setText("");
        tvOfficeNumber.setText(dataEntity.getCountrycode() + " " + dataEntity.getMobilenumber());
        tvOfficeWebSite.setText(dataEntity.getWebsite());
        tvDescription.setText(dataEntity.getDescription());
        tvProjectAcheived.setText(dataEntity.getProjectAchieved());
        tvPhoneNumber.setText(dataEntity.getCountrycode() + " " + dataEntity.getMobilenumber());
        tvLicenseNumber.setText("License No. : " + "000000000000");
        tvLicenseNumber2.setText("License No. : " + "0000000000000");
        tvDurationTime.setText("Member since " + dataEntity.getMembersince());
        getDateAndTime(dataEntity.getCreatedat());
        tvSpecialities1.setText(dataEntity.getSpecialities());
        tvViewedTime.setText("Viewed " + dataEntity.getCount() + " times");
        tvSpecialities.setText("");
        tvSpecialities2.setText("");
        tvSpecialities3.setText("");
        tvAreaCovered.setText(dataEntity.getAreaCovered());
        tvAreaCovered2.setText("");
        tvAreaCovered3.setText("");
        tvOfficeAddress.setText(dataEntity.getArea() + " " + dataEntity.getCity() + " " + dataEntity.getCountry() + " " + dataEntity.getZipcode());


        if (dataEntity.getLikedStatus() != null) {
            if (dataEntity.getLikedStatus().equalsIgnoreCase("yes")) {
                ivLikeUser.setImageResource(R.drawable.like_icon_new);
                ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                isLike = false;
            } else {
                ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                isLike = true;
            }
        }


        setProfilePic(dataEntity.getProfileimage());

        double lat = Double.parseDouble(dataEntity.getLat());
        double lng = Double.parseDouble(dataEntity.getLng());

        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8.0f));
    }
////////////time convert///////////////////////////////////

    public void getDateAndTime(String createdat) {

        //server comes format ?
        String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date date = sdf.parse(createdat);
            System.out.println(date);
            String dateText = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
            String your_format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
            System.out.println(your_format);
            String[] splitted = your_format.split(" ");


            tvUpdatedTime.setText("Updated : " + your_format);
            // Now you can set the TextView here

        } catch (Exception e) {
            System.out.println(e.toString()); //date format error
        }
    }

    private void setProfilePic(final String profilePic) {
        if (profilePic != null && !profilePic.isEmpty()) {
            pbUserDetails.setVisibility(View.VISIBLE);
            Glide.with(this.getApplicationContext())
                    .load(profilePic)
                    .into(new SimpleTarget<Drawable>(200, 200) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            ivProfileImage.setImageDrawable(resource);
                            pbUserDetails.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            pbUserDetails.setVisibility(View.GONE);
                            setProfilePic(profilePic);
                        }
                    });
        } else {
            pbUserDetails.setVisibility(View.GONE);
            Glide.with(this.getApplicationContext())
                    .load(R.color.placeholder_gray)
                    .centerCrop()
                    .into(ivProfileImage);
        }

    }

    private void init(List<GetUserDetails.ImagesFileEntity> imagesFileEntityList) {
        if (imagesFileEntityList.size() > 0) {
            text.setText("1" +" of " + imagesFileEntityList.size());
        } else {
            text.setText("");
        }

        for (int i = 0; i < imagesFileEntityList.size(); i++)
            ImagesArray.add(imagesFileEntityList.get(i).getImage());
        mPager.setAdapter(new SlidingImage_Adapter(this, ImagesArray));
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicatorUserDetails);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = imagesFileEntityList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }

            if (imagesFileEntityList.size() > 0) {
                text.setText(currentPage + 1 + " of " + imagesFileEntityList.size());
            } else {
                text.setText("");
            }
            mPager.setCurrentItem(currentPage++, true);
        };


        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                if (imagesFileEntityList.size() > 0) {
                    text.setText(currentPage + 1 + " of " + imagesFileEntityList.size());
                } else {
                    text.setText("");
                }
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });

    }

    private void getLikeApiCallingPro(String userId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall = api.getLikePost("professional", ModelManager.modelManager().getCurrentUser().getId(), userId, isLike);
        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 200) {

                            Toaster.toast(response.body().getResponseMessage());
                            if (response.body().getLiked() == true) {
                                ivLikeUser.setImageResource(R.drawable.like_icon_new);
                                ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                isLike = false;

                            } else {
                                ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                                ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                isLike = true;

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


    private void getLikeApiCallingUser(String userId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall = api.getLikePost("professional", ModelManager.modelManager().getCurrentUser().getId(), dataEntity.getId(), isLike);
        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 200) {
                            Toaster.toast(response.body().getResponseMessage());
                            if (response.body().getLiked() == true) {
                                ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                ivLikeUser.setImageResource(R.drawable.like_icon_new);
                                ivLikeProperty.setImageResource(R.drawable.like_icon_new);
                                isLike = false;

                            } else {
                                ivLikeUser.setImageResource(R.drawable.unselcted_heart);
                                ivLikeProperty.setImageResource(R.drawable.unselcted_heart);
                                isLike = true;
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
