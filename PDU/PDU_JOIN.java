package PDU;

import java.io.UnsupportedEncodingException;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 * Class that creates a JOIN PDU package.
 * Should be sent to a server to request joining said server.
 */

public class PDU_JOIN extends PDU {

    /**
     * Creates a Join PDU packet from a string representing klient name.
     * @param identity name of client
     * @throws UnsupportedEncodingException if UTF-8 is not accepted.
     */
    public PDU_JOIN(String identity) throws UnsupportedEncodingException {
        byteSequenceBuilder= new ByteSequenceBuilder((byte)12);
        byteSequenceBuilder.append((byte)identity.getBytes("UTF-8").length);
        byteSequenceBuilder.pad();
        byteSequenceBuilder.append(identity.getBytes("UTF-8")).pad();
        super.bytes=byteSequenceBuilder.toByteArray();
    }

    //Is only sent from client to server so no need to be able to print.
    @Override
    public void print() {
        System.out.println("Join pdu");
    }

}
