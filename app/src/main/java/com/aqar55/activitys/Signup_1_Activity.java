package com.aqar55.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.aqar55.R;
import com.aqar55.adapters.SpinnerCategoryList;
import com.aqar55.adapters.SpinnerSubCategoryList;
import com.aqar55.databinding.ActivitySignup1Binding;
import com.aqar55.helper.Toaster;
import com.aqar55.model.ProfessionalDataResponse;
import com.aqar55.model.ProfessionalResponse;
import com.aqar55.model.SubCatResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyDialog;
import com.aqar55.utill.MyValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup_1_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    private String pcategorydata, subcategory, gender, birthyear, email;
    private ActivitySignup1Binding signup1Binding;
    private Intent intent;
    private DatePickerDialog.OnDateSetListener onDateSetListener;



    private SpinnerCategoryList spinnerCategoryList;
    private SpinnerSubCategoryList spinnerSubCategoryList;
    private List<ProfessionalDataResponse> professionalList;
    private List<SubCatResponse.Data> professionalSubCatList;
    private String subCatName = "";
    private String userId = "";
    private String categoryId = "";




    Spinner.OnItemSelectedListener subCategoryList = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            signup1Binding.tvProfSubCat.setText(professionalSubCatList.get(position).getName());
            subCatName = professionalSubCatList.get(position).getName();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    Spinner.OnItemSelectedListener categoryList = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getPrepareSubCatList(professionalList.get(position));
            signup1Binding.tvProfCat.setText(professionalList.get(position).getName());
            categoryId = professionalList.get(position).getId();

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signup1Binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_1_);

        professionalList = new ArrayList<>();
        professionalSubCatList = new ArrayList<>();
        signup1Binding.tvProfSubCat.setOnClickListener(this);

        onDateSetListener = (datePicker, i, i1, i2) -> {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);


            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            signup1Binding.editText9.setText(sdf.format(myCalendar.getTime()));

        };

        if (!new InternetCheck(getApplicationContext()).isConnect()) {
            MyApplication.makeASnack(signup1Binding.getRoot(), "Make sure you are connected to Internet.");
        } else {
            intent = getIntent();

            //signup1Binding.editText6.setOnItemSelectedListener(this);
            signup1Binding.editText8.setOnItemSelectedListener(this);
            signup1Binding.editText9.setOnClickListener(this);
            signup1Binding.tvProfCat.setOnClickListener(this);
            //  signup1Binding.editText9.setOnItemSelectedListener(this)

            getPrepareDataCategory();
            selectGenderData();
            //   selectBirthYear();
            signup1Binding.okButtonLayoutSignup1Activity.setOnClickListener(this);
            signup1Binding.ivBack.setOnClickListener(this);
        }
    }




    //subcat api calling here

    private void getPrepareSubCatList(ProfessionalDataResponse professionalDataResponse) {
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<SubCatResponse> getCategoryList = api.getSubCatList(professionalDataResponse.getId());

        getCategoryList.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubCatResponse> call, @NonNull Response<SubCatResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(Signup_1_Activity.this).hideDialog();
                    if (response != null) {

                        if(response.body().getResponseCode()==200){
                            professionalSubCatList = response.body().getData();

                            spinnerSubCategoryList = new SpinnerSubCategoryList(Signup_1_Activity.this,
                                    android.R.layout.simple_spinner_dropdown_item, professionalSubCatList);
                            signup1Binding.spinnerSubCat.setAdapter(spinnerSubCategoryList);
                            signup1Binding.spinnerSubCat.setOnItemSelectedListener(subCategoryList);
                        }else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                MyDialog.getInstance(Signup_1_Activity.this).hideDialog();
                t.fillInStackTrace();
            }
        });
    }

    //api calling for category
    private void getPrepareDataCategory() {
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<ProfessionalResponse> getCategoryList = api.getCategoryList();

        getCategoryList.enqueue(new Callback<ProfessionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalResponse> call, @NonNull Response<ProfessionalResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(Signup_1_Activity.this).hideDialog();
                    if (response != null) {
                        if(response.body().getResponseCode()==200){
                            professionalList = response.body().getData();
                            spinnerCategoryList = new SpinnerCategoryList(Signup_1_Activity.this,
                                    android.R.layout.simple_spinner_dropdown_item, professionalList);
                            signup1Binding.spinnerCategory.setAdapter(spinnerCategoryList);
                           signup1Binding. spinnerCategory.setOnItemSelectedListener(categoryList);
                        }else {
                            Toaster.toast(response.body().getResponseMessage());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ProfessionalResponse> call, Throwable t) {
                MyDialog.getInstance(Signup_1_Activity.this).hideDialog();

            }
        });


    }
