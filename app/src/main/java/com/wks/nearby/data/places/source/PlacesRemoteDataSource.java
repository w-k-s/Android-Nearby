package com.wks.nearby.data.places.source;

import android.support.annotation.NonNull;

import com.wks.nearby.data.api.ApiResponse;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.PlaceDetail;
import com.wks.nearby.dependencies.AppScope;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by waqqassheikh on 13/10/2017.
 */
@AppScope
public class PlacesRemoteDataSource implements PlacesDataSource {

    private final PlacesService placesService;

    public PlacesRemoteDataSource(@NonNull PlacesService service){
        this.placesService = service;
    }

    @Override
    public void loadNearbyPlaces(final double latitude,
                                 final double longitude,
                                 final double radius,
                                 @NonNull final LoadNearbyPlacesCallback callback) {

        final String latLng = String.format(Locale.US,"%f,%f",latitude,longitude);

        placesService.getNearbyPlaces("",latLng,50000).enqueue(new Callback<ApiResponse<List<Place>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Place>>> call, Response<ApiResponse<List<Place>>> response) {

                ApiResponse<List<Place>> placesResponse =  response.body();

                if (placesResponse == null || !placesResponse.isOK()){
                    final String message = placesResponse == null ? null : placesResponse.getErrorMessage();
                    callback.onDataNotAvailable(message);
                    return;
                }

                callback.onNearbyPlacesLoaded(placesResponse.getResult());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Place>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void loadPlaceDetails(@NonNull String placeId, @NonNull final LoadPlaceDetailsCallback callback) {

        placesService.getPlaceDetails("",placeId).enqueue(new Callback<ApiResponse<PlaceDetail>>() {
            @Override
            public void onResponse(Call<ApiResponse<PlaceDetail>> call, Response<ApiResponse<PlaceDetail>> response) {
                ApiResponse<PlaceDetail> detailResponse = response.body();

                if (detailResponse == null || !detailResponse.isOK()){
                    final String message = detailResponse == null? null : detailResponse.getErrorMessage();
                    callback.onDataNotAvailable(message);
                    return;
                }

                callback.onPlaceDetailsLoaded(detailResponse.getResult());
            }

            @Override
            public void onFailure(Call<ApiResponse<PlaceDetail>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
