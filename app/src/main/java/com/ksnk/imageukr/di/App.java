package com.ksnk.imageukr.di;

import android.app.Application;

import com.ksnk.imageukr.di.modules.RetrofitModule;
import com.ksnk.imageukr.di.modules.SharedPrefsModule;
import com.onesignal.OneSignal;

public class App extends Application {
    private AppComponent appComponent;
    private static final String ONESIGNAL_APP_ID = "895e888e-2a95-4697-9089-40eafea03196";
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        appComponent = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule())
                .sharedPrefsModule(new SharedPrefsModule(this))
                .build();
    }

    public AppComponent getAppComponents() {
        return appComponent;
    }
}
