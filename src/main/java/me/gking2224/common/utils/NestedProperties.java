package me.gking2224.common.utils;

import static java.lang.String.format;

import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestedProperties extends Properties {
    
    private static Logger logger = LoggerFactory.getLogger(NestedProperties.class);

    /**
     * 
     */
    private static final long serialVersionUID = 8863605320894985731L;

    private String prefix;

    private Properties wrapped;

    public NestedProperties(final String prefix, final Properties wrapped) {
        super();
        this.prefix = prefix;
        this.wrapped = wrapped;
    }
    
    @Override public Object get(final Object key) {
        return getOrDefault(key, null);
    }
    
    @Override public Object getOrDefault(Object key, Object defaultValue) {
        return doWithKeyOrDefault(key, defaultValue, (k)-> wrapped.getProperty((String)k));
    }
    
    @Override public String getProperty(String key) {
        return getProperty(key, null);
    }
    
    @Override public String getProperty(String key, String defaultValue) {
        return (String)doWithKeyOrDefault(key, defaultValue, (k)-> wrapped.getProperty((String)k));
    }

    private String prefix(final String key) {
        return format("%s.%s", prefix, key);
    }

    private String removePrefix(final String key) {
        if (key.startsWith(prefix+".")) return key.substring(prefix.length()+1);
        else return key;
    }
    
    private Object doWithKeyOrDefault(final Object initialKey, Object defaultValue, Function<Object,Object> func) {
        Object value = doWithKey(initialKey, func);
        if (value != null) return value;
        else return defaultValue;
    }
    
    private Object doWithKey(final Object initialKey, Function<Object,Object> func) {
        if (initialKey == null) return null;
        Object key = prefix(initialKey.toString());
        Object value = func.apply(key);
        if (value != null && !NestedProperties.class.isAssignableFrom(wrapped.getClass())) {
            logger.debug("Using {}={}", key, value);
        }
        if (value == null) {
            key = initialKey;
            value = func.apply(key);
            if (value != null && !NestedProperties.class.isAssignableFrom(wrapped.getClass())) {
                logger.debug("Using {}={}", key, value);
            }
        }
        return value;
    }
    
    public Set<Map.Entry<Object,Object>> entrySet() {
        Set<Map.Entry<Object, Object>> rv = new HashSet<Map.Entry<Object,Object>>();
        Enumeration<?> keys = wrapped.propertyNames();
        while (keys.hasMoreElements()) {
            Object o= keys.nextElement();
            String key = (String)o;
            Object value = getProperty(key);
            rv.add(new AbstractMap.SimpleEntry<Object, Object>(key, value));
        };
        return rv;
    }
    public synchronized Enumeration<Object> keys() {
        Enumeration<?> keys = wrapped.propertyNames();
        Hashtable<Object,Object> ht = new Hashtable<Object,Object>();
//        Object value = new Object();
        while (keys.hasMoreElements()) {
            Object o = keys.nextElement();
            String key = (String)o;
            String value = getProperty(key);
            if (key.startsWith(prefix)) {
                ht.put(removePrefix(key), value);
            }
            else ht.put(key, value);
            
        }
        return ht.keys();
    }
    
    @Override
    public String toString() {
        return String.format("NestedProperties [prefix=%s, wrapped=%s]", prefix, wrapped);
    }
}
