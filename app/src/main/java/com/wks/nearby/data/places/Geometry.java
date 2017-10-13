package com.wks.nearby.data.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class Geometry {

    @SerializedName("location")
    private final Location location;

    private Geometry(){
        this.location = null;
    }

    public Geometry(@NonNull Location location){
        checkNotNull(location);

        this.location = location;
    }

    public Geometry(double latitude, double longitude){
        this.location = new Location(latitude,longitude);
    }

    public Location getLocation() {
        return location;
    }

    public double getLatitude(){
        return location == null? 0D : location.getLatitude();
    }

    public double getLongitude(){
        return location == null? 0D : location.getLongitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return location != null ? location.equals(geometry.location) : geometry.location == null;

    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
