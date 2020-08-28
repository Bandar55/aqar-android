package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.databinding.LayoutBusinessProfileSocialBinding;

public class My_Business_Social_Fragment extends Fragment implements View.OnClickListener {

    View view;
    LayoutBusinessProfileSocialBinding layoutBusinessProfileSocialBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutBusinessProfileSocialBinding = DataBindingUtil.inflate(inflater, R.layout.layout_business_profile_social, container, false);
        view = layoutBusinessProfileSocialBinding.getRoot();
        layoutBusinessProfileSocialBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutBusinessProfileSocialBinding.ivBack.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:
                getActivity().finish();
                break;
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;

        }
    }
}
