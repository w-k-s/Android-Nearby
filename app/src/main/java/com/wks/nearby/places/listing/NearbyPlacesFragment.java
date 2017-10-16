package com.wks.nearby.places.listing;

import android.content.Intent;
import android.content.IntentSender;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wks.nearby.R;
import com.wks.nearby.app.App;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.FragmentNearbyPlacesBinding;
import com.wks.nearby.places.listing.adapter.PlacesAdapter;
import com.wks.nearby.places.listing.dependencies.DaggerNearbyPlacesComponent;
import com.wks.nearby.utils.EndlessRecyclerViewScrollListener;

import javax.inject.Inject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


public class NearbyPlacesFragment extends Fragment implements LocationController{

    private static final int REQUEST_CODE_COARSE_LOCATION_PERMISSION = 100;
    private static final int REQUEST_CODE_LOCATION_SETTINGS_CHECK = 101;

    NearbyPlacesViewModel viewModel;

    FragmentNearbyPlacesBinding binding;

    PlacesAdapter placesAdapter;

    FusedLocationProviderClient fusedLocationProviderClient;

    LocationRetrievedCallback locationCallback;

    @Inject PlacesRepository placesRepository;

    EndlessRecyclerViewScrollListener scrollListener;

    ObservableList.OnListChangedCallback<ObservableList<Place>> placesListChangeObserver;
    Observable.OnPropertyChangedCallback loadingPlacesObserver;

    public NearbyPlacesFragment() {

    }

    public void setViewModel(NearbyPlacesViewModel viewModel) {
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

        binding = FragmentNearbyPlacesBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        setupBindings();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupRefreshLayout();

        setupPlacesAdapter();

        viewModel.refresh();
    }

    @Override
    public void onDestroy() {

        if (placesListChangeObserver != null) {
            viewModel.items.removeOnListChangedCallback(placesListChangeObserver);
        }

        if (loadingPlacesObserver != null){
            viewModel.loadingPlaces.removeOnPropertyChangedCallback(loadingPlacesObserver);
        }

        super.onDestroy();
    }

    private void setupRefreshLayout() {

        SwipeRefreshLayout refreshLayout = binding.swiperefreshlayout;

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                scrollListener.resetState();
            }
        });

    }

    private void setupPlacesAdapter() {
        RecyclerView recyclerView = binding.recyclerviewPlaces;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.loadMore();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        placesAdapter = new PlacesAdapter(
                getContext(),
                viewModel,
                placesRepository);

        recyclerView.setAdapter(placesAdapter);
    }

    private void setupBindings() {

        placesListChangeObserver = new ObservableList.OnListChangedCallback<ObservableList<Place>>() {
            @Override
            public void onChanged(ObservableList<Place> sender) {
                Log.e(getClass().getSimpleName(),"onChanged");
            }

            @Override
            public void onItemRangeChanged(ObservableList<Place> sender, int positionStart, int itemCount) {
            }

            @Override
            public void onItemRangeInserted(ObservableList<Place> sender, int positionStart, int itemCount) {
                placesAdapter.notifyDataSetChanged();
                scrollListener.finishedLoading();
            }

            @Override
            public void onItemRangeMoved(ObservableList<Place> sender, int fromPosition, int toPosition, int itemCount) {
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Place> sender, int positionStart, int itemCount) {
                placesAdapter.notifyDataSetChanged();
            }
        };
        viewModel.items.addOnListChangedCallback(placesListChangeObserver);

        loadingPlacesObserver = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.swiperefreshlayout.setRefreshing(viewModel.loadingPlaces.get());
            }
        };

        viewModel.loadingPlaces.addOnPropertyChangedCallback(loadingPlacesObserver);
    }


    //-- Location ---

    @Override
    public void determineLocation(LocationRetrievedCallback locationRetrievedCallback) {

        locationCallback = locationRetrievedCallback;

        if (checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION}, REQUEST_CODE_COARSE_LOCATION_PERMISSION);
        } else {
            sendLocationRequest();
        }
    }

    @SuppressWarnings({"MissingPermission"}) //permission has been asked earlier.
    private void sendLocationRequest() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        final LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());

        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                        new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                Location location = locationResult.getLastLocation();
                                if (location != null) {
                                    fusedLocationProviderClient.removeLocationUpdates(this);
                                    if (locationCallback != null){
                                        locationCallback.onLocationRetrieved(
                                                location.getLatitude(),
                                                location.getLongitude());
                                    }
                                }
                            }
                        },
                        null);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(getActivity(),
                                    REQUEST_CODE_LOCATION_SETTINGS_CHECK);
                        } catch (IntentSender.SendIntentException sendEx) {
                            if(locationCallback != null){
                                locationCallback.onLocationUnavailable();
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        if(locationCallback != null){
                            locationCallback.onLocationUnavailable();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            if(locationCallback != null){
                locationCallback.onLocationUnavailable();
            }
            return;
        }
        sendLocationRequest();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOCATION_SETTINGS_CHECK && resultCode == RESULT_OK){
            sendLocationRequest();
        }
    }
}
