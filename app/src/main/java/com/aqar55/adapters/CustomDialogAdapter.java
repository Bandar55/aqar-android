package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.ItemCustomDialogRecyclerBinding;

public class CustomDialogAdapter extends RecyclerView.Adapter<CustomDialogAdapter.ViewHolder> {
    LayoutInflater inflater;
    ItemCustomDialogRecyclerBinding itemCustomDialogRecyclerBinding;
    private Context context;
    private List<String> data;

    public CustomDialogAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemCustomDialogRecyclerBinding = DataBindingUtil.inflate(inflater, R.layout.item_custom_dialog_recycler, viewGroup, false);
        return new ViewHolder(itemCustomDialogRecyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        itemCustomDialogRecyclerBinding.textView108.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemCustomDialogRecyclerBinding itemCustomDialogRecyclerBinding;


        ViewHolder(@NonNull ItemCustomDialogRecyclerBinding itemCustomDialogRecyclerBinding) {
            super(itemCustomDialogRecyclerBinding.getRoot());
            this.itemCustomDialogRecyclerBinding = itemCustomDialogRecyclerBinding;
        }
    }
}
