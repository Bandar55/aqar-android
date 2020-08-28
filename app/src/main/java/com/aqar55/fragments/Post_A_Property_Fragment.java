package com.aqar55.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.LayoutPostAPropertyBinding;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyValidator;

public class Post_A_Property_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private LayoutPostAPropertyBinding layoutPostAPropertyBinding;
    private View view;
    private List<String> data;
    private Bundle bundle = new Bundle();
    private String builtinsizeunit, plotsizeUnit, tearBuilt, streetView, streetWidthUnit, extraBuildingNumber, extraShowroomNumber, revenue;
    private boolean balcony, garden, parking, modularkitchen;
    private boolean isBuildUnit,isPlotUnit,isYear,isStreet,isStreetUnit,isNoOfBuilding,isShowroom,isLengthUnit,isWidthUnit;
    ManageActiveProperty.Data editData;


    private String[] showRoomsData;
    private String[] buildingData;
    private String measureLength;
    private String measureWidth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutPostAPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.layout_post_a_property, container, false);
        view = layoutPostAPropertyBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
        }

        layoutPostAPropertyBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutPostAPropertyBinding.ivBack.setOnClickListener(this);

        layoutPostAPropertyBinding.tvMeasurement2.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.spLength.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.spWidth.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvMeasurement5.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvYearBuilt.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvStreetView.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvBuilding.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvBuildingMeter.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvStreetView2.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvYearBuilt.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvMeasurement7.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvBuiltUnit.setOnClickListener(this);
        layoutPostAPropertyBinding.tvPlotUnit.setOnClickListener(this);
        layoutPostAPropertyBinding.tvYearBuiltNo.setOnClickListener(this);
        layoutPostAPropertyBinding.tvStreet.setOnClickListener(this);
        layoutPostAPropertyBinding.tvStreetUnit.setOnClickListener(this);
        layoutPostAPropertyBinding.tvNoOfBuilding.setOnClickListener(this);
        layoutPostAPropertyBinding.tvNoOfShowroom.setOnClickListener(this);
        layoutPostAPropertyBinding.tvLengthUnit.setOnClickListener(this);
        layoutPostAPropertyBinding.tvWidthUnit.setOnClickListener(this);
        getBuiltinsizeData();
        getPlotSizeData();
        getLengthUnit();
        getWidthUnit();
        selectBuiltYear();
        selectStreetView();
        streetWidth();
        selectNoOfBuilding();
        selectNoOfShowrooms();
        //selectrevenue();

        bundle = getArguments();
        getIntentData();

        disableExtraField();

        /////////////////////////////////////////////////////////////toggerbutton////////////////////////

        layoutPostAPropertyBinding.ivNotificationToggle.setOnClickListener(arg0 -> {
            if( layoutPostAPropertyBinding.ivNotificationToggle.isChecked()){
//                layoutPostAPropertyBinding.ivNotificationToggle2.setChecked(true);
//                layoutPostAPropertyBinding.ivNotificationToggle3.setChecked(true);
//                layoutPostAPropertyBinding.ivNotificationToggle4.setChecked(true);
//                layoutPostAPropertyBinding.ivNotificationToggle5.setChecked(true);
//
//                layoutPostAPropertyBinding.ivNotificationToggle2.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle3.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle4.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle5.setEnabled(true);
//
//                layoutPostAPropertyBinding.ivNotificationToggle2.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle3.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle4.setEnabled(true);
//                layoutPostAPropertyBinding.ivNotificationToggle5.setEnabled(true);
//
//                layoutPostAPropertyBinding.tbLift.setEnabled(true);
//                layoutPostAPropertyBinding.tbDuplex.setEnabled(true);
//                layoutPostAPropertyBinding.tbFurnished.setEnabled(true);
//                layoutPostAPropertyBinding.tbAirConditioning.setEnabled(true);
//                layoutPostAPropertyBinding.tbStore.setEnabled(true);

                layoutPostAPropertyBinding.tvBuilding.setEnabled(true);
                layoutPostAPropertyBinding.tvBuildingMeter.setEnabled(true);
               // layoutPostAPropertyBinding.tvRevenue.setEnabled(true);
                layoutPostAPropertyBinding.tvNoOfBuilding.setEnabled(true);
                layoutPostAPropertyBinding.tvNoOfShowroom.setEnabled(true);

            }
            else{

//                layoutPostAPropertyBinding.ivNotificationToggle2.setChecked(false);
//                layoutPostAPropertyBinding.ivNotificationToggle3.setChecked(false);
//                layoutPostAPropertyBinding.ivNotificationToggle4.setChecked(false);
//                layoutPostAPropertyBinding.ivNotificationToggle5.setChecked(false);
//                layoutPostAPropertyBinding.tbLift.setChecked(false);
//                layoutPostAPropertyBinding.tbDuplex.setChecked(false);
//                layoutPostAPropertyBinding.tbFurnished.setChecked(false);
//                layoutPostAPropertyBinding.tbAirConditioning.setChecked(false);
//                layoutPostAPropertyBinding.tbStore.setChecked(false);
//
//                layoutPostAPropertyBinding.ivNotificationToggle2.setEnabled(false);
//                layoutPostAPropertyBinding.ivNotificationToggle3.setEnabled(false);
//                layoutPostAPropertyBinding.ivNotificationToggle4.setEnabled(false);
//                layoutPostAPropertyBinding.ivNotificationToggle5.setEnabled(false);
//
//                layoutPostAPropertyBinding.tbLift.setEnabled(false);
//                layoutPostAPropertyBinding.tbDuplex.setEnabled(false);
//                layoutPostAPropertyBinding.tbFurnished.setEnabled(false);
//                layoutPostAPropertyBinding.tbAirConditioning.setEnabled(false);
//                layoutPostAPropertyBinding.tbStore.setEnabled(false);

                layoutPostAPropertyBinding.tvBuilding.setEnabled(false);
                layoutPostAPropertyBinding.tvBuildingMeter.setEnabled(false);
               // layoutPostAPropertyBinding.tvRevenue.setEnabled(false);
                layoutPostAPropertyBinding.tvNoOfBuilding.setEnabled(false);
                layoutPostAPropertyBinding.tvNoOfShowroom.setEnabled(false);


            }

        });
        return view;
    }

    private void disableExtraField() {
        layoutPostAPropertyBinding.tvBuilding.setEnabled(false);
        layoutPostAPropertyBinding.tvBuildingMeter.setEnabled(false);
        // layoutPostAPropertyBinding.tvRevenue.setEnabled(false);
        layoutPostAPropertyBinding.tvNoOfBuilding.setEnabled(false);
        layoutPostAPropertyBinding.tvNoOfShowroom.setEnabled(false);

    }

    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
        if(editData!=null){
            layoutPostAPropertyBinding.tvMeasurement3.setText(editData.getBuiltsize());
            layoutPostAPropertyBinding.tvBuiltUnit.setText(editData.getBuiltsizeunit());
            layoutPostAPropertyBinding.tvMeasurement4.setText(editData.getPlotsize());
            layoutPostAPropertyBinding.tvPlotUnit.setText(editData.getPlotsizeunit());
            layoutPostAPropertyBinding.tvYearBuiltNo.setText(editData.getYearbuilt());
            layoutPostAPropertyBinding.tvStreet.setText(editData.getStreetview());
            layoutPostAPropertyBinding.tvMeasurement6.setText(editData.getStreetwidth());
            layoutPostAPropertyBinding.tvStreetUnit.setText(editData.getStreetwidthunit());
            layoutPostAPropertyBinding.edtDetailsContact.setText(editData.getDescription());
            layoutPostAPropertyBinding.tvNoOfBuilding.setText(editData.getExtrabuildingno());
            layoutPostAPropertyBinding.tvNoOfShowroom.setText(editData.getExtrashowroomno());
            layoutPostAPropertyBinding.tvRevenue.setText(editData.getRevenue());
            layoutPostAPropertyBinding.tvRevenue.setText(editData.getRevenue());
            layoutPostAPropertyBinding.tvLength.setText(editData.getLength());
            layoutPostAPropertyBinding.tvWidth.setText(editData.getWidth());
            layoutPostAPropertyBinding.tvLengthUnit.setText(editData.getLengthUnit());
            layoutPostAPropertyBinding.tvWidthUnit.setText(editData.getWidthUnit());
            layoutPostAPropertyBinding.ivNotificationToggle2.setChecked(editData.getBalcony());
            layoutPostAPropertyBinding.ivNotificationToggle3.setChecked(editData.getGarden());
            layoutPostAPropertyBinding.ivNotificationToggle4.setChecked(editData.getParking());
            layoutPostAPropertyBinding.ivNotificationToggle5.setChecked(editData.getModularkitchen());
            layoutPostAPropertyBinding.tbStore.setChecked(editData.isStore());
            layoutPostAPropertyBinding.tbLift.setChecked(editData.isLift());
            layoutPostAPropertyBinding.tbFurnished.setChecked(editData.isFurnished());
            layoutPostAPropertyBinding.tbAirConditioning.setChecked(editData.isAircondition());
            layoutPostAPropertyBinding.tbDuplex.setChecked(editData.isDuplex());

        }else {
           /* layoutPostAPropertyBinding.tvMeasurement4.setText("0.00");
            layoutPostAPropertyBinding.tvMeasurement3.setText("0.00");
            layoutPostAPropertyBinding.tvLength.setText("0.00");
            layoutPostAPropertyBinding.tvWidth.setText("0.00");
            layoutPostAPropertyBinding.tvMeasurement6.setText("0.00");
            layoutPostAPropertyBinding.tvRevenue.setText("0.00");*/
        }
    }

    private void selectrevenue() {
        data = new ArrayList<>();
        data.add("Select Revenue");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        data.add("10");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvStreetView2.setAdapter(dataAdapter);
    }

    private void selectNoOfShowrooms() {
        showRoomsData= DataGenerator.selectNoOfShoowRooms();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, showRoomsData){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvBuildingMeter.setAdapter(dataAdapter);
    }

    private void selectNoOfBuilding() {

        buildingData=DataGenerator.selectNoOfBuilding();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, buildingData){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvBuilding.setAdapter(dataAdapter);
    }

    private void streetWidth() {
        String[] data = DataGenerator.getMeasurement();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvMeasurement7.setAdapter(dataAdapter);
    }

    private void selectStreetView() {
        data = new ArrayList<>();
        data.add("Select Street View");
        data.add("North");
        data.add("South");
        data.add("East");
        data.add("West");
        data.add("North-East");
        data.add("North-West");
        data.add("South-East");
        data.add("South-West");
        data.add("3 Streets");
        data.add("4 Streets");
        data.add("Not Defined");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvStreetView.setAdapter(dataAdapter);

    }

    private void getLengthUnit() {
        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("M2");
        data.add("CM2");
        data.add("Inch");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.spLength.setAdapter(dataAdapter);
    }

    private void getWidthUnit() {
        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("M2");
        data.add("CM2");
        data.add("Inch");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.spWidth.setAdapter(dataAdapter);
    }

    private void getPlotSizeData() {
        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("M2");
        data.add("CM2");
        data.add("Inch");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvMeasurement5.setAdapter(dataAdapter);
    }

    private void getBuiltinsizeData() {
        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("M2");
        data.add("CM2");
        data.add("Inch");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvMeasurement2.setAdapter(dataAdapter);
    }

    private void selectBuiltYear() {
        ArrayList<String> years = new ArrayList<String>();
        years.add("Any");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1000; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;

            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutPostAPropertyBinding.tvYearBuilt.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:

                if (validation())
                    collectData();

                break;

            case R.id.ivBack:
                getActivity().finish();
                break;

            case R.id.tvBuiltUnit:
                isBuildUnit = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvMeasurement2.performClick();
                break;
            case R.id.tvPlotUnit:
                isPlotUnit = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvMeasurement5.performClick();
                break;
            case R.id.tvYearBuiltNo:
                isYear = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvYearBuilt.performClick();
                break;
            case R.id.tvStreet:
                isStreet = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvStreetView.performClick();
                break;
            case R.id.tvStreetUnit:
                isStreetUnit = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvMeasurement7.performClick();
                break;
            case R.id.tvNoOfBuilding:
                isNoOfBuilding = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvBuilding.performClick();
                break;
            case R.id.tvNoOfShowroom:
                isShowroom = true;
                hideKeyboard();
                layoutPostAPropertyBinding.tvBuildingMeter.performClick();
                break;
            case R.id.tvLengthUnit:
                isLengthUnit = true;
                hideKeyboard();
                layoutPostAPropertyBinding.spLength.performClick();
                break;
            case R.id.tvWidthUnit:
                isWidthUnit = true;
                hideKeyboard();
                layoutPostAPropertyBinding.spWidth.performClick();
                break;


        }
    }

    private void collectData() {

        bundle.putString("builtinsize", layoutPostAPropertyBinding.tvMeasurement3.getText().toString());
        bundle.putString("plotsize", layoutPostAPropertyBinding.tvMeasurement4.getText().toString());
        bundle.putString("streetwidth", layoutPostAPropertyBinding.tvMeasurement6.getText().toString());
        bundle.putString("description", layoutPostAPropertyBinding.edtDetailsContact.getText().toString());
        bundle.putString("builtinsizeunit",  layoutPostAPropertyBinding.tvBuiltUnit.getText().toString());
        bundle.putString("plotsizeunit", layoutPostAPropertyBinding.tvPlotUnit.getText().toString());
        bundle.putString("builtinyear", layoutPostAPropertyBinding.tvYearBuiltNo.getText().toString());
        bundle.putString("streetview", layoutPostAPropertyBinding.tvStreet.getText().toString());
        bundle.putString("streetwidthunit",  layoutPostAPropertyBinding.tvStreetUnit.getText().toString());
        bundle.putString("extrabuilding", layoutPostAPropertyBinding.tvNoOfBuilding.getText().toString());
        bundle.putString("extrashowroom", layoutPostAPropertyBinding.tvNoOfShowroom.getText().toString());
        bundle.putString("lengthUnit",layoutPostAPropertyBinding.tvLengthUnit.getText().toString());
        bundle.putString("widthUnit",layoutPostAPropertyBinding.tvWidthUnit.getText().toString());
        bundle.putString("length",layoutPostAPropertyBinding.tvLength.getText().toString());
        bundle.putString("width",layoutPostAPropertyBinding.tvWidth.getText().toString());
        bundle.putString("revenue", layoutPostAPropertyBinding.tvRevenue.getText().toString());
        bundle.putBoolean("balcony", layoutPostAPropertyBinding.ivNotificationToggle2.isChecked());
        bundle.putBoolean("garden", layoutPostAPropertyBinding.ivNotificationToggle3.isChecked());
        bundle.putBoolean("parking", layoutPostAPropertyBinding.ivNotificationToggle4.isChecked());
        bundle.putBoolean("modulerkitchen", layoutPostAPropertyBinding.ivNotificationToggle5.isChecked());
        bundle.putBoolean("store", layoutPostAPropertyBinding.tbStore.isChecked());
        bundle.putBoolean("lift", layoutPostAPropertyBinding.tbLift.isChecked());
        bundle.putBoolean("duplex", layoutPostAPropertyBinding.tbDuplex.isChecked());
        bundle.putBoolean("furnished", layoutPostAPropertyBinding.tbFurnished.isChecked());
        bundle.putBoolean("airConditioning", layoutPostAPropertyBinding.tbAirConditioning.isChecked());
        if(editData!=null){
            bundle.putSerializable("Data",editData);
        }
        Add_Specification add_specification = new Add_Specification();
        add_specification.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_specification).addToBackStack("").commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.tvMeasurement2:
                builtinsizeunit = layoutPostAPropertyBinding.tvMeasurement2.getSelectedItem().toString();
                if(isBuildUnit)
                    layoutPostAPropertyBinding.tvBuiltUnit.setText(builtinsizeunit);
                break;

            case R.id.tvMeasurement5:
                plotsizeUnit = layoutPostAPropertyBinding.tvMeasurement5.getSelectedItem().toString();
                if(isPlotUnit)
                    layoutPostAPropertyBinding.tvPlotUnit.setText(plotsizeUnit);
                break;

            case R.id.tvYearBuilt:
                tearBuilt = layoutPostAPropertyBinding.tvYearBuilt.getSelectedItem().toString();
                if(isYear)
                    layoutPostAPropertyBinding.tvYearBuiltNo.setText(tearBuilt);
                break;

            case R.id.tvStreetView:
                streetView = layoutPostAPropertyBinding.tvStreetView.getSelectedItem().toString();
                if(isStreet)
                    layoutPostAPropertyBinding.tvStreet.setText(streetView);
                break;

            case R.id.tvBuilding:
                extraBuildingNumber = layoutPostAPropertyBinding.tvBuilding.getSelectedItem().toString();
                if(isNoOfBuilding)
                    layoutPostAPropertyBinding.tvNoOfBuilding.setText(extraBuildingNumber);
                break;

            case R.id.tvBuildingMeter:
                extraShowroomNumber = layoutPostAPropertyBinding.tvBuildingMeter.getSelectedItem().toString();
                if(isShowroom)
                    layoutPostAPropertyBinding.tvNoOfShowroom.setText(extraShowroomNumber);
                break;

            case R.id.spLength:
                measureLength = layoutPostAPropertyBinding.spLength.getSelectedItem().toString();
                if(isLengthUnit)
                    layoutPostAPropertyBinding.tvLengthUnit.setText(measureLength);
                break;

            case R.id.spWidth:
                measureWidth = layoutPostAPropertyBinding.spWidth.getSelectedItem().toString();
                if(isWidthUnit)
                    layoutPostAPropertyBinding.tvWidthUnit.setText(measureWidth);
                break;

