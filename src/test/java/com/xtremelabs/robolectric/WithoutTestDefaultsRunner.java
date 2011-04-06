package com.xtremelabs.robolectric;

import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;

public class WithoutTestDefaultsRunner extends RobolectricTestRunner {
    public WithoutTestDefaultsRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override public void internalBeforeTest(Method method) {
        // Don't do any resource loading or shadow class binding, because that's what we're trying to test here.
    }
}
