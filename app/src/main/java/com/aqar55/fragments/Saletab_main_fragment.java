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

import com.aqar55.activitys.ActivityViewProfileThrowProperty;
import com.aqar55.model.GetPropertyDetail;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;

import com.aqar55.ItemClick.ItemClickSupport;
import com.aqar55.R;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.adapters.SaleListMainAdapter;
import com.aqar55.databinding.FragmentSaletabMainFragmentBinding;
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

public class Saletab_main_fragment extends Fragment implements ItemClickSupport.OnItemClickListener {
    private static final String TAG = "Saletab_main_fragment";
    private Response<GetPropertyListing> responseMain;
    private FragmentSaletabMainFragmentBinding fragmentSaletabMainFragmentBinding;
    private SaleListMainAdapter saleListMainAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private boolean priceltoh, pricehtol, sizeltoh, sizehtol;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            priceltoh = intent.getBooleanExtra("priceltoh", false);
            pricehtol = intent.getBooleanExtra("pricehtol", false);
            sizeltoh = intent.getBooleanExtra("sizeltoh", false);
            sizehtol = intent.getBooleanExtra("sizehtol", false);
            getSortedData();
            // getDataFromApi();
        }
    };

    private void getSortedData() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> sortDataCall;
        if(ModelManager.modelManager().getCurrentUser()!=null)
            sortDataCall = api.getSortedData("sale", ModelManager.modelManager().getCurrentUser().getId(),priceltoh, pricehtol, sizeltoh, sizehtol);
        else
            sortDataCall = api.getSortedData("sale", "",priceltoh, pricehtol, sizeltoh, sizehtol);
        sortDataCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.GONE);
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setLayoutManager(linearLayoutManager);
                            saleListMainAdapter = new SaleListMainAdapter(getActivity(), response.body().getData());
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setAdapter(saleListMainAdapter);
                        } else {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                            fragmentSaletabMainFragmentBinding.nodatafound.setText("No Data Found");
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
                MyDialog.getInstance(getContext()).hideDialog();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                fragmentSaletabMainFragmentBinding.nodatafound.setText("Server Error...");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSaletabMainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_saletab_main_fragment, container, false);
        mShimmerViewContainer = fragmentSaletabMainFragmentBinding.getRoot().findViewById(R.id.shimmer_view_container);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.INVISIBLE);
        ItemClickSupport.addTo(fragmentSaletabMainFragmentBinding.salelistrecycler).setOnItemClickListener(this);
        if(getArguments()!=null){


            if(BaseManager.getDataFromPreferences("mapData",HashMap.class)==null){

                if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                    getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                } if (!getArguments().getString("category").toString().isEmpty()) {
                    getRentDataCategoryApi(getArguments().getString("category"));

                } else {
                    getDataFromApi();
                }


               // getDataFromApi();
            }else {

                if (BaseManager.getDataFromPreferences("mapData", HashMap.class) != null) {

                    if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                    } else if (getArguments().getString("lat").isEmpty() && getArguments().getString("long").isEmpty() && getArguments().getString("category").isEmpty() && getArguments().getString("allData").isEmpty()) {
                        getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));

                    } else if (!getArguments().getString("category").toString().isEmpty()) {
                        getRentDataCategoryApi(getArguments().getString("category"));

                    } else {
                        getDataFromApi();
                    }

                } else {

                    if (!getArguments().getString("category").toString().isEmpty()) {
                        getRentDataCategoryApi(getArguments().getString("category"));

                    } else if (!getArguments().getString("lat").isEmpty() && !getArguments().getString("long").isEmpty()) {
                        getSerchDataFromApi(getArguments().getString("fromActivity"), getArguments().getString("lat"), getArguments().getString("long"));

                    } else {
                        getSaleDataApiFromFilter(BaseManager.getDataFromPreferences("mapData", HashMap.class));

                    }
                }
            }


        }else {
            getDataFromApi();
        }


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("sale"));
        return fragmentSaletabMainFragmentBinding.getRoot();
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
                if (response.body().getResponseCode()==200) {
                    if (response.body().getSmalldata() != null) {

                        if (response.body().getSmalldata().size() > 0) {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.GONE);
                            responseMain = response;
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setLayoutManager(linearLayoutManager);
                            saleListMainAdapter = new SaleListMainAdapter(getContext(), response.body().getSmalldata());
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setAdapter(saleListMainAdapter);
                            saleListMainAdapter.SaleAdapterListener(() -> {
                              //  getSerchDataFromApi(type,lat,lon);
                            });
                        } else {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                            fragmentSaletabMainFragmentBinding.nodatafound.setText("No Data Found");
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
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.GONE);
                            responseMain = response;
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setLayoutManager(linearLayoutManager);
                            saleListMainAdapter = new SaleListMainAdapter(getContext(), response.body().getData());
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setAdapter(saleListMainAdapter);
                            saleListMainAdapter.SaleAdapterListener(() -> {
                                getSerchDataFromApi(type,lat,lon);
                            });
                        } else {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                            fragmentSaletabMainFragmentBinding.nodatafound.setText("No Data Found");
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
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategory("sale", categoty,ModelManager.modelManager().getCurrentUser().getId());

        }else {
            getPropertyListingCall = api.getPropertyOnTheBasisOfCategoryWithoutUserId("sale",categoty);

        }

        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        MyDialog.getInstance(getActivity()).hideDialog();
                                if (response.body().getData().size() > 0) {
                                    fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.GONE);
                                    responseMain = response;
                                    fragmentSaletabMainFragmentBinding.salelistrecycler.setLayoutManager(linearLayoutManager);
                                    saleListMainAdapter = new SaleListMainAdapter(getContext(), response.body().getData());
                                    fragmentSaletabMainFragmentBinding.salelistrecycler.setAdapter(saleListMainAdapter);
                                    saleListMainAdapter.SaleAdapterListener(() -> {
                                        getRentDataCategoryApi(categoty);
                                    });
                                } else {
                                    fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                                    fragmentSaletabMainFragmentBinding.nodatafound.setText("No Data Found");
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

    private void getDataFromApi() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        if(ModelManager.modelManager().getCurrentUser()!=null){
            getPropertyListingCall = api.getPropertyListWithUserId("sale",ModelManager.modelManager().getCurrentUser().getId());
        }else {
            getPropertyListingCall = api.getPropertyList("sale");
        }
        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseMessage().equalsIgnoreCase("Properties list found successfully")) {
                        if (response.body().getData().size() > 0) {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.GONE);
                            responseMain = response;
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setLayoutManager(linearLayoutManager);
                            saleListMainAdapter = new SaleListMainAdapter(getContext(), response.body().getData());
                            fragmentSaletabMainFragmentBinding.salelistrecycler.setAdapter(saleListMainAdapter);
                            saleListMainAdapter.SaleAdapterListener(() -> {
                                getDataFromApi();
                            });
                        } else {
                            fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                            fragmentSaletabMainFragmentBinding.nodatafound.setText("No Data Found");
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
                fragmentSaletabMainFragmentBinding.nodatafound.setVisibility(View.VISIBLE);
                fragmentSaletabMainFragmentBinding.nodatafound.setText("Server Error. Please Try Again...");
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        String  type = responseMain.body().getData().get(position).getProfessionalid();
        if(type!=null && type.isEmpty()){

            //startActivity(ActivityViewProfileThrowProperty.getIntent(getActivity(), responseMain.body().getData().get(position)));
            Intent intent = new Intent(getContext(), ProductDetailSale.class);
            intent.putExtra("propid", responseMain.body().getData().get(position).getId());
            intent.putExtra("type",responseMain.body().getData().get(position).getType());
            if(ModelManager.modelManager().getCurrentUser()!=null)
                intent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
            else
                intent.putExtra("user_id","");

            startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(), ProductDetailSale.class);
            intent.putExtra("propid", responseMain.body().getData().get(position).getId());
            intent.putExtra("type","normal");
            if(ModelManager.modelManager().getCurrentUser()!=null)
                intent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
            else
                intent.putExtra("user_id","");

            startActivity(intent);
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
    public void update(Response<GetPropertyListing> response) {

      /*  if (response != null)
            saleListMainAdapter.updateData(response);
        saleListMainAdapter.notifyDataSetChanged();*/
    }
}
