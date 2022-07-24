package com.ksnk.imageukr.di.modules;

import com.ksnk.imageukr.api.ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    private final String baseUrl = "http://ksnk-dm.hqsite.online/";

    @Singleton
    @Provides
    public ApiInterface getRetroFirServiceInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Singleton
    @Provides
    public Retrofit getRetroFitInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
