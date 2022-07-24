package com.ksnk.imageukr.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ksnk.imageukr.utils.Contains;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {
    private Application application;

    public SharedPrefsModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference() {
        return application.getSharedPreferences(Contains.PREFERENCE_INIT, Context.MODE_PRIVATE);
    }
}
