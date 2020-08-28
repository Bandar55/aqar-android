package com.aqar55.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqar55.R;
import com.aqar55.adapters.AdminChatAdapter;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.AdminDetailsModel;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Previous_Chat_With_Admin extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private AdminChatAdapter adminChatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous__chat__with__admin, container, false);
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        recyclerView = view.findViewById(R.id.rcycle_admin);
        prePareData();
        return view;
    }

    private void prePareData() {

        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<AdminDetailsModel> getPropertyDetailCall = api.getAdminDetails(ModelManager.modelManager().getCurrentUser().getId());
        getPropertyDetailCall.enqueue(new Callback<AdminDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<AdminDetailsModel> call, @NonNull Response<AdminDetailsModel> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                if (response.isSuccessful()) {
                    AdminDetailsModel body = response.body();
                    adminChatAdapter = new AdminChatAdapter(getActivity(), body.getData());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adminChatAdapter);
                }
            }

            @Override
            public void onFailure(Call<AdminDetailsModel> call, Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
        }
    }
}
