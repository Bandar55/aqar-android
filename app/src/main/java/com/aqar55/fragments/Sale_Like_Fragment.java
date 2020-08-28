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
import com.aqar55.databinding.FragmentSaleLikeBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.LikeListResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sale_Like_Fragment extends Fragment implements Sale_LIke_Adapter.OnSaleItemClickListener {
    View view;
    private FragmentSaleLikeBinding fragmentSaleLikeBinding;
    private Sale_LIke_Adapter sale_lIke_adapter;
    private String from="";
    private List<LikeListResponse.Data> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSaleLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sale__like_, container, false);
        view = fragmentSaleLikeBinding.getRoot();
        if(getArguments()!=null){
            from=getArguments().getString("from");
        }
        getLikeApiCalling();
        return view;
    }


    //like list for sale
    private void getLikeApiCalling() {
        Call<LikeListResponse> getUserDetailsCall;
        Api api = ApiClientConnection.getInstance().createApiInterface();
        if(from.equalsIgnoreCase("like")){
            getUserDetailsCall = api.getLikePostPorBusinessList("sale", ModelManager.modelManager().getCurrentUser().getId());
        }else {

            if(ModelManager.modelManager().getCurrentUser()!=null)
            getUserDetailsCall = api.getRecentList("sale",ModelManager.modelManager().getCurrentUser().getId());
            else
                getUserDetailsCall = api.getRecentListWithoutId("sale");

        }
        getUserDetailsCall.enqueue(new Callback<LikeListResponse>() {
            @Override
            public void onResponse(Call<LikeListResponse> call, Response<LikeListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getResponseCode()==200) {
                            if(response.body().getData().size()>0){
                                list = response.body().getData();
                                fragmentSaleLikeBinding.tvNoDataFound.setVisibility(View.GONE);
                                fragmentSaleLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                                fragmentSaleLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                //Toaster.toast(response.body().getResponseMessage());
                                if(response.body().getData()!=null)
                                    //Toaster.toast(response.body().getResponseMessage());
                                sale_lIke_adapter = new Sale_LIke_Adapter(getContext(),response.body().getData());
                                sale_lIke_adapter.setOnSaleClickListener(Sale_Like_Fragment.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentSaleLikeBinding.saleLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentSaleLikeBinding.saleLikeRecycler.setAdapter(sale_lIke_adapter);
                                sale_lIke_adapter.MyCallBack(() -> getLikeApiCalling());
                            }else {
                                sale_lIke_adapter = new Sale_LIke_Adapter(getContext(),response.body().getData());
                                sale_lIke_adapter.setOnSaleClickListener(Sale_Like_Fragment.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentSaleLikeBinding.saleLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentSaleLikeBinding.saleLikeRecycler.setAdapter(sale_lIke_adapter);
                                fragmentSaleLikeBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                                fragmentSaleLikeBinding.tvNoDataFound.setText("No Data Found");
                                fragmentSaleLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                                fragmentSaleLikeBinding.shimmerViewContainer.stopShimmerAnimation();
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
        fragmentSaleLikeBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        fragmentSaleLikeBinding.shimmerViewContainer.stopShimmerAnimation();
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
