package com.xtremelabs.robolectric.shadows;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;


@RunWith(WithTestDefaultsRunner.class)
public class BundleTest {

    private Bundle bundle;

    @Before public void setUp() throws Exception {
        bundle = new Bundle();
    }

    @Test
    public void testContainsKey() throws Exception {
        assertFalse(bundle.containsKey("foo"));
        bundle.putString("foo", "bar");
        assertTrue(bundle.containsKey("foo"));
    }
}
