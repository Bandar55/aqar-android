package com.aqar55.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import com.aqar55.R;
import com.aqar55.activitys.MainActivity;
import com.aqar55.databinding.LayoutProfessionalDetailsUpdatedBinding;

public class Professioanl_Property_Details_ extends Fragment implements View.OnClickListener,OnMapReadyCallback {
    LayoutProfessionalDetailsUpdatedBinding layoutProfessionalDetailsUpdatedBinding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutProfessionalDetailsUpdatedBinding = DataBindingUtil.inflate(inflater, R.layout.layout_professional_details_updated, container, false);
        view = layoutProfessionalDetailsUpdatedBinding.getRoot();
        initMap();

       // layoutProfessionalDetailsUpdatedBinding.viewPager.setAdapter(new product_detail_slider_adapter(getContext()));
        layoutProfessionalDetailsUpdatedBinding.indicator.setupWithViewPager(layoutProfessionalDetailsUpdatedBinding.viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Professioanl_Property_Details_.SliderTimer(), 2000, 3000);

        layoutProfessionalDetailsUpdatedBinding.tvReqMoreInfo.setOnClickListener(this);
        layoutProfessionalDetailsUpdatedBinding.ivBack.setOnClickListener(this);

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragProName);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvReqMoreInfo:

                Professional_Property_Listing professional_property_listing = new Professional_Property_Listing();

                getFragmentManager().beginTransaction().replace(R.id.prodessional_property_detail_container, professional_property_listing).addToBackStack("").commit();


                break;
            case R.id.ivBack:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("professional", "prof");
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(28.6208, 77.3639);
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_map_icon)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13.0f));
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null)
                return;
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (layoutProfessionalDetailsUpdatedBinding.viewPager.getCurrentItem() < 7) {
                        layoutProfessionalDetailsUpdatedBinding.viewPager.setCurrentItem(layoutProfessionalDetailsUpdatedBinding.viewPager.getCurrentItem() + 1);
                    } else {
                        layoutProfessionalDetailsUpdatedBinding.viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
