package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.activitys.Chat_Activity;
import com.aqar55.databinding.ItemBusinessListBinding;
import com.aqar55.databinding.ItemProfessionalTabListBinding;


public class Property_Listing_Adpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LayoutInflater inflater;
    private ItemProfessionalTabListBinding itemProfessionalTabListBinding;
    private Context context;

    public Property_Listing_Adpter(Context context) {
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        if (i==0){
            itemProfessionalTabListBinding = DataBindingUtil.inflate(inflater, R.layout.item_professional_tab_list, viewGroup, false);
            return new ViewHolder(itemProfessionalTabListBinding);
        }else {
            View view = inflater.inflate(R.layout.item_list_bottom_nav,viewGroup,false);
            return new ViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemBusinessListBinding itemBusinessListBinding;

        ItemProfessionalTabListBinding itemProfessionalTabListBinding;


        ViewHolder(@NonNull ItemProfessionalTabListBinding itemProfessionalTabListBinding) {
            super(itemProfessionalTabListBinding.getRoot());
            this.itemProfessionalTabListBinding = itemProfessionalTabListBinding;
            itemProfessionalTabListBinding.chatIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Chat_Activity.class);
                    intent.putExtra("CHAT","chat");
                    context.startActivity(intent);
                }
            });
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder {
        ViewHolder2(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Chat_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}

