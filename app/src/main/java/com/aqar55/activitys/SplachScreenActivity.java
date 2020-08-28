package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.databinding.ActivitySplashScreenBinding;
import com.aqar55.helper.BaseManager;

import static com.aqar55.constant.Constants.kAppPreferences;
import static com.aqar55.constant.Constants.kIsFirstTime;
import static com.aqar55.constant.Constants.kRememberMe;

public class SplachScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding splashScreenBinding;
    private boolean isFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = DataBindingUtil.setContentView(SplachScreenActivity.this, R.layout.activity_splash_screen);


        SharedPreferences sharedPreferences = getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        isFirstTime = sharedPreferences.getBoolean(kIsFirstTime, true);
        if (isFirstTime) {
            addShortcutIcon(sharedPreferences);
        }
      


        new Handler().postDelayed(() -> loadToHome(), 3000);
    }

    private void loadToHome() {

        if (!CommonClass.getPreferencesBoolean(SplachScreenActivity.this, "islogin")) {


            if(isFirstTime){
                SharedPreferences sharedPreferences = getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(kIsFirstTime, false);
                editor.commit();
                startActivity(new Intent(SplachScreenActivity.this, IntroActivity.class));
                finish();
            }else {
                startActivity(new Intent(SplachScreenActivity.this, Login_Signup_Button_Activity.class));
                finish();
            }


        } else {
            startActivity(new Intent(SplachScreenActivity.this, MainActivity.class));
            finish();
        }
    }


    private void addShortcutIcon(SharedPreferences sharedPreferences) {
        Intent shortcutIntent = new Intent(getApplicationContext(),
                SplachScreenActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Aqar55");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.mipmap.app_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        //finally broadcast the new Intent
        getApplicationContext().sendBroadcast(addIntent);
     /*   SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(kIsFirstTime, false);
        editor.commit();
*/
    }



    @Override
    protected void onResume() {
        super.onResume();
        getDeviceToken();
    }

    public static String getDeviceToken() {

            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            //Log.d("Firbase id login", "Refreshed token: " + refreshedToken);
            BaseManager.saveDataIntoPreferences(refreshedToken,"kDeviceToken");
            String token = BaseManager.getDataFromPreferences("kDeviceToken",String.class);
            Log.d("deviceToken",String.valueOf(token));
            return (token!=null)?token:"dummyToken";




    }
}
