package com.aqar55.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.aqar55.R;
import com.aqar55.adapters.Notification_Main_Adapter;
import com.aqar55.databinding.FragmentNotificationMainBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.NotificationModel;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Main_Fragment extends Fragment implements View.OnClickListener {
    private FragmentNotificationMainBinding fragmentNotificationMainBinding;
    private View view;
    private Notification_Main_Adapter notification_main_adapter;
    private List<NotificationModel.Data> notificationData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNotificationMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification__main_, container, false);
        view = fragmentNotificationMainBinding.getRoot();
        notificationData = new ArrayList<>();
        fragmentNotificationMainBinding.ivBack.setOnClickListener(this);
        notificationApi();
        return view;
    }

    private void notificationApi() {


        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<NotificationModel> getPropertyListingCall = api.getNoticationList(ModelManager.modelManager().getCurrentUser().getId());
        getPropertyListingCall.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        notificationData = response.body().getData();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        notification_main_adapter = new Notification_Main_Adapter(getContext(), notificationData);
                        fragmentNotificationMainBinding.notificationMainRecycler.setLayoutManager(linearLayoutManager);
                        fragmentNotificationMainBinding.notificationMainRecycler.setAdapter(notification_main_adapter);
                        notification_main_adapter.notifyDataSetChanged();
                    } else {
                        Toaster.toast(response.body().getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {


            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                getActivity().finish();
                break;
        }
    }
}
