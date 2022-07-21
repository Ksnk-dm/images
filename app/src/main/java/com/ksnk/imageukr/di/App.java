package com.ksnk.imageukr.di;

import android.app.Application;

import com.ksnk.imageukr.di.modules.RetrofitModule;
import com.ksnk.imageukr.di.modules.SharedPrefsModule;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule())
                .sharedPrefsModule(new SharedPrefsModule(this))
                .build();
    }

    public AppComponent getAppComponents() {
        return appComponent;
    }
}
