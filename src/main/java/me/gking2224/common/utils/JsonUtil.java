package me.gking2224.common.utils;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@Component
public class JsonUtil extends ObjectSerializer {
    
    private static Set<MediaType> mediaTypes;
    static {
        mediaTypes = new HashSet<MediaType>(Arrays.asList(APPLICATION_JSON, APPLICATION_JSON_UTF8, TEXT_PLAIN));
    }

    private ObjectMapper om = new ObjectMapper();
    
    @Override
    public String serializeMap(Map<String, Object> map) throws ObjectSerializationException {
        try {
            return om.writeValueAsString(map);
        } catch (IOException e) {
            throw new ObjectSerializationException(e.getMessage(), e);
        }
    }
    @Override
    public String serializeObject(Object o) throws ObjectSerializationException {
        try {
            return om.writeValueAsString(o);
        } catch (IOException e) {
            throw new ObjectSerializationException(e.getMessage(), e);
        }
    }
    
    public String getPathValue(String json, String path) {
        return JsonPath.read(json, path);
    }
    
    @Override
    public <T> T deserializeToObject(String json, Class<T> clazz)
            throws ObjectSerializationException {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectSerializationException(e.getMessage(), e);
        }
    }

    public Object[] getArray(String json, String path) {
        return ((JSONArray) getValue(json, path)).toArray();
    }

    public Object getFilterValue(String json, String path) {
        Object[] arr = getArray(json, path);
        
        if (arr == null) return null;
        else if (arr.length > 1) throw new IllegalStateException("Expected max one value");
        else return arr[0];
    }

    public Object getFilterValue(String json, String path, int index) {
        Object[] array = getArray(json,  path);
        index = (index < 0) ? array.length - index : index;
        if (array.length < index + 1) return null;
        else return array[index];
    }

    public Object getValue(String json, String path) {
        return JsonPath.parse(json).read(path);
    }

    @Override
    public Set<MediaType> getSupportedMediaTypes() {
        return mediaTypes;
    }

}
