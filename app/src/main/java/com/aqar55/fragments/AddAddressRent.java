package com.aqar55.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aqar55.CommonClass.CommonClass;
import com.aqar55.R;
import com.aqar55.databinding.LayoutAddAddressBinding;
import com.aqar55.model.AddImageModel;
import com.aqar55.model.PostPropertyForSale;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.MyDialog;
import com.aqar55.utill.MyValidator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressRent extends Fragment implements View.OnClickListener {
    private static final String TAG = "Add_Address_Post_Proper";
    boolean purposeComm, purposeRes, PurposeBoth, AbailableForFam, AbailableForSin, AbailableForBoth;
    private LayoutAddAddressBinding layoutAddAddressBinding;
    private View view;
    private String country = "", city = "", state = "", zip = "", addess = "", locality = "";
    private Bundle bundle = new Bundle();
    private ArrayList<AddImageModel> addImageModels = new ArrayList<>();
    private File file;
    private double lat, longg;


    private List<MultipartBody.Part> list = new ArrayList<>();
    private MultipartBody.Part profilePart;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            country = intent.getStringExtra("country");
            city = intent.getStringExtra("city");
            state = intent.getStringExtra("state");
            zip = intent.getStringExtra("zip");
            addess = intent.getStringExtra("address");
            locality = intent.getStringExtra("locality");
            lat = intent.getDoubleExtra("latitude", 0.0f);
            longg = intent.getDoubleExtra("longitude", 0.0f);
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    public List<MultipartBody.Part> checkimage() {
        if (addImageModels == null) {
            list = null;
        } else {
            list = morePart();
        }
        return list;
    }

    public MultipartBody.Part checkVideo() {
        if (file == null) {
            profilePart = null;
        } else {
            profilePart = getVideoMultipart();
        }
        return profilePart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutAddAddressBinding = DataBindingUtil.inflate(inflater, R.layout.layout_add_address, container, false);
        view = layoutAddAddressBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(layoutAddAddressBinding.addressrent, "Make sure you are connected to Internet.");
        }
        layoutAddAddressBinding.tvAddLocationByMap.setOnClickListener(this);
        layoutAddAddressBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutAddAddressBinding.ivBack.setOnClickListener(this);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("address-data-rent"));
        bundle = getArguments();
        getImageArray();
        getPurposeAndAbailableInfo();
        return view;
    }

    private void getImageArray() {

        addImageModels = (ArrayList<AddImageModel>) bundle.getSerializable("uploadedimages");
        file = (File) bundle.getSerializable("videoview");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddLocationByMap:
                //startActivityForResult(PickAddressActivity.getIntent(getActivity()),100);

                AddMapAddressRent addMapAddressRent = new AddMapAddressRent();
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, addMapAddressRent).addToBackStack("").commit();
                break;
            case R.id.tvSavePostedProperty:

                if (validation()) {
                    collectData();
                }
                break;

            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
        }
    }

    private void collectData() {

        bundle.putString("country", layoutAddAddressBinding.tvAppLanguage.getText().toString());
        bundle.putString("satate", layoutAddAddressBinding.tvSpeakLan.getText().toString());
        bundle.putString("city", layoutAddAddressBinding.tvCurrency.getText().toString());
        bundle.putString("locality", layoutAddAddressBinding.tvMeasurement.getText().toString());
        bundle.putString("zip", layoutAddAddressBinding.tvMeasurement3.getText().toString());
        bundle.putString("address", layoutAddAddressBinding.tvMeasurement2.getText().toString());
        bundle.putString("suitno", layoutAddAddressBinding.tvMeasurement4.getText().toString());
        bundle.putString("buildingno", layoutAddAddressBinding.tvMeasurement5.getText().toString());
        bundle.putString("lat", lat + "");
        bundle.putString("long", longg + "");

        sendDataToApi();


    }

    private void sendDataToApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());
        //Toast.makeText(getContext(), "Api Hit", Toast.LENGTH_SHORT).show();
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<PostPropertyForSale> post_property_for_saleCall = api.postDataForSale(setUpMapData(), checkimage(), checkVideo());

        post_property_for_saleCall.enqueue(new Callback<PostPropertyForSale>() {
            @Override
            public void onResponse(Call<PostPropertyForSale> call, Response<PostPropertyForSale> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), " Property is posted successfully." + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    BottomSheet_Property_Posted bottomSheet_property_posted = new BottomSheet_Property_Posted();
                    bottomSheet_property_posted.show(getFragmentManager(), bottomSheet_property_posted.getTag());
                }
            }

            @Override
            public void onFailure(Call<PostPropertyForSale> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                MyDialog.getInstance(getContext()).hideDialog();
            }
        });
    }

    private MultipartBody.Part getVideoMultipart() {

        RequestBody profile_body;
        MultipartBody.Part profilePart;

        profile_body = RequestBody.create(MediaType.parse("video/*"), file);
        profilePart = MultipartBody.Part.createFormData("videosFile", file.getName(), profile_body);

        return profilePart;
    }

    private boolean validation() {
        if (layoutAddAddressBinding.tvAppLanguage != null && layoutAddAddressBinding.tvAppLanguage.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvAppLanguage.requestFocus();
            layoutAddAddressBinding.tvAppLanguage.setError("Please enter Country Name  ");
            return false;
        } else if (layoutAddAddressBinding.tvAppLanguage != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvAppLanguage.getText().toString()))) {
            layoutAddAddressBinding.tvAppLanguage.requestFocus();
            layoutAddAddressBinding.tvAppLanguage.setError("Please enter a valid Country Name ");
            return false;
        } else if (layoutAddAddressBinding.tvSpeakLan != null && layoutAddAddressBinding.tvSpeakLan.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvAppLanguage.requestFocus();
            layoutAddAddressBinding.tvAppLanguage.setError("Please enter State Name ");
            return false;
        } else if (layoutAddAddressBinding.tvSpeakLan != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvSpeakLan.getText().toString()))) {
            layoutAddAddressBinding.tvSpeakLan.requestFocus();
            layoutAddAddressBinding.tvSpeakLan.setError("Please enter a valid State Name ");
            return false;
        } else if (layoutAddAddressBinding.tvCurrency != null && layoutAddAddressBinding.tvCurrency.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvCurrency.requestFocus();
            layoutAddAddressBinding.tvCurrency.setError("Please enter City Name ");
            return false;
        } else if (layoutAddAddressBinding.tvCurrency != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvCurrency.getText().toString()))) {
            layoutAddAddressBinding.tvCurrency.requestFocus();
            layoutAddAddressBinding.tvCurrency.setError("Please enter a valid City Name ");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement != null && layoutAddAddressBinding.tvMeasurement.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement.requestFocus();
            layoutAddAddressBinding.tvMeasurement.setError("Please enter Area/Locality Name");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement.requestFocus();
            layoutAddAddressBinding.tvMeasurement.setError("Please enter a valid Area/Locality Name ");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement3 != null && layoutAddAddressBinding.tvMeasurement3.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement3.requestFocus();
            layoutAddAddressBinding.tvMeasurement3.setError("Please enter Zip Code");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement3 != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement3.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement3.requestFocus();
            layoutAddAddressBinding.tvMeasurement3.setError("Please enter a valid Zip Code  ");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement2 != null && layoutAddAddressBinding.tvMeasurement2.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement2.requestFocus();
            layoutAddAddressBinding.tvMeasurement2.setError("Please enter Address");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement2 != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement2.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement2.requestFocus();
            layoutAddAddressBinding.tvMeasurement2.setError("Please enter a valid Address");
            return false;
        }/* else if (layoutAddAddressBinding.tvMeasurement4 != null && layoutAddAddressBinding.tvMeasurement4.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement4.requestFocus();
            layoutAddAddressBinding.tvMeasurement4.setError("Please enter Apt/Suite No. ");
            return false;
        }*//* else if (layoutAddAddressBinding.tvMeasurement4 != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement4.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement4.requestFocus();
            layoutAddAddressBinding.tvMeasurement4.setError("Please enter a valid Apt/Suite No.");
            return false;
        } */
      /*  else if (layoutAddAddressBinding.tvMeasurement5 != null && layoutAddAddressBinding.tvMeasurement5.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement5.requestFocus();
            layoutAddAddressBinding.tvMeasurement5.setError("Please enter Building No./Name ");
            return false;
        } *//*else if (layoutAddAddressBinding.tvMeasurement5 != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement5.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement5.requestFocus();
            layoutAddAddressBinding.tvMeasurement5.setError("Please enter a valid Building No./Name ");
            return false;
        }*/
        return true;
    }

    private List<MultipartBody.Part> morePart() {
        List<MultipartBody.Part> list = new ArrayList<>();
        for (int i = 0; i < addImageModels.size(); i++) {
            RequestBody profile_body = RequestBody.create(MediaType.parse("image/*"), addImageModels.get(i).getFile());
            MultipartBody.Part menuPart = MultipartBody.Part.createFormData("imagesFile", addImageModels.get(i).getFile().getName(), profile_body);
            list.add(menuPart);
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutAddAddressBinding.tvAppLanguage.setText(country);
        layoutAddAddressBinding.tvSpeakLan.setText(state);
        layoutAddAddressBinding.tvCurrency.setText(city);
        layoutAddAddressBinding.tvMeasurement3.setText(zip);
        layoutAddAddressBinding.tvMeasurement.setText(locality);
        layoutAddAddressBinding.tvMeasurement2.setText(addess);
    }

    private Map<String, RequestBody> setUpMapData() {

        Map<String, RequestBody> fields = new HashMap<>();

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("title"));
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("type"));
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("category"));
        RequestBody bedrooms = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("bedrooms"));
        RequestBody bathrooms = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("bathrooms"));
        RequestBody kitchen = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("kitchen"));
        RequestBody floor = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("floor"));

        RequestBody builtinsize = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("builtinsize"));
        RequestBody builtinsizeunit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("builtinsizeunit"));

        RequestBody plotsize = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("plotsize"));
        RequestBody plotsizeunit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("plotsizeunit"));
        RequestBody builtinyear = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("builtinyear"));
        RequestBody streetview = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("streetview"));
        RequestBody streetwidth = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("streetwidth"));
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("description"));
        RequestBody extrabuilding = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("extrabuilding"));
        RequestBody extrashowroom = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("extrashowroom"));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("lat"));
        RequestBody longg = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("long"));
        RequestBody country = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("country"));
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("satate"));
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("city"));
        RequestBody locality = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("locality"));
        RequestBody zip = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("zip"));
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("address"));
        RequestBody suitno = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("suitno"));
        RequestBody buildingno = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("buildingno"));
        RequestBody streetWidthUnit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("streetwidthunit"));
        RequestBody revenue = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("revenue"));
        RequestBody renttime = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("renttime"));
        RequestBody rentprice = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("rentprice"));
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "active");

        StringBuilder indoorOption = new StringBuilder();
        if (bundle.getString("indooroption1") != null) {
            indoorOption.append(bundle.get("indooroption1"));
        }
        if (bundle.getString("indooroption2") != null) {
            indoorOption.append("," + bundle.get("indooroption2"));
        }
        if (bundle.getString("indooroption3") != null) {
            indoorOption.append("," + bundle.get("indooroption3"));
        }
        if (bundle.getString("indooroption4") != null) {
            indoorOption.append("," + bundle.get("indooroption4"));
        }

        StringBuilder outdoorOption = new StringBuilder();
        if (bundle.getString("outdooroption1") != null) {
            outdoorOption.append(bundle.get("outdooroption1"));
        }
        if (bundle.getString("outdooroption2") != null) {
            outdoorOption.append("," + bundle.get("outdooroption2"));
        }
        if (bundle.getString("outdooroption3") != null) {
            outdoorOption.append("," + bundle.get("outdooroption3"));
        }
        if (bundle.getString("outdooroption4") != null) {
            outdoorOption.append("," + bundle.get("outdooroption4"));
        }


        StringBuilder parkingOption = new StringBuilder();
        if (bundle.getString("parkingoption1") != null) {
            parkingOption.append(bundle.get("parkingoption1"));
        }
        if (bundle.getString("parkingoption2") != null) {
            parkingOption.append("," + bundle.get("parkingoption2"));
        }
        if (bundle.getString("parkingoption3") != null) {
            parkingOption.append("," + bundle.get("parkingoption3"));
        }
        if (bundle.getString("parkingoption4") != null) {
            parkingOption.append("," + bundle.get("parkingoption4"));
        }


        StringBuilder furnishingOption = new StringBuilder();
        if (bundle.getString("furnishingoption1") != null) {
            furnishingOption.append(bundle.get("furnishingoption1"));
        }
        if (bundle.getString("furnishingoption2") != null) {
            furnishingOption.append("," + bundle.get("furnishingoption2"));
        }
        if (bundle.getString("furnishingoption3") != null) {
            furnishingOption.append("," + bundle.get("furnishingoption3") + ",");
        }
        if (bundle.getString("furnishingoption4") != null) {
            furnishingOption.append("," + bundle.get("furnishingoption4"));
        }

        StringBuilder viewOption = new StringBuilder();
        if (bundle.getString("viewsoption1") != null) {
            viewOption.append(bundle.get("viewsoption1"));
        }
        if (bundle.getString("viewsoption2") != null) {
            viewOption.append("," + bundle.get("viewsoption2"));
        }
        if (bundle.getString("viewsoption3") != null) {
            viewOption.append("," + bundle.get("viewsoption3"));
        }
        if (bundle.getString("viewsoption4") != null) {
            viewOption.append("," + bundle.get("viewsoption4"));
        }
        RequestBody indoor = RequestBody.create(MediaType.parse("text/plain"), indoorOption.toString());
        RequestBody outdoor = RequestBody.create(MediaType.parse("text/plain"), outdoorOption.toString());
        RequestBody parkingOptions = RequestBody.create(MediaType.parse("text/plain"), parkingOption.toString());
        RequestBody furnishing = RequestBody.create(MediaType.parse("text/plain"), furnishingOption.toString());
        RequestBody view = RequestBody.create(MediaType.parse("text/plain"), viewOption.toString());

        RequestBody porpose = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("purpose"));
        RequestBody availablefor = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("availablefor"));
        RequestBody balcony = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("balcony")));
        RequestBody garden = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("garden")));
        RequestBody parking = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("parking")));

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), CommonClass.getPreferencesString(getContext(), "userid"));
//
//        RequestBody purposeCommercial = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(purposeComm));
//        RequestBody purposeResidential = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(purposeRes));
//        RequestBody purposeBoth = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(PurposeBoth));
//        RequestBody availableFamily = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(AbailableForFam));
//        RequestBody availableSingle = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(AbailableForSin));
//        RequestBody availableBoth = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(AbailableForBoth));

