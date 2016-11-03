package JUnitTests;
import PDU.*;
import Threads.*;
import org.junit.Test;


import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class CSOutThread_JUnitTest {


    /**
     * Controlling the threads functionality by sending a PDU
     * to a local thread and checking if it is able to receive that PDU.
     * @throws IOException
     */

    @Test
    public void shouldBeAbleToSendTenMessages() throws IOException {

        ServerSocket socket = new ServerSocket(2000);
        Thread thread;

        thread =new Thread (){
            @Override
            public void run(){
                int i=0;
                PDU inPDU;
                try {
                    Socket socket2 = socket.accept();
                    PDUInputStream inputStream =
                            new PDUInputStream(socket2.getInputStream());
                    while(i<10) {
                        if(inputStream.hasPDU()){
                            inPDU=inputStream.readPdu();
                            assertEquals(PDU_MESS.class,inPDU.getClass());
                            i++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        thread.start();

        CSOutThread out = new CSOutThread(new Socket("127.0.0.1", 2000));

        out.start();
        for(int i=0;i<10;i++){
            out.sendPDU(new PDU_MESS("Tjosan", "Kalle"));
        }

        while(thread.isAlive());
        socket.close();
    }

    /**
     * After sending a QUIT_PDU the thread should stop.
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void shouldQUIT() throws IOException, InterruptedException {

        new Thread() {
            @Override
            public void run(){
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(2000);
                    serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        CSOutThread out = new CSOutThread(new Socket("127.0.0.1",2000));
        out.start();
        out.sendPDU(new PDU_QUIT());
        sleep(400);
        assertEquals(false,out.isAlive());
    }
}
