package com.brijesh.testapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brijesh.testapp.R;
import com.brijesh.testapp.data.AppDataManager;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.ui.interfaces.MainMVP;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements MainMVP.View
{
    /*Declared objects*/

    private MainMVP.Presenter presenter;
    private ArrayList<Rows> rowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*initialize Vies and Presenter*/
        initView();
        initPresenter();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        presenter.onDetach(this);
    }

    private void initView()
    {
        rowsList = new ArrayList<>();
    }


    private void initPresenter()
    {
        /*intialize presenter object, attach view to presenter and call service to fetch data from server*/

        presenter = new ListActivityPresenter(AppDataManager.getInstance(), this);
        presenter.onAttach(this);
        presenter.callWebService("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json");
    }

    @Override
    public void updateResponse(ViewResponse viewResponse)
    {
        /* update view on service response*/
        if(viewResponse != null)
        {
            rowsList.addAll(viewResponse.getRows());
        }
    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
