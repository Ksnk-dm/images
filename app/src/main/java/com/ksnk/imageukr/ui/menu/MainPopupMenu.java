package com.ksnk.imageukr.ui.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.TestListenersDemo;

public class MainPopupMenu {
    private PopupWindow popupWindow;
    private Context context;
    private RadioButton radioButton1, radioButton2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TestListenersDemo testListenersDemo;
    private int setting = 0;

    public void showPopupWindow(final View view, TestListenersDemo testListenersDemo) {
        this.context = view.getContext();
        this.testListenersDemo = testListenersDemo;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.RIGHT, 50, -900);
        initSharedPrefs();
        initVariable(popupView);
        checkSettingStatus();
    }

    private void checkSettingStatus() {
        setting = sharedPreferences.getInt("settings", 0);
        if (setting == 2) {
            radioButton1.setChecked(true);
        } else {
            radioButton2.setChecked(true);
        }
    }

    private void initSharedPrefs() {
        sharedPreferences = context.getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
    }

    private void initVariable(View view) {
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testListenersDemo.updateRecycler();
            }
        });
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("checkbox", String.valueOf(b));
                if (b) {
                    editor.putInt("settings", 2);
                    editor.commit();
                    radioButton2.setChecked(false);
                }
            }
        });
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testListenersDemo.updateRecycler();
            }
        });
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor.putInt("settings", 3);
                    editor.commit();
                    radioButton1.setChecked(false);
                }
            }
        });
    }
}
