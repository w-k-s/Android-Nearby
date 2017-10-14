package com.wks.nearby.data.places;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class PlaceDetailTests {

    private static final String EXAMPLE_ADDRESS = "5, 48 Pirrama Rd, Pyrmont NSW 2009, Australia";
    private static final String EXAMPLE_PHONE_NUMBER = "(02) 9374 4000";
    private static final String EXAMPLE_INTERNATIONAL_PHONE_NUMBER = "+61 2 9374 4000";
    private static final float EXAMPLE_RATING = 4.5f;
    private static final String EXAMPLE_WEBSITE =
            "https://www.google.com.au/about/careers/locations/sydney/";

    private static final String EXAMPLE_JSON =
            "{\n" +
            "      \"formatted_address\" : \""+EXAMPLE_ADDRESS+"\",\n" +
            "      \"formatted_phone_number\" : \""+EXAMPLE_PHONE_NUMBER+"\",\n" +
            "      \"international_phone_number\" : \""+EXAMPLE_INTERNATIONAL_PHONE_NUMBER+"\",\n" +
            "      \"rating\" : "+EXAMPLE_RATING+",\n" +
            "      \"vicinity\" : \"5, 48 Pirrama Road, Pyrmont\",\n" +
            "      \"website\" : \""+EXAMPLE_WEBSITE+"\"\n" +
            "}";

    private PlaceDetail placeDetailUnderTest;

    @Before
    public void setup(){
        this.placeDetailUnderTest = new Gson().fromJson(EXAMPLE_JSON,PlaceDetail.class);
    }

    @Test
    public void testFormattedAddress(){
        assertEquals(placeDetailUnderTest.getAddress(),EXAMPLE_ADDRESS);
    }

    @Test
    public void testPhoneNumber(){
        assertEquals(placeDetailUnderTest.getPhoneNumber(),EXAMPLE_PHONE_NUMBER);
    }

    @Test
    public void testInternationalPhoneNumber(){
        assertEquals(
                placeDetailUnderTest.getInternationalPhoneNumber(),
                EXAMPLE_INTERNATIONAL_PHONE_NUMBER
        );
    }

    @Test
    public void testRating(){
        assertEquals(placeDetailUnderTest.getRating(),EXAMPLE_RATING);
    }

    @Test
    public void testWebsite(){
        assertEquals(placeDetailUnderTest.getWebsite(),EXAMPLE_WEBSITE);
    }
}
