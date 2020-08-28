package com.aqar55.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.aqar55.R;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.adapters.Sale_LIke_Adapter;
import com.aqar55.databinding.FragmentRentLikeBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.LikeListResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Rent_Like_Fragment extends Fragment implements Sale_LIke_Adapter.OnSaleItemClickListener {
    FragmentRentLikeBinding fragmentRentLikeBinding;
    View view;
    Sale_LIke_Adapter sale_lIke_adapter;
    private String from="";
    private List<LikeListResponse.Data> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRentLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rent__like_, container, false);
        view = fragmentRentLikeBinding.getRoot();
        if(getArguments()!=null){
            from=getArguments().getString("from");
        }
        getLikeApiCalling();

        return view;
    }



    //like list for sale
    private void getLikeApiCalling() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeListResponse> getUserDetailsCall;
        if(from.equalsIgnoreCase("like")){
            getUserDetailsCall = api.getLikePostPorBusinessList("rent", ModelManager.modelManager().getCurrentUser().getId());
        }else {

            if(ModelManager.modelManager().getCurrentUser()!=null)
            getUserDetailsCall = api.getRecentList("rent",ModelManager.modelManager().getCurrentUser().getId());
            else
                getUserDetailsCall = api.getRecentListWithoutId("rent");


        }


        // Call<LikeListResponse> getUserDetailsCall = api.getLikePostPorBusinessList("rent", userId);
        getUserDetailsCall.enqueue(new Callback<LikeListResponse>() {
            @Override
            public void onResponse(Call<LikeListResponse> call, Response<LikeListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        fragmentRentLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentRentLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                        if (response.body().getResponseCode()==200) {
                            if(response.body().getData().size()>0){
                                list = response.body().getData();
                                fragmentRentLikeBinding.tvNoDataFound.setVisibility(View.GONE);
                                //Toaster.toast(response.body().getResponseMessage());
                                if(response.body().getData()!=null)
                                sale_lIke_adapter = new Sale_LIke_Adapter(getContext(),response.body().getData());
                                sale_lIke_adapter.setOnSaleClickListener(Rent_Like_Fragment.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentRentLikeBinding.rentLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentRentLikeBinding.rentLikeRecycler.setAdapter(sale_lIke_adapter);
                                sale_lIke_adapter.MyCallBack(() -> getLikeApiCalling());
                            }else {
                                sale_lIke_adapter = new Sale_LIke_Adapter(getContext(),response.body().getData());
                                sale_lIke_adapter.setOnSaleClickListener(Rent_Like_Fragment.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentRentLikeBinding.rentLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentRentLikeBinding.rentLikeRecycler.setAdapter(sale_lIke_adapter);
                                fragmentRentLikeBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                                fragmentRentLikeBinding.tvNoDataFound.setText("No Data Found");
                                fragmentRentLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                fragmentRentLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                            }


                        } else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeListResponse> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentRentLikeBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        fragmentRentLikeBinding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onSaleClick(int position) {
        String  type = list.get(position).getProfessionalUserId();
        if(type!=null && !type.isEmpty()){
            Intent intent = new Intent(getContext(), ProductDetailSale.class);
            intent.putExtra("propid", list.get(position).getId());
            intent.putExtra("type","normal");
            startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(), ProductDetailSale.class);
            intent.putExtra("propid", list.get(position).getId());
            intent.putExtra("type","normal");
            startActivity(intent);
        }
    }
}
