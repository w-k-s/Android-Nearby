package com.wks.nearby.places.listing.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wks.nearby.R;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.PlaceItemBinding;
import com.wks.nearby.places.listing.NearbyPlacesViewModel;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlaceItemViewHolder>{

    private NearbyPlacesViewModel viewModel;
    private PlacesRepository placesRepository;
    private Picasso picasso;

    private int placePhotoWidth;
    private int placePhotoHeight;

    public PlacesAdapter(@NonNull Context context,
                         @NonNull NearbyPlacesViewModel viewModel,
                         @NonNull PlacesRepository placesRepository,
                         @NonNull Picasso picasso){

        checkNotNull(context);
        checkNotNull(viewModel);
        checkNotNull(placesRepository);
        checkNotNull(picasso);

        this.viewModel = viewModel;
        this.placesRepository = placesRepository;
        this.picasso = picasso;

        this.placePhotoWidth = context.getResources().getDimensionPixelSize(R.dimen.place_item_image_width);
        this.placePhotoHeight = context.getResources().getDimensionPixelSize(R.dimen.place_item_image_height);
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

        if (itemViewModel.icon != null){
            picasso.load(itemViewModel.icon).into(holder.binding.imageviewIcon);
        }

        if (itemViewModel.photoReference != null){
            final String photoUrl =
                    placesRepository.imageUrl(itemViewModel.photoReference,placePhotoWidth,placePhotoHeight);
            picasso.load(photoUrl).into(holder.binding.imageviewPlace);
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getItemCount();
    }
}
