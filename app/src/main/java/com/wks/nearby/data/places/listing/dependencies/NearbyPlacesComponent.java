package com.wks.nearby.data.places.listing.dependencies;

import com.wks.nearby.dependencies.ActivityScope;
import com.wks.nearby.dependencies.AppComponent;
import com.wks.nearby.data.places.listing.NearbyPlacesActivity;
import com.wks.nearby.data.places.listing.NearbyPlacesFragment;

import dagger.Component;

/**
 * Created by waqqassheikh on 14/10/2017.
 */
@ActivityScope
@Component(modules = NearbyPlacesModule.class, dependencies = AppComponent.class)
public interface NearbyPlacesComponent {

    void inject(NearbyPlacesActivity nearbyPlacesActivity);

    void inject(NearbyPlacesFragment nearbyPlacesFragment);
}
