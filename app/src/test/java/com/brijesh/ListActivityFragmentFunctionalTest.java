package com.brijesh;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.brijesh.testapp.R;
import com.brijesh.testapp.model.Rows;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.ui.ListActivityFragment;
import com.brijesh.testapp.utils.NetworkCheck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Brijesh.Bhatt on 16/12/18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ListActivityFragmentFunctionalTest
{
    private ListActivityFragment listActivityFragment;
    private TextView errorMessage;
    private FrameLayout appLoading;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        listActivityFragment = new ListActivityFragment();
        SupportFragmentTestUtil.startVisibleFragment(listActivityFragment);
        errorMessage = listActivityFragment.getActivity().findViewById(R.id.errorMessage);
        appLoading = listActivityFragment.getActivity().findViewById(R.id.appLoadingContainer);
    }

    @Test
    public void showLoading()
    {
        listActivityFragment.showLoading(true, false);
        Assert.assertSame(appLoading.getVisibility(), View.VISIBLE);
    }

    @Test
    public void hideLoading()
    {
        listActivityFragment.showLoading(false, false);
        Assert.assertSame(appLoading.getVisibility(), View.GONE);
    }

    @Test
    public void showUpdateResponseSuccessfull() throws Exception
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        List<Rows> rows = new ArrayList<>();
        rows.add(new Rows());
        rows.add(new Rows());
        rows.add(new Rows());
        Mockito.doReturn(rows).when(viewResponse).getRows();
        listActivityFragment.updateResponse(viewResponse);
        Assert.assertSame(errorMessage.getVisibility(), View.GONE);
    }

    @Test
    public void showUpdateResponseWithError() throws Exception
    {
        ViewResponse viewResponse = Mockito.mock(ViewResponse.class);
        Mockito.doReturn(null).when(viewResponse).getRows();
        listActivityFragment.updateResponse(viewResponse);
        Assert.assertSame(errorMessage.getVisibility(), View.VISIBLE);
        Assert.assertEquals(errorMessage.getText(), listActivityFragment.getResources().getString(R.string.no_items));
    }

    @Test
    public void checkNetworkWhenOnline()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(true);
        listActivityFragment.checkNetwork(networkCheck);
        Assert.assertSame(errorMessage.getVisibility(), View.GONE);
    }

    @Test
    public void checkNetworkWhenOffline()
    {
        NetworkCheck networkCheck = Mockito.mock(NetworkCheck.class);
        when(networkCheck.isOnline()).thenReturn(false);
        listActivityFragment.checkNetwork(networkCheck);
        Assert.assertSame(errorMessage.getVisibility(), View.VISIBLE);
        Assert.assertEquals(errorMessage.getText(), listActivityFragment.getResources().getString(R.string.no_network));

    }

    @Test
    public void showError()
    {
        listActivityFragment.showError(listActivityFragment.getResources().getString(R.string.no_items));
        Assert.assertSame(errorMessage.getVisibility(), View.VISIBLE);
        Assert.assertEquals(errorMessage.getText(), listActivityFragment.getResources().getString(R.string.no_items));
    }

}