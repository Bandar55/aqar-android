package com.aqar55.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.aqar55.R;
import com.aqar55.adapters.Add_Image_Adapter;
import com.aqar55.databinding.FragmentAddImagePostPropertyBinding;
import com.aqar55.helper.ImageController;
import com.aqar55.helper.PermissionManager;
import com.aqar55.helper.Utils;
import com.aqar55.model.AddImageModel;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.utill.InternetCheck;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.aqar55.helper.ImageController.GALLERY_REQUEST;
import static com.aqar55.helper.ImageController.REQUEST_IMAGE_CAPTURE;

public class Add_Image_Post_Property_Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Add_Image_Post_Property";
    private String path_img = "";
    private ArrayList<AddImageModel> arrayList = new ArrayList<>();
    private View view;
    private FragmentAddImagePostPropertyBinding fragmentAddImagePostPropertyBinding;
    private Add_Image_Adapter add_image_adapter;
    private String dataText;
    private Bundle bundle = new Bundle();
    private Add_Media_Fragment add_media_fragment;
    private ManageActiveProperty.Data editData;

    private final int REQUEST_PERMISSION_CAMERA_STORAGE = 101;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    private PermissionManager permissionManager;
    private String mImagePath="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddImagePostPropertyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__image__post__property_, container, false);
        view = fragmentAddImagePostPropertyBinding.getRoot();
        permissionManager = new PermissionManager(getActivity());
        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
        }
        add_media_fragment = new Add_Media_Fragment();

        add_image_adapter = new Add_Image_Adapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentAddImagePostPropertyBinding.addImagePostPropertyRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fragmentAddImagePostPropertyBinding.addImagePostPropertyRecycler.setAdapter(add_image_adapter);

        fragmentAddImagePostPropertyBinding.layoutSaveAddImage.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.ivBack.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.constraintLayout.setOnClickListener(this);
        fragmentAddImagePostPropertyBinding.skipimage.setOnClickListener(this);

        bundle = getArguments();
        getIntentData();
        return view;
    }

    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");
        if(editData!=null){
            List<ManageActiveProperty.Imagesfile> imagesfileList = editData.getImagesfile();
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    for (int i=0;i<imagesfileList.size();i++){
                        ManageActiveProperty.Imagesfile imagesfile =  imagesfileList.get(i);
                        String file_name = String.format(Locale.getDefault(), "menu%03d.jpg",i);
                        String file_path = getActivity().getFilesDir().getAbsolutePath() + "/Temp";
                        final File file = new File(file_path, file_name);
                        try {
                            FileUtils.copyURLToFile(new URL(imagesfile.getImage()), file);
                            AddImageModel addImageModel = new AddImageModel("",file);
                            arrayList.add(addImageModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
            try {
               thread.join();
                add_image_adapter.updateList(arrayList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.constraintLayout:

                    getImage();
                    dataText = fragmentAddImagePostPropertyBinding.tvAddCaptionHere.getText().toString();

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
                add_media_fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_media_fragment).addToBackStack("").commit();
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
        if(editData!=null)
            bundle.putSerializable("Data",editData);
        add_media_fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_media_fragment).addToBackStack("").commit();
    }

    private void getImage() {
       /* Intent intent = new Intent(getContext(), TakeImage.class);
        intent.putExtra("from", "gallery");
        startActivityForResult(intent, GALLERY_REQUEST);*/


        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openBottomSheetBanner();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), READ_EXTERNAL_STORAGE)) {
            // We've been denied once before. Explain why we need the permission, then ask again.
            Utils.showDialog(getActivity(), "Camera & Gallary permissions are required to upload profile images!", "Ask Permission", "Discard", (dialog, which) -> {
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



    private void openBottomSheetBanner() {
        View view = getLayoutInflater().inflate(R.layout.custom_bottonsheet_layout, null);
        final Dialog mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        view.findViewById(R.id.camera_view).setOnClickListener(v -> {

                //else as usual for profile
                ImageController.dispatchCameraIntent(getActivity());


            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.gallery_view).setOnClickListener(v -> {

                //else for profile image as usual
                ImageController.dispatchGallaryIntent(getActivity());


            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.cancel_view).setOnClickListener(v -> mBottomSheetDialog.dismiss());

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                File file = new File(ImageController.getCurrentPhotoPath());
                arrayList.add(new AddImageModel(dataText, file));
                Log.d(TAG, "sendDataToRecyclerView: " + arrayList.size());
                add_image_adapter.updateList(arrayList);
                mImagePath = file.getAbsolutePath();
            } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
                Uri imageURI = data.getData();
                String filepath = ImageController.getPath(getActivity(), imageURI);
                File imageFile = new File(filepath);
                mImagePath = imageFile.getAbsolutePath();
                arrayList.add(new AddImageModel(dataText, imageFile));
                Log.d(TAG, "sendDataToRecyclerView: " + arrayList.size());
                add_image_adapter.updateList(arrayList);

            }/*else {
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

        }*/
        fragmentAddImagePostPropertyBinding.tvAddCaptionHere.setText(null);

    }


}
