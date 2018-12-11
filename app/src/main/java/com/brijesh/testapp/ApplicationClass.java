package com.brijesh.testapp;

import android.app.Application;

import com.brijesh.testapp.utils.NetworkCheck;

/**
 * Created by ${Brijesh.Bhatt.K.Arora} on 12/12/18.
 */
public class ApplicationClass extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        /*intialize network class*/
        NetworkCheck.initNetwork(getApplicationContext());
    }
}
