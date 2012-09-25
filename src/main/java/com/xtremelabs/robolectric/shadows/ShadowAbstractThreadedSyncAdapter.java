package com.xtremelabs.robolectric.shadows;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@Implements(AbstractThreadedSyncAdapter.class)
public class ShadowAbstractThreadedSyncAdapter {

    @Implementation
    public Context getContext() {
        return Robolectric.application;
    }
}
