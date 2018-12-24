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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Brijesh.Bhatt on 11/12/18.
 */
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
        presenter = new ListActivityPresenter(dataManager,mview);
        dataManager = AppDataManager.getInstance();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Constant.BASE_URL = mockWebServer.url("/").toString();
    }

    @Test
    public void showDataWhenOnSuccessfulResponse() throws Exception
    {

        String fileName = "api_success_response.json";
        String URL = "/api_success_response.json";
       MockResponse response = new MockResponse()
               .setResponseCode(200)
               .setBody(RestServiceTestHelper.getStringFromFile(instrumentationCtx, fileName));

        // Enqueue request
        mockWebServer.enqueue(response);
        Call<ViewResponse> viewData = dataManager.getResponse(URL);
        Response<ViewResponse> viewResponse = viewData.execute();
        assertTrue(viewResponse.isSuccessful());
        assertNotNull(viewResponse.body());
    }

    @Test
    public void showErrorWhenOnErrorResponse() throws Exception
    {

        String fileName = "api_faliure_response.json";
        String URL = "/api_faliure_response.json";
        MockResponse response = new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(instrumentationCtx, fileName));

        // Enqueue request
        mockWebServer.enqueue(response);
        Call<ViewResponse> viewData = dataManager.getResponse(URL);
        Response<ViewResponse> viewResponse = viewData.execute();
        assertFalse(viewResponse.isSuccessful());
    }


    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}