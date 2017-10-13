package com.wks.nearby.dependencies.modules;

import android.content.Context;

import com.wks.nearby.dependencies.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @AppScope
    public Context context(){
        return context;
    }
}
