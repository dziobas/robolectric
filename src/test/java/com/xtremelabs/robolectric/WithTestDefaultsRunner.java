package com.xtremelabs.robolectric;

import static com.xtremelabs.robolectric.util.TestUtil.resourceFile;

import org.junit.runners.model.InitializationError;

public class WithTestDefaultsRunner extends RobolectricTestRunner {

    public WithTestDefaultsRunner(Class testClass) throws InitializationError {
        super(testClass, new RobolectricConfig(resourceFile("TestAndroidManifest.xml"), resourceFile("res"), resourceFile("assets")));
    }
}
