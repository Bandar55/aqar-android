package com.aqar55.utill;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        try {

            super.onCreate();
//          Fabric.with(this, new Crashlytics());
            myApplication = this;

//            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf");
//
//            FontCache.getInstance().addFont("Bold", "fonts/Roboto-Bold.ttf");
//            FontCache.getInstance().addFont("Medium", "fonts/Roboto-Medium.ttf");
//            FontCache.getInstance().addFont("Regular", "fonts/Roboto-Regular.ttf");
//            FontCache.getInstance().addFont("Light", "fonts/Roboto-Light.ttf");
//            FontCache.getInstance().addFont("Italic", "fonts/Roboto-Italic.ttf");
//            FontCache.getInstance().addFont("CondenseBold", "fonts/RobotoCondensed-Bold.ttf");
//            FontCache.getInstance().addFont("Niconne", "fonts/Niconne-Regular.ttf");


        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }


    }

    public static synchronized MyApplication getInstance() {
        if (myApplication == null) {
            return new MyApplication();
        } else
            return myApplication;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    //  METHOD: TO SHOW FAILURE MESSAGES ,Used IN RETROFIT SERVICE CALL onFailure() Method
    public static void showOnFailureMessages(Context context, Throwable t) {
        if (context != null) {
            if (t instanceof UnknownHostException)
                Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show();
            else if (t instanceof SocketTimeoutException)
                Toast.makeText(context, "Internet is slow! Try again", Toast.LENGTH_SHORT).show();
            else if (t instanceof ConnectException)
                Toast.makeText(context, "Failed to connect to Server!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    //  METHOD: TO HIDE SOFT INPUT KEYBOARD
    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }

    //  METHOD: TO SHOW SOFT INPUT KEYBOARD
    public static void openSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.toggleSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }


//    public static void replaceFrag(AppCompatActivity compatActivity,Fragment frag, String nameTag, Bundle bundle) {
//
//        if (compatActivity != null) {
//            if (bundle != null)
//                frag.setArguments(bundle);
//            else
//                frag.setArguments(null);
//
//            compatActivity.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.rl_containerHome, frag, nameTag)
//                    .addToBackStack(nameTag)
//                    .commit();
//        }
//    }

    @NonNull
    public static String removeAllSpacesInBwFromMob(String str, String TAG)
    {
        str = str.replace(" ", "");

        String builder = str.trim();

        Log.w(TAG, "removeAllSpacesInBwFrom Mob Number:->" + builder + "<-\n");

        return builder.trim();
    }


    public static void makeASnack(View view, String message)
    {
        if (view != null)
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
