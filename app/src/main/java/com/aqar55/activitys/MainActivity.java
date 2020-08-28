package com.aqar55.activitys;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.aqar55.BuildConfig;
import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.adapters.CategoryAdapter;
import com.aqar55.adapters.RentRecyclerMainAdapter;
import com.aqar55.adapters.SaleMainCategoryListAdapter;
import com.aqar55.constant.Constants;
import com.aqar55.fragments.Businesstab_Main_Fragment;
import com.aqar55.fragments.Chat_Main_Fragment;
import com.aqar55.fragments.MarketingPopupSheet;
import com.aqar55.fragments.Menu_Main_Fragment;
import com.aqar55.fragments.Professionaltab_Main_Fragment;
import com.aqar55.fragments.Rent_Main_Fragment;
import com.aqar55.fragments.Saletab_main_fragment;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.MapWrapperLayout;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.OnInfoWindowElemTouchListener;
import com.aqar55.helper.Toaster;
import com.aqar55.intefaces.SetIconInMain;
import com.aqar55.model.CommonModel;
import com.aqar55.model.GetCategoryList;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.model.ProfessionalDataResponse;
import com.aqar55.model.ProfessionalResponse;
import com.aqar55.model.SerachSaleOrRent;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.Animation;
import com.aqar55.utill.GPSTracker;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        SetIconInMain,
        FragmentManager.OnBackStackChangedListener,
        CategoryAdapter.OnCategoryClickListener, SaleMainCategoryListAdapter.OnSaleCategoryClickListener,
        RentRecyclerMainAdapter.OnRentClickListener {
    private CategoryAdapter categoryAdapter;
    private RentRecyclerMainAdapter rentRecyclerMainAdapter;
    private Marker MarkerForWinfoWindow;
    private String PropertyIdForSale = "";
    private String ProfUserId = "";
    private Marker MarkerForBusinessInfoWindow;
    private String BusinessUserId = "";
    private Marker MarkerForRentInfoWindow;
    private String PropertyIdForRent = "";
    private String businessUserId;
    private String searchByCat="";
    private String searchByPro="all";
    private String allData="allData";
    private String getFullName="";
    private  String imagePath="";
    private String description="";
    boolean not_first_time_showing_info_windowBusi;
    boolean not_first_time_showing_info_windowPro;
    boolean not_first_time_showing_info_windowSale;
    boolean not_first_time_showing_info_windowRent;
    @BindView(R.id.setting_icon_main)
    ImageView setting_icon_main;
    @BindView(R.id.post_property_main_activty_add_icon)
    ImageView post_property_main_activty_add_icon;

    @BindView(R.id.list_nav)
    LinearLayout list_nav;

    @BindView(R.id.chat_nav)
    LinearLayout chat_nav;

    @BindView(R.id.notification_nav)
    LinearLayout notification_nav;

    @BindView(R.id.more_nav)
    LinearLayout more_nav;

    @BindView(R.id.all_main_text)
    LinearLayout all_main_text;

    @BindView(R.id.sale_main_text)
    LinearLayout sale_main_text;

    @BindView(R.id.rent_main_text)
    LinearLayout rent_main_text;

    @BindView(R.id.professional_main_text)
    LinearLayout professional_main_text;

    @BindView(R.id.business_main_text)
    LinearLayout business_main_text;

    @BindView(R.id.layout_dish_icon_location)
    LinearLayout layout_dish_icon_location;

    @BindView(R.id.currentlocationicon)
    LinearLayout currentlocationicon;

    @BindView(R.id.salemainrecycler)
    RecyclerView salemainrecycler;

    @BindView(R.id.rentrecyclermainrent)
    RecyclerView rentrecyclermainrent;

    @BindView(R.id.searchedittext)
    TextView searchedittext;

    @BindView(R.id.list_icon)
    ImageView list_icon;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.main_sale_button_layout)
    RelativeLayout main_sale_button_layout;

    @BindView(R.id.main_rent_button_layout)
    RelativeLayout main_rent_button_layout;

    @BindView(R.id.main_professioanl_button_layout)
    RelativeLayout main_professioanl_button_layout;

    @BindView(R.id.sale_view)
    View sale_view;

    @BindView(R.id.view_business)
    View view_business;

    @BindView(R.id.view_rent)
    View view_rent;

    @BindView(R.id.view_professional)
    View view_professional;

    @BindView(R.id.all_view)
    View all_view;

    @BindView(R.id.current_location_icon_layout)
    RelativeLayout current_location_icon_layout;

    @BindView(R.id.recyclerCategoryMainAct)
    RecyclerView recyclerCategoryMainAct;

    @BindView(R.id.tv_total_property)
    TextView tvTotalProperty;

    private String type = "";
    private String latitudeFromSearch="";
    private String longitudeFromSearch="";
    private GPSTracker gpsTracker;
    private android.location.Location location;
    double latitude, longitude;
    private ViewGroup infoWindow;
    private OnInfoWindowElemTouchListener infoItemListener;
    private ImageView ivChatting, ivCall;
    private MapWrapperLayout mapWrapperLayout;
    TextView tvProfessionalName;
    TextView tvProfCategory;
    TextView tvProfID;
    TextView tvListingProf;
    ImageView ivPropertyImage;
    ImageView ivCallProf;
    ImageView ivShare;
    ImageView ivLike;
    private boolean isSaleFilter;
    private boolean isRentFilter;
    private boolean isProfFilter;
    private boolean isBusinessFilter;
    protected GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean a = false;
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private SaleMainCategoryListAdapter saleMainCategoryListAdapter;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    private List_Main_Activity list_main_fragment;
    private String string;
    private Response<GetPropertyListing> responseSale;
    private Response<GetPropertyListing> responseAllAPi;
    private String mNumber = "";
    private HashMap<String, Object> hashMapData;
    private String mPropertyId;
    private String mUserId;
    private String mType;
    private String mLikeStatus;

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name, String heading, int color) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_marker, null);
        TextView txt_name = marker.findViewById(R.id.tvMarker);
        txt_name.setText(_name);
        TextView txt_name_heading = marker.findViewById(R.id.heading);
        txt_name_heading.setText(heading);
        txt_name_heading.setTextColor(context.getResources().getColor(color));
        txt_name.setBackground(context.getResources().getDrawable(resource));
        txt_name.setTextColor(context.getResources().getColor(color));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);
        return bitmap;
    }



    private void hitUpdatePropertyDays() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<CommonModel> call = api.getUpdatePropertyDays();
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }


    public static Intent getIntent(Context context, HashMap<String, Object> hashMap) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("Data", hashMap);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMap();
        hitUpdatePropertyDays();
        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);
        searchedittext.setOnClickListener(this);
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_professional_map, null);
        this.ivCallProf = infoWindow.findViewById(R.id.ivCallProf);
        this.ivChatting = (ImageView) infoWindow.findViewById(R.id.ivChattingProg);
        this.ivShare = (ImageView) infoWindow.findViewById(R.id.imageView5);
        this.ivLike = (ImageView) infoWindow.findViewById(R.id.imageView6);
        tvProfessionalName = infoWindow.findViewById(R.id.tvProfessionalName);
        tvProfCategory = infoWindow.findViewById(R.id.tvProfCategory);
        tvProfID = infoWindow.findViewById(R.id.tvProfID);
        tvListingProf = infoWindow.findViewById(R.id.tvListingProf);
        ivPropertyImage = infoWindow.findViewById(R.id.ivPropertyImage);

        this.infoItemListener = new OnInfoWindowElemTouchListener(ivCallProf) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {

                if(mNumber!=null)
                calling(mNumber);
            }
        };
        this.ivCallProf.setOnTouchListener(infoItemListener);

        this.infoItemListener = new OnInfoWindowElemTouchListener(ivChatting) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                if (ModelManager.modelManager().getCurrentUser() != null)
                    onChat();
                else
                    startActivity(Login_Signup_Button_Activity.getIntent(getApplicationContext()));
            }
        };
        this.ivChatting.setOnTouchListener(infoItemListener);

        this.infoItemListener = new OnInfoWindowElemTouchListener(ivLike) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {

                if (ModelManager.modelManager().getCurrentUser() != null)
                    onLikeClick();
                else
                    startActivity(Login_Signup_Button_Activity.getIntent(getApplicationContext()));
            }
        };
        this.ivLike.setOnTouchListener(infoItemListener);

        this.infoItemListener = new OnInfoWindowElemTouchListener(ivShare) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                share();
            }
        };
        this.ivShare.setOnTouchListener(infoItemListener);

        gpsTracker = new GPSTracker(this);
        if (gpsTracker != null) {
            if (gpsTracker.canGetLocation()) {
                location = gpsTracker.getLocation();
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            } else {
                Toast.makeText(this, "Can't Fetch Location", Toast.LENGTH_SHORT).show();
            }
        }

        Menu_Main_Fragment menu_main_fragment = new Menu_Main_Fragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.main_activity_container, menu_main_fragment).commit();

        setting_icon_main.setOnClickListener(this);
        post_property_main_activty_add_icon.setOnClickListener(this);
        //Bottom Nav Item Click
        list_nav.setOnClickListener(this);
        chat_nav.setOnClickListener(this);
        notification_nav.setOnClickListener(this);
        more_nav.setOnClickListener(this);
        //scrollview item
        all_main_text.setOnClickListener(this);
        sale_main_text.setOnClickListener(this);
        rent_main_text.setOnClickListener(this);
        professional_main_text.setOnClickListener(this);
        business_main_text.setOnClickListener(this);
        layout_dish_icon_location.setOnClickListener(this);
        currentlocationicon.setOnClickListener(this);
        getLocationPermission();
    }

    //share method calling here
    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getString(R.string.body_foodie);
        String shareSub = getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    //calling method calling here
    private void calling(String mNumber) {
        if (mNumber.isEmpty()) {
            Toaster.toast("Please enter mobile number");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mNumber, null));
        startActivity(intent);
    }
    private void getRentDataCategoryApi(String categoty) {
        searchByCat=categoty;
        searchByPro="rent";
        allData="";
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
             getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("rent", categoty,ModelManager.modelManager().getCurrentUser().getId());

        }else {
             getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("rent",categoty);

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                    if (response.body().getData() != null) {
                        mMap.clear();
                        MyDialog.getInstance(MainActivity.this).hideDialog();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                responseSale = response;
                                drawMarkerRent(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()), response.body().getData().get(i).getRentTime(), response.body().getData().get(i).getTotalPriceRent(),
                                        response.body().getData().get(i).getId(), response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTitle(), response.body().getData().get(i).getProfileImage(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getUserid(),
                                        response.body().getData().get(i).getMobileNumber(), response.body().getData().get(i).getType(),
                                        response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getCurrency());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
            }
        });
    }

    private void getDataFromSaleCategoryApi(String categoty) {
        searchByCat=categoty;
        searchByPro="sale";
        allData="";
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("sale", categoty,ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("sale", categoty);

        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());

                        mMap.clear();
                        MyDialog.getInstance(MainActivity.this).hideDialog();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                responseSale = response;
                                drawMarkerSale(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()),
                                        response.body().getData().get(i).getTotalPrice(),
                                        response.body().getData().get(i).getCurrency(),
                                        response.body().getData().get(i).getId(),
                                        response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTitle(),
                                        response.body().getData().get(i).getProfileImage(),
                                        response.body().getData().get(i).getProfessionalid(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(),
                                        response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getType(),
                                        response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalpricesale());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
            }
        });
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                LayoutInflater factory = LayoutInflater.from(this);
                final View deleteDialogView = factory.inflate(R.layout.location_allow_dialog, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.allow_location).setOnClickListener(v -> {
                    //your business logic
                    ActivityCompat.requestPermissions(MainActivity.this,
                            permissions,
                            LOCATION_PERMISSION_REQUEST_CODE);
                    deleteDialog.dismiss();
                });
                deleteDialogView.findViewById(R.id.dont_allow_location).setOnClickListener(v -> deleteDialog.dismiss());
                deleteDialog.show();
            }
        } else {
            LayoutInflater factory = LayoutInflater.from(this);
            final View deleteDialogView = factory.inflate(R.layout.location_allow_dialog, null);
            final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
            deleteDialog.setView(deleteDialogView);
            deleteDialogView.findViewById(R.id.allow_location).setOnClickListener(v -> {
                //your business logic
                ActivityCompat.requestPermissions(MainActivity.this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                deleteDialog.dismiss();
            });
            deleteDialogView.findViewById(R.id.dont_allow_location).setOnClickListener(v -> deleteDialog.dismiss());
            deleteDialog.show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {

            mMap = googleMap;
            GPSTracker gpsTracker = new GPSTracker(MainActivity.this);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            if (getIntent() != null) {
                hashMapData = (HashMap<String, Object>) getIntent().getSerializableExtra("Data");
                if (hashMapData != null) {
                    BaseManager.saveDataIntoPreferences(hashMapData,"mapData");

                    if (hashMapData.get("main_act").toString().equalsIgnoreCase("sale")) {
                        isSaleFilter = true;
                        showSaleData();
                        searchByPro=hashMapData.get("main_act").toString();
                        allData="";
                    } else if (hashMapData.get("main_act").toString().equalsIgnoreCase("rent")) {
                        isRentFilter = true;
                        searchByPro=hashMapData.get("main_act").toString();
                        showRentData();
                        allData="";
                    } else if (hashMapData.get("main_act").toString().equalsIgnoreCase("prof")) {
                        hitProfessionalDataApiFromFilter();
                        searchByPro="professional";
                        allData="";

                    } else if (hashMapData.get("main_act").toString().equalsIgnoreCase("business")) {
                        hitBusinessDataApiFromFilter();
                        searchByPro=hashMapData.get("main_act").toString();
                        allData="";

                    }
                } else {
                    getAllData();
                }

            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_icon_main:
                startActivity(new Intent(this, Setting_Activity.class));
                break;
            case R.id.post_property_main_activty_add_icon:
                if (ModelManager.modelManager().getCurrentUser() != null) {
                    startActivity(new Intent(this, Post_Property_Main.class));
                } else {
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                }
                break;
            case R.id.list_nav:
                if (!CommonClass.getPreferencesBoolean(MainActivity.this, "popup")) {
                    CommonClass.savePreferencesBoolean(MainActivity.this, "popup", true);
                    MarketingPopupSheet marketingPopupSheet = new MarketingPopupSheet();
                    marketingPopupSheet.show(getSupportFragmentManager(), marketingPopupSheet.getTag());
                } else {
                    if (!a) {
                        list_main_fragment = new List_Main_Activity();
                        Bundle bundle = new Bundle();
                        bundle.putString("category",searchByCat);
                        bundle.putString("fromActivity",searchByPro);
                        bundle.putString("lat",latitudeFromSearch);
                        bundle.putString("long",longitudeFromSearch);
                        bundle.putString("allData", allData);
                        list_main_fragment.setArguments(bundle);

                        fragmentManager.beginTransaction().replace(R.id.main_activity_list_container, list_main_fragment, List_Main_Activity.class.getName()).addToBackStack(List_Main_Activity.class.getName()).commit();
                        list_icon.setImageResource(R.drawable.maptext);
                        a = true;
                    } else if (a) {
                        clearBackStack();
                        a = false;
                        list_icon.setImageResource(R.drawable.list_button);
                    }
                }
                break;
            case R.id.chat_nav:

                if (ModelManager.modelManager().getCurrentUser() != null) {
                    startActivity(Chat_Main_Fragment.getIntent(this, ModelManager.modelManager().getCurrentUser().getId()));

                } else {
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                    finish();
                }
                break;
            case R.id.notification_nav:
                if (ModelManager.modelManager().getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, Notification_Activity.class));

                } else {
                    startActivity(Login_Signup_Button_Activity.getIntent(this));
                    finish();
                }
                break;
            case R.id.more_nav:
                Menu_Main_Fragment menu_main_fragment = new Menu_Main_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_container, menu_main_fragment).commit();
                drawer_layout.openDrawer(Gravity.START);
                break;
            case R.id.all_main_text:
                getAllData();
                type = "all";
                searchByCat="";
                latitudeFromSearch="";
                longitudeFromSearch="";
                allData="allData";
                searchByPro=type;
                break;
            case R.id.sale_main_text:
                isSaleFilter = false;
                showSaleData();
                type = "sale";
                searchByCat="";
                latitudeFromSearch="";
                longitudeFromSearch="";
                allData="allData";
                searchByPro=type;
                break;
            case R.id.rent_main_text:
                isRentFilter = false;
                showRentData();
                type = "rent";
                searchByCat="";
                latitudeFromSearch="";
                longitudeFromSearch="";
                allData="allData";
                searchByPro=type;
                break;
            case R.id.professional_main_text:
                /// showProfessionalData();
                type = "professional";
                searchByCat="";
                latitudeFromSearch="";
                longitudeFromSearch="";
                allData="allData";
                searchByPro=type;
                hitProfessionalDataApi();

                break;
            case R.id.business_main_text:
                hitBusinessDataApi();
                type = "business";
                searchByCat="";
                latitudeFromSearch="";
                longitudeFromSearch="";
                allData="allData";
                searchByPro=type;
                break;
            case R.id.layout_dish_icon_location:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                moveCurrentLocation();
                break;
            case R.id.currentlocationicon:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                moveCurrentLocation();
                break;

            case R.id.searchedittext:
                startActivityForResult(AddressPicker.getIntent(this), 101);
                break;
        }
    }


    ////move map at curent location//////
    private void moveCurrentLocation() {
        GPSTracker gpsTracker=new GPSTracker(MainActivity.this);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
    }


    private void getSerchDataFromApi(String type, String lat, String lng) {
        if (type.isEmpty())
            type = "all";
        searchByPro=type;

        if(type.equalsIgnoreCase("all")){
            type="";
        }
        MyDialog.getInstance(this).showDialog();
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        if(ModelManager.modelManager().getCurrentUser()!=null){
             getPropertyListingCall = api.getSearchByGoogle(type, lat, lng,ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getSearchByGoogleWithoutLogin(type, lat, lng);

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                    tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                    if (response.body().getResponseCode()==200) {
                        responseAllAPi=response;
                        if(response.body().getData()!=null)
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                searchedittext.setText("");
                                searchedittext.clearFocus();
                                drawMarker(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()),
                                        response.body().getData().get(i).getType(),
                                        response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTotalPrice(),
                                        response.body().getData().get(i).getCurrency(),
                                        response.body().getData().get(i).getRentTime(),
                                        response.body().getData().get(i).getTotalPriceRent(),
                                        response.body().getData().get(i).getId(),
                                        response.body().getData().get(i).getCategory(), response.body().getData().get(i).getTitle(),
                                        response.body().getData().get(i).getProfileImage(), response.body().getData().get(i).getUserid(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalpricesale());
                            }
                        }
                    }
                } else {
                    MyDialog.getInstance(MainActivity.this).hideDialog();
                   // mMap.clear();
                    Toast.makeText(MainActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
            }
        });
    }

    private void showSaleData() {
        getCateoryList();

        main_sale_button_layout.setVisibility(View.VISIBLE);
        main_rent_button_layout.setVisibility(View.GONE);
        main_professioanl_button_layout.setVisibility(View.GONE);
        recyclerCategoryMainAct.setVisibility(View.GONE);
        Animation.RightToLeft_And_Show(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_business, business_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);
        if (isSaleFilter) {
            getSaleDataApiFromFilter();
        } else {
            getSaleDataFromApi();
        }
    }
    private void getCateoryList() {
        MyDialog.getInstance(MainActivity.this).showDialog(MainActivity.this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<GetCategoryList> getCategoryListCall = api.getPropertyCategory();
        getCategoryListCall.enqueue(new Callback<GetCategoryList>() {
            @Override
            public void onResponse(@NonNull Call<GetCategoryList> call, @NonNull Response<GetCategoryList> response) {
                if (response.isSuccessful()) {
                    if (response != null) {

                        if (response.body().getResponseCode() == 200) {
                            MyDialog.getInstance(MainActivity.this).hideDialog();

                            List<GetCategoryList.Data> data = response.body().getData();
                            salemainrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                            saleMainCategoryListAdapter = new SaleMainCategoryListAdapter(MainActivity.this, data, MainActivity.this);
                            salemainrecycler.setAdapter(saleMainCategoryListAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoryList> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void getSaleDataFromApi() {
        mMap.clear();
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        //  Call<GetPropertyListing> getPropertyListingCall = api.getPropertyList("sale");
        Call<GetPropertyListing> getPropertyListingCall;

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("sale", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("sale");
        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    GetPropertyListing getPropertyListing = response.body();
                    List<GetPropertyListing.Data> dataList = getPropertyListing.getData();
                    if (dataList != null) {  tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {



                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {

                                if(response.body().getData().get(i).getImagesfile().size()>0&&response.body().getData().get(i).getImagesfile()!=null){
                                     imagePath=response.body().getData().get(i).getImagesfile().get(0).getImage();
                                }else{
                                    imagePath=response.body().getData().get(i).getProfileImage();

                                }
                                drawMarkerSale(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()),
                                        response.body().getData().get(i).getTotalPrice(),
                                        response.body().getData().get(i).getCurrency(), response.body().getData().get(i).getId(),
                                        response.body().getData().get(i).getCategory(), response.body().getData().get(i).getTitle(),
                                        imagePath, response.body().getData().get(i).getUserid(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getType(), response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalpricesale());
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void getSaleDataApiFromFilter() {
        mMap.clear();
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<SerachSaleOrRent> getPropertyListingCall = api.getSearchSaleOrRent("sale", hashMapData.get("propertyId").toString(), hashMapData.get("category").toString(), hashMapData.get("purpose").toString(), hashMapData.get("available").toString(), hashMapData.get("bedrooms").toString(), hashMapData.get("bathrooms").toString(), hashMapData.get("kitchens").toString(), "", "",
                "", "", "", hashMapData.get("yearBuilt").toString(), hashMapData.get("balcony").toString(), hashMapData.get("garden").toString(), hashMapData.get("parking").toString(), hashMapData.get("modularKitchen").toString(), hashMapData.get("photos").toString(), hashMapData.get("videos").toString(),
                hashMapData.get("plotSizeMin").toString(),hashMapData.get("plotSizeMax").toString(),"",hashMapData.get("store").toString(),hashMapData.get("lift").toString(),hashMapData.get("duplex").toString(),hashMapData.get("furnished").toString(),hashMapData.get("airConditioning").toString());
        getPropertyListingCall.enqueue(new Callback<SerachSaleOrRent>() {
            @Override
            public void onResponse(@NonNull Call<SerachSaleOrRent> call, @NonNull Response<SerachSaleOrRent> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    SerachSaleOrRent getPropertyListing = response.body();
                    List<SerachSaleOrRent.Data> dataList = getPropertyListing.getData();
                    if (dataList != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                if(response.body().getData().get(i).getImagesfile().size()>0&&response.body().getData().get(i).getImagesfile()!=null){
                                    imagePath=response.body().getData().get(i).getImagesfile().get(0).getImage();
                                }else {
                                    imagePath=response.body().getData().get(i).getProfileImage();

                                }
                                drawMarkerSale(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()),
                                        response.body().getData().get(i).getPlotsize(),
                                        response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getId(),
                                        response.body().getData().get(i).getCategory(), response.body().getData().get(i).getTitle(),
                                        imagePath, response.body().getData().get(i).getUserid(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(),
                                        response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getType(), response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalpricesale());
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SerachSaleOrRent> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void showRentData() {
        getCategoryDataRent();
        main_rent_button_layout.setVisibility(View.VISIBLE);
        main_sale_button_layout.setVisibility(View.GONE);
        main_professioanl_button_layout.setVisibility(View.GONE);
        recyclerCategoryMainAct.setVisibility(View.GONE);
        all_view.setVisibility(View.GONE);
        sale_view.setVisibility(View.GONE);
        view_professional.setVisibility(View.GONE);
        view_business.setVisibility(View.GONE);
        view_rent.setVisibility(View.VISIBLE);
        Animation.RightToLeft_And_Show(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_business, business_main_text, Constants.animDuration);
        if (isRentFilter) {
            getRentDataApiFromFilter();
        } else {
            getRentDataFromApi();
        }

    }

    private void getCategoryDataRent() {
        MyDialog.getInstance(MainActivity.this).showDialog(MainActivity.this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<GetCategoryList> getCategoryListCall = api.getPropertyCategory();
        getCategoryListCall.enqueue(new Callback<GetCategoryList>() {
            @Override
            public void onResponse(@NonNull Call<GetCategoryList> call, @NonNull Response<GetCategoryList> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        if (response.body().getResponseCode() == 200) {
                            MyDialog.getInstance(MainActivity.this).hideDialog();
                            rentrecyclermainrent.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                            rentRecyclerMainAdapter = new RentRecyclerMainAdapter(MainActivity.this, response.body().getData(), MainActivity.this);
                            rentrecyclermainrent.setAdapter(rentRecyclerMainAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCategoryList> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void getRentDataFromApi() {
        mMap.clear();
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        // Call<GetPropertyListing> getPropertyListingCall = api.getPropertyList("rent");
        Call<GetPropertyListing> getPropertyListingCall;

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("rent", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("rent");
        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    GetPropertyListing getPropertyListing = response.body();
                    List<GetPropertyListing.Data> dataList = getPropertyListing.getData();
                    if (dataList != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                if(response.body().getData().get(i).getImagesfile().size()>0&&response.body().getData().get(i).getImagesfile()!=null){
                                    imagePath=response.body().getData().get(i).getImagesfile().get(0).getImage();
                                }else {
                                    imagePath=response.body().getData().get(i).getProfileImage();

                                }


                                drawMarkerRent(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()), response.body().getData().get(i).getRentTime(), response.body().getData().get(i).getTotalPrice(),
                                        response.body().getData().get(i).getId(), response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTitle(), imagePath,
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getProfessionalUserId(), response.body().getData()
                                                .get(i).getMobileNumber(), response.body().getData().get(i).getType(),
                                        response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getTotalPrice()+" "+ response.body().getData().get(i).getCurrency());
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });


    }

    private void getRentDataApiFromFilter() {
        mMap.clear();
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<SerachSaleOrRent> getPropertyListingCall = api.getSearchSaleOrRent("rent", hashMapData.get("propertyId").toString(), hashMapData.get("category").toString(), hashMapData.get("purpose").toString(), hashMapData.get("available").toString(), hashMapData.get("bedrooms").toString(), hashMapData.get("bathrooms").toString(), hashMapData.get("kitchens").toString(), "", "",
                "", "", hashMapData.get("totalPriceMin").toString(), hashMapData.get("yearBuilt").toString(), hashMapData.get("balcony").toString(), hashMapData.get("garden").toString(), hashMapData.get("parking").toString(), hashMapData.get("modularKitchen").toString(), hashMapData.get("photos").toString(), hashMapData.get("videos").toString(),"",
                "",hashMapData.get("renTime").toString(),hashMapData.get("store").toString(),hashMapData.get("lift").toString(),
                hashMapData.get("duplex").toString(),hashMapData.get("furnished").toString(),hashMapData.get("airConditioning").toString());
        getPropertyListingCall.enqueue(new Callback<SerachSaleOrRent>() {
            @Override
            public void onResponse(@NonNull Call<SerachSaleOrRent> call, @NonNull Response<SerachSaleOrRent> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    SerachSaleOrRent getPropertyListing = response.body();
                    List<SerachSaleOrRent.Data> dataList = getPropertyListing.getData();
                    if (dataList != null && !dataList.isEmpty()) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                if(response.body().getData().get(i).getImagesfile().size()>0&&response.body().getData().get(i).getImagesfile()!=null){
                                    imagePath=response.body().getData().get(i).getImagesfile().get(0).getImage();
                                }else {
                                    imagePath=response.body().getData().get(i).getProfileImage();

                                }


                                drawMarkerRent(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()), response.body().getData().get(i).getRentTime(), response.body().getData().get(i).getTotalPriceRent(),
                                        response.body().getData().get(i).getId(), response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTitle(),imagePath,
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getProfessionalUserId(),
                                        response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getType(), response.body().getData().get(i).getLikedStatus(),
                                        response.body().getData().get(i).getTotalPrice()+" "+response.body().getData().get(i).getCurrency());
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SerachSaleOrRent> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });


    }

    private void getAllData() {

        current_location_icon_layout.setVisibility(View.VISIBLE);
        main_sale_button_layout.setVisibility(View.GONE);
        main_rent_button_layout.setVisibility(View.GONE);
        main_professioanl_button_layout.setVisibility(View.GONE);
        recyclerCategoryMainAct.setVisibility(View.GONE);
        getAllDataFromApi();

        Animation.RightToLeft_And_Show(all_view, all_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_business, business_main_text, Constants.animDuration);
    }

    private void getAllDataFromApi() {
        if (mMap != null)
            mMap.clear();
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("");
        }

        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {



                            double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                            double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                            String subCategory = response.body().getData().get(i).getSubCategory();
                            String professionalId = response.body().getData().get(i).getUserid();
                            String professionalUserId = response.body().getData().get(i).getProfessionalid();
                            String businessUserId = response.body().getData().get(i).getBusinessid();
                            String category = response.body().getData().get(i).getCategory();
                            String name = response.body().getData().get(i).getFullName();
                            String profileImage = response.body().getData().get(i).getProfileImage();
                            String propertyId = response.body().getData().get(i).getId();
                            String likeStatus = response.body().getData().get(i).getLikedStatus();
                            String mobileNumber = response.body().getData().get(i).getMobileNumber();
                            String type = response.body().getData().get(i).getType();
                            String desc = response.body().getData().get(i).getDescription();
                            double markerDistance = distance(latitude, longitude, lat, lng);
                            if (type.equalsIgnoreCase("professional")) {
                                drawMarkerProfessional(lat, lng, subCategory, professionalId, category, name, profileImage, propertyId, (int) markerDistance, desc, mobileNumber, likeStatus,type,professionalUserId);

                            } else if (type.equalsIgnoreCase("business")) {
                                drawMarkerBusiness(lat, lng, subCategory, professionalId, category, name, profileImage, propertyId, (int) markerDistance, desc, mobileNumber, likeStatus,type,businessUserId);

                            } else {

                                if(response.body().getData().get(i).getImagesfile()!=null&&response.body().getData().get(i).getImagesfile().size()>0){
                                    if(response.body().getData().get(i).getImagesfile().size()>0&&response.body().getData().get(i).getImagesfile()!=null){
                                        imagePath=response.body().getData().get(i).getImagesfile().get(0).getImage();
                                    }else {
                                        imagePath=response.body().getData().get(i).getProfileImage();
                                    }

                                }else {

                                    imagePath=response.body().getData().get(i).getProfileImage();
                                }


                                drawMarker(Double.parseDouble(response.body().getData().get(i).getLat()),
                                        Double.parseDouble(response.body().getData().get(i).getLongg()),
                                        response.body().getData().get(i).getType(),
                                        response.body().getData().get(i).getCategory(),
                                        response.body().getData().get(i).getTotalPrice(),
                                        response.body().getData().get(i).getCurrency(),
                                        response.body().getData().get(i).getRentTime(),
                                        response.body().getData().get(i).getTotalPrice(), response.body().getData().get(i).getId(),
                                        response.body().getData().get(i).getCategory(), response.body().getData().get(i).getTitle(),
                                        imagePath, response.body().getData().get(i).getUserid(),
                                        response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit(), response.body().getData().get(i).getMobileNumber(),
                                        response.body().getData().get(i).getLikedStatus(),response.body().getData().get(i).getTotalPrice()+" "+ response.body().getData().get(i).getCurrency());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }


    private void drawMarker(double lat, double longg, String type, String category, String plotsize, String plotsizeunit, String rentTime, String totalPriceRent
            , String id, String Category, String name, String image, String professionalUSerId, String details,
                            String mobileNumber, String likeStatus,String currency) {


        if (type.equalsIgnoreCase("sale")) {
            drawMarkerSale(lat, longg, plotsize, plotsizeunit, id, Category, name, image,professionalUSerId, details, mobileNumber, type, likeStatus,currency);
        }

        if (type.equalsIgnoreCase("rent")) {
            drawMarkerRent(lat, longg, rentTime, totalPriceRent, id, Category, name, image, details, professionalUSerId, mobileNumber, type, likeStatus,currency);
        }


    }

    private void drawMarkerSale(double lat, double longg, String plotsize, String plotsizeunit, String propertyId, String category,
                                String saleName, String image, String professionalUserID, String detail,
                                String mobileNumber, String type, String likeStatus,String totalPriceSale) {

        LatLng latLng = new LatLng(lat, longg);

        MarkerForWinfoWindow = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(createCustomMarker(this, R.drawable.red_two, plotsizeunit +" " +  plotsize, "", R.color.dark_red))));


        GetPropertyListing.Data forInfoWindowsData = new GetPropertyListing.Data();
        forInfoWindowsData.setTitle(saleName);
        forInfoWindowsData.setProfileImage(image);
        forInfoWindowsData.setCategory(category);
        forInfoWindowsData.setId(propertyId);
        forInfoWindowsData.setDescription(detail);
        forInfoWindowsData.setMobileNumber(mobileNumber);
        forInfoWindowsData.setUserid(professionalUserID);
        forInfoWindowsData.setType(type);
        forInfoWindowsData.setLikedStatus(likeStatus);

        forInfoWindowsData.setTotalpricesale(totalPriceSale);

        MarkerForWinfoWindow.setTag(forInfoWindowsData);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                GetPropertyListing.Data forInfoWindowsData = (GetPropertyListing.Data) marker.getTag();
                tvProfessionalName.setText(forInfoWindowsData.getTitle());
                tvProfCategory.setText(forInfoWindowsData.getCategory());
                if(forInfoWindowsData.getType().equalsIgnoreCase("rent")){
                    tvProfID.setText(forInfoWindowsData.getRentTime());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                }else if(forInfoWindowsData.getType().equalsIgnoreCase("sale")){
                    tvProfID.setText(forInfoWindowsData.getTotalpricesale());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                }else {

                    tvProfID.setText(forInfoWindowsData.getId());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                }

                mPropertyId = forInfoWindowsData.getId();
                PropertyIdForSale = forInfoWindowsData.getId();

                mLikeStatus = forInfoWindowsData.getLikedStatus();
                String imagePath = forInfoWindowsData.getProfileImage();
                mNumber = forInfoWindowsData.getMobileNumber();
                mType = forInfoWindowsData.getType();
                mUserId = forInfoWindowsData.getUserid();
                mPropertyId = forInfoWindowsData.getId();

                if (forInfoWindowsData.getLikedStatus() != null) {
                    if (forInfoWindowsData.getLikedStatus().equalsIgnoreCase("yes")) {
                        ivLike.setImageResource(R.drawable.like_icon_new);

                    } else {
                        ivLike.setImageResource(R.drawable.unselcted_heart);
                    }
                }
                if (imagePath != null && !imagePath.isEmpty())
                {
                    if (not_first_time_showing_info_windowSale) {
                        Picasso.get().load(imagePath).into(ivPropertyImage);

                    } else {
                        not_first_time_showing_info_windowSale = true;
                        Picasso.get().load(imagePath).into(ivPropertyImage, new InfoWindowRefresher(marker));
                    }
                }else {
                    Picasso.get().load(R.drawable.default_propertyimage).into(ivPropertyImage);

                }
                   // Picasso.get().load(imagePath).into(ivPropertyImage);
                ivCallProf.setOnClickListener(MainActivity.this);
                ivShare.setOnClickListener(MainActivity.this);
                ivChatting.setOnClickListener(MainActivity.this);
                ivLike.setOnClickListener(MainActivity.this);
                infoItemListener.setMarker(marker);
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });


        mMap.setOnInfoWindowClickListener(marker -> {
            GetPropertyListing.Data forInfoWindowsAll = (GetPropertyListing.Data) marker.getTag();
            if(forInfoWindowsAll.getType().equalsIgnoreCase("professional")){
                Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
                intent.putExtra("_userId", PropertyIdForSale);
                intent.putExtra("info_window_type", "professional");
                intent.putExtra("businessUserId",mUserId);
                startActivity(intent);
            }else if(forInfoWindowsAll.getType().equalsIgnoreCase("business")){
                Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
                intent.putExtra("_userId", PropertyIdForSale);
                intent.putExtra("info_window_type", "business");
                intent.putExtra("businessUserId",mUserId);
                startActivity(intent);
            } else if(forInfoWindowsData.getType().equalsIgnoreCase("rent")){
                if (professionalUserID != null) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForSale);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForSale);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                }
            }else {
                if (professionalUserID != null) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForSale);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForSale);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                }
            }
        });


    }

    private void drawMarkerRent(double lat, double longg, String renttime, String rentPrice,
                                String propertyId, String category, String rentName,
                                String image, String details, String professionalUserID,
                                String mobileNumber, String type, String likeStatus,String currency) {
// Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        LatLng latLng = new LatLng(lat, longg);
        markerOptions.position(latLng);

        if (renttime != null && rentPrice != null && rentName != null) {
            if (renttime.equalsIgnoreCase("daily")) {
                MarkerForRentInfoWindow = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(createCustomMarker(this, R.drawable.green_five,  currency+" " + "\nDaily", "", R.color.green))));
            }
            if (renttime.equalsIgnoreCase("weekly")) {
                MarkerForRentInfoWindow = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(createCustomMarker(this, R.drawable.green_five, currency+" "  + "\nWeekly", "", R.color.green))));
            }
            if (renttime.equalsIgnoreCase("monthly")) {
                MarkerForRentInfoWindow = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(createCustomMarker(this, R.drawable.green_five, currency+" " + "\nMonthly", "", R.color.green))));
            }
            if (renttime.equalsIgnoreCase("yearly")) {
                MarkerForRentInfoWindow = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(createCustomMarker(this, R.drawable.green_five, currency+" " + "\nYearly", "", R.color.green))));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8.0f));
            }
        }

        GetPropertyListing.Data forInfoWindowsData = new GetPropertyListing.Data();
        forInfoWindowsData.setTitle(rentName);
        forInfoWindowsData.setProfileImage(image);
        forInfoWindowsData.setCategory(category);
        forInfoWindowsData.setId(propertyId);
        forInfoWindowsData.setDescription(details);
        forInfoWindowsData.setMobileNumber(mobileNumber);
        forInfoWindowsData.setUserid(professionalUserID);
        forInfoWindowsData.setType(type);

        forInfoWindowsData.setLikedStatus(likeStatus);
        forInfoWindowsData.setRentTime(currency+" "+renttime);

        if(renttime != null && rentPrice != null && rentName != null)
                MarkerForRentInfoWindow.setTag(forInfoWindowsData);

        mMap.setOnInfoWindowClickListener(marker -> {
            GetPropertyListing.Data forInfoWindowsAll = (GetPropertyListing.Data) marker.getTag();
            if(forInfoWindowsAll.getType().equalsIgnoreCase("professional")){
                Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
                intent.putExtra("_userId", PropertyIdForRent);
                intent.putExtra("info_window_type", "professional");
                intent.putExtra("businessUserId",mUserId);
                startActivity(intent);
            }else if(forInfoWindowsAll.getType().equalsIgnoreCase("business")){
                Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
                intent.putExtra("_userId", PropertyIdForRent);
                intent.putExtra("info_window_type", "business");
                intent.putExtra("businessUserId",mUserId);
                startActivity(intent);
            }
            else if(forInfoWindowsData.getType().equalsIgnoreCase("rent")){
                if (professionalUserID != null) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForRent);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForRent);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                }
            }else {
                if (professionalUserID != null) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForRent);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProductDetailSale.class);
                    intent.putExtra("propid", PropertyIdForRent);
                    intent.putExtra("type", "normal");
                    startActivity(intent);
                }

            }

        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                GetPropertyListing.Data forInfoWindowsData = (GetPropertyListing.Data) marker.getTag();

                tvProfessionalName.setText(forInfoWindowsData.getTitle());
                tvProfCategory.setText(forInfoWindowsData.getCategory());
                getFullName=forInfoWindowsData.getTitle();


                if(forInfoWindowsData.getType().equalsIgnoreCase("rent")){
                    tvProfID.setText(forInfoWindowsData.getRentTime());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                    description=forInfoWindowsData.getDescription();
                }else if(forInfoWindowsData.getType().equalsIgnoreCase("sale")){
                    tvProfID.setText(forInfoWindowsData.getTotalpricesale());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                }else {

                        tvProfID.setText(forInfoWindowsData.getId());
                    tvListingProf.setText(forInfoWindowsData.getDescription());
                    description=forInfoWindowsData.getDescription();

                }
                PropertyIdForRent = forInfoWindowsData.getId();
                String imagePath = forInfoWindowsData.getProfileImage();
                mLikeStatus = forInfoWindowsData.getLikedStatus();
                mType = forInfoWindowsData.getType();
                mUserId = forInfoWindowsData.getUserid();
                mPropertyId = forInfoWindowsData.getId();
                mNumber = forInfoWindowsData.getMobileNumber();

                if(forInfoWindowsData.getLikedStatus()!=null){
                    if (forInfoWindowsData.getLikedStatus().equalsIgnoreCase("yes")) {
                        ivLike.setImageResource(R.drawable.like_icon_new);
                    } else {
                        ivLike.setImageResource(R.drawable.unselcted_heart);
                    }
                }
                if (imagePath != null && !imagePath.isEmpty()){
                    if (not_first_time_showing_info_windowRent) {
                        Picasso.get().load(imagePath).into(ivPropertyImage);

                    } else {
                        not_first_time_showing_info_windowRent = true;
                        Picasso.get().load(imagePath).into(ivPropertyImage, new InfoWindowRefresher(marker));
                    }
                }else {
                    Picasso.get().load(R.drawable.default_propertyimage).into(ivPropertyImage);
                }



                   // Picasso.get().load(imagePath).into(ivPropertyImage);


                ivCallProf.setOnClickListener(MainActivity.this);
                ivShare.setOnClickListener(MainActivity.this);
                ivChatting.setOnClickListener(MainActivity.this);
                ivLike.setOnClickListener(MainActivity.this);
                infoItemListener.setMarker(marker);

                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });


    }


    @Override
    public void showIcon() {
        list_icon.setImageResource(R.drawable.list_button);
        a = false;
    }

    public void closeDrawer() {
        drawer_layout.closeDrawer(Gravity.START);
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

            if (fragmentTag.equalsIgnoreCase(List_Main_Activity.class.getName())) {
                fragmentManager.popBackStack();
                list_icon.setImageResource(R.drawable.list_button);
                //string = getBackStackFrag(fragmentManager.getBackStackEntryCount() - 2);
                a = false;
            }

            if (fragmentTag.equalsIgnoreCase(Saletab_main_fragment.class.getName())) {
                fragmentManager.popBackStack();
                list_main_fragment.showIndicator(fragmentTag);
                string = getBackStackFrag(fragmentManager.getBackStackEntryCount() - 2);

                showIndicatorOnbackStack(string);
            }

            if (fragmentTag.equalsIgnoreCase(Rent_Main_Fragment.class.getName())) {
                fragmentManager.popBackStack();
                list_main_fragment.showIndicator(fragmentTag);
                string = getBackStackFrag(fragmentManager.getBackStackEntryCount() - 2);
                showIndicatorOnbackStack(string);
            }
            if (fragmentTag.equalsIgnoreCase(Professionaltab_Main_Fragment.class.getName())) {
                fragmentManager.popBackStack();
                list_main_fragment.showIndicator(fragmentTag);
                string = getBackStackFrag(fragmentManager.getBackStackEntryCount() - 2);
                showIndicatorOnbackStack(string);
            }
            if (fragmentTag.equalsIgnoreCase(Businesstab_Main_Fragment.class.getName())) {

                fragmentManager.popBackStack();
                list_main_fragment.showIndicator(fragmentTag);
                string = getBackStackFrag(fragmentManager.getBackStackEntryCount() - 2);
                showIndicatorOnbackStack(string);
            }
        } else {
            super.onBackPressed();
        }
    }

    public String getBackStackFrag(int i) {
        FragmentManager.BackStackEntry backStackEntry = null;

        backStackEntry = fragmentManager.getBackStackEntryAt(i);

        return backStackEntry.getName();
    }

    private void showIndicatorOnbackStack(String string) {
        if (string.equalsIgnoreCase(Saletab_main_fragment.class.getName())) {
            list_main_fragment.saleIndicator();
        }
        if (string.equalsIgnoreCase(Rent_Main_Fragment.class.getName())) {
            list_main_fragment.rentIndicator();
        }
        if (string.equalsIgnoreCase(Professionaltab_Main_Fragment.class.getName())) {
            list_main_fragment.professionalIndicator();
        }
        if (string.equalsIgnoreCase(Businesstab_Main_Fragment.class.getName())) {
            list_main_fragment.businessIndicator();
        }
        if (string.equalsIgnoreCase(List_Main_Activity.class.getName())) {
            list_main_fragment.allIndicator();
        }
    }

    private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }


    // hit professional map api
    private void hitProfessionalDataApi() {
        if (mMap != null)
            mMap.clear();
        recyclerCategoryMainAct.setVisibility(View.VISIBLE);
//        main_professioanl_button_layout.setVisibility(View.VISIBLE);
        main_rent_button_layout.setVisibility(View.GONE);
        main_sale_button_layout.setVisibility(View.GONE);

        Animation.RightToLeft_And_Show(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_business, business_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);

        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();

        //Call<GetPropertyListing> getPropertyListingCall = api.getPropertyList("professional");

        Call<GetPropertyListing> getPropertyListingCall;

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("professional", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("professional");
        }

        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().getData().isEmpty()) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String professionalId = response.body().getData().get(i).getUserid();
                                String professionalUserId = response.body().getData().get(i).getProfessionalid();
                                String category = response.body().getData().get(i).getCategory();
                                String professionalName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String description = response.body().getData().get(i).getDescription();
                                String userId = response.body().getData().get(i).getId();

                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                double markerDistance = distance(latitude, longitude, lat, lng);

                                drawMarkerProfessional(lat, lng, subCategory, professionalId, category, professionalName, profileImage, userId, (int) markerDistance, description, mobileNumber, likeStatus,type,professionalUserId);

                            }
                        }
                        getPrepareDataCategory("professional");
                    } else {

                        tvTotalProperty.setText("Total Properties Count: " +"0");
                        Toast.makeText(MainActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void hitProfessionalDataApiFromFilter() {
        if (mMap != null)
            mMap.clear();
        recyclerCategoryMainAct.setVisibility(View.VISIBLE);
//        main_professioanl_button_layout.setVisibility(View.VISIBLE);
        main_rent_button_layout.setVisibility(View.GONE);
        main_sale_button_layout.setVisibility(View.GONE);

        Animation.RightToLeft_And_Show(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_business, business_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);

        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();

        Call<SerachSaleOrRent> getPropertyListingCall = api.getSearchBusinessProfessional("professional", hashMapData.get("fullName").toString(),
                hashMapData.get("professionalId").toString(), hashMapData.get("category").toString(), hashMapData.get("subCategory").toString(), hashMapData.get("area").toString(), hashMapData.get("speakLanguage").toString());
        getPropertyListingCall.enqueue(new Callback<SerachSaleOrRent>() {
            @Override
            public void onResponse(@NonNull Call<SerachSaleOrRent> call, @NonNull Response<SerachSaleOrRent> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String professionalId = response.body().getData().get(i).getUserid();
                                String professionalUserId = response.body().getData().get(i).getProfessionalid();
                                String category = response.body().getData().get(i).getCategory();
                                String professionalName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String userId = response.body().getData().get(i).getId();
                                String desc = response.body().getData().get(i).getDescription();
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                double markerDistance = distance(latitude, longitude, lat, lng);
                                drawMarkerProfessional(lat, lng, subCategory, professionalId, category, professionalName, profileImage, userId, (int) markerDistance, desc, mobileNumber, likeStatus,type,professionalUserId);

                            }
                        }
                        getPrepareDataCategory("professional");
                    } else {

                        tvTotalProperty.setText("Total Properties Count: " +"0");
                        //Toast.makeText(MainActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<SerachSaleOrRent> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    // show professional map data using marker
    private void drawMarkerProfessional(double lat, double lng, String subCategory, String professionalId, String category, String professionalName
            , String image, String propertyid, int distance, String desc, String mobileNumber, String likeStatus,String type,String professionalUserId) {

        Marker MarkerForProfInfoWindow = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(BitmapDescriptorFactory
                        .fromBitmap(createCustomMarker(this, R.drawable.map_loc, distance + "\nkm", subCategory, R.color.dark_blue))));

        GetPropertyListing.Data forInfoWindowsData = new GetPropertyListing.Data();
        forInfoWindowsData.setFullName(professionalName);
        forInfoWindowsData.setProfileImage(image);
        forInfoWindowsData.setCategory(category);
        forInfoWindowsData.setId(propertyid);
        forInfoWindowsData.setDescription(desc);
        forInfoWindowsData.setMobileNumber(mobileNumber);
        forInfoWindowsData.setType(type);
        forInfoWindowsData.setTitle(professionalName);
        forInfoWindowsData.setUserid(professionalId);
        forInfoWindowsData.setType(type);
        forInfoWindowsData.setLikedStatus(likeStatus);
        forInfoWindowsData.setProfessionalid(professionalUserId);

        MarkerForProfInfoWindow.setTag(forInfoWindowsData);


        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 70));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                GetPropertyListing.Data forInfoWindowsData = (GetPropertyListing.Data) marker.getTag();

                tvProfessionalName.setText(forInfoWindowsData.getFullName());
                tvProfCategory.setText(forInfoWindowsData.getCategory());
                tvProfID.setText(forInfoWindowsData.getProfessionalid());
                ProfUserId = forInfoWindowsData.getId();
                String profileImage = forInfoWindowsData.getProfileImage();
                mNumber = forInfoWindowsData.getMobileNumber();
                mLikeStatus = forInfoWindowsData.getLikedStatus();
                description=forInfoWindowsData.getDescription();
                tvListingProf.setText(forInfoWindowsData.getDescription());
                mType = forInfoWindowsData.getType();
                mUserId=forInfoWindowsData.getUserid();
                getFullName=forInfoWindowsData.getFullName();

                if (forInfoWindowsData.getLikedStatus() != null) {
                    if (forInfoWindowsData.getLikedStatus().equalsIgnoreCase("yes")) {
                        ivLike.setImageResource(R.drawable.like_icon_new);

                    } else {
                        ivLike.setImageResource(R.drawable.unselcted_heart);
                    }
                }

                if (profileImage != null && !profileImage.isEmpty())

                    if (not_first_time_showing_info_windowPro) {
                        Picasso.get().load(profileImage).into(ivPropertyImage);

                    } else {
                        not_first_time_showing_info_windowPro = true;
                        Picasso.get().load(profileImage).into(ivPropertyImage, new InfoWindowRefresher(marker));
                    }


                   // Picasso.get().load(profileImage).into(ivPropertyImage);
                else
                    Picasso.get().load(R.drawable.default_profile).into(ivPropertyImage);

                ivCallProf.setOnClickListener(MainActivity.this);
                ivShare.setOnClickListener(MainActivity.this);
                ivChatting.setOnClickListener(MainActivity.this);
                ivLike.setOnClickListener(MainActivity.this);
                infoItemListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });


        mMap.setOnInfoWindowClickListener(marker -> {
            Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
            intent.putExtra("_userId", ProfUserId);
            intent.putExtra("info_window_type", "professional");
            intent.putExtra("businessUserId",mUserId);
            startActivity(intent);
        });

    }


    //  hit business property api
    private void hitBusinessDataApi() {
        if (mMap != null)
            mMap.clear();

        recyclerCategoryMainAct.setVisibility(View.VISIBLE);

        main_rent_button_layout.setVisibility(View.GONE);
        main_sale_button_layout.setVisibility(View.GONE);
        Animation.RightToLeft_And_Show(view_business, business_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();

        Call<GetPropertyListing> getPropertyListingCall;

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("business", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("business");
        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().getData().isEmpty()) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String businessId = response.body().getData().get(i).getUserid();
                                String businessUserId = response.body().getData().get(i).getBusinessid();
                                String category = response.body().getData().get(i).getCategory();
                                String businessName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String userId = response.body().getData().get(i).getId();
                                String desc = response.body().getData().get(i).getDescription();
                                double markerDistance = distance(latitude, longitude, lat, lng);
                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                String type = response.body().getData().get(i).getLikedStatus();
                                drawMarkerBusiness(lat, lng, subCategory, businessId, category, businessName, profileImage, userId, (int) markerDistance, desc, mobileNumber, likeStatus,type,businessUserId);
                            }
                        }
                        getPrepareDataCategory("business");
                    } else {
                        tvTotalProperty.setText("Total Properties Count: " +"0");
                       // Toast.makeText(MainActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void hitBusinessDataApiFromFilter() {
        if (mMap != null)
            mMap.clear();
        recyclerCategoryMainAct.setVisibility(View.VISIBLE);
        main_rent_button_layout.setVisibility(View.GONE);
        main_sale_button_layout.setVisibility(View.GONE);


        Animation.RightToLeft_And_Show(view_business, business_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(sale_view, sale_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_rent, rent_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(view_professional, professional_main_text, Constants.animDuration);
        Animation.LeftToRight_And_Exit(all_view, all_main_text, Constants.animDuration);

        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<SerachSaleOrRent> getPropertyListingCall = api.getSearchBusinessProfessional("business", hashMapData.get("fullName").toString(),
                hashMapData.get("professionalId").toString(), hashMapData.get("category").toString(), hashMapData.get("subCategory").toString(), hashMapData.get("area").toString(), hashMapData.get("speakLanguage").toString());
        getPropertyListingCall.enqueue(new Callback<SerachSaleOrRent>() {
            @Override
            public void onResponse(@NonNull Call<SerachSaleOrRent> call, @NonNull Response<SerachSaleOrRent> response) {
                MyDialog.getInstance(MainActivity.this).hideDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String businessId = response.body().getData().get(i).getUserid();
                                String businessUserId = response.body().getData().get(i).getBusinessid();
                                String category = response.body().getData().get(i).getCategory();
                                String businessName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String userId = response.body().getData().get(i).getId();
                                String description = response.body().getData().get(i).getDescription();
                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                double markerDistance = distance(latitude, longitude, lat, lng);
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                String type = response.body().getData().get(i).getType();

                                drawMarkerBusiness(lat, lng, subCategory, businessId, category, businessName, profileImage, userId, (int) markerDistance, description, mobileNumber, likeStatus,type,businessUserId);
                            }
                        }
                        getPrepareDataCategory("business");
                    } else {
                        tvTotalProperty.setText("Total Properties Count: " +"0");
                        //Toast.makeText(MainActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "No Property Found", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<SerachSaleOrRent> call, @NonNull Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    // show Business map data using marker
    private void drawMarkerBusiness(double lat, double lng, String subCategory, String userId, String category, String businessName
            , String image, String propertyId, int distance, String desc, String mobileNumber, String likeStatus,String type,String businessUserId) {

        MarkerForBusinessInfoWindow = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(BitmapDescriptorFactory
                        .fromBitmap(createCustomMarker(this, R.drawable.map_red, distance + "\nkm", subCategory, R.color.red))));


        GetPropertyListing.Data forInfoWindowsData = new GetPropertyListing.Data();
        forInfoWindowsData.setFullName(businessName);
        forInfoWindowsData.setProfileImage(image);
        forInfoWindowsData.setCategory(category);
        forInfoWindowsData.setId(propertyId);
        forInfoWindowsData.setDescription(desc);
        forInfoWindowsData.setMobileNumber(mobileNumber);
        forInfoWindowsData.setLikedStatus(likeStatus);
        forInfoWindowsData.setUserid(userId);
        forInfoWindowsData.setType(type);
        forInfoWindowsData.setTitle(businessName);
        forInfoWindowsData.setBusinessid(businessUserId);

        MarkerForBusinessInfoWindow.setTag(forInfoWindowsData);


        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 90));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                GetPropertyListing.Data forInfoWindowsData = (GetPropertyListing.Data) marker.getTag();
                tvProfessionalName.setText(forInfoWindowsData.getFullName());
                tvProfCategory.setText(forInfoWindowsData.getCategory());
                tvProfID.setText(forInfoWindowsData.getBusinessid());
                BusinessUserId = forInfoWindowsData.getId();
                mLikeStatus = forInfoWindowsData.getLikedStatus();
                mUserId=forInfoWindowsData.getUserid();
                getFullName=forInfoWindowsData.getFullName();
                description=forInfoWindowsData.getDescription();
                String profileImage = forInfoWindowsData.getProfileImage();
                tvListingProf.setText(forInfoWindowsData.getDescription());
                mNumber = forInfoWindowsData.getMobileNumber();
                mType = forInfoWindowsData.getType();
                mPropertyId = forInfoWindowsData.getId();

                if (forInfoWindowsData.getLikedStatus() != null) {
                    if (forInfoWindowsData.getLikedStatus().equalsIgnoreCase("yes")) {
                        ivLike.setImageResource(R.drawable.like_icon_new);

                    } else {
                        ivLike.setImageResource(R.drawable.unselcted_heart);
                    }
                }

                if (profileImage != null && !profileImage.isEmpty())
                    if (not_first_time_showing_info_windowBusi) {
                        Picasso.get().load(profileImage).into(ivPropertyImage);

                    } else {
                        not_first_time_showing_info_windowBusi = true;
                        Picasso.get().load(profileImage).into(ivPropertyImage);
                    }

                else
                    Picasso.get().load(R.drawable.default_profile).into(ivPropertyImage);
                ivCallProf.setOnClickListener(MainActivity.this);
                ivShare.setOnClickListener(MainActivity.this);
                ivChatting.setOnClickListener(MainActivity.this);
                ivLike.setOnClickListener(MainActivity.this);
                infoItemListener.setMarker(marker);
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        mMap.setOnInfoWindowClickListener(marker -> {
            Intent intent = new Intent(MainActivity.this, ProfPropertyNameActivity.class);
            intent.putExtra("_userId", BusinessUserId);
            intent.putExtra("info_window_type", "business");
            intent.putExtra("businessUserId",mUserId);


            startActivity(intent);
        });
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void getPrepareDataCategory(String type) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<ProfessionalResponse> getCategoryList = api.getCategoryList();
        getCategoryList.enqueue(new Callback<ProfessionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalResponse> call, @NonNull Response<ProfessionalResponse> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        List<ProfessionalDataResponse> professionalDataResponseList = response.body().getData();
                        setUpRecyclerView(professionalDataResponseList, type);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfessionalResponse> call, Throwable t) {

            }
        });


    }

    private void setUpRecyclerView(List<ProfessionalDataResponse> professionalDataResponseList, String type) {
        recyclerCategoryMainAct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, professionalDataResponseList, this, type);
        recyclerCategoryMainAct.setAdapter(categoryAdapter);
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double Rad = 6372.8; //Earth's Radius In kilometers
        // TODO Auto-generated method stub
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Rad * c;
    }


    @Override
    public void onCategoryClick(int position, List<ProfessionalDataResponse> professionalDataResponseList, String type) {

        String categoryName = professionalDataResponseList.get(position).getName();
        if (type.equalsIgnoreCase("professional")) {
            getDataFromProfCategoryApi(categoryName);
        }
        else{
            getDataFromBusinessCategoryApi(categoryName);
        }

        resetIcon(professionalDataResponseList);
        professionalDataResponseList.get(position).setCheck(true);
        categoryAdapter.notifyDataSetChanged();

    }

    private void resetIcon(List<ProfessionalDataResponse> professionalDataResponseList) {

        for(int i=0;i<professionalDataResponseList.size();i++){
            professionalDataResponseList.get(i).setCheck(false);
        }
    }


    private void getDataFromProfCategoryApi(String category) {
        MyDialog.getInstance(this).showDialog(this);

        searchByCat=category;
        searchByPro="professional";
        allData="";

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
           getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("professional", category,ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("professional", category,"");

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                    mMap.clear();
                    MyDialog.getInstance(MainActivity.this).hideDialog();
                    if (response.body() != null && response.body().getData() != null && !response.body().getData().isEmpty()) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                responseSale = response;
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String professionalId = response.body().getData().get(i).getUserid();
                                String professionalUserId = response.body().getData().get(i).getProfessionalid();
                                String category = response.body().getData().get(i).getCategory();
                                String professionalName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String userId = response.body().getData().get(i).getId();
                                String description = response.body().getData().get(i).getDescription();
                                double markerDistance = distance(latitude, longitude, lat, lng);
                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                drawMarkerProfessional(lat, lng, subCategory, professionalId, category, professionalName, profileImage, userId, (int) markerDistance, description, mobileNumber, likeStatus,type,professionalUserId);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    private void getDataFromBusinessCategoryApi(String category) {
        MyDialog.getInstance(this).showDialog(this);
        searchByCat=category;
        searchByPro="business";
        allData="";

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("business", category,ModelManager.modelManager().getCurrentUser().getId());

        }else {
           getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("business", category);

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        tvTotalProperty.setText("Total Properties Count: " +response.body().getData().size());
                        mMap.clear();
                        MyDialog.getInstance(MainActivity.this).hideDialog();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getLat() != null && response.body().getData().get(i).getLongg() != null) {
                                responseSale = response;
                                double lat = Double.parseDouble(response.body().getData().get(i).getLat());
                                double lng = Double.parseDouble(response.body().getData().get(i).getLongg());
                                String subCategory = response.body().getData().get(i).getSubCategory();
                                String businessId = response.body().getData().get(i).getId();
                                String businessUserId = response.body().getData().get(i).getBusinessid();
                                String category = response.body().getData().get(i).getCategory();
                                String businessName = response.body().getData().get(i).getFullName();
                                String profileImage = response.body().getData().get(i).getProfileImage();
                                String userId = response.body().getData().get(i).getId();
                                String description = response.body().getData().get(i).getDescription();
                                String mobileNumber = response.body().getData().get(i).getMobileNumber();
                                String proId = response.body().getData().get(i).getId();
                                double markerDistance = distance(latitude, longitude, lat, lng);
                                String likeStatus = response.body().getData().get(i).getLikedStatus();
                                String type = response.body().getData().get(i).getLikedStatus();

                                drawMarkerBusiness(lat, lng, subCategory, businessId, category, businessName, profileImage, userId, (int) markerDistance, description, mobileNumber, likeStatus,type,businessUserId);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(MainActivity.this).hideDialog();

            }
        });
    }

    @Override
    public void onSaleCategoryClick(int position, List<GetCategoryList.Data> data) {

        String categoryName = data.get(position).getName();
        getDataFromSaleCategoryApi(categoryName);
        resetCheckBtn(data);
        data.get(position).setSelected(true);
        saleMainCategoryListAdapter.notifyDataSetChanged();
    }

    private void resetCheckBtn(List<GetCategoryList.Data> data) {

        for(int i=0;i<data.size();i++){
            data.get(i).setSelected(false);
        }
    }

    @Override
    public void onRentClick(int position, List<GetCategoryList.Data> data) {
        String categoryName = data.get(position).getName();
        getRentDataCategoryApi(categoryName);
        resetRentCheckbtn(data);
        data.get(position).setSelected(true);
        rentRecyclerMainAdapter.notifyDataSetChanged();

    }

    private void resetRentCheckbtn(List<GetCategoryList.Data> data) {

        for(int i=0;i<data.size();i++){
            data.get(i).setSelected(false);
        }
    }


    public void onLikeClick() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if (mType.equalsIgnoreCase("sale") || mType.equalsIgnoreCase("rent")) {
            if (mLikeStatus.equalsIgnoreCase("yes")) {
                getUserDetailsCall = api.getLikePostPorBusiness(mType, mPropertyId, ModelManager.modelManager().getCurrentUser().getId(), false);
            } else {
                getUserDetailsCall = api.getLikePostPorBusiness(mType, mPropertyId, ModelManager.modelManager().getCurrentUser().getId(), true);
            }
        } else {
            if (mLikeStatus.equalsIgnoreCase("yes")) {
                getUserDetailsCall = api.getLikePost(mType, ModelManager.modelManager().getCurrentUser().getId(), mPropertyId, false);
            } else {
                getUserDetailsCall = api.getLikePost(mType, ModelManager.modelManager().getCurrentUser().getId(), mPropertyId, true);

            }
        }

        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 200) {
                            Toaster.toast(response.body().getResponseMessage());

                            if(type.equalsIgnoreCase("sale"))
                                showSaleData();
                            else if(type.equalsIgnoreCase("rent"))
                                showRentData();
                            else if(type.equalsIgnoreCase("professional"))
                                hitProfessionalDataApi();
                            else if (type.equalsIgnoreCase("business"))
                                hitBusinessDataApi();
                            else
                                getAllData();


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            latitudeFromSearch = data.getStringExtra("LAT");
            longitudeFromSearch = data.getStringExtra("LONG");
            getSerchDataFromApi(type, latitudeFromSearch, longitudeFromSearch);
          //  GPSTracker gpsTracker = new GPSTracker(MainActivity.this);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latitudeFromSearch), Double.parseDouble(longitudeFromSearch)));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

        }
    }

    private void onChat() {
        if(ModelManager.modelManager().getCurrentUser()!=null){
            startActivity(ChatActivity.getIntent(this, mUserId, mPropertyId,getFullName,description));

        }else {
            startActivity(Login_Signup_Button_Activity.getIntent(this));

        }
    }
}
