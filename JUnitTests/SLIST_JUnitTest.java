package JUnitTests;
import PDU.PDU_SLIST;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class SLIST_JUnitTest {

    /**
     * Tests if  PDU is constructed correctly.
     * @throws IOException
     */

    @Test
    public void shouldBeAbleToCreate() throws IOException {
        byte[] b= {(byte)4,0,0,2,127,0,0,1,0,20,1,2,'T','A'
                ,0,0,127,0,0,1,0,20,1,2,'T','S',0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);

        in.read();
        PDU_SLIST sL = new PDU_SLIST(in);
        byte[] b2= sL.getBytes();
        System.out.println(b.length +" : "+ b2.length);

        for(int i=0;i<b.length;i++){
            assertEquals(b[i],b2[i]);
            System.out.println("byte: " +i +  " " +b[i] + " " + b2[i]);
        }
        sL.print();
    }

    /**
     * Tests if corrupted PDU is found and handled.
     * @throws IOException
     */

    @Test
    public void isCorrupt() throws IOException {
        byte[] b= {(byte)4,0,0,2,127,0,0,1,0,20,1,2,'T','A',0
                ,1,127,0,0,1,0,20,1,2,'T','S',0,0};
        InputStream in = new ByteArrayInputStream(b,0,b.length);

        in.read();
        PDU_SLIST sL = new PDU_SLIST(in);
        assertEquals(null,sL.getBytes());
    }

}
