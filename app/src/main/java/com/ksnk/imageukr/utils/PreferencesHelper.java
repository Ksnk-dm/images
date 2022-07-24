package com.ksnk.imageukr.utils;

import android.content.SharedPreferences;

import com.ksnk.imageukr.utils.Contains;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {
    private final SharedPreferences preferences;

    @Inject
    public PreferencesHelper(SharedPreferences sharedPreferences) {
        preferences = sharedPreferences;
    }

    public void setDisplayType(int type) {
        preferences.edit().putInt(Contains.PREFERENCE_VARIABLE_SETTINGS, type).apply();
    }

    public int getDisplayType() {
        return preferences.getInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 2);
    }

    public void setTheme(int theme) {
        preferences.edit().putInt(Contains.PREFERENCE_THEME, theme).apply();
    }

    public int getTheme() {
        return preferences.getInt(Contains.PREFERENCE_THEME, 0);
    }
}
