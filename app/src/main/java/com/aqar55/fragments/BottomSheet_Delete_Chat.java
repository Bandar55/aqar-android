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

import com.aqar55.R;

public class BottomSheet_Delete_Chat extends BottomSheetDialogFragment {
    private String roomID = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_delete_chat, container, false);
        if (getArguments() != null) {
            roomID = getArguments().getString("roomId");

        }
        view.findViewById(R.id.okBtn).setOnClickListener(view12 -> {
            dismiss();
            getFragmentManager().popBackStack();
            deleteChat(roomID);
        });
        view.findViewById(R.id.cancelBtn).setOnClickListener(view1 -> dismiss());
        return view;

    }

    //chat delete api calling here
    private void deleteChat(String roomID) {
        Intent intent = new Intent("sucess");
        // You can also include some extra data.
        intent.putExtra("priceltoh", "data");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);


    }
}
