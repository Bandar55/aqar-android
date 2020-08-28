package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityBusinessPropertyDetailBinding;
import com.aqar55.fragments.Business_Property_Detail_Fragment;

public class Business_Property_Detail_Activity extends AppCompatActivity {
    ActivityBusinessPropertyDetailBinding activityBusinessPropertyDetailBinding;
    Business_Property_Detail_Fragment business_property_detail_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBusinessPropertyDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_business__property__detail_);

        business_property_detail_fragment = new Business_Property_Detail_Fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.business_detail_container, business_property_detail_fragment).commit();
    }
}
