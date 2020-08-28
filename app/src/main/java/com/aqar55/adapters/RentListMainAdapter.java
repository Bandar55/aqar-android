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
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RentListMainAdapter extends RecyclerView.Adapter<RentListMainAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ItemListBottomNavBinding itemListBottomNavBinding;
    private Context context;
    private Response<GetPropertyListing> response;
    private RentListCallBack callBack;
    private OnRentItemClickListener onRentItemClickListener;
    public RentListMainAdapter(Context context, Response<GetPropertyListing> response,OnRentItemClickListener onRentItemClickListener) {
        this.context = context;
        this.response = response;
        this.onRentItemClickListener = onRentItemClickListener;
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

    public void onRentCall(RentListCallBack callBack){
        this.callBack=callBack;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        assert response.body() != null;
        viewHolder.itemListBottomNavBinding.textView84.setText(response.body().getData().get(i).getTitle());
        viewHolder.itemListBottomNavBinding.textView85.setText(response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalPrice()+" "+response.body().getData().get(i).getRentTime());
        viewHolder.itemListBottomNavBinding.textView86.setText(response.body().getData().get(i).getCategory());
        viewHolder.itemListBottomNavBinding.textView87.setText(response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit());
        //viewHolder.itemListBottomNavBinding.textView88.setText(response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getPlotsize() + "/" + response.body().getData().get(i).getPlotsizeunit());


        if(response.body().getData().get(i).getLikedStatus()!=null) {
            if (response.body().getData().get(i).getLikedStatus().equalsIgnoreCase("yes")) {
                viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.like_icon_new);

            } else {
                viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
            }
        }

        if(ModelManager.modelManager().getCurrentUser()!=null){
            if(response.body().getData().get(i).getLikedStatus()!=null){
                if (response.body().getData().get(i).getLikedStatus().equalsIgnoreCase("yes")) {
                    viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.like_icon_new);

                } else {
                    viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
                }
            }

        }else {
            viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

        }


        if (!response.body().getData().get(i).getImagesfile().isEmpty()&& response.body().getData().get(i).getImagesfile()!=null) {
            Glide.with(context).load(response.body().getData().get(i).getImagesfile().get(0).getImage())
                    .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
        }else {
            Glide.with(context).load(R.drawable.default_propertyimage)
                    .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
        }
        viewHolder.itemListBottomNavBinding.relativeLayout4.setOnClickListener(view -> {
                  onRentItemClickListener.onItemClick(i);
        });
        viewHolder.itemListBottomNavBinding.chat.setOnClickListener(view -> {
            if(ModelManager.modelManager().getCurrentUser()!=null)
                context.startActivity(ChatActivity.getIntent(context,response.body().getData().get(i).getUserid(),
                        response.body().getData().get(i).getId(),response.body().getData().get(i).getTitle(),response.body().getData().get(i).getDescription()));
            else
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));
        });


        viewHolder.itemListBottomNavBinding.ivLike.setOnClickListener(v -> {
            if(ModelManager.modelManager().getCurrentUser()==null){
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            }else {
                getLikeApiCalling(response.body().getData().get(i));
            }
        });

        viewHolder.itemListBottomNavBinding.ivCalling.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", response.body().getData().get(i).getMobileNumber(), null));
            context.startActivity(intent);
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
        return response.body().getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBottomNavBinding itemListBottomNavBinding;

        ViewHolder(@NonNull ItemListBottomNavBinding itemListBottomNavBinding) {
            super(itemListBottomNavBinding.getRoot());
            this.itemListBottomNavBinding = itemListBottomNavBinding;
        }
    }

    private void getLikeApiCalling(GetPropertyListing.Data data) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;

        if(data.getLikedStatus().equalsIgnoreCase("yes")){
            getUserDetailsCall = api.getLikePostPorBusiness("rent",data.getId(),ModelManager.modelManager().getCurrentUser().getId(),false);
        }else {
            getUserDetailsCall = api.getLikePostPorBusiness("rent",data.getId(),ModelManager.modelManager().getCurrentUser().getId(),true);
        }

        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        if(response.body().getResponseCode()==200)
                        {
                            callBack.onCallBack();
                            Toaster.toast(response.body().getResponseMessage());

                        }else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }


    public interface  RentListCallBack{
        void onCallBack();
    }


    public void updateData(Response<GetPropertyListing> responseData) {
        this.response = responseData;
        notifyDataSetChanged();
    }

    public interface OnRentItemClickListener{
        void onItemClick(int position);
    }
}


