package com.brijesh.testapp.ui;

import com.brijesh.testapp.R;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.utils.NetworkCheck;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ${Brijesh.Bhatt} on 05/01/19.
 */
@RunWith(MockitoJUnitRunner.class)
public class FunctionalTestCaseUI
{
    @Mock
    private ListActivityFragment mview;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mview = Mockito.mock(ListActivityFragment.class);
    }

    @Test
    public void showLoading()
    {
        mview.showLoading(true, false);
        verify(mview).showLoading(true, false);
        onView(allOf(withId(R.id.loading), isDisplayed()));
    }

    @Test
    public void hideLoading()
    {
        mview.showLoading(false, false);
        verify(mview).showLoading(false, false);
        onView(allOf(withId(R.id.loading), not(isDisplayed())));
    }

    @Test
    public void showUpdateResponse()
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        mview.updateResponse(viewResponse);
        onView(allOf(withId(R.id.errorMessage), not(isDisplayed())));
    }

    @Test
    public void showUpdateResponseWithError()
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        when(viewResponse.getRows()).thenReturn(null);
        mview.updateResponse(viewResponse);
        onView(allOf(withId(R.id.errorMessage), isDisplayed()));
    }

    @Test
    public void TestInitPresenterWithNetwork()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(true);
        mview.initPresenter();
        onView(allOf(withId(R.id.errorMessage), not(isDisplayed())));
    }

    @Test
    public void TestInitPresenterWithoutNetwork()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(false);
        mview.initPresenter();
        onView(allOf(withId(R.id.errorMessage), isDisplayed()));
    }

    @Test
    public void showError()
    {
        mview.showError("Not data to display. Refresh to try again.");
        verify(mview).showError("Not data to display. Refresh to try again.");
        onView(allOf(withId(R.id.errorMessage), isDisplayed()));
    }
}
