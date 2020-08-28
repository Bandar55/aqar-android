package com.aqar55.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqar55.R;
import com.aqar55.activitys.MainActivity;

import retrofit2.http.POST;

public class Signup_Dialog extends Dialog{

    public Activity c;
    public Dialog d;
    ImageView imageView;
    private String title;
    private TextView titleDialog;


    public Signup_Dialog(Activity a, String title) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_signup);
        titleDialog = findViewById(R.id.text_dialog_layout_signup);
        imageView = findViewById(R.id.image_logo_dialog_layout_signup);
        titleDialog.setText("Hello " + title.toUpperCase());


        gotoMainActivity();
    }

    private void gotoMainActivity() {
        new Handler().postDelayed(() -> {
            dismiss();
            c.startActivity(new Intent(c, MainActivity.class));
            c.finishAffinity();
            //Do something after 100ms
        }, 3000);

    }

}