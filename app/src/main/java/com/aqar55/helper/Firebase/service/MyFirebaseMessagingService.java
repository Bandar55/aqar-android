package com.aqar55.helper.Firebase.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.aqar55.activitys.ChatActivity;
import com.aqar55.activitys.ProductDetailSale;
import com.aqar55.activitys.ProfPropertyNameActivity;
import com.aqar55.helper.Toaster;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import com.aqar55.activitys.List_Main_Activity;
import com.aqar55.fragments.Chat_Main_Fragment;
import com.aqar55.helper.Firebase.app.Config;
import com.aqar55.helper.Firebase.utils.NotificationUtils;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Utils;

import java.util.Map;

import static com.aqar55.helper.ApplicationManager.getContext;

/**
 * Created by Siddiqui on 18/7/17.
 * Copyright to Mobulous Technology Pvt. Ltd.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "PUSH";
    private  String id="";

    private NotificationUtils notificationUtils;
   

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage == null)
            return;

        try {
            handleMyDataMessage(remoteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMyDataMessage(RemoteMessage remoteMessage) throws Exception {

        Log.d("daas", remoteMessage + "");
        Map<String, String> data = remoteMessage.getData();


        JSONObject jsonObject = new JSONObject(data);
       // String title = jsonObject.getString("sound");
        String type = jsonObject.getString("type");
        String body = jsonObject.getString("message");




        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

           NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            if (type.equalsIgnoreCase("chat")) {
                Intent intent=ChatActivity.getIntent(getApplicationContext(),jsonObject.getString("sender"),jsonObject.getString("receiver"),jsonObject.getString("propertyTitle"),jsonObject.getString("desc"));
                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), intent);

            }else if(type.equalsIgnoreCase("property")){
                String    id = jsonObject.getString("id");
                Intent resultIntent = new Intent(getContext(), ProductDetailSale.class);
                resultIntent.putExtra("propid", id);
                resultIntent.putExtra("type","normal");
                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), resultIntent);
                if(ModelManager.modelManager().getCurrentUser()!=null)
                    resultIntent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
                else
                    resultIntent.putExtra("user_id","");

            }else if(type.equalsIgnoreCase("profile")){
                String    userId = jsonObject.getString("userId");
                String    userType = jsonObject.getString("userType");
                String    id = jsonObject.getString("id");

                Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
                intent.putExtra("_userId",id);
                intent.putExtra("info_window_type",userType);
                intent.putExtra("businessUserId",userId);

                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), intent);

            }


        } else {
            notificationUtils.playNotificationSound();

            if (type.equalsIgnoreCase("chat")) {
               Intent intent=ChatActivity.getIntent(getApplicationContext(),jsonObject.getString("sender"),jsonObject.getString("receiver"),jsonObject.getString("propertyTitle"),jsonObject.getString("desc"));
                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), intent);

            }else if(type.equalsIgnoreCase("property")){
                String    id = jsonObject.getString("id");
                Intent resultIntent = new Intent(getContext(), ProductDetailSale.class);
                resultIntent.putExtra("propid", id);
                resultIntent.putExtra("type","normal");
                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), resultIntent);
                if(ModelManager.modelManager().getCurrentUser()!=null)
                    resultIntent.putExtra("user_id",ModelManager.modelManager().getCurrentUser().getId());
                else
                    resultIntent.putExtra("user_id","");

            }else if(type.equalsIgnoreCase("profile")){
                String    userId = jsonObject.getString("userId");
                String    userType = jsonObject.getString("userType");
                String    id = jsonObject.getString("id");

                Intent intent = new Intent(getContext(), ProfPropertyNameActivity.class);
                intent.putExtra("_userId",id);
                intent.putExtra("info_window_type",userType);
                intent.putExtra("businessUserId",userId);

                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), intent);

            }



            else {
                Intent resultIntent = List_Main_Activity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), "", body, Utils.generateTimeStamp(), resultIntent);
            }


        }
            /*else if (body.equals(kCancelBookingFoodie)) {
                Intent resultIntent = MyReservationScreen.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
            } else if (body.equals(kCancelBookingHost)) {
                Intent resultIntent = MyHistoryActivity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
            } else if (body.equals(kCompletebooking)) {
                Intent resultIntent = MyHistoryActivity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
            }else  if(body.equals(kPaymentRecieve)){
                Intent resultIntent = HomeHostActivity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
            }else if(body.equals("by_admin")){
                Intent resultIntent= HostNotificationActivity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);

            }

            else {
                Intent resultIntent = MyHistoryActivity.getIntent(getApplicationContext());
                showNotificationMessage(getApplicationContext(), title, body, Utils.generateTimeStamp(), resultIntent);
            }
*/
    }


    private Integer convertIntoInteger(String value) {
        Integer intvalue = 0;
        try {
            intvalue = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intvalue;
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


}
