package FakeChat;

import java.io.IOException;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class ReturnFakePdu extends AbstractFakeChat{
    int i = 0;
    public ReturnFakePdu(int port) throws IOException {
        super(port);
    }

    /**
     * returns a participant pdu and when next pdu is
     * received it will return a fake Message pdu.
     */

    @Override
    void returnInfo() throws IOException {
        byte[] b= {19,2,0,9,'P','0',0,'K','0','0','L','E',0,2,2,2};
        byte[] b2= {10,2,20,1,1,2,2};
        if(i==0) {
            out.write(b);
            i++;
        }
        else
            out.write(b2);
    }
    public static void main(String[] args) throws IOException {
        new ReturnFakePdu(new Integer(args[0]));
    }
}
