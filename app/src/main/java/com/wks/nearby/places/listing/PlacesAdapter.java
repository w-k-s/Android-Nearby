package com.wks.nearby.places.listing;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wks.nearby.R;
import com.wks.nearby.data.places.Photo;
import com.wks.nearby.data.places.Place;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.databinding.PlaceItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlaceItemViewHolder>{

    private Context context;
    private List<Place> items;
    private PlacesRepository placesRepository;
    private Picasso picasso;

    private int placePhotoWidth;
    private int placePhotoHeight;

    public PlacesAdapter(@NonNull Context context,
                         @NonNull PlacesRepository placesRepository,
                         @NonNull Picasso picasso,
                         @Nullable List<Place> places){
        this.items = places == null? new ArrayList<Place>() : places;
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

        final Place place = getItems().get(position);
        holder.binding.setViewModel(new PlaceItemViewModel(place));

        if (place.getIcon() != null){
            picasso.load(place.getIcon()).into(holder.binding.imageviewIcon);
        }

        if (!place.getPhotos().isEmpty()){
            final Photo photo = place.getPhotos().get(0);
            final String photoUrl =
                    placesRepository.imageUrl(photo.getReference(),placePhotoWidth,placePhotoHeight);

            picasso.load(photoUrl).into(holder.binding.imageviewPlace);
        }
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    @NonNull
    public List<Place> getItems() {
        return items;
    }

    public void setItems(List<Place> places){
        this.items.addAll(places);
        notifyDataSetChanged();
    }
}
