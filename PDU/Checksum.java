package PDU;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


public class Checksum {

    /**
     * @param buf The buffer to calculate the checksum for.
     * @return The checksum. Should be -1 when checking whether a PDU has
     * correct checksum.
     */
    public byte computeChecksum(byte[] buf) {
        int checksum=0;
        for(byte b : buf) {
            checksum = checksum + (0xff & b);
            if(checksum>255)
                checksum=(checksum-255);
        }
        return (byte)(255-checksum);
    }
}
