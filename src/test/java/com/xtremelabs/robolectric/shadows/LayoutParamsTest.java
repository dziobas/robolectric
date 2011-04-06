package com.xtremelabs.robolectric.shadows;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.Gallery;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;

@RunWith(WithTestDefaultsRunner.class)
public class LayoutParamsTest {
    @Test
    public void testConstructor() throws Exception {
        Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(123, 456);
        assertThat(layoutParams.width, equalTo(123));
        assertThat(layoutParams.height, equalTo(456));
    }
}
