package com.xtremelabs.robolectric.shadows;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.tester.android.content.TestSharedPreferences;

/**
 * Shadow for {@code PreferenceManager} that returns instances of the {@link com.xtremelabs.robolectric.tester.android.content.TestSharedPreferences} utility class
 */
@Implements(PreferenceManager.class)
public class ShadowPreferenceManager {

    @Implementation
    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        ShadowApplication shadowApplication = shadowOf((Application) context.getApplicationContext());
        return new TestSharedPreferences(shadowApplication.getSharedPreferenceMap(), "__default__", Context.MODE_PRIVATE);
    }

}
