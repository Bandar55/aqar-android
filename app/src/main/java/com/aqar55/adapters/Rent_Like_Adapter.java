package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.databinding.ItemListBottomNavSaleLikedBinding;


public class Rent_Like_Adapter extends RecyclerView.Adapter<Rent_Like_Adapter.ViewHolder> {
    ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding;
    private LayoutInflater inflater;
    private Context context;
    private int size = 4;

    public Rent_Like_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_bottom_nav_sale_liked, viewGroup, false);
        return new ViewHolder(itemListBottomNavSaleLikedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding;

        ViewHolder(@NonNull ItemListBottomNavSaleLikedBinding itemListBottomNavSaleLikedBinding) {
            super(itemListBottomNavSaleLikedBinding.getRoot());
            this.itemListBottomNavSaleLikedBinding = itemListBottomNavSaleLikedBinding;
        }
    }
}
