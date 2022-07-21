package com.ksnk.imageukr.di;

import com.ksnk.imageukr.utils.PreferencesHelper;
import com.ksnk.imageukr.di.modules.RetrofitModule;
import com.ksnk.imageukr.di.modules.SharedPrefsModule;
import com.ksnk.imageukr.ui.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                RetrofitModule.class, SharedPrefsModule.class}
)
public interface AppComponent {
    void inject(MainViewModel mainViewModel);

    PreferencesHelper preferencesHelper();
}
