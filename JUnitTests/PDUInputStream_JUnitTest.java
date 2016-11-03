package JUnitTests;
import PDU.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PDUInputStream_JUnitTest {

    @Test
    public void shouldBeAbleToCreate() throws IOException {
        byte[] b = {2,3,5,6};
        InputStream inputStream = new ByteArrayInputStream(b);
        new PDUInputStream(inputStream);
    }

    @Test
    public void shouldReadParticipants() throws IOException {
        byte[] b= {(byte)19,2,0,9,'P','O',0,'K','A','L','L','E',0,0,0,0};
        InputStream inputStream = new ByteArrayInputStream(b);
        PDUInputStream pIN = new PDUInputStream(inputStream);
        PDU pDU =pIN.readPdu();

        //Comparing incoming/outgoing PDU bit for bit
        for(int i=0;i<b.length;i++)
            assertEquals(pDU.getBytes()[i],b[i]);

        //Testing print on PDU
        pDU.print();
    }

    @Test
    public void hasPDU() throws IOException {

        //Testing if able to tell if stream has a PDU
        PDU_MESS pM = new PDU_MESS("Hur får man pengar på kontot?","KALLE");
        byte[] b= pM.getBytes();
        InputStream inputStream = new ByteArrayInputStream(b);
        PDUInputStream pIN = new PDUInputStream(inputStream);
        assertEquals(true,pIN.hasPDU());

        //Testing if able to tell if stream is Empty
        b= new byte[1];
        b[0]=1;
        inputStream = new ByteArrayInputStream(b);
        pIN = new PDUInputStream(inputStream);
        inputStream.read();
        assertEquals(false,pIN.hasPDU());
    }

    @Test
    public void shouldReadMESSPDU() throws IOException {
        PDU_MESS pM = new PDU_MESS("Hur får man pengar på kontot?","KALLE");
        byte[] b= pM.getBytes();
        InputStream inputStream = new ByteArrayInputStream(b);
        PDUInputStream pIN = new PDUInputStream(inputStream);
        PDU pDU =pIN.readPdu();


        //Checking so that the inputstream has returned the right PDU type

        assertEquals(pDU.getClass(),pM.getClass());

        //Comparing incoming/outgoing PDU bit for bit
        for(int i=0;i<pM.getBytes().length;i++)
            assertEquals(pDU.getBytes()[i],pM.getBytes()[i]);
        //Testing print on PDU
        pDU.print();
    }

    @Test
    public void shouldReactToInvalidInput() throws IOException {
        byte[] b = {-2,3,4,5};
        InputStream inputStream = new ByteArrayInputStream(b);
        PDUInputStream pIN = new PDUInputStream(inputStream);
        assertEquals(PDU_CORRUPT.class,pIN.readPdu().getClass());
    }

    @Test
    public void TestPDUINplusOUT() throws IOException {

        Socket socket = new Socket("scratchy.cs.umu.se",10057);
        PDUOutputStream out = new PDUOutputStream(socket.getOutputStream());
        PDUInputStream in = new PDUInputStream(socket.getInputStream());
        PDU recieved;
        out.sendPDU(new PDU_JOIN("Kalle"));
        boolean sendone = true;
        int i = 0;
        while(i<2) {
            if (in.hasPDU()) {
                recieved = in.readPdu();
                recieved.print();
                if (sendone) {
                    assertEquals(PDU_PARTICIPANTS.class,recieved.getClass());
                    out.sendPDU(new PDU_MESS("Test", "Kalle"));
                    sendone = false;
                }
                i++;
                if(i==2)
                    assertEquals(PDU_MESS.class,recieved.getClass());
            }
        }

        out.sendPDU(new PDU_QUIT());
    }


}
