package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.aqar55.R;
import com.aqar55.databinding.FragmentSetPriceInfoBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;

public class Set_Price_Fragment extends Fragment implements View.OnClickListener {
    FragmentSetPriceInfoBinding fragmentSetPriceInfoBinding;
    View view;
    private List<String> data;
    private Bundle bundle = new Bundle();
    private String sizem2, pricepermeter, totalprice;
    private Add_Image_Post_Property_Fragment add_image_post_property_fragment;
    ManageActiveProperty.Data editData;
    private int totalAmount;
    private int size=1;
    private int pricePrMeter=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSetPriceInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_price_info, container, false);
        view = fragmentSetPriceInfoBinding.getRoot();

        fragmentSetPriceInfoBinding.textView55.setText("Price per meter("+ModelManager.modelManager().getCurrentUser().getCurrency()+")");
        fragmentSetPriceInfoBinding.textView66.setText("Total Price("+ModelManager.modelManager().getCurrentUser().getCurrency()+")");
        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet.", Toast.LENGTH_SHORT).show();


        }

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        fragmentSetPriceInfoBinding.tvSaveSetPrice.setOnClickListener(this);

        add_image_post_property_fragment = new Add_Image_Post_Property_Fragment();
        bundle = getArguments();
        getIntentData();


        fragmentSetPriceInfoBinding.tvSizeM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                try {
                    size=Integer.parseInt(fragmentSetPriceInfoBinding.tvSizeM2.getText().toString());
                    pricePrMeter=Integer.parseInt(fragmentSetPriceInfoBinding.tvPricePerMeter.getText().toString());
                    fragmentSetPriceInfoBinding.tvTotalPrice.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.getDefault()).format(size*pricePrMeter)));
                }catch (Exception e){

                }


            }

            @Override
            public void afterTextChanged(Editable s) {




            }
        });


        fragmentSetPriceInfoBinding.tvPricePerMeter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    size=Integer.parseInt(fragmentSetPriceInfoBinding.tvSizeM2.getText().toString());
                    pricePrMeter=Integer.parseInt(fragmentSetPriceInfoBinding.tvPricePerMeter.getText().toString());
                    fragmentSetPriceInfoBinding.tvTotalPrice.setText(String.valueOf( NumberFormat.getNumberInstance(Locale.getDefault()).format( size*pricePrMeter)));
                }catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        return view;
    }




    private void getIntentData() {
      // fragmentSetPriceInfoBinding.tvSizeM2.setText(bundle.getString("plotsize"));
       editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
       if(editData!=null){
           fragmentSetPriceInfoBinding.tvSizeM2.setText(editData.getSizem2());
           fragmentSetPriceInfoBinding.tvPricePerMeter.setText(editData.getPricepermeter());
           fragmentSetPriceInfoBinding.tvTotalPrice.setText(editData.getTotalpricesale());
       }/*else {
           fragmentSetPriceInfoBinding.tvSizeM2.setText("0.00");
           fragmentSetPriceInfoBinding.tvPricePerMeter.setText("0.00");
           fragmentSetPriceInfoBinding.tvTotalPrice.setText("0.00");

       }*/
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                getFragmentManager().popBackStack();
                break;
            case R.id.tvSaveSetPrice:
                if (validation()) {
                    collectData();
                }
                break;
        }
    }

    private boolean validation() {
        if (fragmentSetPriceInfoBinding.tvSizeM2.getText().toString().equalsIgnoreCase("Please Select Plot Size")) {
            ((TextView) fragmentSetPriceInfoBinding.tvSizeM2).setError("Error message");
            return false;
        } else  if (fragmentSetPriceInfoBinding.tvPricePerMeter.getText().toString().equalsIgnoreCase("Please Select Price Per Meter")) {
            ((TextView) fragmentSetPriceInfoBinding.tvPricePerMeter).setError("Error message");
            return false;
        }else  if (fragmentSetPriceInfoBinding.tvTotalPrice.getText().toString().equalsIgnoreCase("Please Select Total Price")) {
            ((TextView) fragmentSetPriceInfoBinding.tvTotalPrice).setError("Error message");
            return false;
        }
        return true;
    }

    private void collectData() {
        bundle.putString("sizem2", fragmentSetPriceInfoBinding.tvSizeM2.getText().toString());
        bundle.putString("pricepermeter", fragmentSetPriceInfoBinding.tvPricePerMeter.getText().toString());
        bundle.putString("totalprice", fragmentSetPriceInfoBinding.tvTotalPrice.getText().toString());
        if(editData!=null){
            bundle.putSerializable("Data",editData);
        }

        add_image_post_property_fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_image_post_property_fragment).addToBackStack("").commit();
    }


}
