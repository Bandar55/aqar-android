package com.aqar55.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aqar55.helper.Toaster;
import com.aqar55.helper.Utils;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.UpdateProfileModel;
import com.aqar55.utill.MyDialog;
import com.bumptech.glide.Glide;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.activitys.ActivityMyProfessionalProfile;
import com.aqar55.activitys.Contact_Admin_Activity;
import com.aqar55.activitys.Like_Activity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.activitys.MainActivity;
import com.aqar55.activitys.Manage_Posted_Property;
import com.aqar55.activitys.Post_Property_Main;
import com.aqar55.activitys.Recent_Activity;
import com.aqar55.activitys.Settings_Activity;
import com.aqar55.databinding.LayoutMenuBottomNavBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.TotalPropertyOfUserModel;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Main_Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Menu_Main_Fragment";
    private View view;
    private LayoutMenuBottomNavBinding layoutMenuBottomNavBinding;
    private String businessId = "";
    private String ProfessionalId = "";
    private TotalPropertyOfUserModel totalPropertyOfUserModelResponse;
    private Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutMenuBottomNavBinding = DataBindingUtil.inflate(inflater, R.layout.layout_menu_bottom_nav, container, false);
        view = layoutMenuBottomNavBinding.getRoot();
        if (CommonClass.getPreferencesBoolean(getContext(), "islogin")) {
            layoutMenuBottomNavBinding.loginbutton.setVisibility(View.GONE);
            layoutMenuBottomNavBinding.userdetaillayout.setVisibility(View.VISIBLE);
            layoutMenuBottomNavBinding.profile.setVisibility(View.VISIBLE);

        } else {
            layoutMenuBottomNavBinding.loginbutton.setVisibility(View.VISIBLE);
            layoutMenuBottomNavBinding.userdetaillayout.setVisibility(View.GONE);
            layoutMenuBottomNavBinding.profile.setVisibility(View.INVISIBLE);
        }

        layoutMenuBottomNavBinding.layoutLike.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout4MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout2MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout15MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout12MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout9MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout7MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout8MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout10MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout11MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.closeDrawer.setOnClickListener(this);
        layoutMenuBottomNavBinding.loginbutton.setOnClickListener(this);
        layoutMenuBottomNavBinding.freePostProperty.setOnClickListener(this);

        layoutMenuBottomNavBinding.ivFacebook.setOnClickListener(this);
        layoutMenuBottomNavBinding.ivTwitter.setOnClickListener(this);
        layoutMenuBottomNavBinding.ivYoutube.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout14MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.layout13MenuBottomNev.setOnClickListener(this);
        layoutMenuBottomNavBinding.tvManagmentByBusiness.setOnClickListener(this);
        layoutMenuBottomNavBinding.tvManagmentByProfessional.setOnClickListener(this);

        getUserDatails();

        return view;
    }

    private void getUserDatails() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<TotalPropertyOfUserModel> getUserDetailsCall = api.getTotalPropertyOfUser(CommonClass.getPreferencesString(getContext(), "userid"));
        getUserDetailsCall.enqueue(new Callback<TotalPropertyOfUserModel>() {
            @Override
            public void onResponse(Call<TotalPropertyOfUserModel> call, Response<TotalPropertyOfUserModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getUser() != null) {
                            totalPropertyOfUserModelResponse = response.body();
                            BaseManager.saveDataIntoPreferences(response.body().getUser().getBusinessprofile(), "businessProfile");
                            BaseManager.saveDataIntoPreferences(response.body().getUser().getProfessionalprofile(), "professionalProfile");
                            layoutMenuBottomNavBinding.name.setText(response.body().getUser().getFullname());
                            layoutMenuBottomNavBinding.professionalId.setText("Professional ID " + response.body().getUser().getProfessionalid());
                            layoutMenuBottomNavBinding.businessId.setText("Business ID " + response.body().getUser().getBusinessid());
                            layoutMenuBottomNavBinding.tvManageProperty.setText("" + response.body().getProperty());
                            if (response.body().getDays() != null)
                                setImageProfile(response);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<TotalPropertyOfUserModel> call, Throwable t) {

            }
        });

    }

    private void setImageProfile(Response<TotalPropertyOfUserModel> response) {
        Log.d(TAG, "setImageProfile: " + response.body().getUser().getProfileimage() + ModelManager.modelManager().getCurrentUser().getId()/*CommonClass.getPreferencesString(getContext(), "userid")*/);

        Glide
                .with(mActivity)
                .load(response.body().getUser().getProfileimage())
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.placeholder_gray)))
                .error(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.placeholder_gray)))
                .dontAnimate()
                .into(layoutMenuBottomNavBinding.profile);
        layoutMenuBottomNavBinding.tvManageProperty.setText("" + response.body().getProperty());

        businessId = response.body().getUser().getBusiness_id();
        ProfessionalId = response.body().getUser().getProfessional_id();
        if (!response.body().getUser().getBusinessprofile()) {
            layoutMenuBottomNavBinding.tvManagmentByBusinessText.setText("Post  My Business Profile");
            // layoutMenuBottomNavBinding.tvManagmentByBusiness.setVisibility(View.INVISIBLE);
            layoutMenuBottomNavBinding.layout11MenuBottomNev.setBackgroundColor(getResources().getColor(R.color.white));
            layoutMenuBottomNavBinding.tvManagmentByBusiness.setVisibility(View.INVISIBLE);


        } else {
            layoutMenuBottomNavBinding.tvManagmentByBusiness.setVisibility(View.VISIBLE);
            layoutMenuBottomNavBinding.layout11MenuBottomNev.setBackgroundColor(getResources().getColor(R.color.light_lue_menu));
            layoutMenuBottomNavBinding.tvManagmentByBusiness.setVisibility(View.VISIBLE);
            layoutMenuBottomNavBinding.tvManagmentByBusinessText.setText("Post and Manage My Business Profile");
            layoutMenuBottomNavBinding.tvManagmentByBusiness.setText("Update " + response.body().getDays().getBusinessremainingdays() + "/" + response.body().getUser().getTotaldays() + " days");

        }
        if (!response.body().getUser().getProfessionalprofile()) {
            layoutMenuBottomNavBinding.tvManagePropertyText.setText("Post  My Professional Profile");
            // layoutMenuBottomNavBinding.tvManagePropertyText.setVisibility(View.INVISIBLE);
            layoutMenuBottomNavBinding.layout10MenuBottomNev.setBackgroundColor(getResources().getColor(R.color.white));
            layoutMenuBottomNavBinding.tvManagmentByProfessional.setVisibility(View.INVISIBLE);
        } else {
            // layoutMenuBottomNavBinding.tvManagePropertyText.setVisibility(View.VISIBLE);
            layoutMenuBottomNavBinding.tvManagmentByProfessional.setVisibility(View.VISIBLE);

            layoutMenuBottomNavBinding.layout10MenuBottomNev.setBackgroundColor(getResources().getColor(R.color.light_lue_menu));
            layoutMenuBottomNavBinding.tvManagePropertyText.setText("post and Manage My Professional Profile");
            layoutMenuBottomNavBinding.tvManagmentByProfessional.setText("Update " + response.body().getDays().getProfremainingdays() + "/" + response.body().getUser().getTotaldays() + " days");

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_like:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                } else {
                    startActivity(new Intent(getContext(), Like_Activity.class));
                }

                break;
            case R.id.layout_4_menu_bottom_nev:

                    startActivity(new Intent(getContext(), Recent_Activity.class));



                break;
            case R.id.layout_2_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    Intent mIntent = new Intent(getContext(), MainActivity.class);
                    getActivity().finishAffinity();
                    startActivity(mIntent);
                }


                break;
            case R.id.layout_15_menu_bottom_nev:



                if(ModelManager.modelManager().getCurrentUser()==null){
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                }else {
                    startActivity(new Intent(getContext(), Contact_Admin_Activity.class));
                }

                break;
            case R.id.layout_12_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    startActivity(new Intent(getContext(), Settings_Activity.class));
                }

                break;
            case R.id.layout_9_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                } else {
                    startActivity(new Intent(getContext(), My_Profile_Fragment.class));
                }


                break;
            case R.id.layout_7_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    startActivity(new Intent(getContext(), Post_Property_Main.class));
                }

                break;

            case R.id.freePostProperty:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    startActivity(new Intent(getContext(), Post_Property_Main.class));
                }

                break;
            case R.id.layout_8_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

                } else {
                    startActivity(new Intent(getContext(), Manage_Posted_Property.class));
                }


                break;
            case R.id.layout_10_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                } else {
                    startActivity(ActivityMyProfessionalProfile.getIntent(getActivity(), "professional", ProfessionalId, totalPropertyOfUserModelResponse));
                }
                break;
            case R.id.layout_11_menu_bottom_nev:
                if (ModelManager.modelManager().getCurrentUser() == null) {
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                } else {
                    startActivity(ActivityMyProfessionalProfile.getIntent(getActivity(), "business", businessId, totalPropertyOfUserModelResponse));
                }
                break;
            case R.id.close_drawer:
                ((MainActivity) getActivity()).closeDrawer();
                break;
            case R.id.loginbutton:
                Intent intent = new Intent(getContext(), Login_Signup_Button_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                break;


            case R.id.iv_youtube:


                    openBrowser("https://www.youtube.com/channel/UCV6X789T18rxbagDuxAS92Q");

                break;


            case R.id.iv_twitter:

                    openBrowser("https://twitter.com/Aqar55C");

                break;

            case R.id.iv_facebook:

                    openBrowser("https://www.facebook.com/pages/category/Real-Estate-Service/AQar55com-2326605297658310/");


                break;


            case R.id.layout_14_menu_bottom_nev:
                share();
                break;

            case R.id.layout_13_menu_bottom_nev:
                ratePRoperty();
                break;

            case R.id.tv_managment_by_professional:
                updateProBusiness(ProfessionalId);
                break;
            case R.id.tv_managment_by_business:
                updateProBusiness(businessId);
                break;


        }

    }

    private void ratePRoperty(){
        String url = "https://play.google.com/store/apps/details?id=com.aqar55&amp;hl=en_US";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getString(R.string.body_foodie);
        String shareSub = getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void openBrowser(String facebookUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(browserIntent);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }



    private void updateProBusiness(String businessId) {

        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        // Call<GetPropertyListing> getPropertyListingCall = api.getPropertyList("rent");
        Call<UpdateProfileModel> getPropertyListingCall;
        getPropertyListingCall = api.updateBusinessOrProfessional(businessId);

        getPropertyListingCall.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileModel> call, @NonNull Response<UpdateProfileModel> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    Toaster.toast(response.body().getResponseMessage());
                    if(response.body().getData().getType().equalsIgnoreCase("business")){
                        layoutMenuBottomNavBinding.tvManagmentByBusiness.setText("Update " + response.body().getData().getRemainingdays() + "/" + response.body().getData().getTotaldays() + " days");

                    }else {
                        layoutMenuBottomNavBinding.tvManagmentByProfessional.setText("Update " + response.body().getData().getRemainingdays() + "/" + response.body().getData().getTotaldays() + " days");

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileModel> call, @NonNull Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });


    }


}
