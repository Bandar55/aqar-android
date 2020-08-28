package com.aqar55.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.aqar55.R;
import com.aqar55.databinding.LayoutSortbyBottomsheetBinding;


public class BottomSheetSortProduct extends BottomSheetDialogFragment implements View.OnClickListener {

    boolean priceltoh = false, pricehtol = false, sizeltoh = false, sizehtol = false;
    RadioButton radioButton, radioButton2;
    private LayoutSortbyBottomsheetBinding layoutSortbyBottomsheetBinding;
    private View view;
    private String tabSelection;
    private Bundle b = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutSortbyBottomsheetBinding = DataBindingUtil.inflate(inflater, R.layout.layout_sortby_bottomsheet, container, false);
        view = layoutSortbyBottomsheetBinding.getRoot();

        b = getArguments();

        if (b != null) {
            tabSelection = getArguments().getString("tab");
        }
        layoutSortbyBottomsheetBinding.doneSortsheet.setOnClickListener(this);
        layoutSortbyBottomsheetBinding.cancleSortsheet.setOnClickListener(this);
        layoutSortbyBottomsheetBinding.rbCommercial.setOnClickListener(this);
        layoutSortbyBottomsheetBinding.rbBoth.setOnClickListener(this);
        layoutSortbyBottomsheetBinding.lowtohignsize.setOnClickListener(this);
        layoutSortbyBottomsheetBinding.hightolowsize.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done_sortsheet:
                collectData();
                break;
            case R.id.cancle_sortsheet:
                dismiss();
                break;
            case R.id.rbCommercial:
                priceltoh = true;
                pricehtol = false;
                sizehtol = false;
                sizeltoh = false;
                collectData();
                break;
            case R.id.rbBoth:
                priceltoh = false;
                pricehtol = true;
                sizehtol = false;
                sizeltoh = false;
                collectData();
                break;
            case R.id.lowtohignsize:
                priceltoh = false;
                pricehtol = false;
                sizehtol = false;
                sizeltoh = true;
                collectData();
                break;
            case R.id.hightolowsize:
                priceltoh = false;
                pricehtol = false;
                sizehtol = true;
                sizeltoh = false;
                collectData();
                break;
        }
    }

    private void collectData() {
//        checkedRadioButtonId = layoutSortbyBottomsheetBinding.price.getCheckedRadioButtonId();
//        radioButton = layoutSortbyBottomsheetBinding.price.findViewById(checkedRadioButtonId);
//
//
//        checkedRadioButtonIdForFamily = layoutSortbyBottomsheetBinding.size.getCheckedRadioButtonId();
//        radioButton2 = layoutSortbyBottomsheetBinding.size.findViewById(checkedRadioButtonIdForFamily);


//        if (!(layoutSortbyBottomsheetBinding.price.getCheckedRadioButtonId() == -1)) {
//            if (radioButton.isChecked()) {
//                if (radioButton.getText().toString().equalsIgnoreCase("Low to High")) {
//                    radioButton.setTextColor(getResources().getColor(R.color.green));
//                    price = true;
//                    dismiss();
//
//                } else if (radioButton.getText().toString().equalsIgnoreCase("High to Low")) {
//                    radioButton.setTextColor(getResources().getColor(R.color.green));
//                    price = false;
//                    dismiss();
//                }
//            }
//        }
//        if (layoutSortbyBottomsheetBinding.size.getCheckedRadioButtonId() == -1) {
//            if (radioButton2.isChecked()) {
//
//                if (radioButton2.getText().toString().equalsIgnoreCase("Low to High")) {
//                    radioButton.setTextColor(getResources().getColor(R.color.green));
//                    size = true;
//                    dismiss();
//                } else if (radioButton2.getText().toString().equalsIgnoreCase("High to Low")) {
//                    radioButton.setTextColor(getResources().getColor(R.color.green));
//                    size = false;
//                    dismiss();
//                }
//            }
//        }
        Intent intent = new Intent(tabSelection);
        // You can also include some extra data.
        intent.putExtra("priceltoh", priceltoh);
        intent.putExtra("pricehtol", pricehtol);
        intent.putExtra("sizeltoh", sizeltoh);
        intent.putExtra("sizehtol", sizehtol);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        dismiss();
    }


}

