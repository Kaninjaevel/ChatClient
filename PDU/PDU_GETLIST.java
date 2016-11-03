package PDU;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 * Class that creates a GETLIST PDU package.
 */
public class PDU_GETLIST extends PDU {

    public PDU_GETLIST(){
        byteSequenceBuilder= new ByteSequenceBuilder((byte)3).pad();
        bytes=byteSequenceBuilder.toByteArray();
    }

    //Only sent from client to server so no need to be able to print.
    @Override
    public void print() {System.out.println("GETLIST PDU!");}
}
