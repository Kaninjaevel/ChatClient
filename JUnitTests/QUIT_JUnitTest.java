package JUnitTests;

import PDU.PDU_QUIT;
import org.junit.Test;

import static org.junit.Assert.*;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class QUIT_JUnitTest {

    /**
     * Tests if  PDU is constructed correctly.
     * @throws IOException
     */

    @Test
    public void shouldBeAbleToCreate(){
        new PDU_QUIT();
    }

    @Test
    public void shouldHaveOP(){
        byte[] bA;
        PDU_QUIT q = new PDU_QUIT();
        bA= q.getBytes();
        assertEquals(11,bA[0]);
    }
    @Test
    public void shouldPadCorrectly(){
        byte[] bA;
        PDU_QUIT q = new PDU_QUIT();
        bA = q.getBytes();
        for(int i=0;i<3;i++)
            assertEquals(0,bA[i+1]);
    }
    @Test
    public void correctNumberOfBytes(){
        assertEquals(4,new PDU_QUIT().getBytes().length);
    }

    /**
     * Tests if  PDU is constructed correctly from inputstream.
     * @throws IOException
     */
    @Test
    public void shouldCreateFromInputStream() throws IOException {
        byte[] b= {11,0,0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);
        in.read();
        PDU_QUIT pQ = new PDU_QUIT(in);

        byte[]b2 = pQ.getBytes();

        for(int i=0;i<b.length;i++) {
            System.out.println(i +": " + b[i] +" "+ b2[i]);
            assertEquals(b[i], b2[i]);
        }
        pQ.print();

    }

    /**
     * Tests if corrupted PDU is found and handled.
     * @throws IOException
     */
    @Test
    public void isCorrupt() throws IOException {
        byte[] b= {11,0,2,2};
        InputStream in = new ByteArrayInputStream(b,0,b.length);
        in.read();
        PDU_QUIT pQ = new PDU_QUIT(in);

        assertEquals(null, pQ.getBytes());
    }

}
