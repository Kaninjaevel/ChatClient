package JUnitTests;

import PDU.PDU_PARTICIPANTS;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class PARTICIPANTS_JUnitTest {

    /**
     * Tests if  PDU is constructed correctly.
     * @throws IOException
     */
    @Test
    public void shouldBeAbleToCreate() throws IOException {
        byte[] b= {(byte)19,2,0,9,'P','0',0,'K','0','0','L','E',0,0,0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PARTICIPANTS pP = new PDU_PARTICIPANTS(in);
        byte[] b2= pP.getBytes();

        System.out.println(b.length +" : "+ b2.length);

        for(int i=0;i<b.length;i++){
            assertEquals(b[i],b2[i]);
            System.out.println("byte: " +i +  " " +b[i] + " " + b2[i]);
        }

        pP.print();

    }

    /**
     * Tests if corrupted PDU is found and handled.
     * @throws IOException
     */
    @Test
    public void isCorrupt() throws IOException {
        byte[] b= {(byte)19,2,0,9,'P','0',0,'K','0','0','L','E',0,2,2,2};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PARTICIPANTS pP = new PDU_PARTICIPANTS(in);
        assertEquals(null,pP.getBytes());
    }
}
