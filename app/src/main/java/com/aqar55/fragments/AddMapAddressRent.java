package com.aqar55.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.aqar55.R;
import com.aqar55.databinding.FragmentAddLocationBinding;
import com.aqar55.utill.FetchAddressService;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;

import static android.app.Activity.RESULT_OK;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.support.constraint.Constraints.TAG;

public class AddMapAddressRent extends Fragment implements View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener {

    public static final int REQ_CODE_LOCATION = 12;
    private static final int REQ_CODE_GPS_SETTINGS = 123;
    double lat, lonng;
    private GoogleMap gm;
    private LatLng currentLocationLatLngs;
    private GoogleApiClient googleApiClient;
    private FragmentAddLocationBinding fragmentAddLocationBinding;
    private View view;
    private LatLng midLatLng;
    private LocationRequest locationRequest;
    private Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAddLocationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_location, container, false);
        view = fragmentAddLocationBinding.getRoot();
        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(fragmentAddLocationBinding.maprent, "Make sure you are connected to Internet.");
        }

        initMap();
        fragmentAddLocationBinding.ivBack.setOnClickListener(this);
        fragmentAddLocationBinding.tvSaveLocation.setOnClickListener(this);
        fragmentAddLocationBinding.ivDishAnteena.setOnClickListener(this);
        fragmentAddLocationBinding.ivMyLocation.setOnClickListener(this);
        startLocationFunctioning();

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void setMarkerToLocation(double lat, double lonng) {
        LatLng latLng = new LatLng(lat, lonng);
        final Marker marker = gm.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.blue_map_icon)).draggable(true));
        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));

//           final Marker marker =  googleMap.addMarker(new MarkerOptions()
//                    .position(midLatLng)
//                    .title("Wrong Turn!")
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.drawable.blue_map_icon)));

        gm.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.setPosition(latLng);
                gm.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        gm.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                Log.d("System out", "onMarkerDragEnd...");
                gm.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                midLatLng = arg0.getPosition();
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.tvSaveLocation:
                getAddress();

                break;

            case R.id.ivDishAnteena:
                gm.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.ivMyLocation:
                gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        if (googleMap != null) {
            gm = googleMap;

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);


        }
        //  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(midLatLng.latitude, midLatLng.longitude), 12.0f));
    }

    private void getAddress() {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(midLatLng.latitude, midLatLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String zip = addresses.get(0).getPostalCode();
            String country = addresses.get(0).getCountryName();
            String locality = addresses.get(0).getLocality();
            String address = addresses.get(0).getAddressLine(0);
            double lat = midLatLng.latitude;
            double longg = midLatLng.longitude;

            Intent intent = new Intent("address-data-rent");
            // You can also include some extra data.
            intent.putExtra("country", country);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("zip", zip);
            intent.putExtra("locality", locality);
            intent.putExtra("address", address);
            intent.putExtra("lat", midLatLng);
            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", longg);


            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }
        getFragmentManager().popBackStack();

    }

    private void startLocationFunctioning() {
        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Internet Not Abaiavle", Toast.LENGTH_SHORT).show();
        } else {
            if (isGPlayServicesOK()) {
                buildGoogleApiClient();
            }

        }

    }

    //  METHOD: TO CHECK IF DEVICE HAS UPDATED GOOGLE PLAY SERVICES
    public boolean isGPlayServicesOK() {
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            /* The returned dialog displays a localized message about the error and upon
             user confirmation (by tapping on dialog) will direct them to the Play Store
              if Google Play services is out of date or missing, */
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), isAvailable, 1001);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "Can't connect to Google Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    //   METHOD: TO  Create an instance of the GoogleAPIClient AND LocationRequest
    private void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(AddMapAddressRent.this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        createLocationRequest();

    }

    //Method: To create location request and set its priorities
    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(10 * 1000);

        //      Initialize AddressResultReceiver class object
        mResultReceiver = new AddressResultReceiver(null);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.w(TAG, "onConnected");

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(AddMapAddressRent.this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended: " + i);
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_GPS_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onResult: GPS IS ENABLED");
                    Log.w(TAG, "onResult: Success 2");

                    loadCurrentLoc(); //GET CURRENT LOCATION
                } else {
                    Log.i(TAG, "onResult: GPS IS DISABLED");

                    showGPSDialog("turn ondevice location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case BUTTON_POSITIVE:
                                    dialog.dismiss();

                                    /*initialize the pending result and locationRequest*/
                                    LocationSettingsRequest.Builder builder =
                                            new LocationSettingsRequest.Builder()
                                                    .addLocationRequest(locationRequest);

                                    builder.setAlwaysShow(true);

                                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                                            .checkLocationSettings(googleApiClient, builder.build());

                                    result.setResultCallback(AddMapAddressRent.this);


                                    break;
                                case BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    getActivity().finish();
                                    break;
                            }
                        }
                    });

                }
                break;


            default:
                super.onActivityResult(requestCode, resultCode, data);

        }
    }

    //    METHOD TO GET CURRENT LOCATION OF DEVICE
    private void loadCurrentLoc() {

        //      Marshmallow +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showDenyRationaleDialog("Need Device Location", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQ_CODE_LOCATION);
                        }
                    });

                    return;
                }

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQ_CODE_LOCATION);

                return;
            }


            /*DO THE LOCATION STUFF*/

            try {

                if (LocationServices.FusedLocationApi.getLastLocation(googleApiClient) != null && mLastLocation == null) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (mLastLocation != null) {

//                        showAndSaveLatLong();  // SAVE LAT AND LONG

                        lat = mLastLocation.getLatitude();
                        lonng = mLastLocation.getLongitude();

                        Intent intent = new Intent(getActivity(), FetchAddressService.class);
                        intent.putExtra(FetchAddressService.FIND_BY, FetchAddressService.FIND_BY_LOCATION);
                        intent.putExtra(FetchAddressService.RECEIVER, mResultReceiver);
                        intent.putExtra(FetchAddressService.LOCATION, mLastLocation);
                        getActivity().startService(intent);

                        currentLocationLatLngs = new LatLng(lat, lonng);

//                        putDefaultMarker();  // put marker on Map for Current Location

                    } else {
                        if (googleApiClient != null && googleApiClient.isConnected()) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getActivity());
                            Toast.makeText(getActivity(), "Make sure that Location is Enabled on the device.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (googleApiClient != null && googleApiClient.isConnected()) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getActivity());
                    } else {
                        if (googleApiClient != null) {
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                        }
                    }
                }

                // Check for Permission to access the location
