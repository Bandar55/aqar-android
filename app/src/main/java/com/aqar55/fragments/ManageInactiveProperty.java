package com.aqar55.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.ItemClick.ItemClickSupport;
import com.aqar55.R;
import com.aqar55.activitys.ProductDetailActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.adapters.ManageActivePropertyAdapter;
import com.aqar55.databinding.FragmentManagePostedPropertyBinding;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageInactiveProperty extends Fragment implements View.OnClickListener, ItemClickSupport.OnItemClickListener {
    private static final String TAG = "Manage_Posted_Property";
    private FragmentManagePostedPropertyBinding fragmentManagePostedPropertyBinding;
    private View view;
    private ManageActivePropertyAdapter manageActivePropertyAdapter;
    private String userId;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Response<ManageActiveProperty> responseData;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManagePostedPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage__posted__property, container, false);
        view = fragmentManagePostedPropertyBinding.getRoot();
        fragmentManagePostedPropertyBinding.textView61.setVisibility(View.INVISIBLE);
        mShimmerViewContainer = fragmentManagePostedPropertyBinding.shimmerViewContainer;
        userId = CommonClass.getPreferencesString(getContext(), "userid");
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getDataFromApi();
        ItemClickSupport.addTo(fragmentManagePostedPropertyBinding.managePostedPropertyRecycler).setOnItemClickListener(this);
        return view;
    }
    private void getDataFromApi() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ManageActiveProperty> managePostedPropertyCall = api.getInactivePropertyList(userId);
        managePostedPropertyCall.enqueue(new Callback<ManageActiveProperty>() {
            @Override
            public void onResponse(@NonNull Call<ManageActiveProperty> call, @NonNull Response<ManageActiveProperty> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode()==200) {
                        if (response.body().getData().size() > 0) {
                            fragmentManagePostedPropertyBinding.textView61.setVisibility(View.GONE);
                            responseData = response;
                            fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setLayoutManager(linearLayoutManager);
                            manageActivePropertyAdapter = new ManageActivePropertyAdapter(getContext(), response);
                            fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setAdapter(manageActivePropertyAdapter);

                            manageActivePropertyAdapter.RemoveItem(() -> getDataFromApi());
                        } else {
                            responseData = response;
                            fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setLayoutManager(linearLayoutManager);
                            manageActivePropertyAdapter = new ManageActivePropertyAdapter(getContext(), response);
                            fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setAdapter(manageActivePropertyAdapter);
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            fragmentManagePostedPropertyBinding.textView61.setVisibility(View.VISIBLE);
                            fragmentManagePostedPropertyBinding.textView61.setText("Data Not Found...");

                        }
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ManageActiveProperty> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                fragmentManagePostedPropertyBinding.textView61.setVisibility(View.VISIBLE);
                fragmentManagePostedPropertyBinding.textView61.setText("Server Error ...");
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
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
        if (responseData != null) {
            if (responseData.body().getData().get(position).getType().equalsIgnoreCase("sale")) {
                if(responseData.body().getData().get(position).getProfessionalid()!=null){
                    Intent intent = new Intent(getContext(), ProductDetailSale.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","sale");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), ProductDetailSale.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","normal");
                    startActivity(intent);
                }
            }
            if (responseData.body().getData().get(position).getType().equalsIgnoreCase("rent")) {
                if(responseData.body().getData().get(position).getProfessionalid()!=null){
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","rent");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","normal");
                    startActivity(intent);
                }

            }
        }
    }
}

