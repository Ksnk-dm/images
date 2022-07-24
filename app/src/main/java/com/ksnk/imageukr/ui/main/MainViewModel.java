package com.ksnk.imageukr.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ksnk.imageukr.api.ApiInterface;
import com.ksnk.imageukr.data.Image;
import com.ksnk.imageukr.utils.PreferencesHelper;
import com.ksnk.imageukr.di.App;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    @Inject
    ApiInterface apiInterface;
    @Inject
    PreferencesHelper preferencesHelper;
    private MutableLiveData<List<Image>> imageLiveData;
    private App application;

    public MainViewModel(Application application) {
        super(application);
        this.application = (App) application;
        this.application.getAppComponents().inject(MainViewModel.this);
        imageLiveData = new MutableLiveData<List<Image>>();
    }

    public MutableLiveData<List<Image>> getImageLiveData() {
        return imageLiveData;
    }

    public void retrofitGet() {
        Call<List<Image>> call = apiInterface.getDataFromAPI();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    imageLiveData.postValue(response.body());
                } else {
                    imageLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                imageLiveData.postValue(null);
            }
        });
    }

    public void setType(int type) {
        preferencesHelper.setDisplayType(type);
    }

    public int getType() {
        return preferencesHelper.getDisplayType();
    }

    public void setTheme(int theme) {
        preferencesHelper.setTheme(theme);
    }

    public int getTheme() {
        return preferencesHelper.getTheme();
    }
}
