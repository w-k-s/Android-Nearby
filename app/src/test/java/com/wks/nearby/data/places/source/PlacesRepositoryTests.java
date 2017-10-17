package com.wks.nearby.data.places.source;

import com.wks.nearby.data.places.Geometry;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.PlaceDetail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static com.wks.nearby.TestConstants.EXAMPLE_NEXT_PAGE_TOKEN;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class PlacesRepositoryTests {

    private PlacesRepository placesRepository;

    private static final Place PLACE_1 = new Place("1","1","Overlook Hotel",new Geometry(10,10));

    private static final String PLACE_DETAIL_ADDRESS = "Sidewinder";
    private static final String PLACE_DETAIL_PHONE_NUMBER = "666";
    private static final String PLACE_DETAIL_INTERNATIONAL_PHONE_NUMBER = "666";

    private static final PlaceDetail PLACE_DETAIL_1 =
            new PlaceDetail(
                    PLACE_1,
                    PLACE_DETAIL_ADDRESS,
                    PLACE_DETAIL_PHONE_NUMBER,
                    PLACE_DETAIL_INTERNATIONAL_PHONE_NUMBER,
                    null,
                    1.0f
            );

    private final List<Place> PLACES = Arrays.asList(
            PLACE_1,
            new Place("2","2","Name 2",new Geometry(20,20))
    );

    private final static String MESSAGE_PLACES_NOT_AVAILABLE = "Places not available";
    private final static String MESSAGE_PLACE_DETAILS_NOT_AVAILANLE = "Details not available";

    @Mock
    private PlacesRemoteDataSource remoteDataSource;

    //Places List

    @Mock
    private PlacesDataSource.LoadNearbyPlacesCallback loadNearbyPlacesCallback;

    @Captor
    private ArgumentCaptor<PlacesDataSource.LoadNearbyPlacesCallback> nearbyPlacesCallbackCaptor;

    //Place Details

    @Mock
    private PlacesDataSource.LoadPlaceDetailsCallback loadPlaceDetailsCallback;

    @Captor
    private ArgumentCaptor<PlacesDataSource.LoadPlaceDetailsCallback> placeDetailsCallbackCaptor;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);

        placesRepository = new PlacesRepository(remoteDataSource);
    }

    @Test
    public void testRetrievingPlacesFromRemoteSource_DataAvailable(){

        final double latitude = 12.001;
        final double longitude = 24.002;
        final long radius = 10000;

        placesRepository.loadNearbyPlaces(latitude,longitude,radius,EXAMPLE_NEXT_PAGE_TOKEN,loadNearbyPlacesCallback);

        verify(remoteDataSource)
                .loadNearbyPlaces(
                        eq(latitude),
                        eq(longitude),
                        eq(radius),
                        eq(EXAMPLE_NEXT_PAGE_TOKEN),
                        nearbyPlacesCallbackCaptor.capture());

        nearbyPlacesCallbackCaptor.getValue().onNearbyPlacesLoaded(PLACES,EXAMPLE_NEXT_PAGE_TOKEN);

        verify(loadNearbyPlacesCallback).onNearbyPlacesLoaded(eq(PLACES),eq(EXAMPLE_NEXT_PAGE_TOKEN));
    }

    @Test
    public void testRetrievingPlacesFromRemoteSource_DataNotAvailable(){

        placesRepository.loadNearbyPlaces(12.0D,13.0D,14000L,EXAMPLE_NEXT_PAGE_TOKEN,loadNearbyPlacesCallback);

        verify(remoteDataSource)
                .loadNearbyPlaces(
                        anyDouble(),
                        anyDouble(),
                        anyLong(),
                        anyString(),
                        nearbyPlacesCallbackCaptor.capture());

        nearbyPlacesCallbackCaptor.getValue().onDataNotAvailable(MESSAGE_PLACES_NOT_AVAILABLE);

        verify(loadNearbyPlacesCallback).onDataNotAvailable(eq(MESSAGE_PLACES_NOT_AVAILABLE));
    }

    @Test
    public void testRetrievePlaceDetailsFromRemoteSource_DataAvailable(){

        final String placeId = "1";

        placesRepository.loadPlaceDetails(placeId,loadPlaceDetailsCallback);

        verify(remoteDataSource)
                .loadPlaceDetails(
                        eq(placeId),
                        placeDetailsCallbackCaptor.capture());

        placeDetailsCallbackCaptor.getValue().onPlaceDetailsLoaded(PLACE_DETAIL_1);

        verify(loadPlaceDetailsCallback).onPlaceDetailsLoaded(eq(PLACE_DETAIL_1));
    }

    @Test
    public void testRetrievePlaceDetailsFromRemoteSource_DataNotAvailable(){

        placesRepository.loadPlaceDetails("2",loadPlaceDetailsCallback);

        verify(remoteDataSource)
                .loadPlaceDetails(
                        anyString(),
                        placeDetailsCallbackCaptor.capture());

        placeDetailsCallbackCaptor.getValue().onDataNotAvailable(MESSAGE_PLACE_DETAILS_NOT_AVAILANLE);

        verify(loadPlaceDetailsCallback).onDataNotAvailable(eq(MESSAGE_PLACE_DETAILS_NOT_AVAILANLE));
    }

}