//                setMyLocationEnabled();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else    //    PRE-Marshmallow
        {

            /*DO THE LOCATION STUFF*/

            try {
                if (LocationServices.FusedLocationApi.getLastLocation(googleApiClient) != null && mLastLocation == null) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (mLastLocation != null) {
//                        showAndSaveLatLong();  // SAVE LAT AND LONG

                        lat = mLastLocation.getLatitude();
                        lonng = mLastLocation.getLongitude();

                        Intent intent = new Intent(getActivity(), FetchAddressService.class);
                        intent.putExtra(FetchAddressService.FIND_BY, FetchAddressService.FIND_BY_LOCATION);
                        intent.putExtra(FetchAddressService.RECEIVER, mResultReceiver);
                        intent.putExtra(FetchAddressService.LOCATION, mLastLocation);
                        getActivity().startService(intent);

                        currentLocationLatLngs = new LatLng(lat, lonng);

//                        putDefaultMarker();  // put marker on Map for Current Location
                    } else {
                        if (googleApiClient != null && googleApiClient.isConnected()) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getActivity());
                            Toast.makeText(getActivity(), "Make sure that Location is Enabled on the device.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (googleApiClient != null && googleApiClient.isConnected()) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getActivity());
                    } else {
                        if (googleApiClient != null) {
                            googleApiClient.disconnect();
                            googleApiClient.connect();
                        }
                    }
                }

                // Check for Permission to access the location
//                setMyLocationEnabled();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void showGPSDialog(String message, DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.app_name))
                        .setIcon(ContextCompat.getDrawable(getContext(), R.drawable.aqar55_logo))
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("ok", okListener)
                        .setNegativeButton("Cancle", okListener);

        AlertDialog dialog = builder.create();
        //   ANIMATION

        dialog.show();

        // Alert Buttons
        Button positive_button = dialog.getButton(BUTTON_POSITIVE);
        Button negative_button = dialog.getButton(BUTTON_NEGATIVE);

        positive_button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        negative_button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_dark));
    }

    private void showDenyRationaleDialog(String message, DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.aqar55_logo))
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("ok", okListener)
                        .setNegativeButton("Cancle", okListener);

        AlertDialog dialog = builder.create();
        //   ANIMATION
        Window window = dialog.getWindow();


        dialog.show();

        // Alert Buttons
        Button positive_button = dialog.getButton(BUTTON_POSITIVE);
        Button negative_button = dialog.getButton(BUTTON_NEGATIVE);

        positive_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        negative_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray_dark));

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        Log.w(TAG, "onResult: status.getStatusCode() " + status.getStatusCode());

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:// GPS is already enabled no need of dialog
                Log.w(TAG, "onResult: Success 1");

                loadCurrentLoc(); //GET CURRENT LOCATION

                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED://  Location settings are not satisfied. Show the user a dialog
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                    status.startResolutionForResult(getActivity(), REQ_CODE_GPS_SETTINGS);


                    Log.w(TAG, "onResult : RESOLUTION_REQUIRED");

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: // Location settings are unavailable so not possible to show any dialog now
                Log.w(TAG, "onResult : SETTINGS_CHANGE_UNAVAILABLE");
                break;
            case LocationSettingsStatusCodes.CANCELED:
                Log.w(TAG, "onResult : CANCELLED");
                getActivity().finish();
                break;


        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (location != null) {
            Log.i(TAG, "onLocationChanged LOCATION: LAT,LNG:  " + location.getLatitude() + ", " + location.getLongitude());
            mLastLocation = location;
        } else {
            buildGoogleApiClient();
        }

    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {

            switch (resultCode) {
                case FetchAddressService.SUCCESS_RESULT:

                    if (this != null) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Address address = resultData.getParcelable(FetchAddressService.RESULT_ADDRESS);

                                String msg = resultData.getString(FetchAddressService.RESULT_MSG);
                                String city = resultData.getString(FetchAddressService.CITY);
                                String area = resultData.getString(FetchAddressService.AREA);
                                String full_loc_via_json = resultData.getString(FetchAddressService.FULL_LOCATION_VIA_JSON);

                                String pin = resultData.getString(FetchAddressService.PIN_CODE);
                                double the_lat = resultData.getDouble(FetchAddressService.LOC_LAT);
                                double the_lng = resultData.getDouble(FetchAddressService.LOC_LNG);
                                ArrayList<String> addressArrayList = resultData.getStringArrayList(FetchAddressService.ADDRESS_ARRAY_LIST);

                                /*
                                 *******
                                 * SAVE USER LOCATION
                                 ******/
                                String fullAddress = "";
                                String locality = "";
                                int max;
                                if (address != null) {
                                    max = address.getMaxAddressLineIndex();

                                    for (int i = 0; i <= max - 1; i++) {
                                        fullAddress = fullAddress + address.getAddressLine(i) + ", ";
                                    }

                                    fullAddress = fullAddress + address.getAddressLine(address.getMaxAddressLineIndex());

                                    if (address.getLocality() != null && !address.getLocality().isEmpty()) {
                                        locality = address.getLocality();
                                    } else {
                                        locality = address.getSubLocality();
                                    }

                                    Log.w(TAG, "Address +++++: " + fullAddress);

                                    Log.w(TAG, "+++++++++++++++++ AddressResultReceiver +++++++++++++++++++");
                                    Log.w("Results ARE: \n",

                                            "MESSAGE: \n" + msg
                                                    + "\nADDRESS: \n" + address
                                                    + "\nLOCALITY: \n" + locality
                                                    + "\nCITY: \n" + city
                                                    + "\nAREA: \n" + area
                                                    + "\nPIN: \n" + pin
                                                    + "\nADDRESS_ARRAY_LIST: \n" + addressArrayList
                                                    + "\nLOC_LAT: \n" + lat
                                                    + "\nLOC_LNG: \n" + lonng);
                                    LatLng latLng = new LatLng(lat, lonng);
                                    AddMapAddressRent.this.gm.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                                    midLatLng = latLng;
                                    setMarkerToLocation(lat, lonng);
//                                    getState =area;
//                                    getDistt =city;
//                                    getTown =locality;

                                } else {
                                    Log.e(TAG, "---- AddressResultReceiver : Address is NULL ----");

                                    Log.w(TAG, "---- full_loc_via_json : ----" + full_loc_via_json);

//                                    getActivity().full_address = full_loc_via_json;

                                }

                            }
                        });
                    }

                    break;

                case FetchAddressService.FAILURE_RESULT:

                    Log.w("Results ARE:", "FAILED"); //Timed out waiting for response from server

                    break;
            }

        }
    }

    //    METHOD: TO START FULL PROCESS FOR FIRST TIME..

}
