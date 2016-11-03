package PDU;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class PDU_MESS extends PDU {
    private InputStream in;
    private int checkSum,identitylength,messageLength;
    private String message,identity;
    private long unixTime;


    /**
     * Builds a Message PDU with a given string representing the message and
     * the identity of the client.
     * @param message client message
     * @param identity the name of the client
     */
    public PDU_MESS(String message, String identity) throws UnsupportedEncodingException {
        this.message=message;
        this.identity=identity;
        byte[] b;

        unixTime=0;
        this.checkSum=0;

        byteSequenceBuilder
                = new ByteSequenceBuilder((byte)10);

        byteSequenceBuilder.append((byte)0);

        byteSequenceBuilder.append((byte)identity.getBytes("UTF-8").length);

        byteSequenceBuilder.append((byte)checkSum);

        byteSequenceBuilder.appendShort(
                (short)message.getBytes("UTF-8").length).pad();

        b=ByteBuffer.allocate(4).putInt((int)unixTime).array();
        byteSequenceBuilder.append(b);

        byteSequenceBuilder.append(message.getBytes("UTF-8")).pad();
        byteSequenceBuilder.append(identity.getBytes("UTF-8")).pad();

        bytes = byteSequenceBuilder.toByteArray();
        bytes[3] = new Checksum().computeChecksum(bytes);

    }

    /**
     * Builds a byte array representing the full Message PDU from a stream.
     * Also saves the necessary variables to be able to print
     * the PDU to console.
     *
     * @param inputStream where the pdu should be read from.
     */
    public PDU_MESS(InputStream inputStream) throws IOException {
        byte b;
        byte[] bA=new byte[1];
        this.in=inputStream;
        in.read(bA,0,1);
        byteSequenceBuilder= new ByteSequenceBuilder((byte)10)
                    .append(bA);


        //Checking length of identity
        identitylength=in.read();
        byteSequenceBuilder.append((byte)identitylength);

        //Reading in checksum
        checkSum=in.read();
        byteSequenceBuilder.append((byte)checkSum);


        //Checking message length
        bA = new byte[2];
        in.read(bA,0,2);
        messageLength = ((bA[0] & 0xff) << 8) | (bA[1] & 0xff);
        byteSequenceBuilder.append(bA);


        //Taking 2bytes of padding from stream.
        in.read(bA,0,2);
        byteSequenceBuilder.append(bA);


        //Checking timestamp
        bA = new byte[4];
        in.read(bA,0,4);
        unixTime= new UnixTimeConverter().byteArrayToUnixTime(bA);
        byteSequenceBuilder.append(bA);


        //Collecting message
        bA = new byte[messageLength];
        in.read(bA,0,messageLength);
        byteSequenceBuilder.append(bA);
        message = new String(bA,"UTF-8");

        bA=new byte[1];
        //Checking for padding and removes them from stream
        if(!(messageLength%4==0))
            for(int i=0;i<(4-(messageLength%4));i++) {
                in.read(bA,0,1);
                byteSequenceBuilder.append(bA);
            }


        //Collecting identity
        bA = new byte[identitylength];
        in.read(bA,0,identitylength);
        byteSequenceBuilder.append(bA);
        identity= new String(bA,"UTF-8");

        bA = new byte[1];
        //Removing padding if found in stream.
        if(!((identitylength % 4) == 0))
            for (int i = 0; i < (4 - (identitylength % 4)); i++) {
                in.read(bA,0,1);
                byteSequenceBuilder.append(bA);
            }

        bytes = byteSequenceBuilder.toByteArray();

        //Checks if pdu is corrupt.
        isCorrupt();

    }

    /**
     * Method to print the message to the chat-client.
     */
    @Override
    public void print() {
        System.out.println(new UnixTimeConverter(unixTime).getDateString()
                +"["+ identity + "]: " + message);
    }

    /**
     * Method calculate checksum and see if PDU is corrupt.
     */

    public void isCorrupt(){

        if(new Checksum().computeChecksum(bytes)!=0){

            bytes = byteSequenceBuilder.toByteArray();
            for(int j=0;j<bytes.length;j++)
                System.out.print(bytes[j] + " ");
            System.out.println("\n");
            bytes = null;
        }
    }
}
