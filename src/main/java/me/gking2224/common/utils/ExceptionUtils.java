package me.gking2224.common.utils;

public class ExceptionUtils {

    public static Throwable getRootCause(final Throwable t) {
        Throwable rv = org.apache.commons.lang.exception.ExceptionUtils.getRootCause(t);
        if (rv == null) rv = t;
        return rv;
    }

    public static String getRootCauseMessage(final Throwable t) {
        return org.apache.commons.lang.exception.ExceptionUtils.getRootCauseMessage(t);
    }
}
