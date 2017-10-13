package com.wks.nearby.dependencies;

import com.wks.nearby.dependencies.modules.AppModule;
import com.wks.nearby.dependencies.modules.ImageLoaderModule;
import com.wks.nearby.dependencies.modules.repositories.places.PlacesRepositoryModule;

import dagger.Component;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

@Component(modules = {AppModule.class, ImageLoaderModule.class, PlacesRepositoryModule.class})
public interface AppComponent {
}
