package com.aqar55.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mobulous06 on 19/7/17.
 */
public class ApiClientConnection {

    private static final String Base_URL = "http://18.189.223.53:3005/api/user/";
    //ServiceProgressDialog serviceProgressDialog;
    private static ApiClientConnection apiClientConnection = null;
    private static Api apiInterface = null, distanceMatrixInterface = null;
    private Api clientService;

    public static ApiClientConnection getInstance() {
        if (apiClientConnection == null) {
            apiClientConnection = new ApiClientConnection();
        }
        return apiClientConnection;
    }

    public Api createApiInterface() {
        if (apiInterface == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(80, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS);

            httpBuilder.addInterceptor(loggingInterceptor);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpBuilder.build());

            Retrofit retrofit = builder.build();
            apiInterface = retrofit.create(Api.class);
        }
        return apiInterface;
    }
//    public ApiInterface createMapApiInterface() {
//        if (distanceMatrixInterface == null) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
//                    .connectTimeout(80, TimeUnit.SECONDS)
//                    .readTimeout(80, TimeUnit.SECONDS);
//
//            httpBuilder.addInterceptor(loggingInterceptor);
//            Retrofit.Builder builder = new Retrofit.Builder()
//                    .baseUrl("https://maps.googleapis.com/")
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .client(httpBuilder.build());
//
//            Retrofit retrofit = builder.build();
//            distanceMatrixInterface = retrofit.create(ApiInterface.class);
//
//        }
//        return distanceMatrixInterface;
//    }
}
