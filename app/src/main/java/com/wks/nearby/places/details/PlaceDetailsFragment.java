package com.wks.nearby.places.details;


import android.databinding.Observable;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wks.nearby.app.App;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.FragmentPlaceDetailsBinding;
import com.wks.nearby.places.details.dependencies.DaggerPlaceDetailsComponent;

import javax.inject.Inject;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class PlaceDetailsFragment extends Fragment {

    @Inject
    PlacesRepository placesRepository;

    PlaceDetailsViewModel viewModel;

    FragmentPlaceDetailsBinding binding;

    DynamicBox dynamicBox;

    Observable.OnPropertyChangedCallback placeDetailsErrorObserver;
    Observable.OnPropertyChangedCallback loadingObserver;

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

        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setHandler(this);

        binding.textviewAddress.setPaintFlags(binding.textviewAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.textviewWebsite.setPaintFlags(binding.textviewWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setupBindings();

        return this.binding.getRoot();
    }

    private void setupBindings(){
        loadingObserver = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(viewModel.loading.get()){
                    dynamicBox.showLoadingLayout();
                }else{
                    dynamicBox.hideAll();
                }
            }
        };
        viewModel.loading.addOnPropertyChangedCallback(loadingObserver);

        placeDetailsErrorObserver = new Observable.OnPropertyChangedCallback(){
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                dynamicBox.setOtherExceptionMessage(viewModel.placeDetailsError.get());
                dynamicBox.setClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        viewModel.start();
                    }
                });
                dynamicBox.showExceptionLayout();
            }
        };
        viewModel.placeDetailsError.addOnPropertyChangedCallback(placeDetailsErrorObserver);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dynamicBox = new DynamicBox(getActivity(),binding.getRoot());

        viewModel.start();
    }

    @Override
    public void onDestroy() {
        if (placeDetailsErrorObserver != null){
            viewModel.placeDetailsError.removeOnPropertyChangedCallback(placeDetailsErrorObserver);
        }
        if (loadingObserver != null){
            viewModel.loading.removeOnPropertyChangedCallback(loadingObserver);
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
