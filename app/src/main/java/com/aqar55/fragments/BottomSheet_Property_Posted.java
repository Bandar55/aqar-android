package com.aqar55.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aqar55.R;
import com.aqar55.activitys.Manage_Posted_Property;

public class BottomSheet_Property_Posted extends BottomSheetDialogFragment {

    private String message;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_property_posted, container, false);
        view.findViewById(R.id.tvPostedProperty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), Manage_Posted_Property.class));
                dismiss();
                getActivity().finish();

            }
        });
        return view;
    }
}