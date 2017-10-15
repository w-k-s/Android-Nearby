package com.wks.nearby.places.details;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public interface PlaceDetailsNavigator {
    void openMaps(double latitude, double longitude);
    void openBrowser(String url);
}
