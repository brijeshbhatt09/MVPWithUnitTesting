package com.brijesh.testapp.data.network;

import com.brijesh.testapp.model.ViewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
public interface ApiRequest
{
    @GET
    Call<ViewResponse> getResponse(@Url String url);
}
