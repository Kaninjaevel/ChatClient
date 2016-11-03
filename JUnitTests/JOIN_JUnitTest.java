package JUnitTests;

import PDU.PDU_JOIN;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class JOIN_JUnitTest {



    @Test
    public void shouldBeAbleToCreate() throws UnsupportedEncodingException {
        new PDU_JOIN("Kalle");
    }

    @Test
    public void shouldHaveCorrectByteArrayLength()
            throws UnsupportedEncodingException {
        assertEquals(12,new PDU_JOIN("Kalle").getBytes().length);
    }

    @Test
    public void shouldCreateCorrectByteArray()
            throws UnsupportedEncodingException {
        PDU_JOIN pJ = new PDU_JOIN("Kalle");
        byte[] b = pJ.getBytes();

        assertEquals(12,b[0]);
        assertEquals(5,b[1]);
        for(int i=2;i<4;i++)
            assertEquals(0,b[i]);

        byte[] bA = {b[4],b[5],b[6],b[7],b[8]};

        assertEquals("Kalle",new String(bA,"UTF-8"));

        for(int i=0;i<"Kalle".length()%4;i++)
            assertEquals(0,b[i+9]);
    }
}
