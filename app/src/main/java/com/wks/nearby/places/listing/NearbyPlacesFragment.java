package com.wks.nearby.places.listing;

import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wks.nearby.R;
import com.wks.nearby.app.App;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.FragmentNearbyPlacesBinding;

import javax.inject.Inject;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class NearbyPlacesFragment extends Fragment {

    NearbyPlacesViewModel viewModel;

    FragmentNearbyPlacesBinding binding;

    PlacesAdapter placesAdapter;

    @Inject PlacesRepository placesRepository;

    @Inject Picasso picasso;

    public NearbyPlacesFragment(){

    }

    public void setViewModel(NearbyPlacesViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DaggerNearbyPlacesComponent.builder()
                .appComponent(App.get(getActivity()).getComponent())
                .build()
                .inject(this);

        binding = FragmentNearbyPlacesBinding.inflate(inflater,container,false);

        setupBindings();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupPlacesAdapter();
    }

    private void setupPlacesAdapter(){
        RecyclerView recyclerView = binding.recyclerviewPlaces;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);

        placesAdapter = new PlacesAdapter(
                getContext(),
                placesRepository,
                picasso,
                null);

        recyclerView.setAdapter(placesAdapter);
    }

    private void setupBindings(){
        viewModel.items.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Place>>() {
            @Override
            public void onChanged(ObservableList<Place> sender) {
                placesAdapter.setItems(viewModel.items);
            }

            @Override
            public void onItemRangeChanged(ObservableList<Place> sender, int positionStart, int itemCount) {
                placesAdapter.setItems(viewModel.items);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Place> sender, int positionStart, int itemCount) {
                placesAdapter.setItems(viewModel.items);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Place> sender, int fromPosition, int toPosition, int itemCount) {
                placesAdapter.setItems(viewModel.items);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Place> sender, int positionStart, int itemCount) {
                placesAdapter.setItems(viewModel.items);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.loadNearbyPlaces(25.2048,55.2708);
    }
}
