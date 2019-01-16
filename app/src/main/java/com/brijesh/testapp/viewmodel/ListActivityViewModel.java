package com.brijesh.testapp.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.brijesh.testapp.model.ViewResponse;


/**
 * Created by ${Brijesh.Bhatt} on 16/01/18.
 */

public final class ListActivityViewModel
        extends ViewModel
{
    private ViewResponse viewResponse;

    public void setViewResponse(ViewResponse viewResponse)
    {
        this.viewResponse = viewResponse;
    }

    public ViewResponse getViewResponse()
    {
        return viewResponse;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
