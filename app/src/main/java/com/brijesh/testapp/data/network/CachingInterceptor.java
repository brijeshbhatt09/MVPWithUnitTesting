package com.brijesh.testapp.data.network;

import com.brijesh.testapp.utils.NetworkCheck;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */
public class CachingInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        Request newRequest;
        if(NetworkCheck.isOnline())
        {
            /*
             *  If there is Internet, get the cache that was stored 5 seconds ago.
             *  If the cache is older than 5 seconds, then discard it,
             *  and indicate an error in fetching the response.
             *  The 'max-age' attribute is responsible for this behavior.
             */
            newRequest = request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
        }
        else
        {
            /*
             *  If there is no Internet, get the cache that was stored 7 days ago.
             *  If the cache is older than 7 days, then discard it,
             *  and indicate an error in fetching the response.
             *  The 'max-stale' attribute is responsible for this behavior.
             *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
             */
            newRequest = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
            // End of if-else statement
        }
        return chain.proceed(newRequest);
    }
}
