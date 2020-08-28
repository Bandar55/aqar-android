package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.aqar55.BuildConfig;
import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.databinding.ItemBusinessListBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Business_List_Adapter extends RecyclerView.Adapter<Business_List_Adapter.ViewHolder> {
    LayoutInflater inflater;
    private ItemBusinessListBinding itemBusinessListBinding;
    private Context context;
    private List<GetPropertyListing.Data> dataList;

    private BusinessListenr listenr;

    public Business_List_Adapter(Context context, List<GetPropertyListing.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void MyBusinessListener(BusinessListenr listenr){
        this.listenr=listenr;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemBusinessListBinding = DataBindingUtil.inflate(inflater, R.layout.item_business_list, viewGroup, false);
        return new ViewHolder(itemBusinessListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        itemBusinessListBinding.tvBusinessNameList.setText(dataList.get(i).getFullName());
        itemBusinessListBinding.tvBusinessCategory.setText("Business Category : "+dataList.get(i).getCategory());
        itemBusinessListBinding.tvBusinessId.setText("Business ID  : "+dataList.get(i).getBusinessid());
        itemBusinessListBinding.textView96.setText("Detail : "+dataList.get(i).getDescription());
        String userId = dataList.get(i).getId();
        String businessUserId = dataList.get(i).getUserid();

        if(ModelManager.modelManager().getCurrentUser()!=null){
            if(dataList.get(i).getLikedStatus()!=null){
                if(dataList.get(i).getLikedStatus().equalsIgnoreCase("yes")){
                    itemBusinessListBinding.ivLike.setImageResource(R.drawable.like_icon_new);
                }else {
                    itemBusinessListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

                }
            }
        }else {
            itemBusinessListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
        }

        if(dataList.get(i).getLikedStatus()!=null){
            if(dataList.get(i).getLikedStatus().equalsIgnoreCase("yes")){
                itemBusinessListBinding.ivLike.setImageResource(R.drawable.like_icon_new);
            }else {
                itemBusinessListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

            }
        }

        String imageProfile = dataList.get(i).getProfileImage();
        if(imageProfile!=null && !imageProfile.isEmpty()){
            Picasso.get().load(imageProfile).into(itemBusinessListBinding.ivBusinessProfileIMageListing);
        }else {
            Picasso.get().load(R.drawable.default_profile).into(itemBusinessListBinding.ivBusinessProfileIMageListing);

        }


        itemBusinessListBinding.ivShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = context.getString(R.string.body_foodie);
            String shareSub = context.getString(R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });


        itemBusinessListBinding.ivCalling.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataList.get(i).getMobileNumber(), null));
            context.startActivity(intent);
        });


        itemBusinessListBinding.ivLike.setOnClickListener(v -> {

            if(ModelManager.modelManager().getCurrentUser()==null){
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            }else {
                getLikeApiCallingPro(dataList.get(i));
            }


        });
        //setProfilePic(dataList.get(i).getProfileImage(),itemBusinessListBinding.ivBusinessProfileIMageListing);


        itemBusinessListBinding.relativeLayout6.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfPropertyNameActivity.class);
            intent.putExtra("_userId",userId);
            intent.putExtra("info_window_type","business");
            intent.putExtra("businessUserId",businessUserId);
            context.startActivity(intent);
        });

        itemBusinessListBinding.chat.setOnClickListener(view -> {

            if(ModelManager.modelManager().getCurrentUser()!=null){
                context.startActivity(ChatActivity.getIntent(context,dataList.get(i).getUserid(),dataList.get(i).getId(),dataList.get(i).getFullName(),dataList.get(i).getDescription()));

            }else {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList!=null?dataList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemBusinessListBinding itemBusinessListBinding;


        ViewHolder(@NonNull ItemBusinessListBinding itemBusinessListBinding) {
            super(itemBusinessListBinding.getRoot());
            this.itemBusinessListBinding = itemBusinessListBinding;
        }
    }




    private void getLikeApiCallingPro(GetPropertyListing.Data userId) {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;

        if(userId.getLikedStatus().equalsIgnoreCase("yes")){
            getUserDetailsCall = api.getLikePost("professional",ModelManager.modelManager().getCurrentUser().getId(),userId.getId(),false);

        }else {
            getUserDetailsCall = api.getLikePost("professional",ModelManager.modelManager().getCurrentUser().getId(),userId.getId(),true);

        }

        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        if(response.body().getResponseCode()==200)
                        {
                            Toaster.toast(response.body().getResponseMessage());
                            if(listenr!=null)
                            listenr.onClick();
                            if(response.body().getLiked()==true){
                                Toaster.toast(response.body().getResponseMessage());
                            }else {
                                Toaster.toast(response.body().getResponseMessage());
                            }

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

    public interface BusinessListenr{
        void onClick();
    }

    public void updateData(List<GetPropertyListing.Data> responseData) {
        this.dataList = responseData;
        notifyDataSetChanged();
    }



}
