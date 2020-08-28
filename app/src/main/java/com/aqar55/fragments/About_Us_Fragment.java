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

import com.aqar55.R;
import com.aqar55.databinding.FragmentAboutUsBinding;
import com.aqar55.model.StaticContentByType;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class About_Us_Fragment extends Fragment implements View.OnClickListener {
    View view;
    FragmentAboutUsBinding fragmentAboutUsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAboutUsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        view = fragmentAboutUsBinding.getRoot();

        getAboutUsData();

        fragmentAboutUsBinding.ivBack.setOnClickListener(this);

        return view;

    }

    private void getAboutUsData() {

        Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
        Call<StaticContentByType> call = apiInterface.getStaticData("AboutUs");

        call.enqueue(new Callback<StaticContentByType>() {
            @Override
            public void onResponse(Call<StaticContentByType> call, Response<StaticContentByType> response) {
                if (response.isSuccessful()) {
                    StaticContentByType staticContentByType = response.body();
                    fragmentAboutUsBinding.tvAboutUsAct.setText(staticContentByType.getData().getDescription());
                    fragmentAboutUsBinding.aboutustitle.setText(staticContentByType.getData().getTitle());
                }
            }

            @Override
            public void onFailure(Call<StaticContentByType> call, Throwable t) {

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
