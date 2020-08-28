package com.aqar55.activitys;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import com.aqar55.ItemClick.UploadFileMoreDataReqListener;
import com.aqar55.R;
import com.aqar55.adapters.Chat_Detail_Property_Adapter;
import com.aqar55.databinding.LayoutChatDetailBinding;
import com.aqar55.helper.FileUploadManager;
import com.aqar55.helper.ModelManager;
import com.aqar55.helper.Toaster;
import com.aqar55.model.ChatDelateModel;
import com.aqar55.model.MessageChat;
import com.aqar55.model.UserBlockModel;
import com.aqar55.model.UserChatListResponse;
import com.aqar55.retrofit.Api;
import com.aqar55.retrofit.ApiClientConnection;
import com.aqar55.utill.DialogFactory;
import com.aqar55.utill.MyDialog;
import com.aqar55.utill.TakeImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    LayoutChatDetailBinding layoutChatDetailBinding;
    View view;
    Chat_Detail_Property_Adapter chat_detail_property_adapter;
    private Socket mSocket;
    private List<MessageChat.Data> MessageList;
    private MessageChat messageChat;
    private String roomId = "";
    private String propertyId;
    private String proId;
    private String fromActivity = "";
    private UserChatListResponse.Data dataAll;
    private String senderId = "";
    private String recieverId = "";
    private String mImagePath = "";
    private final String SEND_MEDIA = "MEDIA";
    private final String SEND_AUDIO = "AUDIO";
    private final String GIF_IMAGE = "GIF";
    private String description = "";
    public static final int IMAGE_FILE = 5;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};
    private final int CAMERA_PIC_REQUEST = 11, REQ_CODE_PICK_IMAGE = 1;


    private String recorded_file_path = null;
    private HashMap<String, String> media_path = new HashMap<>();

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    public static Intent getIntent(Context context, UserChatListResponse.Data data, String from) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("kDataAll", (Serializable) data);
        intent.putExtra("fromAct", from);
        return intent;
    }

    public static Intent getIntent(Context context, String data, String proId, String title, String descrip) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("kData", (Serializable) data);
        intent.putExtra("propId", proId);
        intent.putExtra("kTitle", title);
        intent.putExtra("kDescrip", descrip);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutChatDetailBinding = DataBindingUtil.setContentView(this, R.layout.layout_chat_detail);
        layoutChatDetailBinding.chatDetailsImageviewOption.setOnClickListener(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        layoutChatDetailBinding.ivBack.setOnClickListener(this);
        layoutChatDetailBinding.ivAttach.setOnClickListener(this);
        layoutChatDetailBinding.ivRecord.setOnClickListener(this);
        // chat_detail_property_adapter = new Chat_Detail_Property_Adapter(MessageList, this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("sucess"));


        if (getIntent() != null) {
            fromActivity = (String) getIntent().getSerializableExtra("fromAct");
            if (fromActivity == null) {
                recieverId = (String) getIntent().getSerializableExtra("kData");
                proId = (String) getIntent().getSerializableExtra("propId");
                senderId = ModelManager.modelManager().getCurrentUser().getId();
                description = (String) getIntent().getSerializableExtra("kDescrip");
                layoutChatDetailBinding.textView.setText(description);
                layoutChatDetailBinding.tvTitle.setText(getIntent().getSerializableExtra("kTitle").toString());
            } else {
                dataAll = (UserChatListResponse.Data) getIntent().getSerializableExtra("kDataAll");
                recieverId = dataAll.getReceiver_id();
                senderId = dataAll.getSenderId();
                description = dataAll.getDescription();
                layoutChatDetailBinding.textView.setText(description);
                layoutChatDetailBinding.tvTitle.setText(dataAll.getTitle());
            }

        }

        MyDialog.getInstance(this).showDialog(this);
        MessageList = new ArrayList<>();
        connectSockettest();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_details_imageview_option:
                menuPopUp();
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivAttach:
                openBottomSheetBanner();
                break;
            case R.id.ivRecord:
                if (layoutChatDetailBinding.edtSoftInput.getText().toString().trim().isEmpty()) {
                    Toaster.toast("Please write message");
                    return;
                }
                sendMessage();
                break;
        }
    }

    private void menuPopUp() {
        PopupMenu popup = new PopupMenu(ChatActivity.this, layoutChatDetailBinding.chatDetailsImageviewOption);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().toString().equalsIgnoreCase("Delete")) {
                chatDelete();

            } else {
                onUserBlock();
            }
            return true;
        });

        popup.show(); //showing popup menu
    }


    private void openBottomSheetBanner() {
        View view = getLayoutInflater().inflate(R.layout.custom_bottonsheet_layout, null);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        view.findViewById(R.id.camera_view).setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, TakeImage.class);
            intent.putExtra("from", "camera");
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.gallery_view).setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, TakeImage.class);
            intent.putExtra("from", "gallery");
            startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
            mBottomSheetDialog.dismiss();
        });

        view.findViewById(R.id.cancel_view).setOnClickListener(v -> mBottomSheetDialog.dismiss());



    }


    //send message
    private void sendMessage() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("sender_id", senderId);
            obj.put("receiver_id", recieverId);
            obj.put("room_id", roomId);
            obj.put("title", layoutChatDetailBinding.tvTitle.getText().toString());
            obj.put("description", description);
            obj.put("property_id", proId);
            obj.put("attachment_type", "Text");
            obj.put("message", layoutChatDetailBinding.edtSoftInput.getText().toString().trim());
            mSocket.emit("initialChat", obj);
            layoutChatDetailBinding.edtSoftInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getRoomId() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("receiver_id", recieverId);
            obj.put("sender_id", senderId);
            mSocket.emit("getRoomId", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        //  mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("initialChat", onNewMessage);
        // mSocket.off("room join", onLogin);
        mSocket.off();
    }

    /////////////chatting////////////////////
    private Emitter.Listener onConnect = args -> runOnUiThread(() -> {
        //DialogFactory.showToast(getApplicationContext(), getString(R.string.connected));
        Toast.makeText(ChatActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    });

    private Emitter.Listener onDisconnect = args -> runOnUiThread(() -> {
        //DialogFactory.showToast(getApplicationContext(), getString(R.string.disconnected));
        Toast.makeText(ChatActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
    });

    private Emitter.Listener onConnectError = args -> runOnUiThread(() -> {
        // DialogFactory.showLog("ERROR CONNECT", "ERROR CONNECT");
        Toast.makeText(ChatActivity.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
    });

    private void connectSockettest() {
        MyDialog.getInstance(ChatActivity.this).hideDialog();
        try {
            mSocket = IO.socket("http://3.18.37.151:3004");

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        //  mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        //  mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        //  mSocket.on("room join", onLogin);
        mSocket.on("initialChat", onNewMessage);
        mSocket.on("getRoomId", getRoomIdData);
        mSocket.connect();
        try {
            JSONObject object = new JSONObject();
            object.put("room_id", roomId);
            object.put("sender_id", senderId);
            object.put("receiver_id", recieverId);
            mSocket.emit("room join", object);
            getRoomId();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: 25/1/18 messagechecking
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String msg;
                String senderId;
                String media;
                String time;
                String messageType;
                try {
                    msg = data.getString("message");
                    senderId = data.getString("sender_id");
                    time = data.getString("created");
                    roomId = data.getString("room_id");
                    messageType = data.getString("attachment_type");

                    if (messageType.equalsIgnoreCase("MEDIA")) {
                        media = data.getString("attachment");
                        MessageList.add(new MessageChat.Data(123, time, "", "", roomId, senderId, "12345", msg, messageType, media));
                    } else {
                        MessageList.add(new MessageChat.Data(123, time, "", "", roomId, senderId, "12345", msg, messageType, ""));

                    }
                    layoutChatDetailBinding.recycleChatDetailProperty.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    chat_detail_property_adapter = new Chat_Detail_Property_Adapter(MessageList, ChatActivity.this);
                    layoutChatDetailBinding.recycleChatDetailProperty.setAdapter(chat_detail_property_adapter);
                    layoutChatDetailBinding.recycleChatDetailProperty.setVisibility(View.VISIBLE);
                    chat_detail_property_adapter.notifyDataSetChanged();
                    scrollToBottom();

                } catch (Exception e) {
                    try {
                        Toaster.toast(data.getString("msg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }


            });
        }
    };


    // hit getProfessionalPropertyListing API
    private void getChatDetails(String roomIdd) {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<MessageChat> getPropertyListingCall = api.chatDetails(senderId, roomIdd);
        getPropertyListingCall.enqueue(new Callback<MessageChat>() {
            @Override
            public void onResponse(@NonNull Call<MessageChat> call, @NonNull Response<MessageChat> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        MessageList = response.body().getData();
                        layoutChatDetailBinding.recycleChatDetailProperty.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                        chat_detail_property_adapter = new Chat_Detail_Property_Adapter(MessageList, ChatActivity.this);
                        layoutChatDetailBinding.recycleChatDetailProperty.setAdapter(chat_detail_property_adapter);
                        layoutChatDetailBinding.recycleChatDetailProperty.setVisibility(View.VISIBLE);
                        chat_detail_property_adapter.notifyDataSetChanged();
                        scrollToBottom();
                    } else {
                        Toaster.toast(response.body().getMessage());
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<MessageChat> call, @NonNull Throwable t) {

            }
        });
    }

    private void chatDelete() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<ChatDelateModel> getPropertyListingCall = api.chatDelete(roomId);
        getPropertyListingCall.enqueue(new Callback<ChatDelateModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatDelateModel> call, @NonNull Response<ChatDelateModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        Toaster.toast(response.body().getResponseMessage());

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


    private void onUserBlock() {
        Api api = ApiClientConnection.getInstance().createApiInterface();
        Call<UserBlockModel> getPropertyListingCall = api.userBlock(recieverId, senderId);
        getPropertyListingCall.enqueue(new Callback<UserBlockModel>() {
            @Override
            public void onResponse(@NonNull Call<UserBlockModel> call, @NonNull Response<UserBlockModel> response) {
                if (response.body().getResponseCode() == 200) {
                    Toaster.toast(response.body().getResponseMessage());

                } else {
                    Toaster.toast(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserBlockModel> call, @NonNull Throwable t) {


            }
        });
    }
    // TODO: 25/1/18 messagechecking
    private Emitter.Listener getRoomIdData = args -> runOnUiThread(() -> {
        JSONObject data = (JSONObject) args[0];
        try {
            roomId = data.getJSONObject("Data").getString("room_id");
            getChatDetails(roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST) {
                if (data.getStringExtra("filePath") != null) {
                    mImagePath = data.getStringExtra("filePath");
                    sendAndGetBinaryData(mImagePath, IMAGE_FILE);
                }
            } else {
                if (data.getStringExtra("filePath") != null) {

                    mImagePath = data.getStringExtra("filePath");
                    sendAndGetBinaryData(mImagePath, IMAGE_FILE);
                }
            }

        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void sendAndGetBinaryData(String path, int type) {
        String uni_code = String.valueOf(System.currentTimeMillis());
        media_path.put(uni_code, path);
        if (media_path.size() == 1) {
            uploadFileOnServer(media_path);
        }
    }

    private void uploadFileOnServer(HashMap<String, String> map) {
        if (map.size() > 0) {
            for (String entry : map.keySet()) {
                String key = entry;
                String value = map.get(key);
                //  new FileUploadTask(value, key).execute();//Value ==== Media Path, key========Unicode
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new FileUploadTask(value, key).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");

                break;
            }
        }
    }

    private class FileUploadTask extends AsyncTask<String, Integer, String> {
        private String file_path = "";

        private String attachment_type = SEND_MEDIA;
        private String uni_code = "";

        private UploadFileMoreDataReqListener callback;
        private FileUploadManager mFileUploadManager;

        public FileUploadTask(String file_path, String uni_code) {
            this.file_path = file_path;

            this.uni_code = uni_code;
            DialogFactory.showLog("path uni_code", "path uni_code-- " + file_path);
            attachment_type = SEND_MEDIA;
        }

        @Override
        protected void onPreExecute() {
            DialogFactory.showLog("mFileUploadManager", "in it mFileUploadManager for-- " + file_path);
            mFileUploadManager = new FileUploadManager();
        }

        @Override
        protected String doInBackground(String... params) {
            boolean isSuccess = mSocket.connected();
            if (isSuccess) {
                mFileUploadManager.prepare(file_path, ChatActivity.this);

                // This function gets callback when server requests more data
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener);
                // This function will get a call back when upload completes
                setUploadFileCompleteListener();
                // Tell server we are ready to start uploading ..
                if (mSocket.connected()) {
                    JSONArray jsonArr = new JSONArray();
                    JSONObject res = new JSONObject();
                    try {
                        res.put("Name", mFileUploadManager.getFileName());
                        res.put("Size", mFileUploadManager.getFileSize());
                        res.put("room_id", roomId);
                        jsonArr.put(res);
                        mSocket.emit("uploadFileStart", jsonArr);
                    } catch (JSONException e) {
                        //TODO: Log errors some where..
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DialogFactory.showLog("onPostExecute result-- ", "onPostExecute result--");
            if (s == null) {
                return;
            }
            if (s.equalsIgnoreCase("OK")) {
                media_path.remove(uni_code);
                mFileUploadManager.close();
                mSocket.off("uploadFileMoreDataReq", uploadFileMoreDataReq);
                mSocket.off("uploadFileCompleteRes", onCompletedddd);
                uploadFileOnServer(media_path);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] > 107) {
                if (media_path.containsKey(uni_code)) {
                    onPostExecute("OK");
                }
            }
        }

        private UploadFileMoreDataReqListener mUploadFileMoreDataReqListener = new UploadFileMoreDataReqListener() {
            @Override
            public void uploadChunck(int offset, int percent) {
                DialogFactory.showLog("CHAT_ACTIVITY", String.format("Uploading %d completed. offset at: %d", percent, offset));
                // Read the next chunk
                mFileUploadManager.read(offset);
                if (mSocket.connected()) {
                    JSONArray jsonArr = new JSONArray();
                    JSONObject res = new JSONObject();
                    try {
                        res.put("Name", mFileUploadManager.getFileName());
                        res.put("Data", mFileUploadManager.getData());
                        res.put("chunkSize", mFileUploadManager.getBytesRead());
                        res.put("room_id", roomId);
                        res.put("sender_id", senderId);
                        res.put("receiver_id", recieverId);
                        res.put("message", "Read Attachment");
                        res.put("unique_code", uni_code);
                        res.put("attachment_type", attachment_type);
                        res.put("title", layoutChatDetailBinding.tvTitle.getText().toString());
                        res.put("description", description);
                        jsonArr.put(res);
                        mSocket.emit("uploadFileChuncks", jsonArr);
                    } catch (JSONException e) {
                        //TODO: Log errors some where..
                    }
                }
            }

            @Override
            public void err(JSONException e) {
                // TODO Auto-generated method stub
            }
        };

        Emitter.Listener uploadFileMoreDataReq = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (int jj = 0; jj < args.length; jj++) {
                    DialogFactory.showLog("setUploadFileMoreDataReqListener", "setUploadFileMoreDataReqListener-- " + args[jj]);
                }
                try {
                    JSONObject json_data = (JSONObject) args[0];
                    int place = json_data.getInt("Place");
                    int percent = json_data.getInt("Percent");
                    publishProgress(json_data.getInt("Percent"));
                    callback.uploadChunck(place, percent);
                } catch (JSONException e) {
                    callback.err(e);
                }
            }
        };

        Emitter.Listener onCompletedddd = args -> {
            JSONObject json_data = (JSONObject) args[0];
            if (json_data.has("IsSuccess")) {
                publishProgress(110);
                return;
            }
        };

        private void setUploadFileMoreDataReqListener(final UploadFileMoreDataReqListener callbackk) {
            callback = callbackk;
            mSocket.on("uploadFileMoreDataReq", uploadFileMoreDataReq);
        }

        private void setUploadFileCompleteListener() {
            mSocket.on("uploadFileCompleteRes", onCompletedddd);
        }
    }

    private void scrollToBottom() {
        layoutChatDetailBinding.recycleChatDetailProperty.scrollToPosition(chat_detail_property_adapter.getItemCount() - 1);
    }


}
