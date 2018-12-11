package com.brijesh.testapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.brijesh.testapp.R;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.ui.holder.DataViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */

/* Adapter class to set data in list*/

public class DataViewAdapter extends RecyclerView.Adapter<DataViewHolder>
{
    private List<Rows> rowsList;

    public DataViewAdapter(ArrayList<Rows> rows)
    {
        this.rowsList = rows;
    }

    /*This will inflate view and returns viewholder bind with view*/
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new DataViewHolder(view);
    }

    /*This method Binds the data with view*/
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position)
    {
        holder.bindViewHolder(rowsList.get(position));
    }

    /*returns size of list if not empty else returns zero*/
    @Override
    public int getItemCount()
    {
        return rowsList != null ? rowsList.size() : 0;
    }
}
