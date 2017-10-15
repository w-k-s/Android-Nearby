package com.wks.nearby.places.details.dependencies;

import android.content.Context;

import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.dependencies.ActivityScope;
import com.wks.nearby.places.details.PlaceDetailsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by waqqassheikh on 14/10/2017.
 */
@Module
public class PlaceDetailsModule {

    @Provides
    @ActivityScope
    public PlaceDetailsViewModel placeDetailsViewModel(Context context, PlacesRepository placesRepository){
        return new PlaceDetailsViewModel(context,placesRepository);
    }
}
