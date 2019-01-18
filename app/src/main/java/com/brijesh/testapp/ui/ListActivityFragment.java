package com.brijesh.testapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brijesh.testapp.R;
import com.brijesh.testapp.data.AppDataManager;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.presenter.ListActivityPresenter;
import com.brijesh.testapp.ui.adapter.DataViewAdapter;
import com.brijesh.testapp.ui.interfaces.MainMVP;
import com.brijesh.testapp.ui.interfaces.UpdateTitleListener;
import com.brijesh.testapp.utils.Constant;
import com.brijesh.testapp.utils.NetworkCheck;
import com.brijesh.testapp.viewmodel.ListActivityViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */
public class ListActivityFragment extends Fragment implements MainMVP.View
{
    @BindView(R.id.recyclerView)
    RecyclerView _recyclerView;
    @BindView(R.id.swipeRefreshView)
    SwipeRefreshLayout _swipeRefreshView;
    @BindView(R.id.appLoadingContainer)
    FrameLayout _loadingContainer;
    @BindView(R.id.errorMessage)
    TextView errorMessage;

    private RecyclerView.LayoutManager _layoutManager;
    private DataViewAdapter _dataViewAdapter;
    private MainMVP.Presenter _presenter;
    private ArrayList<Rows> _rowsList;
    private ArrayList<Rows> _filterList;
    private UpdateTitleListener _updateTitleListener;
    private NetworkCheck networkCheck;
    private ListActivityViewModel viewModel;
    private Unbinder unbinder;


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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        /*create viewModel using provider to hold data*/
        viewModel = ViewModelProviders.of(getActivity()).get(ListActivityViewModel.class);

        /*initialize NetworkCheck class*/
        networkCheck = new NetworkCheck();
        networkCheck.initNetwork(getContext());

        /*initialize Views and Presenter*/
        initView();
        initPresenterandCheckData(networkCheck);
    }

    /*This method will set listener to update app title*/
    public void set_updateTitleListener(UpdateTitleListener updateTitleListener)
    {
        _updateTitleListener = updateTitleListener;
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
    }

    public void initView()
    {
        /* This will create empty list, adapter and set adapter to recycler*/
        _rowsList = new ArrayList<>();
        _filterList = new ArrayList<>();
        _dataViewAdapter = new DataViewAdapter(_filterList);
        _layoutManager = new LinearLayoutManager(getContext());
        _recyclerView.setLayoutManager(_layoutManager);
        _recyclerView.setAdapter(_dataViewAdapter);

        /*set listener to refresh list of rows*/
        _swipeRefreshView.setOnRefreshListener(mOnRefreshListener);

    }

    /*intialize presenter object, attach view to presenter  and check viewModel for data*/
    public void initPresenterandCheckData(NetworkCheck networkCheck)
    {
        _presenter = new ListActivityPresenter(AppDataManager.getInstance());
        _presenter.onAttach(this);
        _presenter.setView();

        if(viewModel.getViewResponse() != null)
        {
            errorMessage.setVisibility(View.GONE);
            updateViewOnResponse(viewModel.getViewResponse());
        }
        else
        {
            checkNetwork(networkCheck);
        }
    }

    /*Check Network:
    if available: call service to fetch data from server
    esle: show error*/

    public void checkNetwork(NetworkCheck networkCheck)
    {
        if(networkCheck.isOnline())
        {
            errorMessage.setVisibility(View.GONE);
            _presenter.callHomeApi(Constant.HOME_API_URL, false);
        }
        else
        {
            showError(getResources().getString(R.string.no_network));
        }
    }

    /* Added listener to refresh list of rows by calling service again*/
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            if (_presenter != null)
            {
                /*Check network If not network available show error message else call service*/
                if(networkCheck.isOnline())
                {
                    errorMessage.setVisibility(View.GONE);
                    _presenter.callHomeApi(Constant.HOME_API_URL, true);
                }
                else
                {
                    showLoading(false, true);
                    showError(getResources().getString(R.string.no_network));
                }
            }
        }
    };

    /*Show or Hide loader while fetching data*/
    @Override
    public void showLoading(boolean isVisible, boolean isRefresh)
    {
        if(isRefresh)
        {
            _swipeRefreshView.setRefreshing(false);
            return;
        }
        if (isVisible)
        {
            _loadingContainer.setVisibility(View.VISIBLE);
        } else
        {
            _loadingContainer.setVisibility(View.GONE);
        }
    }

    /* update view on service response*/
    @Override
    public void updateViewOnResponse(ViewResponse viewResponse)
    {
        if (viewResponse != null)
        {
            viewModel.setViewResponse(viewResponse);
            if(viewResponse.getRows() != null && viewResponse.getRows().size() > 0)
            {
                if(_updateTitleListener != null)
                {
                    _updateTitleListener.onTitleUpdateCallback(viewResponse.getTitle()); // call method in activity to update toolbar title
                }
                _rowsList.clear();
                _rowsList.addAll(viewResponse.getRows());
                filerList();
                _dataViewAdapter.notifyDataSetChanged();
            }
            else
            {
                showError(getResources().getString(R.string.no_items));
            }
        }
    }

    /*This method display error message for any Api faliure or unexpected result*/
    @Override
    public void showError(String errorMssg)
    {
        if(_filterList != null && _filterList.size() > 0)
        {
            Toast.makeText(getContext(), errorMssg, Toast.LENGTH_SHORT).show();
        }
        else
        {
            errorMessage.setText(errorMssg);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    /*This method will search for null values in list and create list with non null values*/
    private void filerList()
    {
        _filterList.clear();
        for(Rows rows : _rowsList)
        {
            if(!TextUtils.isEmpty(rows.getTitle()) || !TextUtils.isEmpty(rows.getDescription()) || !TextUtils.isEmpty(rows.getImageHref()))
            {
                _filterList.add(rows);
            }
        }
    }

}
