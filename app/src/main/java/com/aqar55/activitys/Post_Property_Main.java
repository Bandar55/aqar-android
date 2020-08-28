package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;

import com.aqar55.R;
import com.aqar55.adapters.ViewPagerAdapter;
import com.aqar55.databinding.ActivityPostPropertyMainBinding;
import com.aqar55.fragments.Post_Property_For_Rent;
import com.aqar55.fragments.Post_Property_For_Sale;
import com.aqar55.helper.BaseManager;
import com.aqar55.model.ManageActiveProperty;

public class Post_Property_Main extends AppCompatActivity implements View.OnClickListener {
    private ActivityPostPropertyMainBinding activityPostPropertyMainBinding;
    private Post_Property_For_Sale post_property_for_sale;
    private ManageActiveProperty.Data data;

    public static Intent getIntent(Context context, ManageActiveProperty.Data data){
        Intent intent= new Intent(context,Post_Property_Main.class);
        intent.putExtra("Data", (Serializable) data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPostPropertyMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_post__property__main);
        post_property_for_sale = new Post_Property_For_Sale();
        getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_sale).commit();
        activityPostPropertyMainBinding.postForsaleView.setVisibility(View.VISIBLE);
        BaseManager.saveDataIntoPreferences("sale","from_activity");


        if(getIntent()!=null){
            data=(ManageActiveProperty.Data )getIntent().getSerializableExtra("Data");


            if(data!=null){
                String type = data.getType();
                if(type.equalsIgnoreCase("sale")){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Data",data);
                    BaseManager.saveDataIntoPreferences("sale","from_activity");
                    post_property_for_sale.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_sale).commit();
                    activityPostPropertyMainBinding.postForsaleView.setVisibility(View.VISIBLE);
                    activityPostPropertyMainBinding.postForrentView.setVisibility(View.INVISIBLE);
                }else{
                    Post_Property_For_Rent post_property_for_rent = new Post_Property_For_Rent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Data",data);
                    BaseManager.saveDataIntoPreferences("rent","from_activity");
                    post_property_for_rent.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_rent).commit();
                    activityPostPropertyMainBinding.postForrentView.setVisibility(View.VISIBLE);
                    activityPostPropertyMainBinding.postForsaleView.setVisibility(View.INVISIBLE);
                }
            }else {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    post_property_for_sale = new Post_Property_For_Sale();
                    BaseManager.saveDataIntoPreferences("sale","from_activity");
                    getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_sale).commit();
                    activityPostPropertyMainBinding.postForsaleView.setVisibility(View.VISIBLE);
                    activityPostPropertyMainBinding.postForrentView.setVisibility(View.INVISIBLE);
                } else {
                    String type = extras.getString("type");
                    if (type.equalsIgnoreCase("sale")) {
                        post_property_for_sale = new Post_Property_For_Sale();
                        BaseManager.saveDataIntoPreferences("sale","from_activity");
                        getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_sale).commit();
                        activityPostPropertyMainBinding.postForsaleView.setVisibility(View.VISIBLE);
                        activityPostPropertyMainBinding.postForrentView.setVisibility(View.INVISIBLE);

                    } else if (type.equalsIgnoreCase("rent")) {
                        Post_Property_For_Rent post_property_for_rent = new Post_Property_For_Rent();
                        BaseManager.saveDataIntoPreferences("rent","from_activity");
                        getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_rent).commit();
                        activityPostPropertyMainBinding.postForrentView.setVisibility(View.VISIBLE);
                        activityPostPropertyMainBinding.postForsaleView.setVisibility(View.INVISIBLE);

                    }
                }
            }

        }

        activityPostPropertyMainBinding.postForSale.setOnClickListener(this);
        activityPostPropertyMainBinding.postForRent.setOnClickListener(this);
        activityPostPropertyMainBinding.ivBack.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Post_Property_For_Sale(), "For Sale");
        adapter.addFragment(new Post_Property_For_Rent(), "For Rent");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_for_sale:
                activityPostPropertyMainBinding.postForsaleView.setVisibility(View.VISIBLE);
                activityPostPropertyMainBinding.postForrentView.setVisibility(View.GONE);
                Post_Property_For_Sale post_property_for_sale = new Post_Property_For_Sale();
                BaseManager.saveDataIntoPreferences("sale","from_activity");

                getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_sale).commit();
                break;
            case R.id.post_for_rent:
                activityPostPropertyMainBinding.postForsaleView.setVisibility(View.GONE);
                activityPostPropertyMainBinding.postForrentView.setVisibility(View.VISIBLE);
                Post_Property_For_Rent post_property_for_rent = new Post_Property_For_Rent();
                BaseManager.saveDataIntoPreferences("rent","from_activity");
                getSupportFragmentManager().beginTransaction().replace(R.id.post_property_container, post_property_for_rent).commit();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }
}
