package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityMenuMainBottomNavBinding;
import com.aqar55.fragments.Menu_Main_Fragment;

public class Menu_Main_Bottom_Nav extends AppCompatActivity {
    ActivityMenuMainBottomNavBinding activityMenuMainBottomNavBinding;
    Menu_Main_Fragment menu_main_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMenuMainBottomNavBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu__main__bottom__nav);

        menu_main_fragment = new Menu_Main_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_main_container,
                menu_main_fragment).commit();

    }
}
