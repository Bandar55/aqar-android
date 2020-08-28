package com.aqar55.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import com.aqar55.constant.Constants;

import static com.aqar55.constant.Constants.kAppPreferences;

public class BaseManager  {

    private static final String TAG = BaseManager.class.getSimpleName();
    /********************************Statuc Methods for SharedPreferences**********************************/
    /**stores dataObject to SharedPreferences and commit the user default. This must be called in main thread*/
    public synchronized static void saveDataIntoPreferences(Object dataObject, String key)   {
        Gson gson = new Gson();
        String json = gson.toJson(dataObject);
        int size = json.length();
        Log.v(TAG,"size of json "+size);

        SharedPreferences sharedPreferences = ApplicationManager.getContext().getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    /**Return the dataObject of type typeOfT class if successfull. if fail to get data from SharedPreferences then returns null*/
    public synchronized static <T> T getDataFromPreferences(String key, Type typeOfT)   {
        SharedPreferences sharedPreferences = ApplicationManager.getContext().getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        T dataObject = null;
        try {
            Gson gson = new Gson();
            String jsonString = sharedPreferences.getString(key, Constants.kEmptyString);
            dataObject = gson.fromJson(jsonString, typeOfT);
        }catch (Exception e)    {
            e.printStackTrace();
        }

        return dataObject;
    }
    /********************************Statuc Methods for SharedPreferences**********************************/


    public static List<ApplicationInfo> getAppInfo(){
        PackageManager packageManager = ApplicationManager.getContext().getPackageManager();
        List<ApplicationInfo> info = packageManager.getInstalledApplications(0);



        return info;
    }

    /**Get the app name if available under android:label tag in Manifest file. if not specified then return kDefaultAppName*/
    public static String getAppName()   {
        String appName = Constants.kDefaultAppName;

        try {
            ApplicationInfo applicationInfo = ApplicationManager.getContext().getApplicationInfo();
            int stringId = applicationInfo.labelRes;
            appName = (stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : ApplicationManager.getContext().getString(stringId));
        }catch (Exception e)    {
            e.printStackTrace();
        }
        finally {
            return appName;
        }
    }
}

