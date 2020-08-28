package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.constant.Constants;
import com.aqar55.databinding.ActivityListMainBinding;
import com.aqar55.fragments.Alltabs_List_fragment;
import com.aqar55.fragments.BottomSheetAlphabetically;
import com.aqar55.fragments.BottomSheetSortProduct;
import com.aqar55.fragments.Businesstab_Main_Fragment;
import com.aqar55.fragments.Professionaltab_Main_Fragment;
import com.aqar55.fragments.Rent_Main_Fragment;
import com.aqar55.fragments.Saletab_main_fragment;
import com.aqar55.helper.ModelManager;
import com.aqar55.intefaces.SetIconInMain;
import com.aqar55.utill.Animation;

import static android.app.Activity.RESULT_OK;


public class List_Main_Activity extends Fragment implements View.OnClickListener {
    private static final String TAG = "List_Main_Activity";
    private ActivityListMainBinding activityListMainBinding;
    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private SetIconInMain setIconInMain;
    private String string = null;
    private String tabSelection;
    private String latitude = "";
    private String longitude = "";

    public static Intent getIntent(Context context) {
        return new Intent(context, List_Main_Activity.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityListMainBinding = DataBindingUtil.inflate(inflater, R.layout.activity_list__main_, container, false);
        view = activityListMainBinding.getRoot();
        tabSelection = "all";


        activityListMainBinding.allListText.setOnClickListener(this);
        activityListMainBinding.saleListText.setOnClickListener(this);
        activityListMainBinding.rentListText.setOnClickListener(this);
        activityListMainBinding.professionalListText.setOnClickListener(this);
        activityListMainBinding.businessListText.setOnClickListener(this);
        activityListMainBinding.ivBack.setOnClickListener(this);
        activityListMainBinding.filterIcon.setOnClickListener(this);
        activityListMainBinding.ivPost.setOnClickListener(this);
        activityListMainBinding.searchlist.setOnClickListener(this);



        if (getArguments() != null) {
            if (getArguments().getString("fromActivity").equalsIgnoreCase("all") && getArguments().getString("lat").isEmpty()) {
                tabSelection = "all";
                Alltabs_List_fragment alltabs_list_fragment = new Alltabs_List_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("fromActivity", tabSelection);
                bundle.putString("category", getArguments().getString("category"));
                bundle.putString("lat", getArguments().getString("lat"));
                bundle.putString("long", getArguments().getString("long"));
                bundle.putString("allData", getArguments().getString("allData"));
                alltabs_list_fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.list_container, alltabs_list_fragment, Alltabs_List_fragment.class.getName()).addToBackStack(Alltabs_List_fragment.class.getName()).commit();
                allIndicator();


            }else if (getArguments().getString("fromActivity").equalsIgnoreCase("sale") && getArguments().getString("lat").isEmpty()) {
                tabSelection = "sale";

                Saletab_main_fragment saletab_main_fragment = new Saletab_main_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("fromActivity", tabSelection);
                bundle.putString("category", getArguments().getString("category"));
                bundle.putString("lat", getArguments().getString("lat"));
                bundle.putString("long", getArguments().getString("long"));
                bundle.putString("allData", getArguments().getString("allData"));
                saletab_main_fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.list_container, saletab_main_fragment, Saletab_main_fragment.class.getName()).addToBackStack(Saletab_main_fragment.class.getName()).commit();
                saleIndicator();


            } else if (getArguments().getString("fromActivity").equalsIgnoreCase("rent") && getArguments().getString("lat").isEmpty()) {
                tabSelection = "rent";
                Rent_Main_Fragment rent_main_fragment = new Rent_Main_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("fromActivity", tabSelection);
                bundle.putString("category", getArguments().getString("category"));
                bundle.putString("lat", getArguments().getString("lat"));
                bundle.putString("long", getArguments().getString("long"));
                bundle.putString("allData", getArguments().getString("allData"));
                rent_main_fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.list_container, rent_main_fragment, Rent_Main_Fragment.class.getName()).addToBackStack(Rent_Main_Fragment.class.getName()).commit();
                rentIndicator();

            } else if (getArguments().getString("fromActivity").equalsIgnoreCase("professional") && getArguments().getString("lat").isEmpty()) {
                tabSelection = "professional";
                Professionaltab_Main_Fragment professionaltab_main_fragment = new Professionaltab_Main_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("fromActivity", tabSelection);
                bundle.putString("category", getArguments().getString("category"));
                bundle.putString("lat", getArguments().getString("lat"));
                bundle.putString("long", getArguments().getString("long"));
                bundle.putString("allData", getArguments().getString("allData"));
                professionaltab_main_fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.list_container, professionaltab_main_fragment, Professionaltab_Main_Fragment.class.getName()).addToBackStack(Professionaltab_Main_Fragment.class.getName()).commit();
                professionalIndicator();

            } else if (getArguments().getString("fromActivity").equalsIgnoreCase("business") && getArguments().getString("lat").isEmpty()) {
                tabSelection = "business";
                Businesstab_Main_Fragment businesstab_main_fragment = new Businesstab_Main_Fragment();

                Bundle bundle = new Bundle();
                bundle.putString("fromActivity", tabSelection);
                bundle.putString("category", getArguments().getString("category"));
                bundle.putString("lat", getArguments().getString("lat"));
                bundle.putString("long", getArguments().getString("long"));
                bundle.putString("allData", getArguments().getString("allData"));
                businesstab_main_fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.list_container, businesstab_main_fragment, Businesstab_Main_Fragment.class.getName()).addToBackStack(Businesstab_Main_Fragment.class.getName()).commit();
                businessIndicator();

            } else if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {

                if (getArguments().getString("fromActivity").equalsIgnoreCase("sale")) {
                    tabSelection = "sale";
                    Saletab_main_fragment saletab_main_fragment = new Saletab_main_fragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("category", "");
                    bundle.putString("fromActivity", tabSelection);
                    bundle.putString("lat", getArguments().getString("lat"));
                    bundle.putString("long", getArguments().getString("long"));
                    saletab_main_fragment.setArguments(bundle);


                    getFragmentManager().beginTransaction().replace(R.id.list_container, saletab_main_fragment, Saletab_main_fragment.class.getName()).addToBackStack(Saletab_main_fragment.class.getName()).commit();
                    saleIndicator();
                    //getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                } else if (getArguments().getString("fromActivity").equalsIgnoreCase("rent")) {
                    tabSelection = "rent";
                    Rent_Main_Fragment rent_main_fragment = new Rent_Main_Fragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("category", "");
                    bundle.putString("fromActivity", tabSelection);
                    bundle.putString("lat", getArguments().getString("lat"));
                    bundle.putString("long", getArguments().getString("long"));
                    rent_main_fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.list_container, rent_main_fragment, Rent_Main_Fragment.class.getName()).addToBackStack(Rent_Main_Fragment.class.getName()).commit();
                    rentIndicator();
                    // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                } else if (getArguments().getString("fromActivity").equalsIgnoreCase("professional")) {

                    tabSelection = "professional";
                    Professionaltab_Main_Fragment professionaltab_main_fragment = new Professionaltab_Main_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("category", "");
                    bundle.putString("fromActivity", tabSelection);
                    bundle.putString("lat", getArguments().getString("lat"));
                    bundle.putString("long", getArguments().getString("long"));
                    professionaltab_main_fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.list_container, professionaltab_main_fragment, Professionaltab_Main_Fragment.class.getName()).addToBackStack(Professionaltab_Main_Fragment.class.getName()).commit();
                    professionalIndicator();
                    // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                } else if (getArguments().getString("fromActivity").equalsIgnoreCase("business")) {
                    tabSelection = "business";
                    Businesstab_Main_Fragment businesstab_main_fragment = new Businesstab_Main_Fragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("category", "");
                    bundle.putString("fromActivity", tabSelection);
                    bundle.putString("lat", getArguments().getString("lat"));
                    bundle.putString("long", getArguments().getString("long"));
                    businesstab_main_fragment.setArguments(bundle);


                    getFragmentManager().beginTransaction().replace(R.id.list_container, businesstab_main_fragment, Businesstab_Main_Fragment.class.getName()).addToBackStack(Businesstab_Main_Fragment.class.getName()).commit();
                    businessIndicator();
                    // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                }else {
                    tabSelection = "all";
                    Alltabs_List_fragment alltabs_list_fragment = new Alltabs_List_fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromActivity", tabSelection);
                    bundle.putString("category", getArguments().getString("category"));
                    bundle.putString("lat", getArguments().getString("lat"));
                    bundle.putString("long", getArguments().getString("long"));
                    bundle.putString("allData", getArguments().getString("allData"));
                    alltabs_list_fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.list_container, alltabs_list_fragment, Alltabs_List_fragment.class.getName()).addToBackStack(Alltabs_List_fragment.class.getName()).commit();
                    allIndicator();
                }


            } else {
                fragmentManager = getFragmentManager();
                fragment = new Alltabs_List_fragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.list_container, fragment, Alltabs_List_fragment.class.getName()).commit();
            }

        } else {
            fragmentManager = getFragmentManager();
            fragment = new Alltabs_List_fragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.list_container, fragment, Alltabs_List_fragment.class.getName()).commit();
        }

        return view;
    }

    private void getSerchDataFromApi(String type, String lat, String lon) {
        tabSelection = type;

        if (type.equalsIgnoreCase("sale")) {
            tabSelection = "sale";
            Saletab_main_fragment saletab_main_fragment = new Saletab_main_fragment();
            Bundle bundle = new Bundle();

            bundle.putString("category", "");
            bundle.putString("fromActivity", tabSelection);
            bundle.putString("lat", lat);
            bundle.putString("long", lon);
            saletab_main_fragment.setArguments(bundle);


            getFragmentManager().beginTransaction().replace(R.id.list_container, saletab_main_fragment, Saletab_main_fragment.class.getName()).addToBackStack(Saletab_main_fragment.class.getName()).commit();

            //getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

        } else if (type.equalsIgnoreCase("rent")) {
            tabSelection = "rent";
            Rent_Main_Fragment rent_main_fragment = new Rent_Main_Fragment();

            Bundle bundle = new Bundle();

            bundle.putString("category", "");
            bundle.putString("fromActivity", tabSelection);
            bundle.putString("lat", lat);
            bundle.putString("long", lon);
            rent_main_fragment.setArguments(bundle);

            getFragmentManager().beginTransaction().replace(R.id.list_container, rent_main_fragment, Rent_Main_Fragment.class.getName()).addToBackStack(Rent_Main_Fragment.class.getName()).commit();

            // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

        } else if (type.equalsIgnoreCase("professional")) {

            tabSelection = "professional";
            Professionaltab_Main_Fragment professionaltab_main_fragment = new Professionaltab_Main_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "");
            bundle.putString("fromActivity", tabSelection);
            bundle.putString("lat", lat);
            bundle.putString("long", lon);
            professionaltab_main_fragment.setArguments(bundle);

            getFragmentManager().beginTransaction().replace(R.id.list_container, professionaltab_main_fragment, Professionaltab_Main_Fragment.class.getName()).addToBackStack(Professionaltab_Main_Fragment.class.getName()).commit();

            // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

        } else if (type.equalsIgnoreCase("business")) {
            tabSelection = "business";
            Businesstab_Main_Fragment businesstab_main_fragment = new Businesstab_Main_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "");
            bundle.putString("fromActivity", tabSelection);
            bundle.putString("lat", lat);
            bundle.putString("long", lon);
            businesstab_main_fragment.setArguments(bundle);

            getFragmentManager().beginTransaction().replace(R.id.list_container, businesstab_main_fragment, Businesstab_Main_Fragment.class.getName()).addToBackStack(Businesstab_Main_Fragment.class.getName()).commit();

            // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));


        }else {
            tabSelection = "all";
            Alltabs_List_fragment businesstab_main_fragment = new Alltabs_List_fragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "");
            bundle.putString("fromActivity", tabSelection);
            bundle.putString("lat", lat);
            bundle.putString("long", lon);
            businesstab_main_fragment.setArguments(bundle);

            getFragmentManager().beginTransaction().replace(R.id.list_container, businesstab_main_fragment, Businesstab_Main_Fragment.class.getName()).addToBackStack(Businesstab_Main_Fragment.class.getName()).commit();

            // getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getS
        }




      /*  MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getSearchByGoogle(type, lat, lon, ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getSearchByGoogleWithoutLogin(type, lat, lon);

        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                                activityListMainBinding.searchlist.setText(null);
                                activityListMainBinding.searchlist.clearFocus();
                            if (tabSelection.equalsIgnoreCase("all")) {
                                Alltabs_List_fragment alltabs_list_fragment = (Alltabs_List_fragment) getFragmentManager().findFragmentByTag(Alltabs_List_fragment.class.getName());
                                alltabs_list_fragment.update(response);
                            } else if (tabSelection.equalsIgnoreCase("sale")) {
                                Saletab_main_fragment saletab_main_fragment = (Saletab_main_fragment) getFragmentManager().findFragmentByTag(Saletab_main_fragment.class.getName());
                                saletab_main_fragment.update(response);
                            } else if (tabSelection.equalsIgnoreCase("rent")) {
                                Rent_Main_Fragment rent_main_fragment = (Rent_Main_Fragment) getFragmentManager().findFragmentByTag(Rent_Main_Fragment.class.getName());
                               // rent_main_fragment.update(response);
                            } else if (tabSelection.equalsIgnoreCase("professional")) {
                                Professionaltab_Main_Fragment professionaltab_main_fragment = (Professionaltab_Main_Fragment) getFragmentManager().findFragmentByTag(Professionaltab_Main_Fragment.class.getName());
                                professionaltab_main_fragment.update(response.body().getData());
                            } else if (tabSelection.equalsIgnoreCase("business")) {
                                Businesstab_Main_Fragment businesstab_main_fragment = (Businesstab_Main_Fragment) getFragmentManager().findFragmentByTag(Businesstab_Main_Fragment.class.getName());
                                businesstab_main_fragment.update(response.body().getData());
                            }

                }else {
                    Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                          MyDialog.getInstance(getActivity()).hideDialog();
            }
        });*/
    }


    private void clearBackStack() {
        final FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                clearBackStack();
                setIconInMain.showIcon();
                break;
            case R.id.all_list_text:
                tabSelection = "all";
                allIndicator();

                getFragmentManager().beginTransaction().replace(R.id.list_container, new Alltabs_List_fragment(), Alltabs_List_fragment.class.getName()).addToBackStack(Alltabs_List_fragment.class.getName()).commit();
                allIndicator();
                break;
            case R.id.sale_list_text:
                tabSelection = "sale";

                getFragmentManager().beginTransaction().replace(R.id.list_container, new Saletab_main_fragment(), Saletab_main_fragment.class.getName()).addToBackStack(Saletab_main_fragment.class.getName()).commit();
                saleIndicator();
                break;
            case R.id.rent_list_text:
                tabSelection = "rent";
                getFragmentManager().beginTransaction().replace(R.id.list_container, new Rent_Main_Fragment(), Rent_Main_Fragment.class.getName()).addToBackStack(Rent_Main_Fragment.class.getName()).commit();
                rentIndicator();

                break;
            case R.id.professional_list_text:
                tabSelection = "professional";
                getFragmentManager().beginTransaction().replace(R.id.list_container, new Professionaltab_Main_Fragment(), Professionaltab_Main_Fragment.class.getName()).addToBackStack(Professionaltab_Main_Fragment.class.getName()).commit();
                professionalIndicator();
                break;
            case R.id.business_list_text:
                tabSelection = "business";
                getFragmentManager().beginTransaction().replace(R.id.list_container, new Businesstab_Main_Fragment(), Businesstab_Main_Fragment.class.getName()).addToBackStack(Businesstab_Main_Fragment.class.getName()).commit();
                businessIndicator();
                break;
            case R.id.filter_icon:
             /*   Bundle bundle = new Bundle();
                bundle.putString("tab", tabSelection);
                BottomSheetAlphabetically bottomSheetAlphabetically = new BottomSheetAlphabetically();
                bottomSheetAlphabetically.setArguments(bundle);
                bottomSheetAlphabetically.show(fragmentManager, "BottomSheetAlphabetically");
*/



               /* int count = fragmentManager.getBackStackEntryCount();
                String fragName = fragmentManager.getBackStackEntryAt(count - 1).getName();
*/
                if (tabSelection.equalsIgnoreCase("professional") || tabSelection.equalsIgnoreCase("business")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("tab", tabSelection);
                    BottomSheetAlphabetically bottomSheetAlphabetically = new BottomSheetAlphabetically();
                    bottomSheetAlphabetically.setArguments(bundle1);
                    bottomSheetAlphabetically.show(getChildFragmentManager(),bottomSheetAlphabetically.getTag());
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString("tab",tabSelection);
                    BottomSheetSortProduct bottomSheetSortProduct = new BottomSheetSortProduct();
                    bottomSheetSortProduct.setArguments(bundle);
                    bottomSheetSortProduct.show(getChildFragmentManager(),bottomSheetSortProduct.getTag());
                  /*  Bundle bundle = new Bundle();
                    bundle.putString("tab", tabSelection);
                    BottomSheetSortProduct bottomSheetFragment = new BottomSheetSortProduct();
                    bottomSheetFragment.setArguments(bundle);
                    bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());*/
                }

                break;

            case R.id.iv_post:

                if(ModelManager.modelManager().getCurrentUser()!=null)
                startActivity(new Intent(getContext(), Post_Property_Main.class));
                else
                    startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));
                break;

            case R.id.searchlist:
                startActivityForResult(AddressPicker.getIntent(getContext()), 100);

                break;
        }

    }

    public void businessIndicator() {
//        activityListMainBinding.allListView.setVisibility(View.GONE);
//        activityListMainBinding.saleListView.setVisibility(View.GONE);
//        activityListMainBinding.viewListRent.setVisibility(View.GONE);
//        activityListMainBinding.viewListProfessional.setVisibility(View.GONE);
//        activityListMainBinding.viewListBusiness.setVisibility(View.VISIBLE);


        Animation.RightToLeft_And_Show(activityListMainBinding.viewListBusiness, activityListMainBinding.businessListText, Constants.animDuration);

        Animation.LeftToRight_And_Exit(activityListMainBinding.saleListView, activityListMainBinding.saleListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.allListView, activityListMainBinding.allListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListRent, activityListMainBinding.rentListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListProfessional, activityListMainBinding.professionalListText, Constants.animDuration);
    }

    public void professionalIndicator() {
//        activityListMainBinding.allListView.setVisibility(View.GONE);
//        activityListMainBinding.saleListView.setVisibility(View.GONE);
//        activityListMainBinding.viewListRent.setVisibility(View.GONE);
//        activityListMainBinding.viewListProfessional.setVisibility(View.VISIBLE);
//        activityListMainBinding.viewListBusiness.setVisibility(View.GONE);

        Animation.RightToLeft_And_Show(activityListMainBinding.viewListProfessional, activityListMainBinding.professionalListText, Constants.animDuration);

        Animation.LeftToRight_And_Exit(activityListMainBinding.saleListView, activityListMainBinding.saleListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.allListView, activityListMainBinding.allListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListRent, activityListMainBinding.rentListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListBusiness, activityListMainBinding.businessListText, Constants.animDuration);

    }

    public void rentIndicator() {


        Animation.RightToLeft_And_Show(activityListMainBinding.viewListRent, activityListMainBinding.rentListText, Constants.animDuration);

        Animation.LeftToRight_And_Exit(activityListMainBinding.saleListView, activityListMainBinding.saleListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.allListView, activityListMainBinding.allListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListProfessional, activityListMainBinding.professionalListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListBusiness, activityListMainBinding.businessListText, Constants.animDuration);
    }

    public void allIndicator() {

        Animation.RightToLeft_And_Show(activityListMainBinding.allListView, activityListMainBinding.allListText, Constants.animDuration);

        Animation.LeftToRight_And_Exit(activityListMainBinding.saleListView, activityListMainBinding.saleListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListRent, activityListMainBinding.rentListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListProfessional, activityListMainBinding.professionalListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListBusiness, activityListMainBinding.businessListText, Constants.animDuration);
    }

    public void saleIndicator() {

//       activityListMainBinding.allListView.setVisibility(View.GONE);
        Animation.LeftToRight_And_Exit(activityListMainBinding.allListView, activityListMainBinding.allListText, Constants.animDuration);
        Animation.RightToLeft_And_Show(activityListMainBinding.saleListView, activityListMainBinding.saleListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListRent, activityListMainBinding.rentListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListProfessional, activityListMainBinding.professionalListText, Constants.animDuration);
        Animation.LeftToRight_And_Exit(activityListMainBinding.viewListBusiness, activityListMainBinding.businessListText, Constants.animDuration);


//        activityListMainBinding.viewListRent.setVisibility(View.GONE);
//        activityListMainBinding.viewListProfessional.setVisibility(View.GONE);
//        activityListMainBinding.viewListBusiness.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        setIconInMain = (SetIconInMain) activity;
    }

    public void showIndicator(String sale) {


        if(fragmentManager==null){

           startActivity(new Intent(getActivity(),MainActivity.class));
           getActivity().finishAffinity();
           return;
        }


        int count = fragmentManager.getBackStackEntryCount();
        string = sale;

        if (string.equalsIgnoreCase("sale_list_fragment")) {
            for (int i = 1; i < count; i++) {
                String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - i).getName();
                Log.d(TAG, "showIndicator_sale: " + fragmentManager.getBackStackEntryCount() + tag);
                count = count - 1;
            }
            allIndicator();

        }
        if (string.equalsIgnoreCase("rent_list_fragment")) {

            for (int i = 1; i < count; i++) {
                String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - i).getName();
                Log.d(TAG, "showIndicator:  rent" + fragmentManager.getBackStackEntryCount() + tag);
                count = count - 1;
            }
            saleIndicator();
        }
        if (string.equalsIgnoreCase("professional_list_fragment")) {
            for (int i = 1; i < count; i++) {
                String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - i).getName();
                Log.d(TAG, "showIndicator: professional " + fragmentManager.getBackStackEntryCount() + tag);
                count = count - 1;
            }
            rentIndicator();
        }
        if (string.equalsIgnoreCase("business_list_fragment")) {
            for (int i = 1; i < count; i++) {
                String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - i).getName();
                Log.d(TAG, "showIndicator: business" + fragmentManager.getBackStackEntryCount() + tag);
                count = count - 1;
            }
            professionalIndicator();
        }
    }

    private void showIndicatorOnbackStack(String string) {
        if (string.equalsIgnoreCase(Saletab_main_fragment.class.getName())) {
            saleIndicator();
        }
        if (string.equalsIgnoreCase(Rent_Main_Fragment.class.getName())) {
            rentIndicator();
        }
        if (string.equalsIgnoreCase(Professionaltab_Main_Fragment.class.getName())) {
            professionalIndicator();
        }
        if (string.equalsIgnoreCase(Businesstab_Main_Fragment.class.getName())) {
            businessIndicator();
        }
        if (string.equalsIgnoreCase(List_Main_Activity.class.getName())) {
            allIndicator();
        }
    }

    public String getBackStackFrag(int i) {
        FragmentManager.BackStackEntry backStackEntry = null;
        backStackEntry = fragmentManager.getBackStackEntryAt(i);
        return backStackEntry.getName();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            latitude = data.getStringExtra("LAT");
            longitude = data.getStringExtra("LONG");
            getSerchDataFromApi(tabSelection, latitude, longitude);


        }

    }

}
