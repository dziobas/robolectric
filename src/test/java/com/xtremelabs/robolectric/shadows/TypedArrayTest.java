package com.xtremelabs.robolectric.shadows;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;

@RunWith(WithTestDefaultsRunner.class)
public class TypedArrayTest {
    private Context context;

    @Before public void setUp() throws Exception {
        context = new Activity();
    }

    @Test
    public void getResources() throws Exception {
        assertNotNull(context.obtainStyledAttributes(null).getResources());
    }
}
