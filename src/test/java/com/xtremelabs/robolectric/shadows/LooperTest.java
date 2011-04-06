package com.xtremelabs.robolectric.shadows;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Looper;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;

@RunWith(WithTestDefaultsRunner.class)
public class LooperTest {

    @Test
    public void testMainLooperAndMyLooperAreTheSameInstanceOnMainThread() throws Exception {
        assertSame(Looper.myLooper(), Looper.getMainLooper());
    }
}
