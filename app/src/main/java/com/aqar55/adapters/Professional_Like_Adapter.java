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
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.databinding.ItemProfessionalTabLikedBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.model.ProfessionalBusinessModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Professional_Like_Adapter extends RecyclerView.Adapter<Professional_Like_Adapter.ViewHolder> {
    ItemProfessionalTabLikedBinding itemProfessionalTabLikedBinding;
    private LayoutInflater inflater;
    private Context context;
    private List<ProfessionalBusinessModelResponse.Data> data;
    private ProfessionalLikeListener listener;
    private String type="";


    public Professional_Like_Adapter(Context context, List<ProfessionalBusinessModelResponse.Data> data,String type) {
        this.context = context;
        this.data=data;
        this.type = type;
    }

   public void  MyProfessionalClick(ProfessionalLikeListener listener){
        this.listener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemProfessionalTabLikedBinding = DataBindingUtil.inflate(inflater, R.layout.item_professional_tab_liked, viewGroup, false);
        return new ViewHolder(itemProfessionalTabLikedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(type.equalsIgnoreCase("Professional")){
            viewHolder.itemListBottomNavSaleLikedBinding.tvName.setText(data.get(position).getFullname());
            viewHolder.itemListBottomNavSaleLikedBinding.textView105.setText("Professional ID : "+data.get(position).getProfessionalId());
            viewHolder.itemListBottomNavSaleLikedBinding.textView104.setText("Professional Category : "+data.get(position).getCategory());
            viewHolder.itemListBottomNavSaleLikedBinding.textView106.setText("Detail : "+data.get(position).getDescription());
        }else {
            viewHolder.itemListBottomNavSaleLikedBinding.tvName.setText(data.get(position).getFullname());
            viewHolder.itemListBottomNavSaleLikedBinding.textView105.setText("Business ID : "+data.get(position).getBusinessId());
            viewHolder.itemListBottomNavSaleLikedBinding.textView104.setText("Business Category : "+data.get(position).getCategory());
            viewHolder.itemListBottomNavSaleLikedBinding.textView106.setText("Detail : "+data.get(position).getDescription());
        }

        if(data.get(position).getProfileimage()!=null && !data.get(position).getProfileimage().isEmpty())
            Glide.with(context).load(data.get(position).getProfileimage()).into(itemProfessionalTabLikedBinding.ivProfLiked);


        viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.like_icon_new);

        if(data.get(position).getLikedStatus()!=null){
            if(data.get(position).getLikedStatus().equalsIgnoreCase("yes"))
                viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.like_icon_new);
            else
                viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
        }

        viewHolder.itemListBottomNavSaleLikedBinding.ivLike.setOnClickListener(v -> {
            if(ModelManager.modelManager().getCurrentUser()==null){
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));
            }else {
                getLikeApiCallingUser(data.get(position));
            }
        });


        itemProfessionalTabLikedBinding.ivCalling.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", data.get(position).getMobilenumber(), null));
            context.startActivity(intent);

        });
        itemProfessionalTabLikedBinding.ivShare.setOnClickListener(v -> {
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


        itemProfessionalTabLikedBinding.imageView46.setOnClickListener(v->{

            if(ModelManager.modelManager().getCurrentUser()!=null){
                context.startActivity(ChatActivity.getIntent(context,data.get(position).getUserid().toString(),data.get(position).getId(),data.get(position).getFullname(),data.get(position).getDescription()));

            }else {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            }


        });
    }

    @Override
    public int getItemCount() {
        if(data!=null)
            return data.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemProfessionalTabLikedBinding itemListBottomNavSaleLikedBinding;

        ViewHolder(@NonNull ItemProfessionalTabLikedBinding itemListBottomNavSaleLikedBinding) {
            super(itemListBottomNavSaleLikedBinding.getRoot());
            this.itemListBottomNavSaleLikedBinding = itemListBottomNavSaleLikedBinding;
            itemListBottomNavSaleLikedBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfPropertyNameActivity.class);
                    intent.putExtra("_userId",data.get(getAdapterPosition()).getId());
                    intent.putExtra("businessUserId",data.get(getAdapterPosition()).getUserid());

                    if(type.equalsIgnoreCase("Professional"))
                         intent.putExtra("info_window_type","professional");
                    else
                        intent.putExtra("info_window_type","business");

                    context.startActivity(intent);
                }
            });
        }
    }


    private void getLikeApiCallingUser(ProfessionalBusinessModelResponse.Data data) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;

        if(data.getLikedStatus()!=null){
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
                            if(response.body().getResponseCode()==200)
                            {
                                listener.onClick();
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
        }else {
            getUserDetailsCall = api.getLikePost(data.getType(),ModelManager.modelManager().getCurrentUser().getId(),data.getId(),false);
            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body()!=null) {
                            if(response.body().getResponseCode()==200)
                            {
                                listener.onClick();
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
    }

    public interface  ProfessionalLikeListener{
        void onClick();
    }

}

