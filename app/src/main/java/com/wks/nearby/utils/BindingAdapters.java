package com.wks.nearby.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wks.nearby.app.App;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) return;
        final Picasso picasso = ((App)imageView.getContext().getApplicationContext()).getComponent().getPicasso();
        if (picasso != null) {
            picasso.with(imageView.getContext()).load(url).into(imageView);
        }
    }
}
