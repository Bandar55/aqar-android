package com.aqar55.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import com.aqar55.ItemClick.ItemClickSupport;
import com.aqar55.R;
import com.aqar55.adapters.CustomDialogAdapter;
import com.aqar55.databinding.SimpleDialogLayoutBinding;

public class SimpleCustomDialog extends Dialog implements
        ItemClickSupport.OnItemClickListener {

    public Activity c;
    public Dialog d;
    private String title;
    private List<String> data;
    private SimpleDialogLayoutBinding simpleDialogLayoutBinding;
    private CustomDialogAdapter customDialogAdapter;
    private RecyclerView recyclerView;
    private TextView titleDialog;
    private String key;


    public SimpleCustomDialog(Activity a, List<String> data, String title, String key) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.title = title;
        this.data = data;
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_dialog_layout);
        recyclerView = findViewById(R.id.recycler_simple_dialog);
        titleDialog = findViewById(R.id.textView107);
        titleDialog.setText(title);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this);

        customDialogAdapter = new CustomDialogAdapter(c, data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customDialogAdapter);

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        dismiss();
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", data.get(position));
        intent.putExtra("key",key);
        LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
    }
}