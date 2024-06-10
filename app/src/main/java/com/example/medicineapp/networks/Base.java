package com.example.medicineapp.networks;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.medicineapp.networks.interceptor.HandleError;
import com.example.medicineapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Base {

// remember change ip address by ipconfig in terminal because every net lan have a different ip address
    private static final String BASE_URL = "https://api.fda.gov/drug/label.json/";
    private static Retrofit retrofit;
    public static Retrofit getClient(Context context) {
            if (retrofit == null) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .addInterceptor(new HandleError(context));
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(loggingInterceptor);
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
        return retrofit;
    }
}
