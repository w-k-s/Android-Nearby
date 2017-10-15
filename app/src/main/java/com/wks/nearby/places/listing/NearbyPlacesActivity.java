package com.wks.nearby.places.listing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.wks.nearby.R;
import com.wks.nearby.app.App;
import com.wks.nearby.base.BaseActivity;
import com.wks.nearby.places.details.PlaceDetailsActivity;
import com.wks.nearby.places.listing.dependencies.DaggerNearbyPlacesComponent;

import javax.inject.Inject;

public class NearbyPlacesActivity extends BaseActivity implements NearbyPlacesNavigator{

    @Inject
    NearbyPlacesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject();
        setup();

    }

    private void inject(){
        DaggerNearbyPlacesComponent.builder()
                .appComponent(App.get(this).getComponent())
                .build()
                .inject(this);
    }

    private void setup(){
        setContentView(R.layout.activity_nearby_places);
        setToolbar((Toolbar)findViewById(R.id.toolbar));
        setTitle(getString(R.string.app_name));

        NearbyPlacesFragment fragment = findOrCreateFragment();

        viewModel.setLocationController(fragment);
        viewModel.setNavigator(this);

        fragment.setViewModel(viewModel);
    }

    private NearbyPlacesFragment findOrCreateFragment(){

        NearbyPlacesFragment fragment = (NearbyPlacesFragment)
                getSupportFragmentManager().findFragmentById(R.id.framelayout_container);

        if (fragment == null){
            fragment = new NearbyPlacesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.framelayout_container,fragment,NearbyPlacesFragment.class.getSimpleName())
                    .commit();
        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        viewModel.onActivityDestroyed();
        super.onDestroy();
    }

    //-- NearbyPlacesNavigator

    @Override
    public void openPlaceDetails(@NonNull String placeId) {
        startActivity(PlaceDetailsActivity.newIntent(this,placeId));
    }
}
