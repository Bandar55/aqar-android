package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.aqar55.databinding.ItemListBottomNavSaleLikedBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.LikeListResponse;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sale_LIke_Adapter extends RecyclerView.Adapter<Sale_LIke_Adapter.ViewHolder> {
    ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding;
    private LayoutInflater inflater;
    private Context context;
    private List<LikeListResponse.Data> data;
    private LikeListInterface onCallBack;
    public OnSaleItemClickListener onSaleItemClickListener;

    public Sale_LIke_Adapter(Context context, List<LikeListResponse.Data> data) {
        this.context = context;
        this.data = data;
    }

    public void MyCallBack(LikeListInterface onCallBack) {
        this.onCallBack = onCallBack;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemListBottomNavSaleLikedBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_bottom_nav_sale_liked, viewGroup, false);
        return new ViewHolder(itemListBottomNavSaleLikedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        itemListBottomNavSaleLikedBinding.tvTitle.setText(data.get(position).getTitle());
        itemListBottomNavSaleLikedBinding.tvCategorySale.setText(data.get(position).getCategory());
        itemListBottomNavSaleLikedBinding.tvPlotSizeSale.setText(data.get(position).getPlotsize()+" "+data.get(position).getPlotsizeunit());



        if(data.get(position).getType().equalsIgnoreCase("sale")){


            itemListBottomNavSaleLikedBinding.tvAmount.setText(data.get(position).getCurrency()+" "+data.get(position).getTotalpricesale());
            itemListBottomNavSaleLikedBinding.tvPerPriceSale.setText(data.get(position).getCurrency()+" "+data.get(position).getPricepermeter() + "/" + data.get(position).getPlotsizeunit());
        }else {
            itemListBottomNavSaleLikedBinding.tvAmount.setText(data.get(position).getCurrency()+" "+data.get(position).getTotalprice()+" "+data.get(position).getRentTime());
        }

        if(data.get(position).getImagesfile()!=null && !data.get(position).getImagesfile().isEmpty())
            Glide.with(context).load(data.get(position).getImagesfile().get(0).getImage()).into(itemListBottomNavSaleLikedBinding.ivProfile);

         viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.like_icon_new);

         if(data.get(position).getLikedStatus()!=null){
             if(data.get(position).getLikedStatus().equalsIgnoreCase("yes"))
                 viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.like_icon_new);
             else
                 viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
         }

        itemListBottomNavSaleLikedBinding.ivLike.setOnClickListener(v -> {

            if (ModelManager.modelManager().getCurrentUser() == null) {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            } else {
                getLikeApiCalling(data.get(position));
            }

        });

         itemListBottomNavSaleLikedBinding.imageView41.setOnClickListener(v->{

             if(ModelManager.modelManager().getCurrentUser()!=null){
                 context.startActivity(ChatActivity.getIntent(context, data.get(position).getUserId(), data.get(position).getId(),data.get(position).getTitle(),data.get(position).getDescription()));

             }else {
                 context.startActivity(Login_Signup_Button_Activity.getIntent(context));

             }



         });
        itemListBottomNavSaleLikedBinding.ivDelete.setOnClickListener(v -> {
            if (ModelManager.modelManager().getCurrentUser() == null) {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            } else {
                getLikeApiCalling(data.get(position));
            }
        });




        itemListBottomNavSaleLikedBinding.imageView39.setOnClickListener(v -> {
            final String appPackageName = BuildConfig.APPLICATION_ID;
            final String appName = context.getString(R.string.app_name);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id=" +
                    appPackageName;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            context.startActivity(Intent.createChooser(shareIntent, "Aqar 55"));
        });


        itemListBottomNavSaleLikedBinding.imageView40.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", data.get(position).getMobileNumber(), null));
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    private void getLikeApiCalling(LikeListResponse.Data datadata) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if(datadata.getLikedStatus()!=null){
            if(datadata.getLikedStatus().equalsIgnoreCase("yes"))
                getUserDetailsCall = api.getLikePostPorBusiness(datadata.getType(), datadata.getId(),ModelManager.modelManager().getCurrentUser().getId(),false);
            else
                getUserDetailsCall = api.getLikePostPorBusiness(datadata.getType(), datadata.getId(),ModelManager.modelManager().getCurrentUser().getId(),true);

            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 200) {
                                onCallBack.onCallBack();
                                Toaster.toast(response.body().getResponseMessage());
                            } else {
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
        }else {
            getUserDetailsCall = api.getLikePostPorBusiness(datadata.getType(), datadata.getId(),ModelManager.modelManager().getCurrentUser().getId(),false);
            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 200) {
                                onCallBack.onCallBack();
                                Toaster.toast(response.body().getResponseMessage());

                            } else {
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
    }


    public interface LikeListInterface {
        void onCallBack();
    }

    public interface OnSaleItemClickListener{
        void onSaleClick(int position);
    }

    public void setOnSaleClickListener(OnSaleItemClickListener onSaleClickListener){
        this.onSaleItemClickListener = onSaleClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding;

        ViewHolder(@NonNull ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding) {
            super(itemListBottomNavSaleLikedBinding.getRoot());
            this.itemListBottomNavSaleLikedBinding = itemListBottomNavSaleLikedBinding;
            itemListBottomNavSaleLikedBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSaleItemClickListener.onSaleClick(getAdapterPosition());
                }
            });
        }
    }
}
