package com.wks.nearby.places.details;

import android.content.Context;
import android.content.res.Resources;

import com.wks.nearby.R;
import com.wks.nearby.data.places.Geometry;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.PlaceDetail;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static com.wks.nearby.TestConstants.EXAMPLE_ICON;
import static com.wks.nearby.TestConstants.EXAMPLE_ID;
import static com.wks.nearby.TestConstants.EXAMPLE_INTL_PHONE_NUMBER;
import static com.wks.nearby.TestConstants.EXAMPLE_LATITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_LONGITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_NAME;
import static com.wks.nearby.TestConstants.EXAMPLE_PHONE_NUMBER;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_HEIGHT;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_REF;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_WIDTH;
import static com.wks.nearby.TestConstants.EXAMPLE_PLACE_ADDRESS;
import static com.wks.nearby.TestConstants.EXAMPLE_PLACE_ID;
import static com.wks.nearby.TestConstants.EXAMPLE_RATING;
import static com.wks.nearby.TestConstants.EXAMPLE_VICINITY;
import static com.wks.nearby.TestConstants.EXAMPLE_WEBSITE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by waqqassheikh on 16/10/2017.
 */

public class PlaceDetailsViewModelTests {

    private static final String EXAMPLE_PLACE_DETAILS_ERROR = "Error place details";

    private static final PlaceDetail PLACE_DETAIL;

    static{
        Place place = new Place(
                EXAMPLE_ID,
                EXAMPLE_PLACE_ID,
                EXAMPLE_NAME,
                new Geometry(EXAMPLE_LATITUDE,EXAMPLE_LONGITUDE),
                EXAMPLE_ICON,
                EXAMPLE_VICINITY,
                Arrays.asList(new Photo(EXAMPLE_PHOTO_REF,EXAMPLE_PHOTO_WIDTH,EXAMPLE_PHOTO_HEIGHT))
        );
        PLACE_DETAIL = new PlaceDetail(
                place,
                EXAMPLE_PLACE_ADDRESS,
                EXAMPLE_PHONE_NUMBER,
                EXAMPLE_INTL_PHONE_NUMBER,
                EXAMPLE_WEBSITE,
                EXAMPLE_RATING
        );
    }

    @Mock
    Context context;

    @Mock PlacesRepository placesRepository;

    @Mock PlaceDetailsNavigator navigator;

    @Captor ArgumentCaptor<PlacesDataSource.LoadPlaceDetailsCallback> loadPlaceDetailsCallbackCaptor;

    PlaceDetailsViewModel viewModelUnderTest;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        setupContext();

        viewModelUnderTest = new PlaceDetailsViewModel(context,placesRepository);
        viewModelUnderTest.setNavigator(navigator);
        viewModelUnderTest.setPlaceId(EXAMPLE_PLACE_ID);
    }

    private void setupContext(){
        Resources mockResources = mock(Resources.class);
        when(mockResources.getDimensionPixelSize(R.dimen.place_details_banner_image_height)).thenReturn(10);

        when(context.getResources()).thenReturn(mockResources);
    }

    @After
    public void tearDown(){
        viewModelUnderTest = null;
    }

    @Test
    public void testLoadPlaceDetails_RequestSuccessful(){
        viewModelUnderTest.start();

        assertTrue(viewModelUnderTest.loading.get());

        verify(placesRepository).loadPlaceDetails(eq(EXAMPLE_PLACE_ID),loadPlaceDetailsCallbackCaptor.capture());

        loadPlaceDetailsCallbackCaptor.getValue().onPlaceDetailsLoaded(PLACE_DETAIL);

        assertFalse(viewModelUnderTest.loading.get());

        assertEquals(viewModelUnderTest.name.get(),EXAMPLE_NAME);
        assertEquals(viewModelUnderTest.rating.get(),EXAMPLE_RATING);
        assertEquals(viewModelUnderTest.icon.get(),EXAMPLE_ICON);
        assertEquals(viewModelUnderTest.address.get(),EXAMPLE_PLACE_ADDRESS);
        assertEquals(viewModelUnderTest.phoneNumber.get(),EXAMPLE_PHONE_NUMBER);
        assertEquals(viewModelUnderTest.internationalPhoneNumber.get(),EXAMPLE_INTL_PHONE_NUMBER);
        assertEquals(viewModelUnderTest.website.get(),EXAMPLE_WEBSITE);
    }

    @Test
    public void testLoadPlaceDetails_RequestFailure(){

        viewModelUnderTest.start();

        verify(placesRepository).loadPlaceDetails(eq(EXAMPLE_PLACE_ID),loadPlaceDetailsCallbackCaptor.capture());

        loadPlaceDetailsCallbackCaptor.getValue().onDataNotAvailable(EXAMPLE_PLACE_DETAILS_ERROR);

        assertEquals(viewModelUnderTest.placeDetailsError.get(),EXAMPLE_PLACE_DETAILS_ERROR);
    }

    @Test
    public void testOnWebsiteClicked(){
        viewModelUnderTest.website.set(EXAMPLE_WEBSITE);
        viewModelUnderTest.onWebsiteClicked();

        verify(navigator).openBrowser(eq(EXAMPLE_WEBSITE));
    }

    @Test
    public void testOnAddressClicked(){
        viewModelUnderTest.start();

        verify(placesRepository).loadPlaceDetails(eq(EXAMPLE_PLACE_ID),loadPlaceDetailsCallbackCaptor.capture());
        loadPlaceDetailsCallbackCaptor.getValue().onPlaceDetailsLoaded(PLACE_DETAIL);

        viewModelUnderTest.onAddressClicked();

        verify(navigator).openMaps(eq(EXAMPLE_LATITUDE),eq(EXAMPLE_LONGITUDE));
    }

}
