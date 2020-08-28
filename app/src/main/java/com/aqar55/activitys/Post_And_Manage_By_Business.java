package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityPostAndManageByBusinessBinding;
import com.aqar55.fragments.My_Business_Profile_Fragment;

public class Post_And_Manage_By_Business extends AppCompatActivity {
    ActivityPostAndManageByBusinessBinding activityPostAndManageByBusinessBinding;
    My_Business_Profile_Fragment my_business_profile_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPostAndManageByBusinessBinding = DataBindingUtil.setContentView(this, R.layout.activity_post__and__manage__by__business);
        my_business_profile_fragment = new My_Business_Profile_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.business_profile_container,my_business_profile_fragment).commit();
    }
}
