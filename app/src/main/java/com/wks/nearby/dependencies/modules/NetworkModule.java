package com.wks.nearby.dependencies.modules;

import android.content.Context;

import com.wks.nearby.dependencies.AppScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

@Module
public class NetworkModule {

    private static final String CACHE_FILE_NAME = "NetworkModuleCache";
    private static final long CACHE_SIZE = 5 * 1024 * 1024; //5MB;

    @Provides
    @AppScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @AppScope
    public HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @AppScope
    public Cache cache(File cacheFile){
        return new Cache(cacheFile,CACHE_SIZE);
    }

    @Provides
    @AppScope
    public File cacheFile(Context context){
        final File cacheFile = new File(context.getCacheDir(),CACHE_FILE_NAME);
        cacheFile.mkdirs();

        return cacheFile;
    }
}
