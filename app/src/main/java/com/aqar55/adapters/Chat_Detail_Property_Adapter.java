package com.aqar55.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.aqar55.R;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.MessageChat;

public class Chat_Detail_Property_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RIGHT_BUBBLE = 1;
    private static final int LEFT_BUBBLE = 0;
    private List<MessageChat.Data> MessageList;
    private Context context;
    public Chat_Detail_Property_Adapter(List<MessageChat.Data> messageList, Context context) {
        this.MessageList = messageList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View viewHolder = null;
        RecyclerView.ViewHolder rcv = null;
        try {
            switch (i) {
                case RIGHT_BUBBLE:
                    viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_right_chatting, parent, false);
                    rcv = new RightBubbleViewHolder(viewHolder);
                    break;

                case LEFT_BUBBLE:
                    viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.layput_left_chatting, parent, false);
                    rcv = new LeftBubbleViewHolder(viewHolder);
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rcv;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (ModelManager.modelManager().getCurrentUser().getId().matches(MessageList.get(position).getSenderId()))
                return RIGHT_BUBBLE;
            else
                return LEFT_BUBBLE;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        try {

            switch (viewHolder.getItemViewType()) {
                case RIGHT_BUBBLE:
                    RightBubbleViewHolder rightSideViewHolder = (RightBubbleViewHolder) viewHolder;
                    if (MessageList.get(position).getAttachment_type().equalsIgnoreCase("MEDIA")) {



                        rightSideViewHolder.constraintLayoutRight.setVisibility(View.GONE);
                        rightSideViewHolder.constraintLayoutMediaRight.setVisibility(View.VISIBLE);
                        Picasso.get().load(MessageList.get(position).getAttachment()).into(rightSideViewHolder.ivMediaRight);
                        rightSideViewHolder.tvMediaTime.setText(setTime(MessageList.get(position).getCreated()));

                    } else {

                        rightSideViewHolder.constraintLayoutRight.setVisibility(View.VISIBLE);
                        rightSideViewHolder.constraintLayoutMediaRight.setVisibility(View.GONE);
                        rightSideViewHolder.tvTimeRight.setText(setTime(MessageList.get(position).getCreated()));
                        rightSideViewHolder.tvMsgRight.setText(MessageList.get(position).getMessage());
                    }

                    rightSideViewHolder.constraintLayoutMediaRight.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(MessageList.get(position).getAttachment()));
                        context.startActivity(intent);
                    });


                    break;

                case LEFT_BUBBLE:
                    LeftBubbleViewHolder leftSideViewHolder = (LeftBubbleViewHolder) viewHolder;


                    if (MessageList.get(position).getAttachment_type().equalsIgnoreCase("MEDIA")) {


                        leftSideViewHolder.constraintLayoutWithOutMedia.setVisibility(View.GONE);
                        leftSideViewHolder.constraintLayoutRightWithMedia.setVisibility(View.VISIBLE);
                        leftSideViewHolder.tvTimeMediaLeft.setVisibility(View.VISIBLE);
                        leftSideViewHolder.tvTimeMediaLeft.setText(setTime(MessageList.get(position).getCreated()));
                        Picasso.get().load(MessageList.get(position).getAttachment()).into(leftSideViewHolder.ivLeft);

                    } else {
                        leftSideViewHolder.constraintLayoutWithOutMedia.setVisibility(View.VISIBLE);
                        leftSideViewHolder.constraintLayoutRightWithMedia.setVisibility(View.GONE);
                        leftSideViewHolder.tvTimeMediaLeft.setVisibility(View.GONE);
                        leftSideViewHolder.tvTime.setText(setTime(MessageList.get(position).getCreated()));
                        leftSideViewHolder.tvMsgLeft.setText(MessageList.get(position).getMessage());
                    }

                    leftSideViewHolder.constraintLayoutRightWithMedia.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(MessageList.get(position).getAttachment()));
                        context.startActivity(intent);
                    });



            }


        } catch (Exception e) {
            e.printStackTrace();
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
        if (MessageList != null)
            return MessageList.size();
        else
            return 0;
    }

    private class RightBubbleViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvMsgRight;
        TextView tvTimeRight;
        TextView tvMediaTime;
        ImageView ivMediaRight;
        ConstraintLayout constraintLayoutMediaRight;
        ConstraintLayout constraintLayoutRight;
        RightBubbleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvMsgRight = (view).findViewById(R.id.tvMsgRight);
            tvTimeRight = (view).findViewById(R.id.tvTimeRight);
            tvMediaTime = (view).findViewById(R.id.tv_time);
            ivMediaRight = (view).findViewById(R.id.iv_profile);
            constraintLayoutMediaRight = (view).findViewById(R.id.cons_media);
            constraintLayoutRight = (view).findViewById(R.id.constraintLayout5);
        }

    }


    private class LeftBubbleViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvMsgLeft;
        TextView tvTime;
        TextView tvTimeMediaLeft;
        ImageView ivLeft;
        ConstraintLayout constraintLayoutWithOutMedia;
        ConstraintLayout constraintLayoutRightWithMedia;
        LeftBubbleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvMsgLeft = (view).findViewById(R.id.tvMsg);
            tvTime = (view).findViewById(R.id.tvTime);

            tvTimeMediaLeft = (view).findViewById(R.id.tv_time_left);
            ivLeft = (view).findViewById(R.id.iv_profile_left);
            constraintLayoutWithOutMedia = (view).findViewById(R.id.constraintLayout6);
            constraintLayoutRightWithMedia = (view).findViewById(R.id.const_layout_left);

        }

    }
}
