package com.wks.nearby.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class IntentUtils {

    public static boolean openBrowser(Context context, String url) {
        if (context == null) return false;
        if (TextUtils.isEmpty(url)) return false;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        if (i.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(i);
            return true;
        }

        return false;
    }

    public static boolean startNavigation(Context context, double lat, double lng) {
        if (context == null) return false;

        Uri googleMapsUri = Uri.parse("google.navigation:q=" + lat + "," + lng);

        Intent intent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        }

        intent = new Intent(
                android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng)
        );

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        }

        return false;
    }
}
