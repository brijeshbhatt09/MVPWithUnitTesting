package com.brijesh.testapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */

/*This class is responsible to check Network Availability*/

public class NetworkCheck
{
    private static ConnectivityManager cm;


    public static void initNetwork(Context context)
    {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /*Check network connection */
    public static boolean isOnline()
    {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null)
        {
            return netInfo.isConnected();
        } else
        {
            return false;
        }

    }
}
