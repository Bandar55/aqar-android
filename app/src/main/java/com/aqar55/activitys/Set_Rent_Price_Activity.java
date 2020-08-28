package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivitySetRentPriceBinding;

import com.aqar55.fragments.Post_A_Property_Fragment;


public class Set_Rent_Price_Activity extends AppCompatActivity {
    ActivitySetRentPriceBinding activitySetRentPriceBinding;
   // Set_Rent_Price_Info_Fragment set_rent_price_info_fragment;
   Post_A_Property_Fragment post_a_property_fragment = new Post_A_Property_Fragment();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetRentPriceBinding = DataBindingUtil.setContentView(this, R.layout.activity_set__rent__price_);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            post_a_property_fragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, post_a_property_fragment).commit();

    }
}
