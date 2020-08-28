package com.aqar55.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.aqar55.R;
import com.aqar55.helper.BaseManager;
import com.aqar55.helper.ChangeImageRotation;
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
public class ActivityMyProfessionalSocialUrl extends AppCompatActivity {
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
    private String userIdd="";
    private List<File> imageUpladeList;
    private HashMap<String, Object> parameterAllData;
    private GetUserDetails.DataEntity responseData;
    public static Intent getIntent(Context context, HashMap<String, Object> hashMap, GetUserDetails.DataEntity responseData) {
        Intent intent = new Intent(context, ActivityMyProfessionalSocialUrl.class);
        intent.putExtra("Data", hashMap);
        intent.putExtra("dataApi",responseData);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_professional_profile_social);
        ButterKnife.bind(this);
        imageUpladeList = new ArrayList<>();


        if (getIntent() != null) {
            parameterAllData = (HashMap<String, Object>) getIntent().getSerializableExtra("Data");
            responseData = (GetUserDetails.DataEntity) getIntent().getSerializableExtra("dataApi");

            if (parameterAllData.get("fromActivity").toString().equalsIgnoreCase("professional")) {
                tvTitle.setText("My Professional Profile");
                userIdd=responseData.getId();
                parameterAllData.put("professionalId", ModelManager.modelManager().getCurrentUser().getProfessionalid());
            } else {
                tvTitle.setText("My Business Profile");
                parameterAllData.put("professionalId", ModelManager.modelManager().getCurrentUser().getBusinessid());
               if(responseData!=null)
                userIdd=responseData.getId();
            }

            if(responseData!=null)
                prepareUi(responseData);

        }
    }

    private void prepareUi(GetUserDetails.DataEntity responseData) {
        if(responseData.getFacebookUrl()==null)
            return;

        edtFbUrl.setText(responseData.getFacebookUrl());
        edtGoogleUrl.setText(responseData.getGoogleplusUrl());
        edtTwitterUrl.setText(responseData.getTwitterUrl());
        edtSearchSecoundUrl.setText(responseData.getSnapchatUrl());
        edtSearchUrl.setText(responseData.getLinkedinUrl());
        edtDesc.setText(responseData.getDescription());
        edtAchievement.setText(responseData.getProjectAchieved());
        edtSpecial.setText(responseData.getSpecialities());
        edtDesco.setText(responseData.getAreaCovered());

    }


    @OnClick({R.id.tvSavePostedProperty, R.id.ivBack,R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSavePostedProperty:
                if (!isValid())
                    return;
                prepare();
                ApiCallingHere();
                break;
            case R.id.ivBack:
                finish();
                break;

            case R.id.delete:

                if(userIdd.isEmpty())
                    return;
                showConfirmation();
                break;

        }
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

                    startActivity(new Intent(ActivityMyProfessionalSocialUrl.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {
                Toaster.toast(t.getMessage());

            }
        });
    }




    //api calling here
    private void ApiCallingHere() {
        RequestBody profile_body;
        RequestBody GorvFirst_body;
        RequestBody GorvSecound_body;
        MultipartBody.Part profilePart;
        MultipartBody.Part GorvFirstPart;
        MultipartBody.Part GorvSecoundPart;
        File profileImage = getProfileImageFile();
        File GorvFirtImage = getGorvFirstImageFile();
        File GorvSecoundImage = getGorvSecoundImageFile();

        if(parameterAllData.get("profileImage").toString().isEmpty()){
            profile_body = RequestBody.create(MediaType.parse("image/*"), profileImage);
            profilePart = MultipartBody.Part.createFormData("profileImage", "");
        }else {
            profile_body = RequestBody.create(MediaType.parse("image/*"), profileImage);
            profilePart = MultipartBody.Part.createFormData("profileImage", profileImage.getName(), profile_body);
        }

        if(parameterAllData.get("govtIdImage1").toString().isEmpty()){
            GorvFirst_body = RequestBody.create(MediaType.parse("image/*"), GorvFirtImage);
            GorvFirstPart = MultipartBody.Part.createFormData("govtIdImage1", "");
        }else {
            GorvFirst_body = RequestBody.create(MediaType.parse("image/*"), GorvFirtImage);
            GorvFirstPart = MultipartBody.Part.createFormData("govtIdImage1", profileImage.getName(), GorvFirst_body);
        }

        if(parameterAllData.get("govtIdImage2").toString().isEmpty()){
            GorvSecound_body = RequestBody.create(MediaType.parse("image/*"), GorvSecoundImage);
            GorvSecoundPart = MultipartBody.Part.createFormData("govtIdImage2", "");
        }else {
            GorvSecound_body = RequestBody.create(MediaType.parse("image/*"), GorvSecoundImage);
            GorvSecoundPart = MultipartBody.Part.createFormData("govtIdImage2", profileImage.getName(), GorvSecound_body);
        }


        MyDialog.getInstance(getApplicationContext()).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ProfessionalListResponse> createBusinessOrProfessionalCall;


        if (parameterAllData.get("fromActivity").toString().equalsIgnoreCase("professional")) {
            if(BaseManager.getDataFromPreferences("professionalProfile",Boolean.class)){
                createBusinessOrProfessionalCall = api.updateBusinessOrProfessional(setUpMapData(), morePart(), profilePart, videoPart(),GorvFirstPart,GorvSecoundPart);
            }else {
                createBusinessOrProfessionalCall = api.createBusinessOrProfessional(setUpMapData(), morePart(), profilePart, videoPart(),GorvFirstPart,GorvSecoundPart);
            }
        } else {
            if(BaseManager.getDataFromPreferences("businessProfile",Boolean.class).equals(true)){
                createBusinessOrProfessionalCall = api.updateBusinessOrProfessional(setUpMapData(), morePart(), profilePart, videoPart(),GorvFirstPart,GorvSecoundPart);

            }else {
                createBusinessOrProfessionalCall = api.createBusinessOrProfessional(setUpMapData(), morePart(), profilePart, videoPart(),GorvFirstPart,GorvSecoundPart);
            }
        }
        createBusinessOrProfessionalCall.enqueue(new Callback<ProfessionalListResponse>() {
            @Override
            public void onResponse(Call<ProfessionalListResponse> call, Response<ProfessionalListResponse> response) {
                MyDialog.getInstance(getApplicationContext()).hideDialog();
                if (response.isSuccessful()) {
                    if (parameterAllData.get("fromActivity").toString().equalsIgnoreCase("professional")) {
                        if (response.body() != null) {
                            BaseManager.saveDataIntoPreferences(response.body().getData().getId(),"userIdProfessional");
                        }
                    } else {
                        if (response.body() != null) {
                            BaseManager.saveDataIntoPreferences(response.body().getData().getId(),"userIdBusiness");
                        }
                    }

                    startActivity(new Intent(ActivityMyProfessionalSocialUrl.this, MainActivity.class));
                    finish();
                    Toaster.toast(response.body().getResponse_message());
                    //Intent intent=new Intent(this,)
                }else {
                    //Toaster.toast(response.body().getResponse_message());
                }
            }

            @Override
            public void onFailure(Call<ProfessionalListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                MyDialog.getInstance(getApplicationContext()).hideDialog();
            }
        });

    }


    private Map<String, RequestBody> setUpMapData() {

        Map<String, RequestBody> prameter = new HashMap<>();
        prameter.put("status", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("status").toString()));
        prameter.put("birthDate", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("birthDate").toString()));
        prameter.put("fullName", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("fullName").toString()));
        prameter.put("memberSince", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("memberSince").toString()));
        prameter.put("category", RequestBody.create(MediaType.parse("text/plain"),parameterAllData.get("category").toString()));
        prameter.put("subCategory", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("subCategory").toString()));
        prameter.put("state", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("state").toString()));
        prameter.put("city", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("city").toString()));
        prameter.put("zipcode", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("zipcode").toString()));
        prameter.put("area", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("area").toString()));
        if(parameterAllData.get("lat")!=null && parameterAllData.get("long")!=null){
            prameter.put("lat", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("lat").toString()));
            prameter.put("long", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("long").toString()));
        }
        prameter.put("mobileNumber", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("mobileNumber").toString()));
        prameter.put("website", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("website").toString()));
        prameter.put("facebookUrl", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("facebookUrl").toString()));
        prameter.put("googleplusUrl", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("googleplusUrl").toString()));
        prameter.put("twitterUrl", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("twitterUrl").toString()));
        prameter.put("snapchatUrl", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("snapchatUrl").toString()));
        prameter.put("linkedinUrl", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("linkedinUrl").toString()));
        prameter.put("description", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("description").toString()));
        prameter.put("projectAchieved", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("projectAchieved").toString()));
        prameter.put("specialities", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("specialities").toString()));
        prameter.put("areaCovered", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("areaCovered").toString()));
        prameter.put("govtIdNumber1", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdNumber1").toString()));
        prameter.put("govtIdNumber2", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdNumber2").toString()));
        prameter.put("email", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("email").toString()));
        prameter.put("userId", RequestBody.create(MediaType.parse("text/plain"), ModelManager.modelManager().getCurrentUser().getId()));
        if(responseData!=null)
            prameter.put("profbusId", RequestBody.create(MediaType.parse("text/plain"), responseData.getId()));

        prameter.put("govtIdType1", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdType1").toString()));
        prameter.put("govtIdType2", RequestBody.create(MediaType.parse("text/plain"), parameterAllData.get("govtIdType2").toString()));

        if (parameterAllData.get("fromActivity").toString().equalsIgnoreCase("professional")) {
            prameter.put("type", RequestBody.create(MediaType.parse("text/plain"),"professional"));
            prameter.put("professionalId", RequestBody.create(MediaType.parse("text/plain"), ModelManager.modelManager().getCurrentUser().getProfessionalid()));
        } else {
            prameter.put("type", RequestBody.create(MediaType.parse("text/plain"),"business"));
            prameter.put("businessId", RequestBody.create(MediaType.parse("text/plain"), ModelManager.modelManager().getCurrentUser().getBusinessid()));
        }

        return prameter;
    }


    private List<MultipartBody.Part> morePart() {

        List<MultipartBody.Part> list = new ArrayList<>();
        imageUpladeList.clear();

        if(parameterAllData.get("imagesFile[]")!=null && !parameterAllData.get("imagesFile[]").equals(""))
           imageUpladeList.addAll((Collection<? extends File>) parameterAllData.get("imagesFile[]"));


        if (imageUpladeList.size() != 0) {
            for (int i = 0; i < imageUpladeList.size(); i++) {
                RequestBody profile_body = RequestBody.create(MediaType.parse("image/*"), imageUpladeList.get(i));
                MultipartBody.Part menuPart = MultipartBody.Part.createFormData("imagesFile", imageUpladeList.get(i).getName(), profile_body);
                list.add(menuPart);
            }
        }
        return list;
    }


    private MultipartBody.Part videoPart() {
        MultipartBody.Part menuPart;
        if(!parameterAllData.get("videosFile").toString().isEmpty()){
            File videoPath = (File) parameterAllData.get("videosFile");
            RequestBody profile_body = RequestBody.create(MediaType.parse("video/*"), videoPath);
            menuPart = MultipartBody.Part.createFormData("videosFile", videoPath.getName(), profile_body);
        }else {
         //   File videoPath = (File) parameterAllData.get("videosFile");
            //RequestBody profile_body = RequestBody.create(MediaType.parse("video/*"), videoPath);
             menuPart = MultipartBody.Part.createFormData("videosFile", "");
        }
        return menuPart;

    }

    public File getProfileImageFile() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(parameterAllData.get("profileImage").toString());
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getGorvFirstImageFile() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(parameterAllData.get("govtIdImage1").toString());
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public File getGorvSecoundImageFile() {
        File file = null;
        try {
            ChangeImageRotation changeImageRotation = new ChangeImageRotation(this);
            String imageUrl = changeImageRotation.setCapturedImage(parameterAllData.get("govtIdImage2").toString());
            file = new File(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
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
