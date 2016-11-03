package Threads;

import PDU.*;

import java.io.IOException;
import java.net.Socket;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

/**
 * Class representing the communication to a given TCP-bound name server
 * following the same PDU-protocol as given by the course 5DV167
 */

public class NSCommunication {

    private PDUInputStream inputStream;
    private PDUOutputStream outputStream;
    private PDU receivedPDU;
    private Socket socket;
    /**
     * Creates a connection between the client and a name server,
     * @param socket socket which is connected to the name-server
     * @throws IOException
     */

    public NSCommunication(Socket socket) throws IOException {
        this.socket=socket;
        System.out.println("Connecting to name-server.. "
                + "<" + socket.getRemoteSocketAddress()+ ">");
        outputStream = new PDUOutputStream(this.socket.getOutputStream());
        inputStream = new PDUInputStream(this.socket.getInputStream());
        connect();
    }

    /**
     * sends a PDU that indicates that a list of servers wants to be
     * received and awaits response. When response is received and
     * is correct the list of servers will be presented to the client.
     * @throws IOException
     */
    public void connect() throws IOException {

        boolean receive = true;

        //Sending GETLIST to name-server
        outputStream.sendPDU(new PDU_GETLIST());

        //Awaiting response from server.
        while (receive) if (inputStream.hasPDU()) {
            receivedPDU = inputStream.readPdu();
            if (!receivedPDU.getClass().equals(PDU_SLIST.class)) {
                throw new IOException("Corrupt Stream/PDU received");
            } else
                receivedPDU.print();
            receive = false;
        }
        socket.close();
    }
    //A method to check incoming pdu, only used in tests.
    public PDU getReceivedPDU(){return receivedPDU;}
}
