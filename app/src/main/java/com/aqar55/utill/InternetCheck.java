package com.aqar55.utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck
{
    private Context context;

    public InternetCheck(Context context){
        this.context=context;
    }

    public boolean isConnect()
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting())
            {
                return true;
            }
        }
        return false;
    }

}