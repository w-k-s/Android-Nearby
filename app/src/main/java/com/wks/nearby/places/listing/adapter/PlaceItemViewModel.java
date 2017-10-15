package com.wks.nearby.places.listing.adapter;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.wks.nearby.data.places.Place;
import com.wks.nearby.places.listing.NearbyPlacesNavigator;

import java.lang.ref.WeakReference;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlaceItemViewModel {

    private final Place place;
    private final WeakReference<NearbyPlacesNavigator> navigator;

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> vicinity = new ObservableField<>();
    public final String icon;
    public final String photoReference;

    public PlaceItemViewModel(@NonNull final Place place,
                              @NonNull final NearbyPlacesNavigator navigator){

        checkNotNull(place);
        checkNotNull(navigator);

        this.place = place;
        this.navigator = new WeakReference<NearbyPlacesNavigator>(navigator);

        this.name.set(place.getName());
        this.vicinity.set(place.getVicinity());
        this.icon = place.getIcon();

        if (!place.getPhotos().isEmpty()){
            this.photoReference = place.getPhotos().get(0).getReference();
        }else{
            this.photoReference = null;
        }
    }

    public void onPlaceItemClick(){
        final NearbyPlacesNavigator navigator = this.navigator.get();
        if (navigator != null){
            navigator.openPlaceDetails(place.getPlaceId());
        }
    }
}
