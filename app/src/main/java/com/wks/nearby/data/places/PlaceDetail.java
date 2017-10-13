package com.wks.nearby.data.places;

import com.google.gson.annotations.SerializedName;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class PlaceDetail extends Place{

    @SerializedName("formatted_address")
    public final String address;

    @SerializedName("formatted_phone_number")
    public final String phoneNumber;

    @SerializedName("website")
    public final String website;

    @SerializedName("rating")
    public final float rating;

    private PlaceDetail(){
        super();
        this.address = "";
        this.phoneNumber = "";
        this.website = "";
        this.rating = 0.0f;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public float getRating() {
        return rating;
    }
}
