package com.wks.nearby.data.places.listing;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public interface LocationRetrievedCallback {
    void onLocationRetrieved(double latitude,double longitude);
    void onLocationUnavailable();
}
