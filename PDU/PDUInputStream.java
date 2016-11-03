package PDU;

import java.io.IOException;
import java.io.InputStream;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class PDUInputStream {

    private InputStream inputStream;
    private int firstByte;
    private PDU nextPDU;

    public PDUInputStream(InputStream inputStream) {
        this.inputStream=inputStream;
        this.nextPDU=null;
    }

    /**
     * @return The next PDU in the stream. if no PDU was found null is returned.
     * @throws java.io.IOException If the stream closed with an error.
     */

    public PDU readPdu() throws IOException {

        firstByte=inputStream.read();

        switch(firstByte) {
            case 4:
                nextPDU = new PDU_SLIST(inputStream);
                break;
            case 10:
                nextPDU = new PDU_MESS(inputStream);
                break;
            case 11:
                nextPDU = new PDU_QUIT(inputStream);
                break;
            case 16:
                nextPDU = new PDU_PJOIN(inputStream);
                break;
            case 17:
                nextPDU = new PDU_PLEAVE(inputStream);
                break;
            case 19:
                nextPDU = new PDU_PARTICIPANTS(inputStream);
                break;
            default:
                nextPDU = new PDU_CORRUPT();
        }

        if(nextPDU.getBytes()==null)
            nextPDU=new PDU_CORRUPT();

        return nextPDU;
    }

    /**
     * @return boolean representation of an available PDU.
     * @throws IOException if the input-stream is unavailable.
     */
    public boolean hasPDU() throws IOException {
        return inputStream.available()!=0;
    }

}
