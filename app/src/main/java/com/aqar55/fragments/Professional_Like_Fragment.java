package com.aqar55.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aqar55.R;
import com.aqar55.adapters.Professional_Like_Adapter;
import com.aqar55.databinding.FragmentProfessionalLikeBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.ProfessionalBusinessModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Professional_Like_Fragment extends Fragment {
    private FragmentProfessionalLikeBinding fragmentProfessionalLikeBinding;
    View view;
    private Professional_Like_Adapter professional_like_adapter;
    private String from="";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfessionalLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_professional__like_, container, false);
        view = fragmentProfessionalLikeBinding.getRoot();
        if(getArguments()!=null){
            from=getArguments().getString("from");
        }

        getLikeApiCalling() ;
        return view;
    }


    //like list for sale
    private void getLikeApiCalling() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ProfessionalBusinessModelResponse> getUserDetailsCall;

        if(from.equalsIgnoreCase("like")){
            getUserDetailsCall = api.getProfessionalBusinessList("professional", ModelManager.modelManager().getCurrentUser().getId());
        }else {

            if(ModelManager.modelManager().getCurrentUser()!=null)
            getUserDetailsCall = api.getProBusinessRecentList("professional",  ModelManager.modelManager().getCurrentUser().getId());
            else
                getUserDetailsCall = api.getProBusinessRecentListWithId("professional");


        }

        getUserDetailsCall.enqueue(new Callback<ProfessionalBusinessModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalBusinessModelResponse> call, Response<ProfessionalBusinessModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode()==200) {
                            if(response.body().getData().size()>0){
                                fragmentProfessionalLikeBinding.tvNoDataFound.setVisibility(View.GONE);
                                fragmentProfessionalLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                fragmentProfessionalLikeBinding.shimmerViewContainer.setVisibility(View.GONE);

                                //Toaster.toast(response.body().getResponseMessage());

                                professional_like_adapter = new Professional_Like_Adapter(getContext(),response.body().getData(),"Professional");


                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentProfessionalLikeBinding.professionalLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentProfessionalLikeBinding.professionalLikeRecycler.setAdapter(professional_like_adapter);
                                professional_like_adapter.MyProfessionalClick(() -> getLikeApiCalling());
                            }else {
                                professional_like_adapter = new Professional_Like_Adapter(getContext(),response.body().getData(),"Professional");

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentProfessionalLikeBinding.professionalLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentProfessionalLikeBinding.professionalLikeRecycler.setAdapter(professional_like_adapter);
                                fragmentProfessionalLikeBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                                fragmentProfessionalLikeBinding.tvNoDataFound.setText("No Data Found");
                                fragmentProfessionalLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                fragmentProfessionalLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                            }


                        } else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfessionalBusinessModelResponse> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentProfessionalLikeBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        fragmentProfessionalLikeBinding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


}
