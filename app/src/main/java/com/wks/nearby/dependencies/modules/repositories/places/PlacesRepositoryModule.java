package com.wks.nearby.dependencies.modules.repositories.places;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wks.nearby.data.places.source.PlacesRemoteDataSource;
import com.wks.nearby.data.places.source.PlacesRepository;
import com.wks.nearby.data.places.source.PlacesService;
import com.wks.nearby.dependencies.AppScope;
import com.wks.nearby.dependencies.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by waqqassheikh on 13/10/2017.
 */

@Module(includes = NetworkModule.class)
public class PlacesRepositoryModule {

    @Provides
    @Singleton
    public PlacesRepository placesRepository(PlacesRemoteDataSource remoteDataSource){
        return new PlacesRepository(remoteDataSource);
    }

    @Provides
    @AppScope
    public PlacesRemoteDataSource placesRemoteDataSource(PlacesService placesService){
        return new PlacesRemoteDataSource(placesService);
    }

    @Provides
    @AppScope
    public PlacesService placesService(@PlacesRetrofitQualifier Retrofit retrofit){
        return retrofit.create(PlacesService.class);
    }

    @Provides
    @AppScope
    @PlacesRetrofitQualifier
    public Retrofit retrofit(@PlacesGsonQualifier Gson gson, OkHttpClient client){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .build();
    }

    @Provides
    @AppScope
    @PlacesGsonQualifier
    public Gson gson(){
        return new GsonBuilder().create();
    }
}
