package com.brijesh.testapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by ${Vijay.K.Arora} on 11/12/18.
 */
public class ListActivityFragment extends Fragment implements MainMVP.View
{
    @BindView(R.id.recyclerView)
    RecyclerView _recyclerView;
    @BindView(R.id.swipeRefreshView)
    SwipeRefreshLayout _swipeRefreshView;

    private RecyclerView.LayoutManager _layoutManager;
    private DataViewAdapter _dataViewAdapter;
    private MainMVP.Presenter _presenter;
    private ArrayList<Rows> _rowsList;
    private static final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        /*Bind view this fragment to butterknife*/
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        /*initialize Vies and Presenter*/
        initView();
        initPresenter();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        _presenter.onDetach(this);
    }

    private void initView()
    {
        /* This will create empty list, adapter  and set adapter to recycler*/

        _rowsList = new ArrayList<>();
        _dataViewAdapter = new DataViewAdapter(_rowsList);
        _layoutManager = new LinearLayoutManager(getContext());
        _recyclerView.setLayoutManager(_layoutManager);
        _recyclerView.setAdapter(_dataViewAdapter);

        /*set listener to refresh list of rows*/
        _swipeRefreshView.setOnRefreshListener(mOnRefreshListener);
    }

    /*intialize presenter object, attach view to presenter and call service to fetch data from server*/
    private void initPresenter()
    {
        _presenter = new ListActivityPresenter(AppDataManager.getInstance(), this);
        _presenter.onAttach(this);
        _presenter.callWebService(URL);
    }

    /* Added listener to refresh list of rows by calling service again*/
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            if (_presenter != null)
            {
                _presenter.callWebService(URL);
            }
        }
    };

    /*Show loader while fetching data*/
    @Override
    public void showLoading()
    {
        _swipeRefreshView.setRefreshing(true);
    }

    /*Hide loader after response*/
    @Override
    public void hideLoading()
    {
        _swipeRefreshView.setRefreshing(false);
    }

    /* update view on service response*/
    @Override
    public void updateResponse(ViewResponse viewResponse)
    {
        if (viewResponse != null)
        {
            _rowsList.clear();
            _rowsList.addAll(viewResponse.getRows());
            _dataViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
