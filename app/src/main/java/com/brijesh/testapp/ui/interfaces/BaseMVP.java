package com.brijesh.testapp.ui.interfaces;


/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */

/*Ba*/
public interface BaseMVP
{
    interface Presenter<V extends View>
    {
        void onAttach(V view);
        void onDetach(V view);
    }

    interface View
    {
        void showLoading();
        void hideLoading();
    }
}
