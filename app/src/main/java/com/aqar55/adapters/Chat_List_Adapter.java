package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.aqar55.helper.Toaster;
import com.aqar55.model.ChatDelateModel;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.aqar55.R;
import com.aqar55.databinding.ItemChatBinding;
import com.aqar55.model.UserChatListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Chat_List_Adapter extends RecyclerView.Adapter<Chat_List_Adapter.ViewHolder> {
    ItemChatBinding itemChatBinding;

    private LayoutInflater inflater;
    private Context context;
    private List<UserChatListResponse.Data> data;
    private ClickAtChatList clickAtChatList;
    private ClickAtChatListLong clickAtChatListLong;

    public Chat_List_Adapter(Context context, List<UserChatListResponse.Data> data) {
        this.context = context;
        this.data = data;
    }

    public void MyChatLiatClick(ClickAtChatList clickAtChatList) {
        this.clickAtChatList = clickAtChatList;

    }

    public void MyChatLongClick(ClickAtChatListLong clickAtChatListLong) {
        this.clickAtChatListLong = clickAtChatListLong;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemChatBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat, viewGroup, false);
        return new ViewHolder(itemChatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemChatBinding.tvTitle.setText(data.get(position).getFullName());
        viewHolder.itemChatBinding.tvMsg.setText(data.get(position).getLastmessage());


        viewHolder.itemChatBinding.tvTime.setText(time(data.get(position).getModified()));

        if (data.get(position).getProfileImage() != null) {
            if (!data.get(position).getProfileImage().isEmpty())
                Picasso.get().load(data.get(position).getProfileImage()).into(viewHolder.itemChatBinding.ivChat);
            else
                Picasso.get().load(R.drawable.user_icon).into(viewHolder.itemChatBinding.ivChat);
        } else {

                Picasso.get().load(R.drawable.user_icon).into(viewHolder.itemChatBinding.ivChat);
        }


        viewHolder.itemChatBinding.constLayout.setOnClickListener(v -> clickAtChatList.onClick(position));

        viewHolder.itemChatBinding.constLayout.setOnLongClickListener(view -> {
            clickAtChatListLong.onLongClick(position);
            return false;
        });

    }


    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemChatBinding itemChatBinding;

        ViewHolder(@NonNull ItemChatBinding itemChatBinding) {
            super(itemChatBinding.getRoot());
            this.itemChatBinding = itemChatBinding;
        }

    }

    public String time(String createdDate) {
        String your_format = "";
        if (createdDate != null) {
            //server comes format ?

            String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
            String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            try {
                Date date = sdf.parse(createdDate);
                System.out.println(date);
                String dateText = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
                your_format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
                System.out.println(your_format);
                String[] splitted = your_format.split(" ");
                // Now you can set the TextView here
                // layoutPropertyDetailBinding.postedon.setText(String.valueOf("Posted on " + dateText));

            } catch (Exception e) {
                System.out.println(e.toString()); //date format error
            }
        }
        return your_format;
    }


    public interface ClickAtChatList {
        void onClick(int position);
    }

    public interface ClickAtChatListLong {

        void onLongClick(int position);


    }
}
