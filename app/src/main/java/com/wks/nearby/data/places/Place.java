package com.wks.nearby.data.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class Place {

    @SerializedName("id")
    private final String id;

    @SerializedName("place_id")
    private final String placeId;

    @SerializedName("name")
    private final String name;

    @SerializedName("geometry")
    private final Geometry geometry;

    @SerializedName("icon")
    private final String icon;

    @SerializedName("vicinity")
    private final String vicinity;

    @SerializedName("photos")
    private final Collection<Photo> photos;

    protected Place(){
        id = "";
        placeId = "";
        name = "";
        geometry = null;
        icon = "";
        vicinity = "";
        photos = Collections.emptyList();
    }

    public String getId() {
        return id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    @NonNull
    public Collection<Photo> getPhotos() {
        return photos;
    }
}
