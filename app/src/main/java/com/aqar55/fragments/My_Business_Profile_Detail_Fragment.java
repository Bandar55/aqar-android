package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.aqar55.R;
import com.aqar55.databinding.LayoutMyBusinessProfileBinding;

public class My_Business_Profile_Detail_Fragment extends Fragment implements View.OnClickListener,OnMapReadyCallback {
    LayoutMyBusinessProfileBinding layoutMyBusinessProfileBinding;
    private static final String TAG = "My_Business_Profile_Det";
    View view;
    private GoogleMap mMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMyBusinessProfileBinding = DataBindingUtil.inflate(inflater, R.layout.layout_my_business_profile, container, false);
        view = layoutMyBusinessProfileBinding.getRoot();


        layoutMyBusinessProfileBinding.tvSearchBusiness.setOnClickListener(this);
        layoutMyBusinessProfileBinding.ivBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchBusiness:
                My_Business_Social_Fragment my_business_social_fragment = new My_Business_Social_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.business_profile_container, my_business_social_fragment).addToBackStack("").commit();

                break;
                case R.id.ivBack:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    break;

        }
    }
}