//
//    private void selectBirthYear() {
//        ArrayList<String> years = new ArrayList<String>();
//        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
//        for (int i = 1970; i <= thisYear; i++) {
//            years.add(Integer.toString(i));
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
//
//        signup1Binding.editText9.setAdapter(adapter);
//    }

    private void selectGenderData() {
        List<String> categories = new ArrayList<>();
        categories.add("Select Gender");
        categories.add("Male");
        categories.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signup1Binding.editText8.setAdapter(dataAdapter);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_button_layout_signup_1_activity:

                if (new InternetCheck(this).isConnect()) {
                    getAllSelectedData();
                } else {
                    MyApplication.makeASnack(signup1Binding.getRoot(), "Please Connect To Internet");
                }
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.editText9:
                hideKeyboard();
                new DatePickerDialog(Signup_1_Activity.this, R.style.DialogTheme, onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.tv_prof_cat:
                hideKeyboard();
                signup1Binding.spinnerCategory.performClick();
                break;

            case R.id.tv_prof_sub_cat:
                hideKeyboard();
                signup1Binding.spinnerSubCat.performClick();
                break;
        }
    }

    private void getAllSelectedData() {

        Bundle bundle = new Bundle();
        bundle.putString("name", signup1Binding.editText1.getText().toString());
        bundle.putString("category", signup1Binding.tvProfCat.getText().toString());
        bundle.putString("subcategory", signup1Binding.tvProfSubCat.getText().toString());
        bundle.putString("gender", gender);
        bundle.putString("byear", birthyear);
        bundle.putString("email", signup1Binding.email.getText().toString());
        bundle.putString("code", intent.getStringExtra("code"));
        bundle.putString("number", intent.getStringExtra("number"));

        if (validation()) {
            Intent intent = new Intent(Signup_1_Activity.this, Signup_2_Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            /*case R.id.editText6:
                hideKeyboard();
                pcategorydata = signup1Binding.editText6.getSelectedItem().toString();
                break;*/
            case R.id.tv_prof_cat:
                hideKeyboard();
                signup1Binding.spinnerCategory.performClick();
                break;
            case R.id.tv_prof_sub_cat:
                hideKeyboard();
               signup1Binding.spinnerSubCat.performClick();
                break;
            case R.id.editText8:
                gender = signup1Binding.editText8.getSelectedItem().toString();
                break;
//            case R.id.editText9:
//                birthyear = signup1Binding.editText9.getSelectedItem().toString();
//                break;
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(signup1Binding.rlSigUpOne.getWindowToken(), 0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validation() {
        if (signup1Binding.editText1 != null && signup1Binding.editText1.getText().toString().isEmpty()) {
            signup1Binding.editText1.requestFocus();
            signup1Binding.editText1.setError("Please enter Full Name ");
            return false;
        } else if (signup1Binding.editText1 != null && !(MyValidator.isValidFullName(signup1Binding.editText1.getText().toString()))) {
            signup1Binding.editText1.requestFocus();
            signup1Binding.editText1.setError("Please enter a valid Full Name ");
            return false;
        } else if (signup1Binding.email != null && signup1Binding.email.getText().toString().isEmpty()) {
            signup1Binding.email.requestFocus();
            signup1Binding.email.setError("Please Enter Email");
            return false;
        } else if (signup1Binding.email != null && !(MyValidator.isValidEmail(signup1Binding.email.getText().toString()))) {
            signup1Binding.email.requestFocus();
            signup1Binding.email.setError("Please enter valid Email");
            return false;
        }/* else if (signup1Binding.editText6.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
            MyApplication.makeASnack(signup1Binding.getRoot(), "Please Select Category");
            signup1Binding.editText6.requestFocus();
            ((TextView) signup1Binding.editText6.getSelectedView()).setError("Error message");
            return false;
        } else if (signup1Binding.editText7.getSelectedItem().toString().equalsIgnoreCase("Select Sub-Category")) {
            MyApplication.makeASnack(signup1Binding.getRoot(), "Please Select Sub Category");
            signup1Binding.editText7.requestFocus();
            ((TextView) signup1Binding.editText7.getSelectedView()).setError("Error message");
            return false;
        }*/ else if (signup1Binding.editText8.getSelectedItem().toString().equalsIgnoreCase("Select Gender")) {
            MyApplication.makeASnack(signup1Binding.getRoot(), "Please Select Gender");
            signup1Binding.editText8.requestFocus();
            ((TextView) signup1Binding.editText8.getSelectedView()).setError("Error message");
            return false;
        } else if (signup1Binding.editText9.getText().toString().equalsIgnoreCase("Please Select Date Of Birth")) {
            MyApplication.makeASnack(signup1Binding.getRoot(), "Please Enter DOB");
            signup1Binding.editText9.requestFocus();
            signup1Binding.editText9.setError("");
            return false;
        }
        return true;
    }


}
