package com.anchor.API;

import com.anchor.activities.Global_Data;
import com.anchor.helper.UnsafeOkHttpClient;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://150.242.140.105:8006/metal/api/v1/";

//    public static final String BASE_URL = "http://e3deedc8.ngrok.io/metal/api/v1/";

   // public static final String BASE_URL = "http://150.242.140.105:8005/metal/api/performance/v1/ ";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
//            //integrating okHttp
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder()
//                                            .addInterceptor(interceptor)
//                                            .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
