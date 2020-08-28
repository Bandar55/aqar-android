package com.aqar55.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqar55.R;
import com.aqar55.databinding.ActivityChatBinding;
import com.aqar55.fragments.Chat_Detail_Fragment;
import com.aqar55.fragments.Chat_Main_Fragment;

public class Chat_Activity extends AppCompatActivity {
    ActivityChatBinding activityChatBinding;
    Chat_Main_Fragment chat_main_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(Chat_Activity.this, R.layout.activity_chat_);
        chat_main_fragment = new Chat_Main_Fragment();
        getIntentData();


    }

    private void getIntentData() {
        if(getIntent()!=null){
           Bundle bundle =  getIntent().getExtras();
           if (bundle!=null){
               String s = bundle.getString("CHAT");
               if (s.equalsIgnoreCase("chat")) {
                   Chat_Detail_Fragment chat_detail_fragment = new Chat_Detail_Fragment();
                   Bundle bundle1=new Bundle();
                   bundle1.putString("propertieId","");
                   chat_detail_fragment.setArguments(bundle1);
                   getSupportFragmentManager().beginTransaction().replace(R.id.chat_main_container, chat_detail_fragment).commit();

               }
           }else {
               startActivity(new Intent(this,Chat_Main_Fragment.class));
           }
        }
    }
}
