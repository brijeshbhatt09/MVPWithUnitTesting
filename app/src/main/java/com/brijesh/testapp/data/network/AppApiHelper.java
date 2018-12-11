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
    private ApiRequest _apiRequest;
    private static AppApiHelper _appApiHelper;
    private static final String Base_url = "https://dl.dropboxusercontent.com/";
    private static final int TIME_OUT = 60;


    private AppApiHelper()
    {

    }

    public static AppApiHelper getInstance()
    {
        if (_appApiHelper == null)
            {
                _appApiHelper = new AppApiHelper();
            }

        return _appApiHelper;
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
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();


        return new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public ApiRequest getApiRequest()
    {
        if (_apiRequest == null)
        {
            _apiRequest = apiClient().create(ApiRequest.class);
            return _apiRequest;
        }
        return apiClient().create(ApiRequest.class);
    }
}
