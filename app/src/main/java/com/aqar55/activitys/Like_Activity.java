package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aqar55.R;
import com.aqar55.databinding.ActivityLikeBinding;
import com.aqar55.fragments.Business_Like_Fragment;
import com.aqar55.fragments.Professional_Like_Fragment;
import com.aqar55.fragments.Rent_Like_Fragment;
import com.aqar55.fragments.Sale_Like_Fragment;

public class Like_Activity extends AppCompatActivity implements View.OnClickListener {
    ActivityLikeBinding activityLikeBinding;
    Sale_Like_Fragment sale_like_fragment = new Sale_Like_Fragment();
    Rent_Like_Fragment rent_like_fragment = new Rent_Like_Fragment();
    Professional_Like_Fragment professional_like_fragment = new Professional_Like_Fragment();
    Business_Like_Fragment business_like_fragment = new Business_Like_Fragment();

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLikeBinding = DataBindingUtil.setContentView(this, R.layout.activity_like_);
        fragmentManager = getSupportFragmentManager();
        sale_like_fragment = new Sale_Like_Fragment();
        Bundle bundle=new Bundle();
        bundle.putString("from","like");
        sale_like_fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.like_container, sale_like_fragment).commit();
        activityLikeBinding.saleLikeView.setVisibility(View.VISIBLE);

        activityLikeBinding.likeSearch.setOnClickListener(this);
        activityLikeBinding.saleLikeText.setOnClickListener(this);
        activityLikeBinding.rentLikeText.setOnClickListener(this);
        activityLikeBinding.professionalLikeText.setOnClickListener(this);
        activityLikeBinding.businessLikeText.setOnClickListener(this);
        activityLikeBinding.ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like_search:
                startActivity(new Intent(this, Setting_Activity.class));
                break;

            case R.id.sale_like_text:
                activityLikeBinding.saleLikeView.setVisibility(View.VISIBLE);
                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
                sale_like_fragment = new Sale_Like_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("from","like");
                sale_like_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.like_container, sale_like_fragment).commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.like_container, sale_like_fragment).commit();
                break;

            case R.id.rent_like_text:
                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
                activityLikeBinding.viewLikeRent.setVisibility(View.VISIBLE);
                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
                rent_like_fragment = new Rent_Like_Fragment();
                Bundle bundleRent=new Bundle();
                bundleRent.putString("from","like");
                rent_like_fragment.setArguments(bundleRent);
                fragmentManager.beginTransaction().replace(R.id.like_container, rent_like_fragment).commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.like_container, rent_like_fragment).addToBackStack(rent_like_fragment.getClass().getName()).commit();
                break;

            case R.id.professional_like_text:
                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
                activityLikeBinding.viewLikeProfessional.setVisibility(View.VISIBLE);
                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
                professional_like_fragment = new Professional_Like_Fragment();
                Bundle bundlePro=new Bundle();
                bundlePro.putString("from","like");
                professional_like_fragment.setArguments(bundlePro);
                fragmentManager.beginTransaction().replace(R.id.like_container, professional_like_fragment).commit();

                //getSupportFragmentManager().beginTransaction().replace(R.id.like_container, professional_like_fragment).addToBackStack(professional_like_fragment.getClass().getName()).commit();
                break;

            case R.id.business_like_text:
                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
                activityLikeBinding.viewLikeBusiness.setVisibility(View.VISIBLE);
                business_like_fragment = new Business_Like_Fragment();
                Bundle bundleBusiness=new Bundle();
                bundleBusiness.putString("from","like");
                business_like_fragment.setArguments(bundleBusiness);
                fragmentManager.beginTransaction().replace(R.id.like_container, business_like_fragment) .commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.like_container, business_like_fragment).addToBackStack(business_like_fragment.getClass().getName()).commit();
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
//                activityLikeBinding.saleLikeView.setVisibility(View.VISIBLE);
//                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
//            }else if(tagName.equalsIgnoreCase(rent_like_fragment.getClass().getName())){
//                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeRent.setVisibility(View.VISIBLE);
//                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
//            }else if(tagName.equalsIgnoreCase(professional_like_fragment.getClass().getName())){
//                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeProfessional.setVisibility(View.VISIBLE);
//                activityLikeBinding.viewLikeBusiness.setVisibility(View.GONE);
//            }else if(tagName.equalsIgnoreCase(business_like_fragment.getClass().getName())){
//                activityLikeBinding.saleLikeView.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeRent.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeProfessional.setVisibility(View.GONE);
//                activityLikeBinding.viewLikeBusiness.setVisibility(View.VISIBLE);
//            }
//        }else {
//            super.onBackPressed();
//        }

    }
}
