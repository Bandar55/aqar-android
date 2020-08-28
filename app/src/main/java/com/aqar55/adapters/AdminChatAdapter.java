package com.aqar55.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.aqar55.R;
import com.aqar55.model.AdminDetailsModel;

public class AdminChatAdapter extends RecyclerView.Adapter<AdminChatAdapter.ViewHolder> {
    private List<AdminDetailsModel.Data> dataArrayList;
    private Context context;
    public AdminChatAdapter(Context context, List<AdminDetailsModel.Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_admin_chat,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvAdminFirst.setText(dataArrayList.get(i).getReason());
        viewHolder.tvAdminSecound.setText(dataArrayList.get(i).getDetails());

    }

    @Override
    public int getItemCount() {
        if(dataArrayList!=null)
        return dataArrayList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_admin_first)
        TextView tvAdminFirst;
        @BindView(R.id.tv_admin_secound)
        TextView tvAdminSecound;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
