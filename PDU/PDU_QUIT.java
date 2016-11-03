package PDU;

import java.io.IOException;
import java.io.InputStream;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PDU_QUIT extends PDU {

    InputStream in;
    public PDU_QUIT(){
        byteSequenceBuilder = new ByteSequenceBuilder((byte)11).pad();
        bytes=byteSequenceBuilder.toByteArray();
    }

    public PDU_QUIT(InputStream inputStream) throws IOException {
        in=inputStream;
        byte[] bA= new byte[3];
        boolean isCorrupt=false;

        byteSequenceBuilder= new ByteSequenceBuilder((byte)11);
        in.read(bA,0,3);
        if(bA[0]!=0||bA[1]!=0||bA[2]!=0)
            isCorrupt=true;
        byteSequenceBuilder.append(bA);


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
    public void print(){
        System.out.println(new UnixTimeConverter(System.currentTimeMillis())
                .getDateString() + "--Disconnected from server--" +
                "\nPRESS ENTER TO QUIT");
    }

}
