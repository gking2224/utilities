package me.gking2224.common.utils;

import java.util.Properties;
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
        super(wrapped);
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
        return key.substring(dottedPrefix.length());
    }

    @Override public Object get(final Object key) {
        if (!prefixMatches(key)) return null;
        return getOrDefault(key, null);
    }
    
    private boolean prefixMatches(Object k) {
        String key = stringKey(k);
        return (key.startsWith(this.dottedPrefix));
    }

    @Override public Object getOrDefault(Object key, Object defaultValue) {
        return doWithKeyOrDefault(key, defaultValue, (k)-> wrapped.get(k));
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
        if (initialKey == null) return null;
        Object key = removePrefix(initialKey);
        return func.apply(key);
    }

    @Override
    public String toString() {
        return String.format("PrefixedProperties [prefix=%s, wrapped=%s]", prefix, wrapped);
    }
}
