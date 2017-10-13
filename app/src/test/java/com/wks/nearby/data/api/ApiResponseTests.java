package com.wks.nearby.data.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by waqqassheikh on 13/10/2017.
 */
public class ApiResponseTests {

    private static final String EXAMPLE_NEXT_PAGE_TOKEN = "EXAMPLE_NEXT_PAGE_TOKEN";

    private static final String EXAMPLE_INVALID_REQUEST_MESSAGE = "TEST_INVALID_REQUEST";
    private static final String EXAMPLE_OVER_QUERY_LIMIT_MESSAGE = "OVER_QUERY_LIMIT";
    private static final String EXAMPLE_REQUEST_DENIED_MESSAGE = "REQUEST_DENIED";

    private static final String EXAMPLE_LIST_ITEM = "TEST";

    private static final String OK_RESPONSE =
            "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [\""+EXAMPLE_LIST_ITEM+"\"],\n" +
            "   \"status\" : \"OK\",\n" +
            "   \"next_page_token\" : \""+ EXAMPLE_NEXT_PAGE_TOKEN +"\"\n" +
            "}";

    private static final String INVALID_REQUEST_RESPONSE =
            "{\n" +
            "   \"status\" : \"INVALID_REQUEST\",\n" +
            "   \"error_message\" : \""+ EXAMPLE_INVALID_REQUEST_MESSAGE +"\"\n" +
            "}";

    private static final String ZERO_RESULTS_RESPONSE =
            "{\n" +
            "   \"status\" : \"ZERO_RESULTS\",\n" +
            "   \"results\" : [],\n" +
            "   \"error_message\" : \"Zero Results\"\n" +
            "}";

    private static final String OVER_QUERY_LIMIT_RESPONSE =
            "{\n" +
            "   \"status\" : \"OVER_QUERY_LIMIT\",\n" +
            "   \"error_message\" : \""+EXAMPLE_OVER_QUERY_LIMIT_MESSAGE+"\"\n" +
            "}";

    private static final String REQUEST_DENIED_RESPONSE =
            "{\n" +
            "   \"status\" : \"REQUEST_DENIED\",\n" +
            "   \"error_message\" : \""+EXAMPLE_REQUEST_DENIED_MESSAGE+"\"\n" +
            "}";

    @Test
    public void testOkStatusWithListOfStringsResult(){
        ApiResponse<List<String>> apiResponse = parseApiResponse(OK_RESPONSE);

        //Test Status
        assertEquals(apiResponse.getStatus(), ApiStatus.OK);
        assertTrue(apiResponse.isOK());

        //Test Result
        assertEquals(apiResponse.getResult().size(),1);
        assertTrue(apiResponse.getResult().contains(EXAMPLE_LIST_ITEM));

        //Test Next Page Token
        assertEquals(apiResponse.getNextPageToken(),EXAMPLE_NEXT_PAGE_TOKEN);
    }

    @Test
    public void testInvalidRequestResponse(){
        ApiResponse<Void> apiResponse = parseApiResponse(INVALID_REQUEST_RESPONSE);

        assertEquals(apiResponse.getStatus(),ApiStatus.INVALID_REQUEST);
        assertEquals(apiResponse.getErrorMessage(),EXAMPLE_INVALID_REQUEST_MESSAGE);
        assertFalse(apiResponse.isOK());
    }

    @Test
    public void testZeroResultsResponse(){
        ApiResponse<List<String>> apiResponse = parseApiResponse(ZERO_RESULTS_RESPONSE);

        assertEquals(apiResponse.getStatus(), ApiStatus.ZERO_RESULTS);
        assertEquals(apiResponse.getResult().size(),0);
        assertFalse(apiResponse.isOK());
    }

    @Test
    public void testOverQueryLimitResponse(){
        ApiResponse<Void> apiResponse = parseApiResponse(OVER_QUERY_LIMIT_RESPONSE);

        assertEquals(apiResponse.getStatus(),ApiStatus.OVER_QUERY_LIMIT);
        assertEquals(apiResponse.getErrorMessage(),EXAMPLE_OVER_QUERY_LIMIT_MESSAGE);
        assertFalse(apiResponse.isOK());
    }

    @Test
    public void testRequestDeniedResponse(){
        ApiResponse<Void> apiResponse = parseApiResponse(REQUEST_DENIED_RESPONSE);

        assertEquals(apiResponse.getStatus(), ApiStatus.REQUEST_DENIED);
        assertEquals(apiResponse.getErrorMessage(), EXAMPLE_REQUEST_DENIED_MESSAGE);
        assertFalse(apiResponse.isOK());
    }

    private <T> ApiResponse<T> parseApiResponse(String json){
        TypeToken<ApiResponse<T>> typeToken = new TypeToken<ApiResponse<T>>() {};
        return new Gson().fromJson(json,typeToken.getType());
    }
}