//        RequestBody rentTimeDaily = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("dailyprice"));
//        RequestBody rentTimeWeekly = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("weeklyprice"));
//        RequestBody rentTimeMonthly = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("monthlyprice"));
//        RequestBody rentTimeYearly = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("yearlyprice"));
        fields.put("status", status);
        fields.put("rentTime", renttime);
        fields.put("totalPriceRent", rentprice);
        fields.put("userId", userId);
        fields.put("title", title);
        fields.put("type", type);
        fields.put("category", category);
        fields.put("bedrooms", bedrooms);
        fields.put("bathrooms", bathrooms);
        fields.put("kitchens", kitchen);
        fields.put("floor", floor);
        fields.put("builtSize", builtinsize);
        fields.put("builtSizeUnit", builtinsizeunit);
        fields.put("plotSize", plotsize);//
        fields.put("plotSizeUnit", plotsizeunit);
        fields.put("yearBuilt", builtinyear);
        fields.put("streetView", streetview);
        fields.put("streetWidth", streetwidth);
        fields.put("streetWidthUnit", streetWidthUnit);
        fields.put("description", description);
        fields.put("extrabuildingNo", extrabuilding);
        fields.put("extrashowroomNo", extrashowroom);
        fields.put("revenue", revenue);
        fields.put("country", country);
        fields.put("state", state);
        fields.put("city", city);
        fields.put("area", locality);
        fields.put("zipcode", zip);
        fields.put("apartmentNo", suitno);
        fields.put("buildingNo", buildingno);
        fields.put("address", address);
        fields.put("lat", lat);
        fields.put("long", longg);
        //imagecaption
        fields.put("balcony", balcony);
        fields.put("garden", garden);
        fields.put("parking", parking);

        // fields.put("modularKitchen", modularKitchen);
        fields.put("purpose", porpose);
        fields.put("available", availablefor);
        fields.put("indoor", indoor);
        fields.put("outdoor", outdoor);
        fields.put("furnish", furnishing);
        fields.put("parkingOption", parkingOptions);
        fields.put("views", view);

        return fields;
    }

    public void getPurposeAndAbailableInfo() {
        if (bundle.get("purpose").equals("Commercial")) {
            purposeComm = true;
        } else if (bundle.get("purpose").equals("Residential")) {
            purposeRes = true;
        } else if (bundle.get("purpose").equals("Both")) {
            PurposeBoth = true;
        }

        if (bundle.get("availablefor").equals("Family")) {
            purposeComm = true;
        } else if (bundle.get("availablefor").equals("Single")) {
            purposeRes = true;
        } else if (bundle.get("availablefor").equals("Both")) {
            PurposeBoth = true;
        }
    }
}

