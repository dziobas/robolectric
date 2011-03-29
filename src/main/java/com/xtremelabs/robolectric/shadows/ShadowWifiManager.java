package com.xtremelabs.robolectric.shadows;

import android.net.wifi.WifiManager;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(WifiManager.class)
public class ShadowWifiManager {
    private boolean wifiEnabled = true;

    @Implementation
    public boolean setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
        return true;
    }

    @Implementation
    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

}
