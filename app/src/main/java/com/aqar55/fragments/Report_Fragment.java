package com.aqar55.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aqar55.R;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.ResponseReport;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report_Fragment extends Fragment implements View.OnClickListener {

    View view;

    private Spinner spnReasons;
    private LinearLayout btnSubmit;
    private TextView tvReasons;
    private EditText edtDetails;
    private boolean isReason;
    private  String[] reasons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_report, container, false);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.layout_3_report).setOnClickListener(this);
        view.findViewById(R.id.layout_1_report).setOnClickListener(this);
        edtDetails = view.findViewById(R.id.edtDetails);
        spnReasons = view.findViewById(R.id.spnReasons);
        tvReasons = view.findViewById(R.id.tvReasons);
       // btnSubmit = view.findViewById(R.id.tvSubmit);

        setMeasurement();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //getActivity().finish();
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                fm.popBackStack("Property_Detail_Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.layout_1_report:

                isReason = true;
                spnReasons.performClick();
                break;

            case R.id.layout_3_report:
                hitContactAdminApi();
                break;


        }
    }

    private void hitContactAdminApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ResponseReport> getPropertyDetailCall = api.setReport(ModelManager.modelManager().getCurrentUser().getId(),tvReasons.getText().toString(),edtDetails.getText().toString());
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

    private void setMeasurement() {
        String[] measurements = DataGenerator.getReasons();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,measurements){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if(position==0){
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        spnReasons.setAdapter(adapter);
        spnReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String measurement = parent.getItemAtPosition(position).toString();

                    tvReasons.setText(measurement);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




}
