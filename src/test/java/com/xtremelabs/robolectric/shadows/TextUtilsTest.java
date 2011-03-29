package com.xtremelabs.robolectric.shadows;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.text.TextUtils;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;

@RunWith(WithTestDefaultsRunner.class)
public class TextUtilsTest {
    @Test
    public void testExpandTemplate() throws Exception {
        assertThat(
                (String) TextUtils.expandTemplate("a^1b^2c^3d", "A", "B", "C", "D"),
                equalTo("aAbBcCd"));
    }
}
