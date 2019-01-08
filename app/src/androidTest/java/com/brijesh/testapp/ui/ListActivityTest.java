package com.brijesh.testapp.ui;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.brijesh.testapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */
public class ListActivityTest
{
    ListActivity listActivity = null;

    @Rule
    public ActivityTestRule<ListActivity> mActivityTestRule = new ActivityTestRule<ListActivity>(ListActivity.class);

    @Before
    public void setUp() throws Exception {

        listActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void TestInitFragment(){

        View view = listActivity.findViewById(R.id.fragmentContainer);
        assertNotNull(view);

        ListActivityFragment listActivityFragment = new ListActivityFragment();
        listActivity.initFragment(listActivityFragment);

        getInstrumentation().waitForIdleSync();

        View fragment = listActivityFragment.getView().findViewById(R.id.swipeRefreshView);
        assertNotNull(fragment);
    }

    @Test
    public void TestTitleUpdate(){

        listActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                listActivity.onTitleUpdateCallback("Title");
            }
        });
        onView(withText("Title")).check(matches(isDisplayed()));
    }
}