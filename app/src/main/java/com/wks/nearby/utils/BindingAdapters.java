package com.wks.nearby.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by waqqassheikh on 15/10/2017.
 */

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) return;
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
