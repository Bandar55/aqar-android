package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.activitys.MainActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.databinding.ItemNotificationBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Utils;
import com.aqar55.model.NotificationModel;

import static com.aqar55.helper.ApplicationManager.getContext;


public class Notification_Main_Adapter extends RecyclerView.Adapter<Notification_Main_Adapter.ViewHolder> {
    private List<NotificationModel.Data> dataList;
    private ItemNotificationBinding itemNotificationBinding;
    private LayoutInflater inflater;
    private Context context;

    public Notification_Main_Adapter(Context context, List<NotificationModel.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        itemNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.item_notification, viewGroup, false);
        return new ViewHolder(itemNotificationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        itemNotificationBinding.tvNotification.setText(dataList.get(i).getTitle());
        itemNotificationBinding.tvTime.setText(setTime(dataList.get(i).getCreated()));

        if (dataList.get(i).getNotificationtype().equalsIgnoreCase("chat")) {
            if (ModelManager.modelManager().getCurrentUser() != null) {
                itemNotificationBinding.constantlayout.setOnClickListener(view -> context.startActivity(ChatActivity.getIntent(context, dataList.get(i).getNotificationsender(), dataList.get(i).getNotificationreceiver(), dataList.get(i).getDescription(), dataList.get(i).getPropertyTitle())));

            } else {
                context.startActivity(Login_Signup_Button_Activity.getIntent(context));

            }


        } else if (dataList.get(i).getNotificationtype().equalsIgnoreCase("property")) {
            itemNotificationBinding.constantlayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailSale.class);
                intent.putExtra("propid", dataList.get(i).getPropOrRoomOrUserId());
                intent.putExtra("type", "normal");
                context.startActivity(intent);
            });
        } else {
            itemNotificationBinding.constantlayout.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
                intent.putExtra("_userId", dataList.get(i).getPropOrRoomOrUserId());
                intent.putExtra("info_window_type", dataList.get(i).getPropOrUserType());
                intent.putExtra("businessUserId", dataList.get(i).getUserid());
                context.startActivity(intent);
            });


        }

    }


    private String setTime(String createdDate) {
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding itemNotificationBinding;

        ViewHolder(@NonNull ItemNotificationBinding itemNotificationBinding) {
            super(itemNotificationBinding.getRoot());
            this.itemNotificationBinding = itemNotificationBinding;
        }
    }
}
