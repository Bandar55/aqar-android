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

import com.aqar55.helper.Toaster;
import com.aqar55.model.PropertyUpdateModel;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.aqar55.R;
import com.aqar55.activitys.Post_Property_Main;
import com.aqar55.databinding.LayoutManagePropertyTitleItemBinding;
import com.aqar55.model.ManageActiveProperty;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Manage_Property_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private Response<ManageActiveProperty> response;
    private LayoutManagePropertyTitleItemBinding layoutManagePropertyTitleItemBinding;
    private OnManageListItemClick onManageListItemClick;
    public Manage_Property_Adapter(Context context, Response<ManageActiveProperty> response) {
        this.context = context;
        this.response = response;
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
                layoutManagePropertyTitleItemBinding.tvProfCategory.setText(response.body().getData().get(i).getCurrency() + response.body().getData().get(i).getTotalPrice());
                layoutManagePropertyTitleItemBinding.tvProfID.setText(response.body().getData().get(i).getCategory());
                layoutManagePropertyTitleItemBinding.tvProfID2.setText(response.body().getData().get(i).getPricepermeter() + " " + response.body().getData().get(i).getPlotsizeunit());


                if(response.body().getData().get(i).getRemainingDays()!=null){
                    layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update " + response.body().getData().get(i).getRemainingDays() + "/" + response.body().getData().get(i).getTotalDays() + " days");

                }else {
                    layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update " + "0" + "/" + response.body().getData().get(i).getTotalDays() + " days");

                }
            }
            if (response.body().getData().get(i).getType().equalsIgnoreCase("rent")) {
                layoutManagePropertyTitleItemBinding.Title.setText(response.body().getData().get(i).getTitle());
                layoutManagePropertyTitleItemBinding.tvProfCategory.setText(response.body().getData().get(i).getCurrency() + response.body().getData().get(i).getTotalPrice() + " " + response.body().getData().get(i).getRenttime());
                layoutManagePropertyTitleItemBinding.tvProfID.setText(response.body().getData().get(i).getCategory());
                layoutManagePropertyTitleItemBinding.tvProfID2.setText(response.body().getData().get(i).getPlotsize() + " " + response.body().getData().get(i).getPlotsizeunit());

               if(response.body().getData().get(i).getRemainingDays()!=null){
                   layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update " + response.body().getData().get(i).getRemainingDays() + "/" + response.body().getData().get(i).getTotalDays() + " days");

               }else {
                   layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setText("Update " + "0" + "/" + response.body().getData().get(i).getTotalDays() + " days");

               }

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
        if (response.body().getData().size() <= 0) {
            return 0;
        } else {
            return response.body().getData().size();
        }

    }

    public void remove(int position) {
        List<String> list = new ArrayList<>();
        assert response.body() != null;
        response.body().getData().remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position, response.body().getData().size());
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
            layoutManagePropertyTitleItemBinding.tvSaveSpecificationBTn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivEditManageProperty:
                    context.startActivity(Post_Property_Main.getIntent(context, response.body().getData().get(getAdapterPosition())));
                    break;
                case R.id.ivChatIcon:
                    onManageListItemClick.onChatIconClick(getAdapterPosition());
                    break;
                case R.id.tvSaveSpecificationBTn:
                    updateProBusiness(response.body().getData().get(getAdapterPosition()).getId());
                    break;

            }
        }
    }

    public interface OnManageListItemClick {
        void onChatIconClick(int position);
    }

    public void setOnManageListItemClick(OnManageListItemClick onManageListItemClick) {
        this.onManageListItemClick = onManageListItemClick;
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


}

