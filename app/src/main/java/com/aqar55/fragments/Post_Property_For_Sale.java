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
import com.aqar55.activitys.Add_Post__Items_Activity;
import com.aqar55.activitys.Post_A_Property_Activity;
import com.aqar55.databinding.FragmentPostPropertyForSaleBinding;
import com.aqar55.dialogs.SimpleCustomDialog;
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

public class Post_Property_For_Sale extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "Post_Property_For_Sale";
    private FragmentPostPropertyForSaleBinding fragmentPostPropertyForSaleBinding;
    private View view;
    private SimpleCustomDialog cdd;
    private List<String> data;
    private String[] bedRoom;
    private String[] floorData;
    private String[] bedRoomData;
    private String[] kitchenData;
    private String[] statusData;
     private int checkedRadioButtonId, checkedRadioButtonIdForFamily;
    private String category, bedrooms, bathrooms, kitchen, floor;
    private String status="";
    private boolean isCategory,isBedroom,isBathroom,isKitchen,isFloor,isStatus;
    private ManageActiveProperty.Data editData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPostPropertyForSaleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post__property__for__sale, container, false);
        view = fragmentPostPropertyForSaleBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            //MyApplication.makeASnack(view, "Make sure you are connected to Internet.");
        }

        fragmentPostPropertyForSaleBinding.tvSavePostedProperty.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvAppLanguage.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.tvNoOfKitchen.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.tvFloor.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.constrint.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbResidential.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbCommercial.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbBoth.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbFamily.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbSingle.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.rbBothAvailable.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvCategory.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvSelectBedrooms.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvSelectBathrooms.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvKitchen.setOnClickListener(this);
        fragmentPostPropertyForSaleBinding.tvSelectFloor.setOnClickListener(this);

        fragmentPostPropertyForSaleBinding.spinnerStatus.setOnItemSelectedListener(this);
        fragmentPostPropertyForSaleBinding.tvSelectStatus.setOnClickListener(this);
        selectKitchen();

        getCategory();
        selectBedRooms();
        selectBashroom();

        selectFloor();
        getIntentData();
        getStatus();

        return view;
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
            if (editData != null) {
                fragmentPostPropertyForSaleBinding.tvPropertyTitle.setText(editData.getTitle());
                fragmentPostPropertyForSaleBinding.tvCategory.setText(editData.getCategory());
                String purpose = editData.getPurpose();
                switch (purpose){
                    case "commercial":
                        fragmentPostPropertyForSaleBinding.rbResidential.setChecked(true);
                        break;
                    case "residential":
                        fragmentPostPropertyForSaleBinding.rbCommercial.setChecked(true);
                        break;
                    case "both":
                        fragmentPostPropertyForSaleBinding.rbBoth.setChecked(true);
                        break;
                }
                String available  = editData.getAvailable();
                switch (available){
                    case "family":
                        fragmentPostPropertyForSaleBinding.rbFamily.setChecked(true);
                        break;
                    case "single":
                        fragmentPostPropertyForSaleBinding.rbSingle.setChecked(true);
                        break;
                    case "both":
                        fragmentPostPropertyForSaleBinding.rbBothAvailable.setChecked(true);
                        break;
                }
                fragmentPostPropertyForSaleBinding.tvSelectBedrooms.setText(editData.getBedrooms());
                fragmentPostPropertyForSaleBinding.tvSelectBathrooms.setText(editData.getBathrooms());
                fragmentPostPropertyForSaleBinding.tvKitchen.setText(editData.getKitchens());
                fragmentPostPropertyForSaleBinding.tvSelectFloor.setText(editData.getFloor());
                fragmentPostPropertyForSaleBinding.tvSelectStatus.setText(editData.getStatus());

            }

        }
    }

    private void selectFloor() {
        floorData= DataGenerator.selectNoOfFoor();

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

        fragmentPostPropertyForSaleBinding.tvFloor.setAdapter(dataAdapter);
    }

    private void selectBashroom() {
        bedRoom= DataGenerator.selectNoOfBathRoom();




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bedRoom){
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

        fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.setAdapter(dataAdapter);
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
        fragmentPostPropertyForSaleBinding.spinnerStatus.setAdapter(dataAdapter);
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

        fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.setAdapter(dataAdapter);
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

        fragmentPostPropertyForSaleBinding.tvNoOfKitchen.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddPrice:
                startActivity(new Intent(getContext(), Add_Post__Items_Activity.class));
                break;
            case R.id.tvSavePostedProperty:
                checkedRadioButtonId = fragmentPostPropertyForSaleBinding.rgPurpose.getCheckedRadioButtonId();
                checkedRadioButtonIdForFamily = fragmentPostPropertyForSaleBinding.rgAvailableForU.getCheckedRadioButtonId();
                if (validation()) {
                    collectData();
                }
                break;
            case R.id.constrint:
                hideKeyboard();
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
            case R.id.tvCategory:
                isCategory = true;
                hideKeyboard();
                fragmentPostPropertyForSaleBinding.tvAppLanguage.performClick();
                break;
            case R.id.tvSelectBedrooms:
                isBedroom = true;
                hideKeyboard();
                fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.performClick();
                break;
            case R.id.tvSelectBathrooms:
                hideKeyboard();
                isBathroom = true;
                fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.performClick();
                break;
            case R.id.tvKitchen:
                hideKeyboard();
                isKitchen = true;
                fragmentPostPropertyForSaleBinding.tvNoOfKitchen.performClick();
                break;
            case R.id.tvSelectFloor:
                hideKeyboard();
                isFloor = true;
                fragmentPostPropertyForSaleBinding.tvFloor.performClick();
                break;

            case R.id.tvSelectStatus:
                hideKeyboard();
                isStatus = true;
                fragmentPostPropertyForSaleBinding.spinnerStatus.performClick();
                break;


        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fragmentPostPropertyForSaleBinding.rootConstraint.getWindowToken(), 0);
    }

    private boolean validation() {
        if (fragmentPostPropertyForSaleBinding.tvPropertyTitle != null && fragmentPostPropertyForSaleBinding.tvPropertyTitle.getText().toString().isEmpty()) {
            fragmentPostPropertyForSaleBinding.tvPropertyTitle.requestFocus();
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please enter Property Title ");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvPropertyTitle != null && !(MyValidator.isValidFullName(fragmentPostPropertyForSaleBinding.tvPropertyTitle.getText().toString()))) {
            fragmentPostPropertyForSaleBinding.tvPropertyTitle.requestFocus();
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please enter Property Title ");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvCategory.getText().toString().isEmpty() || fragmentPostPropertyForSaleBinding.tvCategory.getText().toString().equalsIgnoreCase("Select Category")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvAppLanguage.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select Category");
            return false;
        } else if (checkedRadioButtonId == -1) {
//            Toast.makeText(getContext(), "Plase Select purpose", Toast.LENGTH_SHORT).show();
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select purpose");
            return false;
        } else if (checkedRadioButtonIdForFamily == -1) {
//            Toast.makeText(getContext(), "Plase select Available for", Toast.LENGTH_SHORT).show();
            fragmentPostPropertyForSaleBinding.rbBothAvailable.setError("Please select Available for");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select purpose");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvSelectBedrooms.getText().toString().isEmpty() || fragmentPostPropertyForSaleBinding.tvSelectBedrooms.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select Number of Bedrooms");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvSelectBathrooms.getText().toString().isEmpty() || fragmentPostPropertyForSaleBinding.tvSelectBathrooms.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select Number of Bathrooms");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvKitchen.getText().toString().isEmpty() || fragmentPostPropertyForSaleBinding.tvKitchen.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvNoOfKitchen.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select Number of Kitchens");
            return false;
        } else if (fragmentPostPropertyForSaleBinding.tvSelectFloor.getText().toString().isEmpty() || fragmentPostPropertyForSaleBinding.tvSelectFloor.getText().toString().equalsIgnoreCase("Select")) {
//            ((TextView) fragmentPostPropertyForSaleBinding.tvFloor.getSelectedView()).setError("Error message");
            MyApplication.makeASnack(fragmentPostPropertyForSaleBinding.getRoot(), "Please Select Floor");
            return false;
        }
        return true;
    }

    private void collectData() {
        RadioButton radioButton = fragmentPostPropertyForSaleBinding.rgPurpose.findViewById(checkedRadioButtonId);
        RadioButton radioButton2 = fragmentPostPropertyForSaleBinding.rgAvailableForU.findViewById(checkedRadioButtonIdForFamily);

        Bundle bundle = new Bundle();
        bundle.putString("title", fragmentPostPropertyForSaleBinding.tvPropertyTitle.getText().toString());
        bundle.putString("type", "sale");
        bundle.putString("purpose", radioButton.getText().toString());
        bundle.putString("availablefor", radioButton2.getText().toString());
        bundle.putString("category", fragmentPostPropertyForSaleBinding.tvCategory.getText().toString());
        bundle.putString("bedrooms", fragmentPostPropertyForSaleBinding.tvSelectBedrooms.getText().toString());
        bundle.putString("bathrooms", fragmentPostPropertyForSaleBinding.tvSelectBathrooms.getText().toString());
        bundle.putString("kitchen", fragmentPostPropertyForSaleBinding.tvKitchen.getText().toString());
        bundle.putString("floor", fragmentPostPropertyForSaleBinding.tvSelectFloor.getText().toString());
        bundle.putString("status", fragmentPostPropertyForSaleBinding.tvSelectStatus.getText().toString());
        if(editData!=null)
            bundle.putSerializable("Data",editData);

        Intent intent = new Intent(getContext(), Post_A_Property_Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);

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

        fragmentPostPropertyForSaleBinding.tvAppLanguage.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fragmentPostPropertyForSaleBinding.tvPropertyTitle.clearFocus();

        switch (adapterView.getId()) {
            case R.id.tvAppLanguage:
                category = fragmentPostPropertyForSaleBinding.tvAppLanguage.getSelectedItem().toString();
                if(isCategory)
                    fragmentPostPropertyForSaleBinding.tvCategory.setText(category);
                break;

            case R.id.tvNoOfBedRooms:
                bedrooms = fragmentPostPropertyForSaleBinding.tvNoOfBedRooms.getSelectedItem().toString();
                if (isBedroom)
                    fragmentPostPropertyForSaleBinding.tvSelectBedrooms.setText(bedrooms);
                break;

            case R.id.spinner_status:
                status = fragmentPostPropertyForSaleBinding.spinnerStatus.getSelectedItem().toString();
                if(isStatus)
                    fragmentPostPropertyForSaleBinding.tvSelectStatus.setText(status);
                break;

            case R.id.tvNoOfBathrooms:
                bathrooms = fragmentPostPropertyForSaleBinding.tvNoOfBathrooms.getSelectedItem().toString();
                if(isBathroom)
                    fragmentPostPropertyForSaleBinding.tvSelectBathrooms.setText(bathrooms);
                break;

            case R.id.tvNoOfKitchen:
                kitchen = fragmentPostPropertyForSaleBinding.tvNoOfKitchen.getSelectedItem().toString();
                if(isKitchen)
                    fragmentPostPropertyForSaleBinding.tvKitchen.setText(kitchen);
                break;

            case R.id.tvFloor:
                floor = fragmentPostPropertyForSaleBinding.tvFloor.getSelectedItem().toString();
                if(isFloor)
                     fragmentPostPropertyForSaleBinding.tvSelectFloor.setText(floor);
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
