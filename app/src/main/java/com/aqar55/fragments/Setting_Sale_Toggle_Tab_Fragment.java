package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.aqar55.R;
import com.aqar55.activitys.MainActivity;
import com.aqar55.databinding.LayoutSearchSaleToggleBinding;
import com.aqar55.model.AllDataList;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.GetCategoryList;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting_Sale_Toggle_Tab_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    LayoutSearchSaleToggleBinding layoutSearchSaleToggleBinding;
    View view;
    private String[] data;
    List<String> categories;
    List<String> propertIdList;
    private boolean isSelected;
    private HashMap<String,Object> stringObjectHashMap;
    private String purpose="",available="";
    private boolean isPropertIdSelected;
    private boolean isBedroomSelected;
    private boolean isBathrommSelected;
    private boolean isKitchenSelected;
    private boolean isBuildSize;
    private boolean isPlotSize;
    private boolean isMinBudget;
    private boolean isMaxBudget;
    private boolean isBuildYear;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutSearchSaleToggleBinding = DataBindingUtil.inflate(inflater, R.layout.layout_search_sale_toggle, container, false);

        view = layoutSearchSaleToggleBinding.getRoot();
        layoutSearchSaleToggleBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvReset.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvMoreForSale.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvPropertyTitle.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvAppLanguage.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvNoOfBathrooms.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvNoOfBedRooms.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvNoOfKitchen.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvMinPlotSize.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvMeasurement2.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvMinBudget.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvMaxBudget.setOnItemSelectedListener(this);
        layoutSearchSaleToggleBinding.tvSelectCategory.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvBedRooms.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvfBathrooms.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvKitchen.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvBuildSize.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvPlotSize.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvMinBudgetText.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvMaxBudgetText.setOnClickListener(this);
        layoutSearchSaleToggleBinding.tvYearOfBuildText.setOnClickListener(this);



        //getPropertyIdListing();
        getCategory();
        selectBashroom();
        selectBedRooms();
        selectKitchen();
