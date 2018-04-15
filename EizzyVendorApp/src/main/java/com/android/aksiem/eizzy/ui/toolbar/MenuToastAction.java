package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.widget.Toast;

import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuAction;

import static android.widget.Toast.LENGTH_SHORT;


public class MenuToastAction implements MenuAction {
    private final String text;

    private Context context;

    public MenuToastAction(@ApplicationContext Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void execute() {
        Toast.makeText(context, text, LENGTH_SHORT).show();
    }
}