package com.aqar55.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.netcompss.ffmpeg4android.CommandValidationException;
import com.netcompss.ffmpeg4android.GeneralUtils;
import com.netcompss.loader.LoadJNI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import com.aqar55.R;
import com.aqar55.adapters.Add_Media_Adapter;
import com.aqar55.databinding.FragmentAddMediaBinding;
import com.aqar55.helper.PermissionManager;
import com.aqar55.model.ManageActiveProperty;
import com.aqar55.model.PictureVideoModel;
import com.aqar55.utill.Config;
import com.aqar55.utill.FileUtil;
import com.aqar55.utill.InternetCheck;
import wseemann.media.FFmpegMediaMetadataRetriever;

import static android.app.Activity.RESULT_OK;
import static com.aqar55.helper.ImageController.VIDEO_CAMERA_CODE;
import static com.aqar55.helper.ImageController.VIDEO_REQUEST_CODE;


public class Add_Media_Fragment extends Fragment implements View.OnClickListener {
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
    private Bundle bundle;
    private String selectedVideoPath;
    private File newfile;
    private Add_Address_Post_Property add_address_post_property;
    private ManageActiveProperty.Data editData;
    private final int VID_COMPRESS_REQ_CODE = 16;
    private final int CAMERA_REQ_CODE = 10;
    private final int GALLERY_REQ_CODE = 12;
    private File tempFile = null;

