package com.wks.nearby.data.places;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by waqqassheikh on 13/10/2017.
 */
//Reminder: Reviews could be added later as an additional feature
public class PlaceDetail extends Place{

    @SerializedName("formatted_address")
    public final String address;

    @SerializedName("formatted_phone_number")
    public final String phoneNumber;

    @SerializedName("international_phone_number")
    public final String internationalPhoneNumber;

    @SerializedName("website")
    public final String website;

    @SerializedName("rating")
    public final float rating;

    private PlaceDetail(){
        super();
        this.address = "";
        this.phoneNumber = "";
        this.internationalPhoneNumber = "";
        this.website = "";
        this.rating = 0.0f;
    }

    public PlaceDetail(@NonNull final Place place,
                       @Nullable final String address,
                       @Nullable final String phoneNumber,
                       @Nullable final String internationalPhoneNumber,
                       @Nullable final String website,
                       final float rating){
        super(place.getId(),
                place.getPlaceId(),
                place.getName(),
                place.getGeometry(),
                place.getIcon(),
                place.getVicinity(),
                place.getPhotos());

        this.address = address;
        this.phoneNumber = phoneNumber;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.website = website;
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public float getRating() {
        return rating;
    }

    public int getMaxRating(){
        return 5;
    }
}
