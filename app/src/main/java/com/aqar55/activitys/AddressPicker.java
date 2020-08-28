package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.adapters.PlaceAutocompleteAdapter;
import com.aqar55.utill.GPSTracker;

public class AddressPicker  extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleApiClient.OnConnectionFailedListener {
    private double Lat, Long;
    private GoogleMap mMap;
    private String Location;
    private String lat;
    private String longs;
    private CharSequence primaryText;
    protected GoogleApiClient mGoogleApiClient;
    private  List<Address> addresses = null;
    private boolean isCorrectAddress=false;
    @BindView(R.id.edt_search)
    AutoCompleteTextView autoLocation;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private Marker marker;
    private  MarkerOptions markerOptions;
    private LatLng latLng;
    private String currentAddress="";
    int i=0;
    private Context context;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private ResultCallback<PlaceBuffer> placeBufferResultCallback = places -> {
        if (!places.getStatus().isSuccess()) {
            places.release();
            return;
        }
        final Place place = places.get(0);
        latLng = place.getLatLng();

        if(latLng!=null){
            pickNewLocation();
        }
        places.release();
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            primaryText = item.getPrimaryText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(placeBufferResultCallback);
            isCorrectAddress=true;
        }
    };
    public static Intent getIntent(Context context) {
        return new Intent(context, AddressPicker.class);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_address);
        ButterKnife.bind(this);
        context=this;
       // tvTitle.setText(R.string.address_picker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
        GPSTracker gpsTracker = new GPSTracker(this);
        autoLocation.setOnItemClickListener(mAutocompleteClickListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, null);
        autoLocation.setAdapter(placeAutocompleteAdapter);
        Lat = gpsTracker.getLatitude();
        Long = gpsTracker.getLongitude();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lat = String.valueOf(Lat);
        longs = String.valueOf(Long);
        LatLng latLngg = new LatLng(Lat, Long);
        /*Marker marker = mMap.addMarker(new MarkerOptions().position(latLngg));
        marker.showInfoWindow();*/
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(Lat, Long, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            tvAddress.setText(address);
            Location = address;
            currentAddress=address;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngg));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.setOnMapClickListener(this);

        mMap.setOnMarkerDragListener(this);


        getLocationAtChangeLocation(mMap);
    }

    private void getLocationAtChangeLocation(GoogleMap mMap) {

        if(mMap==null)
            return;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng position = mMap.getCameraPosition().target;

                lat =String.valueOf( position.latitude);
                longs =String.valueOf(position.longitude);


                try {
                    Geocoder geocoder = new Geocoder(AddressPicker.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(position.latitude,position.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses != null && addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    tvAddress.setText(address);
                    Location = address;
                    currentAddress=address;
                }
                Log.d("MapActivity", "Position: " + position.latitude);
            }
        });


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        // marker.setAlpha(0.5f);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            lat = String.valueOf(marker.getPosition().latitude);
            longs = String.valueOf(marker.getPosition().longitude);
            addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            tvAddress.setText(address);
            Location = address; }
    }

    @OnClick({R.id.btn_done, R.id.iv_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:


                    Intent intent = new Intent();
                    intent.putExtra("ADDRESS", Location);
                    intent.putExtra("LAT", lat);
                    intent.putExtra("LONG", longs);
                    setResult(RESULT_OK, intent);
                    finish();





                break;
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }

    private void pickNewLocation() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoLocation.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        List<Address> addresses = null;
        if (mMap == null) {
            return;
        }
        mMap.clear();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        //Address pick here
        try {
            lat = String.valueOf(latLng.latitude);
            longs = String.valueOf(latLng.latitude);
            addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            tvAddress.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //  autoLocation.setText(address);
            Location = autoLocation.getText().toString();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

/*    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();

        markerOptions = new MarkerOptions().position(latLng);
        markerOptions.draggable(true);
        mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_in)));

    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        pickNewLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }



}
