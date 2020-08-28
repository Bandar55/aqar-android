package com.aqar55.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.aqar55.R;
import com.aqar55.databinding.FragmentContactAdminBinding;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.ResponseReport;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contact_Admin_Fragment extends Fragment implements View.OnClickListener {
    private FragmentContactAdminBinding fragmentContactAdminBinding;
    private View view;
    private Previous_Chat_With_Admin previous_chat_with_admin = new Previous_Chat_With_Admin();



    private LinearLayout btnSubmit;
    private EditText edtDetails;
    private ArrayList<String> checkList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentContactAdminBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact__admin_, container, false);
        view = fragmentContactAdminBinding.getRoot();
        fragmentContactAdminBinding.chatImageviewContactAdmin.setOnClickListener(this);
        fragmentContactAdminBinding.tvSubmitContact.setOnClickListener(this);
        fragmentContactAdminBinding.ivBack.setOnClickListener(this);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvSubmitContact).setOnClickListener(this);
        edtDetails = view.findViewById(R.id.edtDetailsContact);
        checkList=new ArrayList<>();



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_imageview_contact_admin:

                getFragmentManager().beginTransaction().replace(R.id.propertysalecontainer, previous_chat_with_admin).addToBackStack("").commit();

                break;

            case R.id.ivBack:
                getActivity().finish();
              // getFragmentManager().popBackStack();
                break;


            case R.id.tvSubmitContact:

                getCheckData();
                hitContactAdminApi();
                break;

        }
    }

    private void getCheckData() {

        if(fragmentContactAdminBinding.cbWrongPrice.isChecked()){
            checkList.add(fragmentContactAdminBinding.cbWrongPrice.getText().toString());

        } if(fragmentContactAdminBinding.cbWrongLocation.isChecked()){
            checkList.add(fragmentContactAdminBinding.cbWrongLocation.getText().toString());

        } if(fragmentContactAdminBinding.cbImpliteResponse.isChecked()){
            checkList.add(fragmentContactAdminBinding.cbImpliteResponse.getText().toString());

        } if(fragmentContactAdminBinding.cbExpireAd.isChecked()){
            checkList.add(fragmentContactAdminBinding.cbExpireAd.getText().toString());

        } if(fragmentContactAdminBinding.cbOther.isChecked()){
            checkList.add(fragmentContactAdminBinding.cbOther.getText().toString());

        }
    }


    private void hitContactAdminApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ResponseReport> getPropertyDetailCall = api.setReport(ModelManager.modelManager().getCurrentUser().getId(), TextUtils.join(",",checkList),edtDetails.getText().toString());
        getPropertyDetailCall.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReport> call, @NonNull Response<ResponseReport> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                if (response.isSuccessful()) {
                    ResponseReport body = response.body();

                    Toaster.toast(body.getResponseMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseReport> call, Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();

            }
        });
    }


}
