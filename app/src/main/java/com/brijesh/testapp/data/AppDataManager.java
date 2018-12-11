package com.brijesh.testapp.data;


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
    private static AppDataManager dataManager;
    private AppApiHelper apiHelper;

    private AppDataManager()
    {
        this.apiHelper = AppApiHelper.getInstance();
    }

    public static AppDataManager getInstance()
    {
        if(dataManager == null)
        {
            dataManager = new AppDataManager();
        }
        return dataManager;
    }

    @Override
    public Call<ViewResponse> getResponse(String url)
    {
        return apiHelper.getResponse(url);
    }
}
