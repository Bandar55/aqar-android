package com.aqar55.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aqar55.R;
import com.aqar55.adapters.ProfPropertyListingAdapter;
import com.aqar55.fragments.BottomSheetSortProduct;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.GetPropertyListing;
import com.aqar55.model.LikeModelResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.MyDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessionalPropertyListingActivity extends AppCompatActivity implements ProfPropertyListingAdapter.OnPropertyListingItemClick {

    @BindView(R.id.rvProfPropertyListing)
    RecyclerView rvProfPropertyListing;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String type = "";
    private String propertyType;
    private String _id = "";
    private boolean priceltoh, pricehtol, sizeltoh, sizehtol;

    private List<GetPropertyListing.Data> dataList;
    ProfPropertyListingAdapter adapter;
    private boolean sortByName;
    private boolean sortByCategory;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            sortByName = intent.getBooleanExtra("sortByName",false);
//            sortByCategory = intent.getBooleanExtra("sortByCategory",false);
            priceltoh = intent.getBooleanExtra("priceltoh", false);
            pricehtol = intent.getBooleanExtra("pricehtol", false);
            sizeltoh = intent.getBooleanExtra("sizeltoh", false);
            sizehtol = intent.getBooleanExtra("sizehtol", false);
            hitSortPropertyApi();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_property_listing);
        ButterKnife.bind(this);
        setToolbar();
        getIntentData();
        hitProfPropertyListing();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            propertyType = bundle.getString("propertyType");
            _id = bundle.getString("_id");
        }
    }

    private void setUpRecyclerView(List<GetPropertyListing.Data> dataList) {
        rvProfPropertyListing.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfPropertyListingAdapter(this, dataList, this);
        rvProfPropertyListing.setAdapter(adapter);
    }

    @OnClick({R.id.tvShowMap, R.id.tvSortBy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvShowMap:
                startActivity(ProfPropertyListingMapActivity.getIntent(this, dataList));
                break;

            case R.id.tvSortBy:
                Bundle bundle = new Bundle();
                bundle.putString("tab", propertyType);
                BottomSheetSortProduct bottomSheetSortProduct = new BottomSheetSortProduct();
                bottomSheetSortProduct.setArguments(bundle);
                bottomSheetSortProduct.show(getSupportFragmentManager(), bottomSheetSortProduct.getTag());
                break;
        }
    }

    // hit getProfessionalPropertyListing API
    private void hitProfPropertyListing() {
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> getPropertyListingCall;
        if (type.equalsIgnoreCase("professional")) {
            if (ModelManager.modelManager().getCurrentUser() != null)
                getPropertyListingCall = api.getProfessionalPropertyListing(ModelManager.modelManager().getCurrentUser().getId(), _id);
            else
                getPropertyListingCall = api.getProfessionalPropertyListing("", _id);
        } else {
            if (ModelManager.modelManager().getCurrentUser() != null)
                getPropertyListingCall = api.getProfessionalPropertyListing(ModelManager.modelManager().getCurrentUser().getId(), _id);
            else
                getPropertyListingCall = api.getProfessionalPropertyListing("", _id);
        }

        getPropertyListingCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(ProfessionalPropertyListingActivity.this).hideDialog();
                    if (response.body() != null && response.body().getResponseCode() == 200) {
                        assert response.body() != null;
                        dataList = response.body().getData();
                        setUpRecyclerView(dataList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(ProfessionalPropertyListingActivity.this).hideDialog();
                Toast.makeText(ProfessionalPropertyListingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // sort professional list using name or category
    public void hitSortPropertyApi() {
        MyDialog.getInstance(this).showDialog(this);
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<GetPropertyListing> sortDataCall;
        if (ModelManager.modelManager().getCurrentUser() != null) {
            if (propertyType.equalsIgnoreCase("professional") || propertyType.equalsIgnoreCase("business")) {
                sortDataCall = api.sortTotalPostedPropertyProfessional(propertyType, _id, priceltoh, pricehtol, sizeltoh, sizehtol);

            } else {
                sortDataCall = api.sortTotalPostedPropertynormalUserId(propertyType, _id, priceltoh, pricehtol, sizeltoh, sizehtol);

            }


        } else {
            if (propertyType.equalsIgnoreCase("professional") || propertyType.equalsIgnoreCase("business")) {
                sortDataCall = api.sortTotalPostedPropertyProfessional(propertyType, "", priceltoh, pricehtol, sizeltoh, sizehtol);

            } else {
                sortDataCall = api.sortTotalPostedPropertynormalUserId(propertyType, "", priceltoh, pricehtol, sizeltoh, sizehtol);

            }
        }

        sortDataCall.enqueue(new Callback<GetPropertyListing>() {
            @Override
            public void onResponse(@NonNull Call<GetPropertyListing> call, @NonNull Response<GetPropertyListing> response) {
                if (response.isSuccessful()) {
                    MyDialog.getInstance(ProfessionalPropertyListingActivity.this).hideDialog();
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            List<GetPropertyListing.Data> dataList = response.body().getData();
                            setUpRecyclerView(dataList);
                        } else {
                            Toast.makeText(ProfessionalPropertyListingActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPropertyListing> call, @NonNull Throwable t) {
                MyDialog.getInstance(ProfessionalPropertyListingActivity.this).hideDialog();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(propertyType));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    @Override
    public void onShareClick(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "I just used the Aqar55 App and saw the property " + dataList.get(position).getTitle() + " for " +
                dataList.get(position).getType() + ". Highly recommended that you try it too.");
        intent.setType("text/plain");
        startActivity(intent);
    }

    @Override
    public void onCallClick(int position) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataList.get(position).getMobileNumber(), null));
        startActivity(intent);
    }

    @Override
    public void onChatClick(int position) {

        if (ModelManager.modelManager().getCurrentUser() != null)

            startActivity(ChatActivity.getIntent(this, dataList.get(position).getUserid(), dataList.get(position).getId(), dataList.get(position).getTitle(), dataList.get(position).getDescription()));
        else
            startActivity(Login_Signup_Button_Activity.getIntent(this));

    }

    @Override
    public void onLikeClick(int position) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<LikeModelResponse> getUserDetailsCall;
        if (dataList.get(position).getLikedStatus() != null) {
            if (dataList.get(position).getLikedStatus().equalsIgnoreCase("yes"))
                getUserDetailsCall = api.getLikePostPorBusiness(dataList.get(position).getType(),
                        dataList.get(position).getId(), ModelManager.modelManager().getCurrentUser().getId(), false);
            else
                getUserDetailsCall = api.getLikePostPorBusiness(dataList.get(position).getType(),
                        dataList.get(position).getId(), ModelManager.modelManager().getCurrentUser().getId(), true);

            getUserDetailsCall.enqueue(new Callback<LikeModelResponse>() {
                @Override
                public void onResponse(Call<LikeModelResponse> call, Response<LikeModelResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 200) {
                                if (response.body().getLiked()) {
                                    dataList.get(position).setLikedStatus("yes");
                                    adapter.updateAdapter();
                                    Toaster.toast(response.body().getResponseMessage());
                                } else {
                                    dataList.get(position).setLikedStatus("no");
                                    adapter.updateAdapter();
                                    Toaster.toast(response.body().getResponseMessage());
                                }

                            } else {
                                Toaster.toast(response.body().getResponseMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeModelResponse> call, Throwable t) {
                    Toaster.toast(t.getMessage());
                }
            });
        }
    }


    // set toolbar
    public void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button_new);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

}
