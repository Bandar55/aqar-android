package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityProductDetailSaleBinding;
import com.aqar55.fragments.Property_Detail_Fragment;


public class ProductDetailSale extends AppCompatActivity {
    private static final String TAG = "ProductDetailActivity";
    private ActivityProductDetailSaleBinding activityProductDetailSaleBinding;
    private String proId;
    private Property_Detail_Fragment propertyDetailSaleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailSaleBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail_sale);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("propid", intent.getStringExtra("propid"));
        bundle.putString("type",intent.getStringExtra("type"));
        bundle.putString("user_id",intent.getStringExtra("user_id"));

        propertyDetailSaleFragment = new Property_Detail_Fragment();
        propertyDetailSaleFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.propertysalecontainer,
                propertyDetailSaleFragment).commit();

}

}
