package com.wks.nearby.app;

import android.app.Activity;
import android.app.Application;

import com.wks.nearby.dependencies.AppComponent;
import com.wks.nearby.dependencies.DaggerAppComponent;
import com.wks.nearby.dependencies.modules.AppModule;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

public class App extends Application{

    public static App get(Activity activity){
        return (App) activity.getApplication();
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
