
package JUnitTests;

import PDU.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.io.*;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class PDUOutputStream_JUnitTest {



    @Test
    public void shouldBeAbleToSendPDU() throws IOException {
        PDU_MESS pM = new PDU_MESS("Hejsan på er?","Kalle");
        byte[] b= pM.getBytes();


        ByteArrayOutputStream bOut = new ByteArrayOutputStream(36);
        OutputStream out = bOut;
        PDUOutputStream pOut = new PDUOutputStream(out);

        pOut.sendPDU(pM);

        byte[] b2=bOut.toByteArray();

        //comparing number of bytes in/out of Outputstream
        assertEquals(b.length,b2.length);

        //Comparing each byte before/after outputstream.
        for(int i=0;i<b.length;i++)
            assertEquals(b[i],b2[i]);
    }

}
