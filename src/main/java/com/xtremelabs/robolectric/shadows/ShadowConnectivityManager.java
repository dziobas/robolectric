package com.xtremelabs.robolectric.shadows;

import static com.xtremelabs.robolectric.Robolectric.newInstanceOf;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 * Shadow of {@code ConnectivityManager} that provides for the simulation of
 * the active connection status.
 */

@Implements(ConnectivityManager.class)
public class ShadowConnectivityManager {

    private NetworkInfo networkInfo;

    @Implementation
    public NetworkInfo getActiveNetworkInfo() {
        return networkInfo == null ? networkInfo = newInstanceOf(NetworkInfo.class) : networkInfo;
    }
}
