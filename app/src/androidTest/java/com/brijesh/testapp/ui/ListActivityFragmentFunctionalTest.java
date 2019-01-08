package com.brijesh.testapp.ui;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.brijesh.testapp.R;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.utils.NetworkCheck;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

/**
 * Created by ${Brijesh.Bhatt} on 05/01/19.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListActivityFragmentFunctionalTest
{
    @Rule
    public FragmentTestRule<?, ListActivityFragment> fragmentTestRule =
            FragmentTestRule.create(ListActivityFragment.class);

    private ListActivityFragment listActivityFragment;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        listActivityFragment = fragmentTestRule.getFragment();
    }

    @Test
    public void showLoading()
    {
        listActivityFragment.showLoading(true, false);
        onView(withId(R.id.appLoadingContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void hideLoading()
    {
        listActivityFragment.showLoading(false, false);
        onView(withId(R.id.appLoadingContainer)).check(matches(not(isDisplayed())));
    }

    @Test
    public void showUpdateResponse()
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        List<Rows> rows = new ArrayList<>();
        rows.add(new Rows());
        rows.add(new Rows());
        rows.add(new Rows());
        Mockito.doReturn(rows).when(viewResponse).getRows();
        listActivityFragment.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                listActivityFragment.updateResponse(viewResponse);
            }
        });
        onView(withId(R.id.errorMessage)).check(matches(not(isDisplayed())));
    }

    @Test
    public void showUpdateResponseWithError()
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        Mockito.doReturn(null).when(viewResponse).getRows();
        listActivityFragment.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                listActivityFragment.updateResponse(viewResponse);
            }
        });
        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void TestInitPresenterWithNetwork()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(true);
        listActivityFragment.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                listActivityFragment.initPresenter(networkCheck);
            }
        });
        onView(withId(R.id.errorMessage)).check(matches(not(isDisplayed())));
    }

    @Test
    public void TestInitPresenterWithoutNetwork()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(false);
        listActivityFragment.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                listActivityFragment.initPresenter(networkCheck);
            }
        });
        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()));

    }

    @Test
    public void showError()
    {
        listActivityFragment.showError("No data to display. Refresh to try again.");
        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()));
    }
}
