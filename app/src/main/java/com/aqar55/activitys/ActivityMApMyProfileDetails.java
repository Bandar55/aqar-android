package com.aqar55.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.adapters.TinyArrayAdapter;
import com.aqar55.helper.ChangeImageRotation;
import com.aqar55.helper.ImageController;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.helper.Utils;
import com.aqar55.helper.Validator;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.GetUserDetails;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aqar55.helper.ApplicationManager.getContext;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST_MENU;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE_MENU;

public class ActivityMApMyProfileDetails extends AppCompatActivity {

    View view;
    @BindView(R.id.tvState)
    EditText tvState;
    @BindView(R.id.tv_city)
    EditText tvCity;
    @BindView(R.id.tv_zip_code)
    EditText tvZipCode;
    @BindView(R.id.tv_area)
    EditText tvArea;
    @BindView(R.id.tvSearchID)
    TextView tvSearchID;
    @BindView(R.id.tvSelectIDType)
    TextView tvSelectIDType;
    @BindView(R.id.spinnerSelectIdTypeFirst)
    Spinner spinnerSelectIdTypeFirst;
    @BindView(R.id.sp_selected_type_second)
    Spinner spinnerSelectIdTypeSecond;
    @BindView(R.id.ivUploadIDs)
    ImageView ivUploadIdFirst;
    @BindView(R.id.iv_indetity_prof_secound)
    ImageView ivUploadIdSecound;
    @BindView(R.id.edt_website_url)
    EditText edtWebSiteUrl;
    @BindView(R.id.edt_search)
    EditText edtSearchGrvIdFirst;
    @BindView(R.id.edt_searchFirst)
    EditText edtSearchFirst;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_phoneNo)
    EditText edtMobileNo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvAddressHeading)
    TextView tvAddressHeading;
    @BindView(R.id.tvPhoneHeading)
    TextView tvPhoneHeading;

    @BindView(R.id.chat_imageview_contact_admin)
    ImageView ivDelete;

    private String country = "", city = "", state = "", zip = "", addess = "", locality = "";
    private double lat, longg;
    private String latitude = "";
    private String longitude = "";
    private GetUserDetails.DataEntity responseData;
    private String mImagePath = "";
    private String mImagePathScound = "";
    private String userIdd="";


    private String[] selectIdType;
    private HashMap<String, Object> parameterData;

    Spinner.OnItemSelectedListener onItemSelectedListenerActive = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if(position==0)
                return;
            tvSearchID.setText(selectIdType[position]);

        }


        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //  Toaster.toast("Please select the timeslot in which you want to add the menu");
        }
    };
    Spinner.OnItemSelectedListener onItemSelectedListenerActiveSecond = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if(position==0)
                return;
            tvSelectIDType.setText(selectIdType[position]);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //  Toaster.toast("Please select the timeslot in which you want to add the menu");
        }
    };

    public static Intent getIntent(Context context, HashMap<String, Object> objectHashMap, GetUserDetails.DataEntity responseData) {
        Intent intent = new Intent(context, ActivityMApMyProfileDetails.class);
        intent.putExtra("Data", objectHashMap);
        intent.putExtra("ApiData", responseData);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_professional_profile);
        ButterKnife.bind(this);

        prepareIdTypeDataFirst();
        prepareIdTypeDataSecond();

        if (getIntent() != null) {
            parameterData = (HashMap<String, Object>) getIntent().getSerializableExtra("Data");

            responseData = (GetUserDetails.DataEntity) getIntent().getSerializableExtra("ApiData");

            if (parameterData.get("fromActivity").toString().equalsIgnoreCase("professional")) {
                tvTitle.setText("My Professional Profile");
                ivDelete.setVisibility(View.VISIBLE);
                tvAddressHeading.setText(getString(R.string.professional_address));
                tvPhoneHeading.setText(getString(R.string.professional_phone));
                userIdd=responseData.getId();

            } else {
                tvTitle.setText("My Business Profile");
                tvAddressHeading.setText(getString(R.string.business_address));
                tvPhoneHeading.setText(getString(R.string.business_phone));

                if(responseData!=null)
                userIdd=responseData.getId();
                ivDelete.setVisibility(View.VISIBLE);
            }

        }
        if(responseData!=null)
        prepareUi(responseData);
    }

    //here prepare ui
    void prepareUi(GetUserDetails.DataEntity responseData) {

        tvState.setText(responseData.getState());
        tvCity.setText(responseData.getCity());
        tvZipCode.setText(responseData.getZipcode());
        tvArea.setText(responseData.getArea());
        edtMobileNo.setText(responseData.getMobilenumber());
        edtWebSiteUrl.setText(responseData.getWebsite());
        edtEmail.setText(responseData.getEmail());
        if(responseData.getGovtIdImage1()!=null && !responseData.getGovtIdImage1().isEmpty()){
            Picasso.get().load(responseData.getGovtIdImage1()).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdFirst);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String file_name = String.format(Locale.getDefault(), "menu%03d.jpg", 0);
                    String file_path = ActivityMApMyProfileDetails.this.getFilesDir().getAbsolutePath() + "/Temp";
                    final File file = new File(file_path, file_name);
                    try {
                        FileUtils.copyURLToFile(new URL(responseData.getGovtIdImage1()), file);
                        mImagePath=file.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            };
            thread.start();
            try {
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        if(responseData.getGovtIdImage2()!=null && !responseData.getGovtIdImage2().isEmpty()){
            Picasso.get().load(responseData.getGovtIdImage2()).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdSecound);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String file_name = String.format(Locale.getDefault(), "menu%03d.jpg", 1);
                    String file_path = ActivityMApMyProfileDetails.this.getFilesDir().getAbsolutePath() + "/Temp";
                    final File file = new File(file_path, file_name);
                    try {
                        FileUtils.copyURLToFile(new URL(responseData.getGovtIdImage2()), file);
                        mImagePathScound=file.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            };
            thread.start();
            try {
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        edtSearchGrvIdFirst.setText(responseData.getGovtIdNumber1());
        edtSearchFirst.setText(responseData.getGovtIdNumber2());
        tvSearchID.setText(responseData.getGovtIdType1());
        tvSelectIDType.setText(responseData.getGovtIdType2());
        latitude = responseData.getLat();
        longitude = responseData.getLng();
        parameterData.put("lat", latitude);
        parameterData.put("long", longitude);


    }


    private void prepareIdTypeDataSecond() {
        selectIdType = DataGenerator.getSelectIdType();
        TinyArrayAdapter<String> slotAdapter = new TinyArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectIdType);
        spinnerSelectIdTypeSecond.setAdapter(slotAdapter);
        spinnerSelectIdTypeSecond.setSelection(0);
        spinnerSelectIdTypeSecond.setOnItemSelectedListener(onItemSelectedListenerActiveSecond);
    }

    private void prepareIdTypeDataFirst() {
        selectIdType = DataGenerator.getSelectIdType();
        TinyArrayAdapter<String> slotAdapter = new TinyArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectIdType);
        spinnerSelectIdTypeFirst.setAdapter(slotAdapter);
        spinnerSelectIdTypeFirst.setSelection(0);
        spinnerSelectIdTypeFirst.setOnItemSelectedListener(onItemSelectedListenerActive);
    }


    @OnClick({R.id.tv_pick_locatuion, R.id.iv_indetity_prof_pic_secound, R.id.tv_identy_fisrt, R.id.tvSearchBusiness, R.id.ivBack
            , R.id.tvSearchID, R.id.tvSelectIDType,R.id.chat_imageview_contact_admin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchBusiness:
                if (!isValide())
                    return;
                HashMap<String, Object> data = prepared();
                startActivity(ActivityMyProfessionalSocialUrl.getIntent(this, data,responseData));
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_pick_locatuion:
                startActivityForResult(AddressPicker.getIntent(ActivityMApMyProfileDetails.this), 100);

                break;
            case R.id.iv_indetity_prof_pic_secound:
                openBottomSheetBanner(1);

                break;
            case R.id.tv_identy_fisrt:
                openBottomSheetBanner(2);
                break;
            case R.id.tvSearchID:
                spinnerSelectIdTypeFirst.performClick();
                break;
            case R.id.tvSelectIDType:
                spinnerSelectIdTypeSecond.performClick();
                break;

            case R.id.chat_imageview_contact_admin:

                if(userIdd.isEmpty())
                    return;
                showConfirmation();
                break;
        }
    }

    private HashMap<String, Object> prepared() {
        parameterData.put("city", tvCity.getText().toString());
        parameterData.put("area", tvArea.getText().toString());
        parameterData.put("state", tvState.getText().toString());
        parameterData.put("zipcode", tvZipCode.getText().toString());
        parameterData.put("mobileNumber", edtMobileNo.getText().toString());
        parameterData.put("website", edtWebSiteUrl.getText().toString());
        parameterData.put("govtIdNumber1", edtSearchGrvIdFirst.getText().toString());
        parameterData.put("govtIdNumber2", edtSearchFirst.getText().toString());
        parameterData.put("email", edtEmail.getText().toString());
        if(mImagePath.isEmpty()){
            parameterData.put("govtIdImage1", "");
        }else {
            parameterData.put("govtIdImage1", getProfileImageFile());
        }
        if (mImagePathScound.isEmpty()){
            parameterData.put("govtIdImage2", "");
        }else {
            parameterData.put("govtIdImage2", getProfileImageFileSecound());
        }
        parameterData.put("govtIdType1", tvSearchID.getText().toString());
        parameterData.put("govtIdType2", tvSelectIDType.getText().toString());
        return parameterData;
    }

    private boolean isValide() {
        boolean isValide = true;

        if(tvState.getText().toString().isEmpty()){
            Toaster.toast("Please enter address");
            isValide = false;
        }else if (Utils.getProperText(tvCity).isEmpty()) {
            Toaster.toast("Please enter city name");
            isValide = false;

        }else if (Utils.getProperText(tvZipCode).isEmpty()) {
            Toaster.toast("Please enter zip code");
            isValide = false;

        }else if (Utils.getProperText(tvArea).isEmpty()) {
            Toaster.toast("Please enter area");
            isValide = false;
        } else if (Utils.getProperText(edtMobileNo).isEmpty()) {
            Toaster.toast("Please enter mobile number");
            isValide = false;

        } else if (!Patterns.WEB_URL.matcher(Utils.getProperText(edtWebSiteUrl)).matches()) {
           Toaster.toast("Please enter website");
           isValide = false;

        } else if (Utils.getProperText(edtEmail).isEmpty()) {
            Toaster.toast("Please enter email id");
            isValide = false;
        } else if (!Validator.isValidEmail(Utils.getProperText(edtEmail))) {
            Toaster.toast("Please enter valide email id");
            isValide = false;
        } else if (getProfileImageFile() == null) {
            Toaster.toast("Please select first government ID's to get verify ");
            isValide = false;

        } else if (getProfileImageFileSecound() == null) {
            Toaster.toast("Please  secound government ID's to get verify ");
            isValide = false;
        } else if (Utils.getProperText(edtSearchFirst).isEmpty()) {
            Toaster.toast("Please enter first government ID's  number to get verify ");
            isValide = false;
        } else if (Utils.getProperText(edtSearchFirst).isEmpty()) {
            Toaster.toast("Please enter secound government ID's number to get verify ");
            isValide = false;
        }else if (Utils.getProperText(tvSearchID).isEmpty()) {
            Toaster.toast("Please enter first government ID's type");
            isValide = false;
        }else if (Utils.getProperText(tvSelectIDType).isEmpty()) {
            Toaster.toast("Please enter second government ID's type");
            isValide = false;
        }


        return isValide;
    }




    private void showConfirmation() {
        final android.app.Dialog dialog = new android.app.Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.findViewById(R.id.btn_signup).setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.findViewById(R.id.btn_login).setOnClickListener(v -> {
            dialog.dismiss();
            deleteProfile();


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }


    //delete profile for business and normal professional
    private void deleteProfile() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetUserDetails> call = api.deleteProfessionalOrBusinessMap(ModelManager.modelManager().getCurrentUser().getId(), userIdd);
        call.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {
                responseData = response.body().getData();
                if (response.isSuccessful()) {

                    startActivity(new Intent(ActivityMApMyProfileDetails.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }


    private void openBottomSheetBanner(int i) {
        View view = getLayoutInflater().inflate(R.layout.custom_bottonsheet_layout, null);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        view.findViewById(R.id.camera_view).setOnClickListener(v -> {
            if (i == 1) {
                //1 for menu images
                ImageController.dispatchCameraMenuIntent(this);
            } else {
                //else as usual for profile
                ImageController.dispatchCameraIntent(this);
            }

            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.gallery_view).setOnClickListener(v -> {
            if (i == 1) {
                //1 for menu images
                ImageController.dispatchMenuGallaryIntent(this);
            } else {
                //else for profile image as usual
                ImageController.dispatchGallaryIntent(this);
            }

            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.cancel_view).setOnClickListener(v -> mBottomSheetDialog.dismiss());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File file = new File(ImageController.getCurrentPhotoPath());

            mImagePath = file.getAbsolutePath();
            Picasso.get().load(file).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdFirst);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            String filepath = ImageController.getPath(this, imageURI);
            File imageFile = new File(filepath);
            mImagePath = imageFile.getAbsolutePath();
            Picasso.get().load(imageFile).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdFirst);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_MENU && resultCode == RESULT_OK) {
            File file = new File(ImageController.getCurrentPhotoPath());
            //imageList.add(file);
            //  photoAdapter.notifyDataSetChanged();
            mImagePathScound = file.getAbsolutePath();
            Picasso.get().load(file).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdSecound);
        } else if (requestCode == GALLERY_REQUEST_MENU && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            String filepath = ImageController.getPath(this, imageURI);
            File imageFile = new File(filepath);
            //imageList.add(imageFile);
            //photoAdapter.notifyDataSetChanged();
            mImagePathScound = imageFile.getAbsolutePath();
            Picasso.get().load(imageFile).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIdSecound);
        } else if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {


                //address1 = data.getStringExtra("ADDRESS");
                latitude = data.getStringExtra("LAT");
                longitude = data.getStringExtra("LONG");
                parameterData.put("lat", latitude);
                parameterData.put("long", longitude);
                // pLat = Double.parseDouble(lat1);
                // pLong = Double.parseDouble(lat2);


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

                    prepareUi(addresses);
                }
            }
            //  edtPickUpLocation.setText(address1);
        }



    }




    private void prepareUi(List<Address> addresses) {
        tvCity.setText(addresses.get(0).getLocality());
        tvState.setText(addresses.get(0).getAdminArea());
        tvZipCode.setText(addresses.get(0).getPostalCode());
        tvArea.setText(addresses.get(0).getLocality());

    }


    public File getProfileImageFile() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(mImagePath);
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getProfileImageFileSecound() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(mImagePathScound);
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


}
