package com.aqar55.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.aqar55.R;
import com.aqar55.adapters.Add_Media_Adapter;
import com.aqar55.databinding.FragmentAddMediaBinding;
import com.aqar55.utill.InternetCheck;
import com.aqar55.utill.MyApplication;


public class AddMediaRent extends Fragment implements View.OnClickListener {
    private static final String TAG = "Add_Media_Fragment";
    private static final int GALLERY_REQUEST = 1;
    private static final int RESULT_CANCELED = 0;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private FragmentAddMediaBinding fragmentAddMediaBinding;
    private View view;
    private Add_Media_Adapter add_media_adapter;
    private ArrayList<File> files = new ArrayList<>();
    private String videoTitle;
    private String path_img = "";
    private MediaController mediaControls;
    private Uri contentURI;
    private Bundle bundle = new Bundle();
    private String selectedVideoPath;
    private File newfile;
    private AddAddressRent addAddressRent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddMediaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__media_, container, false);
        view = fragmentAddMediaBinding.getRoot();

        if (!new InternetCheck(getContext()).isConnect()) {
            MyApplication.makeASnack(fragmentAddMediaBinding.getRoot(), "Make sure you are connected to Internet.");
        }

        addAddressRent = new AddAddressRent();
        if (savedInstanceState != null) {
            String myString = savedInstanceState.getString("contenturi");
            Uri uri = Uri.parse(myString);
           /* fragmentAddMediaBinding.imageVideo.setMediaController(mediaControls);
            fragmentAddMediaBinding.imageVideo.setVideoURI(uri);
            fragmentAddMediaBinding.imageVideo.seekTo(1);*/
            fragmentAddMediaBinding.imageVideo.setImageURI(uri);
            fragmentAddMediaBinding.imageVideo.setVisibility(view.VISIBLE);
        }


        add_media_adapter = new Add_Media_Adapter(getContext(), files, videoTitle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentAddMediaBinding.addImagePostPropertyRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fragmentAddMediaBinding.addImagePostPropertyRecycler.setAdapter(add_media_adapter);
        fragmentAddMediaBinding.layoutSaveAddMedia.setOnClickListener(this);
        fragmentAddMediaBinding.ivBack.setOnClickListener(this);
        fragmentAddMediaBinding.constraintLayout.setOnClickListener(this);
        fragmentAddMediaBinding.skipvideo.setOnClickListener(this);

        if (mediaControls == null) {
            mediaControls = new MediaController(getContext());
        }
        bundle = getArguments();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.constraintLayout:
                if (fragmentAddMediaBinding.tvAddCaptionHere.getText().toString().isEmpty()) {
                    fragmentAddMediaBinding.tvAddCaptionHere.requestFocus();
                    fragmentAddMediaBinding.tvAddCaptionHere.setError("Please enter specific Video Title");
                } else {
                    getVideo();
                    videoTitle = fragmentAddMediaBinding.tvAddCaptionHere.getText().toString();
                }
                break;
            case R.id.layout_save_add_media:
                if (validation()) {
                    collectData();
                }
                break;
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.skipvideo:

                addAddressRent.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, addAddressRent).addToBackStack("").commit();
                break;
        }
    }

    private boolean validation() {

        if (!fragmentAddMediaBinding.imageVideo.isShown()) {
            Toast.makeText(getContext(), "Please Select Video To Proceed", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void collectData() {

        bundle.putSerializable("videoview", newfile);


        addAddressRent.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.set_rent_price_container, addAddressRent).addToBackStack("").commit();
    }

    private void getVideo() {




        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("result", "" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY_REQUEST) {
            if (data != null) {
                contentURI = data.getData();

                selectedVideoPath = getPath(contentURI);
                saveVideoToInternalStorage(selectedVideoPath);
                //videoView.setVideoURI(contentURI);
                //videoView.requestFocus();
                //videoView.start();
                Bitmap bmThumbnail;


                // MINI_KIND: 512 x 384 thumbnail
                bmThumbnail = ThumbnailUtils.createVideoThumbnail(selectedVideoPath,
                        MediaStore.Images.Thumbnails.MINI_KIND);

                fragmentAddMediaBinding.imageVideo.setImageBitmap(bmThumbnail);
               /* fragmentAddMediaBinding.imageVideo.setMediaController(mediaControls);
                fragmentAddMediaBinding.imageVideo.setVideoURI(contentURI);
                fragmentAddMediaBinding.imageVideo.seekTo(1);*/
                //fragmentAddMediaBinding.imageVideo.setVideoPath(selectedVideoPath);

                Log.d(TAG, "onActivityResult: " + videoTitle);
                fragmentAddMediaBinding.videotitle.setText(videoTitle);
                fragmentAddMediaBinding.videolayout.setVisibility(view.VISIBLE);
                //fragmentAddMediaBinding.imageVideo.start();
                fragmentAddMediaBinding.tvAddCaptionHere.setText(null);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            Toast.makeText(getContext(), "Hello null", Toast.LENGTH_SHORT).show();
        return null;
    }


    private void saveVideoToInternalStorage(String filePath) {

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            } else {
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("contenturi", contentURI + "");
    }

}
