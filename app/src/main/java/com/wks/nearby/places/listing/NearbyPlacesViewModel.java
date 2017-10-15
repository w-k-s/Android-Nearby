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
import com.wks.nearby.R;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.places.listing.adapter.PlaceItemViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class NearbyPlacesViewModel extends BaseObservable implements LocationRetrievedCallback{

    private Context context;
    private PlacesRepository placesRepository;

    public final ObservableBoolean loadingLocation = new ObservableBoolean();

    public final ObservableList<Place> items = new ObservableArrayList<>();
    public final ObservableBoolean loadingPlaces = new ObservableBoolean();
    public final ObservableField<String> dataLoadingError = new ObservableField<>();

    private WeakReference<LocationController> locationController;
    private NearbyPlacesNavigator navigator;

    public NearbyPlacesViewModel(Context context,
                                 PlacesRepository placesRepository){
        this.context = context;
        this.placesRepository = placesRepository;
    }

    //-- Getters & Setters

    public void setLocationController(@NonNull LocationController locationController) {
        checkNotNull(locationController);
        this.locationController = new WeakReference<LocationController>(locationController);
    }

    public void setNavigator(NearbyPlacesNavigator navigator) {
        this.navigator = navigator;
    }

    //-- Lifecycle

    public void onActivityDestroyed(){
        this.navigator = null;
    }

    public void start(){
        findLocation();
    }

    public void refresh(){
        loadingPlaces.set(false);
        items.clear();
        findLocation();
    }

    //-- Load Nearby Places


    public void findLocation(){
        final LocationController controller = locationController.get();
        if(controller != null){
            controller.determineLocation(this);
            loadingLocation.set(true);
        }
    }

    private void loadNearbyPlaces(double latitude,
                                  double longitude) {
        loadingPlaces.set(true);
        this.placesRepository.loadNearbyPlaces(
                latitude,
                longitude,
                50000,
                new PlacesDataSource.LoadNearbyPlacesCallback() {
                    @Override
                    public void onNearbyPlacesLoaded(@NonNull List<Place> places) {
                        loadingPlaces.set(false);
                        items.addAll(places);
                        notifyPropertyChanged(BR.empty);
                    }

                    @Override
                    public void onDataNotAvailable(@Nullable String message) {
                        loadingPlaces.set(false);
                        dataLoadingError.set(message);
                        notifyPropertyChanged(BR.empty);
                    }
                }
        );
    }

    @Bindable
    public boolean isEmpty(){
        return items.isEmpty();
    }

    //-- LocationRetrievedCallback

    @Override
    public void onLocationRetrieved(double latitude, double longitude) {
        loadingLocation.set(false);
        loadNearbyPlaces(latitude,longitude);
    }

    @Override
    public void onLocationUnavailable() {
        loadingLocation.set(false);
        dataLoadingError.set(context.getString(R.string.location_not_available));
    }

    //-- Listing Places

    public int getItemCount(){
        return this.items.size();
    }

    public PlaceItemViewModel viewModelForItem(int index){
        final Place place = items.get(index);
        return new PlaceItemViewModel(place,navigator);
    }
}