//        getPlotSizeUnit();
//        getBuiltinsizeUnit();
//        budgetMax();
//        budgetMin();
        selectBuiltYear();
        stringObjectHashMap=new HashMap<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutSearchSaleToggleBinding.textView36.setVisibility(View.VISIBLE);
        layoutSearchSaleToggleBinding.tvPlotSize.setVisibility(View.VISIBLE);
        layoutSearchSaleToggleBinding.tvMaxPlotSize.setVisibility(View.VISIBLE);

        layoutSearchSaleToggleBinding.textView53.setVisibility(View.GONE);
        layoutSearchSaleToggleBinding.tvRentTime.setVisibility(View.GONE);
        layoutSearchSaleToggleBinding.spRentTime.setVisibility(View.GONE);
    }

    private void getPropertyIdListing() {
        propertIdList = new ArrayList<>();

        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<AllDataList> getCategoryListCall = api.getAllData();

        getCategoryListCall.enqueue(new Callback<AllDataList>() {
            @Override
            public void onResponse(@NonNull Call<AllDataList> call, @NonNull Response<AllDataList> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            propertIdList.add(response.body().getData().get(i).getId());
                        }
                        propertIdList.add(0,"Select Property ID");
                        setSpinnerPropertyId();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllDataList> call, @NonNull Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
            }
        });
    }

    private void setSpinnerPropertyId() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, propertIdList);

        layoutSearchSaleToggleBinding.spnPropertyId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String industry_type = parent.getItemAtPosition(position).toString();
                if(isPropertIdSelected){
                    layoutSearchSaleToggleBinding.tvPropertyTitle.setText(industry_type);
                }
                if((TextView)parent.getChildAt(0)!=null){
                    if(industry_type.equalsIgnoreCase("Select Property ID")){
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.greyDim));
                    }else {
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.spnPropertyId.setAdapter(dataAdapter);
    }

    private void selectBuiltYear() {
        ArrayList<String> years = new ArrayList<String>();
        years.add("Any");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2019; i >= 1000; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, years);

        layoutSearchSaleToggleBinding.tvYearOfBuild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isBuildYear)
                    layoutSearchSaleToggleBinding.tvYearOfBuildText.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        layoutSearchSaleToggleBinding.tvYearOfBuild.setAdapter(adapter);
    }

    private void getCategory() {
        categories = new ArrayList<>();

        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetCategoryList> getCategoryListCall = api.getPropertyCategory();

        getCategoryListCall.enqueue(new Callback<GetCategoryList>() {
            @Override
            public void onResponse(@NonNull Call<GetCategoryList> call, @NonNull Response<GetCategoryList> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.body() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                categories.add(response.body().getData().get(i).getName());
                            }
                            categories.add(0,"Select Category");
                            setSpinnerAdapter();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCategoryList> call, @NonNull Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
            }
        });


    }

    public void setSpinnerAdapter(){
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        layoutSearchSaleToggleBinding.tvAppLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String industry_type = parent.getItemAtPosition(position).toString();
                if(isSelected){
                    layoutSearchSaleToggleBinding.tvSelectCategory.setText(industry_type);
                }

                if((TextView)parent.getChildAt(0)!=null){
                    if(industry_type.equalsIgnoreCase("Select Category")){
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.greyDim));
                    }else {
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvAppLanguage.setAdapter(dataAdapter);
    }

    private void selectBashroom() {
        data = DataGenerator.selectBashroom();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvNoOfBathrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isBathrommSelected){
                    layoutSearchSaleToggleBinding.tvfBathrooms.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvNoOfBathrooms.setAdapter(dataAdapter);
    }

    private void selectBedRooms() {
        data = DataGenerator.selectBashroom();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
        layoutSearchSaleToggleBinding.tvNoOfBedRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isBedroomSelected){
                    layoutSearchSaleToggleBinding.tvBedRooms.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvNoOfBedRooms.setAdapter(dataAdapter);
    }

    private void selectKitchen() {
        data = DataGenerator.selectBashroom();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvNoOfKitchen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isKitchenSelected){
                    layoutSearchSaleToggleBinding.tvKitchen.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvNoOfKitchen.setAdapter(dataAdapter);
    }

    private void getPlotSizeUnit() {
        data = DataGenerator.getPlotSizeUnit();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvMinPlotSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isPlotSize){
                    layoutSearchSaleToggleBinding.tvPlotSize.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvMinPlotSize.setAdapter(dataAdapter);
    }

    private void getBuiltinsizeUnit() {
        data = DataGenerator.getPlotSizeUnit();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvMeasurement2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isBuildSize){
                    layoutSearchSaleToggleBinding.tvBuildSize.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvMeasurement2.setAdapter(dataAdapter);
    }

    private void budgetMin() {
        data = DataGenerator.budgetMin();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvMinBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isMinBudget){
                    layoutSearchSaleToggleBinding.tvMinBudgetText.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutSearchSaleToggleBinding.tvMinBudget.setAdapter(dataAdapter);
    }

    private void budgetMax() {
        data = DataGenerator.budgetMin();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        layoutSearchSaleToggleBinding.tvMaxBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(isMaxBudget){
                    layoutSearchSaleToggleBinding.tvMaxBudgetText.setText(item);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutSearchSaleToggleBinding.tvMaxBudget.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvSavePostedProperty:
                HashMap<String,Object> parameterData=prepareData();
                startActivity(MainActivity.getIntent(getActivity(),parameterData));
                getActivity().finish();
                break;

            case R.id.tvReset:
                getFragmentManager().beginTransaction().replace(R.id.frameSearchTab_container,new Setting_Sale_Toggle_Tab_Fragment()).commit();
                break;

            case R.id.tvMoreForSale:
                layoutSearchSaleToggleBinding.tvMoreForSale.setVisibility(View.GONE);
                layoutSearchSaleToggleBinding.constraintLayout4.setVisibility(View.VISIBLE);
                break;

            case R.id.tvSelectCategory:
                isSelected  = true;
                layoutSearchSaleToggleBinding.tvAppLanguage.performClick();
                break;

            case R.id.tvPropertyTitle:
//                isPropertIdSelected = true;
//                layoutSearchSaleToggleBinding.spnPropertyId.performClick();
                break;
            case R.id.tvBedRooms:
                isBedroomSelected = true;
                layoutSearchSaleToggleBinding.tvNoOfBedRooms.performClick();
                break;
            case R.id.tvfBathrooms:
                isBathrommSelected = true;
                layoutSearchSaleToggleBinding.tvNoOfBathrooms.performClick();
                break;
            case R.id.tvKitchen:
                isKitchenSelected = true;
                layoutSearchSaleToggleBinding.tvNoOfKitchen.performClick();
                break;
            case R.id.tvBuildSize:
//                isBuildSize = true;
//                layoutSearchSaleToggleBinding.tvMeasurement2.performClick();
                break;
            case R.id.tvPlotSize:
//                isPlotSize = true;
//                layoutSearchSaleToggleBinding.tvMinPlotSize.performClick();
                break;
            case R.id.tvMinBudgetText:
//                isMinBudget = true;
//                layoutSearchSaleToggleBinding.tvMinBudget.performClick();
                break;
            case R.id.tvMaxBudgetText:
//                isMaxBudget = true;
//                layoutSearchSaleToggleBinding.tvMaxBudget.performClick();
                break;

            case R.id.tvYearOfBuildText:
                isBuildYear = true;
                layoutSearchSaleToggleBinding.tvYearOfBuild.performClick();
                break;
        }
    }

    private HashMap<String, Object> prepareData() {

        stringObjectHashMap.put("main_act","sale");
        stringObjectHashMap.put("propertyId",layoutSearchSaleToggleBinding.tvPropertyTitle.getText().toString());
        stringObjectHashMap.put("category",layoutSearchSaleToggleBinding.tvSelectCategory.getText().toString());
        int i = layoutSearchSaleToggleBinding.rgPurpose.getCheckedRadioButtonId();
        switch (i){
            case R.id.rbResidential:
                purpose = layoutSearchSaleToggleBinding.rbResidential.getText().toString();
                break;
            case R.id.rbCommercial:
                purpose = layoutSearchSaleToggleBinding.rbCommercial.getText().toString();
                break;
            case R.id.rbBoth:
                purpose = layoutSearchSaleToggleBinding.rbBoth.getText().toString();
                break;
        }
        stringObjectHashMap.put("purpose",purpose);
        int j = layoutSearchSaleToggleBinding.rgAvailableForU.getCheckedRadioButtonId();
        switch (i){
            case R.id.rbFamily:
                available = layoutSearchSaleToggleBinding.rbFamily.getText().toString();
                break;
            case R.id.rbSingle:
                available = layoutSearchSaleToggleBinding.rbSingle.getText().toString();
                break;
            case R.id.rbBothAvailable:
                available = layoutSearchSaleToggleBinding.rbBothAvailable.getText().toString();
                break;
        }
        stringObjectHashMap.put("available",available);

        stringObjectHashMap.put("bedrooms",layoutSearchSaleToggleBinding.tvBedRooms.getText().toString());
        stringObjectHashMap.put("bathrooms",layoutSearchSaleToggleBinding.tvfBathrooms.getText().toString());
        stringObjectHashMap.put("kitchens",layoutSearchSaleToggleBinding.tvKitchen.getText().toString());
        stringObjectHashMap.put("buildSizeMin",layoutSearchSaleToggleBinding.tvMinBudgetText.getText().toString());
        stringObjectHashMap.put("buildSizeMax",layoutSearchSaleToggleBinding.tvMaxBudgetText.getText().toString());
        stringObjectHashMap.put("plotSize",layoutSearchSaleToggleBinding.tvMaxPlotSize.getText().toString());
        stringObjectHashMap.put("plotSizeUnit",layoutSearchSaleToggleBinding.tvPlotSize.getText().toString());
        stringObjectHashMap.put("plotSizeMin",layoutSearchSaleToggleBinding.tvMaxPlotSize.getText().toString());
        stringObjectHashMap.put("plotSizeMax",layoutSearchSaleToggleBinding.tvPlotSize.getText().toString());
        stringObjectHashMap.put("yearBuilt",layoutSearchSaleToggleBinding.tvYearOfBuildText.getText().toString());
        stringObjectHashMap.put("balcony",layoutSearchSaleToggleBinding.ivNotificationToggle2.isChecked());
        stringObjectHashMap.put("garden",layoutSearchSaleToggleBinding.toggleGarden.isChecked());
        stringObjectHashMap.put("parking",layoutSearchSaleToggleBinding.toggleParking.isChecked());
        stringObjectHashMap.put("modularKitchen",layoutSearchSaleToggleBinding.toggleKitchen.isChecked());
        stringObjectHashMap.put("photos","");
        stringObjectHashMap.put("videos","");
        stringObjectHashMap.put("store",layoutSearchSaleToggleBinding.toggleStore.isChecked());
        stringObjectHashMap.put("lift",layoutSearchSaleToggleBinding.toggleLift.isChecked());
        stringObjectHashMap.put("duplex",layoutSearchSaleToggleBinding.toggleDuplex.isChecked());
        stringObjectHashMap.put("furnished",layoutSearchSaleToggleBinding.tbFurnished.isChecked());
        stringObjectHashMap.put("airConditioning",layoutSearchSaleToggleBinding.tbAirConditioning.isChecked());
        stringObjectHashMap.put("renTime",layoutSearchSaleToggleBinding.tvRentTime.getText().toString());




        return stringObjectHashMap;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
