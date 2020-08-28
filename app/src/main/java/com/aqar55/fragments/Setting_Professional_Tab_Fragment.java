package com.aqar55.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.activitys.MainActivity;
import com.aqar55.adapters.SpinnerCategoryList;
import com.aqar55.adapters.SpinnerSubCategoryList;
import com.aqar55.adapters.TinyArrayAdapter;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.ProfessionalDataResponse;
import com.aqar55.model.ProfessionalResponse;
import com.aqar55.model.SubCatResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting_Professional_Tab_Fragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.spinner_category2)
    Spinner spSubCate;
    @BindView(R.id.spinner_category3)
    Spinner spLanguage;
    View view;
    @BindView(R.id.tvSpockenLanguage)
    TextView tvLangueage;
    @BindView(R.id.tvProfCategory)
    TextView tvCategory;
    @BindView(R.id.tvSubCate)
    TextView tvSubCate;
    @BindView(R.id.tvSpecialitiies)
    EditText edtSpecialities;
    @BindView(R.id.edtAreaCoverage)
    EditText edtAreaCoverage;
    @BindView(R.id.edt_search_by_name2)
    EditText edtSearchById;
    @BindView(R.id.edt_search_by_name)
    EditText edtSeachByName;


    private String categoryId = "";
    private String subCatName = "";
    private String catName = "";
    private SpinnerCategoryList spinnerCategoryList;
    private SpinnerSubCategoryList spinnerSubCategoryList;
    private List<ProfessionalDataResponse> professionalList;
    private List<SubCatResponse.Data> professionalSubCatList;
    private HashMap<String,Object> stringObjectList;
    private String professionalId = "";
    private boolean isCategory;
    private boolean isSubCategory;



    Spinner.OnItemSelectedListener subCategoryList = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                // getPrepareSubCatList(professionalList.get(position));
                if (position == 0)
                    return;
                tvSubCate.setText(professionalSubCatList.get(position).getName());

                subCatName = professionalSubCatList.get(position).getName();


            } catch (Exception e) {

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private String[] language;
    //For language Spinner
    Spinner.OnItemSelectedListener onItemSelectedListenerActive = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (position == 0)
                return;
            tvLangueage.setText(language[position]);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //  Toaster.toast("Please select the timeslot in which you want to add the menu");
        }
    };

    Spinner.OnItemSelectedListener categoryList = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                getPrepareSubCatList(professionalList.get(position));
                if(position==0)
                    return;
                tvCategory.setText(professionalList.get(position).getName());
                categoryId = professionalList.get(position).getId();
                catName = professionalList.get(position).getName();
            } catch (Exception e) {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prof_tab, container, false);
        ButterKnife.bind(this, view);
        stringObjectList = new HashMap<>();
        language = DataGenerator.getLaguage();
        languageSpinner();
        getPrepareDataCategory();


        return view;
    }


    //subcat api calling here

    private void getPrepareSubCatList(ProfessionalDataResponse professionalDataResponse) {

        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<SubCatResponse> getCategoryList = api.getSubCatList(professionalDataResponse.getId());

        getCategoryList.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubCatResponse> call, @NonNull Response<SubCatResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    if (response.body() != null) {

                        professionalSubCatList = response.body().getData();
                        if(professionalSubCatList!=null){
                            SubCatResponse.Data data = new SubCatResponse.Data();
                            data.setName("Select Subcategory");
                            professionalSubCatList.add(0,data);
                            spinnerSubCategoryList = new SpinnerSubCategoryList(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item, professionalSubCatList);
                            spSubCate.setAdapter(spinnerSubCategoryList);
                            spSubCate.setOnItemSelectedListener(subCategoryList);
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });
    }

    //api calling for category
    private void getPrepareDataCategory() {
        MyDialog.getInstance(getActivity()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<ProfessionalResponse> getCategoryList = api.getCategoryList();

        getCategoryList.enqueue(new Callback<ProfessionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalResponse> call, @NonNull Response<ProfessionalResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getActivity()).hideDialog();
                    ProfessionalResponse body = response.body();
                    if (body != null  && body.getData()!=null && !body.getData().isEmpty()) {
                        professionalList = body.getData();
                        ProfessionalDataResponse professionalDataResponse = new ProfessionalDataResponse();
                        professionalDataResponse.setId("Select Category");
                        professionalList.add(0,professionalDataResponse);
                        spinnerCategoryList = new SpinnerCategoryList(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, professionalList);
                        spinnerCategory.setAdapter(spinnerCategoryList);
                        spinnerCategory.setOnItemSelectedListener(categoryList);


                        // }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfessionalResponse> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();

            }
        });


    }


    private void languageSpinner() {
        language = DataGenerator.getLaguage();
        TinyArrayAdapter<String> slotAdapter = new TinyArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, language);
        spLanguage.setAdapter(slotAdapter);
        spLanguage.setSelection(0);
        spLanguage.setOnItemSelectedListener(onItemSelectedListenerActive);
    }


    @OnClick({R.id.tvProfCategory, R.id.tvSearchProf, R.id.tvSpockenLanguage,R.id.tvSubCate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchProf:
                //getUserDatails();
                HashMap<String, Object> hashMap = prepareData();
                startActivity(MainActivity.getIntent(getActivity(),hashMap));
                getActivity().finish();

                break;
            case R.id.tvProfCategory:

                spinnerCategory.performClick();
                break;
            case R.id.tvSubCate:

                spSubCate.performClick();
                break;
            case R.id.tvSpockenLanguage:

                spLanguage.performClick();
                break;

        }
    }

    private HashMap<String, Object> prepareData() {

        if(getArguments()!=null){
          String from = getArguments().getString("from");
          if(from.equalsIgnoreCase("prof")){
              stringObjectList.put("main_act","prof");
          }else {
              stringObjectList.put("main_act","business");
          }
        }
        //if(ModelManager.modelManager().getCurrentUser()!=null)
            //professionalId = ModelManager.modelManager().getCurrentUser().getProfessionalid();
        stringObjectList.put("fullName",edtSeachByName.getText().toString().trim());
        stringObjectList.put("professionalId",professionalId);
        stringObjectList.put("category",tvCategory.getText().toString());
        stringObjectList.put("subCategory",subCatName);
        stringObjectList.put("area",edtAreaCoverage.getText().toString().trim());
        stringObjectList.put("speakLanguage",tvLangueage.getText().toString());

        return stringObjectList;

    }


}
