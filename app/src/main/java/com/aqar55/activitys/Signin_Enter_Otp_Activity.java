package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.databinding.ActivitySigninEnterOtpBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.Sign_Up;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin_Enter_Otp_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Signin_Enter_Otp_Activi";

    private ActivitySigninEnterOtpBinding activitySigninEnterOtpBinding;
    private String phone, code;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySigninEnterOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_signin__enter__otp_);
        if (!new InternetCheck(getApplicationContext()).isConnect()) {
            MyApplication.makeASnack(activitySigninEnterOtpBinding.getRoot(), "Make sure you are connected to Internet.");
        } else {

            mAuth = FirebaseAuth.getInstance();

            Intent i = getIntent();
            phone = i.getStringExtra("phone");
            code = i.getStringExtra("code");

            StartFirebaseLogin();
            GenerateOtp(phone);

            activitySigninEnterOtpBinding.textView83.setText("Sent to +" + code + " " + phone);

            activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.length() == 1) {
                        activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.clearFocus();
                        activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.requestFocus();
                        activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.setCursorVisible(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.length() == 1) {
                        activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.clearFocus();
                        activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.requestFocus();
                        activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.setCursorVisible(true);
                    } else {
                        activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 2) {
//                    sb.deleteCharAt(1);
//                }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.length() == 1) {
                        activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.clearFocus();
                        activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.requestFocus();
                        activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.setCursorVisible(true);
                    } else {
                        activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.length() == 1) {
                        activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.clearFocus();
                        activitySigninEnterOtpBinding.edtotp5.requestFocus();
                        activitySigninEnterOtpBinding.edtotp5.setCursorVisible(true);
                    } else {
                        activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            activitySigninEnterOtpBinding.edtotp5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (activitySigninEnterOtpBinding.edtotp5.length() == 1) {
                        activitySigninEnterOtpBinding.edtotp5.clearFocus();
                        activitySigninEnterOtpBinding.editTextotp6.requestFocus();
                        activitySigninEnterOtpBinding.editTextotp6.setCursorVisible(true);
                    } else {
                        activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            activitySigninEnterOtpBinding.editTextotp6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String s = null;
                    if (activitySigninEnterOtpBinding.editTextotp6.length() == 1) {

                        //startActivity(new Intent(Signin_Enter_Otp_Activity.this, MainActivity.class));
                        s = activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.getText().toString() + activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.getText().toString() +
                                activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.getText().toString() + activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.getText().toString()
                                + activitySigninEnterOtpBinding.edtotp5.getText().toString() + activitySigninEnterOtpBinding.editTextotp6.getText().toString();

                        try {
                            PhoneAuthCredential
                                    credential = PhoneAuthProvider.getCredential(verificationCode, s);
                            SigninWithPhone(credential);
                        }catch (Exception e){
                            Toast toast = Toast.makeText(Signin_Enter_Otp_Activity.this, "Verification Code is wrong", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }

                    } else {
                        activitySigninEnterOtpBinding.edtotp5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            activitySigninEnterOtpBinding.ivBack.setOnClickListener(this);
            activitySigninEnterOtpBinding.textResendSignupEnterOtp.setOnClickListener(this);
            activitySigninEnterOtpBinding.textView83.setOnClickListener(this);
        }
    }

    private void SigninWithPhone(final PhoneAuthCredential credential) {
        MyDialog.getInstance(this).showDialog();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        MyDialog.getInstance(Signin_Enter_Otp_Activity.this).hideDialog();
                        getLoginData();
                    } else {
                        Toast.makeText(Signin_Enter_Otp_Activity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getLoginData() {
        MyDialog.getInstance(Signin_Enter_Otp_Activity.this).showDialog(this);
        Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
        Call<Sign_Up> loginCall = apiInterface.getLoginData("+"+code, phone, "android", BaseManager.getDataFromPreferences("kDeviceToken",String.class));
        loginCall.enqueue(new Callback<Sign_Up>() {
            @Override
            public void onResponse(@NonNull Call<Sign_Up> call, @NonNull Response<Sign_Up> response) {
                MyDialog.getInstance(Signin_Enter_Otp_Activity.this).hideDialog();

                if (response.isSuccessful()) {
                    if (response.body().getResponseCode()==200) {
                        Toast.makeText(Signin_Enter_Otp_Activity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + response.body().getData().getId());
                        CommonClass.savePreferencesBoolean(Signin_Enter_Otp_Activity.this,"islogin",true);
                        CommonClass.savePreferencesBoolean(Signin_Enter_Otp_Activity.this,"popup",false);
                        CommonClass.savePreferenceString(Signin_Enter_Otp_Activity.this, "userid", response.body().getData().getId());
                        CommonClass.savePreferenceString(Signin_Enter_Otp_Activity.this, "professionalid", response.body().getData().getProfessionalid());
                        CommonClass.savePreferenceString(Signin_Enter_Otp_Activity.this, "businessid", response.body().getData().getBusinessid());
                        Sign_Up sign_up=new Sign_Up(response.body().getData());
                        sign_up.setData(response.body().getData());
                        ModelManager.modelManager().setCurrentUser(response.body().getData());
                        BaseManager.saveDataIntoPreferences(response.body().getData().getCurrency(),"setting_currency");
                        Toast.makeText(Signin_Enter_Otp_Activity.this, "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signin_Enter_Otp_Activity.this, MainActivity.class));
                        finishAffinity();
                        finish();
                    } else if (response.body().getResponseMessage().equalsIgnoreCase("Internal server error")) {
                        Toast.makeText(Signin_Enter_Otp_Activity.this,response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                        MyApplication.makeASnack(activitySigninEnterOtpBinding.getRoot(), "You are not register with us.");
                    }else {
                        Toast.makeText(Signin_Enter_Otp_Activity.this,response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Sign_Up> call, @NonNull Throwable t) {
                MyDialog.getInstance(Signin_Enter_Otp_Activity.this).hideDialog();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.text_resend_signup_enter_otp:
                if (new InternetCheck(this).isConnect()) {
                    StartFirebaseLogin();
                    GenerateOtp(phone);
                    activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.setText(null);
                    activitySigninEnterOtpBinding.editTextOtp2SigninEnterOtp.setText(null);
                    activitySigninEnterOtpBinding.editTextOtp3SigninEnterOtp.setText(null);
                    activitySigninEnterOtpBinding.editTextOtp4SigninEnterOtp.setText(null);
                    activitySigninEnterOtpBinding.edtotp5.setText(null);
                    activitySigninEnterOtpBinding.editTextotp6.setText(null);
                    activitySigninEnterOtpBinding.editTextOtp1SigninEnterOtp.requestFocus();

                } else {
                    MyApplication.makeASnack(activitySigninEnterOtpBinding.getRoot(), "Please Connect To Internet.");
                }
                break;
            case R.id.textView83:
                finish();
                break;
        }
    }

    private void GenerateOtp(String userNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + code + userNumber,                  // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                Signin_Enter_Otp_Activity.this,        // Activity (for callback binding)
                mCallbacks);
    }

    private void StartFirebaseLogin() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(Signin_Enter_Otp_Activity.this, "verification completed", Toast.LENGTH_SHORT).show();
                getLoginData();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Signin_Enter_Otp_Activity.this, "verification failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(Signin_Enter_Otp_Activity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                verificationCode = s;

                reverseTimer(60,activitySigninEnterOtpBinding.textTimeSignupEnterOtp);
            }
        };
    }


    //    private void startTimer(){
//
//        CountDownTimer countDownTimer=new CountDownTimer(45000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int seconds = (int) (millisUntilFinished / 1000);
//                int minutes = seconds / 60;
//                seconds = seconds % 60;
//                activitySigninEnterOtpBinding.textTimeSignupEnterOtp.setText(/*"TIME : " + String.format("%02d", minutes)*/
//                         ":" + String.format("%02d", seconds));
//            }
//
//            @Override
//            public void onFinish() {
////                Toast.makeText(Signin_Enter_Otp_Activity.this, "on tme start", Toast.LENGTH_SHORT).show();
////                dispatchTestResultAct(ans_record);
//            }
//        }.start();
//
//
//    }
    public void reverseTimer(int Seconds, final TextView tv) {
        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                tv.setText("00:" + seconds);
            }

            public void onFinish() {
                tv.setText("Completed");
            }
        }.start();
    }



}
