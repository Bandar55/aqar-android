package com.aqar55.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.aqar55.R;
import com.aqar55.model.ProfessionalDataResponse;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<ProfessionalDataResponse> professionalDataResponseList;
    private OnCategoryClickListener onCategoryClickListener;
    private String type;

    public CategoryAdapter(Context context, List<ProfessionalDataResponse> professionalDataResponseList,OnCategoryClickListener onCategoryClickListener,String type) {
        this.context = context;
        this.professionalDataResponseList = professionalDataResponseList;
        this.onCategoryClickListener = onCategoryClickListener;
        this.type = type;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_main_act,viewGroup,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {

        if(type.equalsIgnoreCase("professional")){
            categoryViewHolder.tvCategoryAdapter.setTextColor(context.getResources().getColor(R.color.orange));

        }else {
            categoryViewHolder.tvCategoryAdapter.setTextColor(context.getResources().getColor(R.color.dark_blue));

        }
      //  String upperString = professionalDataResponseList.get(i).getName().substring(0,1).toUpperCase() + professionalDataResponseList.get(i).getName().substring(1);

        categoryViewHolder.tvCategoryAdapter.setText(professionalDataResponseList.get(i).getName());
        if(professionalDataResponseList.get(i).isCheck()){
            categoryViewHolder.ivCheckSign.setImageResource(R.drawable.blue_tick_mark);
        }else {
            categoryViewHolder.ivCheckSign.setImageDrawable(null);

        }

    }

    @Override
    public int getItemCount() {
        return professionalDataResponseList!=null?professionalDataResponseList.size():0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCategoryAdapter)
        TextView tvCategoryAdapter;
      @BindView(R.id.iv_check_btn)
       ImageView ivCheckSign;
        @BindView(R.id.layout_1_professional_main_map)
        LinearLayout layout_1_professional_main_map;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            layout_1_professional_main_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       onCategoryClickListener.onCategoryClick(getAdapterPosition(),professionalDataResponseList,type);
                }
            });
        }
    }

    public interface OnCategoryClickListener{
        void onCategoryClick(int position,List<ProfessionalDataResponse> professionalDataResponseList,String type);
    }

    public void updateAdapter(List<ProfessionalDataResponse> professionalDataResponseList){
        this.professionalDataResponseList = professionalDataResponseList;
        notifyDataSetChanged();
    }
}
