package com.aqar55.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.aqar55.R;
import com.aqar55.activitys.AddressPicker;
import com.aqar55.databinding.LayoutAddAddressBinding;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.model.AddImageModel;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.model.PostPropertyForSale;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyDialog;
import com.aqar55.utill.MyValidator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Address_Post_Property extends Fragment implements View.OnClickListener {

    private static final String TAG = "Add_Address_Post_Proper";
    boolean purposeComm, purposeRes, PurposeBoth, AbailableForFam, AbailableForSin, AbailableForBoth;
    private List<MultipartBody.Part> list = new ArrayList<>();
    private MultipartBody.Part profilePart;
    private LayoutAddAddressBinding layoutAddAddressBinding;
    private String country = "", city = "", state = "", zip = "", addess = "", locality = "";
    private Bundle bundle = new Bundle();
    private ArrayList<AddImageModel> addImageModels = new ArrayList<>();
    private String lat, longg;
    private File file;
    private ManageActiveProperty.Data editData;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            country = intent.getStringExtra("country");
            city = intent.getStringExtra("city");
            state = intent.getStringExtra("state");
            zip = intent.getStringExtra("zip");
            addess = intent.getStringExtra("address");



        }
    };

    public void getPurposeAndAbailableInfo() {
        if (bundle.get("purpose").equals("Commercial")) {
            purposeComm = true;
        } else if (bundle.get("purpose").equals("Residential")) {
            purposeRes = true;
        } else if (bundle.get("purpose").equals("Both")) {
            PurposeBoth = true;
        }

        if (bundle.get("availablefor").equals("Family")) {
            AbailableForFam = true;
        } else if (bundle.get("availablefor").equals("Single")) {
            AbailableForSin = true;
        } else if (bundle.get("availablefor").equals("Both")) {
            AbailableForBoth = true;
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutAddAddressBinding = DataBindingUtil.inflate(inflater, R.layout.layout_add_address, container, false);

        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
        }

        layoutAddAddressBinding.tvAddLocationByMap.setOnClickListener(this);
        layoutAddAddressBinding.tvSavePostedProperty.setOnClickListener(this);
        layoutAddAddressBinding.ivBack.setOnClickListener(this);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("address-data"));

        bundle = getArguments();
        getIntentData();
        getImageArray();
        getPurposeAndAbailableInfo();
        return layoutAddAddressBinding.getRoot();
    }

    private void getIntentData() {
       editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
       if(editData!=null){
           layoutAddAddressBinding.tvAppLanguage.setText(editData.getCountry());
           layoutAddAddressBinding.tvSpeakLan.setText(editData.getState());
           layoutAddAddressBinding.tvCurrency.setText(editData.getCity());
           layoutAddAddressBinding.tvMeasurement.setText(editData.getArea());
           layoutAddAddressBinding.tvMeasurement3.setText(editData.getZipcode());
           layoutAddAddressBinding.tvMeasurement2.setText(editData.getAddress());
           layoutAddAddressBinding.tvMeasurement4.setText(editData.getApartmentno());
           layoutAddAddressBinding.tvMeasurement5.setText(editData.getBuildingno());
           lat=editData.getLat();
           longg=editData.getLongg();

       }
    }

    private void getImageArray() {

        addImageModels = (ArrayList<AddImageModel>) bundle.getSerializable("uploadedimages");
        file = (File) bundle.getSerializable("videoview");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddLocationByMap:
                startActivityForResult(AddressPicker.getIntent(getContext()), 100);
               /* Add_Map_Location_Fragment add_map_location_fragment = new Add_Map_Location_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_map_location_fragment).addToBackStack("").commit();*/
                break;
            case R.id.tvSavePostedProperty:
                if (!new InternetCheck(getContext()).isConnect()) {
                    Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
                } else {
                    if (validation()) {
                        collectData();
                    }
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
        bundle.putString("lat", lat );
        bundle.putString("long", longg );


        sendDataToApi();

    }

    private void sendDataToApi() {
        MyDialog.getInstance(getContext()).showDialog(getActivity());

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<PostPropertyForSale> post_property_for_saleCall;

        if(editData!=null){
            post_property_for_saleCall = api.updateProperty(setUpMapData(), checkimage(), checkVideo());
        }else {
            post_property_for_saleCall = api.postDataForSale(setUpMapData(), checkimage(), checkVideo());
        }


        post_property_for_saleCall.enqueue(new Callback<PostPropertyForSale>() {
            @Override
            public void onResponse(@NonNull Call<PostPropertyForSale> call, @NonNull Response<PostPropertyForSale> response) {
                MyDialog.getInstance(getContext()).hideDialog();
                if (response.body().getResponseCode()==200) {
                   // Toast.makeText(getContext() , response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    BottomSheet_Property_Posted bottomSheet_property_posted = new BottomSheet_Property_Posted();
                    bottomSheet_property_posted.show(getFragmentManager(), bottomSheet_property_posted.getTag());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostPropertyForSale> call, @NonNull Throwable t) {
                BottomSheet_Property_Posted bottomSheet_property_posted = new BottomSheet_Property_Posted();
                bottomSheet_property_posted.show(getFragmentManager(), bottomSheet_property_posted.getTag());
            }
        });
    }

    private MultipartBody.Part getVideoMultipart() {

        RequestBody profile_body;


        profile_body = RequestBody.create(MediaType.parse("video/*"), file);
        profilePart = MultipartBody.Part.createFormData("videosFile", file.getName(), profile_body);
        return profilePart;
    }

    private boolean validation() {
        if (layoutAddAddressBinding.tvAppLanguage != null && layoutAddAddressBinding.tvAppLanguage.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvAppLanguage.requestFocus();
            layoutAddAddressBinding.tvAppLanguage.setError("Please enter Country Name ");
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
            layoutAddAddressBinding.tvMeasurement3.setError("Please enter a valid Zip Code ");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement2 != null && layoutAddAddressBinding.tvMeasurement2.getText().toString().isEmpty()) {
            layoutAddAddressBinding.tvMeasurement2.requestFocus();
            layoutAddAddressBinding.tvMeasurement2.setError("Please enter Address");
            return false;
        } else if (layoutAddAddressBinding.tvMeasurement2 != null && !(MyValidator.isValidFullName(layoutAddAddressBinding.tvMeasurement2.getText().toString()))) {
            layoutAddAddressBinding.tvMeasurement2.requestFocus();
            layoutAddAddressBinding.tvMeasurement2.setError("Please enter a valid Address");
            return false;
        }
        return true;
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

    private List<MultipartBody.Part> morePart() {

        for (int i = 0; i < addImageModels.size(); i++) {
            RequestBody profile_body = RequestBody.create(MediaType.parse("image/*"), addImageModels.get(i).getFile());
            MultipartBody.Part menuPart = MultipartBody.Part.createFormData("imagesFile", addImageModels.get(i).getFile().getName(), profile_body);
            list.add(menuPart);
        }
        return list;
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
       // RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "active");
        RequestBody streetWidthUnit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("streetwidthunit"));
        RequestBody revenue = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("revenue"));
        RequestBody totalprice = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("totalprice"));
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("status"));

        fields.put("status", status);



        if(BaseManager.getDataFromPreferences("from_activity",String.class).toString().equalsIgnoreCase("rent")){
            RequestBody dailyprice = RequestBody.create(MediaType.parse("text/plain"),bundle.getString("dailyprice"));
            RequestBody weeklyprice = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("weeklyprice"));
            RequestBody monthlyprice = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("monthlyprice"));
            RequestBody yearlyprice = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("yearlyprice"));
            RequestBody rentTime = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("renttime"));


            fields.put("defaultDailyPrice", dailyprice);
            fields.put("defaultWeeklyPrice", weeklyprice);
            fields.put("defaultMonthlyPrice", monthlyprice);
            fields.put("defaultyearlyPrice", yearlyprice);
            fields.put("rentTime",rentTime);
            fields.put("totalPriceRent", totalprice);

        }else {
            RequestBody sizem2 = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("sizem2"));
            RequestBody pricepermeter = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("pricepermeter"));

            fields.put("sizem2", sizem2);
            fields.put("pricePerMeter", pricepermeter);
            fields.put("totalPriceSale", totalprice);

        }



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
            furnishingOption.append("," + bundle.get("furnishingoption3"));
        }
        if (bundle.getString("furnishingoption4") != null) {
            furnishingOption.append("," + bundle.get("furnishingoption4"));
        }

        StringBuilder viewOption = new StringBuilder();
        if (bundle.getString("viewsoption1") != null) {
            viewOption.append(bundle.get("viewsoption1"));
        }
        if (bundle.getString("viewsoption2") != null) {
            viewOption.append(","+bundle.get("viewsoption2"));
        }
        if (bundle.getString("viewsoption3") != null) {
            viewOption.append(","+bundle.get("viewsoption3") );
        }
        if (bundle.getString("viewsoption4") != null) {
            viewOption.append(","+bundle.get("viewsoption4"));
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
        RequestBody modulerkitchen = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("modulerkitchen")));
        RequestBody store = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("store")));
        RequestBody lift = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("lift")));
        RequestBody duplex = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("duplex")));
        RequestBody furnished = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("furnished")));
        RequestBody airConditioning = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bundle.getBoolean("airConditioning")));
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"),ModelManager.modelManager().getCurrentUser().getId());


        RequestBody lengthUnit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("lengthUnit"));
        RequestBody widthUnit = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("widthUnit"));
        RequestBody length = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("length"));
        RequestBody width = RequestBody.create(MediaType.parse("text/plain"), bundle.getString("width"));

        fields.put("store", store);
        fields.put("lift", lift);
        fields.put("duplex", duplex);
        fields.put("furnished", furnished);
        fields.put("aircondition", airConditioning);

        if(editData!=null){
            fields.put("propertyId",RequestBody.create(MediaType.parse("text/plain"), editData.getId()));

        }

        fields.put("length", length);
        fields.put("lengthUnit", lengthUnit);
        fields.put("widthUnit", widthUnit);
        fields.put("width", width);

        fields.put("status", status);
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
        fields.put("plotSize", plotsize);
        fields.put("plotSizeUnit", plotsizeunit);
        fields.put("yearBuilt", builtinyear);
        fields.put("streetView", streetview);
        fields.put("streetWidth", streetwidth);
        fields.put("streetWidthUnit", streetWidthUnit);

        fields.put("description", description);
        fields.put("extrabuildingNo", extrabuilding);
        fields.put("extrashowroomNo", extrashowroom);
        fields.put("revenue", revenue);

        fields.put("state", state);
        fields.put("city", city);
        fields.put("area", locality);
        fields.put("zipcode", zip);
        fields.put("apartmentNo", suitno);
        fields.put("buildingNo", buildingno);
        fields.put("address", address);
        // imageCaption
        fields.put("lat", lat);
        fields.put("long", longg);
        fields.put("country", country);
