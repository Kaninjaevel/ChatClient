package PDU;

import java.io.InputStream;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */



/**
 * Abstract class representing a PDU-package.
 */
public abstract class PDU {
    protected byte[] bytes;
    protected ByteSequenceBuilder byteSequenceBuilder;

    PDU(){}

    /**
     Abstract constructor for incoming PDU's
     * @param inputStream of the incoming Pdu's.
     */
    public PDU(InputStream inputStream){};

    /**
     * @return the bytearray of current PDU.
     */
    public byte[] getBytes(){return bytes;}

    /**
     * How the PDU is supposed to be printed to console.
     */
    abstract public void print();
}
