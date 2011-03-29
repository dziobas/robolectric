package com.xtremelabs.robolectric.res;

import static com.xtremelabs.robolectric.util.TestUtil.resourceFile;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.xtremelabs.robolectric.R;

public class StringArrayResourceLoaderTest {
    private StringArrayResourceLoader stringArrayResourceLoader;

    @Before public void setUp() throws Exception {
        ResourceExtractor resourceExtractor = new ResourceExtractor();
        resourceExtractor.addLocalRClass(R.class);
        StringResourceLoader stringResourceLoader = new StringResourceLoader(resourceExtractor);
        File resourceXmlDir = resourceFile("res", "values");
        new DocumentLoader(stringResourceLoader).loadResourceXmlDir(resourceXmlDir);
        stringArrayResourceLoader = new StringArrayResourceLoader(resourceExtractor, stringResourceLoader);
        new DocumentLoader(stringArrayResourceLoader).loadResourceXmlDir(resourceXmlDir);
    }

    @Test
    public void testStringsAreResolved() throws Exception {
        assertThat(Arrays.asList(stringArrayResourceLoader.getArrayValue(R.array.items)), hasItems("foo", "bar"));
    }

    @Test
    public void testStringsAreWithReferences() throws Exception {
        assertThat(Arrays.asList(stringArrayResourceLoader.getArrayValue(R.array.greetings)), hasItems("hola", "Hello"));
    }
}
