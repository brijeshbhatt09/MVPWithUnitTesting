package com.brijesh.testapp.presenter;


import com.brijesh.testapp.ui.interfaces.BaseMVP;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
public class BasePresenter<V extends BaseMVP.View> implements BaseMVP.Presenter<V>
{
    private V baseView;

    @Override
    public void onAttach(V view)
    {
        this.baseView = view;
    }

    @Override
    public void onDetach(V view)
    {
        this.baseView = null;
    }

    public V getBaseView()
    {
        return baseView;
    }
}
