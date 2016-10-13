package me.gking2224.common.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class RandomStringTest {

    @Test
    public void testCreateHex() {
        String s = RandomString.asHex(20);
        assertNotNull(s);
    }

    @Test
    public void testCreateBase64() {
        String s = RandomString.asBase64(30);
        assertNotNull(s);
    }
//    @Test
//    public void testCreateVaryingArgs() {
//        (1..30).each {it ->
//            def s = new RandomString(it).toString()
//            assertNotNull(s)
////            println "$it: $s (${s.length()})"
//        }
//    }
//
//    @Test
//    public void testCreateStandardLengths() {
//        [4,8,16,20,24,28,32,36,40,44].each {it ->
//            def s = new RandomString(it).toString()
//            assertNotNull(s)
//            assertEquals it, s.length()
////            println "$it: $s (${s.length()})"
//        }
//    }
//    
//    
//    @Test
//    def void algo() {
//        (1..40).each {int i->
//            def r = ((((int)( (i-1) / 3)) +1)* 4)
////            println "$i : $r"
//        }
//        
//        [4,8,16,20,24].each {int i->
//            def r = (((int)(i / 4) - 1) * 3) + 1
////            println "$i : $r"
//        }
//    }
    
    

}
