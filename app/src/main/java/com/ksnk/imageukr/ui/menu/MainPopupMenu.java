package com.ksnk.imageukr.ui.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.listeners.UpdateRecyclerListener;
import com.ksnk.imageukr.utils.Contains;

public class MainPopupMenu {
    private PopupWindow popupWindow;
    private Context context;
    private RadioButton radioButton3, radioButton2, radioButton1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UpdateRecyclerListener updateRecyclerListener;
    private int setting = 0;

    public void showPopupWindow(final View view, UpdateRecyclerListener updateRecyclerListener) {
        this.context = view.getContext();
        this.updateRecyclerListener = updateRecyclerListener;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.animation_popup);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.RIGHT, 50, -900);
        initSharedPrefs();
        initVariable(popupView);
        checkSettingStatus();
    }

    private void checkSettingStatus() {
        setting = sharedPreferences.getInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 0);
        switch (setting) {
            case 1:
                radioButton1.setChecked(true);
                break;
            case 2:
                radioButton2.setChecked(true);
                break;
            case 3:
                radioButton3.setChecked(true);
                break;
        }
    }

    private void initSharedPrefs() {
        sharedPreferences = context.getSharedPreferences(Contains.PREFERENCE_INIT, 0);
        editor = sharedPreferences.edit();
    }

    private final View.OnClickListener radioButton3OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            radioButton3.setChecked(true);
            updateRecyclerListener.updateRecycler();
        }
    };

    private final CompoundButton.OnCheckedChangeListener radioButton3OnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                editor.putInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 3);
                editor.commit();
                radioButton2.setChecked(false);
                radioButton1.setChecked(false);
            }
        }
    };

    private final View.OnClickListener radioButton2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            radioButton2.setChecked(true);
            updateRecyclerListener.updateRecycler();
        }
    };

    private final CompoundButton.OnCheckedChangeListener radioButton2OnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                editor.putInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 2);
                editor.commit();
                radioButton3.setChecked(false);
                radioButton1.setChecked(false);
            }
        }
    };

    private final View.OnClickListener radioButton1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            radioButton1.setChecked(true);
            updateRecyclerListener.updateRecycler();
        }
    };

    private final CompoundButton.OnCheckedChangeListener radioButton1OnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                editor.putInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 1);
                editor.commit();
                radioButton3.setChecked(false);
                radioButton2.setChecked(false);
            }
        }
    };

    private void initVariable(View view) {
        radioButton3 = view.findViewById(R.id.radioButton3);
        radioButton3.setOnClickListener(radioButton3OnClickListener);
        radioButton3.setOnCheckedChangeListener(radioButton3OnCheckedChangeListener);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton2.setOnClickListener(radioButton2OnClickListener);
        radioButton2.setOnCheckedChangeListener(radioButton2OnCheckedChangeListener);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton1.setOnClickListener(radioButton1OnClickListener);
        radioButton1.setOnCheckedChangeListener(radioButton1OnCheckedChangeListener);
    }
}
