package com.brijesh.testapp.ui;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.brijesh.testapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ${Brijesh.Bhatt} on 23/12/18.
 */
public class ListActivityFragmentTest
{

    @Rule
    public ActivityTestRule<ListActivity>mActivityTestRule = new ActivityTestRule<ListActivity>(ListActivity.class);

    private ListActivity mActivity = null;

    @Before
    public void setUp() throws Exception
    {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch(){

        View view = mActivity.findViewById(R.id.fragmentContainer);
        assertNotNull(view);

        ListActivityFragment listActivityFragment = new ListActivityFragment();
        mActivity.initFragment(listActivityFragment);

        getInstrumentation().waitForIdleSync();

        View fragment = listActivityFragment.getView().findViewById(R.id.swipeRefreshView);
        assertNotNull(fragment);
    }

    @After
    public void tearDown() throws Exception
    {
        mActivity = null;
    }

}