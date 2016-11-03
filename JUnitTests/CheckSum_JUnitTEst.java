package JUnitTests;

import PDU.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;


/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class CheckSum_JUnitTEst {

    /**
     * Testing so that the checksum returns the correct complementary
     * checksum.
     */
    @Test
    public void checkSumTest(){

        byte[] b = {120,100, 100,100,0};
        b[4]= (new Checksum().computeChecksum(b));
        assertEquals(0,new Checksum().computeChecksum(b));
    }

    /**
     * Controlling the checksum of a message PDU.
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testPDUChecksum() throws UnsupportedEncodingException {
        assertEquals(0,new Checksum().computeChecksum(
                new PDU_MESS("Tja","Kalle").getBytes()));
    }

}
