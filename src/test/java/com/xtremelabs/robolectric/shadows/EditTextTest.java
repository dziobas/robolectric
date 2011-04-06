package com.xtremelabs.robolectric.shadows;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.EditText;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;

@RunWith(WithTestDefaultsRunner.class)
public class EditTextTest {
    @Test
    public void shouldBeFocusableByDefault() throws Exception {
        assertTrue(new EditText(null).isFocusable());
        assertTrue(new EditText(null).isFocusableInTouchMode());
    }
}
