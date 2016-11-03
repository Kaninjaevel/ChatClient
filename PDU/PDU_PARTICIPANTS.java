package PDU;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PDU_PARTICIPANTS extends PDU {
    private String[] participants;


    public PDU_PARTICIPANTS(InputStream inputStream) throws IOException {
        InputStream in = inputStream;
        byte[] bA = new byte[3];
        in.read(bA,0,3);
        boolean isCorrupt=false;

        //Collecting information about the identities.
        int noOfIdentities = bA[0],length = ((bA[1]
                & 0xff) << 8) | (bA[2] & 0xff);
        byteSequenceBuilder = new ByteSequenceBuilder((byte)19).append(bA);

        //Collecting all UTF-8 characters involving identitites.
        bA= new byte[length];
        in.read(bA,0, length);

        //Collecting the separate strings representing unique identitites.
        participants= new String[noOfIdentities];
        byte[] temp = new byte[256];
        int i=0;
        int startOfIdentity=0;
        for (int j=0;j<length;j++) {
            if (bA[j]==0){
                participants[i] = new String(Arrays.copyOfRange
                        (bA,startOfIdentity,j),"UTF-8");
                startOfIdentity=j+1;
                i++;
            }
        }

        byteSequenceBuilder.append(bA);


        /** Adding null termination to bytes if there is no null termination
         *  is non-existent the file is labeled corrupt.
         */
        if(!(length %4==0)) {
            bA = new byte[(4- length % 4)];
            in.read(bA,0,(4- length %4));
            for(int j=0;j<bA.length;j++)
                if(bA[j]!=0)
                    isCorrupt=true;
            byteSequenceBuilder.append(bA);
        }


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


    @Override
    public void print() {
        System.out.println("Participants:");
        for (String participant : participants) {
            System.out.println("[" + participant + "]");
        }
    }
}
