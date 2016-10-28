package me.gking2224.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

public class PrefixedPropertiesTest {

    @Test
    public void test() {
        Properties p = new Properties();
        p.put("a", "b");
        Properties pp = new PrefixedProperties("x", p);
        
        assertTrue(pp.containsKey("x.a"));
        assertFalse(pp.containsKey("a"));
        assertEquals("b", pp.get("x.a"));
        assertNull(pp.get("a"));
        
    }

}
