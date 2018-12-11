package com.brijesh.testapp.data.network;

import android.content.Context;

import com.brijesh.testapp.model.ViewResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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
    private static final int cacheSize = 10 * 1024 * 1024; // 10 MB
    private Context _context;
    private Cache _cache;


    private AppApiHelper(Context context)
    {
        this._context = context;
        _cache = new Cache(_context.getCacheDir(), cacheSize);
    }

    public static AppApiHelper getInstance(Context context)
    {
        if (_appApiHelper == null)
            {
                _appApiHelper = new AppApiHelper(context);
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

        CachingInterceptor cachingInterceptor = new CachingInterceptor();

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cache(_cache)
                .addInterceptor(cachingInterceptor)
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
