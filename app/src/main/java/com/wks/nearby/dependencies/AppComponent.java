package com.wks.nearby.dependencies;

import android.content.Context;

import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.dependencies.modules.AppModule;
import com.wks.nearby.dependencies.modules.repositories.places.PlacesRepositoryModule;

import dagger.Component;

/**
 * Created by waqqassheikh on 13/10/2017.
 */
@AppScope
@Component(modules = {AppModule.class, PlacesRepositoryModule.class})
public interface AppComponent {

    Context getContext();

    PlacesRepository getPlacesRepository();
}
