package com.wks.nearby.places.details.dependencies;

import com.wks.nearby.dependencies.ActivityScope;
import com.wks.nearby.dependencies.AppComponent;
import com.wks.nearby.places.details.PlaceDetailsActivity;
import com.wks.nearby.places.details.PlaceDetailsFragment;

import dagger.Component;

/**
 * Created by waqqassheikh on 14/10/2017.
 */
@ActivityScope
@Component(modules = PlaceDetailsModule.class, dependencies = AppComponent.class)
public interface PlaceDetailsComponent {

    void inject(PlaceDetailsActivity placeDetailsActivity);

    void inject(PlaceDetailsFragment placeDetailsFragment);
}
