package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.adapters.Add_Image_property_detail_Adapter;
import com.aqar55.databinding.FragmentMyBusinessProfileBinding;

public class My_Business_Profile_Fragment extends Fragment implements View.OnClickListener {
    FragmentMyBusinessProfileBinding fragmentMyBusinessProfileBinding;
    View view;
    Add_Image_property_detail_Adapter add_image_property_detail_adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMyBusinessProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_business_profile, container, false);
        view = fragmentMyBusinessProfileBinding.getRoot();
        fragmentMyBusinessProfileBinding.button.setOnClickListener(this);
        add_image_property_detail_adapter = new Add_Image_property_detail_Adapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentMyBusinessProfileBinding.recyclerView2.setLayoutManager(linearLayoutManager);
        fragmentMyBusinessProfileBinding.recyclerView2.setAdapter(add_image_property_detail_adapter);
        fragmentMyBusinessProfileBinding.ivBack.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                My_Business_Profile_Detail_Fragment my_business_profile_detail_fragment = new My_Business_Profile_Detail_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.business_profile_container, my_business_profile_detail_fragment).addToBackStack("").commit();
                break;
            case R.id.ivBack:
                getActivity().finish();
                break;

        }
    }
}
