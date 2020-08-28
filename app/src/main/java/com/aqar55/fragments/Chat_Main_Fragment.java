package com.aqar55.fragments;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import com.aqar55.R;
import com.aqar55.activitys.ChatActivity;
import com.aqar55.adapters.Chat_List_Adapter;
import com.aqar55.databinding.FragmentChatMainBinding;
import com.aqar55.helper.Toaster;
import com.aqar55.intefaces.RecyclerViewClickListner;
import com.aqar55.model.ChatDelateModel;
import com.aqar55.model.UserChatListResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Chat_Main_Fragment extends AppCompatActivity implements View.OnClickListener {
    FragmentChatMainBinding fragmentChatBinding;
    View view;
    Chat_List_Adapter chat_list_adapter;
    ChatActivity chat_detail_fragment;
    private RecyclerViewClickListner recyclerViewClickListner;
    private List<UserChatListResponse.Data> data;
    private String propId;

    public static Intent getIntent(Context context,String propId){
        Intent intent=new Intent(context,Chat_Main_Fragment.class);
        intent.putExtra("propId",propId);
        return  intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentChatBinding=  DataBindingUtil.setContentView(Chat_Main_Fragment.this, R.layout.fragment_chat__main_);
        fragmentChatBinding.ivBack.setOnClickListener(this);
        fragmentChatBinding.reportImageviewPropertyDetail.setVisibility(View.GONE);
       if(getIntent()!=null){
           propId=(String)getIntent().getSerializableExtra("propId");
       }

        getApiData();
    }
    private void getApiData() {

        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<UserChatListResponse> getPropertyListingCall = api.getChatUserList(propId);
        getPropertyListingCall.enqueue(new Callback<UserChatListResponse>() {
            @Override
            public void onResponse(Call<UserChatListResponse> call, Response<UserChatListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            fragmentChatBinding.tvDataNotFound.setVisibility(View.GONE);

                            chat_list_adapter = new Chat_List_Adapter(getApplicationContext(), response.body().getData());
                            data = response.body().getData();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            fragmentChatBinding.chatListRecycler.setLayoutManager(linearLayoutManager);
                            fragmentChatBinding.chatListRecycler.setAdapter(chat_list_adapter);
                            chat_list_adapter.MyChatLiatClick(position -> startActivity(ChatActivity.getIntent(Chat_Main_Fragment.this,data.get(position),"fomchat")));
                            chat_list_adapter.MyChatLongClick(position -> showConfirmation(data.get(position).getRoomId()));

                        } else {
                            fragmentChatBinding.tvDataNotFound.setVisibility(View.VISIBLE);
                            fragmentChatBinding.tvDataNotFound.setText(response.body().getResponseMessage());
                           // Toaster.toast(response.body().getResponseMessage());

                        }
                    }
                    fragmentChatBinding.shimmerViewContainer.stopShimmerAnimation();
                    fragmentChatBinding.shimmerViewContainer.setVisibility(View.GONE);
                }
                fragmentChatBinding.shimmerViewContainer.stopShimmerAnimation();
                fragmentChatBinding.shimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<UserChatListResponse> call, @NonNull Throwable t) {
                Toaster.toast(t.getMessage());
                fragmentChatBinding.shimmerViewContainer.stopShimmerAnimation();
                fragmentChatBinding.shimmerViewContainer.setVisibility(View.GONE);

            }
        });
    }



    private void showConfirmation(String roomId) {
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
            chatDelete(roomId);


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }


    private void chatDelete(String roomId) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ChatDelateModel> getPropertyListingCall = api.chatDelete(roomId);
        getPropertyListingCall.enqueue(new Callback<ChatDelateModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatDelateModel> call, @NonNull Response<ChatDelateModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        Toaster.toast(response.body().getResponseMessage());
                        getApiData();


                    } else {
                        Toaster.toast(response.body().getResponseMessage());
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<ChatDelateModel> call, @NonNull Throwable t) {


            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentChatBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        fragmentChatBinding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
               finish();
                break;
        }

    }
}
