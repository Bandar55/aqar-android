package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.CategorylistitemBinding;
import com.aqar55.model.GetCategoryList;


public class SaleMainCategoryListAdapter extends RecyclerView.Adapter<SaleMainCategoryListAdapter.ViewHolder> {
    private CategorylistitemBinding categorylistitemBinding;
    private LayoutInflater inflater;
    private Context context;
    private List<GetCategoryList.Data> data;
    public OnSaleCategoryClickListener onSaleCategoryClickListener;

    public SaleMainCategoryListAdapter(Context context, List<GetCategoryList.Data> data,OnSaleCategoryClickListener onSaleCategoryClickListener) {
        this.data = data;
        this.context = context;
        this.onSaleCategoryClickListener = onSaleCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        categorylistitemBinding = DataBindingUtil.inflate(inflater, R.layout.categorylistitem, viewGroup, false);
        return new ViewHolder(categorylistitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (data != null) {
            viewHolder.categorylistitemBinding.textView109.setText( data.get(i).getName().substring(0,1).toUpperCase() + data.get(i).getName().substring(1));



            if(data.get(i).isSelected()){
                viewHolder.categorylistitemBinding.radioButton.setImageResource(R.drawable.blue_tick_mark);
            }else {
                viewHolder.categorylistitemBinding.radioButton.setImageDrawable(null);


            }

        }
    }

    public void update(List<GetCategoryList.Data> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (data == null && data.isEmpty()) {
            return 0;
        } else return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CategorylistitemBinding categorylistitemBinding;

        ViewHolder(@NonNull CategorylistitemBinding categorylistitemBinding) {
            super(categorylistitemBinding.getRoot());
            this.categorylistitemBinding = categorylistitemBinding;
            this.categorylistitemBinding.clCategory.setOnClickListener(v -> onSaleCategoryClickListener.onSaleCategoryClick(getAdapterPosition(),data));
        }
    }

    public interface OnSaleCategoryClickListener{
        void onSaleCategoryClick(int position, List<GetCategoryList.Data> data);
    }
}
