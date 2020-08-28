package com.aqar55.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.aqar55.R;
import com.aqar55.databinding.LayoutAddSpecificationBinding;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;


public class AddSpecificationRent extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Add_Specification";
    String indooroption1, indooroption2, indooroption3, indooroption4, outdooroption1, outdooroption2, outdooroption3, outdooroption4,
            furnishedoption1, furnishedoption2, furnishedoption3, furnishedoption4, parkingoption1, parkingoption2, parkingoption3, parkingoption4,
            viewsoption1, viewsoption2, viewsoption3, viewsoption4;
    private Bundle bundle = new Bundle();
    private View view;
    private LayoutAddSpecificationBinding layoutAddSpecificationBinding;
    private AddImageRent addImageRent;
    private Set_Rent_Price_Info_Fragment set_rent_price_info_fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutAddSpecificationBinding = DataBindingUtil.inflate(inflater, R.layout.layout_add_specification, container, false);
        view = layoutAddSpecificationBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(view, "Make sure you are connected to Internet.");
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

        set_rent_price_info_fragment = new Set_Rent_Price_Info_Fragment();
        return view;
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
                getFragmentManager().popBackStack();
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
        bundle.putString("furnishingoption4", furnishedoption3);

        bundle.putString("parkingoption1", parkingoption1);
        bundle.putString("parkingoption2", parkingoption2);
        bundle.putString("parkingoption3", parkingoption3);
        bundle.putString("parkingoption4", parkingoption4);

        bundle.putString("viewsoption1", viewsoption1);
        bundle.putString("viewsoption2", viewsoption2);
        bundle.putString("viewsoption3", viewsoption3);
        bundle.putString("viewsoption4", viewsoption4);


        set_rent_price_info_fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, set_rent_price_info_fragment).addToBackStack("").commit();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cbOptINDOOR:
                indooroption1 = compoundButton.getText().toString();
                break;
            case R.id.cbOptIndoor2:
                indooroption2 = compoundButton.getText().toString();
                break;
            case R.id.cbOptINDOOR3:
                indooroption3 = compoundButton.getText().toString();
                break;
            case R.id.cbOptIndoor4:
                indooroption4 = compoundButton.getText().toString();
                break;
            case R.id.cbOptOutdoor:
                outdooroption1 = compoundButton.getText().toString();
                break;
            case R.id.cbOptOutdoor2:
                outdooroption2 = compoundButton.getText().toString();
                break;
            case R.id.cbOptOutdoor3:
                outdooroption3 = compoundButton.getText().toString();
                break;
            case R.id.cbOptOutdoor4:
                outdooroption4 = compoundButton.getText().toString();
                break;
            case R.id.cbOptFurn:
                furnishedoption1 = compoundButton.getText().toString();
                break;
            case R.id.cbOptFurn2:
                furnishedoption2 = compoundButton.getText().toString();
                break;
            case R.id.cbOptFurn3:
                furnishedoption3 = compoundButton.getText().toString();
                break;
            case R.id.cbOptFurn4:
                furnishedoption4 = compoundButton.getText().toString();
                break;
            case R.id.cbOptParking:
                parkingoption1 = compoundButton.getText().toString();
                break;
            case R.id.cbOptParking1:
                parkingoption2 = compoundButton.getText().toString();
                break;
            case R.id.cbOptParking2:
                parkingoption3 = compoundButton.getText().toString();
                break;
            case R.id.cbOptParking3:
                parkingoption4 = compoundButton.getText().toString();
                break;
            case R.id.cbOptViews:
                viewsoption1 = compoundButton.getText().toString();
                break;
            case R.id.cbOptViews1:
                viewsoption2 = compoundButton.getText().toString();
                break;
            case R.id.cbOptViews2:
                viewsoption3 = compoundButton.getText().toString();
                break;
            case R.id.cbOptViews3:
                viewsoption4 = compoundButton.getText().toString();
                break;


        }
    }
}
