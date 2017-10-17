package com.wks.nearby.places.listing.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wks.nearby.R;
import com.wks.nearby.databinding.PlaceItemBinding;
import com.wks.nearby.places.listing.NearbyPlacesViewModel;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlaceItemViewHolder>{

    private NearbyPlacesViewModel viewModel;

    public PlacesAdapter(@NonNull Context context,
                         @NonNull NearbyPlacesViewModel viewModel){

        checkNotNull(context);
        checkNotNull(viewModel);

        this.viewModel = viewModel;

    }

    @Override
    public PlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PlaceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.place_item, parent,false);
        return new PlaceItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PlaceItemViewHolder holder, int position) {

        final PlaceItemViewModel itemViewModel = viewModel.viewModelForItem(position);
        holder.binding.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return viewModel.getItemCount();
    }
}
