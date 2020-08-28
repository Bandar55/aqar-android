package com.aqar55.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.aqar55.R;
import com.aqar55.activitys.ActivityMyProfileSocial;
import com.aqar55.databinding.LayoutMyProfileBinding;
import com.aqar55.helper.ChangeImageRotation;
import com.aqar55.helper.ImageController;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.PermissionManager;
import com.aqar55.helper.Toaster;
import com.aqar55.helper.Utils;
import com.aqar55.model.DataGenerator;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.ProfessionalDataResponse;
import com.aqar55.model.ProfessionalResponse;
import com.aqar55.model.SubCatResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST_MENU;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST_PROFILE;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE_MENU;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE_PROFILE;

public class My_Profile_Fragment extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "My_Profile_Fragment";
    private LayoutMyProfileBinding layoutMyProfileBinding;
    private View view;
    private boolean isStatus, isGender, isMeasurement, isCurrency, isSubCategory, isCountry, isCategory, isSearchId, isSearchIdType;


    private List<ProfessionalDataResponse> professionalDataResponseList;
    private final int REQUEST_PERMISSION_CAMERA_STORAGE = 101;
    private PermissionManager permissionManager;
    private String mImagePath = "", mGovImagePath1 = "", mGovImagePath2 = "";
    private TextView tvSearchID, tvSelectIDType, tv_identy_fisrt, iv_indetity_prof_pic_secound;
    private Spinner spinnerSelectIdTypeFirst, sp_selected_type_second;
    private ImageView ivUploadIDs, iv_indetity_prof_secound;
    private EditText edt_search, edt_searchFirst;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    final Calendar myCalendar = Calendar.getInstance();
    private String userId = "";
    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutMyProfileBinding = DataBindingUtil.setContentView(this, R.layout.layout_my_profile);
        initViews();

        getCalentInstance();
        map = new HashMap<>();
        tv_identy_fisrt.setOnClickListener(this);
        iv_indetity_prof_pic_secound.setOnClickListener(this);
        tvSearchID.setOnClickListener(this);
        tvSelectIDType.setOnClickListener(this);

        permissionManager = new PermissionManager(this);
        layoutMyProfileBinding.ivBack.setOnClickListener(this);
        layoutMyProfileBinding.tvAccountStatus.setOnClickListener(this);
        layoutMyProfileBinding.tvMeasurement.setOnClickListener(this);
        layoutMyProfileBinding.tvGender.setOnClickListener(this);
        layoutMyProfileBinding.tvCurrency.setOnClickListener(this);
        layoutMyProfileBinding.tvSubcategory.setOnClickListener(this);
        layoutMyProfileBinding.edtCountry.setOnClickListener(this);
        layoutMyProfileBinding.edtMemberSince.setOnClickListener(this);
        layoutMyProfileBinding.tvCategory.setOnClickListener(this);
        layoutMyProfileBinding.ivTakeImage.setOnClickListener(this);
        layoutMyProfileBinding.tvSaveMyProfile.setOnClickListener(this);
        layoutMyProfileBinding.tvCancelMyProfile.setOnClickListener(this);


        // layoutMyProfileBinding.spnCategory.setOnItemClickListener(this);


        setAccountStatusSpinner();
        setGenderSpinner();
        setMeasurement();
        setSearchIdFirst();
        setSearchIdSecondSpinner();

        setCurrencySpinner();
        //setCountrySpinner();
        getPrepareDataCategory();

        getMyProfileData();


    }

    private void getCalentInstance() {


        //server comes format ?
        String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date date = sdf.parse((ModelManager.modelManager().getCurrentUser().getCreated()));
            System.out.println(date);
            String dateText = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
            String your_format = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
            System.out.println(your_format);
            String[] splitted = your_format.split(" ");


            layoutMyProfileBinding.edtMemberSince.setText(your_format);
            // Now you can set the TextView here

        } catch (Exception e) {
            System.out.println(e.toString()); //date format error
        }

    }

    ;


    private void initViews() {
        tvSearchID = findViewById(R.id.tvSearchID);
        tvSelectIDType = findViewById(R.id.tvSelectIDType);
        sp_selected_type_second = findViewById(R.id.sp_selected_type_second);
        spinnerSelectIdTypeFirst = findViewById(R.id.spinnerSelectIdTypeFirst);
        tv_identy_fisrt = findViewById(R.id.tv_identy_fisrt);
        iv_indetity_prof_pic_secound = findViewById(R.id.iv_indetity_prof_pic_secound);
        ivUploadIDs = findViewById(R.id.ivUploadIDs);
        iv_indetity_prof_secound = findViewById(R.id.iv_indetity_prof_secound);
        edt_search = findViewById(R.id.edt_search);
        edt_searchFirst = findViewById(R.id.edt_searchFirst);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;

            case R.id.tv_identy_fisrt:
                checkPermission(2);
                break;

            case R.id.iv_indetity_prof_pic_secound:
                checkPermission(3);
                break;

            case R.id.tvSearchID:
                isSearchId = true;
                spinnerSelectIdTypeFirst.performClick();
                break;

            case R.id.tvSelectIDType:
                isSearchIdType = true;
                sp_selected_type_second.performClick();
                break;

            case R.id.tvSaveMyProfile:
               /* if (validation())
                    return;*/


                HashMap<String, Object> prepareData = hashmapData();

                startActivity(ActivityMyProfileSocial.getIntent(this, prepareData));
                //  hitUpdateProfileApi();
                break;

            case R.id.tvCancelMyProfile:
                finish();
                break;

            case R.id.ivTakeImage:
                checkPermission(1);
                break;

            case R.id.tvAccountStatus:
                isStatus = true;
                layoutMyProfileBinding.spnAccountStatus.performClick();
                break;

            case R.id.tvMeasurement:
                isMeasurement = true;
                layoutMyProfileBinding.spnMeasurement.performClick();
                break;

            case R.id.tvGender:
                isGender = true;
                layoutMyProfileBinding.spnGender.performClick();
                break;

            case R.id.tvCurrency:
                isCurrency = true;
                layoutMyProfileBinding.spnCurrency.performClick();
                // setCurrenceyPicker();
                break;

            case R.id.tvSubcategory:
                isSubCategory = true;
                layoutMyProfileBinding.spnSubCategory.performClick();
                break;


            case R.id.tvCategory:
                isCategory = true;
                layoutMyProfileBinding.spnCategory.performClick();
                break;

        }
    }

    private HashMap<String, Object> hashmapData() {

        map.put("userId", ModelManager.modelManager().getCurrentUser().getId());
        map.put("status", layoutMyProfileBinding.tvAccountStatus.getText().toString());
        map.put("fullName", layoutMyProfileBinding.edtProfileName.getText().toString());
        map.put("gender", layoutMyProfileBinding.tvGender.getText().toString());
        map.put("memberSince", layoutMyProfileBinding.edtMemberSince.getText().toString());
        map.put("country", layoutMyProfileBinding.edtCountry.getText().toString());
        map.put("currency", layoutMyProfileBinding.tvCurrency.getText().toString());
        map.put("email", layoutMyProfileBinding.edtEmailId.getText().toString());
        map.put("userId", userId);
        map.put("govtIdType1", tvSearchID.getText().toString());
        map.put("govtIdNumber1", edt_search.getText().toString());
        map.put("govtIdType2", tvSelectIDType.getText().toString());
        map.put("govtIdNumber2", edt_searchFirst.getText().toString());
        map.put("category", layoutMyProfileBinding.tvCategory.getText().toString());
        map.put("subCategory", layoutMyProfileBinding.tvSubcategory.getText().toString());


        if (mGovImagePath1.isEmpty()) {
            map.put("mGovImagePath1", "");
        } else {
            map.put("mGovImagePath1", getGorvImageFirst());
        }
        if (mGovImagePath2.isEmpty()) {
            map.put("mGovImagePath2", "");
        } else {
            map.put("mGovImagePath2", getGorvSecound());
        }


        if (mImagePath.isEmpty()) {
            map.put("mImagePath", "");
        } else {
            map.put("mImagePath", getProfileImageFile());
        }
        map.put("govtIdType1", tvSearchID.getText().toString());
        map.put("govtIdType2", tvSelectIDType.getText().toString());

        return map;


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


    public File getGorvImageFirst() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(mGovImagePath1);
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getGorvSecound() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(mGovImagePath2);
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


   /* private boolean validation() {
        if (mImagePath.isEmpty()) {
            Toast.makeText(this, "Please pick profile image!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mGovImagePath1.isEmpty()) {
            Toast.makeText(this, "Please pick Government Id 1!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mGovImagePath2.isEmpty()) {
            Toast.makeText(this, "Please pick Government Id 2!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
*/

    private void setSearchIdSecondSpinner() {
        String[] searchId = DataGenerator.getSelectIdType();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchId) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }

        };
        sp_selected_type_second.setAdapter(adapter);
        sp_selected_type_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String searchId = parent.getItemAtPosition(position).toString();
                if (isSearchIdType)
                    tvSelectIDType.setText(searchId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSearchIdFirst() {
        String[] searchId = DataGenerator.getSelectIdType();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchId) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }

        };
        spinnerSelectIdTypeFirst.setAdapter(adapter);
        spinnerSelectIdTypeFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String searchId = parent.getItemAtPosition(position).toString();
                if (isSearchId)
                    tvSearchID.setText(searchId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCategorySpinner(ArrayList<String> arrayList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }

        };
        layoutMyProfileBinding.spnCategory.setAdapter(adapter);
        layoutMyProfileBinding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (isCategory) {
                    getPrepareSubCatList(professionalDataResponseList.get(position).getId());
                    layoutMyProfileBinding.tvCategory.setText(category);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setSubCategorySpinner(ArrayList<String> arrayList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }


            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }


        };
        layoutMyProfileBinding.spnSubCategory.setAdapter(adapter);
        layoutMyProfileBinding.spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subCategory = parent.getItemAtPosition(position).toString();
                if (isSubCategory)
                    layoutMyProfileBinding.tvSubcategory.setText(subCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setMeasurement() {
        String[] measurements = DataGenerator.getMeasurement();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, measurements) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        layoutMyProfileBinding.spnMeasurement.setAdapter(adapter);
        layoutMyProfileBinding.spnMeasurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String measurement = parent.getItemAtPosition(position).toString();
                if (isMeasurement)
                    layoutMyProfileBinding.tvMeasurement.setText(measurement);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAccountStatusSpinner() {
        String[] accountStatus = DataGenerator.getAccountStatus();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accountStatus) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        layoutMyProfileBinding.spnAccountStatus.setAdapter(adapter);
        layoutMyProfileBinding.spnAccountStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                if (isStatus)
                    layoutMyProfileBinding.tvAccountStatus.setText(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGenderSpinner() {
        String[] genders = DataGenerator.getGender();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genders) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        layoutMyProfileBinding.spnGender.setAdapter(adapter);
        layoutMyProfileBinding.spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
                if (isGender)
                    layoutMyProfileBinding.tvGender.setText(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setCurrencySpinner() {
        String[] currency = DataGenerator.getCurrency();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currency) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;

            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        layoutMyProfileBinding.spnCurrency.setAdapter(adapter);
        layoutMyProfileBinding.spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
                if (isCurrency)
                    layoutMyProfileBinding.tvCurrency.setText(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void enableAllFields() {
        layoutMyProfileBinding.ivTakeImage.setEnabled(true);
        layoutMyProfileBinding.tvAccountStatus.setEnabled(true);
        layoutMyProfileBinding.tvCategory.setEnabled(true);
        layoutMyProfileBinding.edtCountry.setEnabled(true);
        layoutMyProfileBinding.tvSubcategory.setEnabled(true);
        layoutMyProfileBinding.tvCurrency.setEnabled(true);
        layoutMyProfileBinding.tvGender.setEnabled(true);
        layoutMyProfileBinding.tvMeasurement.setEnabled(true);
        layoutMyProfileBinding.edtProfileName.setEnabled(true);
        layoutMyProfileBinding.edtEmailId.setEnabled(true);
        layoutMyProfileBinding.edtMemberSince.setEnabled(true);
        layoutMyProfileBinding.ivTakeImage.setClickable(true);
        tvSearchID.setEnabled(true);
        tvSelectIDType.setEnabled(true);
        tv_identy_fisrt.setEnabled(true);
        iv_indetity_prof_pic_secound.setEnabled(true);
        edt_search.setEnabled(true);
        edt_searchFirst.setEnabled(true);
        layoutMyProfileBinding.ivTakeImage.setEnabled(true);

    }

    // hit userDetails API
    private void getMyProfileData() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetUserDetails> call = api.getUserDetails(ModelManager.modelManager().getCurrentUser().getId());
        call.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {
                //Toast.makeText(this, ""+response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        setDataToViews(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {
                Toaster.customToast("");
            }
        });


    }

    // set all data to fields
    private void setDataToViews(Response<GetUserDetails> response) {

        layoutMyProfileBinding.tvAccountStatus.setText(response.body().getData().getStatus());
        // layoutMyProfileBinding.edtMemberSince.setText(response.body().getData().getMembersince());
        layoutMyProfileBinding.tvProfId.setText(response.body().getData().getProfessionalid());
        layoutMyProfileBinding.edtProfileName.setText(response.body().getData().getFullname());
        layoutMyProfileBinding.tvSubcategory.setText(response.body().getData().getSubcategory());
        layoutMyProfileBinding.tvGender.setText(response.body().getData().getGender());
        layoutMyProfileBinding.edtCountry.setText(response.body().getData().getCountry());
        layoutMyProfileBinding.edtEmailId.setText(response.body().getData().getEmail());
        layoutMyProfileBinding.tvNumber.setText(response.body().getData().getMobilenumber());
        layoutMyProfileBinding.tvMeasurement.setText(response.body().getData().getMeasurement());
        layoutMyProfileBinding.tvCurrency.setText(response.body().getData().getCurrency());
        layoutMyProfileBinding.tvCategory.setText(response.body().getData().getCategory());


        map.put("facebookUrl", response.body().getData().getFacebookUrl());
        map.put("googleplusUrl", response.body().getData().getGoogleplusUrl());
        map.put("twitterUrl", response.body().getData().getTwitterUrl());
        map.put("linkedinUrl", response.body().getData().getLinkedinUrl());
        map.put("snapchatUrl", response.body().getData().getSnapchatUrl());
        map.put("description",response.body().getData().getDescription());
        map.put("specialities", response.body().getData().getSpecialities());
        map.put("areaCovered", response.body().getData().getAreaCovered());
        map.put("projectAchieved", response.body().getData().getProjectAchieved());


        userId = response.body().getData().getId();

        tvSearchID.setText(response.body().getData().getGovtIdType1());
        tvSelectIDType.setText(response.body().getData().getGovtIdType2());
        edt_search.setText(response.body().getData().getGovtIdNumber1());
        edt_searchFirst.setText(response.body().getData().getGovtIdNumber2());

        if (response.body().getData().getGovtIdImage1() != null && !response.body().getData().getGovtIdImage1().isEmpty()) {

            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String file_name = String.format(Locale.getDefault(), "menu%03d.jpg", 0);
                    String file_path = My_Profile_Fragment.this.getFilesDir().getAbsolutePath() + "/Temp";
                    final File file = new File(file_path, file_name);
                    try {
                        FileUtils.copyURLToFile(new URL(response.body().getData().getGovtIdImage1()), file);
                        mGovImagePath1 = file.getAbsolutePath();
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

        if (response.body().getData().getGovtIdImage2() != null && !response.body().getData().getGovtIdImage2().isEmpty()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String file_name = String.format(Locale.getDefault(), "menu%03d.jpg", 1);
                    String file_path = My_Profile_Fragment.this.getFilesDir().getAbsolutePath() + "/Temp";
                    final File file = new File(file_path, file_name);
                    try {
                        FileUtils.copyURLToFile(new URL(response.body().getData().getGovtIdImage2()), file);
                        mGovImagePath2 = file.getAbsolutePath();
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


        if (response.body().getData().getGovtIdImage1() != null && !response.body().getData().getGovtIdImage1().isEmpty())
            Picasso.get().load(response.body().getData().getGovtIdImage1()).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIDs);

        if (response.body().getData().getGovtIdImage2() != null && !response.body().getData().getGovtIdImage2().isEmpty())
            Picasso.get().load(response.body().getData().getGovtIdImage2()).resize(300, 250).centerCrop(Gravity.CENTER).into(iv_indetity_prof_secound);

        if (response.body().getData().getProfileimage() != null && !response.body().getData().getProfileimage().isEmpty()) {
            Picasso.get().load(response.body().getData().getProfileimage()).resize(300, 250).centerCrop(Gravity.CENTER).into(layoutMyProfileBinding.ivMyProfile);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String file_name = String.format(Locale.getDefault(), "menu%03d.jpg", 2);
                    String file_path = My_Profile_Fragment.this.getFilesDir().getAbsolutePath() + "/Temp";
                    final File file = new File(file_path, file_name);
                    try {
                        FileUtils.copyURLToFile(new URL(response.body().getData().getProfileimage()), file);
                        mImagePath = file.getAbsolutePath();
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
    }

    //api calling for category
    private void getPrepareDataCategory() {
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<ProfessionalResponse> getCategoryList = api.getCategoryList();

        getCategoryList.enqueue(new Callback<ProfessionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalResponse> call, @NonNull Response<ProfessionalResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(My_Profile_Fragment.this).hideDialog();
                    if (response != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            professionalDataResponseList = response.body().getData();
                            ProfessionalDataResponse professionalDataResponse = new ProfessionalDataResponse();
                            professionalDataResponse.setName("Select Category");
                            professionalDataResponseList.add(0, professionalDataResponse);

                            ArrayList<String> arrayList = new ArrayList<>();
                            for (int i = 0; i < professionalDataResponseList.size(); i++) {
                                arrayList.add(professionalDataResponseList.get(i).getName());
                            }
                            setCategorySpinner(arrayList);

                        } else {
                            Toaster.toast(response.body().getResponseMessage());

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfessionalResponse> call, Throwable t) {
                MyDialog.getInstance(My_Profile_Fragment.this).hideDialog();

            }
        });


    }

    //subcat api calling here
    private void getPrepareSubCatList(String id) {

        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        final Call<SubCatResponse> getCategoryList = api.getSubCatList(id);

        getCategoryList.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubCatResponse> call, @NonNull Response<SubCatResponse> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(My_Profile_Fragment.this).hideDialog();
                    if (response != null) {

                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            List<SubCatResponse.Data> professionalSubCatList = response.body().getData();
                            SubCatResponse.Data data = new SubCatResponse.Data();
                            data.setName("Select Subcategory");
                            professionalSubCatList.add(0, data);

                            ArrayList<String> arrayList = new ArrayList<>();
                            for (int i = 0; i < professionalSubCatList.size(); i++) {
                                arrayList.add(professionalSubCatList.get(i).getName());
                            }
                            setSubCategorySpinner(arrayList);
                        } else {
                            Toaster.toast(response.body().getResponseMessage());
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                MyDialog.getInstance(My_Profile_Fragment.this).hideDialog();
                t.fillInStackTrace();


            }
        });
    }


    private void checkPermission(int i) {
        if (ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openBottomSheetBanner(i);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
            // We've been denied once before. Explain why we need the permission, then ask again.
            Utils.showDialog(this, "Camera & Gallery permissions are required to upload profile images!", "Ask Permission", "Discard", (dialog, which) -> {
                if (which == -1)
                    permissionManager.requestPermission(new String[]{CAMERA, READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA_STORAGE);
                else
                    dialog.dismiss();
            });
        } else {
            // We've never asked. Just do it.
            permissionManager.requestPermission(new String[]{CAMERA, READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA_STORAGE);
        }
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

                ImageController.dispatchCameraIntent(this);
            } else if (i == 2) {
                ImageController.dispatchCameraMenuIntent(this);
            } else {

                ImageController.dispatchCameraProfileIntent(this);
            }

            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.gallery_view).setOnClickListener(v -> {
            if (i == 1) {
                ImageController.dispatchGallaryIntent(this);
            } else if (i == 2) {
                ImageController.dispatchMenuGallaryIntent(this);
            } else {
                ImageController.dispatchProfileGalleryIntent(this);
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
            Picasso.get().load(file).resize(300, 250).centerCrop(Gravity.CENTER).into(layoutMyProfileBinding.ivMyProfile);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            String filepath = ImageController.getPath(this, imageURI);
            File imageFile = new File(filepath);
            mImagePath = imageFile.getAbsolutePath();
            Picasso.get().load(imageFile).resize(300, 250).centerCrop(Gravity.CENTER).into(layoutMyProfileBinding.ivMyProfile);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_MENU && resultCode == RESULT_OK) {
            File file = new File(ImageController.getCurrentPhotoPath());
            //imageList.add(file);
            //photoAdapter.notifyDataSetChanged();
            mGovImagePath1 = file.getAbsolutePath();
            Picasso.get().load(file).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIDs);
        } else if (requestCode == GALLERY_REQUEST_MENU && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            String filepath = ImageController.getPath(this, imageURI);
            File imageFile = new File(filepath);
            //imageList.add(imageFile);
            //photoAdapter.notifyDataSetChanged();
            mGovImagePath1 = imageFile.getAbsolutePath();
            Picasso.get().load(imageFile).resize(300, 250).centerCrop(Gravity.CENTER).into(ivUploadIDs);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_PROFILE && resultCode == RESULT_OK) {
            File file = new File(ImageController.getCurrentPhotoPath());
            //imageList.add(file);
            //photoAdapter.notifyDataSetChanged();
            mGovImagePath2 = file.getAbsolutePath();
            Picasso.get().load(file).resize(300, 250).centerCrop(Gravity.CENTER).into(iv_indetity_prof_secound);
        } else if (requestCode == GALLERY_REQUEST_PROFILE && resultCode == RESULT_OK) {
            Uri imageURI = data.getData();
            String filepath = ImageController.getPath(this, imageURI);
            File imageFile = new File(filepath);
            //imageList.add(imageFile);
            //photoAdapter.notifyDataSetChanged();
            mGovImagePath2 = imageFile.getAbsolutePath();
            Picasso.get().load(imageFile).resize(300, 250).centerCrop(Gravity.CENTER).into(iv_indetity_prof_secound);
        }

    }





    private void disableAllFields() {
        layoutMyProfileBinding.ivTakeImage.setEnabled(false);
        layoutMyProfileBinding.tvAccountStatus.setEnabled(false);
        layoutMyProfileBinding.tvCategory.setEnabled(false);
        layoutMyProfileBinding.edtCountry.setEnabled(false);
        layoutMyProfileBinding.tvSubcategory.setEnabled(false);
        layoutMyProfileBinding.tvCurrency.setEnabled(false);
        layoutMyProfileBinding.tvGender.setEnabled(false);
        layoutMyProfileBinding.tvMeasurement.setEnabled(false);
        layoutMyProfileBinding.edtProfileName.setEnabled(false);
        layoutMyProfileBinding.edtEmailId.setEnabled(false);
        layoutMyProfileBinding.edtMemberSince.setEnabled(false);
        layoutMyProfileBinding.ivTakeImage.setClickable(false);
        tvSearchID.setEnabled(false);
        tvSelectIDType.setEnabled(false);
        tv_identy_fisrt.setEnabled(false);
        iv_indetity_prof_pic_secound.setEnabled(false);
        edt_search.setEnabled(false);
        edt_searchFirst.setEnabled(false);
        layoutMyProfileBinding.ivTakeImage.setEnabled(false);

    }


}
