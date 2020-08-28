package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import com.aqar55.R;
import com.aqar55.databinding.LayoutGetDefaultDateBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;

public class SetDefault_Time_And_Date extends Fragment implements View.OnClickListener {
    LayoutGetDefaultDateBinding layoutGetDefaultDateBinding;
    View view;
    Bundle bundle = new Bundle();
    private int checkedRadioButtonId;
    ManageActiveProperty.Data editData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutGetDefaultDateBinding = DataBindingUtil.inflate(inflater, R.layout.layout_get_default_date, container, false);

        view = layoutGetDefaultDateBinding.getRoot();
        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(layoutGetDefaultDateBinding.getRoot(), "Make sure you are connected to Internet.");
        }
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvSaveSetPrice).setOnClickListener(this);
        bundle = getArguments();

        if(bundle.getString("dailyprice").isEmpty()){
            layoutGetDefaultDateBinding.textView75.setText("N/A");
            layoutGetDefaultDateBinding.rbDaily.setEnabled(false);
            layoutGetDefaultDateBinding.rbDaily.setFocusable(false);


        }else {
            layoutGetDefaultDateBinding.textView75.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(bundle.getString("dailyprice")))+ " "+ BaseManager.getDataFromPreferences("setting_currency",String.class));

        }
        if(bundle.getString("weeklyprice").isEmpty()){
            layoutGetDefaultDateBinding.textView77.setText("N/A");
            layoutGetDefaultDateBinding.rbWeekly.setEnabled(false);
            layoutGetDefaultDateBinding.rbWeekly.setFocusable(false);


        }else {
            layoutGetDefaultDateBinding.textView77.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(bundle.getString("weeklyprice")))+ " "+BaseManager.getDataFromPreferences("setting_currency",String.class));

        }

        if(bundle.getString("monthlyprice").isEmpty()){
            layoutGetDefaultDateBinding.textView78.setText("N/A");
            layoutGetDefaultDateBinding.rbMonthly.setEnabled(false);
            layoutGetDefaultDateBinding.rbMonthly.setFocusable(false);
        }else {
            layoutGetDefaultDateBinding.textView78.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(bundle.getString("monthlyprice")))+ " "+BaseManager.getDataFromPreferences("setting_currency",String.class));

        }

        if(bundle.getString("yearlyprice").isEmpty()){
            layoutGetDefaultDateBinding.textView79.setText("N/A");
            layoutGetDefaultDateBinding.rbYearly.setEnabled(false);
            layoutGetDefaultDateBinding.rbYearly.setFocusable(false);
        }else {
            layoutGetDefaultDateBinding.textView79.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(bundle.getString("yearlyprice")))+ " "+BaseManager.getDataFromPreferences("setting_currency",String.class));

        }



        //setEnableCheckbtn();
        getIntentData();
        return view;
    }



    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
        if (editData != null) {
            String rentTime  = editData.getRenttime();
            switch (rentTime){
                case "Daily":
                    layoutGetDefaultDateBinding.rbDaily.setChecked(true);
                    break;
                case "Weekly":
                    layoutGetDefaultDateBinding.rbWeekly.setChecked(true);
                    break;
                case "Monthly":
                    layoutGetDefaultDateBinding.rbMonthly.setChecked(true);
                    break;
                case "Yearly":
                    layoutGetDefaultDateBinding.rbYearly.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.tvSaveSetPrice:
                checkedRadioButtonId = layoutGetDefaultDateBinding.radioGroup.getCheckedRadioButtonId();
                if (validation()) {
                    collectData();
                    Add_Image_Post_Property_Fragment addImageRent = new Add_Image_Post_Property_Fragment();
                    addImageRent.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, addImageRent).addToBackStack("").commit();
                }
                break;
        }
    }

    private void collectData() {


        RadioButton radioButton = layoutGetDefaultDateBinding.radioGroup.findViewById(checkedRadioButtonId);
        if (radioButton.getText().toString().equalsIgnoreCase("Daily")) {
            String totalPrice = layoutGetDefaultDateBinding.textView75.getText().toString();
            String[] array = totalPrice.split(" ");
            bundle.putString("totalprice", array[0]);
            bundle.putString("renttime", "daily");
        } else if (radioButton.getText().toString().equalsIgnoreCase("Weekly")) {
            String totalPrice = layoutGetDefaultDateBinding.textView77.getText().toString();
            String[] array = totalPrice.split(" ");
            bundle.putString("totalprice", array[0]);
            bundle.putString("renttime", "weekly");
        } else if (radioButton.getText().toString().equalsIgnoreCase("Monthly")) {
            String totalPrice = layoutGetDefaultDateBinding.textView78.getText().toString();
            String[] array = totalPrice.split(" ");
            bundle.putString("totalprice", array[0]);
            bundle.putString("renttime", "monthly");
        } else if (radioButton.getText().toString().equalsIgnoreCase("Yearly")) {
            String totalPrice = layoutGetDefaultDateBinding.textView79.getText().toString();
            String[] array = totalPrice.split(" ");
            bundle.putString("totalprice",  array[0]);
            bundle.putString("renttime", "yearly");
        }
    }

    private boolean validation() {
        if (checkedRadioButtonId == -1) {
            Toast.makeText(getContext(), "Plase Select preference", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
