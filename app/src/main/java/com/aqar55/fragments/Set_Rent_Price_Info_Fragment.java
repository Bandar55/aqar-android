package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aqar55.R;
import com.aqar55.databinding.LayoutSetPriceInfoRentBinding;
import com.aqar55.helper.Utils;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyValidator;

public class Set_Rent_Price_Info_Fragment extends Fragment implements View.OnClickListener {
    LayoutSetPriceInfoRentBinding layoutSetPriceInfoRentBinding;
    View view;
    Bundle bundle = new Bundle();
    ManageActiveProperty.Data editData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutSetPriceInfoRentBinding = DataBindingUtil.inflate(inflater, R.layout.layout_set_price_info_rent, container, false);
        view = layoutSetPriceInfoRentBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(layoutSetPriceInfoRentBinding.getRoot(), "Make sure you are connected to Internet.");
        }
        layoutSetPriceInfoRentBinding.tvSaveSetPrice.setOnClickListener(this);
        layoutSetPriceInfoRentBinding.ivBack.setOnClickListener(this);

        layoutSetPriceInfoRentBinding.checkBox.setOnClickListener(this);
        layoutSetPriceInfoRentBinding.checkBox2.setOnClickListener(this);
        layoutSetPriceInfoRentBinding.checkBox3.setOnClickListener(this);
        layoutSetPriceInfoRentBinding.checkBox4.setOnClickListener(this);
        bundle = getArguments();
        getIntentData();

        return view;
    }





    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
        if (editData != null) {
            layoutSetPriceInfoRentBinding.tvSizeM2.setText(editData.getDefaultDailyPrice());
            layoutSetPriceInfoRentBinding.tvSizeM3.setText(editData.getDefaultWeeklyPrice());
            layoutSetPriceInfoRentBinding.tvSizeM4.setText(editData.getDefaultMonthlyPrice());
            layoutSetPriceInfoRentBinding.tvSizeM5.setText(editData.getDefaultyearlyPrice());

            if(!layoutSetPriceInfoRentBinding.tvSizeM2.getText().toString().isEmpty())
                layoutSetPriceInfoRentBinding.checkBox.setChecked(true);
            if(!layoutSetPriceInfoRentBinding.tvSizeM3.getText().toString().isEmpty())
                layoutSetPriceInfoRentBinding.checkBox2.setChecked(true);
            if(!layoutSetPriceInfoRentBinding.tvSizeM4.getText().toString().isEmpty())
                layoutSetPriceInfoRentBinding.checkBox3.setChecked(true);
            if(!layoutSetPriceInfoRentBinding.tvSizeM5.getText().toString().isEmpty())
                layoutSetPriceInfoRentBinding.checkBox4.setChecked(true);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvSaveSetPrice:
                if (validation()) {
                    collectData();
                    SetDefault_Time_And_Date setDefault_time_and_date = new SetDefault_Time_And_Date();
                    setDefault_time_and_date.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, setDefault_time_and_date).addToBackStack("").commit();
                }
                break;

            case R.id.ivBack:
                getActivity().finish();
                break;

            case R.id.checkBox:
                if(layoutSetPriceInfoRentBinding.checkBox.isChecked()){
                    layoutSetPriceInfoRentBinding.tvSizeM2.setEnabled(true);

                }else {
                    layoutSetPriceInfoRentBinding.tvSizeM2.setEnabled(false);
                    layoutSetPriceInfoRentBinding.tvSizeM2.setText("");

                }
                break;

            case R.id.checkBox2:
                if(layoutSetPriceInfoRentBinding.checkBox2.isChecked()){
                    layoutSetPriceInfoRentBinding.tvSizeM3.setEnabled(true);

                }else {
                    layoutSetPriceInfoRentBinding.tvSizeM3.setEnabled(false);
                    layoutSetPriceInfoRentBinding.tvSizeM3.setText("");

                }
                break;


            case R.id.checkBox3:
                if(layoutSetPriceInfoRentBinding.checkBox3.isChecked()){
                    layoutSetPriceInfoRentBinding.tvSizeM4.setEnabled(true);

                }else {
                    layoutSetPriceInfoRentBinding.tvSizeM4.setEnabled(false);
                    layoutSetPriceInfoRentBinding.tvSizeM4.setText("");

                }
                break;

            case R.id.checkBox4:
                if(layoutSetPriceInfoRentBinding.checkBox4.isChecked()){
                    layoutSetPriceInfoRentBinding.tvSizeM5.setEnabled(true);

                }else {
                    layoutSetPriceInfoRentBinding.tvSizeM5.setEnabled(false);
                    layoutSetPriceInfoRentBinding.tvSizeM5.setText("");

                }
                break;
        }
    }

    private void collectData() {

        if (layoutSetPriceInfoRentBinding.checkBox.isChecked()) {
            bundle.putString("dailyprice", Utils.getProperText(layoutSetPriceInfoRentBinding.tvSizeM2));
        }else {
            bundle.putString("dailyprice", "");
        }
        if (layoutSetPriceInfoRentBinding.checkBox2.isChecked()) {
            bundle.putString("weeklyprice", Utils.getProperText(layoutSetPriceInfoRentBinding.tvSizeM3));
        }else {
            bundle.putString("weeklyprice", "");

        }
        if (layoutSetPriceInfoRentBinding.checkBox3.isChecked()) {
            bundle.putString("monthlyprice", Utils.getProperText(layoutSetPriceInfoRentBinding.tvSizeM4));
        }else {
            bundle.putString("monthlyprice", "");

        }
        if (layoutSetPriceInfoRentBinding.checkBox4.isChecked()) {
            bundle.putString("yearlyprice", Utils.getProperText(layoutSetPriceInfoRentBinding.tvSizeM5));
        }else {
            bundle.putString("yearlyprice","");

        }
    }

    private boolean validation() {


        if(!layoutSetPriceInfoRentBinding.checkBox.isChecked()&&!layoutSetPriceInfoRentBinding.checkBox2.isChecked()&&!layoutSetPriceInfoRentBinding.checkBox3.isChecked()&&!layoutSetPriceInfoRentBinding.checkBox4.isChecked()){
            Toast.makeText(getContext(), "Please select at least one Price Box", Toast.LENGTH_SHORT).show();
            return false;
        }else if(layoutSetPriceInfoRentBinding.checkBox.isChecked()){

            if (layoutSetPriceInfoRentBinding.tvSizeM2 != null && layoutSetPriceInfoRentBinding.tvSizeM2.getText().toString().isEmpty()) {
                layoutSetPriceInfoRentBinding.tvSizeM2.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM2.setError("Please enter Daily price");
                return false;
            } else if (layoutSetPriceInfoRentBinding.tvSizeM2 != null && !(MyValidator.isValidFullName(layoutSetPriceInfoRentBinding.tvSizeM2.getText().toString()))) {
                layoutSetPriceInfoRentBinding.tvSizeM2.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM2.setError("Please enter a valid Daily price");
                return false;
            }

        }else if(layoutSetPriceInfoRentBinding.checkBox2.isChecked()){

            if (layoutSetPriceInfoRentBinding.tvSizeM3 != null && layoutSetPriceInfoRentBinding.tvSizeM3.getText().toString().isEmpty()) {
                layoutSetPriceInfoRentBinding.tvSizeM3.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM3.setError("Please enter Weekly price");
                return false;
            } else if (layoutSetPriceInfoRentBinding.tvSizeM3 != null && !(MyValidator.isValidFullName(layoutSetPriceInfoRentBinding.tvSizeM3.getText().toString()))) {
                layoutSetPriceInfoRentBinding.tvSizeM3.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM3.setError("Please enter a valid Weekly price");
                return false;
            }


        }else if(layoutSetPriceInfoRentBinding.checkBox3.isChecked()){

            if (layoutSetPriceInfoRentBinding.tvSizeM4 != null && layoutSetPriceInfoRentBinding.tvSizeM4.getText().toString().isEmpty()) {
                layoutSetPriceInfoRentBinding.tvSizeM4.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM4.setError("Please enter Monthly price");
                return false;
            } else if (layoutSetPriceInfoRentBinding.tvSizeM4 != null && !(MyValidator.isValidFullName(layoutSetPriceInfoRentBinding.tvSizeM4.getText().toString()))) {
                layoutSetPriceInfoRentBinding.tvSizeM4.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM4.setError("Please enter a valid Monthly price");
                return false;
            }

        }else if(layoutSetPriceInfoRentBinding.checkBox4.isChecked()){
            if (layoutSetPriceInfoRentBinding.tvSizeM5 != null && layoutSetPriceInfoRentBinding.tvSizeM5.getText().toString().isEmpty()) {
                layoutSetPriceInfoRentBinding.tvSizeM5.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM5.setError("Please enter Yearly price");
                return false;
            } else if (layoutSetPriceInfoRentBinding.tvSizeM5 != null && !(MyValidator.isValidFullName(layoutSetPriceInfoRentBinding.tvSizeM5.getText().toString()))) {
                layoutSetPriceInfoRentBinding.tvSizeM5.requestFocus();
                layoutSetPriceInfoRentBinding.tvSizeM5.setError("Please enter a valid Yearly price");
                return false;
            }

        } else if (!layoutSetPriceInfoRentBinding.checkBox.isChecked()) {
            layoutSetPriceInfoRentBinding.tvSizeM2.setText("");
        } else if (!layoutSetPriceInfoRentBinding.checkBox2.isChecked()) {
            layoutSetPriceInfoRentBinding.tvSizeM3.setText("");
        } else if (!layoutSetPriceInfoRentBinding.checkBox3.isChecked()) {
            layoutSetPriceInfoRentBinding.tvSizeM4.setText("");
        } else if (!layoutSetPriceInfoRentBinding.checkBox4.isChecked()) {
            layoutSetPriceInfoRentBinding.tvSizeM5.setText("");
        }
        return true;
    }

}
