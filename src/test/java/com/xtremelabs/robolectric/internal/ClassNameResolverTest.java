package com.xtremelabs.robolectric.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import android.app.Application;

import com.xtremelabs.robolectric.TestApplication;

public class ClassNameResolverTest {
    @Test
    public void shouldResolveClassesBySimpleName() throws Exception {
        assertEquals(TestApplication.class, new ClassNameResolver<Application>("com.xtremelabs.robolectric", "TestApplication").resolve());
    }

    @Test
    public void shouldResolveClassesByDottedSimpleName() throws Exception {
        assertEquals(TestApplication.class, new ClassNameResolver<Application>("com.xtremelabs.robolectric", ".TestApplication").resolve());
    }

    @Test
    public void shouldResolveClassesByFullyQualifiedName() throws Exception {
        assertEquals(TestApplication.class, new ClassNameResolver<Application>("com.xtremelabs.robolectric", "com.xtremelabs.robolectric.TestApplication").resolve());
    }

    @Test
    public void shouldResolveClassesByPartiallyQualifiedName() throws Exception {
        assertEquals(TestApplication.class, new ClassNameResolver<Application>("com.xtremelabs", ".robolectric.TestApplication").resolve());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotResolveClassesByUndottedPartiallyQualifiedNameBecauseAndroidDoesnt() throws Exception {
        new ClassNameResolver<Application>("com.xtremelabs", "robolectric.TestApplication").resolve();
    }
}
