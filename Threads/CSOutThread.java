package Threads;
import PDU.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 * Class representing the OutputThread to a given TCP-bound chat server
 * following the same PDU-protocol as given by the course 5DV167
 */

public class CSOutThread {
    private Thread thread;
    private ConcurrentLinkedQueue<PDU> outQueue;
    private PDUOutputStream pduOutputStream;


    /**
     * Creates an CSOutThread using a connected socket to a TCP-chat server.
     * @param socket connection to chat-server.
     * @throws IOException
     */
    public CSOutThread(Socket socket) throws IOException {
        pduOutputStream = new PDUOutputStream(socket.getOutputStream());
        outQueue = new ConcurrentLinkedQueue<>();
    }
    public void start(){
        thread =new Thread() {
        @Override
            public void run() {
                boolean running = true;

                while (running) {

                    if (!outQueue.isEmpty() && pduIsQuit())
                        running = false;

                    try {
                        if (!outQueue.isEmpty())
                           pduOutputStream.sendPDU(outQueue.poll());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                       sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }


    /**
     * @return if the next PDU is a QUIT pdu
     */
    private boolean pduIsQuit(){return outQueue.peek().getClass()
            .equals(PDU_QUIT.class);}

    /**
     * Stops the OutThread by sending a quit PDU to server.
     * @throws IOException
     */

    public void stop() throws IOException {
        sendPDU(new PDU_QUIT());
    }

    /**
     * Method to send a pdu to server.
     * @param pdu
     */
    public void sendPDU(PDU pdu){outQueue.add(pdu);}

    /**
     * @return if thread is alive, used for testing.
     */
    public boolean isAlive(){return thread.isAlive();}

}
