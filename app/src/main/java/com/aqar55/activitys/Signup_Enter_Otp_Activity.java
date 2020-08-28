package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import com.aqar55.R;
import com.aqar55.databinding.ActivitySignupEnterOtpBinding;

public class Signup_Enter_Otp_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Signup_Enter_Otp_Activi";
    private ActivitySignupEnterOtpBinding activitySignupEnterOtpBinding;
    private String phone, code="+91";
    private String verificationCode;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupEnterOtpBinding = DataBindingUtil.setContentView(Signup_Enter_Otp_Activity.this, R.layout.activity_signup__enter__otp_);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");

        activitySignupEnterOtpBinding.textView83.setText("Sent to " + code + " " + phone);

        StartFirebaseLogin();
        GenerateOtp(phone);

        activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.length() == 1) {
                    activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.clearFocus();
                    activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.requestFocus();
                    activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (sb.length() == 0) {
//                    activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.requestFocus();
//                }
            }
        });
        activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.length() == 1) {

                    activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.clearFocus();
                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.requestFocus();
                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.setCursorVisible(true);
                } else {
                    activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (sb.length() == 0) {
//                    activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.requestFocus();
//                }
            }
        });
        activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//
//                    sb.deleteCharAt(0);
//
//                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.length() == 1) {
                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.clearFocus();
                    activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.requestFocus();
                    activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.setCursorVisible(true);
                } else {
                    activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (sb.length() == 0) {
//                    activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.requestFocus();
//                }
            }
        });
        activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//
//                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.length() == 1) {

                    activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.clearFocus();
                    activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.requestFocus();
                    activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.setCursorVisible(true);
                } else {
                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (sb.length() == 0) {
//                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.requestFocus();
//                }
            }
        });
        activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (sb.length() == 1) {
//                    sb.deleteCharAt(0);
//                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.length() == 1) {
                    activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.clearFocus();
                    activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.requestFocus();
                    activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.setCursorVisible(true);
                } else {
                    activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (sb.length() == 0) {
//                    activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.requestFocus();
//                }
            }
        });
        activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = null;
                if (activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.length() == 1) {

                    //startActivity(new Intent(Signin_Enter_Otp_Activity.this, MainActivity.class));
                    s = activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.getText().toString() + activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.getText().toString() +
                            activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.getText().toString() + activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.getText().toString()
                            + activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.getText().toString() + activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, s);
                    SigninWithPhone(credential);

                } else {
                    activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activitySignupEnterOtpBinding.ivBack.setOnClickListener(this);
        activitySignupEnterOtpBinding.textResendSignupEnterOtp.setOnClickListener(this);
        activitySignupEnterOtpBinding.textView83.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.text_resend_signup_enter_otp:
                StartFirebaseLogin();
                GenerateOtp(phone);
                activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp2SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp3SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp4SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp5SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp6SignupEnterOtp.setText(null);
                activitySignupEnterOtpBinding.editTextOtp1SignupEnterOtp.requestFocus();
                break;
            case R.id.textView83:
                finish();
                break;
        }
    }

    private void SigninWithPhone(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Signup_Enter_Otp_Activity.this, Signup_1_Activity.class);
                            intent.putExtra("code", code);
                            intent.putExtra("number", phone);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup_Enter_Otp_Activity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void GenerateOtp(String userNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 code + userNumber,                  // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                Signup_Enter_Otp_Activity.this,        // Activity (for callback binding)
                mCallbacks);
    }

    private void StartFirebaseLogin() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(Signup_Enter_Otp_Activity.this, "verification completed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup_Enter_Otp_Activity.this, Signup_1_Activity.class);
                intent.putExtra("code", code);
                intent.putExtra("number", phone);
                startActivity(intent);
                finish();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Signup_Enter_Otp_Activity.this, "verification failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(Signup_Enter_Otp_Activity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                verificationCode = s;
                reverseTimer(60,activitySignupEnterOtpBinding.textTimeSignupEnterOtp);
            }
        };
    }


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
