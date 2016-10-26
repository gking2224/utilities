package me.gking2224.common.utils;

import static java.lang.String.format;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ObjectSerializationUtil implements InitializingBean {

    @Autowired private JsonUtil jsonUtil;
    
    @Autowired private XmlUtil xmlUtil;
    
    private List<ObjectSerializer> objectSerializers = new ArrayList<ObjectSerializer>();
    
    public String serializeObject(
            final Object object, final MediaType mediaType) {
        return serializeObject(object, Arrays.asList(mediaType));
    }
    
    public String serializeObject(final Object object, final List<MediaType> mediaTypes) {
        ObjectSerializer os = getObjectSerializer(mediaTypes);
        return serializeObject(object, os);
    }
        
    private  String serializeObject(
            final Object object, final ObjectSerializer os) {
        return os.serializeObject(object);
    }
    
    public <T> T deserializeToObject(final String string, final MediaType mediaType, final Class<T> clazz) {
        return deserializeToObject(string, Arrays.asList(mediaType), clazz);
    }
    
    public <T> T deserializeToObject(final String string, final List<MediaType> mediaTypes, final Class<T> clazz) {
        ObjectSerializer os = getObjectSerializer(mediaTypes);
        return deserializeToObject(string, os, clazz, Charset.defaultCharset());
    }

    private ObjectSerializationException noSerializers(final List<MediaType> mediaTypes) {
        return new ObjectSerializationException(format("No serializers available for media type: %s", mediaTypes));
    }

    private ObjectSerializationException noSerializers(final MediaType mediaType) {
        return new ObjectSerializationException(format("No serializers available for media type: %s", mediaType));
    }

    
    public <T> T deserializeToObject(
            final String string, final ObjectSerializer os, final Class<T> clazz, final Charset charset) {
        
        return os.deserializeToObject(string, clazz);
    }

    protected ObjectSerializer getObjectSerializer(final MediaType mediaType) {
        for (ObjectSerializer os : objectSerializers) {
            if (os.supportsMediaType(mediaType)) {
                return os;
            }
        }
        throw noSerializers(mediaType);
    }
    
    protected ObjectSerializer getObjectSerializer(final List<MediaType> mediaTypes) {
        for (MediaType m : mediaTypes) {

            ObjectSerializer os = getObjectSerializer(m);
            if (os != null) {
                return os;
            }
        }
        throw noSerializers(mediaTypes);
    }
    
    public MediaType getSuportedMediaType(final List<MediaType> mediaTypes) {
        for (MediaType m : mediaTypes) {

            ObjectSerializer os = getObjectSerializer(m);
            if (os != null) {
                return m;
            }
        }
        return null;
    }
    

    @Override
    public void afterPropertiesSet() throws Exception {
        objectSerializers.add(jsonUtil);
        objectSerializers.add(xmlUtil);
    }
}
