package com.xtremelabs.robolectric.shadows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.res.AssetManager;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.WithTestDefaultsRunner;
import com.xtremelabs.robolectric.util.Strings;

@RunWith(WithTestDefaultsRunner.class)
public class AssetManagerTest {
    AssetManager assetManager;
    String sp = System.getProperty("file.separator");

    @Before
    public void setUp() throws Exception {
        assetManager = new Activity().getAssets();
    }

    @Test
    public void assertGetAssetsNotNull() {
        assertNotNull(assetManager);

        assetManager = Robolectric.application.getAssets();
        assertNotNull(assetManager);

        assetManager = Robolectric.application.getResources().getAssets();
        assertNotNull(assetManager);
    }

    @Test
    public void assetsPathListing() throws IOException {
        List<String> files;
        String testPath;

        testPath = "";
        files = Arrays.asList(assetManager.list(testPath));
        assertTrue(files.contains("docs"));
        assertTrue(files.contains("assetsHome.txt"));

        testPath = "docs";
        files = Arrays.asList(assetManager.list(testPath));
        assertTrue(files.contains("extra"));

        testPath = "docs" + sp + "extra";
        files = Arrays.asList(assetManager.list(testPath));
        assertTrue(files.contains("testing"));

        testPath = "docs" + sp + "extra" + sp + "testing";
        files = Arrays.asList(assetManager.list(testPath));
        assertTrue(files.contains("hello.txt"));
    }

    @Test
    public void assetsInputStreams() throws IOException {
        String testPath;
        String fileContents;
        InputStream inputStream;

        testPath = "assetsHome.txt";
        inputStream = assetManager.open(testPath);
        fileContents = Strings.fromStream(inputStream);
        assertEquals("assetsHome!", fileContents);

        testPath = "docs" + sp + "extra" + sp + "testing" + sp + "hello.txt";
        inputStream = assetManager.open(testPath);
        fileContents = Strings.fromStream(inputStream);
        assertEquals("hello!", fileContents);
    }
}
