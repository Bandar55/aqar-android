package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aqar55.R;
import com.aqar55.databinding.ActivityManagePostedPropertyBinding;
import com.aqar55.fragments.ManageInactiveProperty;

public class Manage_Posted_Property extends AppCompatActivity implements View.OnClickListener {
    private ActivityManagePostedPropertyBinding activityManagePostedPropertyBinding;
    private com.aqar55.fragments.Manage_Posted_Property manage_posted_property;
    private ManageInactiveProperty manageInactiveProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManagePostedPropertyBinding = DataBindingUtil.setContentView(this, R.layout.activity_manage__posted__property);
        manage_posted_property = new com.aqar55.fragments.Manage_Posted_Property();
        manageInactiveProperty = new ManageInactiveProperty();
        getSupportFragmentManager().beginTransaction().replace(R.id.posted_property_container, manageInactiveProperty).commit();
        activityManagePostedPropertyBinding.inactiveView.setVisibility(View.VISIBLE);
        activityManagePostedPropertyBinding.active.setOnClickListener(this);
        activityManagePostedPropertyBinding.inactive.setOnClickListener(this);
        activityManagePostedPropertyBinding.ivBack.setOnClickListener(this);
        activityManagePostedPropertyBinding.addImageviewManageProperty.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inactive:
                activityManagePostedPropertyBinding.inactiveView.setVisibility(View.VISIBLE);
                activityManagePostedPropertyBinding.activeView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.posted_property_container, manageInactiveProperty).commit();
                break;
            case R.id.active:
                activityManagePostedPropertyBinding.activeView.setVisibility(View.VISIBLE);
                activityManagePostedPropertyBinding.inactiveView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.posted_property_container, manage_posted_property).commit();
                break;
            case R.id.ivBack:
                startActivity(new Intent(Manage_Posted_Property.this, MainActivity.class));
                finish();
                break;
            case R.id.add_imageview_manage_property:
                startActivity(new Intent(getApplicationContext(), Post_Property_Main.class));
                finish();
                break;
        }

    }
}
