package me.gking2224.common.utils;

import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;

public abstract class ObjectSerializer {

    public abstract String serializeMap(Map<String, Object> map) throws ObjectSerializationException;

    public abstract String serializeObject(Object o) throws ObjectSerializationException;

    public abstract <T> T deserializeToObject(String json, Class<T> clazz) throws ObjectSerializationException;
    
    public abstract Set<MediaType> getSupportedMediaTypes();

    public boolean supportsMediaType(MediaType mediaType) {
        return getSupportedMediaTypes().stream()
                .filter(m ->
                        m.getType().equalsIgnoreCase(mediaType.getType()) &&
                        m.getSubtype().equalsIgnoreCase(mediaType.getSubtype()))
                .findFirst()
                .isPresent();
    }

}
