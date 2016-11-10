package me.gking2224.common.utils;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrefixedProperties extends Properties {
    
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PrefixedProperties.class);

    /**
     * 
     */
    private static final long serialVersionUID = 8863605320894985731L;

    private Properties wrapped;
    
    private String prefix;

    private String dottedPrefix;

    public PrefixedProperties(final String prefix, final Properties wrapped) {
        super();
        this.prefix = prefix;
        this.dottedPrefix = prefix + ".";
        this.wrapped = wrapped;
    }
    
    @Override
    public boolean containsKey(final Object key) {
        if (!prefixMatches(key)) return false;
        else return wrapped.containsKey(removePrefix(key));
    }

    private String stringKey(final Object key) {
        return (key instanceof String) ? (String)key : key.toString();
    }
    
    private String removePrefix(final Object k) {
        String key = stringKey(k);
        if (key.startsWith(dottedPrefix))
            return key.substring(dottedPrefix.length());
        else return key;
    }

    @Override public Object get(final Object key) {
        return getOrDefault(key, null);
    }
    
    private boolean prefixMatches(Object k) {
        String key = stringKey(k);
        return (key.startsWith(this.dottedPrefix));
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

    private Object doWithKeyOrDefault(final Object initialKey, Object defaultValue, Function<Object,Object> func) {
        Object value = doWithKey(initialKey, func);
        if (value != null) return value;
        else return defaultValue;
    }
    
    private Object doWithKey(final Object initialKey, Function<Object,Object> func) {
        if (!prefixMatches(initialKey)) return null;
        if (initialKey == null) return null;
        Object key = removePrefix(initialKey);
        return func.apply(key);
    }
    
    public Properties unwrap() {
        Properties rv = new Properties();
        Enumeration<?> keys = wrapped.propertyNames();

        while (keys.hasMoreElements()) {
            Object o = keys.nextElement();
            String key = (String)o;
            String value = getProperty(dottedPrefix+key);
            rv.put(key,  value);
        }
        return rv;
    }
    
    public Set<Map.Entry<Object,Object>> entrySet() {
        Set<Map.Entry<Object, Object>> rv = new HashSet<Map.Entry<Object,Object>>();
        Enumeration<?> keys = wrapped.propertyNames();
        while (keys.hasMoreElements()) {
            Object o= keys.nextElement();
            String key = prefix+"."+((String)o);
            Object value = getProperty(key);
            rv.add(new AbstractMap.SimpleEntry<Object, Object>(o, value));
        };
        return rv;
    }
    
    public synchronized Enumeration<Object> keys() {
        Enumeration<Object> keys = wrapped.keys();
        Hashtable<Object,Object> ht = new Hashtable<Object,Object>();
        Object value = new Object();
        while (keys.hasMoreElements()) {
            Object o = keys.nextElement();
            String key = (String)o;
            ht.put(prefix+"."+key, value);
        }
        return ht.keys();
    }
    public Enumeration<?> propertyNames() {
        Enumeration<?> propNames = super.propertyNames();
        return propNames;
    }

    @Override
    public String toString() {
        return String.format("PrefixedProperties [prefix=%s, wrapped=%s]", prefix, wrapped);
    }
}
