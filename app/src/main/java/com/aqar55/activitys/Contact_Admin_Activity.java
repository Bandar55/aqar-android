package com.aqar55.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.fragments.Contact_Admin_Fragment;

public class Contact_Admin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__admin_);
        Contact_Admin_Fragment contact_admin_fragment = new Contact_Admin_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.propertysalecontainer, contact_admin_fragment).addToBackStack("").commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
