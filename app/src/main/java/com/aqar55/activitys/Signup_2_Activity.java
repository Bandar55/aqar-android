package com.aqar55.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.mynameismidori.currencypicker.CurrencyPicker;

import java.util.ArrayList;
import java.util.List;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.databinding.ActivitySignup2Binding;
import com.aqar55.dialogs.Signup_Dialog;
import com.aqar55.dialogs.SimpleCustomDialog;
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

public class Signup_2_Activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Signup_2_Activity";
    private ActivitySignup2Binding activitySignup2Binding;
    private SimpleCustomDialog cdd;
    private List<String> data;
    private boolean termscondition = false;
    private Bundle b;
    private String country, currency, measurement, applanguage, sleaklanguage;
    private CountryCodePicker ccp;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            String key = intent.getStringExtra("key");
            switch (key) {
                case "measurment":
                    activitySignup2Binding.editText3.setText(message);
                    break;
                case "language":
                    activitySignup2Binding.editText4.setText(message);
                    break;
                case "speaklanguage":
                    activitySignup2Binding.editText5.setText(message);
                    break;

                case "Currency":
                    activitySignup2Binding.editText2.setText(message);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignup2Binding = DataBindingUtil.setContentView(Signup_2_Activity.this, R.layout.activity_signup_2_);
        if (!new InternetCheck(getApplicationContext()).isConnect()) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Make sure you are connected to Internet.");
        } else {
            Intent intent = getIntent();
            b = intent.getExtras();

            activitySignup2Binding.submitButton.setOnClickListener(this);
            activitySignup2Binding.ivBack.setOnClickListener(this);
            activitySignup2Binding.editText2.setOnClickListener(this);
            activitySignup2Binding.editText3.setOnClickListener(this);
            activitySignup2Binding.editText4.setOnClickListener(this);
            activitySignup2Binding.editText5.setOnClickListener(this);
            activitySignup2Binding.checkBox5.setOnCheckedChangeListener(this);

            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter("custom-event-name"));
        }
    }

    private void getCountrySpinnerData() {

        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener((name, code, symbol, flagDrawableResID) -> {
            picker.dismiss();
            activitySignup2Binding.editText2.setText(code);
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                if (new InternetCheck(this).isConnect()) {
                    if (validation()) {
                        getSignupSelectedData();
                    }
                } else {
                    MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Please Connect To Internet");
                }

                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.editText2:
                activitySignup2Binding.editText2.setError(null);

                data=new ArrayList<>();
                data.add("USD");
                data.add("AED");
                data.add("SAR");
                data.add("BOND");
                cdd = new SimpleCustomDialog(Signup_2_Activity.this, data, "Select Currency", "Currency");
                cdd.show();


               // getCountrySpinnerData();
                break;
            case R.id.editText3:
                activitySignup2Binding.editText3.setError(null);
                data = new ArrayList<>();
                data.add("Meter");
                data.add("Feet");
                data.add("Inch");
                data.add("Centimeter");
                data.add("Yard");
                cdd = new SimpleCustomDialog(Signup_2_Activity.this, data, "Select Measurement", "measurment");
                cdd.show();
                break;
            case R.id.editText4:
                activitySignup2Binding.editText4.setError(null);
                data = new ArrayList<>();
                data.add("English");
                data.add("Arab");
                cdd = new SimpleCustomDialog(Signup_2_Activity.this, data, "Select App Language", "language");
                cdd.show();
                break;
            case R.id.editText5:
                activitySignup2Binding.editText5.setError(null);
                data = new ArrayList<>();
                data.add("English");
                data.add("Arab");
                cdd = new SimpleCustomDialog(Signup_2_Activity.this, data, "Select Speak Language", "speaklanguage");
                cdd.show();
                break;
        }
    }

    private boolean validation() {
        if (activitySignup2Binding.editText2.getText().toString().equalsIgnoreCase("Select Currency")) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Please Select Currency");
            activitySignup2Binding.editText2.requestFocus();
            activitySignup2Binding.editText2.setError("");
            return false;
        } else if (activitySignup2Binding.editText3.getText().toString().equalsIgnoreCase("Select Measurement")) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Please Select Measurement");
            activitySignup2Binding.editText3.requestFocus();
            activitySignup2Binding.editText3.setError("");
            return false;
        } else if (activitySignup2Binding.editText4.getText().toString().equalsIgnoreCase("Select App Language")) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Please Select App Language");
            activitySignup2Binding.editText4.requestFocus();
            activitySignup2Binding.editText4.setError("");
            return false;
        } else if (activitySignup2Binding.editText5.getText().toString().equalsIgnoreCase("Select Speak Language")) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Please Select Speak Language");
            activitySignup2Binding.editText5.requestFocus();
            activitySignup2Binding.editText5.setError("");
            return false;
        } else if (!termscondition) {
            MyApplication.makeASnack(activitySignup2Binding.getRoot(), "Check Terms and Conditions");
            return false;
        }
        return true;
    }

    private void getSignupSelectedData() {
        country = activitySignup2Binding.editText.getSelectedCountryName();
        currency = activitySignup2Binding.editText2.getText().toString();
        measurement = activitySignup2Binding.editText3.getText().toString();
        applanguage = activitySignup2Binding.editText4.getText().toString();
        sleaklanguage = activitySignup2Binding.editText5.getText().toString();

        getTermsAndConditionData();
    }

    private void getTermsAndConditionData() {

        MyDialog.getInstance(this).showDialog(Signup_2_Activity.this);

        Log.d(TAG, "getTermsAndConditionData: " + b.getString("number") + b.getString("code"));

        Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
        Call<Sign_Up> call = apiInterface.getSignUpData(
                "android",
                BaseManager.getDataFromPreferences("kDeviceToken",String.class)
,
                "",
                b.getString("number"),
                b.getString("code"),
                b.getString("name"),
                b.getString("category"),
                b.getString("subcategory"),
                b.getString("gender"),
                b.getString("byear"),
                b.getString("email"),
                country,
                currency,
                measurement,
                applanguage,
                sleaklanguage,
                termscondition);

        call.enqueue(new Callback<Sign_Up>() {
            @Override
            public void onResponse(@NonNull Call<Sign_Up> call, @NonNull Response<Sign_Up> response) {
                MyDialog.getInstance(Signup_2_Activity.this).hideDialog();

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseMessage().equalsIgnoreCase("All fields are required")) {
                        Toast.makeText(Signup_2_Activity.this, "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResponseMessage().equalsIgnoreCase("Mobile Number exists")) {
                        Toast.makeText(Signup_2_Activity.this, "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResponseMessage().equalsIgnoreCase("signedup successfully ")) {
                        CommonClass.savePreferencesBoolean(Signup_2_Activity.this,"islogin",true);
                        CommonClass.savePreferencesBoolean(Signup_2_Activity.this,"popup",false);
                        CommonClass.savePreferenceString(Signup_2_Activity.this, "userid", response.body().getData().getId());
                        CommonClass.savePreferenceString(Signup_2_Activity.this, "professionalid", response.body().getData().getProfessionalid());
                        CommonClass.savePreferenceString(Signup_2_Activity.this, "businessid", response.body().getData().getBusinessid());
                        showDialog(response.body().getData().getFullname());
                        Sign_Up sign_up=new Sign_Up(response.body().getData());
                        sign_up.setData(response.body().getData());
                        ModelManager.modelManager().setCurrentUser(response.body().getData());
                        BaseManager.saveDataIntoPreferences(response.body().getData().getCurrency(),"setting_currency");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Sign_Up> call, @NonNull Throwable t) {
                MyDialog.getInstance(Signup_2_Activity.this).hideDialog();

            }
        });

    }

    private void showDialog(String name) {
        Signup_Dialog signup_dialog = new Signup_Dialog(Signup_2_Activity.this, name);
        signup_dialog.show();
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        termscondition = b;
    }
}
