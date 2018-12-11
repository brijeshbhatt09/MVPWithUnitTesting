package com.brijesh.testapp.ui;

import android.app.Activity;
import android.os.Build;
import android.support.test.filters.LargeTest;
import android.support.test.filters.RequiresDevice;
import android.support.test.filters.SdkSuppress;
import android.support.test.filters.SmallTest;
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

    @Test
    public void TestAllData(){
        View view = mActivity.findViewById(R.id.fragmentContainer);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    @Test
    @RequiresDevice
    public void testRequiresDevice() {
        Log.d("Test Filters", "This test requires a device");
        ListActivity ListActivity = mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", ListActivity);

    }
    @Test
    @SdkSuppress(minSdkVersion = 19)
    public void testMinSdkVersion() {
        Log.d("Test Filters", "Checking for min sdk >= 19");
        ListActivity ListActivity = mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", ListActivity);
    }
    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.KITKAT)
    public void testMinBuild() {
        Log.d("Test Filters", "Checking for min build > kitkat");
        Activity activity = mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", activity);
    }

    @Test
    @SmallTest
    public void testSmallTest() {
        Log.d("Test Filters", "this is a small test");
        ListActivity ListActivity= mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", ListActivity);
    }

    @Test
    @LargeTest
    public void testLargeTest() {
        Log.d("Test Filters", "This is a large test");
        ListActivity ListActivity = mActivityTestRule.getActivity();
        assertNotNull("ListActivity is not available", ListActivity);
    }
}