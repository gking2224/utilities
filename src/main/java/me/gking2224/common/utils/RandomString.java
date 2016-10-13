package me.gking2224.common.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

public class RandomString {

    /**
     * 
     * @param length Any positive number accepted, but only multiples of 4 can be created
     */
//    public RandomString(int length) {
//        this.length = length;
////        def timeBytes = _getTimeBytes()
////        def allBytes = _concat(randomBytes, timeBytes)
//    }
//    
//    public RandomString() {
//        this(20);
//    }
    
    protected static byte[] getRandom(int length) {
        SecureRandom sr = new SecureRandom();
        sr.setSeed(System.currentTimeMillis());
        byte[] randomBytes = new byte[(int)length];
        sr.nextBytes(randomBytes);
        return randomBytes;
    }
    
    protected static byte[] getTimeBytes() {
        return String.valueOf(System.currentTimeMillis()).getBytes();
    }
    
    protected static byte[] concat(byte[] b1, byte[] b2) {
        byte[] b3 = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b3, 0, b1.length);
        System.arraycopy(b2, 0, b3, b1.length, b2.length);
        return b3;
    }
    
    public static String asHex(int length) {
        byte[] bytes = getRandom(length);
        String hex = new BigInteger(bytes).abs().toString(16);
        return hex.substring(0, length);
    }
    
    public static String asBase64(int l) {
        int length = convertLength(l);
        byte[] bytes = getRandom(length);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
    
    protected static int convertLength(int i) {
        return (((int)(i / 4) - 1) * 3) + 3;
    }

}