package com.wks.nearby.places.listing.adapter;

import android.support.v7.widget.RecyclerView;

import com.wks.nearby.databinding.PlaceItemBinding;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class PlaceItemViewHolder extends RecyclerView.ViewHolder{

    public final  PlaceItemBinding binding;

    public PlaceItemViewHolder(PlaceItemBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
