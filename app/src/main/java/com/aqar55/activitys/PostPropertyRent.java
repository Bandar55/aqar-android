package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.PostpropertyrentBinding;
import com.aqar55.fragments.Post_A_Property_Fragment;

public class PostPropertyRent extends AppCompatActivity {

    PostpropertyrentBinding postpropertyrentBinding;
    Post_A_Property_Fragment post_a_property_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postpropertyrentBinding = DataBindingUtil.setContentView(this, R.layout.postpropertyrent);

        post_a_property_fragment = new Post_A_Property_Fragment();
//        if (bundle != null) {
//            post_a_property_fragment.setArguments(bundle);
//        }
        getSupportFragmentManager().beginTransaction().replace(R.id.postpropertyrentcontainer, post_a_property_fragment).commit();

    }
}
