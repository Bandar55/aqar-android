package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityPostAndManageByProfessionBinding;

public class Post_And_Manage_By_Profession extends AppCompatActivity {
    ActivityPostAndManageByProfessionBinding activityPostAndManageByProfessionBinding;

    ActivityMyProfessionalProfile my_professional_profile_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPostAndManageByProfessionBinding = DataBindingUtil.setContentView(this, R.layout.activity_post__and__manage__by__profession);
        my_professional_profile_fragment = new ActivityMyProfessionalProfile();
       // getSupportFragmentManager().beginTransaction().replace(R.id.profile_container, my_professional_profile_fragment).commit();
    }

    // In your activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
