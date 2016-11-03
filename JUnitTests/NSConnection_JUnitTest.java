package JUnitTests;

import PDU.PDU_SLIST;
import Threads.NSCommunication;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.assertEquals;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class NSConnection_JUnitTest {

    @Test
    public void test() throws IOException {
        Socket socket = new Socket("itchy.cs.umu.se",1338);
        NSCommunication NS = new NSCommunication(socket);
        assertEquals(PDU_SLIST.class,NS.getReceivedPDU().getClass());
        socket.close();
    }

}
