package com.brijesh.testapp.ui.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brijesh.testapp.R;
import com.brijesh.testapp.model.Rows;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */
public class DataViewHolder extends RecyclerView.ViewHolder
{
    /*bind Views with resource id*/

    @BindView(R.id.rowTitle) TextView _rowTitle;
    @BindView(R.id.rowDescription) TextView _rowDescription;
    @BindView(R.id.rowImage) ImageView _rowImage;
    @BindView(R.id.rowContainer) LinearLayout _rowContainer;

    public DataViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /*This method will populate data in all fileds of row and call glide library for image loading*/
    public void bindViewHolder(Rows row)
    {
        setData(row.getTitle(), _rowTitle);
        setData(row.getDescription(), _rowDescription);
        Glide.with(itemView.getContext())
                .load(row.getImageHref())
                .thumbnail(0.5f)
                .apply(new RequestOptions().placeholder((ContextCompat.getDrawable(itemView.getContext(), R.drawable.default_image))))
                .into(_rowImage);
    }


    private void setData(String data, TextView view)
    {
        if(!TextUtils.isEmpty(data))
        {
            view.setText(data);
        }
        else
        {
            view.setText(itemView.getResources().getString(R.string.default_text));
        }
    }
}
