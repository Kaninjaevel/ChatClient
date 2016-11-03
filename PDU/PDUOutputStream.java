package PDU;

import java.io.*;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PDUOutputStream {
    private OutputStream outputStream;

    /**
     * creates a PDUOutputStream from a given outputstream.
     * @param outputStream where to send all information.
     */
    public PDUOutputStream(OutputStream outputStream){
        this.outputStream=outputStream;
    }

    /**
     * @param Pdu to be sent to given outputstream.
     * @throws IOException if outputstream is failing.
     */
    public void sendPDU(PDU Pdu) throws IOException{
        outputStream.write(Pdu.getBytes());
    }
}
