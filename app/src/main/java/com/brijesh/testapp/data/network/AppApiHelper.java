package com.brijesh.testapp.data.network;
import com.brijesh.testapp.model.ViewResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
/*This class is used to make network calls*/

public class AppApiHelper implements ApiHelper
{
    private ApiRequest apiRequest;
    private static AppApiHelper appApiHelper;


    private AppApiHelper()
    {

    }

    public static AppApiHelper getInstance()
    {
        if (appApiHelper == null)
            {
                appApiHelper = new AppApiHelper();
            }

        return appApiHelper;
    }

    @Override
    public Call<ViewResponse> getResponse(String url)
    {
        return getApiRequest().getResponse(url);
    }

    Retrofit apiClient()
    {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();


        return new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com/")
                .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public ApiRequest getApiRequest()
    {
        if (apiRequest == null)
        {
            apiRequest = apiClient().create(ApiRequest.class);
            return apiRequest;
        }
        return apiClient().create(ApiRequest.class);
    }
}
