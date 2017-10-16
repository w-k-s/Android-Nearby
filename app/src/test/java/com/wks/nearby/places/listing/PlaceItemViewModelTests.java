package com.wks.nearby.places.listing;

import com.wks.nearby.data.places.Geometry;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.places.listing.adapter.PlaceItemViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static com.wks.nearby.TestConstants.EXAMPLE_ICON;
import static com.wks.nearby.TestConstants.EXAMPLE_ID;
import static com.wks.nearby.TestConstants.EXAMPLE_LATITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_LONGITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_NAME;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_HEIGHT;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_REF;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_WIDTH;
import static com.wks.nearby.TestConstants.EXAMPLE_PLACE_ID;
import static com.wks.nearby.TestConstants.EXAMPLE_VICINITY;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by waqqassheikh on 16/10/2017.
 */

public class PlaceItemViewModelTests {

    private static final Place PLACE;

    static{
        PLACE = new Place(
                EXAMPLE_ID,
                EXAMPLE_PLACE_ID,
                EXAMPLE_NAME,
                new Geometry(EXAMPLE_LATITUDE,EXAMPLE_LONGITUDE),
                EXAMPLE_ICON,
                EXAMPLE_VICINITY,
                Arrays.asList(new Photo(EXAMPLE_PHOTO_REF,EXAMPLE_PHOTO_WIDTH,EXAMPLE_PHOTO_HEIGHT))
        );
    }

    @Mock
    PlacesRepository placesRepository;

    @Mock
    NearbyPlacesNavigator navigator;

    PlaceItemViewModel viewModelUnderTest;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        viewModelUnderTest = new PlaceItemViewModel(
                PLACE,
                placesRepository,
                navigator,
                EXAMPLE_PHOTO_WIDTH,
                EXAMPLE_PHOTO_HEIGHT
        );
    }

    @After
    public void tearDown(){
        viewModelUnderTest = null;
    }

    @Test
    public void testBinding(){
        assertEquals(viewModelUnderTest.name.get(),EXAMPLE_NAME);
        assertEquals(viewModelUnderTest.vicinity.get(),EXAMPLE_VICINITY);
        assertEquals(viewModelUnderTest.icon.get(),EXAMPLE_ICON);
    }

    @Test
    public void testOnPlaceItemClick(){
        viewModelUnderTest.onPlaceItemClick();

        verify(navigator).openPlaceDetails(eq(EXAMPLE_PLACE_ID));
    }
}
