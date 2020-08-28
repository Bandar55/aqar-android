package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityProductDetailBinding;
import com.aqar55.fragments.Property_Detail_Fragment;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";
    private ActivityProductDetailBinding activityProductDetailBinding;
    private Property_Detail_Fragment property_detail_fragment;
    private String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailBinding = DataBindingUtil.setContentView(ProductDetailActivity.this, R.layout.activity_product_detail);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("propid", intent.getStringExtra("propid"));
        bundle.putString("type",intent.getStringExtra("type"));

        bundle.putString("user_id",intent.getStringExtra("user_id"));
        property_detail_fragment = new Property_Detail_Fragment();

        property_detail_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_detail_container,
                property_detail_fragment).commit();
    }

}
