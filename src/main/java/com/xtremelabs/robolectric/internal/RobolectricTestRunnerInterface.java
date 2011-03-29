package com.xtremelabs.robolectric.internal;

import java.lang.reflect.Method;

import com.xtremelabs.robolectric.RobolectricConfig;

public interface RobolectricTestRunnerInterface {
    Object createTest() throws Exception;

    void internalBeforeTest(Method method);

    void internalAfterTest(Method method);

    void setRobolectricConfig(RobolectricConfig config);
}
