package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.aqar55.R;
import com.aqar55.adapters.SliderAdapter;
import com.aqar55.databinding.ActivityIntroBinding;
import com.aqar55.model.StataticContent;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityIntroBinding activityIntroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntroBinding = DataBindingUtil.setContentView(IntroActivity.this, R.layout.activity_intro);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 30000, 30000);

        activityIntroBinding.imageNextIntroScreen.setOnClickListener(this);

        getApiData();

    }



    private void getApiData() {

        InternetCheck internetCheck = new InternetCheck(IntroActivity.this);

        if (internetCheck.isConnect()) {
            MyDialog.getInstance(IntroActivity.this).showDialog();
            Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<StataticContent> call = apiInterface.getStaticContent();

            call.enqueue(new Callback<StataticContent>() {
                @Override
                public void onResponse(Call<StataticContent> call, Response<StataticContent> response) {
                    MyDialog.getInstance(IntroActivity.this).hideDialog();

                    if (response.isSuccessful()) {

                        StataticContent staticContentByType = response.body();
                        activityIntroBinding.viewPager.setAdapter(new SliderAdapter(IntroActivity.this,staticContentByType.getData()));
                        activityIntroBinding.indicator.setViewPager(activityIntroBinding.viewPager);
                      //  Log.d( "onResponse: " + response.body().getData());


                    }
                }

                @Override
                public void onFailure(Call<StataticContent> call, Throwable t) {
                    MyDialog.getInstance(IntroActivity.this).hideDialog();

                }
            });
        } else {
            Toast.makeText(IntroActivity.this, "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_next_intro_screen:
                startActivity(new Intent(IntroActivity.this, Login_Signup_Button_Activity.class));
                break;
        }
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(() -> {
                if (activityIntroBinding.viewPager.getCurrentItem() < 2) {
                    activityIntroBinding.viewPager.setCurrentItem(activityIntroBinding.viewPager.getCurrentItem() + 1);
                } else {
                    activityIntroBinding.viewPager.setCurrentItem(0);
                }
            });
        }
    }

}
