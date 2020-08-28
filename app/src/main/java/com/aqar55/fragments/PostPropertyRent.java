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
import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.LayoutPostAPropertyBinding;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyValidator;

public class PostPropertyRent extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private LayoutPostAPropertyBinding layoutPostAPropertyBinding;
    private View view;
    private List<String> data;
    private Bundle bundle = new Bundle();
    private String builtinsizeunit, plotsizeUnit, tearBuilt, streetView, streetWidthUnit, extraBuildingNumber, extraShowroomNumber, revenue;
    private boolean balcony, garden, parking, modularkitchen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutPostAPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.layout_post_a_property, container, false);
        view = layoutPostAPropertyBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(layoutPostAPropertyBinding.getRoot(), "Make sure you are connected to Internet.");
        }


        layoutPostAPropertyBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutPostAPropertyBinding.ivBack.setOnClickListener(this);
        layoutPostAPropertyBinding.tvBuiltUnit.setOnClickListener(this);

        layoutPostAPropertyBinding.tvMeasurement2.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvMeasurement5.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvYearBuilt.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvStreetView.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvBuilding.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvBuildingMeter.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvStreetView2.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvYearBuilt.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvMeasurement7.setOnItemSelectedListener(this);
        layoutPostAPropertyBinding.tvStreetView2.setOnItemSelectedListener(this);

        getBuiltinsizeData();
        getPlotSizeData();
        selectBuiltYear();
        selectStreetView();
        streetWidth();
        selectNoOfBuilding();
        selectNoOfShowrooms();
        selectrevenue();

        bundle = getArguments();


        return view;

    }

    private void selectrevenue() {
        data = new ArrayList<>();
        data.add("Enter no.");
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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvStreetView2.setAdapter(dataAdapter);
    }

    private void selectNoOfShowrooms() {
        data = new ArrayList<>();
        data.add("Select No of Showrooms");
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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvBuildingMeter.setAdapter(dataAdapter);
    }

    private void selectNoOfBuilding() {
        data = new ArrayList<>();
        data.add("Apartment or Room");
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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvBuilding.setAdapter(dataAdapter);
    }

    private void streetWidth() {

        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("Meter");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvStreetView.setAdapter(dataAdapter);

    }

    private void getPlotSizeData() {
        data = new ArrayList<>();
        data.add("Select Unit");
        data.add("M2");
        data.add("CM2");
        data.add("Inch");
        data.add("Feet");
        data.add("Yard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutPostAPropertyBinding.tvMeasurement2.setAdapter(dataAdapter);
    }

    private void selectBuiltYear() {
        ArrayList<String> years = new ArrayList<String>();
        years.add("Select Built Year");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1989; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);

        layoutPostAPropertyBinding.tvYearBuilt.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:

                if (validation()) {
                    getToggleButtonData();
                }
                break;

            case R.id.ivBack:
                getActivity().finish();
                break;
        }
    }

    private void getToggleButtonData() {
        if (layoutPostAPropertyBinding.ivNotificationToggle2.isChecked()) {
            balcony = true;
        } else if (!layoutPostAPropertyBinding.ivNotificationToggle2.isChecked()) {
            balcony = false;
        } else if (layoutPostAPropertyBinding.ivNotificationToggle3.isChecked()) {
            garden = true;
        } else if (!layoutPostAPropertyBinding.ivNotificationToggle3.isChecked()) {
            garden = false;
        } else if (layoutPostAPropertyBinding.ivNotificationToggle4.isChecked()) {
            parking = true;
        } else if (!layoutPostAPropertyBinding.ivNotificationToggle4.isChecked()) {
            parking = false;
        } else if (layoutPostAPropertyBinding.ivNotificationToggle5.isChecked()) {
            modularkitchen = true;
        } else if (!layoutPostAPropertyBinding.ivNotificationToggle5.isChecked()) {
            modularkitchen = false;
        }
        collectData();
    }

    private void collectData() {

        bundle.putString("builtinsize", layoutPostAPropertyBinding.tvMeasurement3.getText().toString());
        bundle.putString("plotsize", layoutPostAPropertyBinding.tvMeasurement4.getText().toString());
        bundle.putString("streetwidth", layoutPostAPropertyBinding.tvMeasurement6.getText().toString());
        bundle.putString("description", layoutPostAPropertyBinding.edtDetailsContact.getText().toString());
        bundle.putString("builtinsizeunit", builtinsizeunit);
        bundle.putString("plotsizeunit", plotsizeUnit);
        bundle.putString("builtinyear", tearBuilt);
        bundle.putString("streetview", streetView);
        bundle.putString("streetwidthunit", streetWidthUnit);
        bundle.putString("extrabuilding", extraBuildingNumber);
        bundle.putString("extrashowroom", extraShowroomNumber);
        bundle.putString("revenue", revenue);
        bundle.putBoolean("balcony", balcony);
        bundle.putBoolean("garden", garden);
        bundle.putBoolean("parking", parking);
        bundle.putBoolean("modulerkitchen", modularkitchen);


        AddSpecificationRent add_specification = new AddSpecificationRent();
        add_specification.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, add_specification).addToBackStack("").commit();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.tvMeasurement2:
                builtinsizeunit = layoutPostAPropertyBinding.tvMeasurement2.getSelectedItem().toString();
                break;
            case R.id.tvMeasurement5:
                plotsizeUnit = layoutPostAPropertyBinding.tvMeasurement5.getSelectedItem().toString();
                break;
            case R.id.tvYearBuilt:
                tearBuilt = layoutPostAPropertyBinding.tvYearBuilt.getSelectedItem().toString();
                break;
            case R.id.tvStreetView:
                streetView = layoutPostAPropertyBinding.tvStreetView.getSelectedItem().toString();
                break;
            case R.id.tvMeasurement7:
                streetWidthUnit = layoutPostAPropertyBinding.tvMeasurement7.getSelectedItem().toString();
                break;
            case R.id.tvBuilding:
                extraBuildingNumber = layoutPostAPropertyBinding.tvBuilding.getSelectedItem().toString();
                break;
            case R.id.tvBuildingMeter:
                extraShowroomNumber = layoutPostAPropertyBinding.tvBuildingMeter.getSelectedItem().toString();
                break;
            case R.id.tvStreetView2:
                revenue = layoutPostAPropertyBinding.tvStreetView2.getSelectedItem().toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validation() {
        if (layoutPostAPropertyBinding.tvMeasurement3 != null && layoutPostAPropertyBinding.tvMeasurement3.getText().toString().isEmpty()) {
            layoutPostAPropertyBinding.tvMeasurement3.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement3.setError("Please enter Property Title ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement3 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement3.getText().toString()))) {
            layoutPostAPropertyBinding.tvMeasurement3.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement3.setError("Please enter a valid Property Title ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement2.getSelectedItem().toString().equalsIgnoreCase("Select Unit")) {
            ((TextView) layoutPostAPropertyBinding.tvMeasurement2.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement4 != null && layoutPostAPropertyBinding.tvMeasurement4.getText().toString().isEmpty()) {
            layoutPostAPropertyBinding.tvMeasurement4.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement4.setError("Please Plot Size ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement4 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement4.getText().toString()))) {
            layoutPostAPropertyBinding.tvMeasurement4.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement4.setError("Please enter a  valid plot size ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement5.getSelectedItem().toString().equalsIgnoreCase("Select Unit")) {
            ((TextView) layoutPostAPropertyBinding.tvMeasurement5.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvYearBuilt.getSelectedItem().toString().equalsIgnoreCase("Select Built Year")) {
            ((TextView) layoutPostAPropertyBinding.tvYearBuilt.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvStreetView.getSelectedItem().toString().equalsIgnoreCase("Select Street View")) {
            ((TextView) layoutPostAPropertyBinding.tvStreetView.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement6 != null && layoutPostAPropertyBinding.tvMeasurement6.getText().toString().isEmpty()) {
            layoutPostAPropertyBinding.tvMeasurement6.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement6.setError("Please enter Street Width ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement6 != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.tvMeasurement6.getText().toString()))) {
            layoutPostAPropertyBinding.tvMeasurement6.requestFocus();
            layoutPostAPropertyBinding.tvMeasurement6.setError("Please enter a Street Width ");
            return false;
        } else if (layoutPostAPropertyBinding.tvMeasurement7.getSelectedItem().toString().equalsIgnoreCase("Select Unit")) {
            ((TextView) layoutPostAPropertyBinding.tvMeasurement7.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.edtDetailsContact != null && layoutPostAPropertyBinding.edtDetailsContact.getText().toString().isEmpty()) {
            layoutPostAPropertyBinding.edtDetailsContact.requestFocus();
            layoutPostAPropertyBinding.edtDetailsContact.setError("Please enter Description ");
            return false;
        } else if (layoutPostAPropertyBinding.edtDetailsContact != null && !(MyValidator.isValidFullName(layoutPostAPropertyBinding.edtDetailsContact.getText().toString()))) {
            layoutPostAPropertyBinding.edtDetailsContact.requestFocus();
            layoutPostAPropertyBinding.edtDetailsContact.setError("Please enter a Valid Description");
            return false;
        } else if (layoutPostAPropertyBinding.tvBuilding.getSelectedItem().toString().equalsIgnoreCase("Select No Buildings")) {
            ((TextView) layoutPostAPropertyBinding.tvBuilding.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvBuildingMeter.getSelectedItem().toString().equalsIgnoreCase("Select No Showrooms")) {
            ((TextView) layoutPostAPropertyBinding.tvBuildingMeter.getSelectedView()).setError("Error message");
            return false;
        } else if (layoutPostAPropertyBinding.tvStreetView2.getSelectedItem().toString().equalsIgnoreCase("Select Revenue")) {
            ((TextView) layoutPostAPropertyBinding.tvStreetView2.getSelectedView()).setError("Error message");
            return false;
        }
        return true;
    }


}