    //COMPRESSION VARIABLES
    private String workFolder = null;
    private String demoVideoFolder = null;
    private String demoVideoPath = null;
    private String demoVideoOutPath = null;
    private String vkLogPath = null;
    private boolean commandValidationFailedFlag = false;
    private File compFile = null;
    private String selectedImagePath = "";
    private String mImagePath = "";
    private PermissionManager permissionManager;
    private LatLng latLng;
    private ArrayList<PictureVideoModel> picturesList;
    private ArrayList<PictureVideoModel> videosList;
    private ArrayList<PictureVideoModel> videosListSender;
    private ArrayList<PictureVideoModel> picturesListSender;
    Bitmap retrievedImage = null;
    ByteArrayOutputStream stream = new ByteArrayOutputStream();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddMediaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__media_, container, false);
        view = fragmentAddMediaBinding.getRoot();
        if (!new InternetCheck(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
        }
        if (savedInstanceState != null) {
            String myString = savedInstanceState.getString("contenturi");
            Uri uri = Uri.parse(myString);
            //fragmentAddMediaBinding.imageVideo.setMediaController(mediaControls);
            fragmentAddMediaBinding.imageVideo.setImageURI(uri);
            //fragmentAddMediaBinding.imageVideo.seekTo(1);
            //fragmentAddMediaBinding.imageVideo.setVideoPath(selectedVideoPath);
            fragmentAddMediaBinding.imageVideo.setVisibility(view.VISIBLE);
        }
        add_address_post_property = new Add_Address_Post_Property();


        add_media_adapter = new Add_Media_Adapter(getContext(), files, videoTitle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentAddMediaBinding.addImagePostPropertyRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fragmentAddMediaBinding.addImagePostPropertyRecycler.setAdapter(add_media_adapter);
        fragmentAddMediaBinding.layoutSaveAddMedia.setOnClickListener(this);
        fragmentAddMediaBinding.ivBack.setOnClickListener(this);
        fragmentAddMediaBinding.constraintLayout.setOnClickListener(this);
        fragmentAddMediaBinding.skipvideo.setOnClickListener(this);
        fragmentAddMediaBinding.ivClose.setOnClickListener(this);

        if (mediaControls == null) {
            mediaControls = new MediaController(getContext());
        }
        bundle = getArguments();
        initialSetUp();
        getIntentData();
        return view;
    }


    private void initialSetUp() {
        picturesList = new ArrayList<>();
        videosList = new ArrayList<>();
        videosListSender = new ArrayList<>();
        picturesListSender = new ArrayList<>();


    }


    private void getIntentData() {
        editData = (ManageActiveProperty.Data) bundle.getSerializable("Data");

        if(editData!=null){
            bundle.putSerializable("Data",editData);
            if (editData.getVideosfile() != null &&!editData.getVideosfile().isEmpty()) {
                String videoUrl = editData.getVideosfile().get(0).getVideo();
                FFmpegMediaMetadataRetriever mr = new FFmpegMediaMetadataRetriever();

                String[] separated = videoUrl.split(":");
                mr.setDataSource("http:"+separated[1]);
                retrievedImage = mr.getFrameAtTime(20000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
                try {
                    fragmentAddMediaBinding.videolayout.setVisibility(view.VISIBLE);
                    retrievedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    fragmentAddMediaBinding.imageVideo.setImageBitmap(retrievedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.constraintLayout:
//                if (fragmentAddMediaBinding.tvAddCaptionHere.getText().toString().isEmpty()) {
//                    fragmentAddMediaBinding.tvAddCaptionHere.requestFocus();
//                    fragmentAddMediaBinding.tvAddCaptionHere.setError("Please enter specific Video Title");
//                } else {
                pickVideo();
                    videoTitle = fragmentAddMediaBinding.tvAddCaptionHere.getText().toString().trim();
//                }
                break;
//            case R.id.layout_save_add_media:
//                Add_Address_Post_Property add_address_post_property = new Add_Address_Post_Property();
//                getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_address_post_property).addToBackStack("").commit();
//                break;
            case R.id.layout_save_add_media:
                if (validation()) {

                   collectData();
                }
//                Add_Specification add_specification = new Add_Specification();
//                getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_specification).addToBackStack("").commit();                break;
                break;
            case R.id.ivBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.skipvideo:
                add_address_post_property.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_address_post_property).addToBackStack("").commit();
                break;

            case R.id.iv_close:
                fragmentAddMediaBinding.videolayout.setVisibility(view.INVISIBLE);

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
        if(!videosListSender.isEmpty()){
            bundle.putSerializable("videoview", videosListSender.get(0).getImageVidFile());

        }
        add_address_post_property.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.post_a_property_container, add_address_post_property).addToBackStack("").commit();
    }

    private void pickVideo() {
        openBottomSheetBannerVideo();
    }


    //open bottomsheet for video
    private void openBottomSheetBannerVideo() {

        View view = getLayoutInflater().inflate(R.layout.custom_bottonsheet_layout, null);
        final Dialog mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();


        view.findViewById(R.id.camera_view).setOnClickListener(v -> {

            dispatchVideos();   //VID_COMPRESS_REQ_CODE: VIDEO VIA GALLERY

            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.gallery_view).setOnClickListener(v -> {

            dispatchVideos(); // VID_COMPRESS_REQ_CODE: VIDEO VIA GALLERY

            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.cancel_view).setOnClickListener(v -> {

            //  tvEditIntroduction.setText("Edit");
            mBottomSheetDialog.dismiss();
        });
    }



    private void dispatchVideos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, VID_COMPRESS_REQ_CODE);
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
        } else if (requestCode == VID_COMPRESS_REQ_CODE && resultCode == RESULT_OK) {


            Uri uri = data.getData();

            if (uri != null) {
                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null, null);

                try {
                    if (cursor != null && cursor.moveToFirst()) {

                        String displayName = cursor.getString(
                                cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.i(TAG, "Display Name: " + displayName);

                        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                        String size = null;
                        if (!cursor.isNull(sizeIndex)) {
                            size = cursor.getString(sizeIndex);
                        } else {
                            size = "Unknown";
                        }
                        Log.i(TAG, "Size: " + size);

                        tempFile = FileUtil.saveTempFile(displayName, getActivity(), uri);


                        //COMPRESSION STARTS
                        scanFile(tempFile.getAbsolutePath());

                        setUpFFMpeg4();

                        startTransCodingCompressFFMP4();    // FF_M_PEG_4 ASYNC_TASK

                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }

        }


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




    private void setUpFFMpeg4() {

//        demoVideoFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/videokit/";
//        demoVideoPath = demoVideoFolder + "in.mp4";

        Log.w(TAG, getResources().getString(R.string.app_name) + " version: " + GeneralUtils.getVersionName(getActivity()));
        String workFolder_r = getActivity().getFilesDir().getAbsolutePath() + "/";
        //Log.i(TAG, "workFolder: " + workFolder);
        vkLogPath = workFolder_r + "vk.log";

        workFolder = workFolder_r;

//        FileUtil.createApplicationFolder();

        File f_main = new File(Environment.getExternalStorageDirectory(), File.separator + Config.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME);
        f_main.mkdirs();
        File f_comp = new File(Environment.getExternalStorageDirectory(), File.separator + Config.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + Config.VIDEO_COMPRESSOR_COMPRESSED_VIDEOS_DIR);
        f_comp.mkdirs();
        File f_temp = new File(Environment.getExternalStorageDirectory(), File.separator + Config.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + Config.VIDEO_COMPRESSOR_TEMP_DIR);
        f_temp.mkdirs();


        demoVideoFolder = f_temp.getAbsolutePath() + "/";

        demoVideoPath = demoVideoFolder + "in.mp4";

        demoVideoOutPath = f_comp.getAbsolutePath() + "/";

        Log.w(TAG, "workFolder: " + workFolder);

        Log.w(TAG, "demoVideoFolder: " + demoVideoFolder);
        Log.w(TAG, "demoVideoPath: " + demoVideoPath);
        Log.w(TAG, "demoVideoOutPath: " + demoVideoOutPath);


        GeneralUtils.copyLicenseFromAssetsToSDIfNeeded(getActivity(), workFolder);
        GeneralUtils.copyDemoVideoFromAssetsToSDIfNeeded(getActivity(), demoVideoFolder);

        int rc = GeneralUtils.isLicenseValid(getActivity(), workFolder);
        Log.i(TAG, "License check RC: " + rc);
    }


    //video compress

    private void startTransCodingCompressFFMP4() {
        Log.i(TAG, "run clicked.");

        demoVideoPath = tempFile.getAbsolutePath();

        scanFile(demoVideoPath);

        Log.w(TAG, "demoVideoPath picked now: " + demoVideoPath);

        if (GeneralUtils.checkIfFileExistAndNotEmpty(demoVideoPath)) {
            new TransCodingBackground(getActivity()).execute();
        } else {
            Toast.makeText(getActivity(), demoVideoPath + " not found", Toast.LENGTH_LONG).show();
        }

    }

    // Returns true if Primary External Storage is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "PRIMARY EXTERNAL STORAGE NOT MOUNTED.", Toast.LENGTH_SHORT).show();
                }
            });

            return false;
        }
    }



    // FF_M_PEG_4 ASYNC_TASK
    @SuppressLint("StaticFieldLeak")
    public class TransCodingBackground extends AsyncTask<String, Integer, Integer> {

        Activity _act;
        String commandStr;
        private ProgressDialog dialog;


        //CONSTRUCTOR
        TransCodingBackground(Activity act) {
            _act = act;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(_act);
            dialog.setTitle("Aqar 55");
            Log.d(TAG, "Start video compression");
            dialog.setMessage("Compressing Video ...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected Integer doInBackground(String... paths) {
            Log.i(TAG, "doInBackground started...");

            // delete previous log
            boolean isDeleted = GeneralUtils.deleteFileUtil(workFolder + "/vk.log");
            Log.i(TAG, "vk deleted: " + isDeleted);

            PowerManager powerManager = (PowerManager) _act.getSystemService(Activity.POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "VK_LOCK");
            Log.d(TAG, "Acquire wake lock");
            wakeLock.acquire();

            /*///////////// Set Commands  /////*/

//          commandStr = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -s 320x240 -r 30 -aspect 3:4 -ab 48000 -ac 2 -ar 22050 -vcodec mpeg4 -b 2097152 /sdcard/videokit/out.mp4";

//			commandStr ="ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vcodec libx264 -preset ultrafast -crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 320x240 -aspect 4:3 /sdcard/videokit/out3.mp4";

//          String[] complexCommand = {"ffmpeg", "-y", "-i", "/sdcard/videokit/in.mp4", "-strict", "experimental", "-s", "160x120", "-r", "25", "-vcodec", "mpeg4", "-b", "150k", "-ab", "48000", "-ac", "2", "-ar", "22050", "/sdcard/videokit/out4.mp4"};

            /*///////////// Set Commands  /////*/


            String mediaDirectory = demoVideoOutPath;

            // Create an image fileName
            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd_HHmmmss", Locale.getDefault());
            s.setTimeZone(TimeZone.getTimeZone("GMT"));
            String timeStamp = s.format(new Date());

            Random random = new Random();
            int n = random.nextInt(500) + 1;
            String imageFileName = "VID_" + timeStamp + n + ".mp4";
            String out = "";

            if (isExternalStorageAvailable()) {
                File mediaStorageDir = new File(mediaDirectory);
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                    Log.w(TAG, "Failed to create directory");
                }

                compFile = new File(mediaStorageDir.getAbsolutePath() + File.separator + imageFileName);
                out = compFile.getAbsolutePath();

            }
            Log.d(TAG, "demoVideo In Path command : " + demoVideoPath);
            Log.d(TAG, "demoVideoOutPath command : " + demoVideoOutPath);
            Log.w(TAG, "Out Path  : " + out);


            String[] complexCommand = {"ffmpeg", "-y", "-i", demoVideoPath, "-strict", "experimental", "-s", "160x120", "-r", "25", "-vcodec", "mpeg4", "-b", "150k", "-ab", "48000", "-ac", "2", "-ar", "22050", out};

            commandStr = "ffmpeg -y -i " + demoVideoPath + " -strict experimental -map_metadata 0:g -s 480x320 -r 25 -vcodec mpeg4 -b 320k -ab 48000 -ac 2 -ar 22050 " + out;

            ///////////////////////////////////////////////////////////////////////

            LoadJNI vk = new LoadJNI();
            try {

                // complex command
//                vk.run(complexCommand, workFolder, getApplicationContext());

                vk.run(GeneralUtils.utilConvertToComplex(commandStr), workFolder, getActivity());

                // running without command validation
                //vk.run(complexCommand, workFolder, getApplicationContext(), false);

                // copying vk.log (internal native log) to the videokit folder
                GeneralUtils.copyFileToFolder(vkLogPath, demoVideoFolder);

            } catch (CommandValidationException e) {
                Log.e(TAG, "vk run exeption.", e);
                commandValidationFailedFlag = true;

            } catch (Throwable e) {
                Log.e(TAG, "vk run exeption.", e);
            } finally {
                if (wakeLock.isHeld())
                    wakeLock.release();
                else {
                    Log.i(TAG, "Wake lock is already released, doing nothing");
                }
            }
            Log.i(TAG, "doInBackground finished");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            dialog.dismiss();
            super.onPostExecute(result);

            Log.i(TAG, "onPostExecute");


            // finished Toast
            String rc;
            if (commandValidationFailedFlag) {
                rc = "Command Vaidation Failed";
            } else {
                rc = GeneralUtils.getReturnCodeFromLog(vkLogPath);
            }
            final String status = rc;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    switch (status) {
                        case "Transcoding Status: Finished OK":

                            Log.w(TAG, "Compressed File path: " + compFile.getAbsolutePath());

                            //COMPRESSION ENDS
                            if (compFile != null) {
                                Log.w(TAG, "Compression done!");

                                File imageFile = compFile;
                                scanFile(imageFile.getAbsolutePath());
                                Uri uri1 = Uri.fromFile(imageFile);

                                setImagesVidsToList(VID_COMPRESS_REQ_CODE, imageFile, uri1);

                            } else {
                                Log.w(TAG, "Compression not done!");
                            }
//                            Toast.makeText(EditImageVidsAct.this, "" + status, Toast.LENGTH_LONG).show();
                            break;

                        case "Transcoding Status: Failed":
                            Toast.makeText(getActivity(), "Check: " + vkLogPath + " for more information.", Toast.LENGTH_LONG).show();
                            break;

                        case "Transcoding Status: Stopped":
                            Toast.makeText(getActivity(), "Check: " + vkLogPath + " for more information.", Toast.LENGTH_LONG).show();
                            break;
                    }

                }
            });
        }

    }



    //  METHOD: TO SCAN IMAGE FILE THAT IS CAPTURED CURRENTLY AND REFLECT IT IN GALLERY App OF DEVICE
    private void scanFile(String path) {

        MediaScannerConnection.scanFile(getActivity(), new String[]{path}, null,
                (path1, uri) -> Log.i(TAG, "Finished scanning of " + path1));
    }

    private void setImagesVidsToList(int code, File file, Uri uri) {

        PictureVideoModel pictureVideoModel = new PictureVideoModel();
        fragmentAddMediaBinding.videolayout.setVisibility(view.VISIBLE);
        fragmentAddMediaBinding.videotitle.setText(videoTitle);

        switch (code) {


            case CAMERA_REQ_CODE: //CAMERA
            case GALLERY_REQ_CODE: //GALLERY
                pictureVideoModel.setImageVidUri(uri);
                pictureVideoModel.setImageVidFile(file);
                pictureVideoModel.setEvent_image_video_id("");
                pictureVideoModel.setEvent_image_video_url("");
                picturesListSender.add(pictureVideoModel);


                Glide.with(getActivity())
                        .load(videosListSender.get(0).getImageVidUri())
                        .into(fragmentAddMediaBinding.imageVideo);

                break;

            case VID_COMPRESS_REQ_CODE: // VIDEO VIA GALLERY
                videosListSender.clear();
                videosList.clear();
                pictureVideoModel.setImageVidUri(uri);
                pictureVideoModel.setImageVidFile(file);
                pictureVideoModel.setEvent_image_video_id("");
                pictureVideoModel.setEvent_image_video_url("");
                videosList.add(pictureVideoModel);
                videosListSender.add(pictureVideoModel);
                Glide.with(getActivity())
                        .load(videosListSender.get(0).getImageVidUri())
                        .into(fragmentAddMediaBinding.imageVideo);


            case VIDEO_CAMERA_CODE:
                videosListSender.clear();
                videosList.clear();
                pictureVideoModel.setImageVidUri(uri);
                pictureVideoModel.setImageVidFile(file);
                pictureVideoModel.setEvent_image_video_id("");
                pictureVideoModel.setEvent_image_video_url("");
                videosList.add(pictureVideoModel);
                videosListSender.add(pictureVideoModel);
                break;
            case VIDEO_REQUEST_CODE:
                videosList.clear();
                pictureVideoModel.setImageVidUri(uri);
                pictureVideoModel.setImageVidFile(file);
                pictureVideoModel.setEvent_image_video_id("");
                pictureVideoModel.setEvent_image_video_url("");
                videosList.add(pictureVideoModel);
                break;
            default:
                break;
        }
    }


}
