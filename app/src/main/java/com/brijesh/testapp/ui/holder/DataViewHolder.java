package com.brijesh.testapp.ui.holder;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

    public DataViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /*This method will populate data in all fileds of row and call glide library for image loading*/
    public void bindViewHolder(Rows row)
    {
        _rowTitle.setText(row.getTitle());
        _rowDescription.setText(row.getDescription());
        Glide.with(itemView.getContext())
                .load(row.getImageHref())
                .thumbnail(0.5f)
                .apply(new RequestOptions().placeholder(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.colorPlaceholder))))
                .into(_rowImage);
    }
}
