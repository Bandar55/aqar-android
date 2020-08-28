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
import com.aqar55.adapters.RentListMainAdapter;
import com.aqar55.databinding.FragmentRentTabsFragmentBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rent_Main_Fragment extends Fragment implements ItemClickSupport.OnItemClickListener, RentListMainAdapter.OnRentItemClickListener {
    private static final String TAG = "Rent_Main_Fragment";
    private View view;
    private FragmentRentTabsFragmentBinding fragmentRentTabsFragmentBinding;
    private RentListMainAdapter rentListMainAdapter;
    private boolean priceltoh, pricehtol, sizeltoh, sizehtol;
    private Response<GetPropertyListing> responseMian;
    private LinearLayoutManager linearLayoutManager;
    private ShimmerFrameLayout mShimmerViewContainer;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            priceltoh = intent.getBooleanExtra("priceltoh", false);
            pricehtol = intent.getBooleanExtra("pricehtol", false);
            sizeltoh = intent.getBooleanExtra("sizeltoh", false);
            sizehtol = intent.getBooleanExtra("sizehtol", false);
            getSortedData();
            getSortedData();

        }
    };

    private void getSortedData() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> sortDataCall;
        if (ModelManager.modelManager().getCurrentUser() != null)
            sortDataCall = api.getSortedData("rent", ModelManager.modelManager().getCurrentUser().getId(), priceltoh, pricehtol, sizeltoh, sizehtol);
        else
            sortDataCall = api.getSortedData("rent", "", priceltoh, pricehtol, sizeltoh, sizehtol);

        sortDataCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(Call<GetPropertyListing> call, Response<GetPropertyListing> response) {
                Log.d(TAG, "onResponse: " + response.body().getResponseMessage());
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    //saleListMainAdapter.updateList(response);
                    if (response != null) {
                        fragmentRentTabsFragmentBinding.reantmainrecycler.setLayoutManager(linearLayoutManager);
                        rentListMainAdapter = new RentListMainAdapter(getActivity(), response, Rent_Main_Fragment.this);

                        fragmentRentTabsFragmentBinding.reantmainrecycler.setAdapter(rentListMainAdapter);
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetPropertyListing> call, Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRentTabsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rent_tabs_fragment, container, false);
        view = fragmentRentTabsFragmentBinding.getRoot();
        mShimmerViewContainer = fragmentRentTabsFragmentBinding.shimmerViewContainer;
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemClickSupport.addTo(fragmentRentTabsFragmentBinding.reantmainrecycler).setOnItemClickListener(this);


        if(getArguments()!=null){

            if(BaseManager.getDataFromPreferences("mapData",HashMap.class)==null){
                if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                    getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                } else if (!getArguments().getString("category").toString().isEmpty()) {
                    getRentDataCategoryApi(getArguments().getString("category"));

                } else {
                    getApiData();
                }

            }else {


                if (BaseManager.getDataFromPreferences("mapData", HashMap.class) != null && getArguments().getString("category").toString().isEmpty()) {
                    if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));
                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").isEmpty() && getArguments().getString("allData").isEmpty()) {
                        getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));
                    } else if (!getArguments().getString("category").toString().isEmpty()) {
                        getRentDataCategoryApi(getArguments().getString("category"));

                    } else {
                        getApiData();
                    }
                } else {
                    if (!getArguments().getString("category").toString().isEmpty()) {
                        getRentDataCategoryApi(getArguments().getString("category"));

                    } else if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").toString().isEmpty()) {
                        getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));
                    }
                }

            }


        }else {
            getApiData();
        }







        /*if(getArguments()!=null){
            if(!getArguments().getString("category").toString().isEmpty()){
                getRentDataCategoryApi(getArguments().getString("category"));

            }else  if(!getArguments().getString("lat").isEmpty()&&!getArguments().getString("long").isEmpty()){
                getSerchDataFromApi(getArguments().getString("fromActivity"),getArguments().getString("lat"),getArguments().getString("long"));

            }else if(getArguments().getString("lat").isEmpty()&&getArguments().getString("long").isEmpty()&&getArguments().getString("category").toString().isEmpty()){
                getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData",HashMap.class));

            }
        }else {
            getDataFromApi();
        }*/



      /*  if(getArguments()!=null){
            if(!getArguments().getString("category").toString().isEmpty()){
                getRentDataCategoryApi(getArguments().getString("category"));

            }else if(BaseManager.getDataFromPreferences("mapData", HashMap.class)!=null) {

                getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData",HashMap.class));
            }else {
                getSerchDataFromApi(getArguments().getString("fromActivity"),getArguments().getString("lat"),getArguments().getString("long"));

            }
        }else {
            getDataFromApi();
        }*/




        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("rent"));
        return view;
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
                        MyDialog.getInstance(getActivity()).hideDialog();
                        if (response.body().getData().size() > 0) {
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setLayoutManager(linearLayoutManager);
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.GONE);
                            responseMian = response;
                            rentListMainAdapter = new RentListMainAdapter(getContext(), response, Rent_Main_Fragment.this);
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setAdapter(rentListMainAdapter);

                         //   rentListMainAdapter.onRentCall(() -> getSerchDataFromApi(type,lat,lon));

                        } else {
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.VISIBLE);
                            fragmentRentTabsFragmentBinding.textView97.setText("No Data Found");
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
                        MyDialog.getInstance(getActivity()).hideDialog();
                        if (response.body().getData().size() > 0) {
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setLayoutManager(linearLayoutManager);
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.GONE);
                            responseMian = response;
                            rentListMainAdapter = new RentListMainAdapter(getContext(), response, Rent_Main_Fragment.this);
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setAdapter(rentListMainAdapter);

                            rentListMainAdapter.onRentCall(() -> getSerchDataFromApi(type,lat,lon));

                        } else {
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.VISIBLE);
                            fragmentRentTabsFragmentBinding.textView97.setText("No Data Found");
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



    private void getRentDataCategoryApi(String categoty) {

        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if(ModelManager.modelManager().getCurrentUser()!=null){
           getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("rent", categoty, ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("rent", categoty);

        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        MyDialog.getInstance(getActivity()).hideDialog();
                        if (response.body().getData().size() > 0) {
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setLayoutManager(linearLayoutManager);
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.GONE);
                            responseMian = response;
                            rentListMainAdapter = new RentListMainAdapter(getContext(), response, Rent_Main_Fragment.this);
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setAdapter(rentListMainAdapter);

                            rentListMainAdapter.onRentCall(() -> getRentDataCategoryApi(categoty));

                        } else {
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.VISIBLE);
                            fragmentRentTabsFragmentBinding.textView97.setText("No Data Found");
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


    private void getApiData() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;

        if (ModelManager.modelManager().getCurrentUser() != null) {
            getPropertyListingCall = api.getPropertyListWithUserId("rent", ModelManager.modelManager().getCurrentUser().getId());
        } else {
            getPropertyListingCall = api.getPropertyList("rent");
        }


        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(Call<GetPropertyListing> call, Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setLayoutManager(linearLayoutManager);
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.GONE);
                            responseMian = response;
                            rentListMainAdapter = new RentListMainAdapter(getContext(), response, Rent_Main_Fragment.this);
                            fragmentRentTabsFragmentBinding.reantmainrecycler.setAdapter(rentListMainAdapter);

                            rentListMainAdapter.onRentCall(() -> getApiData());

                        } else {
                            fragmentRentTabsFragmentBinding.textView97.setVisibility(View.VISIBLE);
                            fragmentRentTabsFragmentBinding.textView97.setText("No Data Found");
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
                fragmentRentTabsFragmentBinding.textView97.setVisibility(View.VISIBLE);
                fragmentRentTabsFragmentBinding.textView97.setText("Server Error");
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//        String type = responseMian.body().getData().get(position).getProfessionalUserId();
//        if(type!=null && !type.isEmpty()){
//            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
//            intent.putExtra("position", position);
//            intent.putExtra("propid", responseMian.body().getData().get(position).getId());
//
//            intent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
//            intent.putExtra("type","professional");
//            startActivity(intent);
//        }else {
//            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
//            intent.putExtra("position", position);
//            intent.putExtra("propid", responseMian.body().getData().get(position).getId());
//
//            intent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
//            intent.putExtra("type","normal");
//            startActivity(intent);
//        }

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

   /* public void update(Response<GetPropertyListing> response) {

        if (response != null)
            rentListMainAdapter.updateData(response);
        rentListMainAdapter.notifyDataSetChanged();
    }
*/
    @Override
    public void onItemClick(int position) {
        String type = responseMian.body().getData().get(position).getProfessionalUserId();
        if (type != null && !type.isEmpty()) {
            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("propid", responseMian.body().getData().get(position).getId());
            if (ModelManager.modelManager().getCurrentUser() != null)
                intent.putExtra("user_id", ModelManager.modelManager().getCurrentUser().getId());
            else
                intent.putExtra("user_id", "");

            intent.putExtra("type", "normal");
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("propid", responseMian.body().getData().get(position).getId());
            if (ModelManager.modelManager().getCurrentUser() != null)
                intent.putExtra("user_id", responseMian.body().getData().get(position).getId());
            else
                intent.putExtra("user_id", "");

            intent.putExtra("type", "normal");
            startActivity(intent);
        }
    }
}
