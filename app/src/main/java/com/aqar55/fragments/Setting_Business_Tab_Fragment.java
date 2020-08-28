package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import com.aqar55.R;
import com.aqar55.activitys.MainActivity;
import com.aqar55.databinding.FragmentBusinessTabBinding;

public class Setting_Business_Tab_Fragment  extends Fragment implements View.OnClickListener {
    FragmentBusinessTabBinding fragmentBusinessTabBinding;
    View view;
    private HashMap<String, Object> stringObjectHashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBusinessTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_business_tab,container,false);
        view = fragmentBusinessTabBinding.getRoot();
        stringObjectHashMap = new HashMap<>();
        fragmentBusinessTabBinding.tvSearchBusiness.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSearchBusiness:
                HashMap<String , Object> hashMap = prepareData();
                startActivity(MainActivity.getIntent(getActivity(),hashMap));
                getActivity().finish();
                break;
        }
    }

    private HashMap<String, Object> prepareData() {
        stringObjectHashMap.put("main_act","business");
        return stringObjectHashMap;
    }


}
