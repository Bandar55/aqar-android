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

import com.aqar55.R;
import com.aqar55.activitys.MainActivity;
import com.aqar55.databinding.FragmentForRentTabBinding;

public class Setting_For_Sale_Tab_Fragment extends Fragment implements View.OnClickListener {
    FragmentForRentTabBinding fragmentForRentTabBinding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentForRentTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_rent_tab, container, false);
        view = fragmentForRentTabBinding.getRoot();
        fragmentForRentTabBinding.tvMoreForSale.setOnClickListener(this);
        fragmentForRentTabBinding.tvSavePostedProperty.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvMoreForSale:
                Setting_Sale_Toggle_Tab_Fragment setting_sale_toggle_tab_fragment = new Setting_Sale_Toggle_Tab_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_sale_toggle_tab_fragment).addToBackStack("").commit();
                break;
            case R.id.tvSavePostedProperty:
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.putExtra("professional","sale");
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
        }

    }
}
