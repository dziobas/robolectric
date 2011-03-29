package com.xtremelabs.robolectric.util;

import static junit.framework.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class StringsTest {
    @Test
    public void shouldGetStringFromStream() throws Exception {
        InputStream stream = new ByteArrayInputStream("some random string".getBytes());
        assertEquals("some random string", Strings.fromStream(stream));
    }
}
