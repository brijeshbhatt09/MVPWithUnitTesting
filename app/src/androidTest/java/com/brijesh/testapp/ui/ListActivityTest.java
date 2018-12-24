package com.brijesh.testapp.ui;

import android.app.Activity;
import android.os.Build;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.brijesh.testapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */
public class ListActivityTest
{

    @Rule
    public ActivityTestRule<ListActivity>mActivityTestRule = new ActivityTestRule<ListActivity>(ListActivity.class);

    private ListActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch(){
        View view = mActivity.findViewById(R.id.fragmentContainer);
        assertNotNull(view);

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.KITKAT)
    public void testMinBuild() {
        Log.d("Test Filters", "Checking for min build > kitkat");
        Activity activity = mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", activity);
    }
}