package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aqar55.R;
import com.aqar55.databinding.ItemMainRecyclerBinding;
import com.aqar55.intefaces.RecyclerViewClickListner;

public class Main_Recycler_Adapter extends RecyclerView.Adapter<Main_Recycler_Adapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private String[] textArr = {"All", "Sale", "Rent", "Professional", "Business"};
    private RecyclerViewClickListner recyclerViewClickListner;

    public Main_Recycler_Adapter(Context context, RecyclerViewClickListner recyclerViewClickListner) {
        this.context = context;
        this.recyclerViewClickListner = recyclerViewClickListner;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
        inflater = LayoutInflater.from(context);
        ItemMainRecyclerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_main_recycler, viewGroup, false);
        return new ViewHolder(binding, recyclerViewClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.binding.textMainRecycler.setText(textArr[i]);
        if (i == 0) {
            viewHolder.binding.mainRecyclerItemView.setVisibility(View.VISIBLE);
            viewHolder.binding.mainRecyclerItemView.setBackground(ContextCompat.getDrawable(context, R.color.dark_blue));
            viewHolder.binding.textMainRecycler.setTextColor(context.getResources().getColor(R.color.dark_blue));
        }
        if (i == 1) {
            viewHolder.binding.mainRecyclerItemView.setBackground(ContextCompat.getDrawable(context, R.color.red));
            viewHolder.binding.textMainRecycler.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (i == 2) {
            viewHolder.binding.mainRecyclerItemView.setBackground(ContextCompat.getDrawable(context, R.color.green));
            viewHolder.binding.textMainRecycler.setTextColor(context.getResources().getColor(R.color.green));
        }
        if (i == 3) {
            viewHolder.binding.mainRecyclerItemView.setBackground(ContextCompat.getDrawable(context, R.color.orange));
            viewHolder.binding.textMainRecycler.setTextColor(context.getResources().getColor(R.color.orange));
        }
        if (i == 4) {
            viewHolder.binding.mainRecyclerItemView.setBackground(ContextCompat.getDrawable(context, R.color.purple));
            viewHolder.binding.textMainRecycler.setTextColor(context.getResources().getColor(R.color.purple));
        }
    }

    @Override
    public int getItemCount() {
        return textArr.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ItemMainRecyclerBinding binding;
        private RecyclerViewClickListner recyclerViewClickListner;

        ViewHolder(@NonNull ItemMainRecyclerBinding binding, RecyclerViewClickListner recyclerViewClickListner) {
            super(binding.getRoot());
            this.recyclerViewClickListner = recyclerViewClickListner;
            binding.getRoot().setOnClickListener(this);
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListner.onClick(v, getLayoutPosition());
        }
    }
}
