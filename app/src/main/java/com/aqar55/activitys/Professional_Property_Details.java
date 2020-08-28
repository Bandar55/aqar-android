package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityProfessionalPropertyDetailsBinding;
import com.aqar55.fragments.Professioanl_Property_Details_;

public class Professional_Property_Details extends AppCompatActivity {
    ActivityProfessionalPropertyDetailsBinding activityProfessionalPropertyDetailsBinding;

    Professioanl_Property_Details_ professional_property_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfessionalPropertyDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_professional__property__details);
        professional_property_details = new Professioanl_Property_Details_();

        getSupportFragmentManager().beginTransaction().replace(R.id.prodessional_property_detail_container, professional_property_details).commit();


    }
}
