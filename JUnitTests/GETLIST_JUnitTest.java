package JUnitTests;

import PDU.PDU_GETLIST;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 * JUnit test for PDU_GETLIST
 */

public class GETLIST_JUnitTest {


    @Test
    public void shouldBeAbleToCreate()  {
        new PDU_GETLIST();
    }

    @Test
    public void testCorrectByteArray() {
        PDU_GETLIST pG = new PDU_GETLIST();
        byte[] b = pG.getBytes();
        assertEquals(3,b[0]);
        for(int i=1;i<4;i++)
            assertEquals(0,b[i]);
    }

}
