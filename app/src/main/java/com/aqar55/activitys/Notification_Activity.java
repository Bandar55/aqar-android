package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityNotificationBinding;
import com.aqar55.fragments.Notification_Main_Fragment;

public class Notification_Activity extends AppCompatActivity {
    ActivityNotificationBinding activityNotificationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification_);
        Notification_Main_Fragment notification_main_fragment = new Notification_Main_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.notification_main_container,
                notification_main_fragment).commit();

    }
}
