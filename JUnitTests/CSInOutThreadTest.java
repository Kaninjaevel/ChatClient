package JUnitTests;

import PDU.PDU_JOIN;
import Threads.CSInThread;
import Threads.CSOutThread;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class CSInOutThreadTest {

    /**
     * Connecting to scratchy.cs.umu.se
     * Sends a PDU-join
     * Checks if pdu is recieved
     * that pdu is printed
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test() throws IOException, InterruptedException {
        Socket socket = new Socket("scratchy.cs.umu.se",10057);

        CSInThread in = new CSInThread(socket);
        CSOutThread out = new CSOutThread(socket);
        out.start();
        out.sendPDU(new PDU_JOIN("Groupie#2"));
        in.start();

        new Thread(){
            @Override
            public void run(){
                boolean recieve=true;
                while(recieve)
                    if(!in.queueIsEmpty()) {
                        in.printNextPDU();
                    }
            }
        }.start();

        sleep(400);
        socket.close();

    }
}
