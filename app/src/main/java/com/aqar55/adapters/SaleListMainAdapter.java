package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import com.aqar55.BuildConfig;
import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.databinding.ItemListBottomNavBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleListMainAdapter extends RecyclerView.Adapter<SaleListMainAdapter.ViewHolder> {
    LayoutInflater inflater;
    ItemListBottomNavBinding itemListBottomNavBinding;
    private Context context;
    private List<GetPropertyListing.Data> response;
    private boolean isLike;
    private LikeInterFace mCallBack;
    public SaleListMainAdapter(Context context, List<GetPropertyListing.Data> response) {
        this.response = response;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemListBottomNavBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_bottom_nav, viewGroup, false);
        return new ViewHolder(itemListBottomNavBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.itemListBottomNavBinding.textView84.setText(response.get(i).getTitle());
        viewHolder.itemListBottomNavBinding.textView85.setText(response.get(i).getCurrency()+" "+response.get(i).getTotalpricesale());
        viewHolder.itemListBottomNavBinding.textView86.setText(response.get(i).getCategory());
        viewHolder.itemListBottomNavBinding.textView87.setText(response.get(i).getPlotsize()+" "+response.get(i).getPlotsizeunit());
        viewHolder.itemListBottomNavBinding.textView88.setText(response.get(i).getCurrency()+" "+response.get(i).getPricepermeter() + "/" + response.get(i).getPlotsizeunit());

        if (!response.get(i).getImagesfile().isEmpty() && response.get(i).getImagesfile() != null) {
            Glide.with(context).load(response.get(i).getImagesfile().get(0).getImage())
                    .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
        }else {
            Glide.with(context).load(R.drawable.default_propertyimage)
                    .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
        }




        if(ModelManager.modelManager().getCurrentUser()!=null){
            if(response.get(i).getLikedStatus()!=null){
                if (response.get(i).getLikedStatus().equalsIgnoreCase("yes")) {
                    viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.like_icon_new);

                } else {
                    viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
                }
            }
        }else {
            viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
        }

        viewHolder.itemListBottomNavBinding.ivCalling.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", response.get(i).getMobileNumber(), null));
            context.startActivity(intent);
        });




        viewHolder.itemListBottomNavBinding.chat.setOnClickListener(view -> {
            if(ModelManager.modelManager().getCurrentUser()!=null){
                context.startActivity(ChatActivity.getIntent(context,response.get(i).getUserid(),response.get(i).getId(),response.get(i).getTitle(),response.get(i).getDescription()));
            }else {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));
            }
        });
        viewHolder.itemListBottomNavBinding.ivLike.setOnClickListener(v -> {

            if (ModelManager.modelManager().getCurrentUser() == null) {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            } else {
                getLikeApiCalling(response.get(i));
            }

        });

        viewHolder.itemListBottomNavBinding.ivShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = context.getString(R.string.body_foodie);
            String shareSub = context.getString(R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });
    }



    @Override
    public int getItemCount() {
        return response != null ? response.size() : 0;
    }

    public void SaleAdapterListener(LikeInterFace mCallBack) {
        this.mCallBack = mCallBack;

    }



    private void getLikeApiCalling(GetPropertyListing.Data data) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if(data.getLikedStatus().equalsIgnoreCase("yes")){
             getUserDetailsCall = api.getLikePostPorBusiness("sale", data.getId(),ModelManager.modelManager().getCurrentUser().getId(),false);

        }else {
           getUserDetailsCall = api.getLikePostPorBusiness("sale", data.getId(),ModelManager.modelManager().getCurrentUser().getId(),true);

        }
        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    mCallBack.onClick();
                    if (response.body() != null) {
                        if (response.body().toString().equalsIgnoreCase("200")) {
                            Toaster.toast(response.body().getResponseMessage());

                        } else {
                            Toaster.toast(response.body().getResponseMessage());

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                mCallBack.onClick();

            }
        });
    }


    public interface LikeInterFace {
        void onClick();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBottomNavBinding itemListBottomNavBinding;

        ViewHolder(@NonNull ItemListBottomNavBinding itemListBottomNavBinding) {
            super(itemListBottomNavBinding.getRoot());
            this.itemListBottomNavBinding = itemListBottomNavBinding;
        }
    }


}
