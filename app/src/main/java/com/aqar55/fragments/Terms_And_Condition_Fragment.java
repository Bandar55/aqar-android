package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.aqar55.R;
import com.aqar55.databinding.FragmentTermsAndConditionsBinding;

public class Terms_And_Condition_Fragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    FragmentTermsAndConditionsBinding fragmentTermsAndConditionsBinding;
    View view;
    TextView  title;
    WebView desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentTermsAndConditionsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_terms_and_conditions, container, false);
        view = fragmentTermsAndConditionsBinding.getRoot();
        desc = view.findViewById(R.id.textView35);
        title = view.findViewById(R.id.termstitle);

        getTermsAndConditionData();



        view.findViewById(R.id.ivBack).setOnClickListener(this);
        return view;
    }

    private void getTermsAndConditionData() {
        WebSettings webSettings = desc.getSettings();
        webSettings.setJavaScriptEnabled(true);

        desc.loadUrl("http://18.217.0.63/realstate/terms.html");

      /*  Api apiInterface = ApiClientConnection.getInstance().createApiInterface();
        Call<StaticContentByType> call = apiInterface.getStaticData("TermCondition");

        call.enqueue(new Callback<StaticContentByType>() {
            @Override
            public void onResponse(Call<StaticContentByType> call, Response<StaticContentByType> response) {

                if (response.isSuccessful()) {
                    StaticContentByType staticContentByType = response.body();
                    desc.setText(staticContentByType.getData().getDescription());
                    title.setText(staticContentByType.getData().getTitle());
                }

            }

            @Override
            public void onFailure(Call<StaticContentByType> call, Throwable t) {

            }
        });*/

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

    @Override
    public void onRefresh() {
        getTermsAndConditionData();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
