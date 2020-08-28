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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.helper.ModelManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.ItemClick.ItemClickSupport;
import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.ProductDetailActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.adapters.Manage_Property_Adapter;
import com.aqar55.databinding.FragmentManagePostedPropertyBinding;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_Posted_Property extends Fragment implements View.OnClickListener, ItemClickSupport.OnItemClickListener, Manage_Property_Adapter.OnManageListItemClick {
    private static final String TAG = "Manage_Posted_Property";
    private FragmentManagePostedPropertyBinding fragmentManagePostedPropertyBinding;
    private View view;
    private Manage_Property_Adapter manage_property_adapter;
    private String userId;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Response<ManageActiveProperty> responseData;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManagePostedPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage__posted__property, container, false);
        view = fragmentManagePostedPropertyBinding.getRoot();


        mShimmerViewContainer = fragmentManagePostedPropertyBinding.shimmerViewContainer;
        Log.d(TAG, "onCreateView: " + CommonClass.getPreferencesString(getContext(), "userid"));
        userId = CommonClass.getPreferencesString(getContext(), "userid");
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemClickSupport.addTo(fragmentManagePostedPropertyBinding.managePostedPropertyRecycler).setOnItemClickListener(this);

        getDataFromApi();

        return view;
    }

    private void getDataFromApi() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ManageActiveProperty> managePostedPropertyCall = api.getPostedProperty(userId);

        managePostedPropertyCall.enqueue(new Callback<ManageActiveProperty>() {
            @Override
            public void onResponse(@NonNull Call<ManageActiveProperty> call, @NonNull Response<ManageActiveProperty> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().getData().isEmpty()) {
                        fragmentManagePostedPropertyBinding.textView61.setVisibility(View.GONE);
                        responseData = response;
                        fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setLayoutManager(linearLayoutManager);
                        manage_property_adapter = new Manage_Property_Adapter(getContext(), response);
                        manage_property_adapter.setOnManageListItemClick(Manage_Posted_Property.this);
                        fragmentManagePostedPropertyBinding.managePostedPropertyRecycler.setAdapter(manage_property_adapter);
                    } else {
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        fragmentManagePostedPropertyBinding.textView61.setVisibility(View.VISIBLE);
                        fragmentManagePostedPropertyBinding.textView61.setText("Data Not Found...");
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ManageActiveProperty> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
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
    public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
        final ImageView delete = v.findViewById(R.id.imageView21);
        ImageView editImageView = v.findViewById(R.id.ivEditManageProperty);

       /* editImageView.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), Post_Property_Main.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", responseData.body().getData().get(position).getType());
                intent.putExtras(bundle);
                startActivity(intent);

        });*/
        delete.setOnClickListener(view -> {
            showConfirmation(responseData.body().getData().get(position).getId(),position);
        });







        if (responseData != null) {
            if (responseData.body().getData().get(position).getType().equalsIgnoreCase("sale")) {


                String  type = responseData.body().getData().get(position).getProfessionalUserId();
                if(type!=null && type.isEmpty()){
                    Intent intent = new Intent(getContext(), ProductDetailSale.class);
                    intent.putExtra("type","professional");
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(getContext(), ProductDetailSale.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","normal");
                    startActivity(intent);

                }

            }
            if (responseData.body().getData().get(position).getType().equalsIgnoreCase("rent")) {

                String  type = responseData.body().getData().get(position).getProfessionalUserId();
                if(type!=null && type.isEmpty()){
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","professional");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), ProductDetailSale.class);
                    intent.putExtra("propid", responseData.body().getData().get(position).getId());
                    intent.putExtra("type","normal");
                    startActivity(intent);

                }



            }
        }
    }


    ////////////////////////dialog//////////////////////////
    private void showConfirmation(String id,int position) {
        final android.app.Dialog dialog = new android.app.Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        TextView message=dialog.findViewById(R.id.tv_Address);
        message.setText("Are you sure,You want to delete this property?");

        dialog.findViewById(R.id.btn_signup).setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.findViewById(R.id.btn_login).setOnClickListener(v -> {
            dialog.dismiss();
            //deleteProfile();
            deleteItem(id,position);


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }







    private void deleteItem(String id,int position) {

        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        if (responseData.body().getData() != null) {
            Call<GetPropertyListing> deleteProperty = api.deleteProperty(id);
            deleteProperty.enqueue(new Callback<GetPropertyListing>() {
                @Override
                public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode()==200) {
                                manage_property_adapter.remove(position);
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
    }

    @Override
    public void onChatIconClick(int position) {

        if (ModelManager.modelManager().getCurrentUser() != null) {
            startActivity(Chat_Main_Fragment.getIntent(getActivity(), ModelManager.modelManager().getCurrentUser().getId()));

        } else {
            startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

        }

       /* if(ModelManager.modelManager().getCurrentUser()!=null){
            startActivity(ChatActivity.getIntent(getContext(), responseData.body().getData().get(position).getUserid(), responseData.body().getData().get(position).getId(),responseData.body().getData().get(position).getTitle(),responseData.body().getData().get(position).getDescription()));

        }else {
            startActivity(Login_Signup_Button_Activity.getIntent(getActivity()));

        }*/

    }
}
