package me.gking2224.common.utils;

public class ObjectSerializationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1249038932430289524L;

    public ObjectSerializationException(final String msg, final Throwable t) {
        super(msg, t);
    }

    public ObjectSerializationException(final String msg) {
        super(msg);
    }
}