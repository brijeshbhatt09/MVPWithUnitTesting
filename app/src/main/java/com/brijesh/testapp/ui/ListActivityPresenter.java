package com.brijesh.testapp.ui;

import com.brijesh.testapp.data.DataManager;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.ui.interfaces.BasePresenter;
import com.brijesh.testapp.ui.interfaces.MainMVP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */

/* presenter class responsible to hanlde all the request made by attached view*/

public class ListActivityPresenter<V extends MainMVP.View> extends BasePresenter<V> implements MainMVP.Presenter<V>
{

    DataManager _dataManager;
    MainMVP.View _view;

    public ListActivityPresenter(DataManager dataManager, MainMVP.View view)
    {
        this._dataManager = dataManager;
        this._view = view;
    }

    /*this method is called by attached view to get data from server*/

    @Override
    public void callWebService(String url)
    {
        _view.showLoading();

        Call<ViewResponse> viewData = _dataManager.getResponse(url);
        viewData.enqueue(new Callback<ViewResponse>()
        {
            @Override
            public void onResponse(Call<ViewResponse> call, Response<ViewResponse> response)
            {
                _view.hideLoading();
                if(response != null && response.isSuccessful())
                {
                    _view.updateResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ViewResponse> call, Throwable t)
            {
                _view.hideLoading();
            }
        });
    }
}
