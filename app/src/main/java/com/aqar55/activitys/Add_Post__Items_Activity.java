package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityAddPostItemsBinding;
import com.aqar55.fragments.Set_Price_Fragment;

public class Add_Post__Items_Activity extends AppCompatActivity {
    ActivityAddPostItemsBinding activityAddPostItemsBinding;
    Set_Price_Fragment set_price_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPostItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_add__post___items_);
        set_price_fragment = new Set_Price_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.post_property_item_container, set_price_fragment).commit();

    }
}
