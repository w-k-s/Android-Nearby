package com.wks.nearby.places;

import com.wks.nearby.data.places.Geometry;
import com.wks.nearby.data.places.Location;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class GeometryTests {

    @Test
    public void testConstructorWithValidArguments(){

        final double latitude = 10;
        final double longitude = 10;

        final Location location = new Location(latitude,longitude);
        final Geometry geometry = new Geometry(latitude,longitude);

        assertEquals(geometry.getLatitude(),latitude);
        assertEquals(geometry.getLongitude(),longitude);
        assertEquals(geometry.getLocation(),location);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithInvalidArguments(){
        new Geometry(null);
    }
}
