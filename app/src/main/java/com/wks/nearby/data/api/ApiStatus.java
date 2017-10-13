package com.wks.nearby.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public enum ApiStatus {
    @SerializedName("OK")
    OK,
    @SerializedName("ZERO_RESULTS")
    ZERO_RESULTS,
    @SerializedName("OVER_QUERY_LIMIT")
    OVER_QUERY_LIMIT,
    @SerializedName("REQUEST_DENIED")
    REQUEST_DENIED,
    @SerializedName("INVALID_REQUEST")
    INVALID_REQUEST;
}
