package com.wks.nearby.places.listing.dependencies;

import android.content.Context;

import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.dependencies.ActivityScope;
import com.wks.nearby.places.listing.NearbyPlacesViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by waqqassheikh on 14/10/2017.
 */
@Module
public class NearbyPlacesModule {

    @Provides
    @ActivityScope
    public NearbyPlacesViewModel nearbyPlacesViewModel(Context context, PlacesRepository placesRepository){
        return new NearbyPlacesViewModel(context,placesRepository);
    }
}
