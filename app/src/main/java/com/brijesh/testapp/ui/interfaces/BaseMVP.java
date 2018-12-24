package com.brijesh.testapp.ui.interfaces;


/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */

/*Base Interface MVP*/
public interface BaseMVP
{
    interface Presenter<V extends View>
    {
        void onAttach(V view);
        void onDetach(V view);
    }

    interface View
    {
        void showLoading(boolean isVisible, boolean isRefresh);
        void showError(String errorMssg);
    }
}
