package com.aqar55.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import com.aqar55.R;
import com.aqar55.adapters.Add_Image_Adapter;
import com.aqar55.databinding.FragmentAddImagePostPropertyBinding;
import com.aqar55.model.AddImageModel;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;
import com.aqar55.utill.TakeImage;

public class AddImageRent extends Fragment implements View.OnClickListener {
    private static final String TAG = "Add_Image_Post_Property";
    private static final int GALLERY_REQUEST = 1;
    private String path_img = "";
    private ArrayList<AddImageModel> arrayList = new ArrayList<>();
    private View view;
    private FragmentAddImagePostPropertyBinding fragmentAddImagePostPropertyBinding;
    private Add_Image_Adapter add_image_adapter;
    private String dataText;
    private Bundle bundle = new Bundle();
    private AddMediaRent addMediaRent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddImagePostPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__image__post__property_, container, false);
        view = fragmentAddImagePostPropertyBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(fragmentAddImagePostPropertyBinding.getRoot(), "Make sure you are connected to Internet.");
        }

        addMediaRent = new AddMediaRent();

        add_image_adapter = new Add_Image_Adapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentAddImagePostPropertyBinding.addImagePostPropertyRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fragmentAddImagePostPropertyBinding.addImagePostPropertyRecycler.setAdapter(add_image_adapter);

        fragmentAddImagePostPropertyBinding.layoutSaveAddImage.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.ivBack.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.constraintLayout.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.skipimage.setOnClickListener(this);
        bundle = getArguments();


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.constraintLayout:
                if (fragmentAddImagePostPropertyBinding.tvAddCaptionHere.getText().toString().isEmpty()) {
                    fragmentAddImagePostPropertyBinding.tvAddCaptionHere.requestFocus();
                    fragmentAddImagePostPropertyBinding.tvAddCaptionHere.setError("Please enter specific image name");
                } else {
                    getImage();
                    dataText = fragmentAddImagePostPropertyBinding.tvAddCaptionHere.getText().toString();
                }
                break;
            case R.id.layout_save_add_image:
                if (validate()) {

                    collectData();
                }
                break;
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.skipimage:
                addMediaRent.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, addMediaRent).addToBackStack("").commit();
                break;
        }
    }

    private boolean validate() {

        if (arrayList.isEmpty()) {
            Toast.makeText(getContext(), "Please Select Image To Proceed", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectData() {
        bundle.putSerializable("uploadedimages", arrayList);
        addMediaRent.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, addMediaRent).addToBackStack("").commit();
    }

    private void getImage() {
        Intent intent = new Intent(getContext(), TakeImage.class);
        intent.putExtra("from", "gallery");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            path_img = data.getStringExtra("filePath") + System.currentTimeMillis() + ".jpg";
            if (path_img != null & !path_img.isEmpty()) {

                Log.i("Gallery File--->", path_img);
                File imgFile = new File(data.getStringExtra("filePath"));
                if (imgFile.exists()) {
                    arrayList.add(new AddImageModel(dataText, imgFile));
                    Log.d(TAG, "sendDataToRecyclerView: " + arrayList.size());
                    add_image_adapter.updateList(arrayList);
                }
            } else {
                path_img = "";
            }
        }
        fragmentAddImagePostPropertyBinding.tvAddCaptionHere.setText(null);
        super.onActivityResult(requestCode, resultCode, data);
    }


}


