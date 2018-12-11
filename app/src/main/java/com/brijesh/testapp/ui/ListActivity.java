package com.brijesh.testapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brijesh.testapp.R;
import com.brijesh.testapp.data.AppDataManager;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.ui.adapter.DataViewAdapter;
import com.brijesh.testapp.ui.interfaces.MainMVP;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */

public class ListActivity extends AppCompatActivity implements MainMVP.View
{
    /*Declared objects and bind view with Resources*/

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private DataViewAdapter dataViewAdapter;
    private MainMVP.Presenter presenter;
    private ArrayList<Rows> rowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*Bind view this activity to butterknife*/

        ButterKnife.bind(this);

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
        /* This will create empty list, adapter  and set adapter to recycler*/

        rowsList = new ArrayList<>();
        dataViewAdapter = new DataViewAdapter(rowsList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataViewAdapter);
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
            dataViewAdapter.notifyDataSetChanged();
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
