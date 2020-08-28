package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.aqar55.R;
import com.aqar55.databinding.ActivitySettingsBinding;
import com.aqar55.fragments.Settings_Main_Fragment;

public class Settings_Activity extends AppCompatActivity {
    ActivitySettingsBinding activitySettingsBinding;
    Settings_Main_Fragment settings_main_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings_);
        settings_main_fragment = new Settings_Main_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, settings_main_fragment).commit();

    }
}
