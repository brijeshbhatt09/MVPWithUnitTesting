package com.brijesh.testapp.data.network;

import com.brijesh.testapp.model.ViewResponse;

import retrofit2.Call;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */

/*ApiHelper class provide methods for network call*/

public interface ApiHelper
{
    Call<ViewResponse> getResponse(String url);
}
