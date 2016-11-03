package JUnitTests;
import PDU.PDU_PLEAVE;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PLEAVE_JUnitTest {


    /**
     * Tests if  PDU is constructed correctly.
     * @throws IOException
     */

    @Test
    public void shouldBeAbleToCreate() throws IOException {
        byte[] b= {(byte)17,(byte)5,(byte)0,(byte)0,0,0,0,0
                ,'K','A','L','L','E',0,0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PLEAVE pL = new PDU_PLEAVE(in);
        byte[] b2= pL.getBytes();

        System.out.println(b.length +" : "+ b2.length);

        for(int i=0;i<b.length;i++){
            assertEquals(b[i],b2[i]);
        }

        pL.print();

    }

    /**
     * Tests if corrupted PDU is found and handled.
     * @throws IOException
     */
    @Test
    public void isCorrupted() throws IOException {
        byte[] b= {17,5,0,0,0,0,0,0, 'K','A','L','L','E',2,2,2};
        InputStream in = new ByteArrayInputStream(b,0,b.length);


        in.read();
        PDU_PLEAVE pL = new PDU_PLEAVE(in);
        assertEquals(null,pL.getBytes());
    }

}
