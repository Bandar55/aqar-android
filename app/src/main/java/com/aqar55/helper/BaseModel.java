package com.aqar55.helper;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Type;

import com.aqar55.constant.Constants;

public class BaseModel implements Constants {
    private static final String TAG = BaseModel.class.getSimpleName();
    //*********************************Public Static Methods*******************************************/
    /**To check for nil, if value nil then return @""*/
    private static Object properValue(Object value)   {
        if(value == null  || value.equals("") || ((value instanceof String) && value.equals(Constants.kEmptyString)))  {
            return  Constants.kEmptyString;
        }
        else{
            return value;
        }
    }

    /**Return the nonnull value of type Object and ensure for integrity of data in models corresponding to key*/
    @SuppressWarnings("unchecked")
    public static <T> T getValue(JSONObject jsonResponse, String key, Type type)    {
        try {
            if(type == Boolean.class)   {
                return (T) Boolean.valueOf(jsonResponse.get(key).equals(1));
            }
            else     {
                return (T)properValue(jsonResponse.get(key));
            }
        } catch (Exception e) {
            Log.e(TAG," "+e);
            if(type == String.class)    {
                return (T)Constants.kEmptyString;
            }
            else if(type == Integer.class || type == Double.class)  {
                return (T)Constants.kEmptyNumber;       //Defualt value
            }
            else if(type == Boolean.class)  {
                return (T) Boolean.FALSE;
            }
            else {
                return (T) Constants.kEmptyString;
            }
        }
    }

}
