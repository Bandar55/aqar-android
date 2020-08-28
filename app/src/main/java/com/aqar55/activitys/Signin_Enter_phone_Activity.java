package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.aqar55.R;
import com.aqar55.databinding.ActivitySinginEnterPhoneBinding;
import com.aqar55.utill.InternetCheck;

public class Signin_Enter_phone_Activity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySinginEnterPhoneBinding activitySinginEnterPhoneBinding;
    private InternetCheck internetCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySinginEnterPhoneBinding = DataBindingUtil.setContentView(Signin_Enter_phone_Activity.this, R.layout.activity_singin_enter_phone);
        activitySinginEnterPhoneBinding.textSubmitSigninPhoneActivity.setOnClickListener(this);
        activitySinginEnterPhoneBinding.backButtomSignin.setOnClickListener(this);
        internetCheck = new InternetCheck(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textSubmitSigninPhoneActivity:
                if (internetCheck.isConnect()) {
                    if (validation()) {
                        Intent intent = new Intent(this, Signin_Enter_Otp_Activity.class);
                        intent.putExtra("phone", activitySinginEnterPhoneBinding.edtphone.getText().toString());
                        intent.putExtra("code", activitySinginEnterPhoneBinding.textView82.getSelectedCountryCode());
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backButtomSignin:
                finish();
                break;
        }
    }

    private boolean validation() {
        if (activitySinginEnterPhoneBinding.edtphone != null && activitySinginEnterPhoneBinding.edtphone.getText().toString().isEmpty()) {
            activitySinginEnterPhoneBinding.edtphone.requestFocus();
            activitySinginEnterPhoneBinding.edtphone.setError("Please enter Phone Number ");
            return false;
        }
        return true;
    }
}
