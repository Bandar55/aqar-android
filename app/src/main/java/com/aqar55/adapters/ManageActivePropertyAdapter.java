package com.aqar55.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.fragments.Chat_Main_Fragment;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.PostPropertyForSale;
import com.aqar55.model.PropertyUpdateModel;
import com.aqar55.model.UpdateProfileModel;
import com.bumptech.glide.Glide;

import com.aqar55.R;
import com.aqar55.activitys.Chat_Activity;
import com.aqar55.activitys.Post_Property_Main;
import com.aqar55.databinding.LayoutManagePropertyTitleItemBinding;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManageActivePropertyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private Response<ManageActiveProperty> response;
    private LayoutManagePropertyTitleItemBinding layoutManagePropertyTitleItemBinding;
    private RemoveItem mListenerItem;

    public ManageActivePropertyAdapter(Context context, Response<ManageActiveProperty> response) {
        this.context = context;
        this.response = response;
    }

    public  void RemoveItem(RemoveItem mListenerItem){
        this.mListenerItem=mListenerItem;


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }

        layoutManagePropertyTitleItemBinding = DataBindingUtil.inflate(inflater, R.layout.layout_manage_property_title_item, viewGroup, false);
        return new ViewHolder1(layoutManagePropertyTitleItemBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


        if (response.body() != null) {
            if (response.body().getData().get(i).getType().equalsIgnoreCase("sale")) {
                layoutManagePropertyTitleItemBinding.Title.setText(response.body().getData().get(i).getTitle());
                layoutManagePropertyTitleItemBinding.tvProfCategory.setText(response.body().getData().get(i).getCurrency()+response.body().getData().get(i).getTotalpricesale());
                layoutManagePropertyTitleItemBinding.tvProfID.setText(response.body().getData().get(i).getCategory());
                layoutManagePropertyTitleItemBinding.tvProfID2.setText(response.body().getData().get(i).getPricepermeter()+" "+response.body().getData().get(i).getPlotsizeunit());
                layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update "+response.body().getData().get(i).getRemainingDays()+"/"+response.body().getData().get(i).getTotalDays()+" days");
            }
            if (response.body().getData().get(i).getType().equalsIgnoreCase("rent")) {
                layoutManagePropertyTitleItemBinding.Title.setText(response.body().getData().get(i).getTitle());
                layoutManagePropertyTitleItemBinding.tvProfCategory.setText(response.body().getData().get(i).getCurrency()+response.body().getData().get(i).getTotalPrice());
                layoutManagePropertyTitleItemBinding.tvProfID.setText(response.body().getData().get(i).getCategory());
                layoutManagePropertyTitleItemBinding.tvProfID2.setText(response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit());
                layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update "+response.body().getData().get(i).getRemainingDays()+"/"+response.body().getData().get(i).getTotalDays()+" days");

            }
        }
        assert response.body() != null;
        if (!response.body().getData().get(i).getImagesfile().isEmpty() && response.body().getData().get(i).getImagesfile() != null) {
            Glide.with(context).load(response.body().getData().get(i).getImagesfile().get(0).getImage())
                    .placeholder(new ColorDrawable(Color.GRAY)).into(layoutManagePropertyTitleItemBinding.ivPropertyImage);
        }else {
            Glide.with(context).load(R.drawable.default_propertyimage)
                    .placeholder(new ColorDrawable(Color.GRAY)).into(layoutManagePropertyTitleItemBinding.ivPropertyImage);
        }
    }


    @Override
    public int getItemCount() {

        assert response.body() != null;
        if (response.body().getData() != null && !response.body().getData().isEmpty()) {
            return response.body().getData().size();
        } else {
            return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.ivEditManageProperty).setOnClickListener(view -> context.startActivity(new Intent(context, Post_Property_Main.class)));
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        LayoutManagePropertyTitleItemBinding layoutManagePropertyTitleItemBinding;

        ViewHolder1(@NonNull LayoutManagePropertyTitleItemBinding layoutManagePropertyTitleItemBinding) {
            super(layoutManagePropertyTitleItemBinding.getRoot());

            this.layoutManagePropertyTitleItemBinding = layoutManagePropertyTitleItemBinding;
            layoutManagePropertyTitleItemBinding.ivChatIcon.setOnClickListener(this);
            layoutManagePropertyTitleItemBinding.ivEditManageProperty.setOnClickListener(this);
            layoutManagePropertyTitleItemBinding.imageView21.setOnClickListener(this);
            layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivChatIcon:
                    if (ModelManager.modelManager().getCurrentUser() != null) {
                        context.startActivity(Chat_Main_Fragment.getIntent(context, ModelManager.modelManager().getCurrentUser().getId()));

                    } else {
                        context.startActivity(Login_Signup_Button_Activity.getIntent(context));

                    }
                    //context.startActivity(new Intent(context, Chat_Activity.class));
                    break;

                case R.id.ivEditManageProperty:
                    context.startActivity(Post_Property_Main.getIntent(context,response.body().getData().get(getAdapterPosition())));
                    break;

                case R.id.imageView21:
                    showConfirmation(response.body().getData().get(getAdapterPosition()).getId());
                    break;
                case R.id.tvSaveSpecificationBTn:
                    updateProBusiness(response.body().getData().get(getAdapterPosition()).getId());
                    break;

            }
        }


    }



    private void showConfirmation(String id) {
        final android.app.Dialog dialog = new android.app.Dialog(context);
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
            deleteItem(id);


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }

    private void updateProBusiness(String businessId) {

        MyDialog.getInstance(context.getApplicationContext()).showDialog((Activity) context);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<PropertyUpdateModel> getPropertyListingCall;
        getPropertyListingCall = api.updatePropertyForDays(businessId);

        getPropertyListingCall.enqueue(new Callback<PropertyUpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<PropertyUpdateModel> call, @NonNull Response<PropertyUpdateModel> response) {
                MyDialog.getInstance(context).hideDialog();
                if (response.body().getResponseCode()==200) {
                    Toaster.toast("Property updated successfully");

                }else {
                    Toaster.toast(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PropertyUpdateModel> call, @NonNull Throwable t) {
                MyDialog.getInstance(context).hideDialog();

            }
        });


    }

    private void deleteItem(String id) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
            Call<GetPropertyListing> deleteProperty = api.deleteProperty(id);
            deleteProperty.enqueue(new Callback<GetPropertyListing>() {
                @Override
                public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode()==200) {
                                mListenerItem.remove();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                    MyDialog.getInstance(context).hideDialog();

                }
            });
    }


    public interface  RemoveItem{
        void remove();
    }
}

