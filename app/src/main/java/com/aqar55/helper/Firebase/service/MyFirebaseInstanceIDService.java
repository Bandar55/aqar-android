package com.aqar55.helper.Firebase.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.aqar55.helper.BaseManager;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "sendRegistrationToServer: " + refreshedToken);

        //saving token to shared preference
        BaseManager.saveDataIntoPreferences(refreshedToken,"kDeviceToken");

    }

}
