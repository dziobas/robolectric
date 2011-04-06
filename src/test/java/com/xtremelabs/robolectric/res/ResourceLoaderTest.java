package com.xtremelabs.robolectric.res;


import static com.xtremelabs.robolectric.util.TestUtil.resourceFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.xtremelabs.robolectric.R;

public class ResourceLoaderTest {
    @Test
    public void shouldUseFileSystemSeparatorWhenEvaluatingLayoutDirectories() throws Exception {
        assertTrue(ResourceLoader.isLayoutDirectory(File.separator + "layout"));
    }

    @Test
    public void shouldLoadSystemResources() throws Exception {
        ResourceLoader resourceLoader = new ResourceLoader(9, R.class, resourceFile("res"), resourceFile("assets"));
        String stringValue = resourceLoader.getStringValue(android.R.string.copy);
        assertEquals("Copy", stringValue);
    }
}
