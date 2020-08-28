package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aqar55.R;
import com.aqar55.databinding.ActivitySignupEnterPhoneBinding;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;

public class Signup_Enter_Phone_Activity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignupEnterPhoneBinding signupEnterPhoneBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupEnterPhoneBinding = DataBindingUtil.setContentView(Signup_Enter_Phone_Activity.this, R.layout.activity_signup__enter__phone_);
        if (!new InternetCheck(getApplicationContext()).isConnect()) {
            MyApplication.makeASnack(signupEnterPhoneBinding.getRoot(), "Make sure you are connected to Internet.");
        }
        MyApplication.hideSoftKeyBoard(Signup_Enter_Phone_Activity.this);
        signupEnterPhoneBinding.textsignupsubmitbutton.setOnClickListener(this);
        signupEnterPhoneBinding.backSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textsignupsubmitbutton:
                if (validation()) {
                    Intent intent = new Intent(Signup_Enter_Phone_Activity.this, Signup_Enter_Otp_Activity.class);
                    intent.putExtra("phone", signupEnterPhoneBinding.signupPhone.getText().toString());
                    intent.putExtra("code", signupEnterPhoneBinding.textView82.getSelectedCountryCodeWithPlus());
                    startActivity(intent);
                }
                break;
            case R.id.back_signup:
                finish();
                break;
        }
    }

    private boolean validation() {
        if (signupEnterPhoneBinding.signupPhone != null && signupEnterPhoneBinding.signupPhone.getText().toString().isEmpty()) {
            signupEnterPhoneBinding.signupPhone.requestFocus();
            signupEnterPhoneBinding.signupPhone.setError("Please enter Phone Number ");
            return false;
        }
        return true;
    }
}
