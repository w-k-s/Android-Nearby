package com.wks.nearby.places.listing;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wks.nearby.BR;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;

import java.util.List;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class NearbyPlacesViewModel extends BaseObservable{

    private Context context;
    private PlacesRepository placesRepository;

    public final ObservableList<Place> items = new ObservableArrayList<>();
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableField<String> dataLoadingError = new ObservableField<>();

    public NearbyPlacesViewModel(Context context,
                                 PlacesRepository placesRepository){
        this.context = context;
        this.placesRepository = placesRepository;
    }

    public void loadNearbyPlaces(double latitude,
                                 double longitude){
        loading.set(true);
        this.placesRepository.loadNearbyPlaces(latitude, longitude, 50000, new PlacesDataSource.LoadNearbyPlacesCallback() {
            @Override
            public void onNearbyPlacesLoaded(@NonNull List<Place> places) {
                loading.set(false);
                items.addAll(places);
                notifyPropertyChanged(BR.empty);
            }

            @Override
            public void onDataNotAvailable(@Nullable String message) {
                loading.set(false);
                dataLoadingError.set(message);
                notifyPropertyChanged(BR.empty);
            }
        });
    }

    @Bindable
    public boolean isEmpty(){
        return items.isEmpty();
    }
}
