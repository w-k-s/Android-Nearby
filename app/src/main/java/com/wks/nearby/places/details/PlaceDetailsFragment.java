package com.wks.nearby.places.details;


import android.databinding.Observable;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wks.nearby.app.App;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.FragmentPlaceDetailsBinding;
import com.wks.nearby.places.details.dependencies.DaggerPlaceDetailsComponent;

import javax.inject.Inject;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class PlaceDetailsFragment extends Fragment {

    @Inject
    PlacesRepository placesRepository;

    PlaceDetailsViewModel viewModel;

    FragmentPlaceDetailsBinding binding;

    Observable.OnPropertyChangedCallback placeDetailsErrorObserver;

    public PlaceDetailsFragment() {

    }

    //-- Getters & Setters

    public void setViewModel(PlaceDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    //-- Lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DaggerPlaceDetailsComponent.builder()
                .appComponent(App.get(getActivity()).getComponent())
                .build()
                .inject(this);

        this.binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false);
        this.binding.setViewModel(viewModel);
        this.binding.setHandler(this);

        this.binding.textviewAddress.setPaintFlags(this.binding.textviewAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        this.binding.textviewWebsite.setPaintFlags(this.binding.textviewWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setupBindings();

        return this.binding.getRoot();
    }

    private void setupBindings(){
        placeDetailsErrorObserver = new Observable.OnPropertyChangedCallback(){
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Snackbar.make(binding.getRoot(),viewModel.placeDetailsError.get(),Snackbar.LENGTH_INDEFINITE).show();
            }
        };
        viewModel.placeDetailsError.addOnPropertyChangedCallback(placeDetailsErrorObserver);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.start();
    }

    @Override
    public void onDestroy() {
        if (placeDetailsErrorObserver != null){
            viewModel.placeDetailsError.removeOnPropertyChangedCallback(placeDetailsErrorObserver);
        }
        super.onDestroy();
    }

    public void onAddressClicked(View view){
        viewModel.onAddressClicked();
    }

    public void onWebsiteClicked(View view){
        viewModel.onWebsiteClicked();
    }
}
