package com.ksnk.imageukr.ui.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.listeners.UpdateUi;
import com.ksnk.imageukr.ui.main.MainViewModel;

public class MainPopupMenu {

    private PopupWindow popupWindow;
    private Context context;
    private RadioGroup visibilityRg;
    private RadioGroup themeRg;
    private UpdateUi updateUi;
    private int setting = 0;
    private MainViewModel mainViewModel;

    public void showPopupWindow(final View view, UpdateUi updateUi, MainViewModel mainViewModel) {
        this.context = view.getContext();
        this.updateUi = updateUi;
        this.mainViewModel = mainViewModel;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.animation_popup);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.RIGHT, 50, -900);
        initVariable(popupView);
        checkSettingStatus();
        checkTheme();
    }

    private void checkTheme() {
        int theme = mainViewModel.getTheme();
        switch (theme) {
            case 0:
                themeRg.check(R.id.radioButtonSystem);
                break;
            case 1:
                themeRg.check(R.id.radioButtonDark);
                break;
            case 2:
                themeRg.check(R.id.radioButtonLight);
                break;
        }
    }

    private void checkSettingStatus() {
        setting = mainViewModel.getType();
        switch (setting) {
            case 1:
                visibilityRg.check(R.id.radioButton1);
                break;
            case 2:
                visibilityRg.check(R.id.radioButton2);
                break;
            case 3:
                visibilityRg.check(R.id.radioButton3);
                break;
        }
    }

    private void initVariable(View view) {
        visibilityRg = view.findViewById(R.id.visibilityRadioGroup);
        visibilityRg.setOnCheckedChangeListener(rgVisibilityCheckedChangeListener);
        themeRg = view.findViewById(R.id.themeRg);
        themeRg.setOnCheckedChangeListener(rgThemeCheckedChangeListener);
    }

    private final RadioGroup.OnCheckedChangeListener rgThemeCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.radioButtonSystem:
                    mainViewModel.setTheme(0);
                    updateUi.updateTheme(0);
                    break;
                case R.id.radioButtonDark:
                    mainViewModel.setTheme(1);
                    updateUi.updateTheme(1);
                    break;
                case R.id.radioButtonLight:
                    mainViewModel.setTheme(2);
                    updateUi.updateTheme(2);
                    break;
            }
        }
    };

    private final RadioGroup.OnCheckedChangeListener rgVisibilityCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.radioButton1:
                    mainViewModel.setType(1);
                    updateUi.updateRecycler();
                    break;
                case R.id.radioButton2:
                    mainViewModel.setType(2);
                    updateUi.updateRecycler();
                    break;
                case R.id.radioButton3:
                    mainViewModel.setType(3);
                    updateUi.updateRecycler();
                    break;
            }
        }
    };
}
