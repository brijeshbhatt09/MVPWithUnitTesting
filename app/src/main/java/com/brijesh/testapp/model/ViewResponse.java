package com.brijesh.testapp.model;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
public class ViewResponse
{
    String title;
    ArrayList<Rows> rows;

    public String getTitle()
    {
        return !TextUtils.isEmpty(title) ? title.trim() : "";
    }

    public ArrayList<Rows> getRows()
    {
        return rows;
    }
}
