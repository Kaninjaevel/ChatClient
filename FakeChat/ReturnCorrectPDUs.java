package FakeChat;

import PDU.PDU_MESS;
import PDU.PDU_QUIT;

import java.io.IOException;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class ReturnCorrectPDUs extends AbstractFakeChat {
    int i = 0;
    public ReturnCorrectPDUs(int port) throws IOException {
        super(port);
    }

    /**
     * returns a participant pdu and when next pdu is
     * received it will return a fake pdu.
     */

    @Override
    void returnInfo() throws IOException {
        byte[] b= {19,2,0,9,'P','0',0,'K','A','L','L','E',0,0,0,0};
        byte[] b2= {(byte)16,(byte)5,(byte)0,(byte)0,0,0,0,0
                ,'K','A','L','L','E',0,0,0};
        byte[] b3= {(byte)17,(byte)5,(byte)0,(byte)0,0,0,0,0
                ,'K','A','L','L','E',0,0,0};
        out.write(b);
        out.write(b3);
        out.write(b2);
        out.write(new PDU_MESS("TJENARE!","KALLE!").getBytes());

        out.write(new PDU_QUIT().getBytes());
    }
    public static void main(String[] args) throws IOException {
        new ReturnCorrectPDUs(new Integer(args[0]));
    }
}
