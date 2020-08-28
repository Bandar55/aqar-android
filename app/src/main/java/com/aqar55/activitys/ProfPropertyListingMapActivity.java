package com.aqar55.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.aqar55.R;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.utill.GPSTracker;

public class ProfPropertyListingMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private  GPSTracker gpsTracker;
    private GoogleMap mGoogleMap;
    private List<GetPropertyListing.Data> dataList;

    public static Intent getIntent(Context context, List<GetPropertyListing.Data> dataList){
        Intent intent = new Intent(context, ProfPropertyListingMapActivity.class);
        intent.putExtra("Data", (Serializable) dataList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_property_listing_map);
        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(ProfPropertyListingMapActivity.this);
        setToolbar();
        initMap();
        getIntentData();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapProfList);
        mapFragment.getMapAsync(this);
    }

    private void getIntentData() {
        if(getIntent()!=null){
            dataList = (List<GetPropertyListing.Data>)getIntent().getSerializableExtra("Data");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        GPSTracker gpsTracker = new GPSTracker(ProfPropertyListingMapActivity.this);
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
        mGoogleMap.moveCamera(center);
        mGoogleMap.animateCamera(zoom);

        if(dataList!=null && !dataList.isEmpty()){
            for (int i=0;i< dataList.size();i++){
                LatLng latLng = new LatLng(Double.parseDouble(dataList.get(i).getLat()),
                        Double.parseDouble(dataList.get(i).getLongg()));

//                double markerDistance = distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(),
//                        Double.parseDouble(dataList.get(i).getLat()), Double.parseDouble(dataList.get(i).getLongg()));

                if(dataList.get(i).getType().equalsIgnoreCase("sale")){
                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(createCustomMarker(this, R.drawable.red_two, dataList.get(i).getPlotsizeunit()+" "+
                                            dataList.get(i).getPlotsize()))));
                }else {
                    String rentTime = dataList.get(i).getRentTime();
                    switch (rentTime){
                        case "daily":
                            drawMarkerForRent(latLng,dataList.get(i).getCurrency(),dataList.get(i).getTotalPriceRent(),rentTime);
                            break;
                        case "weekly":
                            drawMarkerForRent(latLng,dataList.get(i).getCurrency(),dataList.get(i).getTotalPriceRent(),rentTime);
                            break;
                        case "monthly":
                            drawMarkerForRent(latLng,dataList.get(i).getCurrency(),dataList.get(i).getTotalPriceRent(),rentTime);
                            break;
                        case "yearly":
                            drawMarkerForRent(latLng,dataList.get(i).getCurrency(),dataList.get(i).getTotalPriceRent(),rentTime);
                            break;
                    }

                }

//                if(markerDistance<1){
//                    //drawMarkerSale(Double.parseDouble(dataList.get(i).getLat()), Double.parseDouble(dataList.get(i).getLongg()),"1.0");
//                }else {
//                    //drawMarkerSale(Double.parseDouble(dataList.get(i).getLat()), Double.parseDouble(dataList.get(i).getLongg()),String.valueOf(markerDistance));
//                }
            }
        }else {
            Toast.makeText(this, "Data Not Found!", Toast.LENGTH_SHORT).show();
        }


    }

    private void drawMarkerForRent(LatLng latLng,String currency,String rentPrice,String rentTime) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(createCustomMarker(this, R.drawable.red_two, currency+" "+
                                rentPrice+"\n"+rentTime))));
    }

//    private void drawMarkerSale(double lat, double longg,String distance) {
//
//        LatLng latLng = new LatLng(lat, longg);
//
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory
//                        .fromBitmap(createCustomMarker(this, R.drawable.map_in, distance+"\nKM", "", R.color.dark_blue))));
//
//
//
//
//    }

    // create custom marker
    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource,
                                            String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_marker, null);

        TextView txt_name = marker.findViewById(R.id.tvMarker);
        txt_name.setText(_name);
        TextView txt_name_heading = marker.findViewById(R.id.heading);
        //txt_name_heading.setText(heading);
        //txt_name_heading.setTextColor(context.getResources().getColor(color));
        txt_name.setBackground(context.getResources().getDrawable(resource));
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


    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double Rad = 6372.8; //Earth's Radius In kilometers
        // TODO Auto-generated method stub
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        /*Log.d("Distance", String.valueOf((Rad * c)));
        if (Rad * c < 1) {
            hostList.get(i).setDistance("1 km");
        } else {
            hostList.get(i).setDistance(String.valueOf((int) (Rad * c)) + "Km");
        }
        haverdistanceKM = String.valueOf(haverdistanceKM);*/
        return Rad * c;
    }


    public void setToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button_new);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }
}
