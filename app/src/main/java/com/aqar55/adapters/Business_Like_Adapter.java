package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.databinding.ItemBusinessTabLikedBinding;


public class Business_Like_Adapter extends RecyclerView.Adapter<Business_Like_Adapter.ViewHolder> {
    private ItemBusinessTabLikedBinding itemBusinessTabLikedBinding;
    private LayoutInflater inflater;
    private Context context;

    public Business_Like_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemBusinessTabLikedBinding = DataBindingUtil.inflate(inflater, R.layout.item_business_tab_liked, viewGroup, false);
        return new ViewHolder(itemBusinessTabLikedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemBusinessTabLikedBinding itemBusinessTabLikedBinding;

        ViewHolder(@NonNull ItemBusinessTabLikedBinding itemBusinessTabLikedBinding) {
            super(itemBusinessTabLikedBinding.getRoot());
            this.itemBusinessTabLikedBinding = itemBusinessTabLikedBinding;
        }
    }
}
