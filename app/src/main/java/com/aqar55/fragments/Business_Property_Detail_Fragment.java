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
import com.aqar55.activitys.Chat_Activity;
import com.aqar55.activitys.MainActivity;
import com.aqar55.databinding.LayoutBusinessDetailsUpdatedBinding;

public class Business_Property_Detail_Fragment extends Fragment implements View.OnClickListener,OnMapReadyCallback {
    View view;
    LayoutBusinessDetailsUpdatedBinding layoutBusinessDetailsUpdatedBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBusinessDetailsUpdatedBinding = DataBindingUtil.inflate(inflater, R.layout.layout_business_details_updated, container, false);
        view = layoutBusinessDetailsUpdatedBinding.getRoot();
        initMap();
       // layoutBusinessDetailsUpdatedBinding.viewPager.setAdapter(new product_detail_slider_adapter(getContext()));
        layoutBusinessDetailsUpdatedBinding.indicator.setupWithViewPager(layoutBusinessDetailsUpdatedBinding.viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Business_Property_Detail_Fragment.SliderTimer(), 2000, 3000);

        layoutBusinessDetailsUpdatedBinding.ivBack.setOnClickListener(this);
        layoutBusinessDetailsUpdatedBinding.tvReqMoreInfo.setOnClickListener(this);

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragProName);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("professional", "buss");
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            case R.id.tvReqMoreInfo:
                Intent intent2 = new Intent(getActivity(),Chat_Activity.class);
                intent2.putExtra("CHAT","chat");
                startActivity(intent2);
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
                    if (layoutBusinessDetailsUpdatedBinding.viewPager.getCurrentItem() < 7) {
                        layoutBusinessDetailsUpdatedBinding.viewPager.setCurrentItem(layoutBusinessDetailsUpdatedBinding.viewPager.getCurrentItem() + 1);
                    } else {
                        layoutBusinessDetailsUpdatedBinding.viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