//            case R.id.tvStreetView2:
//                revenue = layoutPostAPropertyBinding.tvStreetView2.getSelectedItem().toString();
//                if(isRevenue)
//                    layoutPostAPropertyBinding.tvRevenue.setText(revenue);
//                break;

            case R.id.tvMeasurement7:
                streetWidthUnit = layoutPostAPropertyBinding.tvMeasurement7.getSelectedItem().toString();
                if(isStreetUnit)
                    layoutPostAPropertyBinding.tvStreetUnit.setText(streetWidthUnit);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validation() {
     /*   if (layoutPostAPropertyBinding.tvMeasurement3 != null && layoutPostAPropertyBinding.tvMeasurement3.getText().toString().isEmpty()) {
//            layoutPostAPropertyBinding.tvMeasurement3.requestFocus();
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Built Size");
//            layoutPostAPropertyBinding.tvMeasurement3.setError("Please enter Built Size");
            return false;
//        } else if (layoutPostAPropertyBinding.tvMeasurement3 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement3.getText().toString()))) {
////            layoutPostAPropertyBinding.tvMeasurement3.requestFocus();
////            layoutPostAPropertyBinding.tvMeasurement3.setError("Please enter a valid Built Size");
//            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter built size");
//            return false;
//        }
        }else if (layoutPostAPropertyBinding.tvBuiltUnit.getText().toString().isEmpty() || layoutPostAPropertyBinding.tvBuiltUnit.getText().toString().equalsIgnoreCase("Select Unit")) {
//            ((TextView) layoutPostAPropertyBinding.tvMeasurement2.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select Built Size Unit");
            return false;
        } else*/ if (layoutPostAPropertyBinding.tvMeasurement4 != null && layoutPostAPropertyBinding.tvMeasurement4.getText().toString().isEmpty()) {
//            layoutPostAPropertyBinding.tvMeasurement4.requestFocus();
//            layoutPostAPropertyBinding.tvMeasurement4.setError("Please enter Plot Size ");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Plot Size");

            return false;
//        } else if (layoutPostAPropertyBinding.tvMeasurement4 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement4.getText().toString()))) {
//            layoutPostAPropertyBinding.tvMeasurement4.requestFocus();
//            layoutPostAPropertyBinding.tvMeasurement4.setError("Please enter a valid plot size ");
//            return false;
        } else if (layoutPostAPropertyBinding.tvPlotUnit.getText().toString().isEmpty() || layoutPostAPropertyBinding.tvPlotUnit.getText().toString().equalsIgnoreCase("Select Unit")) {
//            ((TextView) layoutPostAPropertyBinding.tvMeasurement5.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select Plot Size Unit");
            return false;
        } /*else if (layoutPostAPropertyBinding.tvYearBuiltNo.getText().toString().isEmpty()){
//            ((TextView) layoutPostAPropertyBinding.tvYearBuilt.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select Year Built");

            return false;
        } else if (layoutPostAPropertyBinding.tvStreet.getText().toString().isEmpty()) {
//            ((TextView) layoutPostAPropertyBinding.tvStreetView.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select Street View");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement6 != null && layoutPostAPropertyBinding.tvMeasurement6.getText().toString().isEmpty()) {
//            layoutPostAPropertyBinding.tvMeasurement6.requestFocus();
//            layoutPostAPropertyBinding.tvMeasurement6.setError("Please enter Street Width");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Street Width");
            return false;
//        } else if (layoutPostAPropertyBinding.tvMeasurement6 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement6.getText().toString()))) {
//            layoutPostAPropertyBinding.tvMeasurement6.requestFocus();
//            layoutPostAPropertyBinding.tvMeasurement6.setError("Please enter Street Width");
//            return false;
        } else if (layoutPostAPropertyBinding.tvStreetUnit.getText().toString().isEmpty() || layoutPostAPropertyBinding.tvStreetUnit.getText().toString().equalsIgnoreCase("Select Street View")) {
//            ((TextView) layoutPostAPropertyBinding.tvMeasurement7.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Street Width Unit");
            return false;
        } */else if (layoutPostAPropertyBinding.edtDetailsContact != null && layoutPostAPropertyBinding.edtDetailsContact.getText().toString().isEmpty()) {
//            layoutPostAPropertyBinding.edtDetailsContact.requestFocus();
//          layoutPostAPropertyBinding.edtDetailsContact.setError("Please enter Description ");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Description");
            return false;
        } else if (layoutPostAPropertyBinding.edtDetailsContact != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.edtDetailsContact.getText().toString()))) {
//            layoutPostAPropertyBinding.edtDetailsContact.requestFocus();
//            layoutPostAPropertyBinding.edtDetailsContact.setError("Please enter a Valid Description");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Description");
            return false;
        }
        /*if(layoutPostAPropertyBinding.ivNotificationToggle.isChecked()){
            if(layoutPostAPropertyBinding.tvNoOfBuilding.getText().toString().isEmpty() || layoutPostAPropertyBinding.tvNoOfBuilding.getText().toString().equalsIgnoreCase("Select No of Buildings")){
                MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select no. of building");
                return false;
            }else if(layoutPostAPropertyBinding.tvNoOfShowroom.getText().toString().isEmpty() || layoutPostAPropertyBinding.tvNoOfShowroom.getText().toString().equalsIgnoreCase("Select No of Showrooms")){
                MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select no. of showroom");
                return false;
            }
        }*/
        /*else if (layoutPostAPropertyBinding.tvNoOfBuilding.getText().toString().isEmpty()) {
//            ((TextView) layoutPostAPropertyBinding.tvBuilding.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select No. of Building");
            return false;
        } else if (layoutPostAPropertyBinding.tvNoOfShowroom.getText().toString().isEmpty()) {
//            ((TextView) layoutPostAPropertyBinding.tvBuildingMeter.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please select No. of Showroom");

            return false;
        } else if (layoutPostAPropertyBinding.tvRevenue.getText().toString().isEmpty()) {
//            ((TextView) layoutPostAPropertyBinding.tvStreetView2.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Please enter Revenue");
            return false;
        }*/
        return true;
    }


    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(layoutPostAPropertyBinding.clPostRoot.getWindowToken(), 0);
    }


}
