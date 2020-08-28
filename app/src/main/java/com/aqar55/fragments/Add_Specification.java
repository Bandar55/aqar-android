package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.aqar55.R;
import com.aqar55.databinding.LayoutAddSpecificationBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;

public class Add_Specification extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Add_Specification";
    String indooroption1 = "", indooroption2 = "", indooroption3 = "", indooroption4 = "", outdooroption1 = "", outdooroption2 = "", outdooroption3 = "", outdooroption4 = "",
            furnishedoption1 = "", furnishedoption2 = "", furnishedoption3 = "", furnishedoption4 = "", parkingoption1 = "", parkingoption2 = "", parkingoption3 = "", parkingoption4 = "",
            viewsoption1 = "", viewsoption2 = "", viewsoption3 = "", viewsoption4 = "";
    private Bundle bundle = new Bundle();
    private LayoutAddSpecificationBinding layoutAddSpecificationBinding;
    private Set_Price_Fragment set_price_fragment;
    private Set_Rent_Price_Info_Fragment set_price_fragment_rent;
    private Set_Rent_Price_Info_Fragment set_rent_price_info_fragment;
    ManageActiveProperty.Data editData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutAddSpecificationBinding = DataBindingUtil.inflate(inflater, R.layout.layout_add_specification, container, false);

        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
        }

        layoutAddSpecificationBinding.tvSaveSpecificationBTn.setOnClickListener(this);
        layoutAddSpecificationBinding.ivBack.setOnClickListener(this);
        layoutAddSpecificationBinding.skipspecification.setOnClickListener(this);


        layoutAddSpecificationBinding.cbOptINDOOR.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptIndoor2.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptINDOOR3.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptIndoor4.setOnCheckedChangeListener(this);

        layoutAddSpecificationBinding.cbOptOutdoor.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptOutdoor2.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptOutdoor3.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptOutdoor4.setOnCheckedChangeListener(this);

        layoutAddSpecificationBinding.cbOptFurn.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptFurn2.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptFurn3.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptFurn4.setOnCheckedChangeListener(this);

        layoutAddSpecificationBinding.cbOptParking.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptParking1.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptParking2.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptParking3.setOnCheckedChangeListener(this);

        layoutAddSpecificationBinding.cbOptViews.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptViews1.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptViews2.setOnCheckedChangeListener(this);
        layoutAddSpecificationBinding.cbOptViews3.setOnCheckedChangeListener(this);

        bundle = getArguments();
        getIntentData();
        set_price_fragment = new Set_Price_Fragment();
        return layoutAddSpecificationBinding.getRoot();
    }

    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
        if (editData != null) {
            String indoor = editData.getIndoor();
            String[] indoorOptions = indoor.split(",");
            for (String indoorOption : indoorOptions) {
                switch (indoorOption) {
                    case "Maid Room":
                        layoutAddSpecificationBinding.cbOptINDOOR.setChecked(true);
                        break;
                    case "Pool":
                        layoutAddSpecificationBinding.cbOptIndoor2.setChecked(true);
                        break;
                    case "Basement":
                        layoutAddSpecificationBinding.cbOptINDOOR3.setChecked(true);
                        break;
                    case "Internal Stair":
                        layoutAddSpecificationBinding.cbOptIndoor4.setChecked(true);
                        break;
                }
            }

            String outDoor = editData.getOutdoor();
            String[] outDoorOptions = outDoor.split(",");
            for (String outDoorOption : outDoorOptions) {
                switch (outDoorOption) {
                    case "Driver Room":
                        layoutAddSpecificationBinding.cbOptOutdoor.setChecked(true);
                        break;
                    case "Pool":
                        layoutAddSpecificationBinding.cbOptOutdoor2.setChecked(true);
                        break;
                    case "Extra Room":
                        layoutAddSpecificationBinding.cbOptOutdoor3.setChecked(true);
                        break;
                    case "Playground":
                        layoutAddSpecificationBinding.cbOptOutdoor4.setChecked(true);
                        break;
                }
            }

            String furnishing = editData.getFurnish();
            String[] furnishingOptions = furnishing.split(",");
            for (String furnishingOption : furnishingOptions) {
                switch (furnishingOption) {
                    case "Security Alarm":
                        layoutAddSpecificationBinding.cbOptFurn.setChecked(true);
                        break;
                    case "Fire Alarm":
                        layoutAddSpecificationBinding.cbOptFurn2.setChecked(true);
                        break;
                    case "Maintenance Covered":
                        layoutAddSpecificationBinding.cbOptFurn3.setChecked(true);
                        break;
                    case "Housekeeping":
                        layoutAddSpecificationBinding.cbOptFurn4.setChecked(true);
                        break;
                }
            }

            String parking = editData.getParkingoption();
            String[] parkingOptions = parking.split(",");
            for (String parkingOption : parkingOptions) {
                switch (parkingOption) {
                    case "Extra Parking":
                        layoutAddSpecificationBinding.cbOptParking.setChecked(true);
                        break;
                    case " Security Man":
                        layoutAddSpecificationBinding.cbOptParking1.setChecked(true);
                        break;
                    case "Gym":
                        layoutAddSpecificationBinding.cbOptParking2.setChecked(true);
                        break;
                    case "In Compound":
                        layoutAddSpecificationBinding.cbOptParking3.setChecked(true);
                        break;
                }
            }

            String views = editData.getViews();
            String[] viewsOptions = views.split(",");
            for (String viewsOption : viewsOptions) {
                switch (viewsOption) {
                    case "See View":
                        layoutAddSpecificationBinding.cbOptViews.setChecked(true);
                        break;
                    case "Garden View":
                        layoutAddSpecificationBinding.cbOptViews1.setChecked(true);
                        break;
                    case "High City View":
                        layoutAddSpecificationBinding.cbOptViews2.setChecked(true);
                        break;
                    case "Unique View":
                        layoutAddSpecificationBinding.cbOptViews3.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSaveSpecificationBTn:

                collectData();

                //Add_Address_Post_Property add_address_post_property = new Add_Address_Post_Property();
                // getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_address_post_property).addToBackStack("").commit();
                break;

            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.skipspecification:

                collectData();

                break;

        }
    }

    private void collectData() {

        bundle.putString("indooroption1", indooroption1);
        bundle.putString("indooroption2", indooroption2);
        bundle.putString("indooroption3", indooroption3);
        bundle.putString("indooroption4", indooroption4);

        bundle.putString("outdooroption1", outdooroption1);
        bundle.putString("outdooroption2", outdooroption2);
        bundle.putString("outdooroption3", outdooroption3);
        bundle.putString("outdooroption4", outdooroption4);

        bundle.putString("furnishingoption1", furnishedoption1);
        bundle.putString("furnishingoption2", furnishedoption2);
        bundle.putString("furnishingoption3", furnishedoption3);
        bundle.putString("furnishingoption4", furnishedoption4);

        bundle.putString("parkingoption1", parkingoption1);
        bundle.putString("parkingoption2", parkingoption2);
        bundle.putString("parkingoption3", parkingoption3);
        bundle.putString("parkingoption4", parkingoption4);

        bundle.putString("viewsoption1", viewsoption1);
        bundle.putString("viewsoption2", viewsoption2);
        bundle.putString("viewsoption3", viewsoption3);
        bundle.putString("viewsoption4", viewsoption4);
        if (editData != null) {
            bundle.putSerializable("Data", editData);

        }


        if (BaseManager.getDataFromPreferences("from_activity", String.class).toString().equalsIgnoreCase("sale")) {
            set_price_fragment = new Set_Price_Fragment();
            set_price_fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, set_price_fragment).addToBackStack("").commit();

        } else {
            set_price_fragment_rent = new Set_Rent_Price_Info_Fragment();
            set_price_fragment_rent.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, set_price_fragment_rent).addToBackStack("").commit();

        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cbOptINDOOR:
                if (layoutAddSpecificationBinding.cbOptINDOOR.isChecked())
                    indooroption1 = compoundButton.getText().toString();
                else
                    indooroption1 = "";
                break;
            case R.id.cbOptIndoor2:
                if (layoutAddSpecificationBinding.cbOptIndoor2.isChecked())
                    indooroption2 = compoundButton.getText().toString();
                else
                    indooroption2 = "";
                break;
            case R.id.cbOptINDOOR3:
                if (layoutAddSpecificationBinding.cbOptINDOOR3.isChecked())
                    indooroption3 = compoundButton.getText().toString();
                else
                    indooroption3 = "";
                break;
            case R.id.cbOptIndoor4:
                if (layoutAddSpecificationBinding.cbOptIndoor4.isChecked())
                    indooroption4 = compoundButton.getText().toString();
                else
                    indooroption4 = "";
                break;
            case R.id.cbOptOutdoor:
                if (layoutAddSpecificationBinding.cbOptOutdoor.isChecked())
                    outdooroption1 = compoundButton.getText().toString();
                else
                    outdooroption1 = "";
                break;
            case R.id.cbOptOutdoor2:
                if (layoutAddSpecificationBinding.cbOptOutdoor2.isChecked())
                    outdooroption2 = compoundButton.getText().toString();
                else
                    outdooroption2 = "";
                break;
            case R.id.cbOptOutdoor3:
                if (layoutAddSpecificationBinding.cbOptOutdoor3.isChecked())
                    outdooroption3 = compoundButton.getText().toString();
                else
                    outdooroption3 = "";
                break;
            case R.id.cbOptOutdoor4:
                if (layoutAddSpecificationBinding.cbOptOutdoor4.isChecked())
                    outdooroption4 = compoundButton.getText().toString();
                else
                    outdooroption4 = "";
                break;
            case R.id.cbOptFurn:
                if (layoutAddSpecificationBinding.cbOptFurn.isChecked())
                    furnishedoption1 = compoundButton.getText().toString();
                else
                    furnishedoption1 = "";
                break;
            case R.id.cbOptFurn2:
                if (layoutAddSpecificationBinding.cbOptFurn2.isChecked())
                    furnishedoption2 = compoundButton.getText().toString();
                else
                    furnishedoption2 = "";
                break;
            case R.id.cbOptFurn3:
                if (layoutAddSpecificationBinding.cbOptFurn3.isChecked())
                    furnishedoption3 = compoundButton.getText().toString();
                else
                    furnishedoption3 = "";
                break;
            case R.id.cbOptFurn4:
                if (layoutAddSpecificationBinding.cbOptFurn4.isChecked())
                    furnishedoption4 = compoundButton.getText().toString();
                else
                    furnishedoption4 = "";
                break;
            case R.id.cbOptParking:
                if (layoutAddSpecificationBinding.cbOptParking.isChecked())
                    parkingoption1 = compoundButton.getText().toString();
                else
                    parkingoption1 = "";
                break;
            case R.id.cbOptParking1:
                if (layoutAddSpecificationBinding.cbOptParking1.isChecked())
                    parkingoption2 = compoundButton.getText().toString();
                else
                    parkingoption2 = "";
                break;
            case R.id.cbOptParking2:
                if (layoutAddSpecificationBinding.cbOptParking2.isChecked())
                    parkingoption3 = compoundButton.getText().toString();
                else
                    parkingoption3 = "";
                break;
            case R.id.cbOptParking3:
                if (layoutAddSpecificationBinding.cbOptParking3.isChecked())
                    parkingoption4 = compoundButton.getText().toString();
                else
                    parkingoption4 = "";
                break;
            case R.id.cbOptViews:
                if (layoutAddSpecificationBinding.cbOptViews.isChecked())
                    viewsoption1 = compoundButton.getText().toString();
                else
                    viewsoption1 = "";
                break;
            case R.id.cbOptViews1:
                if (layoutAddSpecificationBinding.cbOptViews1.isChecked())
                    viewsoption2 = compoundButton.getText().toString();
                else
                    viewsoption2 = "";
                break;
            case R.id.cbOptViews2:
                if (layoutAddSpecificationBinding.cbOptViews2.isChecked())
                    viewsoption3 = compoundButton.getText().toString();
                else
                    viewsoption3 = "";
                break;
            case R.id.cbOptViews3:
                if (layoutAddSpecificationBinding.cbOptViews3.isChecked())
                    viewsoption4 = compoundButton.getText().toString();
                else
                    viewsoption4 = "";
                break;


        }
    }
}
