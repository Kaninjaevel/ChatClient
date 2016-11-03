package JUnitTests;
import PDU.PDU_PJOIN;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PJOIN_JUnitTest {

    /**
     * Tests if  PDU is constructed correctly.
     * @throws IOException
     */
    @Test
    public void shouldBeAbleToCreate() throws IOException {
        byte[] b= {(byte)16,(byte)5,(byte)0,(byte)0,0,0,0,0
                ,'K','A','L','L','E',0,0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PJOIN pJ = new PDU_PJOIN(in);
        byte[] b2= pJ.getBytes();

        System.out.println(b.length +" : "+ b2.length);

        for(int i=0;i<b.length;i++){
            assertEquals(b[i],b2[i]);
            System.out.println("byte: " +i +  " " +b[i] + " " + b2[i]);
        }
        pJ.print();
    }
    /**
     * Tests if corrupted PDU is found and handled.
     * @throws IOException
     */

    @Test
    public void isCorrupt() throws IOException {
        byte[] b= {(byte)16,(byte)5,(byte)0,(byte)0,0,0,0,0
                ,'K','A','L','L','E',2,2,2};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PJOIN pJ = new PDU_PJOIN(in);
        assertEquals(null,pJ.getBytes());
    }

}
