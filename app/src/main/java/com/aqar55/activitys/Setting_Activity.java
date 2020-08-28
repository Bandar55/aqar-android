package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aqar55.R;
import com.aqar55.databinding.ActivitySettingBinding;
import com.aqar55.fragments.RentToggleButtonSearch;
import com.aqar55.fragments.Setting_Professional_Tab_Fragment;
import com.aqar55.fragments.Setting_Sale_Toggle_Tab_Fragment;

public class Setting_Activity extends AppCompatActivity implements View.OnClickListener {
    ActivitySettingBinding activitySettingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_);

        Setting_Sale_Toggle_Tab_Fragment setting_sale_toggle_tab_fragment = new Setting_Sale_Toggle_Tab_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_sale_toggle_tab_fragment).commit();
        activitySettingBinding.saleSettingText.setOnClickListener(this);
        activitySettingBinding.rentSettingText.setOnClickListener(this);
        activitySettingBinding.professionalSettingText.setOnClickListener(this);
        activitySettingBinding.businessSettingText.setOnClickListener(this);
        activitySettingBinding.ivback.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sale_setting_text:
                Setting_Sale_Toggle_Tab_Fragment setting_sale_toggle_tab_fragment = new Setting_Sale_Toggle_Tab_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_sale_toggle_tab_fragment).commit();
                activitySettingBinding.view6.setVisibility(View.VISIBLE);
                activitySettingBinding.view7.setVisibility(View.GONE);
                activitySettingBinding.view9.setVisibility(View.GONE);
                activitySettingBinding.view10.setVisibility(View.GONE);
                break;

            case R.id.rent_setting_text:
                RentToggleButtonSearch rentToggleButtonSearch = new RentToggleButtonSearch();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, rentToggleButtonSearch).commit();
                activitySettingBinding.view7.setVisibility(View.VISIBLE);
                activitySettingBinding.view6.setVisibility(View.GONE);
                activitySettingBinding.view9.setVisibility(View.GONE);
                activitySettingBinding.view10.setVisibility(View.GONE);
                break;

            case R.id.business_setting_text:
                Setting_Professional_Tab_Fragment setting_business_tab_fragment = new Setting_Professional_Tab_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_business_tab_fragment).commit();
                Bundle  bundle=new Bundle();
                bundle.putString("from","business");
                setting_business_tab_fragment.setArguments(bundle);


                //Setting_Business_Tab_Fragment setting_business_tab_fragment = new Setting_Business_Tab_Fragment();
               // getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_business_tab_fragment).commit();
                activitySettingBinding.view10.setVisibility(View.VISIBLE);
                activitySettingBinding.view6.setVisibility(View.GONE);
                activitySettingBinding.view9.setVisibility(View.GONE);
                activitySettingBinding.view7.setVisibility(View.GONE);

                break;
            case R.id.professional_setting_text:
                Setting_Professional_Tab_Fragment setting_professional_tab_fragment = new Setting_Professional_Tab_Fragment();
                Bundle  bundle2=new Bundle();
                bundle2.putString("from","prof");
                setting_professional_tab_fragment.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container, setting_professional_tab_fragment).commit();
                activitySettingBinding.view9.setVisibility(View.VISIBLE);
                activitySettingBinding.view6.setVisibility(View.GONE);
                activitySettingBinding.view7.setVisibility(View.GONE);
                activitySettingBinding.view10.setVisibility(View.GONE);
                break;
            case R.id.ivback:
                finish();
                break;
        }

    }
}
