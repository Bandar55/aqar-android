package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.aqar55.BuildConfig;
import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.databinding.ItemListBottomNavBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.GetPropertyListing;
import retrofit2.Response;

public class All_List_Main_Adapter extends RecyclerView.Adapter<All_List_Main_Adapter.ViewHolder> {
    LayoutInflater inflater;
    ItemListBottomNavBinding itemListBottomNavBinding;
    public OnLikeClickListener onLikeClickListener;
    private Context context;
    private Response<GetPropertyListing> response;

    public All_List_Main_Adapter(Context context, Response<GetPropertyListing> response, OnLikeClickListener onLikeClickListener) {
        this.context = context;
        this.response = response;
        this.onLikeClickListener = onLikeClickListener;
        notifyDataSetChanged();
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
        if (response.body() != null) {
            if (response.body().getData().get(i).getType().equalsIgnoreCase("sale")) {
                viewHolder.itemListBottomNavBinding.textView84.setText(response.body().getData().get(i).getTitle());
                viewHolder.itemListBottomNavBinding.textView85.setText(response.body().getData().get(i).getCurrency()+response.body().getData().get(i).getTotalpricesale());
                viewHolder.itemListBottomNavBinding.textView86.setText(response.body().getData().get(i).getCategory());
                viewHolder.itemListBottomNavBinding.textView87.setText(response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit());
                viewHolder.itemListBottomNavBinding.textView88.setText((response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalPrice() + "/" + response.body().getData().get(i).getPlotsizeunit()));
            } else if (response.body().getData().get(i).getType().equalsIgnoreCase("rent")) {
                viewHolder.itemListBottomNavBinding.textView84.setText(response.body().getData().get(i).getTitle());
                viewHolder.itemListBottomNavBinding.textView85.setText(response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalPrice()+" "+response.body().getData().get(i).getRentTime());
                viewHolder.itemListBottomNavBinding.textView86.setText(response.body().getData().get(i).getCategory());
                viewHolder.itemListBottomNavBinding.textView87.setText(response.body().getData().get(i).getPlotsize()+" "+response.body().getData().get(i).getPlotsizeunit());
                //viewHolder.itemListBottomNavBinding.textView88.setText(response.body().getData().get(i).getCurrency()+" "+response.body().getData().get(i).getTotalPrice() + "/" + response.body().getData().get(i).getPlotsizeunit());
            } else if (response.body().getData().get(i).getType().equalsIgnoreCase("professional")) {
                viewHolder.itemListBottomNavBinding.textView84.setText(response.body().getData().get(i).getFullName());
                String profID = "Professional ID : "+response.body().getData().get(i).getProfessionalid();
                SpannableString spannableString = new SpannableString(profID);
                spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.green)),0,profID.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.itemListBottomNavBinding.textView85.setText(spannableString);
                viewHolder.itemListBottomNavBinding.textView86.setText("Professional Category : "+response.body().getData().get(i).getCategory());
                viewHolder.itemListBottomNavBinding.textView87.setText("Detail : "+response.body().getData().get(i).getDescription());
            } else {
                viewHolder.itemListBottomNavBinding.textView84.setText(response.body().getData().get(i).getFullName());
                String businessID = "Business ID : "+response.body().getData().get(i).getBusinessid();
                SpannableString spannableString = new SpannableString(businessID);
                spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.green)),0,businessID.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.itemListBottomNavBinding.textView85.setText(spannableString);
                viewHolder.itemListBottomNavBinding.textView86.setText("Business Category : "+response.body().getData().get(i).getCategory());
                viewHolder.itemListBottomNavBinding.textView87.setText("Detail : "+response.body().getData().get(i).getDescription());
            }


            if (ModelManager.modelManager().getCurrentUser() != null) {

                if (response.body().getData().get(i).getLikedStatus() != null) {

                    if (response.body().getData().get(i).getLikedStatus().equalsIgnoreCase("yes")) {
                        viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.like_icon_new);

                    } else {
                        viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);
                    }


                } else {
                    viewHolder.itemListBottomNavBinding.ivLike.setImageResource(R.drawable.unselcted_heart);

                }


            }
            viewHolder.itemListBottomNavBinding.ivShare.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = context.getString(R.string.body_foodie);
                String shareSub = context.getString(R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            });



            viewHolder.itemListBottomNavBinding.ivCalling.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", response.body().getData().get(i).getMobileNumber(), null));
                context.startActivity(intent);
            });

            viewHolder.itemListBottomNavBinding.ivLike.setOnClickListener(v -> {
                if(ModelManager.modelManager().getCurrentUser()!=null)
                    onLikeClickListener.onLikeClick(i, response);
                else
                    context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            });


          //  viewHolder.itemListBottomNavBinding.relativeLayout4.setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetailActivity.class)));
            viewHolder.itemListBottomNavBinding.chat.setOnClickListener(v -> {


                if(ModelManager.modelManager().getCurrentUser()!=null){

                    if (ModelManager.modelManager().getCurrentUser() != null) {

                        if(response.body().getData().get(i).getTitle()!=null){
                            context.startActivity(ChatActivity.getIntent(context, response.body().getData().get(i).getUserid(), response.body().getData().get(i).getId(),response.body().getData().get(i).getTitle(),response.body().getData().get(i).getDescription()));

                        }else{
                            context.startActivity(ChatActivity.getIntent(context, response.body().getData().get(i).getUserid(), response.body().getData().get(i).getId(),response.body().getData().get(i).getFullName(),response.body().getData().get(i).getDescription()));

                        }
                    } else {
                        context.startActivity(Login_Signup_Button_Activity.getIntent(context));
                    }


                }else {
                    context.startActivity(Login_Signup_Button_Activity.getIntent(context));

                }

            });

            if (response.body().getData().get(i).getImagesfile() != null && !response.body().getData().get(i).getImagesfile().isEmpty()) {
                Glide.with(context).load(response.body().getData().get(i).getImagesfile().get(0).getImage())
                        .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
            }else {
                Glide.with(context).load(R.drawable.default_propertyimage)
                        .placeholder(new ColorDrawable(Color.GRAY)).into(viewHolder.itemListBottomNavBinding.imageView24);
            }


        }
    }

    public void updateData(Response<GetPropertyListing> responseData) {
        this.response = responseData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (response.body() != null)
            return response.body().getData().size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBottomNavBinding itemListBottomNavBinding;

        ViewHolder(@NonNull ItemListBottomNavBinding itemListBottomNavBinding) {
            super(itemListBottomNavBinding.getRoot());
            this.itemListBottomNavBinding = itemListBottomNavBinding;
        }
    }

    public interface OnLikeClickListener {
        void onLikeClick(int position, Response<GetPropertyListing> response);
    }
}
