package com.wks.nearby.data.places.listing.adapter;

import android.databinding.ObservableField;

import com.wks.nearby.data.places.Place;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlaceItemViewModel {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> vicinity = new ObservableField<>();

    public PlaceItemViewModel(Place place){
        this.name.set(place.getName());
        this.vicinity.set(place.getVicinity());
    }
}
