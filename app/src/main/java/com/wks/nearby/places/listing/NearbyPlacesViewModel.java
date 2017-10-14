package com.wks.nearby.places.listing;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;

import java.util.List;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class NearbyPlacesViewModel {

    private Context context;
    private PlacesRepository placesRepository;

    public final ObservableList<Place> items = new ObservableArrayList<>();

    public NearbyPlacesViewModel(Context context,
                                 PlacesRepository placesRepository){
        this.context = context;
        this.placesRepository = placesRepository;
    }

    public void loadNearbyPlaces(double latitude,
                                 double longitude){
        this.placesRepository.loadNearbyPlaces(latitude, longitude, 50000, new PlacesDataSource.LoadNearbyPlacesCallback() {
            @Override
            public void onNearbyPlacesLoaded(@NonNull List<Place> places) {
                items.addAll(places);
            }

            @Override
            public void onDataNotAvailable(@Nullable String message) {
                Log.e(getClass().getSimpleName(),"Error Loading Places: "+message);
            }
        });
    }
}
