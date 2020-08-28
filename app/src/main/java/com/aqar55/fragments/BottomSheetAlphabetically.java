package com.aqar55.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;

public class BottomSheetAlphabetically extends BottomSheetDialogFragment {

    private String tabSelection;
    private boolean sortByName;
    private boolean sortByCategory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sort_by_alphabatically,container,false);
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            tabSelection = getArguments().getString("tab");
        }
        return view;
    }

    @OnClick({R.id.tvCancelAlphabetically,R.id.rbName,R.id.rbCategory})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvCancelAlphabetically:
                dismiss();
                break;

            case R.id.rbName:
                sortByName = true;
                sortByCategory = false;
                collectData();
                break;

            case R.id.rbCategory:
                sortByCategory  = true;
                sortByName = false;
                collectData();
                break;
        }
    }


    private void collectData() {

        Intent intent = new Intent(tabSelection);

        // You can also include some extra data.
        intent.putExtra("sortByName", sortByName);
        intent.putExtra("sortByCategory", sortByCategory);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        dismiss();
    }
}
