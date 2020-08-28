package com.aqar55.fragments;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.aqar55.R;
import com.aqar55.databinding.ProfessionalPropertyListingMapBinding;

public class Professional_Property_Listing_Map extends Fragment implements View.OnClickListener,OnMapReadyCallback {

    View view;
    GoogleMap mGoogleMap;
    ProfessionalPropertyListingMapBinding professionalPropertyListingMapBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        professionalPropertyListingMapBinding = DataBindingUtil.inflate(inflater, R.layout.professional_property_listing_map, container, false);
        view = professionalPropertyListingMapBinding.getRoot();
        initMap();
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapProListing);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
          mGoogleMap = googleMap;

        drawMarker(30.6110, 78.3541, 4);
        drawMarker(30.6022, 78.3664, 5);
        drawMarker(30.6210, 78.3741, 6);
        drawMarker(30.6410, 78.3741, 4);
        drawMarker(30.6122, 78.3764, 5);
        drawMarker(30.6310, 78.3841, 6);
        drawMarker(30.5922, 78.3564, 5);
        drawMarker(30.6110, 78.3641, 6);


    }
    private void drawMarker(double v, double parseDouble, int x) {
// Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        LatLng latLng = new LatLng(v, parseDouble);
        markerOptions.position(latLng);

// Adding marker on the Google Map
        if (x == 4) {
            mGoogleMap.addMarker(new MarkerOptions()

                    .position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(createCustomMarker(getContext(), R.drawable.box_icon, "20k", "", R.color.gray_dark))));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        }
        if (x == 5) {
            mGoogleMap.addMarker(new MarkerOptions()

                    .position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(createCustomMarker(getContext(), R.drawable.box_icon, "20k", "", R.color.gray_dark))));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        }
        if (x == 6) {
            mGoogleMap.addMarker(new MarkerOptions()

                    .position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(createCustomMarker(getContext(), R.drawable.box_icon_new, "20k", "", R.color.gray_dark))));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        }


    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name, String heading, int color) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_marker, null);


        TextView txt_name = marker.findViewById(R.id.tvMarker);
        txt_name.setText(_name);
        TextView txt_name_heading = marker.findViewById(R.id.heading);
        txt_name_heading.setText(heading);
        txt_name_heading.setTextColor(context.getResources().getColor(color));
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


}
