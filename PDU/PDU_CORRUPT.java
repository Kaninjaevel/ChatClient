package PDU;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

/**
 * Created if incoming pdu is corrupt in some way.
 */
public class PDU_CORRUPT extends PDU{

    public PDU_CORRUPT(){}
    @Override
    public void print() {
        System.out.println(new UnixTimeConverter(System.currentTimeMillis())
                .getDateString() + "--Corrupt PDU received--" +
                "\nPRESS ENTER TO QUIT");
    }
}
