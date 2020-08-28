package com.aqar55.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Firebase_Utills {

    private static FirebaseAuth mAuth;
    private static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static String verificationCode;
    private Context context;
    private static final String TAG = "Firebase_Utills";


    public static void GenerateOtp(String userNumber, Activity context) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + userNumber,                  // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                context,        // Activity (for callback binding)
                mCallbacks);
    }

    public static String StartFirebaseLogin(final Activity activity) {
        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(activity, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(activity, "verification fialed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(activity, "OTP Sent", Toast.LENGTH_SHORT).show();
                verificationCode = s;
            }
        };
        Log.d(TAG, "StartFirebaseLogin: "+verificationCode);
        return verificationCode;
    }


}
