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

    @Test
    public void testGetProperty() {
        Properties p = new Properties();
        p.put("a", "b");
        Properties pp = new PrefixedProperties("x", p);
        
        assertTrue(pp.containsKey("x.a"));
        assertFalse(pp.containsKey("a"));
        assertEquals("b", pp.getProperty("x.a"));
        assertNull(pp.getProperty("a"));
        
    }

    @Test
    public void testGetPropertyWithLongKey() {
        Properties p = new Properties();
        p.put("a", "b");
        Properties pp = new PrefixedProperties("abcdef", p);
        
        assertTrue(pp.containsKey("abcdef.a"));
        assertFalse(pp.containsKey("a"));
        assertEquals("b", pp.getProperty("abcdef.a"));
        assertNull(pp.getProperty("a"));
    }

    @Test
    public void testPutAll() {
        Properties p = new Properties();
        p.put("a", "b");
        Properties pp = new PrefixedProperties("abcdef", p);

        Properties blank = new Properties();
        blank.putAll(pp);
        assertTrue(blank.containsKey("a"));
        assertEquals("b", blank.get("a"));
    }

    @Test
    public void testUnwrap() {
        Properties common = new Properties();
        common.put("x", "y");
        common.put("local.x", "local.y");

        Properties commonNested = new NestedProperties("local", common);
        
        
        Properties fallback = new Properties(commonNested);
        fallback.put("local.a", "local.b");
        fallback.put("c", "d");
        Properties fallbackNested = new NestedProperties("local", fallback);
        
        PrefixedProperties pp = new PrefixedProperties("prefix", fallbackNested);

        Properties blank = pp.unwrap();
        assertTrue(blank.containsKey("a"));
        assertEquals("local.b", blank.get("a"));
        assertTrue(blank.containsKey("c"));
        assertEquals("d", blank.get("c"));
        assertTrue(blank.containsKey("c"));
        assertEquals("local.y", blank.get("x"));
    }

}
