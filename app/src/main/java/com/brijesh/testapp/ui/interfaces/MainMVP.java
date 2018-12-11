package com.brijesh.testapp.ui.interfaces;


import com.brijesh.testapp.model.ViewResponse;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
public interface MainMVP
{
    interface View extends BaseMVP.View
    {
        void updateResponse(ViewResponse viewResponse);
    }

    interface Presenter<T extends MainMVP.View> extends BaseMVP.Presenter<T>
    {
        void callWebService(String url);
    }
}
