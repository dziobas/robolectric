package com.xtremelabs.robolectric.tester.android.view;

import static com.xtremelabs.robolectric.Robolectric.newInstanceOf;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

@SuppressWarnings({"UnusedDeclaration"})
public class TestWindowManager implements WindowManager {

    private static Display display;

    @Override
    public void addView(View arg0, android.view.ViewGroup.LayoutParams arg1) {
    }

    @Override
    public void removeView(View arg0) {
    }

    @Override
    public void updateViewLayout(View arg0, android.view.ViewGroup.LayoutParams arg1) {
    }

    @Override
    public Display getDefaultDisplay() {
        return display == null ? display = newInstanceOf(Display.class) : display;
    }

    @Override
    public void removeViewImmediate(View arg0) {
    }

}
