package com.brijesh.testapp.data;


import android.content.Context;

import com.brijesh.testapp.data.network.AppApiHelper;
import com.brijesh.testapp.model.ViewResponse;

import retrofit2.Call;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
/* This class is used to get datamanager object and call the methods implemented from datamanager
* */

public class AppDataManager implements DataManager
{
    private static AppDataManager _dataManager;
    private AppApiHelper _apiHelper;

    private AppDataManager(Context context)
    {
        this._apiHelper = AppApiHelper.getInstance(context);
    }

    public static AppDataManager getInstance(Context context)
    {
        if(_dataManager == null)
        {
            _dataManager = new AppDataManager(context);
        }
        return _dataManager;
    }

    @Override
    public Call<ViewResponse> getResponse(String url)
    {
        return _apiHelper.getResponse(url);
    }
}
