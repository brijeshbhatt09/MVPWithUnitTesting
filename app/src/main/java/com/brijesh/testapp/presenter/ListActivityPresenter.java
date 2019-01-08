package com.brijesh.testapp.presenter;

import com.brijesh.testapp.data.DataManager;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.ui.interfaces.MainMVP;
import com.brijesh.testapp.utils.Constant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

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

    /*This method is called by attached view to get data from server*/

    @Override
    public void callHomeApi(String url, boolean isRefresh)
    {
        if (!isRefresh)
        {
            _view.showLoading(true, false);
        }

        Call<ViewResponse> viewData = _dataManager.getResponse(url);
        viewData.enqueue(new Callback<ViewResponse>()
        {
            @Override
            public void onResponse(Call<ViewResponse> call, Response<ViewResponse> response)
            {
                handleSuccessFullResponse(response, isRefresh);
            }

            @Override
            public void onFailure(Call<ViewResponse> call, Throwable t)
            {
                handleFaliure(t, isRefresh);
            }
        });
    }

    /* Handle successfull api response*/
    public void handleSuccessFullResponse(Response<ViewResponse> response, boolean isRefresh)
    {
        if (!isRefresh)
        {
            _view.showLoading(false, false);
        }
        else
        {
            _view.showLoading(false, true);
        }

        if(response != null && response.isSuccessful())
        {
            _view.updateResponse(response.body());
        }
        else
        {
            _view.showError(response.code() + Constant.ERROR);
        }
    }

    /* Handle api faliures*/
    public void handleFaliure(Throwable t, boolean isRefresh)
    {
        if (!isRefresh)
        {
            _view.showLoading(false, false);
        }
        else
        {
            _view.showLoading(false, true);
        }
        if(t instanceof ConnectException)
        {
            _view.showError(Constant.ERROR_CONNECTION);
        }
        else if(t instanceof SocketTimeoutException)
        {
            _view.showError(Constant.ERROR_TIMEOUT);
        }
        else
        {
            _view.showError(t.getMessage() + "\n Refresh to try again.");
        }
    }
}
