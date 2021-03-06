package com.wks.nearby.places.details;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wks.nearby.BR;
import com.wks.nearby.R;
import com.wks.nearby.app.Constants;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.PlaceDetail;
import com.wks.nearby.data.places.source.PlacesDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class PlaceDetailsViewModel extends BaseObservable{

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableFloat rating = new ObservableFloat();
    public final ObservableInt numStars = new ObservableInt();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> photo = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> internationalPhoneNumber = new ObservableField<>();
    public final ObservableField<String> website = new ObservableField<>();

    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableField<String> placeDetailsError = new ObservableField<>();


    private Context context;
    private PlaceDetail placeDetail;
    private PlacesRepository placesRepository;
    private PlaceDetailsNavigator navigator;
    private String placeId;

    public PlaceDetailsViewModel(Context context,
                                 PlacesRepository placesRepository){
        this.context = context;
        this.placesRepository = placesRepository;
    }

    //-- Getters & Setters

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setNavigator(PlaceDetailsNavigator navigator) {
        this.navigator = navigator;
    }

    //-- Lifecycle

    public void start(){
        loadPlaceDetails();
    }

    public void onDestroy(){
        this.navigator = null;
    }

    //-- Load Place Details

    private void loadPlaceDetails(){
        checkNotNull(placeId);

        this.loading.set(true);

        this.placesRepository.loadPlaceDetails(placeId, new PlacesDataSource.LoadPlaceDetailsCallback() {
            @Override
            public void onPlaceDetailsLoaded(@NonNull PlaceDetail placeDetail) {
                loading.set(false);
                PlaceDetailsViewModel.this.placeDetail = placeDetail;
                bind();
            }

            @Override
            public void onDataNotAvailable(@Nullable String message) {
                loading.set(false);
                placeDetailsError.set(message);
            }
        });
    }

    private void bind(){
        name.set(placeDetail.getName());
        rating.set(placeDetail.getRating());
        numStars.set(placeDetail.getMaxRating());
        icon.set(placeDetail.getIcon());
        address.set(placeDetail.getAddress());
        phoneNumber.set(placeDetail.getPhoneNumber());
        internationalPhoneNumber.set(placeDetail.getInternationalPhoneNumber());
        website.set(placeDetail.getWebsite());

        final int height = context
                .getResources()
                .getDimensionPixelSize(R.dimen.place_details_banner_image_height);

        final Photo firstPhoto = placeDetail.getFirstPhoto();
        if (firstPhoto != null){
            photo.set(placesRepository.imageUrl(
                    firstPhoto.getReference(),
                    Constants.GOOGLE_PLACES_MAX_PHOTO_SIZE,
                    height
            ));
        }

        notifyPropertyChanged(BR._all);
    }

    public void onWebsiteClicked(){
        this.navigator.openBrowser(website.get());
    }

    public void onAddressClicked(){
        this.navigator.openMaps(
                placeDetail.getGeometry().getLatitude(),
                placeDetail.getGeometry().getLongitude()
        );
    }
}
