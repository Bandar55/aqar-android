package com.aqar55.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.activitys.Login_Signup_Button_Activity;
import com.aqar55.databinding.FragmentSettingsBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.SignOut;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings_Main_Fragment extends Fragment implements View.OnClickListener {
    View view;
    FragmentSettingsBinding fragmentSettingsBinding;
    private boolean isAppLang, isSpeakLang, isCurrency, isMeasurement;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        view = fragmentSettingsBinding.getRoot();
        fragmentSettingsBinding.tvAboutUS.setOnClickListener(this);
        fragmentSettingsBinding.tvTermsAndCondition.setOnClickListener(this);
        fragmentSettingsBinding.ivBack.setOnClickListener(this);
        fragmentSettingsBinding.tvLogout.setOnClickListener(this);
        fragmentSettingsBinding.tvAppLanguage.setOnClickListener(this);
        fragmentSettingsBinding.tvSpeakLan.setOnClickListener(this);
        fragmentSettingsBinding.tvCurrency.setOnClickListener(this);
        fragmentSettingsBinding.tvMeasurement.setOnClickListener(this);
        fragmentSettingsBinding.ivNotificationToggle.setOnClickListener(this);
        fragmentSettingsBinding.tvCurrency.setOnClickListener(this);

        setAppLangSpinner();
        setSpeakLanguage();
        setMeasurement();
        setCurrencySpinner();
        hitAppSettings();

        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAboutUS:
                About_Us_Fragment about_us_fragment = new About_Us_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.settings_container, about_us_fragment).addToBackStack("").commit();
                break;
            case R.id.tvTermsAndCondition:
                Terms_And_Condition_Fragment terms_and_condition_fragment = new Terms_And_Condition_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.settings_container, terms_and_condition_fragment).addToBackStack("").commit();
                break;
            case R.id.tvLogout:
                logout();
                break;
            case R.id.ivBack:
                getActivity().finish();
                break;
            case R.id.tvAppLanguage:
                isAppLang = true;
                fragmentSettingsBinding.spnAppLang.performClick();
                break;
            case R.id.tvSpeakLan:
                isSpeakLang = true;
                fragmentSettingsBinding.spnSpeakLang.performClick();
                break;
            case R.id.tvCurrency:
                isCurrency = true;
                fragmentSettingsBinding.spnCurrency.performClick();
                break;
            case R.id.tvMeasurement:
                isMeasurement = true;
                fragmentSettingsBinding.spnMeasurement.performClick();
                break;
            case R.id.ivNotificationToggle:
                hitNotificationApi();
                break;

        }
    }

    private void setAppLangSpinner() {
        String[] appLanguages = DataGenerator.getLaguage();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, appLanguages) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        fragmentSettingsBinding.spnAppLang.setAdapter(adapter);
        fragmentSettingsBinding.spnAppLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(position==0)
                    return;
                if (isAppLang)
                    updateSettings();
                fragmentSettingsBinding.tvAppLanguage.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpeakLanguage() {
        String[] array = DataGenerator.getLaguage();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, array) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        fragmentSettingsBinding.spnSpeakLang.setAdapter(adapter);
        fragmentSettingsBinding.spnSpeakLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(position==0)
                    return;

                if (isSpeakLang)
                    fragmentSettingsBinding.tvSpeakLan.setText(item);
                updateSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCurrencyPicker() {
        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                picker.dismiss();
                fragmentSettingsBinding.tvCurrency.setText(code);
            }
        });
        picker.show(getFragmentManager(), "CURRENCY_PICKER");
    }

    private void setMeasurement() {
        String[] measurements = DataGenerator.getMeasurement();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, measurements) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        fragmentSettingsBinding.spnMeasurement.setAdapter(adapter);
        fragmentSettingsBinding.spnMeasurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String measurement = parent.getItemAtPosition(position).toString();
                if(position==0)
                    return;

                if (isMeasurement)
                    fragmentSettingsBinding.tvMeasurement.setText(measurement);
                updateSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCurrencySpinner() {
        String[] currency = DataGenerator.getCurrency();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, currency) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        fragmentSettingsBinding.spnCurrency.setAdapter(adapter);
        fragmentSettingsBinding.spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String measurement = parent.getItemAtPosition(position).toString();
                if(position==0)
                    return;

                if (isCurrency){
                    fragmentSettingsBinding.tvCurrency.setText(measurement);
                    updateSettings();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // hit appSetting API
    private void hitAppSettings() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetUserDetails> call = api.getUserDetails(ModelManager.modelManager().getCurrentUser().getId());
        call.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        setDataToViews(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {

            }
        });

    }

    // set all data from response
    private void setDataToViews(Response<GetUserDetails> response) {
        fragmentSettingsBinding.tvAppLanguage.setText(response.body().getData().getApplanguage());
        fragmentSettingsBinding.tvSpeakLan.setText(response.body().getData().getSpeaklanguage());
        fragmentSettingsBinding.tvCurrency.setText(response.body().getData().getCurrency());
        fragmentSettingsBinding.tvMeasurement.setText(response.body().getData().getMeasurement());
        fragmentSettingsBinding.ivNotificationToggle.setChecked(response.body().getData().isNotification());
    }

    // hit notificationSetting API
    private void hitNotificationApi() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetUserDetails> call = api.getNotificationSetting(ModelManager.modelManager().getCurrentUser().getId(), fragmentSettingsBinding.ivNotificationToggle.isChecked());
        call.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {

            }
        });
    }

    private void logout() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<SignOut> outCall = api.getSignoutData(CommonClass.getPreferencesString(getContext(), "userid"));
        outCall.enqueue(new Callback<SignOut>() {
            @Override
            public void onResponse(Call<SignOut> call, Response<SignOut> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseMessage().equalsIgnoreCase("signedout successfully")) {
                        CommonClass.removeAllPreference(getContext());
                        Intent intent = new Intent(getContext(), Login_Signup_Button_Activity.class);
                        // Intent.FLAG_ACTIVITY_NEW_TASK;
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(intent);
                        ModelManager.modelManager().setCurrentUser(null);

                    }
                }
            }

            @Override
            public void onFailure(Call<SignOut> call, Throwable t) {

            }
        });

    }


    // hit notificationSetting API
    private void updateSettings() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetUserDetails> call = api.upDateSettings(fragmentSettingsBinding.tvCurrency.getText().toString(), ModelManager.modelManager().getCurrentUser().getId(), fragmentSettingsBinding.tvMeasurement.getText().toString(), fragmentSettingsBinding.tvAppLanguage.getText().toString(), fragmentSettingsBinding.tvSpeakLan.getText().toString());
        call.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        Toaster.toast(response.body().getResponseMessage());
                        BaseManager.saveDataIntoPreferences(response.body().getData().getCurrency(),"setting_currency");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {

            }
        });
    }


}
