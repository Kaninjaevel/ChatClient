package JUnitTests;
import PDU.*;
import Threads.*;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class CSInThreadJUnit {

    /**
     * Testing to receive a PDU
     * Control so that it ends up in the queue
     * @throws IOException
     */
    @Test
    public void shouldAddToQue() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = new Socket("localhost",2000);
        PDUOutputStream out = new PDUOutputStream(socket.getOutputStream());
        CSInThread inThread = new CSInThread(serverSocket.accept());
        inThread.start();
        out.sendPDU(new PDU_QUIT());
        sleep(400);
        assertEquals(false,inThread.queueIsEmpty());

        serverSocket.close();
        socket.close();
    }

    /**
     * Testing to recieve ten PDUs
     * Control so that all of them are in the queue
     * Print them all
     * Check so that the queue is empty.
     */

    @Test
    public void shouldAddTenToQueThenPrint() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = new Socket("localhost",2000);
        PDUOutputStream out = new PDUOutputStream(socket.getOutputStream());
        CSInThread inThread = new CSInThread(serverSocket.accept());
        inThread.start();
        for(int i=0;i<9;i++)
        out.sendPDU(new PDU_MESS("TJA", "Kalle"));
        out.sendPDU(new PDU_QUIT());
        sleep(1000);
        assertEquals(10,inThread.queueLength());
        for(int i = 0;i<10;i++)
            inThread.printNextPDU();
        assertEquals(true,inThread.queueIsEmpty());

        serverSocket.close();
        socket.close();
    }

    /**
     * After receiving PDU_QUIT
     * Control so that the thread closes.
     */

    @Test
    public void testIfAliveAfterQuit() throws IOException, InterruptedException{
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = new Socket("localhost",2000);
        PDUOutputStream out = new PDUOutputStream(socket.getOutputStream());
        CSInThread inThread = new CSInThread(serverSocket.accept());
        inThread.start();
        out.sendPDU(new PDU_QUIT());
        sleep(400);
        assertEquals(false,inThread.isAlive());
        serverSocket.close();
        socket.close();
    }

    /**
     * After receiving an corrupt PDU
     * Control so that the thread
     */

    @Test
    public void recieveCorrupPDU() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = new Socket("localhost",2000);
        OutputStream out = socket.getOutputStream();
        CSInThread inThread = new CSInThread(serverSocket.accept());
        inThread.start();
        out.write(355);
        sleep(400);
        assertEquals(false,inThread.isValidPDU());
        assertEquals(false,inThread.isAlive());

        serverSocket.close();
        socket.close();
    }
}
