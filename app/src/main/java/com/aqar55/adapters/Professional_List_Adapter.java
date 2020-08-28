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
import com.aqar55.databinding.ItemProfessionalTabListBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Professional_List_Adapter extends RecyclerView.Adapter<Professional_List_Adapter.ViewHolder> {
    LayoutInflater inflater;
    private ItemProfessionalTabListBinding itemProfessionalTabListBinding;
    private Context context;
    private List<GetPropertyListing.Data> dataList;
    private ProfessionalLikeListener likeListener;
    public Professional_List_Adapter(Context context, List<GetPropertyListing.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void MyProfessionalList(ProfessionalLikeListener likeListener ){
        this.likeListener=likeListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemProfessionalTabListBinding = DataBindingUtil.inflate(inflater, R.layout.item_professional_tab_list, viewGroup, false);
        return new ViewHolder(itemProfessionalTabListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        itemProfessionalTabListBinding.tvProfeesionalName.setText(dataList.get(i).getFullName());
        itemProfessionalTabListBinding.tvProfCategoryList.setText("Professional Category : "+dataList.get(i).getCategory());
        itemProfessionalTabListBinding.tvProfIDList.setText("Professional ID : "+dataList.get(i).getProfessionalid());
        itemProfessionalTabListBinding.tvProfDeatils.setText("Detail : "+dataList.get(i).getDescription());

        itemProfessionalTabListBinding.ivCalling.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataList.get(i).getMobileNumber(), null));
            context.startActivity(intent);

        });
        itemProfessionalTabListBinding.ivShare.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = context.getString(R.string.body_foodie);
            String shareSub = context.getString(R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share using"));

        });

        if(ModelManager.modelManager().getCurrentUser()!=null){
            if(dataList.get(i).getLikedStatus()!=null){
                if(dataList.get(i).getLikedStatus().equalsIgnoreCase("yes")){
                    itemProfessionalTabListBinding.ivLike.setImageResource(R.drawable.like_icon_new);
                }else {
                    itemProfessionalTabListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

                }
            }

        }else {
            itemProfessionalTabListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
        }

        if(dataList.get(i).getLikedStatus()!=null){
            if(dataList.get(i).getLikedStatus().equalsIgnoreCase("yes")){
                itemProfessionalTabListBinding.ivLike.setImageResource(R.drawable.like_icon_new);
            }else {
                itemProfessionalTabListBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

            }
        }

        String userId = dataList.get(i).getId();
        String businessUserId = dataList.get(i).getUserid();
        String profileImage = dataList.get(i).getProfileImage();
        if( profileImage!=null && !profileImage.isEmpty()){
            Picasso.get().load(profileImage).into(itemProfessionalTabListBinding.ivProfProfileImage);
        }else {
            Picasso.get().load(R.drawable.default_profile).into(itemProfessionalTabListBinding.ivProfProfileImage);

        }
        //setProfilePic(dataList.get(i).getProfileImage(),itemProfessionalTabListBinding.ivProfProfileImage);

        itemProfessionalTabListBinding.relativeLayout5.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfPropertyNameActivity.class);
            intent.putExtra("_userId",userId);
            intent.putExtra("info_window_type","professional");
            intent.putExtra("businessUserId",businessUserId);
            context.startActivity(intent);
        });
        itemProfessionalTabListBinding.chatIcon.setOnClickListener(view -> {
            if(ModelManager.modelManager().getCurrentUser()!=null)
                context.startActivity(ChatActivity.getIntent(context,dataList.get(i).getUserid(),userId,dataList.get(i).getFullName(),dataList.get(i).getDescription()));
            else
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));
        });

        itemProfessionalTabListBinding.ivLike.setOnClickListener(v -> {
            if(ModelManager.modelManager().getCurrentUser()==null){
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));
            }else {
                getLikeApiCallingUser(dataList.get(i));
            } });
    }

    @Override
    public int getItemCount() {
        return dataList!=null?dataList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemProfessionalTabListBinding itemProfessionalTabListBinding;

        ViewHolder(@NonNull ItemProfessionalTabListBinding itemProfessionalTabListBinding) {
            super(itemProfessionalTabListBinding.getRoot());

            this.itemProfessionalTabListBinding = itemProfessionalTabListBinding;
        }
    }


    private void getLikeApiCallingUser(GetPropertyListing.Data data) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if(data.getLikedStatus().equalsIgnoreCase("yes")){
            getUserDetailsCall = api.getLikePost(data.getType(),ModelManager.modelManager().getCurrentUser().getId(),data.getId(),false);

        }else {
            getUserDetailsCall = api.getLikePost(data.getType(),ModelManager.modelManager().getCurrentUser().getId(),data.getId(),true);

        }
        getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
            @Override
            public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        likeListener.onClick();
                        if(response.body().getResponseCode()==200)
                        {

                            Toaster.toast(response.body().getResponseMessage());
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

    public interface  ProfessionalLikeListener{
        void onClick();
    }

    public void updateData(List<GetPropertyListing.Data> responseData) {
        this.dataList = responseData;
        notifyDataSetChanged();
    }

}
