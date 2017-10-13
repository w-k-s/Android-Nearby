package com.wks.nearby.data.places;

import com.google.gson.annotations.SerializedName;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class Photo {

    @SerializedName("photo_reference")
    private final String reference;

    @SerializedName("width")
    private final int width;

    @SerializedName("height")
    private final int height;

    private Photo(){
        reference = "";
        width = 0;
        height = 0;
    }

    public String getReference() {
        return reference;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
