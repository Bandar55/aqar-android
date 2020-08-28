package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.helper.Utils;
import com.aqar55.model.GetUserDetails;
import com.aqar55.model.ProfessionalListResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMyProfileSocial extends AppCompatActivity {
    private static final String TAG = ActivityMyProfessionalSocialUrl.class.getSimpleName();
    @BindView(R.id.edt_fb_url)
    EditText edtFbUrl;
    @BindView(R.id.edt_google_url)
    EditText edtGoogleUrl;
    @BindView(R.id.edt_twitter_url)
    EditText edtTwitterUrl;
    @BindView(R.id.edt_enter_url)
    EditText edtSearchUrl;
    @BindView(R.id.edt_enter_secound_url)
    EditText edtSearchSecoundUrl;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.edt_achieved)
    EditText edtAchievement;
    @BindView(R.id.edt_specialities)
    EditText edtSpecial;
    @BindView(R.id.edt_discver)
    EditText edtDesco;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.delete)
    ImageView ivdelete;
    private List<File> imageUpladeList;
    private HashMap<String, Object> parameterAllData;
    private GetUserDetails.DataEntity responseData;
    public static Intent getIntent(Context context, HashMap<String, Object> hashMap) {
        Intent intent = new Intent(context, ActivityMyProfileSocial.class);
        intent.putExtra("Data", hashMap);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_professional_profile_social);
        ButterKnife.bind(this);
        imageUpladeList = new ArrayList<>();
        ivdelete.setVisibility(View.GONE);


        if (getIntent() != null) {
            parameterAllData = (HashMap<String, Object>) getIntent().getSerializableExtra("Data");
                tvTitle.setText("My Profile");

            edtFbUrl.setText(parameterAllData.get("facebookUrl").toString());
            edtGoogleUrl.setText(parameterAllData.get("googleplusUrl").toString());
            edtTwitterUrl.setText(parameterAllData.get("twitterUrl").toString());
            edtSearchSecoundUrl.setText(parameterAllData.get("linkedinUrl").toString());
            edtSearchUrl.setText(parameterAllData.get("snapchatUrl").toString());

            edtDesc.setText(parameterAllData.get("description").toString());
            edtSpecial.setText(parameterAllData.get("specialities").toString());
            edtDesco.setText(parameterAllData.get("areaCovered").toString());
            edtAchievement.setText(parameterAllData.get("projectAchieved").toString());


        }
    }



    @OnClick({R.id.tvSavePostedProperty, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:
                if (!isValid())
                    return;
                prepare();
                hitUpdateProfileApi();
                break;
            case R.id.ivBack:
                finish();
                break;

        }
    }

    private void hitUpdateProfileApi() {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("userId", RequestBody.create(MediaType.parse("text/plain"), ModelManager.modelManager().getCurrentUser().getId()));
        map.put("status", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("status").toString()));
        map.put("fullName", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("fullName").toString()));
        map.put("gender", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("gender").toString()));
        map.put("memberSince", RequestBody.create(MediaType.parse("text/plain"),parameterAllData.get("memberSince").toString()));
        map.put("country", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("country").toString()));
        map.put("currency", RequestBody.create(MediaType.parse("text/plain"),parameterAllData.get("currency").toString()));
        map.put("email", RequestBody.create(MediaType.parse("text/plain"),parameterAllData.get("email").toString()));
        map.put("userId", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("userId").toString()));
        map.put("govtIdType1", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdType1").toString()));
        map.put("govtIdNumber1", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdNumber1").toString()));
        map.put("govtIdType2", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdType2").toString()));
        map.put("govtIdNumber2", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdNumber2").toString()));
        map.put("category", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("category").toString()));
        map.put("subCategory", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("subCategory").toString()));

        map.put("facebookUrl",RequestBody.create(MediaType.parse("text/plain"), edtFbUrl.getText().toString()));
        map.put("googleplusUrl",RequestBody.create(MediaType.parse("text/plain"), edtGoogleUrl.getText().toString()));
        map.put("twitterUrl", RequestBody.create(MediaType.parse("text/plain"),edtTwitterUrl.getText().toString()));
        map.put("linkedinUrl", RequestBody.create(MediaType.parse("text/plain"),edtSearchSecoundUrl.getText().toString()));
        map.put("snapchatUrl",RequestBody.create(MediaType.parse("text/plain"), edtSearchUrl.getText().toString()));
        map.put("description", RequestBody.create(MediaType.parse("text/plain"),edtDesc.getText().toString()));
        map.put("specialities",RequestBody.create(MediaType.parse("text/plain"), edtSpecial.getText().toString()));
        map.put("areaCovered", RequestBody.create(MediaType.parse("text/plain"),edtDesco.getText().toString()));
        map.put("projectAchieved", RequestBody.create(MediaType.parse("text/plain"),edtAchievement.getText().toString()));

        MyDialog.getInstance(getApplicationContext()).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ProfessionalListResponse> createBusinessOrProfessionalCall;

        if (parameterAllData.get("mImagePath").toString().isEmpty() && parameterAllData.get("mGovImagePath1").toString().isEmpty() && parameterAllData.get("mGovImagePath2").toString().isEmpty()) {
            createBusinessOrProfessionalCall = api.updateProfile(map);
        } else if (!parameterAllData.get("mImagePath").toString().isEmpty() && parameterAllData.get("mGovImagePath1").toString().isEmpty() &&parameterAllData.get("mGovImagePath2").toString().isEmpty()) {
            File profilePath = new File(parameterAllData.get("mImagePath").toString());
            MultipartBody.Part profileFile = MultipartBody.Part.createFormData("profileImage", profilePath.getName(), RequestBody.create(MediaType.parse("image/*"), profilePath));
            createBusinessOrProfessionalCall = api.updateProfile(map, profileFile);
        } else if (parameterAllData.get("mImagePath").toString().isEmpty() && !parameterAllData.get("mGovImagePath1").toString().isEmpty() && parameterAllData.get("mGovImagePath2").toString().isEmpty()) {
            File GovFile1 = new File(parameterAllData.get("mGovImagePath1").toString());
            MultipartBody.Part governmentIdFileFirst = MultipartBody.Part.createFormData("govtIdImage1", GovFile1.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile1));
            createBusinessOrProfessionalCall = api.updateProfile(map, governmentIdFileFirst);
        } else if (!parameterAllData.get("mGovImagePath2").toString().isEmpty() && parameterAllData.get("mImagePath").toString().isEmpty() && parameterAllData.get("mGovImagePath1").toString().isEmpty()) {
            File GovFile2 = new File(parameterAllData.get("mGovImagePath2").toString());
            MultipartBody.Part governmentIdFileSecond = MultipartBody.Part.createFormData("govtIdImage2", GovFile2.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile2));
            createBusinessOrProfessionalCall = api.updateProfile(map, governmentIdFileSecond);
        } else if (!parameterAllData.get("mImagePath").toString().isEmpty() && !parameterAllData.get("mGovImagePath1").toString().isEmpty() && parameterAllData.get("mGovImagePath2").toString().isEmpty()) {
            File profilePath = new File(parameterAllData.get("mImagePath").toString());
            MultipartBody.Part profileFile = MultipartBody.Part.createFormData("profileImage", profilePath.getName(), RequestBody.create(MediaType.parse("image/*"), profilePath));
            File GovFile1 = new File(parameterAllData.get("mGovImagePath1").toString());
            MultipartBody.Part governmentIdFileFirst = MultipartBody.Part.createFormData("govtIdImage1", GovFile1.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile1));
            createBusinessOrProfessionalCall = api.updateProfile(map, profileFile, governmentIdFileFirst);
        } else if (parameterAllData.get("mGovImagePath1").toString().isEmpty()&& !parameterAllData.get("mImagePath").toString().isEmpty()  && ! parameterAllData.get("mGovImagePath2").toString().isEmpty()) {
            File profilePath = new File(parameterAllData.get("mImagePath").toString());
            MultipartBody.Part profileFile = MultipartBody.Part.createFormData("profileImage", profilePath.getName(), RequestBody.create(MediaType.parse("image/*"), profilePath));
            File GovFile2 = new File( parameterAllData.get("mGovImagePath2").toString());
            MultipartBody.Part governmentIdFileSecond = MultipartBody.Part.createFormData("govtIdImage2", GovFile2.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile2));
            createBusinessOrProfessionalCall = api.updateProfile(map, profileFile, governmentIdFileSecond);
        } else {
            File profilePath = new File(parameterAllData.get("mImagePath").toString());
            MultipartBody.Part profileFile = MultipartBody.Part.createFormData("profileImage", profilePath.getName(), RequestBody.create(MediaType.parse("image/*"), profilePath));
            File GovFile1 = new File(parameterAllData.get("mGovImagePath1").toString());
            MultipartBody.Part governmentIdFileFirst = MultipartBody.Part.createFormData("govtIdImage1", GovFile1.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile1));
            File GovFile2 = new File(parameterAllData.get("mGovImagePath2").toString());
            MultipartBody.Part governmentIdFileSecond = MultipartBody.Part.createFormData("govtIdImage2", GovFile2.getName(), RequestBody.create(MediaType.parse("image/*"), GovFile2));
            createBusinessOrProfessionalCall = api.updateProfile(map, profileFile, governmentIdFileFirst, governmentIdFileSecond);
        }

        createBusinessOrProfessionalCall.enqueue(new Callback<ProfessionalListResponse>() {
            @Override
            public void onResponse(Call<ProfessionalListResponse> call, Response<ProfessionalListResponse> response) {
                MyDialog.getInstance(getApplicationContext()).hideDialog();
                if (response.isSuccessful()) {

                    if (response.body().getResponse_message().equalsIgnoreCase("profile updated successfully")) {
                        Toast.makeText(ActivityMyProfileSocial.this, response.body().getResponse_message(), Toast.LENGTH_SHORT).show();


                       BaseManager.saveDataIntoPreferences(response.body().getData().getCurrency(), "setting_currency");
                        startActivity(new Intent(ActivityMyProfileSocial.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ActivityMyProfileSocial.this, response.body().getResponse_message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toaster.toast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfessionalListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                MyDialog.getInstance(getApplicationContext()).hideDialog();
            }
        });
    }









    private boolean isValid() {
        boolean isValide = true;
       /* if (Utils.getProperText(edtFbUrl).isEmpty()) {
            Toaster.toast("Please enter facebookUrl");
            isValide = false;
        } else if (!Utils.isValidURL(Utils.getProperText(edtFbUrl))) {
            Toaster.toast("Please enter Valide url");
            isValide = false;
        } else if (Utils.getProperText(edtGoogleUrl).isEmpty()) {
            Toaster.toast("Please enter Google Url");
            isValide = false;
        } else if (!Utils.isValidURL(Utils.getProperText(edtGoogleUrl))) {
            Toaster.toast("Please enter Valide url");
            isValide = false;
        } else if (Utils.getProperText(edtTwitterUrl).isEmpty()) {
            Toaster.toast("Please enter twitterUrl");
            isValide = false;
        } else if (!Utils.isValidURL(Utils.getProperText(edtTwitterUrl))) {
            Toaster.toast("Please enter Valide url");
            isValide = false;
        } else if (Utils.getProperText(edtSearchSecoundUrl).isEmpty()) {
            Toaster.toast("Please enter linkedinUrl");
            isValide = false;
        } else if (!Utils.isValidURL(Utils.getProperText(edtSearchSecoundUrl))) {
            Toaster.toast("Please enter Valide url");
            isValide = false;
        } else if (Utils.getProperText(edtSearchUrl).isEmpty()) {
            Toaster.toast("Please enter snapchatUrl");
            isValide = false;
        } else if (!Utils.isValidURL(Utils.getProperText(edtSearchUrl))) {
            Toaster.toast("Please enter Valide url");
            isValide = false;
        } else*/ if (Utils.getProperText(edtDesc).isEmpty()) {
            Toaster.toast("Please enter description");
            isValide = false;
        } else if (Utils.getProperText(edtSpecial).isEmpty()) {
            Toaster.toast("Please enter specialities");
            isValide = false;
        } else if (Utils.getProperText(edtDesco).isEmpty()) {
            Toaster.toast("Please enter areaCovered");
            isValide = false;
        } else if (Utils.getProperText(edtAchievement).isEmpty()) {
            Toaster.toast("Please enter projectAchieved");
            isValide = false;
        }
        return isValide;
    }

    private HashMap<String, Object> prepare() {
        parameterAllData.put("facebookUrl", edtFbUrl.getText().toString());
        parameterAllData.put("googleplusUrl", edtGoogleUrl.getText().toString());
        parameterAllData.put("twitterUrl", edtTwitterUrl.getText().toString());
        parameterAllData.put("linkedinUrl", edtSearchSecoundUrl.getText().toString());
        parameterAllData.put("snapchatUrl", edtSearchUrl.getText().toString());
        parameterAllData.put("description", edtDesc.getText().toString());
        parameterAllData.put("specialities", edtSpecial.getText().toString());
        parameterAllData.put("areaCovered", edtDesco.getText().toString());
        parameterAllData.put("projectAchieved", edtAchievement.getText().toString());
        return parameterAllData;
    }
}

