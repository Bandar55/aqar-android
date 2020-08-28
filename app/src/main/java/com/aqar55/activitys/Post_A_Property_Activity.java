package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityPostAPropertyBinding;
import com.aqar55.fragments.Post_A_Property_Fragment;
;

public class Post_A_Property_Activity extends AppCompatActivity {

    ActivityPostAPropertyBinding activityPostAPropertyBinding;
    Post_A_Property_Fragment post_a_property_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPostAPropertyBinding = DataBindingUtil.setContentView(this, R.layout.activity_post__a__property_);
        Bundle bundle = getIntent().getExtras();

        post_a_property_fragment = new Post_A_Property_Fragment();
        if (bundle != null) {
            post_a_property_fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.post_a_property_container, post_a_property_fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
