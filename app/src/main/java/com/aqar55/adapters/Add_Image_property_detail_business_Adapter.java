package com.aqar55.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;


public class Add_Image_property_detail_business_Adapter extends RecyclerView.Adapter<Add_Image_property_detail_business_Adapter.ViewHolder> {
    private Context context;
    private int size = 4;

    public Add_Image_property_detail_business_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;
        }else {
            return 2;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i==1){
            view = LayoutInflater.from(context).inflate(R.layout.layout_upload_image_recycler, viewGroup, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_professional_recycler_item, viewGroup, false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

