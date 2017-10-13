package com.wks.nearby.data.api;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class ApiResponse<T> {

    @SerializedName("status")
    private final ApiStatus status;

    @SerializedName(value = "result",alternate = "results")
    private final T result;

    @SerializedName("next_page_token")
    private final String nextPageToken;

    @SerializedName("error_message")
    private final String errorMessage;

    private ApiResponse(){
        this.status = null;
        this.result = null;
        this.nextPageToken = null;
        this.errorMessage = null;
    }

    public ApiStatus getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }

    @Nullable
    public String getNextPageToken() {
        return nextPageToken;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOK(){
        return ApiStatus.OK.equals(status);
    }
}
