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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;

import com.aqar55.ItemClick.ItemClickSupport;
import com.aqar55.R;
import com.aqar55.activitys.ProductDetailActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.adapters.All_List_Main_Adapter;
import com.aqar55.databinding.FragmentAllListBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Alltabs_List_fragment extends Fragment implements View.OnClickListener, ItemClickSupport.OnItemClickListener, All_List_Main_Adapter.OnLikeClickListener {
    private static final String TAG = "Alltabs_List_fragment";
    Response<GetPropertyListing> responsemain;
    private View view;
    private FragmentAllListBinding fragmentAlltabsMainFragmentBinding;
    private All_List_Main_Adapter all_list_main_adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private boolean priceltoh, pricehtol, sizeltoh, sizehtol;
    private boolean isFirst=false;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            priceltoh = intent.getBooleanExtra("priceltoh", false);
            pricehtol = intent.getBooleanExtra("pricehtol", false);
            sizeltoh = intent.getBooleanExtra("sizeltoh", false);
            sizehtol = intent.getBooleanExtra("sizehtol", false);
            getSortedData();
            //getDataFromApi();
        }
    };

    private void getSortedData() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> sortDataCall = api.getSortedData("all",ModelManager.modelManager().getCurrentUser().getId(), priceltoh, pricehtol, sizeltoh, sizehtol);

        sortDataCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {

                if (response.isSuccessful()) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.GONE);
                            responsemain = response;
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setLayoutManager(linearLayoutManager);
                            all_list_main_adapter = new All_List_Main_Adapter(getContext(), response, Alltabs_List_fragment.this);
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setAdapter(all_list_main_adapter);
                        } else {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                            fragmentAlltabsMainFragmentBinding.textView62.setText("No Data Found");
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                fragmentAlltabsMainFragmentBinding.textView62.setText("Server Error...");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAlltabsMainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_list, container, false);
        view = fragmentAlltabsMainFragmentBinding.getRoot();
        ItemClickSupport.addTo(fragmentAlltabsMainFragmentBinding.allListFragmentRecycler).setOnItemClickListener(this);
        mShimmerViewContainer = fragmentAlltabsMainFragmentBinding.shimmerViewContainer;
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        if(getArguments()!=null){


            if(BaseManager.getDataFromPreferences("mapData", HashMap.class)==null){
                getAllDataFromApi();
            }else {


                if (BaseManager.getDataFromPreferences("mapData", HashMap.class) != null && getArguments().getString("category").toString().isEmpty()) {
                    if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").isEmpty() && getArguments().getString("allData").isEmpty()) {

                        if (BaseManager.getDataFromPreferences("mapData", HashMap.class) != null) {
                            getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));

                        } else {
                            getAllDataFromApi();
                        }
                    } else {
                        getAllDataFromApi();
                    }
                } else {
                    if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").toString().isEmpty()) {
                        getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));
                    }
                }
            }


        }else {
            getAllDataFromApi();
        }

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("all"));


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
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.GONE);
                            responsemain = response;
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setLayoutManager(linearLayoutManager);
                            all_list_main_adapter = new All_List_Main_Adapter(getContext(), response, Alltabs_List_fragment.this);
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setAdapter(all_list_main_adapter);
                        } else {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                            fragmentAlltabsMainFragmentBinding.textView62.setText("No Data Found");
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });
    }




    private void getSaleDataApiFromFilter(HashMap<String,Object> hashMapData ) {

        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall = api.getSearchAll("sale", hashMapData.get("propertyId").toString(), hashMapData.get("category").toString(), hashMapData.get("purpose").toString(), hashMapData.get("available").toString(), hashMapData.get("bedrooms").toString(), hashMapData.get("bathrooms").toString(), hashMapData.get("kitchens").toString(), "", "",
                "", "", "", hashMapData.get("yearBuilt").toString(), hashMapData.get("balcony").toString(), hashMapData.get("garden").toString(), hashMapData.get("parking").toString(), hashMapData.get("modularKitchen").toString(), hashMapData.get("photos").toString(), hashMapData.get("videos").toString(),
                hashMapData.get("plotSizeMin").toString(),hashMapData.get("plotSizeMax").toString(),"",hashMapData.get("store").toString(),hashMapData.get("lift").toString(),hashMapData.get("duplex").toString(),hashMapData.get("furnished").toString(),hashMapData.get("airConditioning").toString());
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.GONE);
                            responsemain = response;
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setLayoutManager(linearLayoutManager);
                            all_list_main_adapter = new All_List_Main_Adapter(getContext(), response, Alltabs_List_fragment.this);
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setAdapter(all_list_main_adapter);
                        } else {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                            fragmentAlltabsMainFragmentBinding.textView62.setText("No Data Found");
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);


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
        BaseManager.saveDataIntoPreferences("secoundTime","isFirst");
        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getSearchByGoogle(type, lat, lon, ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getSearchByGoogleWithoutLogin(type, lat, lon);
        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.GONE);
                            responsemain = response;
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setLayoutManager(linearLayoutManager);
                            all_list_main_adapter = new All_List_Main_Adapter(getContext(), response, Alltabs_List_fragment.this);
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setAdapter(all_list_main_adapter);
                        } else {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                            fragmentAlltabsMainFragmentBinding.textView62.setText("No Data Found");
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }
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


    private void getAllDataFromApi() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        BaseManager.saveDataIntoPreferences("firstTime","isFirst");

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("");
        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.GONE);
                            responsemain = response;
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setLayoutManager(linearLayoutManager);
                            all_list_main_adapter = new All_List_Main_Adapter(getContext(), response, Alltabs_List_fragment.this);
                            fragmentAlltabsMainFragmentBinding.allListFragmentRecycler.setAdapter(all_list_main_adapter);
                        } else {
                            fragmentAlltabsMainFragmentBinding.textView62.setVisibility(View.VISIBLE);
                            fragmentAlltabsMainFragmentBinding.textView62.setText("No Data Found");
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
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
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


        if (responsemain.body().getData().get(position).getType().equalsIgnoreCase("sale")||responsemain.body().getData().get(position).getType().equalsIgnoreCase("rent")) {

            String  typeId = responsemain.body().getData().get(position).getProfessionalUserId();
            if(typeId!=null){
                Intent intent1 = new Intent(getContext(), ProductDetailSale.class);
                intent1.putExtra("propid", responsemain.body().getData().get(position).getId());
                intent1.putExtra("type","normal");
                startActivity(intent1);
            }else {
                Intent intent1 = new Intent(getContext(), ProductDetailSale.class);
                intent1.putExtra("propid", responsemain.body().getData().get(position).getId());
                intent1.putExtra("type","normal");
                startActivity(intent1);
            }
        }
       else if (responsemain.body().getData().get(position).getType().equalsIgnoreCase("rent")) {
            Intent intent2 = new Intent(getContext(), ProductDetailActivity.class);
            intent2.putExtra("position", position);
            intent2.putExtra("type",responsemain.body().getData().get(position).getType());
            intent2.putExtra("propid", responsemain.body().getData().get(position).getId());
            startActivity(intent2);
        }else if(responsemain.body().getData().get(position).getType().equalsIgnoreCase("business")) {
            Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
            intent.putExtra("_userId",responsemain.body().getData().get(position).getId());
            intent.putExtra("info_window_type","business");
            intent.putExtra("businessUserId",responsemain.body().getData().get(position).getUserid());

           startActivity(intent);
        }else{
            Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
            intent.putExtra("_userId",responsemain.body().getData().get(position).getId());
            intent.putExtra("info_window_type","professional");
            intent.putExtra("businessUserId",responsemain.body().getData().get(position).getUserid());
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void update(Response<GetPropertyListing> response) {

        if (response != null)
            all_list_main_adapter.updateData(response);
        all_list_main_adapter.notifyDataSetChanged();
    }

    @Override
    public void onLikeClick(int position, Response<GetPropertyListing> response) {
        String type = response.body().getData().get(position).getType();
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if (type.equalsIgnoreCase("sale") || type.equalsIgnoreCase("rent")) {
            if (response.body().getData().get(position).getLikedStatus().equalsIgnoreCase("yes")) {
                getUserDetailsCall = api.getLikePostPorBusiness(type, response.body().getData().get(position).getId(), ModelManager.modelManager().getCurrentUser().getId(), false);
            } else {
                getUserDetailsCall = api.getLikePostPorBusiness(type, response.body().getData().get(position).getId(), ModelManager.modelManager().getCurrentUser().getId(), true);
            }
        } else {
            if (response.body().getData().get(position).getLikedStatus().equalsIgnoreCase("yes")) {
                getUserDetailsCall = api.getLikePost(type, ModelManager.modelManager().getCurrentUser().getId(), response.body().getData().get(position).getId(), false);
            } else {
                getUserDetailsCall = api.getLikePost(type, ModelManager.modelManager().getCurrentUser().getId(), response.body().getData().get(position).getId(), true);

            }
        }


        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode()==200) {
                            if(!BaseManager.getDataFromPreferences("isFirst",String.class).toString().equalsIgnoreCase("firstTime")){
                                getSerchDataFromApi(getArguments().getString("fromActivity"),getArguments().getString("lat"),getArguments().getString("long"));
                            }else {
                                getAllDataFromApi();

                            }
                            Toaster.toast(response.body().getResponseMessage());

                        } else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }
}
