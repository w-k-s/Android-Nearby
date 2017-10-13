package com.wks.nearby.data.places.source;

import android.support.annotation.NonNull;

import com.wks.nearby.data.api.ApiResponse;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.PlaceDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public interface PlacesService {

    @GET("nearbysearch/json")
    Call<ApiResponse<List<Place>>> getNearbyPlaces(@NonNull @Query("key") String key,
                                                   @NonNull @Query("location") String latLng,
                                                   @Query("radius") long metres);

    @GET("details/json")
    Call<ApiResponse<PlaceDetail>> getPlaceDetails(@NonNull @Query("key") String key,
                                                   @NonNull @Query("placeid") String placeId);
}
