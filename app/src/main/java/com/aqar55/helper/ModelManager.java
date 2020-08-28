package com.aqar55.helper;
import android.util.Log;
import com.aqar55.constant.Constants;
import com.aqar55.model.Sign_Up;


public class ModelManager extends BaseManager implements Constants {

    private static final String TAG = ModelManager.class.getSimpleName();
    //Static properties
    private static ModelManager _ModelManger;
    private static String mGenericAuthToken = "";
    //Instance Properties
    private static Sign_Up.DataEntity mCurrentUser = null;
//    private DispatchQueue dispatchQueue =
//            new DispatchQueue("com.queue.serial.modelmanager", DispatchQueue.QoS.userInitiated);
//    private SocialUser socialUser;

    /**
     * private constructor
     */
    private ModelManager() {

    }

    /**
     * method to create a threadsafe singleton class instance
     */
    public static synchronized ModelManager modelManager() {
        if (_ModelManger == null) {
            _ModelManger = new ModelManager();
            mCurrentUser = getDataFromPreferences(kCurrentUser, Sign_Up.DataEntity.class);
            Log.i(TAG, "Current User : " + ((mCurrentUser == null) ? null : mCurrentUser.toString()));
        }
        return _ModelManger;
    }

    /**
     * to initialize the singleton object
     */
    public void initializeModelManager() {
        System.out.print("ModelManager object initialized.");
    }

    /**
     * Stores {@link Sign_Up.DataEntity} to the share preferences and synchronize sharedpreferece
     */
    public synchronized void archiveCurrentUser() {
        saveDataIntoPreferences(mCurrentUser, BaseModel.kCurrentUser);
    }

    /**
     * getter method for genericAuthToken
     */
    public synchronized String getGenericAuthToken() {
        return mGenericAuthToken;
    }




    public synchronized Sign_Up.DataEntity getCurrentUser() {
        return mCurrentUser;
    }

    public synchronized void setCurrentUser(Sign_Up.DataEntity o) {
        mCurrentUser = o;
        archiveCurrentUser();
    }
}
