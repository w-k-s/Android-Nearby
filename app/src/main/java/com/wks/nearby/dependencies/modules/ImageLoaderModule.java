package com.wks.nearby.dependencies.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.wks.nearby.dependencies.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


/**
 * Created by waqqassheikh on 13/10/2017.
 */
@Module(includes = {AppModule.class, NetworkModule.class})
public class ImageLoaderModule {

    @Provides
    @AppScope
    public Picasso picasso(Context context, OkHttp3Downloader downloader){
        return new Picasso.Builder(context)
                .downloader(downloader)
                .build();
    }

    @Provides
    @AppScope
    public OkHttp3Downloader downloader(OkHttpClient client){
        return new OkHttp3Downloader(client);
    }
}
