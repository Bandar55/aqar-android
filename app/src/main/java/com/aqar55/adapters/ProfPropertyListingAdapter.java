package com.aqar55.adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqar55.activitys.MainActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.GetPropertyListing;
public class ProfPropertyListingAdapter extends RecyclerView.Adapter<ProfPropertyListingAdapter.ProfViewHolder> {
    private Context context;
    private List<GetPropertyListing.Data> dataList;
    private OnPropertyListingItemClick onPropertyListingItemClick;
    public ProfPropertyListingAdapter(Context context, List<GetPropertyListing.Data> dataList, OnPropertyListingItemClick onPropertyListingItemClick) {
        this.context = context;
        this.dataList = dataList;
        this.onPropertyListingItemClick = onPropertyListingItemClick;
    }

    @NonNull
    @Override
    public ProfViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_bottom_nav,viewGroup,false);
        return new ProfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfViewHolder profViewHolder, int i) {
        profViewHolder.textView84.setText(dataList.get(i).getTitle());
        profViewHolder.textView86.setText(dataList.get(i).getCategory());
        profViewHolder.textView87.setText(dataList.get(i).getPlotsize()+" "+dataList.get(i).getPlotsizeunit());
        if(dataList.get(i).getType().equalsIgnoreCase("sale")){
            profViewHolder.textView85.setText(dataList.get(i).getCurrency()+" "+dataList.get(i).getTotalpricesale());
            profViewHolder.textView88.setText(dataList.get(i).getCurrency()+" "+dataList.get(i).getPricepermeter()+"/"+dataList.get(i).getPlotsizeunit());
        }else
            profViewHolder.textView85.setText(dataList.get(i).getCurrency()+" "+dataList.get(i).getTotalPrice()+" "+dataList.get(i).getRentTime());
        if ( dataList.get(i).getImagesfile()!= null && !dataList.get(i).getImagesfile().isEmpty()) {
            Glide.with(context).load(dataList.get(i).getImagesfile().get(0).getImage())
                    .placeholder(new ColorDrawable(Color.GRAY)).into(profViewHolder.imageView24);
        }

        if(ModelManager.modelManager().getCurrentUser()!=null){
            if(dataList.get(i).getLikedStatus()!=null){
                if (dataList.get(i).getLikedStatus().equalsIgnoreCase("yes")) {
                    profViewHolder.iv_like.setImageResource(R.drawable.like_icon_new);

                } else {
                    profViewHolder.iv_like.setImageResource(R.drawable.unselcted_heart);
                }
            }
        }

        profViewHolder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailSale.class);
            intent.putExtra("propid", dataList.get(i).getId());
            intent.putExtra("type", "normal");
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return dataList!=null?dataList.size():0;
    }

    public class ProfViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView84)
        TextView textView84;
        @BindView(R.id.textView85)
        TextView textView85;
        @BindView(R.id.textView86)
        TextView textView86;
        @BindView(R.id.textView87)
        TextView textView87;
        @BindView(R.id.textView88)
        TextView textView88;
        @BindView(R.id.imageView24)
        ImageView imageView24;
        @BindView(R.id.iv_like)
        ImageView iv_like;
        @BindView(R.id.relativeLayout4)
        ConstraintLayout constraintLayout;
        public ProfViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        @OnClick({R.id.iv_like,R.id.iv_share,R.id.iv_calling,R.id.chat})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.iv_like:
                    if(ModelManager.modelManager().getCurrentUser()!=null)
                        onPropertyListingItemClick.onLikeClick(getAdapterPosition());
                    else
                        context.startActivity(Login_Signup_Button_Activity.getIntent(context));
                    break;
                case R.id.iv_share:
                    onPropertyListingItemClick.onShareClick(getAdapterPosition());
                    break;
                case R.id.iv_calling:
                    onPropertyListingItemClick.onCallClick(getAdapterPosition());
                    break;
                case R.id.chat:
                    if(ModelManager.modelManager().getCurrentUser()!=null)
                       onPropertyListingItemClick.onChatClick(getAdapterPosition());
                    else
                        context.startActivity(Login_Signup_Button_Activity.getIntent(context));
                    break;
            }
        }
    }
    public void updateAdapter(){
        notifyDataSetChanged();
    }
    public interface OnPropertyListingItemClick{
        void onShareClick(int position);
        void onCallClick(int position);
        void onChatClick(int position);
        void onLikeClick(int position);
    }
}
