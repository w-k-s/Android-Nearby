package com.wks.nearby.data.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.wks.nearby.utils.Preconditions.checkNotNull;

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

    public Photo(@NonNull String reference, int width, int height){
        checkNotNull(reference);

        this.reference = reference;
        this.width = width;
        this.height = height;
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
