package com.brijesh.testapp.model;

import android.text.TextUtils;

/**
 * Created by ${Brijesh.Bhatt} on 10/12/18.
 */
public class Rows
{
    String title;
    String description;
    String imageHref;

    public String getTitle()
    {
        return !TextUtils.isEmpty(title) ? title.trim() : "";
    }

    public String getDescription()
    {
        return !TextUtils.isEmpty(description) ? description.trim() : "";
    }

    public String getImageHref()
    {
        return imageHref;
    }
}
