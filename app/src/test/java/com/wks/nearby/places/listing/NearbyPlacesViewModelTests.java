package com.wks.nearby.places.listing;

import android.content.Context;
import android.content.res.Resources;

import com.wks.nearby.R;
import com.wks.nearby.data.places.Geometry;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.places.listing.adapter.PlaceItemViewModel;

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
import static com.wks.nearby.TestConstants.EXAMPLE_LATITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_LONGITUDE;
import static com.wks.nearby.TestConstants.EXAMPLE_NAME;
import static com.wks.nearby.TestConstants.EXAMPLE_NEXT_PAGE_TOKEN;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_HEIGHT;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_REF;
import static com.wks.nearby.TestConstants.EXAMPLE_PHOTO_WIDTH;
import static com.wks.nearby.TestConstants.EXAMPLE_PLACE_ID;
import static com.wks.nearby.TestConstants.EXAMPLE_VICINITY;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by waqqassheikh on 16/10/2017.
 */

public class NearbyPlacesViewModelTests {

    private static final String ERROR_LOCATION_NOT_AVAILABLE = "error location not available";
    private static final String ERROR_NEARBY_PLACES_UNAVAILABLE = "eror nearby places unavailable";

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
    Context context;

    @Mock
    PlacesRepository placesRepository;

    @Mock
    LocationController locationController;

    @Mock
    NearbyPlacesNavigator navigator;

    @Captor ArgumentCaptor<LocationRetrievedCallback> locationRetrievedCallbackCaptor;

    @Captor ArgumentCaptor<PlacesDataSource.LoadNearbyPlacesCallback> nearbyPlacesCallbackCaptor;

    NearbyPlacesViewModel viewModelUnderTest;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        setupContext();

        viewModelUnderTest = new NearbyPlacesViewModel(context,placesRepository);
        viewModelUnderTest.setNavigator(navigator);
        viewModelUnderTest.setLocationController(locationController);
    }

    private void setupContext(){
        Resources mockResources = mock(Resources.class);
        when(mockResources.getDimensionPixelSize(R.dimen.place_item_image_height)).thenReturn(EXAMPLE_PHOTO_HEIGHT);
        when(mockResources.getDimensionPixelSize(R.dimen.place_item_image_width)).thenReturn(EXAMPLE_PHOTO_WIDTH);

        when(context.getString(R.string.location_not_available)).thenReturn(ERROR_LOCATION_NOT_AVAILABLE);
        when(context.getResources()).thenReturn(mockResources);
    }

    @After
    public void tearDown(){
        viewModelUnderTest = null;
    }

    @Test
    public void testRefresh(){
        viewModelUnderTest.refresh();

        //clear items
        assertEquals(viewModelUnderTest.items.size(),0);

        //loads location
        verify(locationController).determineLocation(locationRetrievedCallbackCaptor.capture());
        assertTrue(viewModelUnderTest.loadingLocation.get());

        locationRetrievedCallbackCaptor.getValue().onLocationRetrieved(EXAMPLE_LATITUDE,EXAMPLE_LONGITUDE);

        assertFalse(viewModelUnderTest.loadingLocation.get());

        //loads places
        assertTrue(viewModelUnderTest.loadingPlaces.get());
        verify(placesRepository).loadNearbyPlaces(eq(EXAMPLE_LATITUDE),eq(EXAMPLE_LONGITUDE),anyLong(),(String) isNull(),nearbyPlacesCallbackCaptor.capture());

        nearbyPlacesCallbackCaptor.getValue().onNearbyPlacesLoaded(Arrays.asList(PLACE),EXAMPLE_NEXT_PAGE_TOKEN);

        assertFalse(viewModelUnderTest.loadingPlaces.get());
        assertEquals(viewModelUnderTest.items.size(),1);
        assertEquals(viewModelUnderTest.getItemCount(),1);
        assertFalse(viewModelUnderTest.isEmpty());
    }

    @Test
    public void testLocationUnavailable(){
        viewModelUnderTest.refresh();

        verify(locationController).determineLocation(locationRetrievedCallbackCaptor.capture());

        locationRetrievedCallbackCaptor.getValue().onLocationUnavailable();

        assertFalse(viewModelUnderTest.loadingPlaces.get());
        assertEquals(viewModelUnderTest.dataLoadingError.get(),ERROR_LOCATION_NOT_AVAILABLE);
        assertEquals(viewModelUnderTest.getItemCount(),0);
        assertTrue(viewModelUnderTest.isEmpty());
    }

    @Test
    public void testNearbyPlacesUnavailable(){
        viewModelUnderTest.refresh();

        verify(locationController).determineLocation(locationRetrievedCallbackCaptor.capture());

        locationRetrievedCallbackCaptor.getValue().onLocationRetrieved(EXAMPLE_LATITUDE,EXAMPLE_LONGITUDE);

        verify(placesRepository).loadNearbyPlaces(anyDouble(),anyDouble(),anyLong(),(String) isNull(),nearbyPlacesCallbackCaptor.capture());

        nearbyPlacesCallbackCaptor.getValue().onDataNotAvailable(ERROR_NEARBY_PLACES_UNAVAILABLE);

        assertEquals(viewModelUnderTest.dataLoadingError.get(),ERROR_NEARBY_PLACES_UNAVAILABLE);
    }

    @Test
    public void testViewModelForItem(){
        viewModelUnderTest.refresh();

        verify(locationController).determineLocation(locationRetrievedCallbackCaptor.capture());

        locationRetrievedCallbackCaptor.getValue().onLocationRetrieved(EXAMPLE_LATITUDE,EXAMPLE_LONGITUDE);

        verify(placesRepository).loadNearbyPlaces(anyDouble(),anyDouble(),anyLong(),(String) isNull(),nearbyPlacesCallbackCaptor.capture());

        nearbyPlacesCallbackCaptor.getValue().onNearbyPlacesLoaded(Arrays.asList(PLACE),EXAMPLE_NEXT_PAGE_TOKEN);

        PlaceItemViewModel itemViewModel = viewModelUnderTest.viewModelForItem(0);

        assertNotNull(itemViewModel);
        assertEquals(itemViewModel.name.get(),EXAMPLE_NAME);
        assertEquals(itemViewModel.icon.get(),EXAMPLE_ICON);
        assertEquals(itemViewModel.vicinity.get(),EXAMPLE_VICINITY);
    }
}
