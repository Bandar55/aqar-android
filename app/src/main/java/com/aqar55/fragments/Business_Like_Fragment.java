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
public class Business_Like_Fragment extends Fragment {
    FragmentProfessionalLikeBinding fragmentBusinessLikeBinding;
    View view;
    private Professional_Like_Adapter professional_like_adapter;
    private String from="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBusinessLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_professional__like_, container, false);
        // Inflate the layout for this fragment
        view = fragmentBusinessLikeBinding.getRoot();
      //  professional_like_adapter = new Professional_Like_Adapter(getContext());

       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentBusinessLikeBinding.businessLikeRecycler.setLayoutManager(linearLayoutManager);
        fragmentBusinessLikeBinding.businessLikeRecycler.setAdapter(professional_like_adapter);*/

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
            getUserDetailsCall = api.getProfessionalBusinessList("business",  ModelManager.modelManager().getCurrentUser().getId());
        }else {
            if(ModelManager.modelManager().getCurrentUser()!=null)
            getUserDetailsCall = api.getProBusinessRecentList("business",  ModelManager.modelManager().getCurrentUser().getId());
            else
                getUserDetailsCall = api.getProBusinessRecentListWithId("business");


        }
        getUserDetailsCall.enqueue(new Callback<ProfessionalBusinessModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalBusinessModelResponse> call, Response<ProfessionalBusinessModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode()==200) {
                            if(response.body().getData().size()>0){
                                fragmentBusinessLikeBinding.tvNoDataFound.setVisibility(View.GONE);
                                fragmentBusinessLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                fragmentBusinessLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
                                //Toaster.toast(response.body().getResponseMessage());
                                professional_like_adapter = new Professional_Like_Adapter(getContext(),response.body().getData(),"Business");
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentBusinessLikeBinding.professionalLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentBusinessLikeBinding.professionalLikeRecycler.setAdapter(professional_like_adapter);
                                professional_like_adapter.MyProfessionalClick(() -> getLikeApiCalling());

                                professional_like_adapter.notifyDataSetChanged();
                            }else {

                                professional_like_adapter = new Professional_Like_Adapter(getContext(),response.body().getData(),"Business");
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                fragmentBusinessLikeBinding.professionalLikeRecycler.setLayoutManager(linearLayoutManager);
                                fragmentBusinessLikeBinding.professionalLikeRecycler.setAdapter(professional_like_adapter);
                                fragmentBusinessLikeBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                                fragmentBusinessLikeBinding.tvNoDataFound.setText("No Data Found");
                                fragmentBusinessLikeBinding.shimmerViewContainer.stopShimmerAnimation();
                                fragmentBusinessLikeBinding.shimmerViewContainer.setVisibility(View.GONE);
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
        fragmentBusinessLikeBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        fragmentBusinessLikeBinding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
