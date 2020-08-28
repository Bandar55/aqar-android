package com.aqar55.activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aqar55.R;
import com.aqar55.databinding.ActivityRecentBinding;
import com.aqar55.fragments.Business_Like_Fragment;
import com.aqar55.fragments.Professional_Like_Fragment;
import com.aqar55.fragments.Rent_Like_Fragment;
import com.aqar55.fragments.Sale_Like_Fragment;

public class Recent_Activity extends AppCompatActivity implements View.OnClickListener {
    ActivityRecentBinding activityRecentBinding;
    Sale_Like_Fragment sale_like_fragment = new Sale_Like_Fragment();
    Rent_Like_Fragment rent_like_fragment = new Rent_Like_Fragment();
    Professional_Like_Fragment professional_like_fragment = new Professional_Like_Fragment();
    Business_Like_Fragment business_like_fragment = new Business_Like_Fragment();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecentBinding = DataBindingUtil.setContentView(this, R.layout.activity_recent_);
        fragmentManager = getSupportFragmentManager();
        sale_like_fragment = new Sale_Like_Fragment();
        Bundle bundleSale=new Bundle();
        bundleSale.putString("from","recent");
        sale_like_fragment.setArguments(bundleSale);
        fragmentManager.beginTransaction().replace(R.id.recent_container, sale_like_fragment).commit();
        activityRecentBinding.saleRecentView.setVisibility(View.VISIBLE);
        activityRecentBinding.saleRecentText.setOnClickListener(this);
        activityRecentBinding.rentRecentText.setOnClickListener(this);
        activityRecentBinding.professionalRecentText.setOnClickListener(this);
        activityRecentBinding.businessRecentText.setOnClickListener(this);
        activityRecentBinding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sale_recent_text:
                activityRecentBinding.saleRecentView.setVisibility(View.VISIBLE);
                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
                sale_like_fragment = new Sale_Like_Fragment();
                Bundle bundleSale=new Bundle();
                bundleSale.putString("from","recent");
                sale_like_fragment.setArguments(bundleSale);
                fragmentManager.beginTransaction().replace(R.id.recent_container, sale_like_fragment).commit();
                break;
            case R.id.rent_recent_text:
                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
                activityRecentBinding.viewRecentRent.setVisibility(View.VISIBLE);
                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
                rent_like_fragment = new Rent_Like_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("from","recent");
                rent_like_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.recent_container, rent_like_fragment).commit();
                break;
            case R.id.professional_recent_text:
                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
                activityRecentBinding.viewRecentProfessional.setVisibility(View.VISIBLE);
                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
                professional_like_fragment = new Professional_Like_Fragment();
                Bundle bundleProf=new Bundle();
                bundleProf.putString("from","recent");
                professional_like_fragment.setArguments(bundleProf);
                fragmentManager.beginTransaction().replace(R.id.recent_container, professional_like_fragment).commit();
                break;
            case R.id.business_recent_text:
                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
                activityRecentBinding.viewRecentBusiness.setVisibility(View.VISIBLE);
                business_like_fragment = new Business_Like_Fragment();
                Bundle bundleBusiness=new Bundle();
                bundleBusiness.putString("from","recent");
                business_like_fragment.setArguments(bundleBusiness);
                fragmentManager.beginTransaction().replace(R.id.recent_container, business_like_fragment).commit();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        int entryCount = fragmentManager.getBackStackEntryCount();
//        if(entryCount>0){
//            String tagName = fragmentManager.getBackStackEntryAt(entryCount-1).getName();
//            if(tagName.equalsIgnoreCase(sale_like_fragment.getClass().getName())){
//                activityRecentBinding.saleRecentView.setVisibility(View.VISIBLE);
//                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
//
//            }else if(tagName.equalsIgnoreCase(rent_like_fragment.getClass().getName())){
//                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentRent.setVisibility(View.VISIBLE);
//                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
//
//            }else if(tagName.equalsIgnoreCase(professional_like_fragment.getClass().getName())){
//                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentProfessional.setVisibility(View.VISIBLE);
//                activityRecentBinding.viewRecentBusiness.setVisibility(View.GONE);
//
//            }else if(tagName.equalsIgnoreCase(business_like_fragment.getClass().getName())){
//                activityRecentBinding.saleRecentView.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentRent.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentProfessional.setVisibility(View.GONE);
//                activityRecentBinding.viewRecentBusiness.setVisibility(View.VISIBLE);
//            }
//        }else {
//            super.onBackPressed();
//        }
    }

}
