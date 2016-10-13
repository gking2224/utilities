package me.gking2224.common.utils;

import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class XmlUtil extends ObjectSerializer {
    
    private static Set<MediaType> mediaTypes;
    static {
        mediaTypes = new HashSet<MediaType>(Arrays.asList(APPLICATION_XML, TEXT_PLAIN));
    }

    private XmlMapper om = new XmlMapper();
    
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

    @Override
    public <T> T deserializeToObject(String json, Class<T> clazz) throws ObjectSerializationException {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public Set<MediaType> getSupportedMediaTypes() {
        return mediaTypes;
    }

}
