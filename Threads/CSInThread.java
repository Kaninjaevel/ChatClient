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
 * Class representing the InputThread from a given TCP-bound chat server
 * following the same PDU-protocol as given by the course 5DV167
 */
public class CSInThread {
    private Thread thread;
    private ConcurrentLinkedQueue<PDU> inQueue;
    private PDUInputStream pduInputStream;


    public CSInThread(Socket socket) throws IOException {
        inQueue = new ConcurrentLinkedQueue<>();
        pduInputStream=new PDUInputStream(socket.getInputStream());
    }


    public void start(){
        thread =new Thread(){
            @Override
            public void run() {
                boolean running = true;
                PDU temp;
                while (running) {
                    try {
                        if (pduInputStream.hasPDU()) {
                            temp = pduInputStream.readPdu();
                            if (temp.getClass().equals(PDU_QUIT.class)
                                    ||temp.getClass().equals(PDU_CORRUPT.class))
                                running = false;
                            inQueue.add(temp);
                        }
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
     * @return if the pdu is valid.
     */
    public boolean isValidPDU(){
        boolean validPDU=true;

        if(inQueue.peek().getClass().equals(PDU_CORRUPT.class))
            validPDU=false;

        return validPDU;
    }

    /**
     * Prints the next PDU in queue to console.
     */
    public void printNextPDU(){
        inQueue.poll().print();
    }

    /**
     * @return if the next PDU is a Quit pdu
     */
    public boolean pduIsQuit(){return inQueue.peek().getClass().equals(PDU_QUIT.class);}

    /**
     * @return if the queue is empty.
     */
    public boolean queueIsEmpty(){return inQueue.isEmpty();}

    /**
     * Peeks at first value of queue, used in tests.
     * @return
     */
    public PDU getPDU(){ return inQueue.peek();}

    /**
     * Checks the length of the queue, used in tests.
     * @return
     */
    public int queueLength(){return inQueue.size();}

    /**
     * Checks if the thread is alive, used in tests.
     * @return
     */
    public boolean isAlive(){return thread.isAlive();}

}
