package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.adapters.Chat_Detail_Property_Adapter;
import com.aqar55.databinding.LayoutChatDetailBinding;

public class Chat_Detail_Fragment extends Fragment implements View.OnClickListener {
    LayoutChatDetailBinding layoutChatDetailBinding;
    View view;
    Chat_Detail_Property_Adapter chat_detail_property_adapter;

    private String proId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutChatDetailBinding = DataBindingUtil.inflate(inflater, R.layout.layout_chat_detail, container, false);
        view = layoutChatDetailBinding.getRoot();
        //chat_detail_property_adapter = new Chat_Detail_Property_Adapter(getContext());

        layoutChatDetailBinding.chatDetailsImageviewOption.setOnClickListener(this);
        layoutChatDetailBinding.ivBack.setOnClickListener(this);



        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        layoutChatDetailBinding.recycleChatDetailProperty.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutChatDetailBinding.recycleChatDetailProperty.setAdapter(chat_detail_property_adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_details_imageview_option:
                BottomSheet_Delete_Chat bottomSheetFragment = new BottomSheet_Delete_Chat();
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
                break;

                case R.id.ivBack:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    break;

        }
    }
}
