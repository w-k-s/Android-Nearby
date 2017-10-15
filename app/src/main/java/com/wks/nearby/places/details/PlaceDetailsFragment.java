package com.wks.nearby.places.details;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
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

    @Inject
    Picasso picasso;

    private PlaceDetailsViewModel viewModel;

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

        FragmentPlaceDetailsBinding binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setHandler(this);

        binding.textviewAddress.setPaintFlags(binding.textviewAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.textviewWebsite.setPaintFlags(binding.textviewWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.start();
    }

    public void onAddressClicked(View view){
        viewModel.onAddressClicked();
    }

    public void onWebsiteClicked(View view){
        viewModel.onWebsiteClicked();
    }
}
