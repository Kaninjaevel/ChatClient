package PDU;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


/**
 *  A simple class that converts Unix Timestamp into the format
 *  "<dd-MM-yyy HH:mm:ss>".
 */
public class UnixTimeConverter {
    String dateString;

    public UnixTimeConverter(){}
    /**
     * Converts unixTime to proper string.
     * @param unixTime Unix-time stamp.
     */
    public UnixTimeConverter(long unixTime){
        Date date = new Date(unixTime*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateString = sdf.format(date);
        dateString= "<" + dateString + "> ";
    }

    public int byteArrayToUnixTime(byte[] bytes){

        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * @return String representing unix-time.
     */
    public String getDateString(){return dateString;}
}
