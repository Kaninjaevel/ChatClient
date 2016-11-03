package PDU;

import java.io.IOException;
import java.io.InputStream;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 * Creates a PJOIN PDU package from an inputStream.
 */
public class PDU_PJOIN extends PDU {

    String identity;
    int identityLenght;
    long unixTime;
    InputStream in;

    /**
     * From an inputStream this constructor creates a PJOIN PDU.
     *
     * @param inputStream where the pdu should be created from.
     * @throws IOException when inputStream is empty/missing/corrupt.
     */

    public PDU_PJOIN(InputStream inputStream) throws IOException {
        byteSequenceBuilder = new ByteSequenceBuilder((byte)16);
        in = inputStream;

        //ByteArray used for reading inputstream.
        byte[] bA = new byte[2];
        boolean isCorrupt=false;

        identityLenght=in.read();
        byteSequenceBuilder.append((byte)identityLenght);


        //Checking pad for indications of PDU being corrupt.
        in.read(bA,0,2);
        if(bA[0]!=0||bA[1]!=0)
            isCorrupt=true;
        byteSequenceBuilder.append(bA);

        bA = new byte[4];
        in.read(bA,0,4);

        unixTime= new UnixTimeConverter().byteArrayToUnixTime(bA);
        byteSequenceBuilder.append(bA);

        bA = new byte[identityLenght];
        in.read(bA,0,identityLenght);

        identity = new String(bA,"UTF-8");
        byteSequenceBuilder.append(bA);

        bA= new byte[1];
        int toPad = identityLenght % 4 == 0 ? 0 : 4 - (identityLenght % 4);

        for(int i=0;i<toPad;i++) {
            in.read(bA);
            if (bA[0] != 0) {
                isCorrupt = true;
            }
        }
        byteSequenceBuilder.append(bA[0]);

        //If the PDU is corrupt it's indicated by setting the byteArray to null.
        if(isCorrupt) {
            bytes = byteSequenceBuilder.toByteArray();
            for(int j=0;j<bytes.length;j++)
                System.out.print(bytes[j] + " ");
            System.out.println("\n");
            bytes = null;
        }
        else
            bytes=byteSequenceBuilder.toByteArray();

    }


    /**
     * Method to print to "chat-window"/console.
     */
    @Override
    public void print() {
        System.out.println(new UnixTimeConverter(unixTime).getDateString()
                + "["+ identity + "]: has joined the server!");
    }
}
