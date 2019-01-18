package com.brijesh.testapp.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.brijesh.testapp.data.AppDataManager;
import com.brijesh.testapp.data.DataManager;
import com.brijesh.testapp.model.ViewResponse;
import com.brijesh.testapp.presenter.ListActivityPresenter;
import com.brijesh.testapp.ui.interfaces.MainMVP;
import com.brijesh.testapp.utils.Constant;
import com.brijesh.testapp.utils.RestServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

/**
 * Created by ${Brijesh.Bhatt} on 11/12/18.
 */

/*Required Device or emulator with active window{unlocked}*/

@RunWith(MockitoJUnitRunner.class)
public class ListActivityPresenterTest {

    @Mock
    private DataManager dataManager;
    @Mock
    private MainMVP.View mview;
    private ListActivityPresenter presenter;
    private MockWebServer mockWebServer;
    private Context instrumentationCtx;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        instrumentationCtx = InstrumentationRegistry.getContext();
        presenter = new ListActivityPresenter(dataManager);
        presenter.onAttach(mview);
        presenter.setView();
        dataManager = AppDataManager.getInstance();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Constant.BASE_URL = mockWebServer.url("/").toString();
    }

    @Test
    public void testSuccessFullResponse() throws Exception
    {
        String fileName = "api_success_response.json";
        String URL = "/api_success_response.json";
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(instrumentationCtx, fileName));

        // Enqueue request
        mview.showLoading(true, false);
        mockWebServer.enqueue(response);
        Call<ViewResponse> viewData = dataManager.getResponse(URL);
        Response<ViewResponse> viewResponse = viewData.execute();
        presenter.handleSuccessFullResponse(viewResponse, false);
        assertTrue(viewResponse.isSuccessful());
        assertNotNull(viewResponse.body());
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, false);
        inOrder.verify(mview, times(1)).showLoading(false, false);
        inOrder.verify(mview, times(1)).updateViewOnResponse(viewResponse.body());
    }

    @Test
    public void testSuccessFullResponse_ErrorCode() throws Exception
    {
        String fileName = "api_success_response.json";
        String URL = "/api_success_response.json";
        MockResponse response = new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(instrumentationCtx, fileName));

        // Enqueue request
        mview.showLoading(true, false);
        mockWebServer.enqueue(response);
        Call<ViewResponse> viewData = dataManager.getResponse(URL);
        Response<ViewResponse> viewResponse = viewData.execute();
        presenter.handleSuccessFullResponse(viewResponse, false);
        assertFalse(viewResponse.isSuccessful());
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, false);
        inOrder.verify(mview, times(1)).showLoading(false, false);
        inOrder.verify(mview, times(1)).showError(viewResponse.code() + Constant.ERROR);
    }


    @Test
    public void testFaliure_SocketTimeOut()
    {
        SocketTimeoutException socketTimeoutException = Mockito.mock(SocketTimeoutException.class);
        mview.showLoading(true, false);
        presenter.handleFaliure(socketTimeoutException, false);
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, false);
        inOrder.verify(mview, times(1)).showLoading(false, false);
        inOrder.verify(mview, times(1)).showError(Constant.ERROR_TIMEOUT);
    }

    @Test
    public void testFaliure_Connection()
    {
        ConnectException connectException = Mockito.mock(ConnectException.class);
        mview.showLoading(true, false);
        presenter.handleFaliure(connectException, false);
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, false);
        inOrder.verify(mview, times(1)).showLoading(false, false);
        inOrder.verify(mview, times(1)).showError(Constant.ERROR_CONNECTION);
    }

    @Test
    public void testFaliure()
    {
        Throwable throwable = Mockito.mock(Throwable.class);
        mview.showLoading(true, false);
        presenter.handleFaliure(throwable, false);
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, false);
        inOrder.verify(mview, times(1)).showLoading(false, false);
        inOrder.verify(mview, times(1)).showError(throwable.getMessage() + "\n Refresh to try again.");
    }

    @Test
    public void testfaliure_Refresh()
    {
        Throwable throwable = Mockito.mock(Throwable.class);
        mview.showLoading(true, true);
        presenter.handleFaliure(throwable, true);
        InOrder inOrder = Mockito.inOrder(mview);
        inOrder.verify(mview, times(1)).showLoading(true, true);
        inOrder.verify(mview, times(1)).showLoading(false, true);
        inOrder.verify(mview, times(1)).showError(throwable.getMessage() + "\n Refresh to try again.");
    }


    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}