package com.aqar55.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.adapters.Property_Listing_Adpter;
import com.aqar55.databinding.FragmentProfessionalPropertyListingBinding;

public class Professional_Property_Listing extends Fragment implements View.OnClickListener {
    FragmentProfessionalPropertyListingBinding fragmentProfessionalPropertyListingBinding;
    View view;

    Property_Listing_Adpter property_listing_adpter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfessionalPropertyListingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_professional__property__listing, container, false);
        view = fragmentProfessionalPropertyListingBinding.getRoot();


        property_listing_adpter = new Property_Listing_Adpter(getContext());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentProfessionalPropertyListingBinding.professionalListingRecycler.setLayoutManager(linearLayoutManager);
        fragmentProfessionalPropertyListingBinding.professionalListingRecycler.setAdapter(property_listing_adpter);


        fragmentProfessionalPropertyListingBinding.mapProfessionalListing.setOnClickListener(this);
        fragmentProfessionalPropertyListingBinding.ivBack.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_professional_listing:
                Professional_Property_Listing_Map professional_property_listing_map = new Professional_Property_Listing_Map();
                getFragmentManager().beginTransaction().replace(R.id.prodessional_property_detail_container, professional_property_listing_map).addToBackStack("").commit();


                break;

            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;

        }
    }
}
