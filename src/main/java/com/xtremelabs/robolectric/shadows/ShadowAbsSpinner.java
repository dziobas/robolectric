package com.xtremelabs.robolectric.shadows;

import android.widget.AbsSpinner;
import android.widget.SpinnerAdapter;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(AbsSpinner.class)
public class ShadowAbsSpinner extends ShadowAdapterView {
    @Implementation
    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override @Implementation
    public SpinnerAdapter getAdapter() {
        return (SpinnerAdapter) super.getAdapter();
    }
}
