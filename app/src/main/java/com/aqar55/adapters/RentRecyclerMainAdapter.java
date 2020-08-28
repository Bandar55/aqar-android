package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.RowCatRentBinding;
import com.aqar55.model.GetCategoryList;

public class RentRecyclerMainAdapter extends RecyclerView.Adapter<RentRecyclerMainAdapter.ViewHolder> {
    private RowCatRentBinding categorylistitemBinding;
    private LayoutInflater inflater;
    private Context context;
    private List<GetCategoryList.Data> data;
    private OnRentClickListener onRentClickListener;
    public RentRecyclerMainAdapter(Context context, List<GetCategoryList.Data> data,OnRentClickListener onRentClickListener) {
        this.data = data;
        this.context = context;
        this.onRentClickListener = onRentClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        categorylistitemBinding = DataBindingUtil.inflate(inflater, R.layout.row_cat_rent, viewGroup, false);
        return new ViewHolder(categorylistitemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (data != null) {
            viewHolder.categorylistitemBinding.textView109.setText(data.get(i).getName().substring(0,1).toUpperCase() + data.get(i).getName().substring(1));
            if(data.get(i).isSelected()){
                viewHolder.categorylistitemBinding.ivCheckBtn.setImageResource(R.drawable.blue_tick_mark);

            }else {
                viewHolder.categorylistitemBinding.ivCheckBtn.setImageDrawable(null);


            }
        }
    }

    @Override
    public int getItemCount() {
        if (data == null && data.isEmpty()) {
            return 0;
        } else return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        RowCatRentBinding categorylistitemBinding;
        ViewHolder(@NonNull RowCatRentBinding categorylistitemBinding) {
            super(categorylistitemBinding.getRoot());
            this.categorylistitemBinding = categorylistitemBinding;
            this.categorylistitemBinding.clCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        onRentClickListener.onRentClick(getAdapterPosition(),data);
                }
            });

        }
    }

    public interface OnRentClickListener{
        void onRentClick(int position, List<GetCategoryList.Data> data);
    }
}
