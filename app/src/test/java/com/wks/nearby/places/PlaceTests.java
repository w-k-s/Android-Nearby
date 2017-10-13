package com.wks.nearby.places;

import com.google.gson.Gson;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.Place;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class PlaceTests {

    private static final String EXAMPLE_ID = "21a0b251c9b8392186142c798263e289fe45b4aa";
    private static final String EXAMPLE_PLACE_ID = "ChIJyWEHuEmuEmsRm9hTkapTCrk";
    private static final String EXAMPLE_ICON_URL =
            "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png";
    private static final String EXAMPLE_NAME = "Rhythmboat Cruises";
    private static final String EXAMPLE_PHOTO_REF = "photo_ref";
    private static final String EXAMPLE_VICINITY = "Pyrmont Bay Wharf Darling Dr, Sydney";

    private static final long EXAMPLE_WIDTH = 519;
    private static final long EXAMPLE_HEIGHT = 270;

    private static final double EXAMPLE_LAT = -33.870775;
    private static final double EXAMPLE_LON = 151.199025;

    private static final String EXAMPLE_JSON =
            "{\n" +
            "   \"geometry\" : {\n" +
            "      \"location\" : {\n" +
            "         \"lat\" : "+EXAMPLE_LAT+",\n" +
            "         \"lng\" : "+EXAMPLE_LON+"\n" +
            "      }\n" +
            "   },\n" +
            "   \"icon\" : \""+EXAMPLE_ICON_URL+"\",\n" +
            "   \"id\" : \""+EXAMPLE_ID+"\",\n" +
            "   \"name\" : \""+EXAMPLE_NAME+"\",\n" +
            "   \"photos\" : [\n" +
            "      {\n" +
            "         \"height\" : "+EXAMPLE_HEIGHT+",\n" +
            "         \"html_attributions\" : [],\n" +
            "         \"photo_reference\" : \""+EXAMPLE_PHOTO_REF+"\",\n" +
            "         \"width\" : "+EXAMPLE_WIDTH+"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"place_id\" : \""+EXAMPLE_PLACE_ID+"\",\n" +
            "   \"vicinity\" : \""+EXAMPLE_VICINITY+"\"\n" +
            "}";

    private Place placeUnderTest;

    @Before
    public void setup(){
        this.placeUnderTest = new Gson().fromJson(EXAMPLE_JSON,Place.class);
    }

    @Test
    public void testGeometry(){
        assertEquals(placeUnderTest.getGeometry().getLatitude(),EXAMPLE_LAT);
        assertEquals(placeUnderTest.getGeometry().getLongitude(),EXAMPLE_LON);
    }

    @Test
    public void testIcon(){
        assertEquals(placeUnderTest.getIcon(),EXAMPLE_ICON_URL);
    }

    @Test
    public void testId(){
        assertEquals(placeUnderTest.getId(),EXAMPLE_ID);
    }

    @Test
    public void testName(){
        assertEquals(placeUnderTest.getName(),EXAMPLE_NAME);
    }

    @Test
    public void testPhotos(){
        assertEquals(placeUnderTest.getPhotos().size(),1);

        final Photo photo = placeUnderTest.getPhotos().get(0);
        assertEquals(photo.getWidth(),EXAMPLE_WIDTH);
        assertEquals(photo.getHeight(),EXAMPLE_HEIGHT);
        assertEquals(photo.getReference(),EXAMPLE_PHOTO_REF);
    }

    @Test
    public void testPlaceId(){
        assertEquals(placeUnderTest.getPlaceId(),EXAMPLE_PLACE_ID);
    }

    @Test
    public void testVicinity(){
        assertEquals(placeUnderTest.getVicinity(),EXAMPLE_VICINITY);
    }
}
