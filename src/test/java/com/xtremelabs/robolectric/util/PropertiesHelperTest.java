package com.xtremelabs.robolectric.util;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropertiesHelperTest {
    @Test
    public void shouldDoVariableSubstitutionUsingSystemProperties() throws Exception {
        System.setProperty("blinfiddle.ox.heart", "orange juice");
        System.setProperty("ornithopter.defenestration", "nickel");

        assertEquals(PropertiesHelper.doSingleSubstitution("presenting: ${blinfiddle.ox.heart} -- ${ornithopter.defenestration}.", null), "presenting: orange juice -- nickel.");
    }

    @Test
    public void shouldDoVariableSubstitutionOnProperties() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("result", "{${first.value} + ${system.value.xbf5547}}");
        System.setProperty("system.value.xbf5547", "system");
        properties.setProperty("first.value", "first");
        PropertiesHelper.doSubstitutions(properties);
        assertEquals("{first + system}", properties.getProperty("result"));
    }
}
