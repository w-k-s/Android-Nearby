package com.wks.nearby.data.places;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

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
    private final List<Photo> photos;

    protected Place(){
        id = "";
        placeId = "";
        name = "";
        geometry = null;
        icon = "";
        vicinity = "";
        photos = Collections.emptyList();
    }

    public Place(@NonNull final String id,
                 @NonNull final String placeId,
                 @NonNull final String name,
                 @NonNull final Geometry geometry,
                 @Nullable final String icon,
                 @Nullable final String vicinity,
                 @Nullable final List<Photo> photos){
        checkNotNull(id);
        checkNotNull(placeId);
        checkNotNull(name);
        checkNotNull(geometry);

        this.id = id;
        this.placeId = placeId;
        this.name = name;
        this.geometry = geometry;
        this.icon = icon;
        this.vicinity = vicinity;
        this.photos = photos == null
                ? Collections.<Photo>emptyList()
                : Collections.unmodifiableList(photos);
    }

    public Place(@NonNull final String id,
                 @NonNull final String placeId,
                 @NonNull final String name,
                 @NonNull final Geometry geometry){
        this(id,placeId,name,geometry,null,null,null);
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
    public List<Photo> getPhotos() {
        return photos;
    }

    @Nullable
    public Photo getFirstPhoto(){
        if (getPhotos().isEmpty()) return null;
        return getPhotos().get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (id != null ? !id.equals(place.id) : place.id != null) return false;
        return placeId != null ? placeId.equals(place.placeId) : place.placeId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (placeId != null ? placeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", placeId='" + placeId + '\'' +
                ", name='" + name + '\'' +
                ", vicinity='" + vicinity + '\'' +
                '}';
    }
}
