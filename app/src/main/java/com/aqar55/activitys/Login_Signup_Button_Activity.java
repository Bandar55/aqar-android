package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.aqar55.R;
import com.aqar55.databinding.ActivityLoginSignupButtonBinding;
import com.aqar55.model.StaticContentByType;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Signup_Button_Activity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginSignupButtonBinding activityLoginSignupButtonBinding;

    public static Intent getIntent(Context context){
        return new Intent(context,Login_Signup_Button_Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginSignupButtonBinding = DataBindingUtil.setContentView(Login_Signup_Button_Activity.this, R.layout.activity_login__signup__button);
        activityLoginSignupButtonBinding.textSignupLoginSignupButtonActivity.setOnClickListener(this);
        activityLoginSignupButtonBinding.textSigninSignupLoginSignupButtonActivity.setOnClickListener(this);
        activityLoginSignupButtonBinding.skip.setOnClickListener(this);

        InternetCheck internetCheck = new InternetCheck(this);


        if (internetCheck.isConnect()) {

            MyDialog.getInstance(this).showDialog();
            Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<StaticContentByType> call = apiInterface.getStaticData("Home2");
            call.enqueue(new Callback<StaticContentByType>() {
                @Override
                public void onResponse(@NonNull Call<StaticContentByType> call, @NonNull Response<StaticContentByType> response) {
                    MyDialog.getInstance(Login_Signup_Button_Activity.this).hideDialog();
                    if (response.isSuccessful()) {
                        StaticContentByType staticContentByType = response.body();
                        //activityLoginSignupButtonBinding.text.setText(staticContentByType.getData().getDescription());
                    }
                }

                @Override
                public void onFailure(Call<StaticContentByType> call, Throwable t) {
                    MyDialog.getInstance(Login_Signup_Button_Activity.this).hideDialog();

                }
            });

        } else {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_signup_login_signup_button_activity:
                startActivity(new Intent(Login_Signup_Button_Activity.this, Signup_Enter_Phone_Activity.class));
                break;
            case R.id.text_signin_signup_login_signup_button_activity:
                startActivity(new Intent(Login_Signup_Button_Activity.this, Signin_Enter_phone_Activity.class));
                break;
            case R.id.skip:
                startActivity(new Intent(Login_Signup_Button_Activity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
