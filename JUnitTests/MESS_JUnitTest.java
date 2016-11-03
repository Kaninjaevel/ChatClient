package JUnitTests;
import PDU.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

/**
 * JUnit test for PDU_MESS
 */
public class MESS_JUnitTest {

    @Test
    public void shouldBeAbleToCreate() throws UnsupportedEncodingException {
        new PDU_MESS("Hej på dig","Kalle");
    }

    /**
     * Testing so that the constructor creates a correct byteArray from given
     * string and identity.
     * @throws UnsupportedEncodingException
     */
    @Test
    public void shouldCreateCorrectArray() throws UnsupportedEncodingException {
        PDU_MESS pM = new PDU_MESS("Hej","Kalle");
        byte[] b = pM.getBytes();
        byte[] bA = {10,0,5,-22,0,3,0,0,0,0,0,0
                ,'H','e','j',0,'K','a','l','l','e',0,0,0};

        for(int i=0;i<bA.length;i++)
            assertEquals(bA[i],b[i]);
        //Checking so that the bytearray is complete
        assertEquals(0,new Checksum().computeChecksum(b));
    }

    /**
     * Tests if the PDU creates the same byte structure from inputstream as from
     * the normal constructor.
     * @throws IOException
     */

    @Test
    public void shouldCreateFromInputStream() throws IOException {
        PDU_MESS pM = new PDU_MESS("Hej","Kalle");
        byte[] b= pM.getBytes();
        InputStream in = new ByteArrayInputStream(pM.getBytes()
                ,0,pM.getBytes().length);
        in.read();
        PDU_MESS pM2 = new PDU_MESS(in);
        byte[] b2 = pM2.getBytes();
        assertEquals(b.length, b2.length);

        for(int i=0;i<pM.getBytes().length;i++){
            assertEquals(b[i],b2[i]);
        }

    }

    @Test
    public void badCheckSum() throws IOException {
        byte[] bA = {10,0,5,42,0,3,0,0,0,0,0,0,'H','e','j',0
                ,'K','a','l','l','e',0,0,0};
        InputStream in = new ByteArrayInputStream(bA);
        PDU_MESS pM2 = new PDU_MESS(in);
        assertEquals(null,pM2.getBytes());


    }
    @Test
    public void shouldPrint() throws UnsupportedEncodingException {
        new PDU_MESS("In natural science the principles of truth ought " +
                "to be confirmed by observation.", "Karl von Linné").print();
    }

}
