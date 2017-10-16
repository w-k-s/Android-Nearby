package com.wks.nearby.data.places.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.PlaceDetail;

import java.util.List;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public interface PlacesDataSource {

    interface LoadNearbyPlacesCallback{
        void onNearbyPlacesLoaded(@NonNull List<Place> places, @Nullable String nextPageToken);
        void onDataNotAvailable(@Nullable String message);
    }

    interface LoadPlaceDetailsCallback{
        void onPlaceDetailsLoaded(@NonNull PlaceDetail placeDetail);
        void onDataNotAvailable(@Nullable String message);
    }

    void loadNearbyPlaces(double latitude,
                          double longitude,
                          long radius,
                          String pageToken,
                          @NonNull LoadNearbyPlacesCallback callback);

    void loadPlaceDetails(@NonNull String placeId,
                          @NonNull LoadPlaceDetailsCallback callback);

    String imageUrl(@NonNull String photoReference,
                    int width,
                    int height);
}