//boolean
        fields.put("balcony", balcony);
        fields.put("garden", garden);
        fields.put("parking", parking);
        fields.put("modularKitchen", modulerkitchen);

        fields.put("purpose", porpose);
        fields.put("available", availablefor);

        fields.put("indoor", indoor);
        fields.put("outdoor", outdoor);
        fields.put("furnish", furnishing);
        fields.put("parkingOption", parkingOptions);
        fields.put("views", view);

        return fields;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {


                //address1 = data.getStringExtra("ADDRESS");
                lat = data.getStringExtra("LAT");
                longg = data.getStringExtra("LONG");


                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(Double.parseDouble(data.getStringExtra("LAT")), Double.parseDouble(data.getStringExtra("LONG")), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses != null) {
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String zip = addresses.get(0).getPostalCode();
                    String country = addresses.get(0).getCountryName();
                    String locality = addresses.get(0).getLocality();
                    String address = addresses.get(0).getAddressLine(0);

                    layoutAddAddressBinding.tvAppLanguage.setText(country);
                    layoutAddAddressBinding.tvSpeakLan.setText(state);
                    layoutAddAddressBinding.tvCurrency.setText(city);
                    layoutAddAddressBinding.tvMeasurement3.setText(zip);
                    layoutAddAddressBinding.tvMeasurement.setText(locality);
                    layoutAddAddressBinding.tvMeasurement2.setText(address);


                }
            }
            //  edtPickUpLocation.setText(address1);
        }

    }
}
