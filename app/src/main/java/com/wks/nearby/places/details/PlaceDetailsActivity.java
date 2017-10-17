package com.wks.nearby.places.details;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.wks.nearby.R;
import com.wks.nearby.app.App;
import com.wks.nearby.base.BaseActivity;
import com.wks.nearby.databinding.ActivityPlaceDetailsBinding;
import com.wks.nearby.places.details.dependencies.DaggerPlaceDetailsComponent;
import com.wks.nearby.places.listing.NearbyPlacesFragment;
import com.wks.nearby.utils.IntentUtils;

import javax.inject.Inject;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

public class PlaceDetailsActivity extends BaseActivity implements PlaceDetailsNavigator{

    private static final String EXTRA_PLACE_ID = "com.wks.nearby.PlaceDetailsActivity.placeId";

    @Inject
    PlaceDetailsViewModel viewModel;

    String placeId;

    public static Intent newIntent(@NonNull Context context,
                                   @NonNull String placeId){
        checkNotNull(context);
        checkNotNull(placeId);

        Intent intent = new Intent(context,PlaceDetailsActivity.class);
        intent.putExtra(EXTRA_PLACE_ID, placeId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadExtras();
        inject();
        setup();

    }

    @Override
    protected void onDestroy() {

        if (viewModel != null){
            viewModel.onDestroy();
        }
        super.onDestroy();
    }

    private void loadExtras(){

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            placeId = extras.getString(EXTRA_PLACE_ID);
        }

        if (TextUtils.isEmpty(placeId)){
            finish();
        }
    }

    private void inject(){
        DaggerPlaceDetailsComponent.builder()
                .appComponent(App.get(this).getComponent())
                .build()
                .inject(this);
    }

    private void setup(){
        final ActivityPlaceDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_place_details);
        binding.setViewModel(viewModel);

        setToolbar((Toolbar)findViewById(R.id.toolbar));
        binding.collapsingToolbar.setTitle(" ");

        PlaceDetailsFragment fragment = findOrCreateFragment();

        viewModel.setPlaceId(placeId);
        viewModel.setNavigator(this);

        fragment.setViewModel(viewModel);
    }

    private PlaceDetailsFragment findOrCreateFragment(){

        PlaceDetailsFragment fragment = (PlaceDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.framelayout_container);

        if (fragment == null){
            fragment = new PlaceDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.framelayout_container,fragment,NearbyPlacesFragment.class.getSimpleName())
                    .commit();
        }
        return fragment;
    }

    @Override
    public void openMaps(double latitude, double longitude) {
        IntentUtils.startNavigation(this,latitude,longitude);
    }

    @Override
    public void openBrowser(String url) {
        IntentUtils.openBrowser(this,url);
    }
}
