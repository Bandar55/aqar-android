package com.aqar55.fragments;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import com.aqar55.R;
import com.aqar55.activitys.Post_A_Property_Activity;
import com.aqar55.databinding.FragmentPostPropertyForRentBinding;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.GetCategoryList;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyDialog;
import com.aqar55.utill.MyValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_Property_For_Rent extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentPostPropertyForRentBinding fragmentPostPropertyForRentBinding;
    private View view;
    private List<String> data;
    private int checkedRadioButtonId, checkedRadioButtonIdForFamily;
    private String category, bedrooms, bathrooms, kitchen, floor;
    private String status="active";
    private boolean isCategory,isBedroom,isBathroom,isKitchen,isFloor,isStatus;
    private ManageActiveProperty.Data editData;
    private String[] bathRoom;
    private String[] floorData;
    private String[] bedRoomData;
    private String[] kitchenData;
    private String[] statusData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPostPropertyForRentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post__property__for__rent, container, false);

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(view, "Make sure you are connected to Internet.");
        }

        view = fragmentPostPropertyForRentBinding.getRoot();
        fragmentPostPropertyForRentBinding.tvSavePostedProperty.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvAppLanguage.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.tvNoOfBedRooms.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.tvNoOfBathrooms.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.tvNoOfKitchen.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.tvFloor.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.spinnerStatus.setOnItemSelectedListener(this);
        fragmentPostPropertyForRentBinding.tvSelectStatus.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbResidential.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbCommercial.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbBoth.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbFamily.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbSingle.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.rbBothAvailable.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvSelectCategory.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvBedrooms.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvBathrooms.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvKitchen.setOnClickListener(this);
        fragmentPostPropertyForRentBinding.tvSelectedFloor.setOnClickListener(this);


        getCategory();
        selectBedRooms();
        getStatus();
        selectBashroom();
        selectKitchen();
        selectFloor();

        getIntentData();

        return view;
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
            if (editData != null) {
                fragmentPostPropertyForRentBinding.tvPropertyTitle.setText(editData.getTitle());
                fragmentPostPropertyForRentBinding.tvSelectCategory.setText(editData.getCategory());
                String purpose = editData.getPurpose();
                switch (purpose){
                    case "commercial":
                        fragmentPostPropertyForRentBinding.rbResidential.setChecked(true);
                        break;
                    case "residential":
                        fragmentPostPropertyForRentBinding.rbCommercial.setChecked(true);
                        break;
                    case "both":
                        fragmentPostPropertyForRentBinding.rbBoth.setChecked(true);
                        break;
                }
                String available  = editData.getAvailable();
                switch (available){
                    case "family":
                        fragmentPostPropertyForRentBinding.rbFamily.setChecked(true);
                        break;
                    case "single":
                        fragmentPostPropertyForRentBinding.rbSingle.setChecked(true);
                        break;
                    case "both":
                        fragmentPostPropertyForRentBinding.rbBothAvailable.setChecked(true);
                        break;
                }
                fragmentPostPropertyForRentBinding.tvBedrooms.setText(editData.getBedrooms());
                fragmentPostPropertyForRentBinding.tvBathrooms.setText(editData.getBathrooms());
                fragmentPostPropertyForRentBinding.tvKitchen.setText(editData.getKitchens());
                fragmentPostPropertyForRentBinding.tvSelectedFloor.setText(editData.getFloor());
                fragmentPostPropertyForRentBinding.tvSelectStatus.setText(editData.getStatus());

            }

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:
                checkedRadioButtonId = fragmentPostPropertyForRentBinding.rgPurpose.getCheckedRadioButtonId();
                checkedRadioButtonIdForFamily = fragmentPostPropertyForRentBinding.rgAvailableForU.getCheckedRadioButtonId();

                if (validation()) {
                    collectData();
                }
                break;
            case R.id.rbResidential:
                hideKeyboard();
                break;
            case R.id.rbCommercial:
                hideKeyboard();
                break;
            case R.id.rbBoth:
                hideKeyboard();
                break;
            case R.id.rbFamily:
                hideKeyboard();
                break;
            case R.id.rbSingle:
                hideKeyboard();
                break;
            case R.id.rbBothAvailable:
                hideKeyboard();
                break;
            case R.id.tvSelectCategory:
                isCategory = true;
                hideKeyboard();
                fragmentPostPropertyForRentBinding.tvAppLanguage.performClick();
                break;
            case R.id.tvBedrooms:
                isBedroom = true;
                hideKeyboard();
                fragmentPostPropertyForRentBinding.tvNoOfBedRooms.performClick();
                break;
            case R.id.tvBathrooms:
                hideKeyboard();
                isBathroom = true;
                fragmentPostPropertyForRentBinding.tvNoOfBathrooms.performClick();
                break;
            case R.id.tvKitchen:
                hideKeyboard();
                isKitchen = true;
                fragmentPostPropertyForRentBinding.tvNoOfKitchen.performClick();
                break;
            case R.id.tvSelectedFloor:
                hideKeyboard();
                isFloor = true;
                fragmentPostPropertyForRentBinding.tvFloor.performClick();
                break;


            case R.id.tvSelectStatus:
                hideKeyboard();
                isStatus=true;
                fragmentPostPropertyForRentBinding.spinnerStatus.performClick();

                break;
        }
    }

    private void selectFloor() {
        floorData=DataGenerator.selectNoOfFoor();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, floorData){
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

        fragmentPostPropertyForRentBinding.tvFloor.setAdapter(dataAdapter);
    }

    private void selectBashroom() {
        bathRoom= DataGenerator.selectNoOfBathRoom();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bathRoom){
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

        fragmentPostPropertyForRentBinding.tvNoOfBathrooms.setAdapter(dataAdapter);
    }

    private void selectBedRooms() {
        bedRoomData=DataGenerator.selectNoOfBedRooms();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bedRoomData){
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

        fragmentPostPropertyForRentBinding.tvNoOfBedRooms.setAdapter(dataAdapter);
    }

    private void getStatus() {
        statusData=DataGenerator.getStatus();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, statusData){
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
        fragmentPostPropertyForRentBinding.spinnerStatus.setAdapter(dataAdapter);
    }

    private void selectKitchen() {
        kitchenData=DataGenerator.selectNoOfKitchen();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kitchenData){
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

        fragmentPostPropertyForRentBinding.tvNoOfKitchen.setAdapter(dataAdapter);
    }

    private void getCategory() {

        final List<String> categories = new ArrayList<>();
        categories.add("Select Category");

        MyDialog.getInstance(getContext()).showDialog(getActivity());
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetCategoryList> getCategoryListCall = api.getPropertyCategory();

        getCategoryListCall.enqueue(new Callback<GetCategoryList>() {
            @Override
            public void onResponse(@NonNull Call<GetCategoryList> call, @NonNull Response<GetCategoryList> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.body() != null) {
                        if (response.body().getResponseMessage().equalsIgnoreCase("property category list found")) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                categories.add(response.body().getData().get(i).getName());


                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCategoryList> call, @NonNull Throwable t) {
                MyDialog.getInstance(getContext()).hideDialog();
            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories){
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

        fragmentPostPropertyForRentBinding.tvAppLanguage.setAdapter(dataAdapter);

    }

    private boolean validation() {
        if (fragmentPostPropertyForRentBinding.tvPropertyTitle != null && fragmentPostPropertyForRentBinding.tvPropertyTitle.getText().toString().isEmpty()) {
            fragmentPostPropertyForRentBinding.tvPropertyTitle.requestFocus();
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please enter Property Title ");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvPropertyTitle != null && !(MyValidator.isValidFullName(fragmentPostPropertyForRentBinding.tvPropertyTitle.getText().toString()))) {
            fragmentPostPropertyForRentBinding.tvPropertyTitle.requestFocus();
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please enter Property Title ");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvSelectCategory.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvSelectCategory.toString().equalsIgnoreCase("Select Category")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvAppLanguage.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Category");
            return false;
        } else if (checkedRadioButtonId == -1) {
//            Toast.makeText(getContext(), "Plase Select purpose", Toast.LENGTH_SHORT).show();
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select purpose");
            return false;
        } else if (checkedRadioButtonIdForFamily == -1) {
//            Toast.makeText(getContext(), "Plase select Available for", Toast.LENGTH_SHORT).show();
            fragmentPostPropertyForRentBinding.rbBothAvailable.setError("Please select Available for");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select purpose");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvBedrooms.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvBedrooms.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Number of Bedrooms");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvBathrooms.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvBathrooms.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Number of Bathrooms");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvKitchen.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvKitchen.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfKitchen.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Number of Kitchens");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvSelectedFloor.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvSelectedFloor.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvFloor.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Floor");
            return false;
        } else if (fragmentPostPropertyForRentBinding.tvSelectStatus.getText().toString().isEmpty() || fragmentPostPropertyForRentBinding.tvSelectStatus.getText().toString().equalsIgnoreCase("Select status")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvFloor.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForRentBinding.getRoot(), "Please Select Floor");
            return false;
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fragmentPostPropertyForRentBinding.tvPropertyTitle.clearFocus();

        switch (adapterView.getId()) {
            case R.id.tvAppLanguage:
                category = fragmentPostPropertyForRentBinding.tvAppLanguage.getSelectedItem().toString();
                if(isCategory)
                    fragmentPostPropertyForRentBinding.tvSelectCategory.setText(category);
                break;

            case R.id.tvNoOfBedRooms:
                bedrooms = fragmentPostPropertyForRentBinding.tvNoOfBedRooms.getSelectedItem().toString();
                if(isBedroom)
                    fragmentPostPropertyForRentBinding.tvBedrooms.setText(bedrooms);
                break;

            case R.id.tvNoOfBathrooms:
                bathrooms = fragmentPostPropertyForRentBinding.tvNoOfBathrooms.getSelectedItem().toString();
                if(isBathroom)
                    fragmentPostPropertyForRentBinding.tvBathrooms.setText(bathrooms);
                break;

            case R.id.tvNoOfKitchen:

                kitchen = fragmentPostPropertyForRentBinding.tvNoOfKitchen.getSelectedItem().toString();
                if(isKitchen)
                    fragmentPostPropertyForRentBinding.tvKitchen.setText(kitchen);
                break;

            case R.id.tvFloor:

                floor = fragmentPostPropertyForRentBinding.tvFloor.getSelectedItem().toString();
                if(isFloor)
                    fragmentPostPropertyForRentBinding.tvSelectedFloor.setText(floor);
                break;
            case R.id.spinner_status:
                status=fragmentPostPropertyForRentBinding.spinnerStatus.getSelectedItem().toString();
                if(isStatus)
                    fragmentPostPropertyForRentBinding.tvSelectStatus.setText(status);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void collectData() {

        RadioButton radioButton = fragmentPostPropertyForRentBinding.rgPurpose.findViewById(checkedRadioButtonId);
        RadioButton radioButton2 = fragmentPostPropertyForRentBinding.rgAvailableForU.findViewById(checkedRadioButtonIdForFamily);

        Bundle bundle = new Bundle();
        bundle.putString("title", fragmentPostPropertyForRentBinding.tvPropertyTitle.getText().toString());
        bundle.putString("type", "rent");
        bundle.putString("purpose", radioButton.getText().toString());
        bundle.putString("availablefor", radioButton2.getText().toString());
        bundle.putString("category",  fragmentPostPropertyForRentBinding.tvSelectCategory.getText().toString());
        bundle.putString("bedrooms", fragmentPostPropertyForRentBinding.tvBedrooms.getText().toString());
        bundle.putString("bathrooms", fragmentPostPropertyForRentBinding.tvBathrooms.getText().toString());
        bundle.putString("kitchen", fragmentPostPropertyForRentBinding.tvKitchen.getText().toString());
        bundle.putString("floor", fragmentPostPropertyForRentBinding.tvSelectedFloor.getText().toString());
        bundle.putString("status",  fragmentPostPropertyForRentBinding.tvSelectStatus.getText().toString());

        if(editData!=null)
            bundle.putSerializable("Data",editData);

        Intent intent = new Intent(getContext(), Post_A_Property_Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);

//        Intent intent2 = new Intent("sale-data");
//        intent2.putExtras(bundle);
//        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent2);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fragmentPostPropertyForRentBinding.clForRent.getWindowToken(), 0);
    }

}
