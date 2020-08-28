package com.aqar55.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;

import com.aqar55.R;
import com.aqar55.adapters.Professional_List_Adapter;
import com.aqar55.databinding.FragmentProfessionaltabMainBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Professionaltab_Main_Fragment extends Fragment {
    View view;
    FragmentProfessionaltabMainBinding fragmentProfessionaltabMainBinding;
    Professional_List_Adapter professional_list_adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    private boolean sortByName;
    private boolean sortByCategory;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            sortByName = intent.getBooleanExtra("sortByName",false);
            sortByCategory = intent.getBooleanExtra("sortByCategory",false);

            hitSortPropertyApi();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfessionaltabMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_professionaltab__main_, container, false);
        view = fragmentProfessionaltabMainBinding.getRoot();
        mShimmerViewContainer = fragmentProfessionaltabMainBinding.shimmerViewContainer;






        if(getArguments()!=null){
            if(BaseManager.getDataFromPreferences("mapData", HashMap.class)==null){
                if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                    getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                }else  if (!getArguments().getString("category").toString().isEmpty()) {
                    getRentDataCategoryApi(getArguments().getString("category"));

                }

                else {
                    hitProfessionalListApi();
                }
            }else {


                if (BaseManager.getDataFromPreferences("mapData", HashMap.class) != null && getArguments().getString("category").toString().isEmpty()) {
                    if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").isEmpty() && getArguments().getString("allData").isEmpty()) {

                        hitProfessionalDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));
                    } else {
                        hitProfessionalListApi();
                    }
                } else {
                    if (!getArguments().getString("category").toString().isEmpty()) {
                        getRentDataCategoryApi(getArguments().getString("category"));

                    } else if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").toString().isEmpty()) {
                        hitProfessionalDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));
                    }
                }
            }

        }else {
            hitProfessionalListApi();
        }

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("professional"));

        return view;
    }





    private void hitProfessionalDataApiFromFilter(HashMap<String,Object> hashMapData) {
        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall = api.getSearchBusinessProfessionalList("professional", hashMapData.get("fullName").toString(),
                hashMapData.get("professionalId").toString(), hashMapData.get("category").toString(), hashMapData.get("subCategory").toString(), hashMapData.get("area").toString(), hashMapData.get("speakLanguage").toString());
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(response.body().getResponseCode()==200){
                    List<GetPropertyListing.Data> dataList =  response.body().getSmalldata();
                    setUpRecclerView(dataList);
                }else {
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setVisibility(View.VISIBLE);
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setText(response.body().getResponseMessage());
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });
    }

    private void getSerchDataFromApi(String type, String lat, String lon) {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
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
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                MyDialog.getInstance(getActivity()).hideDialog();
                if(response.body().getResponseCode()==200){
                    List<GetPropertyListing.Data> dataList =  response.body().getData();
                    professional_list_adapter = new Professional_List_Adapter(getContext(),dataList);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setLayoutManager(linearLayoutManager);
                    fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setAdapter(professional_list_adapter);
                    professional_list_adapter.MyProfessionalList(() -> getSerchDataFromApi(type,lat,lon));
                }else {
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setVisibility(View.VISIBLE);
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setText(response.body().getResponseMessage());
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }


            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();
            }
        });
    }





    private void getRentDataCategoryApi(String categoty) {
        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("professional", categoty, ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("professional", categoty);

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                MyDialog.getInstance(getActivity()).hideDialog();
                if(response.body().getResponseCode()==200){
                    List<GetPropertyListing.Data> dataList =  response.body().getData();
                    professional_list_adapter = new Professional_List_Adapter(getContext(),dataList);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setLayoutManager(linearLayoutManager);
                    fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setAdapter(professional_list_adapter);
                    professional_list_adapter.MyProfessionalList(() -> getRentDataCategoryApi(categoty));
                }else {
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setVisibility(View.VISIBLE);
                    fragmentProfessionaltabMainBinding.tvErrorMsg.setText(response.body().getResponseMessage());
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();
            }
        });
    }

    private void hitProfessionalListApi() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        //Call<GetPropertyListing> getPropertyListingCall = api.getPropertyList("professional");

        Call<GetPropertyListing> getPropertyListingCall;
        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getPropertyListWithUserId("professional",ModelManager.modelManager().getCurrentUser().getId());
        }else {
            getPropertyListingCall = api.getPropertyList("professional");
        }

        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                    if(response.body().getResponseCode()==200){
                        List<GetPropertyListing.Data> dataList =  response.body().getData();
                        setUpRecclerView(dataList);
                    }else {
                        fragmentProfessionaltabMainBinding.tvErrorMsg.setVisibility(View.VISIBLE);
                        fragmentProfessionaltabMainBinding.tvErrorMsg.setText(response.body().getResponseMessage());
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }



            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {


            }
        });
    }

    private void setUpRecclerView(List<GetPropertyListing.Data> dataList) {
        professional_list_adapter = new Professional_List_Adapter(getContext(),dataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setLayoutManager(linearLayoutManager);
        fragmentProfessionaltabMainBinding.professionalListFragmentRecycler.setAdapter(professional_list_adapter);
        professional_list_adapter.MyProfessionalList(() -> hitProfessionalListApi());
    }

    public void hitSortPropertyApi(){
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> sortDataCall;
        if(ModelManager.modelManager().getCurrentUser()!=null)
            sortDataCall = api.getSortedData("professional",ModelManager.modelManager().getCurrentUser().getId(),sortByName,sortByCategory);
        else
            sortDataCall = api.getSortedData("professional","",sortByName,sortByCategory);

        sortDataCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {

                if (response.isSuccessful()) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    if (response.body() != null) {
                        if (response.body().getData()!=null) {
                            List<GetPropertyListing.Data> dataList =  response.body().getData();
                            setUpRecclerView(dataList);
                        } else {
                            Toast.makeText(getContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
    }

    public void update(List<GetPropertyListing.Data> response) {

        if (response != null)
            professional_list_adapter.updateData(response);
        professional_list_adapter.notifyDataSetChanged();
    }
}
